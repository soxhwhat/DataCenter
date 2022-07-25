package test.com.juphoon.rtc.datacenter.test;

import com.juphoon.rtc.datacenter.datacore.api.EventContext;
import com.juphoon.rtc.datacenter.datacore.service.EventService;
import com.juphoon.rtc.datacenter.datacore.utils.TestUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <p>EventServiceTest</p>
 *
 * @author ajian.zheng@juphoon.com
 * @date 7/22/22 5:19 PM
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("random")
public class EventServiceTest {
    @Autowired
    private EventService eventService;

//    @Test
//    public void testSingleThread() {
//    }

    @Test
    public void testMultiThread() {
        // 创建线程池
        ExecutorService threadPool = Executors.newFixedThreadPool(10);

        log.info("*** ready~~ ");
        log.info("*** GO!");

        long begin = System.currentTimeMillis();

        for (int i = 0; i < 10; i++) {
            // 执行任务
            threadPool.execute(() -> {
                System.out.println("任务被执行,线程:" + Thread.currentThread().getName());

                EventContext context = TestUtils.randomEventContext();
                eventService.commit(context);
            });
        }

        long end = System.currentTimeMillis();



        log.info("*** DONE !");

        log.info("*************");
        log.info("** cost:{} **", end - begin);
        log.info("*************");
    }

}
