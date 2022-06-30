package com.juphoon.rtc.datacenter.servicecore.entity.po.thea;

import com.juphoon.rtc.datacenter.datacore.api.Event;
import com.juphoon.rtc.datacenter.datacore.api.EventContext;
import com.juphoon.rtc.datacenter.servicecore.utils.TheaUtil;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;

import static com.juphoon.rtc.datacenter.servicecore.api.TheaConstant.*;

/**
 * <p>天赛上行通话质量检测</p>
 *
 * @author Jiahui.Huang
 * @date 7/7/22 11:10 AM
 */
@Getter
@Setter
@ToString
@SuppressFBWarnings("SpellCheckingInspection")
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

    /**
     * rtt往返时延
     */
    private Integer suRtt;

    /**
     * 上行抖动
     */
    private Integer suJitter;

    /**
     * 下行抖动
     */
    private Integer sdJitter;

    /**
     * 将EventContext转换为TheaSendPO
     *  数据源为天赛全量数据（type=900, number=0)
     *  转换结果为：
     *    {
     *       "_id": "62cd553477e3912ce881cccd",
     *       "csVBr": "-1",
     *       "suLoss": "0",
     *       "csVFps": "-1",
     *       "csVCapFps": "-1",
     *       "csABr": "-1",
     *       "csAVol": "100",
     *       "csSFps": "-1",
     *       "csSCapFps": "-1",
     *       "suRtt": "0",
     *       "suJitter": "0",
     *       "sdJitter": "0",
     *       "timestamp": "1657623860079",
     *       "callId": "103451680701174926",
     *       "cbAccountid": "[username:delivery_JMDS.Main0.Main01.Main_0@100074.cloud.justalk.com]",
     *       "domainId": "100645",
     *       "appId": "0",
     *       "_class": "com.juphoon.rtc.datacenter.servicecore.entity.po.thea.TheaSendPO"
     *     }
     * @param context
     * @return TheaSendPO
     */
    public static TheaSendPO fromEvent(EventContext context) {
        Event event = context.getEvent();
        commonCheckParam(event);

        Map<String, Object> general = TheaUtil.getGeneral(event);
        Map<String, Object> jsm = TheaUtil.getJsm(event);
        Map<String, Object> zmf = TheaUtil.getZmf(event);

        TheaSendPO po = new TheaSendPO();
        po.setTimestamp(event.getTimestamp());
        po.setAppId(event.getAppId());
        po.setDomainId(event.getDomainId());
        po.setCallId((String) general.get(CALL_ID));
        po.setCbAccountid((String) jsm.get(CB_ACCOUNT_ID));
        po.setCsVBr((Integer) jsm.getOrDefault(CS_V_BR, -1));
        po.setCsAVol((Integer) zmf.getOrDefault(CS_A_VOL, -1));
        po.setCsABr((Integer) jsm.getOrDefault(CS_A_BR, -1));
        po.setCsSCapFps((Integer) jsm.getOrDefault(CS_S_CAP_FPS, -1));
        po.setCsSFps((Integer) jsm.getOrDefault(CS_S_FPS, -1));
        po.setCsSBr((Integer) jsm.getOrDefault(CS_S_BR, -1));
        po.setCsVCapFps((Integer) jsm.getOrDefault(CS_V_CAP_FPS, -1));
        po.setCsVFps((Integer) jsm.getOrDefault(CS_V_FPS, -1));
        po.setSuLoss((Integer) jsm.getOrDefault(SU_LOSS, -1));
        po.setSuRtt((Integer) jsm.getOrDefault(SU_RTT, -1));
        po.setSuJitter((Integer) jsm.getOrDefault(SU_JITTER, -1));
        po.setSdJitter((Integer) jsm.getOrDefault(SD_JITTER, -1));

        return po;
    }

}
