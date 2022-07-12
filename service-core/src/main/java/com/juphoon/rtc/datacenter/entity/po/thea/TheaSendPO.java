package com.juphoon.rtc.datacenter.entity.po.thea;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.juphoon.rtc.datacenter.api.Event;
import com.juphoon.rtc.datacenter.api.EventContext;
import com.juphoon.rtc.datacenter.api.StateContext;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>天赛上行通话质量检测</p>
 *
 * @author Jiahui.Huang
 * @date 7/7/22 11:10 AM
 */
@Getter
@Setter
@ToString
public class TheaSendPO extends TheaMonitorPO {

    /**
     * 视频发送码率
     */
    private Integer csVBr;

    /**
     * 上行丢包率
     */
    private Integer suLoss;

    /**
     * 发送帧率
     */
    private Integer csVFps;

    /**
     * 采集帧率
     */
    private Integer csVCapFps;


    /**
     * 音频发送码率
     */
    private Integer csABr;

    /**
     * 音频采集音量
     */
    private Integer csAVol;

    /**
     * 屏幕共享码率
     */
    private Integer csSBr;


    /**
     * 发送帧率
     */
    private Integer csSFps;

    /**
     * 采集帧率
     */
    private Integer csSCapFps;

    public static TheaSendPO fromEvent(EventContext context) {
        Event event = context.getEvent();
        commonCheckParam(event);

        Map<String, Object> jsm = event.getJsm();
        Map<String, Object> zmf = event.getZmf();

        TheaSendPO po = new TheaSendPO();
        po.setTimestamp(event.getTimestamp());
        po.setAppId(event.getAppId());
        po.setDomainId(event.getDomainId());
        po.setCallId((String) event.getGeneral().get("m_id"));
        po.setCbAccountid((String) event.getJsm().get("cb_account_id"));
        po.setCsVBr((Integer) jsm.getOrDefault("cs_v_br", -1));
        po.setCsAVol((Integer) zmf.getOrDefault("cs_a_vol", -1));
        po.setCsABr((Integer) jsm.getOrDefault("cs_a_br", -1));
        po.setCsSCapFps((Integer) jsm.getOrDefault("cs_s_cap_fps", -1));
        po.setCsSFps((Integer) jsm.getOrDefault("cs_s_fps", -1));
        po.setCsVBr((Integer) jsm.getOrDefault("cs_v_br", -1));
        po.setCsVCapFps((Integer) jsm.getOrDefault("cs_v_cap_fps", -1));
        po.setCsVFps((Integer) jsm.getOrDefault("cs_v_fps", -1));
        po.setSuLoss((Integer) jsm.getOrDefault("su_loss", -1));

        return po;
    }

}
