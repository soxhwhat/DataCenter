package com.juphoon.rtc.datacenter.test;

import com.juphoon.rtc.datacenter.api.HandlerId;
import com.juphoon.rtc.datacenter.api.ProcessorId;
import com.juphoon.rtc.datacenter.exception.JrtcInvalidProcessorConfigurationException;
import com.juphoon.rtc.datacenter.factory.HandlerFactory;
import com.juphoon.rtc.datacenter.factory.ProcessorFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import static com.juphoon.rtc.datacenter.JrtcDataCenterConstant.*;

/**
 * TestApplication Tester.
 *
 * @author <Authors name>
 * @since <pre>May 16, 2022</pre>
 * @version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
@Slf4j
public class DataCenterFactoryTest {

    @Autowired
    private HandlerFactory handlerFactory;

    @Autowired
    private ProcessorFactory processorFactory;

    @Before
    public void before() throws Exception {
    }

    @After
    public void after() throws Exception {
    }


    /**
     *
     * Method: main(String[] args)
     *
     */
    @Test
    public void requiredHandlerTest() throws Exception {
        log.info("");
        for (int i = 0; i < HandlerId.values().length; i++) {
            log.info("check handler {}: {}", HandlerId.values()[i].getId(), HandlerId.values()[i].getName());

            // 忽略内置
            if (!HandlerId.values()[i].getScope().equals(RESOURCE_SCOPE_GLOBAL)) {
                continue;
            }

            Assert.notNull(handlerFactory.getEventHandler(HandlerId.values()[i].getId()),
                    HandlerId.values()[i].getId() + ":" + HandlerId.values()[i].getName() + " 未加载到");
        }

        //
//        Assert.isTrue(handlerFactory.getEventHandlers().size() == 10, "");

    }

    @Test(expected = JrtcInvalidProcessorConfigurationException.class)
    public void requiredHandlerExceptionTest() throws Exception {
        handlerFactory.getEventHandler("invalidHandler");

        Assert.isTrue(false, "not here");
    }

    @Test
    public void requiredProcessorTest() throws Exception {
        for (int i = 0; i < ProcessorId.values().length; i++) {
            log.info("check processor {}: {}", ProcessorId.values()[i].getId(), ProcessorId.values()[i].getName());

            switch (ProcessorId.values()[i].getType()) {
                case PROCESSOR_TYPE_TEST:
                    log.info("ignore {}", ProcessorId.values()[i].getId());
                    break;
                case PROCESSOR_TYPE_EVENT:
                    Assert.notNull(processorFactory.getEventProcessor(ProcessorId.values()[i].getId()),
                            ProcessorId.values()[i].getId() + ":" + ProcessorId.values()[i].getName() + " 未加载到");
                    break;
                case PROCESSOR_TYPE_LOG:
                    Assert.notNull(processorFactory.getLogProcessor(ProcessorId.values()[i].getId()),
                            ProcessorId.values()[i].getId() + ":" + ProcessorId.values()[i].getName() + " 未加载到");
                    break;
                case PROCESSOR_TYPE_STATE:
                    Assert.notNull(processorFactory.getStateProcessor(ProcessorId.values()[i].getId()),
                            ProcessorId.values()[i].getId() + ":" + ProcessorId.values()[i].getName() + " 未加载到");
                    break;
                default:
                    Assert.isTrue(false, "未知 Processor " + ProcessorId.values()[i].getId());
            }


        }
    }

    @Test(expected = JrtcInvalidProcessorConfigurationException.class)
    public void requiredProcessorExceptionTest() throws Exception {
        processorFactory.getEventProcessor("invalidProcessor");

        Assert.isTrue(false, "not here");
    }
} 
