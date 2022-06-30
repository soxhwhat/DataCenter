package test.com.juphoon.rtc.datacenter.servicecore.handler.mongo;

import com.juphoon.rtc.datacenter.datacore.api.EventType;
import com.juphoon.rtc.datacenter.servicecore.handle.mongo.MdEventMongoHandler;
import com.juphoon.rtc.datacenter.servicecore.handle.mongo.entity.MongoEventPO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.util.Assert;

import java.util.List;

import static com.juphoon.rtc.datacenter.datacore.api.EventType.CD_END_EVENT;
import static com.juphoon.rtc.datacenter.datacore.api.EventType.CD_LEAVE_ROOM_EVENT;


/**
 * <p>终端埋点事件处理handler</p>
 *
 * @author wenjun.yuan@juphoon.com
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 * @since 2022/7/12
 */
@Slf4j
@RunWith(PowerMockRunner.class)
@PrepareForTest({MongoEventPO.class})
public class MdEventMongoHandlerTest {

    @InjectMocks
    private MdEventMongoHandler mdEventMongoHandler;

    @Test
    public void testCareEvents() {
        List<EventType> events = mdEventMongoHandler.careEvents();
        Assert.notEmpty(events, "careEvent为空");
        Assert.isTrue(events.contains(CD_END_EVENT), CD_END_EVENT.name());
        Assert.isTrue(events.contains(CD_LEAVE_ROOM_EVENT), CD_LEAVE_ROOM_EVENT.name());
    }
}
