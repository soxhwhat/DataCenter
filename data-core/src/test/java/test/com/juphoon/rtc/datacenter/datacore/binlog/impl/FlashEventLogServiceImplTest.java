package test.com.juphoon.rtc.datacenter.datacore.binlog.impl;

import com.juphoon.rtc.datacenter.datacore.api.EventContext;
import com.juphoon.rtc.datacenter.datacore.binlog.ILogService;
import com.juphoon.rtc.datacenter.datacore.binlog.mapper.flash.SqliteFlashEventLogMapper;
import com.juphoon.rtc.datacenter.datacore.utils.TestUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import test.com.juphoon.rtc.datacenter.datacore.DataCoreTestApplication;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import static com.juphoon.rtc.datacenter.datacore.JrtcDataCenterConstant.*;

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
    private SqliteFlashEventLogMapper logMapper;

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
    public void testSave() {
        EventContext context = TestUtils.randomEventContext();

        log.info("context:{}", context.getId());

        eventLogService.save(context);
    }

    @Test
    public void testStart() throws Exception {
        eventLogService.stop();
        String fileName = System.getProperty("user.dir") + LOCAL_DB_FILE_BASE_PATH + LOCAL_DB_FILE_FLASH;
        File dir = new File(System.getProperty("user.dir") + LOCAL_DB_FILE_BASE_PATH);

        // 破坏表
        File file = new File(fileName);
        if (file.exists()) {
            file.delete();
        }
        file.createNewFile();

        FileWriter fileWriter = new FileWriter(file.getAbsoluteFile(), true);
        BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
        bufferedWriter.write("abcdefgabcdefgabcdefgabcdefgabcdefgabcdefgabcdefg");
        bufferedWriter.flush();
        bufferedWriter.close();

        try {
            // start
            eventLogService.start();

            String bakFileNamePrefix = LOCAL_DB_FILE_FLASH + ".bak.";

            String[] files = dir.list((d, name) -> name.startsWith(bakFileNamePrefix));

            Assert.assertNotNull(files);
            Assert.assertEquals(1, files.length);

            /// 清理
            for (String f : files) {
                File del = new File(System.getProperty("user.dir") + LOCAL_DB_FILE_BASE_PATH + f);
                del.delete();
            }

        } catch (Exception e) {
            Assert.fail("不应该在这里");
        } finally {
            file.delete();
        }
    }


} 
