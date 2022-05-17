package com.juphoon.rtc.datacenter.test;

import com.juphoon.rtc.datacenter.api.ProcessorId;
import com.juphoon.rtc.datacenter.configuration.DataServiceConfiguration;
import com.juphoon.rtc.datacenter.property.DataCenterProperties;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.util.Assert;

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
    private DataCenterProperties propertiesWithEmptyEventLog = new DataCenterProperties();
    private DataCenterProperties propertiesWithEmptyRedoLog = new DataCenterProperties();
    private DataCenterProperties propertiesWithEmptyHandlers = new DataCenterProperties();

    @Before
    public void before() throws Exception {
        //
        propertiesWithEmptyProcessorName.getProcessors().add(new DataCenterProperties.Processor());

        //
        DataCenterProperties.Processor processorWithEmptyEventLog = new DataCenterProperties.Processor();
        processorWithEmptyEventLog.setName(ProcessorId.TEST.getId());
        propertiesWithEmptyEventLog.getProcessors().add(processorWithEmptyEventLog);

        //
        DataCenterProperties.Processor processorWithEmptyRedoLog = new DataCenterProperties.Processor();
        processorWithEmptyRedoLog.setName(ProcessorId.TEST.getId());
        processorWithEmptyRedoLog.setEventLog("sqlite");
        propertiesWithEmptyRedoLog.getProcessors().add(processorWithEmptyRedoLog);

        //
        DataCenterProperties.Processor processorWithEmptyEmptyHandlers = new DataCenterProperties.Processor();
        processorWithEmptyEmptyHandlers.setName(ProcessorId.TEST.getId());
        processorWithEmptyEmptyHandlers.setEventLog("sqlite");
        processorWithEmptyEmptyHandlers.setRedoLog("sqlite");
        propertiesWithEmptyHandlers.getProcessors().add(processorWithEmptyEmptyHandlers);
    }

    @After
    public void after() throws Exception {
    }

    @Test(expected = AssertionError.class)
    public void testConfigWithEmptyProcessors() throws Exception {
        DataServiceConfiguration test = Mockito.spy(DataServiceConfiguration.class);

        when(test.getProperties()).thenReturn(propertiesWithEmptyProcessors);

        test.config();

        Assert.isTrue(false, "not here");
    }

    @Test(expected = AssertionError.class)
    public void testConfigWithEmptyEventLog() throws Exception {
        DataServiceConfiguration test = Mockito.spy(DataServiceConfiguration.class);

        when(test.getProperties()).thenReturn(propertiesWithEmptyEventLog);

        test.config();

        Assert.isTrue(false, "not here");
    }

    @Test(expected = AssertionError.class)
    public void testConfigWithEmptyRedoLog() throws Exception {
        DataServiceConfiguration test = Mockito.spy(DataServiceConfiguration.class);

        when(test.getProperties()).thenReturn(propertiesWithEmptyRedoLog);

        test.config();

        Assert.isTrue(false, "not here");
    }

    @Test(expected = AssertionError.class)
    public void testConfigWithEmptyHandlers() throws Exception {
        DataServiceConfiguration test = Mockito.spy(DataServiceConfiguration.class);

        when(test.getProperties()).thenReturn(propertiesWithEmptyHandlers);

        test.config();

        Assert.isTrue(false, "not here");
    }
} 
