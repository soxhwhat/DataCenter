package test.com.juphoon.rtc.datacenter.servicecore.scheduled;

import com.juphoon.rtc.datacenter.servicecore.scheduled.DeleteCollectionJob;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static com.juphoon.rtc.datacenter.datacore.JrtcDataCenterConstant.MONGO_TEMPLATE_EVENT;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test-mysql")
public class DeleteCollectionJobTest {

    @Autowired
    @Qualifier(MONGO_TEMPLATE_EVENT)
    private MongoTemplate eventMongoTemplate;

    @Autowired
    private DeleteCollectionJob deleteCollectionJob;

    @Test
    public void deleteCollectionData() {
        //判断过期时间是否支持可配置
        Assert.assertEquals(7, deleteCollectionJob.getProperties().getClear().getExpireEvent());
        eventMongoTemplate.createCollection("test_event_19990224");
        deleteCollectionJob.deleteEventData();
        //判断集合是否存在
        Assert.assertFalse(eventMongoTemplate.collectionExists("test_event_19990224"));
    }

}