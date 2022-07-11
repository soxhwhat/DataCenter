package com.juphoon.rtc.datacenter.binlog.impl;

import com.juphoon.rtc.datacenter.TestApplication;
import com.juphoon.rtc.datacenter.api.EventContext;
import com.juphoon.rtc.datacenter.binlog.ILogService;
import com.juphoon.rtc.datacenter.binlog.entity.EventBinLogPO;
import com.juphoon.rtc.datacenter.binlog.mapper.flash.FlashEventLogMapper;
import com.juphoon.rtc.datacenter.log.mapper.FlashEventLogMapperTest;
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

import static com.juphoon.rtc.datacenter.JrtcDataCenterConstant.EVENT_BIN_LOG_IMPL_FLASH;

/**
 * FlashEventLogServiceImpl Tester.
 *
 * @author <Authors name>
 * @since <pre>Jun 7, 2022</pre>
 * @version 1.0
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class)
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
