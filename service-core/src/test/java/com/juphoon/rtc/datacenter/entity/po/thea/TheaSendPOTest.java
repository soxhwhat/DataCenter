package com.juphoon.rtc.datacenter.entity.po.thea;

import com.juphoon.rtc.datacenter.api.EventContext;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

@Slf4j
public class TheaSendPOTest {
    EventContext ec;

    @Before
    public void initEc() {
        log.info("init ec");
        ec = TheaEventContextUtils.getEventContext();
    }
    @Test
    public void fromEvent() {
        TheaSendPO po = TheaSendPO.fromEvent(ec);

        Assert.assertNotNull(po);
        Assert.assertTrue(po.getCbAccountid().equals("[username:delivery_JMDS.Main0.Main01.Main_0@100074.cloud.justalk.com]"));
        Assert.assertTrue(po.getCsAVol() == 100);
        Assert.assertTrue(po.getCsVBr() == -1);
        Assert.assertTrue(po.getSuLoss() == 0);
        Assert.assertTrue(po.getCsVFps() == -1);
        Assert.assertTrue(po.getCsVCapFps() == -1);
        Assert.assertTrue(po.getCsABr() == -1);
        Assert.assertTrue(po.getCsSFps() == -1);
        Assert.assertTrue(po.getCsSCapFps() == -1);
    }
}