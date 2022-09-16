package test.com.juphoon.rtc.datacenter.servicecore.handler.database.upload;

import com.juphoon.rtc.datacenter.datacore.api.Event;
import com.juphoon.rtc.datacenter.datacore.api.EventContext;
import com.juphoon.rtc.datacenter.datacore.api.EventType;
import com.juphoon.rtc.datacenter.servicecore.entity.po.upload.VideoKeyFrameEntity;
import com.juphoon.rtc.datacenter.servicecore.entity.po.upload.keyframe.KeyFrameData;
import com.juphoon.rtc.datacenter.servicecore.entity.po.upload.keyframe.TextKeyFrameData;
import com.juphoon.rtc.datacenter.servicecore.handle.database.upload.UploadAddVideoDatabaseHandler;
import com.juphoon.rtc.datacenter.servicecore.mapper.UploadRecordInfoMapper;
import com.juphoon.rtc.datacenter.servicecore.mapper.UploadVideoKeyFrameMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.juphoon.rtc.datacenter.datacore.JrtcDataCenterConstant.MONGO_TEMPLATE_EVENT;
import static com.juphoon.rtc.datacenter.datacore.api.EventType.*;
import static org.mockito.ArgumentMatchers.any;

/**
 * <p>upload音视频数据handler</p>
 *
 * @author ke.wang@juphoon.com
 * @date 2022/7/28
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test-mysql")
public class UploadAddVideoDatabaseHandlerTest {

    @InjectMocks
    private UploadAddVideoDatabaseHandler uploadAddVideoDatabaseHandler;

    @Mock
    private UploadVideoKeyFrameMapper uploadVideoKeyFrameMapper;

    @Mock
    private UploadRecordInfoMapper uploadRecordInfoMapper;

    @Test
    public void testCareEvents() {
        List<EventType> events = uploadAddVideoDatabaseHandler.careEvents();
        Assert.notEmpty(events, "careEvent为空");
        Assert.isTrue(events.contains(INSERT_SUCCESS_VIDEO_PO), INSERT_SUCCESS_VIDEO_PO.name());
    }

    private EventContext eventContext;

    @Before
    public void init() {
        Map<String, Object> params = new HashMap<>();
        params.put("fileName", "538507833391710208.mp4");
        params.put("domainId", 100645);
        params.put("recordTime", System.currentTimeMillis());
        params.put("recordBeginTimestamp", "1663223188569");
        params.put("recordEndTimestamp", "1663223207047");
        params.put("bucketName", "test");
        params.put("fileSize", 779028);
        List<HashMap<String, Object>> objects = new ArrayList<>();
        HashMap<String, Object> map = new HashMap<>();
        map.put("type", "text");
        map.put("data", "打点数据测试");
        map.put("timeOffset", 1111L);
        objects.add(map);
        params.put("videoKeyFrameList", objects);
        Event build = Event.builder().appId(4).domainId(100645)
                .type(INSERT_SUCCESS_VIDEO_PO.getType())
                .number(INSERT_SUCCESS_VIDEO_PO.getNumber())
                .params(params)
                .build();
        eventContext = new EventContext(build);
    }

    @Test
    public void testPre() {
        Mockito.when(uploadRecordInfoMapper.insert(any())).thenReturn(1);
        Mockito.when(uploadVideoKeyFrameMapper.insert(any())).thenReturn(1);
        boolean handle = uploadAddVideoDatabaseHandler.handle(eventContext);
        Assert.isTrue(handle, "access");
    }
}
