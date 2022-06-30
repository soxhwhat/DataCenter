package com.juphoon.rtc.datacenter.servicecore.handle.database.acdstat;

import com.juphoon.rtc.datacenter.datacore.api.Event;
import com.juphoon.rtc.datacenter.servicecore.entity.po.acdstat.AcdExtServiceLevelPartPO;
import com.juphoon.rtc.datacenter.servicecore.mapper.AcdExtServiceLevelPartMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * <p>jrtc_acd_ext_connect_part表的抽象handler类</p>
 *
 * @author wenjun.yuan@juphoon.com
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 * @since 2022/4/6
 */
@Slf4j
@Component
public abstract class AbstractAcdExtServiceLevelPartHandler extends AbstractAcdExtServiceLevelHandler<AcdExtServiceLevelPartPO> {

    @Autowired
    private AcdExtServiceLevelPartMapper acdExtServiceLevelPartMapper;

    @Override
    public AcdExtServiceLevelPartPO poFromEvent(Event event) {
        AcdExtServiceLevelPartPO po = new AcdExtServiceLevelPartPO();
        po.fromEvent(event);
        po.setStatType(statType().getStatType());
        return po;
    }

    @Override
    public AcdExtServiceLevelPartPO selectByUnique(AcdExtServiceLevelPartPO po) {
        return acdExtServiceLevelPartMapper.selectByUniqueKey(po.getUniqueKey());
    }

    @Override
    public int insertSelective(AcdExtServiceLevelPartPO po) {
        return acdExtServiceLevelPartMapper.insertSelective(po);
    }

    @Override
    public void updateByUniqueKey(AcdExtServiceLevelPartPO po) {
        acdExtServiceLevelPartMapper.updateAddValueByUniqueKey(po.getUniqueKey(), po.getCnt());
    }

}
