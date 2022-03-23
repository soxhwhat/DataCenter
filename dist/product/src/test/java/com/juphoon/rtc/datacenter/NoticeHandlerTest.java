package com.juphoon.rtc.datacenter;

import com.juphoon.rtc.datacenter.api.Event;
import com.juphoon.rtc.datacenter.api.EventContext;
import com.juphoon.rtc.datacenter.service.DataService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>在开始处详细描述该类的作用</p>
 * <p>描述请遵循 javadoc 规范</p>
 * <p>TODO</p>
 *
 * @author ke.wang@juphoon.com
 * @date 2022/3/4 16:49
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@SpringBootTest(classes = DataCenterApplication.class)
@Slf4j
@ActiveProfiles("mysql")
public class NoticeHandlerTest {

    @Autowired
    private DataService dataService;

    @Test
    public void testVerCodeHandler() throws InterruptedException {
        Event event = new Event();
        event.setType(25);
        event.setNumber(1);
        Map<String, Object> map = new HashMap();
        event.setParams(map);
        EventContext eventContext = new EventContext();
        eventContext.setEvent(event);
        dataService.commit(eventContext);
    }
}
