package com.juphoon.rtc.datacenter.entity.po.thea;

import com.juphoon.rtc.datacenter.api.Event;
import com.juphoon.rtc.datacenter.api.EventContext;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

@Slf4j
public class TheaRecvPOTest {
    EventContext ec;

    @Before
    public void initEc() {
        log.info("init ec");
        ec = TheaEventContextUtils.getEventContext();
    }

    @Test
    public void fromEvent() {
        List<TheaRecvPO> theaRecvPOS = TheaRecvPO.fromEvent(ec);


        Assert.assertNotNull(theaRecvPOS);
        Assert.assertTrue(theaRecvPOS.size() == 2);
        Assert.assertTrue(theaRecvPOS.get(0).getCallId().equals("103451680701174926"));
        Assert.assertTrue(theaRecvPOS.get(0).getCbAccountid().equals("[username:delivery_JMDS.Main0.Main01.Main_0@100074.cloud.justalk.com]"));
        Assert.assertTrue(theaRecvPOS.get(0).getCrRecvActorid() != null);

    }
}