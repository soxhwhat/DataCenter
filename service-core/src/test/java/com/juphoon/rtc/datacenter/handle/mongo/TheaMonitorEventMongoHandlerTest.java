package com.juphoon.rtc.datacenter.handle.mongo;

import com.juphoon.rtc.datacenter.api.EventContext;
import com.juphoon.rtc.datacenter.entity.po.thea.TheaEventContextUtils;
import com.juphoon.rtc.datacenter.entity.po.thea.TheaRecvPO;
import com.juphoon.rtc.datacenter.entity.po.thea.TheaSendPO;
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


import org.springframework.data.mongodb.core.query.Query;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

import static com.juphoon.rtc.datacenter.JrtcDataCenterConstant.MONGO_TEMPLATE_EVENT;
import static com.juphoon.rtc.datacenter.api.MongoCollectionEnum.COLLECTION_EVENT_THEA_RECV;
import static com.juphoon.rtc.datacenter.api.MongoCollectionEnum.COLLECTION_EVENT_THEA_SEND;

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
        Query query = new Query(new org.springframework.data.mongodb.core.query.Criteria("timestamp").is(ec.getEvent().getTimestamp()));
        List<TheaRecvPO> theaRecvPOS = mongoTemplate.find(query, TheaRecvPO.class, recvCollection);
        TheaSendPO sendPO = mongoTemplate.findOne(query, TheaSendPO.class, sendCollection);
        Assert.assertNotNull(theaRecvPOS);
        Assert.assertNotNull(sendPO);
        Assert.assertTrue(theaRecvPOS.size() == 2);
        Assert.assertTrue(sendPO.getCallId().equals("103451680701174926"));


    }


}