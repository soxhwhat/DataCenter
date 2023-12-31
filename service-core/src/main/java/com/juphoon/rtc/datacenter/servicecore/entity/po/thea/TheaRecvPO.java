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
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.juphoon.rtc.datacenter.servicecore.api.TheaConstant.*;

/**
 * <p>天赛下行通话质量检测</p>
 *
 * @author Jiahui.Huang
 * @date 7/7/22 11:10 AM
 */
@Getter
@Setter
@ToString
@SuppressFBWarnings("SpellCheckingInspection")
@Document
@CompoundIndexes({
        @CompoundIndex(name = "time_domain_app_actor_idx",
                def = "{'timestamp':1,'domainId':1,'appId':1,'crRecvActorid':1}")
})
public class TheaRecvPO extends TheaMonitorPO {

    /**
     * 对端会议成员
     */
    private String crRecvActor;

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

    /**
     * 视频接收Mos分
     */
    private Integer crTmos;

    /**
     * 音频接收Mos分
     */
    private Integer crAMos;

    /**
     * 将EventContext转换为TheaRecvPO
     *  数据源为天赛全量数据（type=900, number=0)
     *  转换结果为：
     *     {
     *       "_id": "62ce2340fbca731d3147e6e1",
     *       "crRecvActorid": "3",
     *       "crVBr": "0",
     *       "sdLoss": "-1",
     *       "crVFps": "0",
     *       "crVH": "-1",
     *       "crVW": "-1",
     *       "crABr": "-1",
     *       "crSBr": "-1",
     *       "crSFps": "-1",
     *       "crSRendFps": "-1",
     *       "crSH": "-1",
     *       "crSW": "-1",
     *       "timestamp": "1657676558031",
     *       "callId": "103451680701174926",
     *       "cbAccountid": "[username:delivery_JMDS.Main0.Main01.Main_0@100074.cloud.justalk.com]",
     *       "domainId": "100645",
     *       "appId": "0",
     *       "_class": "com.juphoon.rtc.datacenter.servicecore.entity.po.thea.TheaRecvPO"
     *     }
     * @param context
     * @return TheaRecvPO
     */
    public static List<TheaRecvPO> fromEvent(EventContext context) {
        List<TheaRecvPO> theaRecvPoList = new ArrayList<>();

        Event event = context.getEvent();
        commonCheckParam(event);
        Map<String, Object> jsm = TheaUtil.getJsm(event);

        List recvActors = TheaUtil.getRecvActors(event);
        if (recvActors != null && recvActors.size() > 0) {
            recvActors.forEach(recvActor -> {
                Map<String, Object> recvActorMap = (Map<String, Object>) recvActor;
                String cbAccountid = TheaUtil.absAccountId((String) jsm.getOrDefault(CB_ACCOUNT_ID,"null"));
                if (recvActorMap.get(CR_RECV_ACTOR_ID) != null) {
                    String crRecvActor = TheaUtil.absAccountId((String) recvActorMap.getOrDefault(CR_RECV_ACTOR, "null"));
                    TheaRecvPO po = new TheaRecvPO();
                    po.setTimestamp(event.getTimestamp());
                    po.setAppId(event.getAppId());
                    po.setDomainId(event.getDomainId());
                    po.setCallId((String) TheaUtil.getGeneral(event).get(CALL_ID));
                    po.setCbAccountid(cbAccountid);
                    po.setCrRecvActor(crRecvActor);
                    po.setCrAMos((Integer)recvActorMap.getOrDefault(CR_A_MOS, -1));
                    po.setCrTmos((Integer)recvActorMap.getOrDefault(CR_V_TMOS, -1));
                    po.setCrSBr((Integer) recvActorMap.getOrDefault(CR_S_BR, -1));
                    po.setCrSFps((Integer) recvActorMap.getOrDefault(CR_S_FPS, -1));
                    po.setCrSRendFps((Integer) recvActorMap.getOrDefault(CR_S_REND_FPS, -1));
                    po.setCrSH((Integer) recvActorMap.getOrDefault(CR_S_H, -1));
                    po.setCrSW((Integer) recvActorMap.getOrDefault(CR_S_W, -1));
                    po.setCrABr((Integer) recvActorMap.getOrDefault(CR_A_BR, -1));
                    po.setCrVBr((Integer) recvActorMap.getOrDefault(CR_V_BR, -1));
                    po.setCrVFps((Integer) recvActorMap.getOrDefault(CR_V_FPS, -1));
                    po.setCrVH((Integer) recvActorMap.getOrDefault(CR_V_H, -1));
                    po.setCrVW((Integer) recvActorMap.getOrDefault(CR_V_W, -1));
                    po.setSdLoss((Integer) jsm.getOrDefault(SD_LOSS, -1));
                    theaRecvPoList.add(po);
                }
            });

        }
        return theaRecvPoList;

    }
}