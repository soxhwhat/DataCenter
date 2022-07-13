package com.juphoon.rtc.datacenter.entity.po.thea;

import com.juphoon.rtc.datacenter.api.EventContext;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

@Slf4j
public class TheaQualityMonitorPOTest {
    EventContext ec;


    @Before
    public void initEc() {
        log.info("init ec");
        ec = TheaEventContextUtils.getEventContext();
    }

    @Test
    public void fromEvent() {
//        MonitorAcdAgentStatePO po = MonitorAcdAgentStatePO.fromState(context);
        TheaQualityMonitorPO po = TheaQualityMonitorPO.fromEvent(ec);
        Assert.assertNotNull(po);
        //断言判断aMosLowCount的值是否为2
        Assert.assertEquals(2, (int) po.getAMosLowCount());
        //断言判断tMosLowCount的值是否为0
        Assert.assertEquals(0, (int) po.getTMosLowCount());
        Assert.assertEquals(2, (int) po.getTotalAMosCount());
        Assert.assertEquals(0, (int) po.getTotalTMosCount());
        Assert.assertEquals(1, (int) po.getUnLossCount());
        Assert.assertEquals(1, (int) po.getLossTotalCount());
        Assert.assertEquals(0, (int) po.getSuRtt());
        Assert.assertEquals(1, (int) po.getRttTotalCount());
        Assert.assertEquals(0, (int) po.getSuJitter());
        Assert.assertEquals(0, (int) po.getSdJitter());
        Assert.assertEquals(1, (int) po.getSuJitterTotalCount());
        Assert.assertEquals(1, (int) po.getSdJitterTotalCount());

    }
}