package test.com.juphoon.rtc.datacenter.test;

import com.juphoon.iron.cube.starter.CubeIdGenerator;
import com.juphoon.rtc.datacenter.datacore.binlog.entity.EventBinLogPO;
import com.juphoon.rtc.datacenter.datacore.binlog.mapper.flash.FlashEventLogMapper;
import com.juphoon.rtc.datacenter.test.TestApplication;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * <p>在开始处详细描述该类的作用</p>
 * <p>描述请遵循 javadoc 规范</p>
 * <p>TODO</p>
 *
 * @author haijie.liang@juphoon.com
 * @date 2022/8/23 13:39
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplication.class)
@ActiveProfiles("random")
@Ignore
public class SqliteLoadTest {
    @Autowired
    private FlashEventLogMapper flashEventLogMapper;

    @Before
    @SneakyThrows
    public void init() {
        flashEventLogMapper.dropTable();
        flashEventLogMapper.createTable();
    }

    @After
    public void after() {
        flashEventLogMapper.dropTable();
    }

    private static final int MAX = 100000;

    @Test
    public void sqliteSaveLoadTest() throws InterruptedException {

        EventBinLogPO po = new EventBinLogPO();

        po.setDomainId(1);
        po.setAppId(2);
        po.setFrom("3");
        po.setNumber(4);
        po.setParams("5");
        po.setProcessorId("6");
        po.setLastUpdateTimestamp(7L);
        po.setReceivedTimestamp(8L);
        po.setRedoHandler("9");
        po.setRequestId("10");
        po.setRetryCount(11);
        po.setTimestamp(12L);
        po.setType(13);

        long begin = System.currentTimeMillis();

        for (int i = 0; i < MAX; i++) {
            po.setUuid(CubeIdGenerator.newStringId());
            po.setId(CubeIdGenerator.newId());

            flashEventLogMapper.save(po);
        }

        long end = System.currentTimeMillis();

        log.info("round:{}, cost:{}", MAX, end - begin);
        log.info("tps:{}", (float) MAX / (end - begin) * 1000);
    }


}
