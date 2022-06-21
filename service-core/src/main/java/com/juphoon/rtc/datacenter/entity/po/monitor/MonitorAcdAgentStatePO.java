package com.juphoon.rtc.datacenter.entity.po.monitor;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.juphoon.rtc.datacenter.api.EventContext;
import com.juphoon.rtc.datacenter.api.StateContext;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

import static com.juphoon.rtc.datacenter.JrtcDataCenterConstant.ACD_AGENT_STATE_CHECK_OUT;

/**
 * <p>视频客服排队机「坐席状态」监控</p>
 *
 * @author ajian.zheng@juphoon.com
 * @date 6/21/22 11:10 AM
 */
@Data
public class MonitorAcdAgentStatePO {
    /**
     * 域ID
     */
    private Integer domainId;

    /**
     * 应用ID
     */
    private Integer appId;

    /**
     * 队列
     * </br> e.g. 10086
     */
    private String agentId;

    /**
     * 签入时间
     * </br> e.g. 1655263216229
     */
    private Long checkInTimestamp;

    /**
     * 最后更新时间
     * </br> e.g. 1655263216229
     */
    private Long updateTimestamp;

    /**
     * 上次签出时间
     * </br> e.g. 1655263216229
     */
    private Long checkOutTimestamp;

    /**
     * 当前状态开始时间
     * </br> e.g. 1655263216229
     */
    private Long stateBeginTimestamp;

    /**
     * 坐席状态
     */
    private Integer state;

    /**
     * 子状态
     * // todo 待补充
     */
    private Integer subState = 0;

    /**
     * 转换
     * 状态数据上报:FlowStatusJson(
     *    uniqueId=[username:10637048@103291.cloud.justalk.com],
     *    type=12,
     *    status=0,
     *    params={
     *       "acdId" : "#CcOm@CcOm.Main2-0.Main",
     *       "agentId" : "[username:10637048@103291.cloud.justalk.com]",
     *       "agentStatus" : 1,
     *       "checkInTimestamp" : 1655254469663,
     *       "stateBeginTimestamp" : 1655254469663,
     *       "updateTimestamp" : 1655263195948
     *    },
     *    domainId=103291,
     *    appId=0
     * )
     * @param context
     * @return
     */
    public static MonitorAcdAgentStatePO fromState(StateContext context) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        TypeReference<HashMap<String, Object>> typeRef = new TypeReference<HashMap<String, Object>>() {
        };
        Map<String, Object> params = mapper.readValue(context.getState().getParams(), typeRef);

        String from = (String) params.get("acdId");
        Long checkInTimestamp = (Long) params.get("checkInTimestamp");
        Long updateTimestamp = (Long) params.get("updateTimestamp");
        Long stateBeginTimestamp = (Long) params.get("stateBeginTimestamp");
        Integer state = (Integer) params.get("agentStatus");

        assert null != from : "坐席队列参数 from 为空";
        assert null != checkInTimestamp : "坐席队列参数 checkInTimestamp 为空";
        assert null != updateTimestamp : "坐席队列参数 updateTimestamp 为空";
        assert null != state : "坐席队列参数 state 为空";

        MonitorAcdAgentStatePO po = new MonitorAcdAgentStatePO();

        po.setDomainId(context.getState().getDomainId());
        po.setAppId(context.getState().getAppId());
        po.setAgentId(context.getState().getUniqueId());
        po.setCheckInTimestamp(checkInTimestamp);
        po.setUpdateTimestamp(updateTimestamp);
        po.setStateBeginTimestamp(stateBeginTimestamp);
        po.setState(state);

        return po;
    }

    /**
     * 转换，用于坐席签出
     * (
     *    domainId=100645,
     *    appId=4,
     *    uuid=a5f2be31-20a0-44bf-a062-9a3e1a19da00,
     *    type=100,
     *    number=14,
     *    timestamp= 1655710419029,
     *    updateTime=1655710421908,
     *    params={
     *       duration=0,
     *       agentId=[username:agent3@100645.cloud.justalk.com],
     *       beginTimestamp=1655710419029,
     *       endType=0,
     *       appId=4,
     *       endTimestamp=1655710419029,
     *       domainId=100645
     *    }
     * )
     * @param context
     * @return
     * @throws JsonProcessingException
     */
    public static MonitorAcdAgentStatePO fromEvent(EventContext context) throws JsonProcessingException {
        Map<String, Object> params = context.getEvent().getParams();

        String agentId = (String) params.get("agentId");

        assert null != agentId : "坐席队列参数 agentId 为空";

        MonitorAcdAgentStatePO po = new MonitorAcdAgentStatePO();

        po.setDomainId(context.getEvent().getDomainId());
        po.setAppId(context.getEvent().getAppId());
        po.setAgentId(agentId);
        po.setCheckOutTimestamp(context.getEvent().getTimestamp());
        po.setUpdateTimestamp(context.getEvent().getTimestamp());
        po.setStateBeginTimestamp(context.getEvent().getTimestamp());
        po.setState(ACD_AGENT_STATE_CHECK_OUT);
        po.setSubState(0);

        return po;
    }
}