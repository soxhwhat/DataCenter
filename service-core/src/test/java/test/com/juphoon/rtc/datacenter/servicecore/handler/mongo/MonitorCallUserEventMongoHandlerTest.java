package test.com.juphoon.rtc.datacenter.servicecore.handler.mongo;


import com.juphoon.rtc.datacenter.servicecore.entity.po.monitor.MonitorCallUserPO;
import com.juphoon.rtc.datacenter.servicecore.handle.mongo.MonitorCallUserEventMongoHandler;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
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
import static org.mockito.Mockito.times;

@Slf4j
@RunWith(PowerMockRunner.class)
@PrepareForTest({MonitorCallUserPO.class})
public class MonitorCallUserEventMongoHandlerTest {
    @Mock
    @Qualifier(MONGO_TEMPLATE_EVENT)
    private MongoTemplate mongoTemplate;

    @InjectMocks
    private MonitorCallUserEventMongoHandler monitorCallUserEventMongoHandler;


    @SneakyThrows
    @Test
    public void handlerId() {
        PowerMockito.mockStatic(MonitorCallUserPO.class);

        MonitorCallUserPO po = new MonitorCallUserPO();
        po.setAppId(1);
        PowerMockito.when(MonitorCallUserPO.fromEvent(null)).thenReturn(po);

        Mockito.when(mongoTemplate.insert(po)).thenReturn(null);

        monitorCallUserEventMongoHandler.handle(null);

        Mockito.verify(mongoTemplate, times(1)).insert(po);

    }
}