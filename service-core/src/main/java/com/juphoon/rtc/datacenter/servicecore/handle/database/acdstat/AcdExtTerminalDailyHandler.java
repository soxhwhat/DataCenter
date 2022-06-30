package com.juphoon.rtc.datacenter.servicecore.handle.database.acdstat;

import com.juphoon.rtc.datacenter.datacore.api.Event;
import com.juphoon.rtc.datacenter.datacore.api.HandlerId;
import com.juphoon.rtc.datacenter.datacore.api.StatType;
import com.juphoon.rtc.datacenter.servicecore.entity.po.acdstat.AcdExtTerminalDailyPO;
import com.juphoon.rtc.datacenter.servicecore.mapper.AcdExtTerminalDailyMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * <p>处理日渠道平台的统计信息</p>
 *
 * @author wenjun.yuan@juphoon.com
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 * @since 2022/5/5
 */
@Slf4j
@Component
public class AcdExtTerminalDailyHandler extends AbstractAcdExtTerminalHandler<AcdExtTerminalDailyPO> {

    @Autowired
    private AcdExtTerminalDailyMapper acdExtTerminalDailyMapper;

    @Override
    public HandlerId handlerId() {
        return HandlerId.AcdExtTerminalDailyHandler;
    }

    @Override
    public StatType statType() {
        return StatType.STAT_DAY;
    }

    @Override
    public AcdExtTerminalDailyPO poFromEvent(Event event) {
        AcdExtTerminalDailyPO po = new AcdExtTerminalDailyPO();
        po.fromEvent(event);
        return po;
    }

    @Override
    public AcdExtTerminalDailyPO selectByUnique(AcdExtTerminalDailyPO po) {
        return acdExtTerminalDailyMapper.selectByUniqueKey(po.getUniqueKey());
    }

    @Override
    public int insertSelective(AcdExtTerminalDailyPO commonPo) {
        return acdExtTerminalDailyMapper.insertSelective(commonPo);
    }

    @Override
    public void updateByUniqueKey(AcdExtTerminalDailyPO po) {
        acdExtTerminalDailyMapper.updateAddValueByUniqueKey(po.getUniqueKey(), po.getCnt());
    }

}
