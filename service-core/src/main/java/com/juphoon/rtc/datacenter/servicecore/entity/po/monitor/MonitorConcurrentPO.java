package com.juphoon.rtc.datacenter.servicecore.entity.po.monitor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.juphoon.rtc.datacenter.datacore.api.StateContext;
import lombok.Data;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>实时房间并发数、实时房间同时在线人数监测</p>
 *
 * @author Jiahui.Huang
 * @date 7/17/22 11:10 AM
 * @description
 * FlowStatusJson(uniqueId = JSMS@JSMS.Main0.Main01.Main, type = 22, status = 0, params = {
 *    "appId" : 4,
 *    "concurrentNumber" : 1,
 *    "concurrentPerson" : 2,
 *    "domainId" : 100645,
 *    "from" : "JSMS@JSMS.Main0.Main01.Main",
 *    "resourceType" : 0,
 *    "updateTimeStamp" : "1655773334312"
 * }
 * , domainId=100645, appId=4)
 */
@Data
@Document("jrtc_concurrent_room_#{new java.text.SimpleDateFormat(\"yyyyMMdd\").format(new java.util.Date())}")
@CompoundIndexes({
        @CompoundIndex(name = "time_domain_app_idx",
                def = "{'timestamp':1,'domainId':1,'appId':1}")
})
public class MonitorConcurrentPO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 上报服务
     * </br> e.g. JSMS@JSMS.Main0.Main01.Main
     */
    private String from;

    /**
     * 最后更新时间戳
     * </br> e.g. 1655263216229
     */
    private Long timestamp;

    /**
     * 并发房间数
     */
    private Integer room;

    /**
     * 并发成员数
     */
    private Integer actor;


    /**
     * 域ID
     */
    private Integer domainId;

    /**
     * 应用ID
     */
    private Integer appId;

    /**
     * 转换
     * 态数据上报:FlowStatusJson(uniqueId=JSMS@JSMS.Main0.Main01.Main, type=22, status=0, params={
     *    "appId" : 4,
     *    "concurrentNumber" : 1,
     *    "concurrentPerson" : 2,
     *    "domainId" : 100645,
     *    "from" : "JSMS@JSMS.Main0.Main01.Main",
     *    "resourceType" : 0,
     *    "updateTimeStamp" : "1655773334312"
     * }
     * , domainId=100645, appId=4)
     * @param context
     * @return
     * @throws JsonProcessingException
     */
    public static MonitorConcurrentPO fromState(StateContext context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<HashMap<String, Object>> typeRef = new TypeReference<HashMap<String, Object>>() {
        };
        Map<String, Object> params = mapper.readValue(context.getState().getParams(), typeRef);

        Long timestamp = Long.valueOf((String.valueOf(params.get("updateTimeStamp"))));
        Integer concurrentNumber = (Integer) params.get("concurrentNumber");
        Integer concurrentPerson = (Integer) params.get("concurrentPerson");
        Integer domainId = (Integer) params.get("domainId");
        Integer appId = (Integer) params.get("appId");

        assert timestamp > 0: "坐席队列参数 timestamp 为空";
        assert null != concurrentNumber : "坐席队列参数 concurrentNumber 为空";
        assert null != concurrentPerson : "坐席队列参数 concurrentPerson 为空";

        MonitorConcurrentPO po = new MonitorConcurrentPO();

        po.setDomainId(domainId);
        po.setAppId(appId);
        po.setFrom(context.getState().getUniqueId());
        po.setTimestamp(timestamp);
        po.setRoom(concurrentNumber);
        po.setActor(concurrentPerson);

        return po;
    }


}
