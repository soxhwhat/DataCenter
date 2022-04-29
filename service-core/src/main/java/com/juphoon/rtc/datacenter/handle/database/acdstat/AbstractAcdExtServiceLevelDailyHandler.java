package com.juphoon.rtc.datacenter.handle.database.acdstat;

import com.juphoon.rtc.datacenter.api.*;
import com.juphoon.rtc.datacenter.entity.po.acdstat.AcdExtServiceLevelDailyPO;
import com.juphoon.rtc.datacenter.entity.po.acdstat.AcdExtServiceLevelPO;
import com.juphoon.rtc.datacenter.mapper.AcdExtServiceLevelDailyMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * <p>jrtc_acd_ext_connect_daily表的抽象handler类</p>
 *
 * @author wenjun.yuan@juphoon.com
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 * @since 2022/4/6
 */
@Slf4j
@Component
public abstract class AbstractAcdExtServiceLevelDailyHandler extends AbstractAcdExtServiceLevelHandler {

    @Autowired
    private AcdExtServiceLevelDailyMapper acdExtServiceLevelDailyMapper;

    @Override
    public StatType statType() {
        return StatType.STAT_DAY;
    }

    @Override
    public AcdExtServiceLevelDailyPO poFromEvent(Event event) {
        AcdExtServiceLevelDailyPO po = new AcdExtServiceLevelDailyPO();
        po.fromEvent(event);
        return po;
    }

    @Override
    public AcdExtServiceLevelDailyPO selectByUnique(AcdExtServiceLevelPO po) {
        return acdExtServiceLevelDailyMapper.selectByUniqueKey(po.getUniqueKey());
    }

    /**
     * todo 重新抽象
     */
    @Override
    public int insertSelective(AcdExtServiceLevelPO commonPo) {
        if (commonPo instanceof AcdExtServiceLevelDailyPO) {
            return acdExtServiceLevelDailyMapper.insertSelective((AcdExtServiceLevelDailyPO) commonPo);
        }

        throw new RuntimeException("invalid object");
    }

    /**
     * todo 重新抽象
     */
    @Override
    public void updateByUniqueKey(AcdExtServiceLevelPO po) {
        if (po instanceof AcdExtServiceLevelDailyPO) {
            acdExtServiceLevelDailyMapper.updateAddValueByUniqueKey(po.getUniqueKey(), po.getCnt());
        }

        throw new RuntimeException("invalid object");
    }

}
