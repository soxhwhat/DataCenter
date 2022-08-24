package test.com.juphoon.rtc.datacenter.servicecore.factory;

import com.juphoon.rtc.datacenter.datacore.api.HandlerId;
import com.juphoon.rtc.datacenter.datacore.api.ProcessorId;
import com.juphoon.rtc.datacenter.datacore.exception.JrtcInvalidProcessorConfigurationException;
import com.juphoon.rtc.datacenter.servicecore.factory.HandlerFactory;
import com.juphoon.rtc.datacenter.servicecore.factory.ProcessorFactory;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import test.com.juphoon.rtc.datacenter.servicecore.ServiceCoreTestApplication;

import static com.juphoon.rtc.datacenter.datacore.JrtcDataCenterConstant.*;

/**
 * TestApplication Tester.
 *
 * @author <Authors name>
 * @since <pre>May 16, 2022</pre>
 * @version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServiceCoreTestApplication.class)
@ActiveProfiles("test-mysql")
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
    public void requiredEventHandlerTest() throws Exception {
        log.info("");
        for (int i = 0; i < HandlerId.values().length; i++) {
            log.info("check handler {}: {}", HandlerId.values()[i].getId(), HandlerId.values()[i].getName());

            // 忽略内置
            if (!HandlerId.values()[i].getScope().equals(RESOURCE_SCOPE_GLOBAL_EVENT)) {
                continue;
            }

            Assert.assertNotNull(HandlerId.values()[i].getId() + ":" + HandlerId.values()[i].getName() + " 未加载到",
                    handlerFactory.getEventHandler(HandlerId.values()[i].getId()));
        }

        //
//        Assert.isTrue(handlerFactory.getEventHandlers().size() == 10, "");

    }

    @Test
    public void requiredStateHandlerTest() throws Exception {
        log.info("");
        for (int i = 0; i < HandlerId.values().length; i++) {
            log.info("check handler {}: {}", HandlerId.values()[i].getId(), HandlerId.values()[i].getName());

            // 忽略内置
            if (!HandlerId.values()[i].getScope().equals(RESOURCE_SCOPE_GLOBAL_STATE)) {
                continue;
            }

            Assert.assertNotNull(
                    HandlerId.values()[i].getId() + ":" + HandlerId.values()[i].getName() + " 未加载到",
                    handlerFactory.getStateHandler(HandlerId.values()[i].getId()));
        }
    }

    @Test(expected = JrtcInvalidProcessorConfigurationException.class)
    public void requiredHandlerExceptionTest() throws Exception {
        handlerFactory.getEventHandler("invalidHandler");

        Assert.fail("not here");
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
                    Assert.assertNotNull(ProcessorId.values()[i].getId() + ":" + ProcessorId.values()[i].getName() + " 未加载到",
                            processorFactory.getEventProcessor(ProcessorId.values()[i].getId()));
                    break;
                case PROCESSOR_TYPE_STATE:
                    Assert.assertNotNull(ProcessorId.values()[i].getId() + ":" + ProcessorId.values()[i].getName() + " 未加载到",
                            processorFactory.getStateProcessor(ProcessorId.values()[i].getId()));
                    break;
                default:
                    Assert.fail("未知 Processor " + ProcessorId.values()[i].getId());
            }
        }
    }

    @Test(expected = JrtcInvalidProcessorConfigurationException.class)
    public void requiredProcessorExceptionTest() throws Exception {
        processorFactory.getEventProcessor("invalidProcessor");

        Assert.fail("not here");
    }
} 
