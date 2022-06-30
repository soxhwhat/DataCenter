package com.juphoon.rtc.datacenter.servicecore.handle.database.acdstat;

import com.juphoon.rtc.datacenter.datacore.api.Event;
import com.juphoon.rtc.datacenter.datacore.api.HandlerId;
import com.juphoon.rtc.datacenter.datacore.api.StatType;
import com.juphoon.rtc.datacenter.servicecore.entity.po.acdstat.AcdExtServiceLevelDailyPO;
import com.juphoon.rtc.datacenter.servicecore.mapper.AcdExtServiceLevelDailyMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * <p>处理日接通量的统计信息</p>
 *
 * @author wenjun.yuan@juphoon.com
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 * @since 2022/4/6
 */
@Slf4j
@Component
public class AcdExtServiceLevelDailyHandler extends AbstractAcdExtServiceLevelHandler<AcdExtServiceLevelDailyPO> {

    @Autowired
    private AcdExtServiceLevelDailyMapper acdExtServiceLevelDailyMapper;

    @Override
    public HandlerId handlerId() {
        return HandlerId.AcdExtServiceLevelDailyHandler;
    }

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
    public AcdExtServiceLevelDailyPO selectByUnique(AcdExtServiceLevelDailyPO po) {
        return acdExtServiceLevelDailyMapper.selectByUniqueKey(po.getUniqueKey());
    }

    @Override
    public int insertSelective(AcdExtServiceLevelDailyPO commonPo) {
        return acdExtServiceLevelDailyMapper.insertSelective(commonPo);
    }

    @Override
    public void updateByUniqueKey(AcdExtServiceLevelDailyPO po) {
        acdExtServiceLevelDailyMapper.updateAddValueByUniqueKey(po.getUniqueKey(), po.getCnt());
    }
}
