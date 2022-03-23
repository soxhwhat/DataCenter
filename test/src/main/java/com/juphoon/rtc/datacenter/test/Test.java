package com.juphoon.rtc.datacenter.test;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.List;

/**
 * <p>在开始处详细描述该类的作用</p>
 * <p>描述请遵循 javadoc 规范</p>
 * <p>TODO</p>
 *
 * @author ajian.zheng@juphoon.com
 * @date 3/23/22 10:54 AM
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Slf4j
public class Test {

    @Data
    public static class Pojo {
        private Long statTimestamp;

        private Long duration;
    }

    public static long getStatTimestamp(long begin, long gap) {
        long remainder = begin % gap;
        return begin - remainder;
    }

    public static List<Pojo> split(long begin, long end, long interval) {
        List<Pojo> ret = new LinkedList<>();

        // 跨时段
        while (begin + interval <= end) {
            Pojo pojo = new Pojo();

            long statTimestamp = getStatTimestamp(begin, interval);
            long duration = statTimestamp + interval - begin;

            pojo.setStatTimestamp(statTimestamp);
            pojo.setDuration(duration);
            ret.add(pojo);

            begin = statTimestamp + interval;
        }

        // 首段或最后一段
        if (begin < end) {
            Pojo pojo = new Pojo();
            long statTimestamp = getStatTimestamp(begin, interval);
            long duration = end - begin;
            pojo.setStatTimestamp(statTimestamp);
            pojo.setDuration(duration);
            ret.add(pojo);
        }

        return ret;
    }

    public static void dump(List<Pojo> list) {
        list.forEach(p -> log.info("{}", p));
        log.info("----------------");
    }

    public static void main(String[] args) {
        // 15 分钟
        long interval = 15 * 60 * 1000;

        // 2022.1.1 00:05:00
        long begin = 1640966700000L;

        // case 1 1段
        // 2022.1.1 00:10:00
        long end1 = 1640967000000L;
        dump(split(begin, end1, interval));

        // case 2 2段
        // 2022.1.1 00:20:00
        long end2 = 1640967600000L;
        dump(split(begin, end2, interval));

        // case 3 3段
        // 2022.1.1 00:50:00
        long end3 = 1640969400000L;
        dump(split(begin, end3, interval));

        // case 4 3段边界
        // 2022.1.1 00:45:00
        long end4 = 1640969100000L;
        dump(split(begin, end4, interval));

        // TODO 边界测试
    }
}
