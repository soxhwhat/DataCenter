package com.juphoon.rtc.datacenter.entity.po.monitor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.juphoon.rtc.datacenter.api.StateContext;
import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

/**
 * <p>视频客服排队机队列监控</p>
 *
 * @author ajian.zheng@juphoon.com
 * @date 6/21/22 11:10 AM
 */
@Data
public class MonitorAcdQueueCountPO implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 队列
     * </br> e.g. 10086
     */
    private String queue;

    /**
     * 上报服务
     * </br> e.g. #CcAcd@CcAcd.Main2-0.Main
     */
    private String from;

    /**
     * 最后更新时间戳
     * </br> e.g. 1655263216229
     */
    private Long timestamp;

    /**
     * 计数
     */
    private Integer count;

    /**
     * 类型=13
     */
    private Integer type;

    /**
     * 类型
     * @link https://juphoon.yuque.com/videoproducts/wboebb/rc884g#1E9M
     * status=0/1/2
     * 0 waiting
     * 1 ringing
     * 2 talking
     */
    private Integer number;

    /**
     * 域ID
     */
    private Integer domainId;

    /**
     * 应用ID
     */
    private Integer appId;

    @Override
    public String toString() {
        return new StringJoiner(", ", MonitorAcdQueueCountPO.class.getSimpleName() + "[", "]")
                .add("queue='" + queue + "'")
                .add("from='" + from + "'")
                .add("timestamp=" + timestamp)
                .add("count=" + count)
                .add("type=" + type)
                .add("number=" + number)
                .add("domainId=" + domainId)
                .add("appId=" + appId)
                .toString();
    }

    /**
     * 转换
     * (
     *  uniqueId=7000,
     *  type=13,
     *  status=0,
     *  params={
     *    "from" : "#CcAcd@CcAcd.Main2-0.Main",
     *    "queue" : "7000",
     *    "timestamp" : 1655263216229,
     *    "waitCount" : 0
     *  }
     * @param context
     * @return
     * @throws JsonProcessingException
     */
    public static MonitorAcdQueueCountPO fromState(StateContext context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<HashMap<String, Object>> typeRef = new TypeReference<HashMap<String, Object>>() {
        };
        Map<String, Object> params = mapper.readValue(context.getState().getParams(), typeRef);

        String from = (String) params.get("from");
        Long timestamp = Long.valueOf((String.valueOf(params.get("timestamp"))));
        Integer waitCount = (Integer) params.get("waitCount");

        assert null != from : "坐席队列参数 from 为空";
        assert timestamp > 0 : "坐席队列参数 timestamp 为空";
        assert null != waitCount : "坐席队列参数 waitCount 为空";

        MonitorAcdQueueCountPO po = new MonitorAcdQueueCountPO();

        po.setDomainId(context.getState().getDomainId());
        po.setAppId(context.getState().getAppId());
        po.setQueue(context.getState().getUniqueId());
        po.setType(context.getState().getType());
        po.setNumber(context.getState().getState());
        po.setFrom(from);
        po.setTimestamp(timestamp);
        po.setCount(waitCount);

        return po;
    }


}
