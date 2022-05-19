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

import static com.juphoon.rtc.datacenter.constant.JrtcDataCenterConstant.RESOURCE_SCOPE_GLOBAL;

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

            Assert.notNull(handlerFactory.getHandler(HandlerId.values()[i].getId()),
                    HandlerId.values()[i].getId() + ":" + HandlerId.values()[i].getName() + " 未加载到");
        }
    }

    @Test(expected = JrtcInvalidProcessorConfigurationException.class)
    public void requiredHandlerExceptionTest() throws Exception {
        handlerFactory.getHandler("invalidHandler");

        Assert.isTrue(false, "not here");
    }

    @Test
    public void requiredProcessorTest() throws Exception {
        for (int i = 0; i < ProcessorId.values().length; i++) {
            log.info("check processor {}: {}", ProcessorId.values()[i].getId(), ProcessorId.values()[i].getName());

            Assert.notNull(processorFactory.getProcessor(ProcessorId.values()[i].getId()),
                    ProcessorId.values()[i].getId() + ":" + ProcessorId.values()[i].getName() + " 未加载到");
        }
    }

    @Test(expected = JrtcInvalidProcessorConfigurationException.class)
    public void requiredProcessorExceptionTest() throws Exception {
        processorFactory.getProcessor("invalidProcessor");

        Assert.isTrue(false, "not here");
    }
} 