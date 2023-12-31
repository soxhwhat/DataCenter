package com.juphoon.rtc.datacenter.servicecore.handle.database.acdstat;

import com.juphoon.rtc.datacenter.datacore.api.Event;
import com.juphoon.rtc.datacenter.datacore.api.EventType;
import com.juphoon.rtc.datacenter.servicecore.entity.po.acdstat.AcdCallInfoStatPartPO;
import com.juphoon.rtc.datacenter.servicecore.mapper.AcdCallInfoStatPartMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;

import static com.juphoon.rtc.datacenter.datacore.api.EventType.*;

/**
 * <p>话务信息抽象类</p>
 *
 * @author ajian.zheng@juphoon.com
 * @date 3/23/22 7:35 PM
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Slf4j
public abstract class AbstractAcdCallInfoStatPartHandler extends AbstractAcdStatHandler<AcdCallInfoStatPartPO> {

    @Autowired
    private AcdCallInfoStatPartMapper acdCallInfoStatPartMapper;

    @Override
    public AcdCallInfoStatPartPO poFromEvent(Event event) {
        AcdCallInfoStatPartPO po = new AcdCallInfoStatPartPO();
        po.fromEvent(event);
        po.setStatType(statType().getStatType());
        return po;
    }

    @Override
    public List<EventType> careEvents() {
        // TODO 待定事件：转接，邀请坐席
        return Arrays.asList(TICKER_EVENT_WAIT, TICKER_EVENT_RING, TICKER_EVENT_TALK, TICKER_EVENT_OVERFLOW);
    }

    @Override
    public AcdCallInfoStatPartPO selectByUnique(AcdCallInfoStatPartPO po) {
        return acdCallInfoStatPartMapper.selectByUniqueKey(po.getUniqueKey());
    }

    @Override
    public int insertSelective(AcdCallInfoStatPartPO po) {
        return acdCallInfoStatPartMapper.insertSelective(po);
    }

    /***
     *
     * @param po
     * @return
     */
    @Override
    public void updateByUniqueKey(AcdCallInfoStatPartPO po) {
        acdCallInfoStatPartMapper.updateAddValueByUniqueKey(po.getUniqueKey(), po.getDuration(), po.getCnt());
    }

}
