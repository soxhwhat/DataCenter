package test.com.juphoon.rtc.datacenter.servicecore.handler.mongo;

import com.juphoon.rtc.datacenter.servicecore.api.WindowLevelEnum;
import com.juphoon.rtc.datacenter.servicecore.entity.po.monitor.MonitorConcurrentPO;
import com.juphoon.rtc.datacenter.servicecore.handle.mongo.MonitorConcurrentStateMongoHandler;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;

import static com.juphoon.rtc.datacenter.datacore.JrtcDataCenterConstant.MONGO_TEMPLATE_EVENT;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

@Slf4j
@RunWith(PowerMockRunner.class)
@PrepareForTest({MonitorConcurrentPO.class})
public class MonitorConcurrentStateMongoHandlerTest {
    @Mock
    @Qualifier(MONGO_TEMPLATE_EVENT)
    private MongoTemplate mongoTemplate;

    @InjectMocks
    private MonitorConcurrentStateMongoHandler monitorConcurrentStateMongoHandler;

    /**
     * 转换
     * 态数据上报:FlowStatusJson(uniqueId=JSMS@JSMS.Main0.Main01.Main, type=22, status=0, params={
     *    "appId" : 4,
     *    "concurrentNumber" : 1,
     *    "concurrentPerson" : 2,
     *    "domainId" : 100645,
     *    "from" : "JSMS@JSMS.Main0.Main01.Main",
     *    "resourceType" : 0,
     *    "updateTimeStamp" : "1655773334312"
     * }
     */
    @SneakyThrows
    @Test
    public void handle() {
        WindowLevelEnum size = monitorConcurrentStateMongoHandler.getSize();
        Assert.assertEquals(WindowLevelEnum.WIN_MIN, size);

        PowerMockito.mockStatic(MonitorConcurrentPO.class);
        MonitorConcurrentPO po = new MonitorConcurrentPO();
        po.setTimestamp(1655773334312L);
        po.setRoom(1);
        po.setActor(2);

        PowerMockito.when(MonitorConcurrentPO.fromState(null)).thenReturn(po);

        Mockito.when(mongoTemplate.upsert(any(), any(), (String) any())).thenReturn(null);
        monitorConcurrentStateMongoHandler.handle(null);

        Mockito.verify(mongoTemplate, times(1)).upsert(any(), any(), (String) any());

    }
}