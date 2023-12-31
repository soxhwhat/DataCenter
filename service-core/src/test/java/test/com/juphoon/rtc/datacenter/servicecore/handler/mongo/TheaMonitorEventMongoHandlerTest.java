package test.com.juphoon.rtc.datacenter.servicecore.handler.mongo;

import com.juphoon.rtc.datacenter.datacore.api.EventContext;
import com.juphoon.rtc.datacenter.servicecore.entity.po.thea.TheaRecvPO;
import com.juphoon.rtc.datacenter.servicecore.entity.po.thea.TheaSendPO;
import com.juphoon.rtc.datacenter.servicecore.handle.mongo.TheaMonitorEventMongoHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.time.DateFormatUtils;
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

import java.util.Date;
import java.util.List;

import static com.juphoon.rtc.datacenter.datacore.JrtcDataCenterConstant.MONGO_TEMPLATE_EVENT;
import static com.juphoon.rtc.datacenter.servicecore.api.MongoCollectionEnum.COLLECTION_EVENT_THEA_RECV;
import static com.juphoon.rtc.datacenter.servicecore.api.MongoCollectionEnum.COLLECTION_EVENT_THEA_SEND;


/**
 * <p>天赛音视频上下行通话质量handler测试类</p>
 *
 * @author Jiahui.Huang
 * @date 2022-07-11
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test-mysql")
public class TheaMonitorEventMongoHandlerTest {

    @Autowired
    private TheaMonitorEventMongoHandler theaMonitorEventMongoHandler;

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
        theaMonitorEventMongoHandler.handle(ec);
        String recvCollection = COLLECTION_EVENT_THEA_RECV.getName() + DateFormatUtils.format(new Date(ec.getCreatedTimestamp()), "yyyyMMdd");
        String sendCollection = COLLECTION_EVENT_THEA_SEND.getName() + DateFormatUtils.format(new Date(ec.getCreatedTimestamp()), "yyyyMMdd");
        //查询条件为timestamp的记录，并返回pojo类型的结果
        Query query = new Query(new Criteria("timestamp").is(ec.getEvent().getTimestamp()));
        List<TheaRecvPO> theaRecvPos = mongoTemplate.find(query, TheaRecvPO.class, recvCollection);
        TheaSendPO sendPo = mongoTemplate.findOne(query, TheaSendPO.class, sendCollection);
        Assert.assertNotNull(theaRecvPos);
        Assert.assertNotNull(sendPo);
        Assert.assertEquals(2, theaRecvPos.size());
        Assert.assertEquals("103451680701174926", sendPo.getCallId());


    }


}