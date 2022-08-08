package com.juphoon.rtc.datacenter.servicecore.entity.po.thea;

import com.juphoon.rtc.datacenter.datacore.api.Event;
import com.juphoon.rtc.datacenter.datacore.api.EventContext;
import com.juphoon.rtc.datacenter.servicecore.utils.TheaUtil;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.juphoon.rtc.datacenter.servicecore.api.TheaConstant.*;

/**
 * <p>天赛音视频质量监测</p>
 *
 *
 * @author Jiahui.Huang
 * @date 7/7/22 11:10 AM
 * @description
 * 监测指标包括：
 *           1. 音频卡顿率
 *           2. 视频卡顿率
 *           3. 优质传输率
 *           4. rtt
 *           5. 上行抖动
 *           6. 下行抖动
 */
@Getter
@Setter
@ToString
@SuppressFBWarnings("SpellCheckingInspection")
@Document
@CompoundIndexes({
        @CompoundIndex(name = "date_domain_app_idx",
                def = "{'date':1,'domainId':1,'appId':1}")
})
public class TheaQualityMonitorPO extends TheaCommonPO {

    /**
     * 日期
     * </br> e.g. 20220705
     */
    @Indexed(unique = true)
    private Integer date;

    /**
     * MOS分低于2.5的音频事件次数
     */
    private Integer aMosLowCount = 0;

    /**
     * MOS分低于2.5的视频事件次数
     */
    private Integer tMosLowCount = 0;

    /**
     * 总MOS音频事件次数
     */
    private Integer totalAMosCount = 0;

    /**
     * 总MOS视频事件次数
     */
    private Integer totalTMosCount = 0;


    /**
     * 未丢包次数
     */
    private Integer unLossCount = 0;


    /**
     * 丢包统计次数
     */
    private Integer lossTotalCount = 0;

    /**
     * 总rtt往返时延
     */
    private Integer suRtt = 0;

    /**
     * rtt统计次数
     */
    private Integer rttTotalCount = 0;


    /**
     * 总上行抖动
     */
    private Integer suJitter = 0;

    /**
     * 总下行抖动
     */
    private Integer sdJitter = 0;

    /**
     * 上行抖动统计次数
     */
    private Integer suJitterTotalCount = 0;

    /**
     * 下行抖动统计次数
     */
    private Integer sdJitterTotalCount = 0;

    /**
     * 上传设备类型, 0代表不区分设备,1代表录制CD
     */
    private Integer type = 0;

    /**
     * 将EventContext转换为TheaQualityMonitorPO
     *  数据源为天赛全量数据（type=900, number=0)
     *  转换结果为：
     *  {
     *       "_id": "62ce7a97b33c18d6f43d61c2",
     *       "appId": "0",
     *       "domainId": "100645",
     *       "date": "20220701",
     *       "aMosLowCount": "2",
     *       "lossTotalCount": "1",
     *       "rttTotalCount": "1",
     *       "sdJitter": "0",
     *       "sdJitterTotalCount": "1",
     *       "suJitter": "0",
     *       "suJitterTotalCount": "1",
     *       "suRtt": "0",
     *       "tMosLowCount": "0",
     *       "totalAMosCount": "2",
     *       "totalTMosCount": "0",
     *       "unLossCount": "1"
     * }
     * @param context
     * @return TheaQualityMonitorPO
     */
    public static TheaQualityMonitorPO fromEvent(EventContext context) {
        Event event = context.getEvent();
        commonCheckParam(event);

        Map<String, Object> jsm = TheaUtil.getJsm(event);
        List recvActors = TheaUtil.getRecvActors(event);

        TheaQualityMonitorPO po = new TheaQualityMonitorPO();
        po.setDate(Integer.valueOf(new SimpleDateFormat("yyyyMMdd").format(new Date((Long) event.getParams().get("tkm_collect_time")))));
        po.setAppId(event.getAppId());
        po.setDomainId(event.getDomainId());
        //筛选出上传设备类型为录制CD的设备
        if (jsm.get(CB_ACCOUNT_ID).toString().startsWith("[username:delivery")) {
            po.setType(1);
        }
        //如果sd_loss小于5，则认为是未丢包，给unLossCount加1
        //每统计一次，totalLossCount加1
        if (jsm.get(SD_LOSS) != null) {
            if (Integer.parseInt(jsm.get(SD_LOSS).toString()) < SD_LOSS_STANDARD) {
                po.setUnLossCount(po.getUnLossCount() + 1);
            }
            po.setLossTotalCount(po.getLossTotalCount() + 1);
        }
        //统计rtt，如果有rtt，则给rttTotalCount加1，给suRtt加上rtt的值
        if (jsm.get(SU_RTT) != null) {
            po.setRttTotalCount(po.getRttTotalCount() + 1);
            po.setSuRtt(po.getSuRtt() + Integer.parseInt(jsm.get(SU_RTT).toString()));
        }
        //统计上行抖动，如果有上行抖动，则给jitterTotalCount加1，给suJitter加上上行抖动的值
        if (jsm.get(SU_JITTER) != null) {
            po.setSuJitterTotalCount(po.getSuJitterTotalCount() + 1);
            po.setSuJitter(po.getSuJitter() + Integer.parseInt(jsm.get(SU_JITTER).toString()));
        }
        //统计下行抖动，如果有下行抖动，则给jitterTotalCount加1，给sdJitter加上下行抖动的值
        if (jsm.get(SD_JITTER) != null) {
            po.setSdJitterTotalCount(po.getSdJitterTotalCount() + 1);
            po.setSdJitter(po.getSdJitter() + Integer.parseInt(jsm.get(SD_JITTER).toString()));
        }


        if (recvActors != null && recvActors.size() > 0) {
            recvActors.forEach(recvActor -> {
                Map<String, Object> recvActorMap = (Map<String, Object>) recvActor;
                if (recvActorMap.get(CR_A_MOS) != null) {
                    po.setTotalAMosCount(po.getTotalAMosCount() + 1);
                    if (Integer.parseInt(recvActorMap.get(CR_A_MOS).toString()) < MOS_STANDARD) {
                        po.setAMosLowCount(po.getAMosLowCount() + 1);
                    }
                }
                if (recvActorMap.get(CR_V_TMOS) != null) {
                    po.setTotalTMosCount(po.getTotalTMosCount() + 1);
                    if (Integer.parseInt(recvActorMap.get(CR_V_TMOS).toString()) < MOS_STANDARD) {
                        po.setTMosLowCount(po.getTMosLowCount() + 1);
                    }
                }
            });

        }
        return po;
    }
}
