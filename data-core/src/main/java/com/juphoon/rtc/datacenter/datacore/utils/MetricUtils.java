package com.juphoon.rtc.datacenter.datacore.utils;

import io.micrometer.core.instrument.Meter;
import io.micrometer.core.instrument.Metrics;
import io.micrometer.core.instrument.Timer;
import io.micrometer.core.instrument.search.Search;
import io.micrometer.core.instrument.simple.SimpleMeterRegistry;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p>在开始处详细描述该类的作用</p>
 * <p>描述请遵循 javadoc 规范</p>
 * <p>TODO</p>
 *
 * @author ajian.zheng@juphoon.com
 * @date 8/29/22 10:22 PM
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Slf4j
public class MetricUtils {
    private static Map<String, Timer> timerMap = new ConcurrentHashMap<>();

    static {
        Metrics.addRegistry(new SimpleMeterRegistry());
    }

    public static Timer get(String name) {
        Timer sample = timerMap.get(name);
        if (null == sample) {
            sample = Timer.builder(name)
                    .register(Metrics.globalRegistry);
            timerMap.put(name, sample);
        }

        return sample;
    }

    public static void dump() {
        Search.in(Metrics.globalRegistry).timers().forEach(MetricUtils::printMonitorMessage);
    }

    private static void printMonitorMessage(Meter meter) {
        StringBuffer logContext = new StringBuffer();

        logContext.append("name: ").append(meter.getId().getName());

        meter.measure().forEach((m) -> logContext.append("\t").append(m.getStatistic()).append(": ").append(m.getValue()));

        log.info(logContext.toString());
    }
}

