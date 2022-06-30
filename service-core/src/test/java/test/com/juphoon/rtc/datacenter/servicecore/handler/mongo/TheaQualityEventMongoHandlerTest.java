
package test.com.juphoon.rtc.datacenter.servicecore.handler.mongo;

import com.juphoon.rtc.datacenter.datacore.api.EventContext;
import com.juphoon.rtc.datacenter.servicecore.entity.po.thea.TheaQualityMonitorPO;
import com.juphoon.rtc.datacenter.servicecore.handle.mongo.TheaQualityEventMongoHandler;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;


import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import test.com.juphoon.rtc.datacenter.servicecore.entity.po.thea.TheaEventContextUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.juphoon.rtc.datacenter.datacore.JrtcDataCenterConstant.MONGO_TEMPLATE_EVENT;


/**
 * <p>音视频质量监测处理handler测试类</p>
 *
 * @author Jiahui.Huang
 * @date 2022-07-11
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test-mysql")
public class TheaQualityEventMongoHandlerTest {

    @Autowired
    private TheaQualityEventMongoHandler theaQualityEventMongoHandler;

    @Autowired
    @Qualifier(MONGO_TEMPLATE_EVENT)
    private MongoTemplate mongoTemplate;

    EventContext ec;

    @Before
    public void initEc() {
        log.info("init ec");
        ec = TheaEventContextUtils.getEventContext();
    }


    @Test
    public void handle() {
        theaQualityEventMongoHandler.handle(ec);
        String collectionName = "jrtc_thea_quality";
        Integer date = Integer.valueOf(new SimpleDateFormat("yyyyMMdd").format(new Date((Long) ec.getEvent().getParams().get("tkm_collect_time"))));
        TheaQualityMonitorPO po = mongoTemplate.findOne(Query.query(Criteria.where("date").is(date)), TheaQualityMonitorPO.class, collectionName);
        Assert.assertNotNull(po);
        Assert.assertEquals(20220701, (int) po.getDate());

    }


}