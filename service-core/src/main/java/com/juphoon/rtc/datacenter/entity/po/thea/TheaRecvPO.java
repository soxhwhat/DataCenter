package com.juphoon.rtc.datacenter.entity.po.thea;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.juphoon.rtc.datacenter.api.Event;
import com.juphoon.rtc.datacenter.api.EventContext;
import com.juphoon.rtc.datacenter.api.StateContext;
import com.juphoon.rtc.datacenter.entity.po.monitor.MonitorAcdAgentStatePO;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.util.StringUtils;

import java.util.*;

/**
 * <p>天赛下行通话质量检测</p>
 *
 * @author Jiahui.Huang
 * @date 7/7/22 11:10 AM
 */
@Getter
@Setter
@ToString
public class TheaRecvPO extends TheaMonitorPO {

    /**
     * 对端会议成员ID
     */
    private Integer crRecvActorid;

    /**
     * 视频接收码率
     */
    private Integer crVBr;

    /**
     * 终端接收丢包率
     */
    private Integer sdLoss;

    /**
     * 接收帧率
     */
    private Integer crVFps;

    /**
     * 订阅视频高
     */
    private Integer crVH;

    /**
     * 订阅视频宽
     */
    private Integer crVW;


    /**
     * 音频接收码率
     */
    private Integer crABr;

    /**
     * 屏幕共享接收码率
     */
    private Integer crSBr;


    /**
     * 屏幕共享接收帧率
     */
    private Integer crSFps;

    /**
     * 屏幕共享渲染帧率
     */
    private Integer crSRendFps;


    /**
     * 屏幕共享视频高
     */
    private Integer crSH;

    /**
     * 屏幕共享视频宽
     */
    private Integer crSW;


    public static List<TheaRecvPO> fromEvent(EventContext context) {
        List<TheaRecvPO> theaRecvPOList = new ArrayList<>();

        Event event = context.getEvent();
        commonCheckParam(event);


        Map<String, Object> jsm = event.getJsm();
        List recvActors = event.getRecvActors();
        if (recvActors != null && recvActors.size() > 0) {
            recvActors.forEach(recvActor -> {
                Map<String, Object> recvActorMap = (Map<String, Object>) recvActor;
                if (recvActorMap.get("cr_recv_actor_id") != null) {
                    TheaRecvPO po = new TheaRecvPO();
                    po.setTimestamp(event.getTimestamp());
                    po.setAppId(event.getAppId());
                    po.setDomainId(event.getDomainId());
                    po.setCallId((String) event.getGeneral().get("m_id"));
                    po.setCbAccountid((String) event.getJsm().get("cb_account_id"));
                    po.setCrRecvActorid((Integer) recvActorMap.get("cr_recv_actor_id"));
                    po.setCrSBr((Integer) recvActorMap.getOrDefault("cr_s_br", -1));
                    po.setCrSFps((Integer) recvActorMap.getOrDefault("cr_s_fps", -1));
                    po.setCrSRendFps((Integer) recvActorMap.getOrDefault("cr_s_rend_fps", -1));
                    po.setCrSH((Integer) recvActorMap.getOrDefault("cr_s_h", -1));
                    po.setCrSW((Integer) recvActorMap.getOrDefault("cr_s_w", -1));
                    po.setCrABr((Integer) recvActorMap.getOrDefault("cr_a_br", -1));
                    po.setCrVBr((Integer) recvActorMap.getOrDefault("cr_v_br", -1));
                    po.setCrVFps((Integer) recvActorMap.getOrDefault("cr_v_fps", -1));
                    po.setCrVH((Integer) recvActorMap.getOrDefault("cr_v_h", -1));
                    po.setCrVW((Integer) recvActorMap.getOrDefault("cr_v_w", -1));
                    po.setSdLoss((Integer) recvActorMap.getOrDefault("sd_loss", -1));
                    theaRecvPOList.add(po);
                }
            });

        }
        return theaRecvPOList;

    }
}