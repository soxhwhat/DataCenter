package com.juphoon.rtc.datacenter.servicecore.entity.po.monitor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.juphoon.rtc.datacenter.datacore.api.Event;
import com.juphoon.rtc.datacenter.datacore.api.EventContext;
import lombok.Data;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Map;

/**
 * <p>平台创建、销毁房间监控</p>
 *
 * @author Jiahui.Huang
 * @date 7/17/22 11:10 AM
 * @description
 */
@Document(collection = "jrtc_call_room")
@Data
@CompoundIndexes({
        @CompoundIndex(name = "domain_app_call_idx",
                def = "{'domainId':1,'appId':1,'callId':1}")
})
public class MonitorCallRoomPO implements Serializable {


    /**
     * 创建房间时间
     */
    private Long beginTimestamp;

    /**
     * 销毁房间时间
     */
    private Long endTimestamp;

    /**
     * 域ID
     */
    private Integer domainId;

    /**
     * 应用ID
     */
    private Integer appId;

    /**
     * 房间ID
     */
    private String roomId;

    /**
     * 会场ID
     */
    private String callId;

    /**
     * 离开原因
     */
    private String reason;
    /**
     *
     * 转换: * FlowStatusJson(timestamp=1658200991816, type=23, eventNumber=2, uuid=, domainId=100645, appId=4, params={
     *    "appId" : "4",
     *    "beginTimestamp" : 1658200811719,
     *    "callId" : "108486620719112011",
     *    "domainId" : "100645",
     *    "endTimestamp" : 1658200991816,
     *    "reason" : "",
     *    "roomId" : "gvdf"
     * })
     * @param context
     * @return
     * @throws JsonProcessingException
     */
    public static MonitorCallRoomPO fromEvent(EventContext context) throws JsonProcessingException {
        Map<String, Object> params = context.getEvent().getParams();
        Event event = context.getEvent();

        Long beginTimestamp = (Long) params.get("beginTimestamp");
        Long endTimestamp = (Long) params.get("endTimestamp");
        Integer domainId = event.getDomainId();
        Integer appId = event.appId();
        String roomId = (String) params.get("roomId");
        String callId = (String) params.get("callId");
        String reason = (String) params.get("reason");

        MonitorCallRoomPO po = new MonitorCallRoomPO();

        po.setDomainId(domainId);
        po.setAppId(appId);
        po.setBeginTimestamp(beginTimestamp);
        po.setEndTimestamp(endTimestamp);
        po.setCallId(callId);
        po.setRoomId(roomId);
        po.setReason(reason);

        return po;
    }


}
