package com.juphoon.rtc.datacenter.dist.c09.entity.po.ticket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.juphoon.rtc.datacenter.datacore.api.Event;
import com.juphoon.rtc.datacenter.dist.c09.entity.po.C09CommonPO;
import lombok.Data;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;

import java.util.Collections;
import java.util.Map;

/**
 * <p>宁波客服话单事件</p>
 *
 * @author Jiahui.Huang
 * @date 2022-08-02
 * @description 用于统计宁波客服话单拉起通话成功率、等待时长、通话时长
 */
@Data
@CompoundIndexes({
        @CompoundIndex(name = "time_domain_app_idx",
                def = "{'timestamp':1,'domainId':1,'appId':1}")
})
public class ExternalCallTicketPO extends C09CommonPO {

    /**
     * 通话时长
     * talkTime = 0表示没接通
     */
    private Long talkTime;

    /**
     * 拉起通话成功次数
     */
    private Integer successCount;

    /**
     * 拉起通话总次数
     */
    private Integer totalCount;

    /**
     * 等待时长
     */
    private Long waitTime;


    /**
     * 转换
     * (uniqueId = JSMS @ JSMS.Main0.Main01.Main, type = 11, status = 1, params = {
     *    "waitTime" : 1,
     *    ”talkTime“ : 1,
     * }
     * , domainId=100645, appId=4)
     * @param event
     * @return
     */
    @Override
    public void fromEvent(Event event) throws JsonProcessingException {
        Map<String, Object> params = event.getParams() == null ? Collections.EMPTY_MAP : event.getParams();

        super.fromEvent(event);
        Long talkTime = (Long) params.getOrDefault("talkTime", -1);
        Long waitTime = (Long) params.getOrDefault("waitTime", -1);

        if (talkTime != -1) {
            this.setSuccessCount(talkTime > 0 ? 1 : 0);
            this.setTotalCount(1);
            this.setTalkTime(talkTime);
        }

        if (waitTime != -1) {
            this.setWaitTime(waitTime);
        }

        this.setUniqueKey(getUniqueKey());
    }

}
