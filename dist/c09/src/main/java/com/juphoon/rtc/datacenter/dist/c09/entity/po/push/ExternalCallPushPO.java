package com.juphoon.rtc.datacenter.dist.c09.entity.po.push;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.juphoon.rtc.datacenter.datacore.api.Event;
import com.juphoon.rtc.datacenter.dist.c09.entity.po.C09CommonPO;
import lombok.Data;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Collections;
import java.util.Map;

/**
 * <p>宁波外呼push事件</p>
 *
 * @author Jiahui.Huang
 * @date 2022-08-02
 * @description 用于统计宁波外呼push成功率
 */
@Data
@CompoundIndexes({
        @CompoundIndex(name = "time_domain_app_idx",
                def = "{'timestamp':1,'domainId':1,'appId':1}")
})
public class ExternalCallPushPO extends C09CommonPO {

    /**
     * 0失败 1成功
     */
    private Integer result;

    /**
     * 外呼成功次数
     */
    private Integer successCount = 0;

    /**
     * 外呼总次数
     */
    private Integer totalCount = 1;

    /**
     * 请求参数
     */
    private Map requestAppParams;

    /**
     * 堆栈信息或者接收方返回的code以及data
     */
    private String reason;


    /**
     * 转换
     * (uniqueId = JSMS @ JSMS.Main0.Main01.Main, type = 26, status = 0, params = {
     *    "result" : 1,
     *    "requestAppParams" : {appId : 1111, taskNumber : "http://127.0.0.1:8000/?call=123"},
     *    "reason" : "leave",
     * }
     * , domainId=100645, appId=4)
     * @param event
     * @return
     */
    @Override
    public void fromEvent(Event event) throws JsonProcessingException {
        Map<String, Object> params = event.getParams() == null ? Collections.EMPTY_MAP : event.getParams();

        super.fromEvent(event);
        Integer result = (Integer) params.getOrDefault("result", 0);
        Map requestAppParams = (Map) params.get("requestAppParams");
        String reason = (String) params.get("reason");

        if (result != 0) {
            this.setSuccessCount(1);
        }


        this.setResult(result);
        this.setRequestAppParams(requestAppParams);
        this.setReason(reason);
        this.setUniqueKey(getUniqueKey());
    }

}
