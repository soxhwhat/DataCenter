package com.juphoon.rtc.datacenter.handle.database.acdstat;

import com.juphoon.rtc.datacenter.api.Event;
import com.juphoon.rtc.datacenter.entity.po.acdstat.AcdExtTerminalPartPO;
import com.juphoon.rtc.datacenter.mapper.AcdExtTerminalPartMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>jrtc_acd_ext_terminal_part表的抽象handler类</p>
 *
 * @author wenjun.yuan@juphoon.com
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 * @since 2022/5/5
 */
@Slf4j
public abstract class AbstractAcdExtTerminalPartHandler extends AbstractAcdExtTerminalHandler<AcdExtTerminalPartPO> {

    @Autowired
    private AcdExtTerminalPartMapper acdExtTerminalPartMapper;

    @Override
    public AcdExtTerminalPartPO poFromEvent(Event event) {
        AcdExtTerminalPartPO po = new AcdExtTerminalPartPO();
        po.fromEvent(event);
        po.setStatType(statType().getStatType());
        return po;
    }

    @Override
    public AcdExtTerminalPartPO selectByUnique(AcdExtTerminalPartPO po) {
        return acdExtTerminalPartMapper.selectByUniqueKey(po.getUniqueKey());
    }

    @Override
    public int insertSelective(AcdExtTerminalPartPO po) {
        return acdExtTerminalPartMapper.insertSelective(po);
    }

    @Override
    public void updateByUniqueKey(AcdExtTerminalPartPO po) {
        acdExtTerminalPartMapper.updateAddValueByUniqueKey(po.getUniqueKey(), po.getCnt());
    }

}
