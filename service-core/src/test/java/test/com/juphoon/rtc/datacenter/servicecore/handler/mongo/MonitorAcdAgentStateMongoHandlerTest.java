package test.com.juphoon.rtc.datacenter.servicecore.handler.mongo;

import com.juphoon.rtc.datacenter.datacore.api.StateContext;
import com.juphoon.rtc.datacenter.servicecore.entity.po.monitor.MonitorAcdAgentStatePO;
import com.juphoon.rtc.datacenter.servicecore.handle.mongo.MonitorAcdAgentStateMongoHandler;
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

/**
 * <p>视频客服坐席状态统计handler测试</p>
 *
 * @author Jiahui.Huang
 * @date 2022-09-21
 */
@Slf4j
@RunWith(PowerMockRunner.class)
@PrepareForTest({MonitorAcdAgentStatePO.class})
public class MonitorAcdAgentStateMongoHandlerTest {
    @Mock
    @Qualifier(MONGO_TEMPLATE_EVENT)
    private MongoTemplate mongoTemplate;

    @InjectMocks
    private MonitorAcdAgentStateMongoHandler handler;

    @Test
    public void handlerSuccess() throws Exception {
        PowerMockito.mockStatic(MonitorAcdAgentStatePO.class);
        MonitorAcdAgentStatePO po = new MonitorAcdAgentStatePO();
        po.setDomainId(100645);

        StateContext context = new StateContext(null);
        context.setCreatedTimestamp(System.currentTimeMillis());
        String collectionName = handler.getCollectionName(handler, context);
        PowerMockito.when(MonitorAcdAgentStatePO.fromState(context)).thenReturn(po);

        Mockito.when(mongoTemplate.insert(po)).thenReturn(null);

        handler.handle(context);

        Mockito.verify(mongoTemplate, times(1)).insert(po, collectionName);
    }
}