package com.juphoon.rtc.datacenter.servicecore.handle.database.acdstat;

import com.juphoon.rtc.datacenter.datacore.api.Event;
import com.juphoon.rtc.datacenter.datacore.api.EventContext;
import com.juphoon.rtc.datacenter.datacore.api.StatType;
import com.juphoon.rtc.datacenter.servicecore.entity.po.acdstat.AcdCommonPO;
import com.juphoon.rtc.datacenter.datacore.handler.AbstractEventHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.time.*;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>数据库表handler的抽象类</p>
 * <p>描述请遵循 javadoc 规范</p>
 *
 * @author wenjun.yuan@juphoon.com
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 * @since 2022/3/10 14:44
 */
@Slf4j
public abstract class AbstractAcdStatHandler<T extends AcdCommonPO> extends AbstractEventHandler {

    /**
     * 统计类型
     *
     * @return
     */
    public abstract StatType statType();

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean handle(EventContext ec) {
        boolean ret = true;

        T po = poFromEvent(ec.getEvent());
        try {
            ret = handle(ec, po);
        } catch (Exception e) {
            log.warn("ec.id[{}], handler[{}] handle failed!", ec.getId(), handlerId().getName());
            log.warn(e.getMessage(), e);
        }
        return ret;
    }

    /**
     * 统一回调处理
     * @param ec
     * @param po
     * @return
     * @throws Exception
     */
    public boolean handle(EventContext ec, T po) throws Exception {
        long beginTimestamp = ec.getEvent().beginTimestamp();
        long endTimestamp = ec.getEvent().endTimestamp();

        List<T> list = splitStatTime(po, beginTimestamp, endTimestamp, statType());
        try {
            list.forEach(this::upsert);
        } catch (Exception e) {
            log.warn("ec.id[{}], handler[{}] handle failed!", ec.getId(), handlerId().getName());
            log.warn(e.getMessage(), e);
            // 捕获了异常，需手动回滚事务
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return false;
        }

        return true;
    }

    /**
     * 插入或更新数据
     * // TODO 优化
     *
     * @param commonPo
     * @return
     */
    protected void upsert(T commonPo) {
        T localObj = selectByUnique(commonPo);
        if (null == localObj) {
            // 上锁，保证并发时 确定只有一条数据insert
            synchronized (this) {
                localObj = selectByUnique(commonPo);
                if (null == localObj) {
                    try {
                        insertSelective(commonPo);
                    } catch (DuplicateKeyException e) {
                        tryUpdate(commonPo);
                    }
                    return;
                }
            }
        }
        updateByUniqueKey(commonPo);
    }

    protected void tryUpdate(T commonPo) {
        T localObj = selectByUnique(commonPo);
        if (null != localObj) {
            updateByUniqueKey(commonPo);
        } else {
            log.warn("{}", commonPo.toString());
            throw new RuntimeException("insert failed, then update ,but not found data!!!");
        }
    }

