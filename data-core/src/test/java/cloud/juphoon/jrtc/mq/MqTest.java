package cloud.juphoon.jrtc.mq;

import cloud.juphoon.jrtc.DataCenterApplication;
import cloud.juphoon.jrtc.api.Event;
import cloud.juphoon.jrtc.api.EventContext;
import cloud.juphoon.jrtc.service.DataService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @Author: Zhiwei.zhai
 * @Date: 2022/3/1 15:28
 * @Description:
 */
@SpringBootTest(classes = DataCenterApplication.class)
@Slf4j
public class MqTest {

    @Autowired
    private DataService dataService;

    @Test
    public void printDataService() {
        log.info(dataService.toString());
    }

    @SneakyThrows
    @Test
    public void commit() {
        EventContext eventContext = new EventContext();
        Event event = new Event();
        event.setNumber(0);
        event.setType(1);
        eventContext.setEvent(event);
        dataService.commit(eventContext);
        Thread.sleep(10000);
    }

}
