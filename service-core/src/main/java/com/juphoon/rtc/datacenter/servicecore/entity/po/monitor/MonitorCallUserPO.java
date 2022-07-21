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
 * <p>成员加入、离开房间监控</p>
 *
 * @author Jiahui.Huang
 * @date 7/17/22 11:10 AM
 * @description
 */
@Data
@Document(collection = "jrtc_call_user")
@CompoundIndexes({
        @CompoundIndex(name = "domain_app_call_idx",
                def = "{'domainId':1,'appId':1,'callId':1}")
})
public class MonitorCallUserPO implements Serializable {

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
     * 用户ID
     */
    private String userId;

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
     * 转换数据上报: * FlowStatusJson(timestamp=1658200863847, type=23, eventNumber=98, uuid=, domainId=100645, appId=4, params={
     *    "appId" : "4",
     *    "beginTimestamp" : 1658200813102,
     *    "callId" : "108486620719112011",
     *    "domainId" : "100645",
     *    "endTimestamp" : 1658200863847,
     *    "reason" : "leave",
     *    "roomId" : "gvdf",
     *    "userId" : "[username:delivery_jrtcRecordAgentManager.DC0.Signal100.Main_44@100074.cloud.justalk.com]"
     *    })
     * @param context
     * @return
     * @throws JsonProcessingException
     */

    public static MonitorCallUserPO fromEvent(EventContext context) throws JsonProcessingException {
        Map<String, Object> params = context.getEvent().getParams();
        Event event = context.getEvent();

        Long beginTimestamp = (Long) params.get("beginTimestamp");
        Long endTimestamp = (Long) params.get("endTimestamp");
        Integer domainId = event.getDomainId();
        Integer appId = event.getAppId();
        String roomId = (String) params.get("roomId");
        String user = (String) params.get("userId");
        String callId = (String) params.get("callId");
        String reason = (String) params.get("reason");
        String userId;
        //用户名提取操作
        if (user.startsWith("[username:")) {
            int start = user.indexOf(":") + 1;
            int end = user.indexOf("@", start);
            userId = user.substring(start, end);
        } else {
            userId = user;
        }

        MonitorCallUserPO po = new MonitorCallUserPO();

        po.setDomainId(domainId);
        po.setAppId(appId);
        po.setBeginTimestamp(beginTimestamp);
        po.setEndTimestamp(endTimestamp);
        po.setCallId(callId);
        po.setRoomId(roomId);
        po.setReason(reason);
        po.setUserId(userId);

        return po;
    }


}
