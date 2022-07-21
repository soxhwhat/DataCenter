package test.com.juphoon.rtc.datacenter.servicecore.entity.po.thea;


import com.juphoon.rtc.datacenter.datacore.api.EventContext;
import com.juphoon.rtc.datacenter.servicecore.entity.po.thea.TheaJoinMonitorPO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

@Slf4j
public class TheaJoinMonitorPOTest {
    EventContext ec;

    @Before
    public void initEc() {
        log.info("init ec");
        ec = TheaEventContextUtils.getEventContext();
    }


    /**
     * 将EventContext转换为TheaJoinMonitorPO
     *  数据源为天赛全量数据（type=900, number=0)
     *  转换结果为：
     *  {
     *       "_id": "62d678d1e23bd80a5e0b7199",
     *       "timestamp": 1658222801572,
     *       "callId": "103451680701174926",
     *       "joinSuccessCount": 1,
     *       "joinRoomCount": 1,
     *       "domainId": 100646,
     *       "appId": 1,
     * }
     */
    @Test
    public void fromEventOk() {
        TheaJoinMonitorPO po = TheaJoinMonitorPO.fromEvent(ec);

        Assert.assertEquals("103451680701174926", po.getCallId());
        Assert.assertEquals(1, (int) po.getJoinSuccessCount());
        Assert.assertEquals(1, (int)po.getJoinRoomCount());
        Assert.assertEquals(100646, (int)po.getDomainId());
        Assert.assertEquals(1, (int)po.getAppId());

    }
}