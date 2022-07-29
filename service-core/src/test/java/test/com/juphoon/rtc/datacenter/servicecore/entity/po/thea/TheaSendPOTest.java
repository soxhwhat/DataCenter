package test.com.juphoon.rtc.datacenter.servicecore.entity.po.thea;

import com.juphoon.rtc.datacenter.datacore.api.EventContext;
import com.juphoon.rtc.datacenter.servicecore.entity.po.thea.TheaSendPO;
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
        Assert.assertEquals("delivery_JMDS.Main0.Main01.Main_0", po.getCbAccountid());
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