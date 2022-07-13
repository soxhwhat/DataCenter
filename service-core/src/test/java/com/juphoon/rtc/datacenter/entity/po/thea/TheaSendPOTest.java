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
        Assert.assertEquals("[username:delivery_JMDS.Main0.Main01.Main_0@100074.cloud.justalk.com]", po.getCbAccountid());
        Assert.assertEquals(100, (int) po.getCsAVol());
        Assert.assertEquals(-1, (int) po.getCsVBr());
        Assert.assertEquals(0, (int) po.getSuLoss());
        Assert.assertEquals(-1, (int) po.getCsVFps());
        Assert.assertEquals(-1, (int) po.getCsVCapFps());
        Assert.assertEquals(-1, (int) po.getCsABr());
        Assert.assertEquals(-1, (int) po.getCsSFps());
        Assert.assertEquals(-1, (int) po.getCsSCapFps());
    }
}