package test.com.juphoon.rtc.datacenter.servicecore.handler.database.upload;

import com.juphoon.rtc.datacenter.datacore.api.EventType;
import com.juphoon.rtc.datacenter.servicecore.handle.database.upload.UploadAddImgDatabaseHandler;
import com.juphoon.rtc.datacenter.servicecore.handle.database.upload.UploadAddVideoDatabaseHandler;
import com.juphoon.rtc.datacenter.servicecore.handle.database.upload.UploadAddVideoKeyFrameDatabaseHandler;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import java.util.List;

import static com.juphoon.rtc.datacenter.datacore.api.EventType.INSERT_SUCCESS_IMAGE_PO;
import static com.juphoon.rtc.datacenter.datacore.api.EventType.INSERT_VIDEO_KEY_FRAME_PO;

/**
 * <p>upload打点数据handler</p>
 *
 * @author ke.wang@juphoon.com
 * @date 2022/7/28
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test-mysql")
public class UploadAddVideoKeyFrameDatabaseHandlerTest {

    @InjectMocks
    private UploadAddVideoKeyFrameDatabaseHandler uploadAddVideoDatabaseHandler;

    @Test
    public void testCareEvents() {
        List<EventType> events = uploadAddVideoDatabaseHandler.careEvents();
        Assert.notEmpty(events, "careEvent为空");
        Assert.isTrue(events.contains(INSERT_VIDEO_KEY_FRAME_PO), INSERT_VIDEO_KEY_FRAME_PO.name());
    }
}
