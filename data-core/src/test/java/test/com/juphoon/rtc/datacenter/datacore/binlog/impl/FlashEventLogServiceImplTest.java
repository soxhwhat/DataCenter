package test.com.juphoon.rtc.datacenter.datacore.binlog.impl;

import com.juphoon.rtc.datacenter.datacore.api.EventContext;
import com.juphoon.rtc.datacenter.datacore.binlog.ILogService;
import com.juphoon.rtc.datacenter.datacore.binlog.mapper.flash.FlashEventLogMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import test.com.juphoon.rtc.datacenter.datacore.DataCoreTestApplication;
import test.com.juphoon.rtc.datacenter.datacore.binlog.mapper.FlashEventLogMapperTest;

import static com.juphoon.rtc.datacenter.datacore.JrtcDataCenterConstant.EVENT_BIN_LOG_IMPL_FLASH;

/**
 * FlashEventLogServiceImpl Tester.
 *
 * @author <Authors name>
 * @since <pre>Jun 7, 2022</pre>
 * @version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DataCoreTestApplication.class)
@ActiveProfiles("test")
@Slf4j
public class FlashEventLogServiceImplTest {
    @Autowired
    @Qualifier(EVENT_BIN_LOG_IMPL_FLASH)
    private ILogService<EventContext> eventLogService;

    @Autowired
    private FlashEventLogMapper logMapper;

    @Before
    public void before() throws Exception {
        logMapper.dropTable();
        logMapper.createTable();
    }

    @After
    public void after() throws Exception {
    }

    /**
     *
     * Method: getEventLogMapper()
     *
     */
    @Test
    public void testSave() throws Exception {
        EventContext context = FlashEventLogMapperTest.randomEventContext();

        log.info("context:{}", context.getId());

        eventLogService.save(context);
    }


} 
