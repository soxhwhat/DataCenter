package test.com.juphoon.rtc.datacenter.servicecore.entity.po.upload;

import com.juphoon.iron.component.utils.IronJsonUtils;
import com.juphoon.rtc.datacenter.servicecore.entity.po.upload.UploadImageInfoPO;
import com.juphoon.rtc.datacenter.servicecore.entity.po.upload.UploadRecordErrorInfoPO;
import com.juphoon.rtc.datacenter.servicecore.entity.po.upload.UploadRecordInfoPO;
import com.juphoon.rtc.datacenter.servicecore.entity.po.upload.VideoKeyFrameEntity;
import com.juphoon.rtc.datacenter.servicecore.entity.po.upload.keyframe.LinkKeyFrameData;
import com.juphoon.rtc.datacenter.servicecore.entity.po.upload.keyframe.TextKeyFrameData;
import com.juphoon.rtc.datacenter.servicecore.entity.po.upload.keyframe.VideoKeyFrameBO;
import org.junit.Assert;
import org.junit.Test;

/**
 * <p>在开始处详细描述该类的作用</p>
 * <p>描述请遵循 javadoc 规范</p>
 * <p>TODO</p>
 *
 * @author ke.wang@juphoon.com
 * @date 2022/7/29 10:51
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class UploadPOTest {


    @Test
    public void testUploadRecordInfoPO() {
        UploadRecordInfoPO uploadRecordInfoPO = new UploadRecordInfoPO();
        uploadRecordInfoPO.setAppId(4);
        uploadRecordInfoPO.setDomainId(100645);
        uploadRecordInfoPO.setBucketName("test-bucket");
        uploadRecordInfoPO.setCallId("12345678");
        uploadRecordInfoPO.setCallUploadParams("");
        uploadRecordInfoPO.setCallParams("");
        uploadRecordInfoPO.setBusiSerialNo("11");
        uploadRecordInfoPO.setFileHash("hash");
        uploadRecordInfoPO.setFileSize(1000L);
        uploadRecordInfoPO.setUrl("http://xxx");
        uploadRecordInfoPO.setUploadHost("http://127.0.0.1");
        uploadRecordInfoPO.setRecordTime(312312L);
        uploadRecordInfoPO.setMoreInfo("");
        uploadRecordInfoPO.setFilePath("http://2312312.mp4");
        uploadRecordInfoPO.setRecordBeginTimestamp(1000L);
        uploadRecordInfoPO.setRecordEndTimestamp(2000L);
        uploadRecordInfoPO.setRecordResourceId("1");
        String origin = IronJsonUtils.objectToJson(uploadRecordInfoPO);

        UploadRecordInfoPO uploadRecordInfoPO1 = IronJsonUtils.jsonToPojo(origin, UploadRecordInfoPO.class);
        Assert.assertNotNull(uploadRecordInfoPO1);
        Assert.assertEquals(uploadRecordInfoPO.getAppId(), uploadRecordInfoPO1.getAppId());
        Assert.assertEquals(uploadRecordInfoPO.getBucketName(), uploadRecordInfoPO1.getBucketName());
        Assert.assertEquals(uploadRecordInfoPO.getCallId(), uploadRecordInfoPO1.getCallId());
        Assert.assertEquals(uploadRecordInfoPO.getBusiSerialNo(), uploadRecordInfoPO1.getBusiSerialNo());
        Assert.assertEquals(uploadRecordInfoPO.getMoreInfo(), uploadRecordInfoPO1.getMoreInfo());
        Assert.assertEquals(uploadRecordInfoPO.getRecordBeginTimestamp(), uploadRecordInfoPO1.getRecordBeginTimestamp());
        Assert.assertEquals(uploadRecordInfoPO.getRecordEndTimestamp(), uploadRecordInfoPO1.getRecordEndTimestamp());
    }

    @Test
    public void testUploadRecordErrorInfoPO() {
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
        String origin = IronJsonUtils.objectToJson(uploadRecordInfoPO);

        UploadRecordErrorInfoPO uploadRecordInfoPO1 = IronJsonUtils.jsonToPojo(origin, UploadRecordErrorInfoPO.class);
        Assert.assertNotNull(uploadRecordInfoPO1);
        Assert.assertEquals(uploadRecordInfoPO.getAppId(), uploadRecordInfoPO1.getAppId());
        Assert.assertEquals(uploadRecordInfoPO.getBucketName(), uploadRecordInfoPO1.getBucketName());
        Assert.assertEquals(uploadRecordInfoPO.getCallId(), uploadRecordInfoPO1.getCallId());
        Assert.assertEquals(uploadRecordInfoPO.getBusiSerialNo(), uploadRecordInfoPO1.getBusiSerialNo());
        Assert.assertEquals(uploadRecordInfoPO.getMoreInfo(), uploadRecordInfoPO1.getMoreInfo());
        Assert.assertEquals(uploadRecordInfoPO.getRecordBeginTimestamp(), uploadRecordInfoPO1.getRecordBeginTimestamp());
        Assert.assertEquals(uploadRecordInfoPO.getRecordEndTimestamp(), uploadRecordInfoPO1.getRecordEndTimestamp());
        Assert.assertEquals(uploadRecordInfoPO.getFileSize(), uploadRecordInfoPO1.getFileSize());
        Assert.assertEquals(uploadRecordInfoPO.getRetryCount(), uploadRecordInfoPO1.getRetryCount());
        Assert.assertEquals(uploadRecordInfoPO.getRecordResourceId(), uploadRecordInfoPO1.getRecordResourceId());
    }

    @Test
    public void testUploadImageInfoPO() {
        UploadImageInfoPO uploadRecordInfoPO = new UploadImageInfoPO();
        uploadRecordInfoPO.setAppId(4);
        uploadRecordInfoPO.setDomainId(100645);
        uploadRecordInfoPO.setBucketName("test-bucket");
        uploadRecordInfoPO.setCallId("12345678");
        uploadRecordInfoPO.setCallParams("");
        uploadRecordInfoPO.setBusiSerialNo("11");
        uploadRecordInfoPO.setFileHash("hash");
        uploadRecordInfoPO.setFileSize(1000L);
        uploadRecordInfoPO.setBusiSerialNo("11234253452");
        uploadRecordInfoPO.setUploadHost("http://127.0.0.1");
        uploadRecordInfoPO.setTakeTime(23123123L);
        uploadRecordInfoPO.setId("11231");
        uploadRecordInfoPO.setUrl("http://12312/");

        String origin = IronJsonUtils.objectToJson(uploadRecordInfoPO);

        UploadImageInfoPO uploadRecordInfoPO1 = IronJsonUtils.jsonToPojo(origin, UploadImageInfoPO.class);
        Assert.assertNotNull(uploadRecordInfoPO1);
        Assert.assertEquals(uploadRecordInfoPO.getAppId(), uploadRecordInfoPO1.getAppId());
        Assert.assertEquals(uploadRecordInfoPO.getBucketName(), uploadRecordInfoPO1.getBucketName());
        Assert.assertEquals(uploadRecordInfoPO.getCallId(), uploadRecordInfoPO1.getCallId());
        Assert.assertEquals(uploadRecordInfoPO.getBusiSerialNo(), uploadRecordInfoPO1.getBusiSerialNo());
        Assert.assertEquals(uploadRecordInfoPO.getDomainId(), uploadRecordInfoPO1.getDomainId());
        Assert.assertEquals(uploadRecordInfoPO.getUploadHost(), uploadRecordInfoPO1.getUploadHost());
        Assert.assertEquals(uploadRecordInfoPO.getId(), uploadRecordInfoPO1.getId());
        Assert.assertEquals(uploadRecordInfoPO.getTakeTime(), uploadRecordInfoPO1.getTakeTime());
        Assert.assertEquals(uploadRecordInfoPO.getFileHash(), uploadRecordInfoPO1.getFileHash());
        Assert.assertEquals(uploadRecordInfoPO.getUrl(), uploadRecordInfoPO1.getUrl());

    }

    @Test
    public void testKeyFrame() {

        LinkKeyFrameData linkKeyFrameData = new LinkKeyFrameData();
        linkKeyFrameData.setTitle("1111");
        String s = IronJsonUtils.objectToJson(linkKeyFrameData);

        LinkKeyFrameData videoKeyFrameEntity1 = IronJsonUtils.jsonToPojo(s, LinkKeyFrameData.class);
        Assert.assertNotNull(videoKeyFrameEntity1);
        Assert.assertEquals(videoKeyFrameEntity1.getTitle(), linkKeyFrameData.getTitle());
    }


    @Test
    public void testTextKeyFrameData() {

        TextKeyFrameData linkKeyFrameData = new TextKeyFrameData();
        linkKeyFrameData.setContent("1111");
        String s = IronJsonUtils.objectToJson(linkKeyFrameData);

        TextKeyFrameData videoKeyFrameEntity1 = IronJsonUtils.jsonToPojo(s, TextKeyFrameData.class);
        Assert.assertNotNull(videoKeyFrameEntity1);
        Assert.assertEquals(linkKeyFrameData.getContent(), linkKeyFrameData.getContent());
    }

    @Test
    public void testVideoKeyFrameBO() {

        VideoKeyFrameBO videoKeyFrameBO = new VideoKeyFrameBO();
        videoKeyFrameBO.setId("1");
        videoKeyFrameBO.setKeyFrameData("123");
        videoKeyFrameBO.setKeyFrameType("text");
        videoKeyFrameBO.setTimeOffset(111L);
        String s = IronJsonUtils.objectToJson(videoKeyFrameBO);

        VideoKeyFrameBO videoKeyFrameEntity1 = IronJsonUtils.jsonToPojo(s, VideoKeyFrameBO.class);
        Assert.assertNotNull(videoKeyFrameEntity1);
        Assert.assertEquals(videoKeyFrameEntity1.getId(), videoKeyFrameBO.getId());
        Assert.assertEquals(videoKeyFrameEntity1.getKeyFrameData(), videoKeyFrameBO.getKeyFrameData());
        Assert.assertEquals(videoKeyFrameEntity1.getKeyFrameType(), videoKeyFrameBO.getKeyFrameType());
        Assert.assertEquals(videoKeyFrameEntity1.getTimeOffset(), videoKeyFrameBO.getTimeOffset());
    }

}