    /**
     * 前提：
     *      起始时间：a
     *      结束时间：b
     *      区间起始时间：statTime
     * 特殊情况：(a或b在区间的时间节点上或相同)
     *    同一个区间内：  |.ab...|......|
     *      1； a,b在同一个时间点，并在某个区间的起始时间（同结束时间）    |......(ab)......|
     *          说明：首个statTime=a,b  进入while，if(statTime + interval > b)=true，结束
     *      2： a,b在同一个时间点，并在某个区间内的某个时间点  |.....(ab).|......|
     *          说明：首个statTime为该区间的起始时间，statTime < a,b， 进入while，if(statTime + interval > b))=true，结束
     *      3： a,b不在同一个时间点但在同一个区间内， a在起始时间点, b不在结束时间点   a....b.|......|
     *          说明：首个statTime为该区间的起始时间，statTime = a < b， 进入while，if(statTime + interval > b))=true，结束
     *      4： a,b不在同一个时间点但在同一个区间内， a在起始时间点, b在结束时间点（下一个区间的起始时间点）   a......b......|
     *          说明：首个statTime为该区间的起始时间，statTime = a < b， 进入while，if(statTime + interval = b))=true，结束
     *      5： a,b不在同一个时间点但在同一个区间内， a不在起始时间点, b在结束时间点（下一个区间的起始时间点）  |..a...b......|
     *          说明：首个statTime为该区间的起始时间，statTime < a < b， 进入while，if(statTime + interval = b))=true，结束
     *    跨一个区间：  |.a....|...b..|......|
     *      6： a在起始时间点, b不在结束时间点    a......|...b..|......|
     *          说明：首个statTime为该区间的起始时间，statTime = a < b， 进入while，if(statTime + interval < b))=false，
     *          计算首个区间的时间if (a = statTime)=true, duration=interval,  statTime = 首个statTime + interval(下个区间的起始时间)
     *          下个循环if(statTime + interval > b))=true，结束
     *      7： a在起始时间点, b在结束时间点     a......|......b......|
     *          说明：首个statTime为该区间的起始时间，statTime = a < b， 进入while，if(statTime + interval < b))=false，
     *          计算首个区间的时间if (a = statTime)=true, duration=interval,  statTime = 首个statTime + interval(下个区间的起始时间)
     *          下个循环if(statTime + interval = b))=true，结束
     *      8： a不在起始时间点, b在结束时间点（下一个区间的起始时间点）   |.a....|......b......|
     *          说明：首个statTime为该区间的起始时间，statTime < a < b， 进入while，if(statTime + interval < b))=false，
     *          计算首个区间的时间if (a > statTime)=false, duration=interval-(a-首个statTime),  statTime = 首个statTime + interval(下个区间的起始时间)
     *          下个循环if(statTime + interval = b))=true，结束
     *    跨n区间：  |.a....|......|..b...|
     *      与跨一个区间类似，如跨2次，就是2次循环，3次就是3次循环。
     *      但在第二次循环及之后if (a > statTime)的判断永远为false。
     *
     *  正常情况：(a和b都不在区间的时间节点上)
     *    同一个区间内：  |.a..b.|......|
     *      9： 参考5
     *    跨一个区间：  |.a....|...b..|......|
     *      10： 参考8
     *    跨n区间： 同特殊情况的跨n区间
     */
    private List<T> splitStatTime(T po, Long beginTimestamp, Long endTimestamp, StatType type) {
        long remainder = beginTimestamp % type.getInterval();
        long statTime = beginTimestamp - remainder;
        // 日的 时区要进行处理
        if (type.equals(StatType.STAT_DAY)) {
            statTime = LocalDateTime.of(Instant.ofEpochMilli(beginTimestamp).atZone(ZoneOffset.ofHours(8)).toLocalDate(), LocalTime.MIN)
                    .toInstant(ZoneOffset.ofHours(8)).toEpochMilli();
            remainder = beginTimestamp - statTime;
        }

        List<T> list = new ArrayList<>();

        while (statTime <= endTimestamp) {
            long duration = type.getInterval();

            T p = SerializationUtils.clone(po);

            p.setStatTime(statTime);

            // statTime + interval为下个区间的起始时间
            if (statTime + type.getInterval() >= endTimestamp) {
                if (beginTimestamp > statTime) {
                    // 一个区间内结束的
                    duration = endTimestamp - beginTimestamp;
                } else {
                    // 跨区间结束 也可能在临界点结束
                    duration = endTimestamp - statTime;
                }
                p.setDuration(duration);
                list.add(p);
                break;
            }
            // 到这里必定是跨区间的

            // 首个区间，比较起始时间和区间的时间
            if (beginTimestamp > statTime) {
                duration = type.getInterval() - remainder;
            }

            p.setDuration(duration);
            list.add(p);

            statTime = statTime + type.getInterval();
        }

        return list;
    }

    /**
     * 构建具体表的po
     *
     * @param event
     * @return
     */
    public abstract T poFromEvent(Event event);

    /**
     * 查询确定要更新的那一条数据
     *
     * @param po
     * @return
     */
    public abstract T selectByUnique(T po);

    /**
     * 写入数据
     *
     * @param commonPo
     * @return
     */
    public abstract int insertSelective(T commonPo);

    /**
     * 根据唯一字段更新值
     *
     * @param po
     * @return
     */
    public abstract void updateByUniqueKey(T po);

}
