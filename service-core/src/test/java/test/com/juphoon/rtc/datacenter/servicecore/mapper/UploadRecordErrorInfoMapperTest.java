package test.com.juphoon.rtc.datacenter.servicecore.mapper;

import com.juphoon.rtc.datacenter.servicecore.entity.po.upload.UploadRecordErrorInfoPO;
import com.juphoon.rtc.datacenter.servicecore.mapper.UploadRecordErrorInfoMapper;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import test.com.juphoon.rtc.datacenter.servicecore.ServiceCoreTestApplication;

/**
 * <p>在开始处详细描述该类的作用</p>
 *
 * @author ke.wang@juphoon.com
 * @date 2022/7/29 14:39
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServiceCoreTestApplication.class)
@ActiveProfiles("test-mysql")
public class UploadRecordErrorInfoMapperTest {

    @Autowired
    UploadRecordErrorInfoMapper mapper;

    @Test
    public void testInsert() {
        UploadRecordErrorInfoPO uploadRecordInfoPO = new UploadRecordErrorInfoPO();
        uploadRecordInfoPO.setAppId(4);
        uploadRecordInfoPO.setDomainId(100645);
        uploadRecordInfoPO.setBucketName("test-bucket");
        uploadRecordInfoPO.setCallId("12345678");
        uploadRecordInfoPO.setCallParams("");
        uploadRecordInfoPO.setBusiSerialNo("11");
        uploadRecordInfoPO.setFileHash("hash");
        uploadRecordInfoPO.setFileSize(1000L);
        uploadRecordInfoPO.setUploadHost("http://127.0.0.1");
        uploadRecordInfoPO.setRecordTime(312312L);
        uploadRecordInfoPO.setMoreInfo("");
        uploadRecordInfoPO.setRecordBeginTimestamp(1000L);
        uploadRecordInfoPO.setRecordEndTimestamp(2000L);
        uploadRecordInfoPO.setRetryCount(1);
        uploadRecordInfoPO.setRecordResourceId("1");
        int insert = mapper.insert(uploadRecordInfoPO);
        Assert.assertEquals(1, insert);
    }

}
