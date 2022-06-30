package test.com.juphoon.rtc.datacenter.servicecore.factory;

import com.juphoon.rtc.datacenter.datacore.api.ProcessorId;
import com.juphoon.rtc.datacenter.servicecore.configuration.EventServiceConfiguration;
import com.juphoon.rtc.datacenter.servicecore.property.DataCenterProperties;
import com.juphoon.rtc.datacenter.datacore.service.EventService;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.when;

/**
 * DataServiceConfiguration Tester.
 *
 * @author <Authors name>
 * @since <pre>May 17, 2022</pre>
 * @version 1.0
 */
@RunWith(MockitoJUnitRunner.class)
public class DataServiceConfigurationTest {

    private DataCenterProperties propertiesWithEmptyProcessors = new DataCenterProperties();
    private DataCenterProperties propertiesWithEmptyProcessorName = new DataCenterProperties();
    //    private DataCenterProperties propertiesWithEmptyEventLog = new DataCenterProperties();
//    private DataCenterProperties propertiesWithEmptyRedoLog = new DataCenterProperties();
    private DataCenterProperties propertiesWithEmptyHandlers = new DataCenterProperties();

    @Before
    public void before() throws Exception {
        //
        propertiesWithEmptyProcessorName.getEventProcessors().add(new DataCenterProperties.Processor());

        //
//        DataCenterProperties.Processor processorWithEmptyEventLog = new DataCenterProperties.Processor();
//        processorWithEmptyEventLog.setName(ProcessorId.TEST_STATE.getId());
//        propertiesWithEmptyEventLog.getProcessors().add(processorWithEmptyEventLog);
//
//        //
//        DataCenterProperties.Processor processorWithEmptyRedoLog = new DataCenterProperties.Processor();
//        processorWithEmptyRedoLog.setName(ProcessorId.TEST_STATE.getId());
//        processorWithEmptyRedoLog.setEventLog("sqlite");
//        propertiesWithEmptyRedoLog.getProcessors().add(processorWithEmptyRedoLog);

        //
        DataCenterProperties.Processor processorWithEmptyEmptyHandlers = new DataCenterProperties.Processor();
        processorWithEmptyEmptyHandlers.setName(ProcessorId.TEST_STATE);
//        processorWithEmptyEmptyHandlers.setEventLog("sqlite");
//        processorWithEmptyEmptyHandlers.setRedoLog("sqlite");
        propertiesWithEmptyHandlers.getEventProcessors().add(processorWithEmptyEmptyHandlers);
    }

    @After
    public void after() throws Exception {
    }

    @Test
    public void testConfigWithEmptyProcessors() throws Exception {
        EventServiceConfiguration test = Mockito.spy(EventServiceConfiguration.class);

        when(test.getProperties()).thenReturn(propertiesWithEmptyProcessors);

        EventService eventService = test.eventService();

        Assert.assertTrue(eventService.getProcessors().isEmpty());
    }

//    @Test(expected = AssertionError.class)
//    public void testConfigWithEmptyEventLog() throws Exception {
//        DataServiceConfiguration test = Mockito.spy(DataServiceConfiguration.class);
//
//        when(test.getProperties()).thenReturn(propertiesWithEmptyEventLog);
//
//        test.config();
//
//        Assert.isTrue(false, "not here");
//    }
//
//    @Test(expected = AssertionError.class)
//    public void testConfigWithEmptyRedoLog() throws Exception {
//        DataServiceConfiguration test = Mockito.spy(DataServiceConfiguration.class);
//
//        when(test.getProperties()).thenReturn(propertiesWithEmptyRedoLog);
//
//        test.config();
//
//        Assert.isTrue(false, "not here");
//    }

    @Test(expected = AssertionError.class)
    public void testConfigWithEmptyHandlers() throws Exception {
        EventServiceConfiguration test = Mockito.spy(EventServiceConfiguration.class);

        when(test.getProperties()).thenReturn(propertiesWithEmptyHandlers);

        test.eventService();

        Assert.fail("我应该在车底不应该在车里");
    }
} 
