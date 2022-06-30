package test.com.juphoon.rtc.datacenter.servicecore.handler.database.monitor;

import com.juphoon.rtc.datacenter.servicecore.entity.po.monitor.MonitorAcdQueueCountPO;
import com.juphoon.rtc.datacenter.servicecore.handle.database.monitor.MonitorAcdQueueCountHandler;
import com.juphoon.rtc.datacenter.servicecore.mapper.MonitorAcdQueueCountMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.mockito.Mockito.times;

/**
 * MonitorAcdQueueCountHandler Tester.
 *
 * @author <Authors name>
 * @since <pre>Jun 23, 2022</pre>
 * @version 1.0
 */
@Slf4j
@RunWith(PowerMockRunner.class)
@PrepareForTest({MonitorAcdQueueCountPO.class})
public class MonitorAcdQueueCountHandlerTest {

    @Mock
    private MonitorAcdQueueCountMapper monitorAcdQueueCountMapper;

    @InjectMocks
    private MonitorAcdQueueCountHandler monitorAcdQueueCountHandler;

    /**
     *
     * Method: handle(StateContext context)
     *
     */
    @Test
    public void testHandleUpdate() throws Exception {
        PowerMockito.mockStatic(MonitorAcdQueueCountPO.class);

        PowerMockito.when(MonitorAcdQueueCountPO.fromState(null)).thenReturn(null);

        Mockito.when(monitorAcdQueueCountMapper.updateByQueueAndFrom(null)).thenReturn(1);
        Mockito.when(monitorAcdQueueCountMapper.insert(null)).thenReturn(1);

        monitorAcdQueueCountHandler.handle(null);

        Mockito.verify(monitorAcdQueueCountMapper, times(1)).updateByQueueAndFrom(null);
        Mockito.verify(monitorAcdQueueCountMapper, times(0)).insert(null);
    }

    @Test
    public void testHandleInsert() throws Exception {
        PowerMockito.mockStatic(MonitorAcdQueueCountPO.class);

        PowerMockito.when(MonitorAcdQueueCountPO.fromState(null)).thenReturn(null);

        Mockito.when(monitorAcdQueueCountMapper.updateByQueueAndFrom(null)).thenReturn(0);
        Mockito.when(monitorAcdQueueCountMapper.insert(null)).thenReturn(1);

        monitorAcdQueueCountHandler.handle(null);

        Mockito.verify(monitorAcdQueueCountMapper, times(1)).updateByQueueAndFrom(null);
        Mockito.verify(monitorAcdQueueCountMapper, times(1)).insert(null);
    }
} 
