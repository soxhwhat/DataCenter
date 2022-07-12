package com.juphoon.rtc.datacenter.handle.mongo;

import com.juphoon.rtc.datacenter.api.EventType;
import com.juphoon.rtc.datacenter.api.HandlerId;
import com.juphoon.rtc.datacenter.api.MongoCollectionEnum;
import com.juphoon.rtc.datacenter.entity.ServiceLevelTypeEnum;
import com.juphoon.rtc.datacenter.entity.po.monitor.MonitorAcdQueueCountPO;
import com.juphoon.rtc.datacenter.handle.mongo.entity.MongoEventPO;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.Arrays;
import java.util.List;

import static com.juphoon.rtc.datacenter.JrtcDataCenterConstant.MONGO_TEMPLATE_MD;
import static com.juphoon.rtc.datacenter.api.EventType.*;
import static com.juphoon.rtc.datacenter.api.MongoCollectionEnum.COLLECTION_MD;

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
