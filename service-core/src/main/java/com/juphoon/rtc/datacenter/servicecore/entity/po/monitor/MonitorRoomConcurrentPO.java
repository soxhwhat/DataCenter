package com.juphoon.rtc.datacenter.servicecore.entity.po.monitor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.juphoon.rtc.datacenter.datacore.api.StateContext;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>视频客服排队机队列监控</p>
 *
 * @author ajian.zheng@juphoon.com
 * @date 6/21/22 17:33
 * @description
 * FlowStatusJson(uniqueId = JSMS @ JSMS.Main0.Main01.Main, type = 22, status = 0, params = {
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
public class MonitorRoomConcurrentPO implements Serializable {
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
    public static MonitorRoomConcurrentPO fromState(StateContext context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<HashMap<String, Object>> typeRef = new TypeReference<HashMap<String, Object>>() {
        };
        Map<String, Object> params = mapper.readValue(context.getState().getParams(), typeRef);

        Long timestamp = Long.valueOf((String.valueOf(params.get("updateTimeStamp"))));
        Integer concurrentNumber = (Integer) params.get("concurrentNumber");
        Integer concurrentPerson = (Integer) params.get("concurrentPerson");

        assert timestamp > 0 : "坐席队列参数 timestamp 为空";
        assert null != concurrentNumber : "坐席队列参数 concurrentNumber 为空";
        assert null != concurrentPerson : "坐席队列参数 concurrentPerson 为空";

        MonitorRoomConcurrentPO po = new MonitorRoomConcurrentPO();

        po.setDomainId(context.getState().getDomainId());
        po.setAppId(context.getState().getAppId());
        po.setFrom(context.getState().getUniqueId());
        po.setTimestamp(timestamp);
        po.setRoom(concurrentNumber);
        po.setActor(concurrentPerson);

        return po;
    }


}
