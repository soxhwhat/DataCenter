package test.com.juphoon.rtc.datacenter.servicecore.entity.po.thea;

import com.juphoon.rtc.datacenter.datacore.api.EventContext;
import com.juphoon.rtc.datacenter.servicecore.entity.po.thea.TheaRecvPO;
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
        Assert.assertEquals(2, theaRecvPOS.size());
        Assert.assertEquals("103451680701174926", theaRecvPOS.get(0).getCallId());
        Assert.assertEquals("[username:delivery_JMDS.Main0.Main01.Main_0@100074.cloud.justalk.com]", theaRecvPOS.get(0).getCbAccountid());
        Assert.assertNotNull(theaRecvPOS.get(0).getCrRecvActorid());

    }
}