package test.com.juphoon.rtc.datacenter.servicecore.handler.database.upload;

import com.juphoon.rtc.datacenter.datacore.api.EventType;
import com.juphoon.rtc.datacenter.servicecore.entity.ServiceLevelTypeEnum;
import com.juphoon.rtc.datacenter.servicecore.handle.database.acdstat.AcdExtServiceLevelDailyHandler;
import com.juphoon.rtc.datacenter.servicecore.handle.database.upload.UploadAddVideoDatabaseHandler;
import com.juphoon.rtc.datacenter.servicecore.handle.mongo.MdEventMongoHandler;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import java.util.List;

import static com.juphoon.rtc.datacenter.datacore.api.EventType.*;

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

    @Test
    public void testCareEvents() {
        List<EventType> events = uploadAddVideoDatabaseHandler.careEvents();
        Assert.notEmpty(events, "careEvent为空");
        Assert.isTrue(events.contains(INSERT_SUCCESS_VIDEO_PO), INSERT_SUCCESS_VIDEO_PO.name());
    }
}