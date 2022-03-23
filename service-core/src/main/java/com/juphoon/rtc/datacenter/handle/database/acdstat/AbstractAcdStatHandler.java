package com.juphoon.rtc.datacenter.handle.database.acdstat;

import com.juphoon.rtc.datacenter.api.Event;
import com.juphoon.rtc.datacenter.api.EventContext;
import com.juphoon.rtc.datacenter.api.StatType;
import com.juphoon.rtc.datacenter.entity.po.acdstat.AcdCommonPO;
import com.juphoon.rtc.datacenter.handler.AbstractEventHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.SerializationUtils;
import org.springframework.dao.DuplicateKeyException;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>数据库表handler的抽象类</p>
 * <p>描述请遵循 javadoc 规范</p>
 * <p>TODO</p>
 *
 * @author wenjun.yuan@juphoon.com
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 * @since 2022/3/10 14:44
 */
@Slf4j
public abstract class AbstractAcdStatHandler<T extends AcdCommonPO> extends AbstractEventHandler {

    @Override
    public boolean handle(EventContext ec) {
        boolean ret = true;

        T po = poFromEvent(ec.getEvent());
        try {
            handle(ec, po);
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                log.debug("ec:{}", ec, e);
                ret = false;
            }
        }

        return ret;
    }

    /**
     * 插入或更新数据
     * // TODO 优化
     *
     * @param commonPo
     * @return
     */
    public void upsert(T commonPo) {
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
                }
            }
        }

        updateByUniqueHashCode(localObj.getUniqueHashCode(), commonPo.getDuration(), commonPo.getCnt());
    }

    public void tryUpdate(T commonPo) {
        AcdCommonPO localObj = selectByUnique(commonPo);
        if (null != localObj) {
            updateByUniqueHashCode(localObj.getUniqueHashCode(), commonPo.getDuration(), commonPo.getCnt());
        } else {
            log.warn("{}", commonPo.toString());
            throw new RuntimeException("insert failed, then update ,but not found data!!!");
        }
    }

    public List<T> splitStatTime(T po, Long beginTimestamp, Long endTimestamp, StatType type) {
        long remainder = beginTimestamp % type.getInterval();
        long statTime = beginTimestamp - remainder;

        List<T> list = new ArrayList<>();

        while (statTime <= endTimestamp) {
            long duration = type.getInterval();

            T p = SerializationUtils.clone(po);

            p.setStatTime(statTime);

            if (statTime + type.getInterval() >= endTimestamp) {
                if (beginTimestamp > statTime) {
                    // 一个区间内结束的
                    duration = endTimestamp - beginTimestamp;
                } else {
                    // 跨区间结束
                    duration = endTimestamp - statTime;
                }
                p.setDuration(duration);
                list.add(p);
                break;
            }

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
     * 统一回调处理
     *
     * @param ec
     * @param po
     * @throws Exception
     */
    public abstract void handle(EventContext ec, T po) throws Exception;

    /**
     * 构建具体表的po
     * @param event
     * @return
     */
    public abstract T poFromEvent(Event event);

    /**
     * 查询确定要更新的那一条数据
     * @param po
     * @return
     */
    public abstract T selectByUnique(T po);

    /**
     * 写入数据
     * @param commonPo
     * @return
     */
    public abstract int insertSelective(T commonPo);

    /**
     * 根据唯一字段更新值
     * @param uniqueHashCode
     * @param duration
     * @param cnt
     * @return
     */
    public abstract int updateByUniqueHashCode(Integer uniqueHashCode, Long duration, Integer cnt);

}
