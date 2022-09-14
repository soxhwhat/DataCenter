package com.juphoon.rtc.datacenter.servicecore.entity.po.thea;


import com.juphoon.rtc.datacenter.datacore.api.Event;
import com.juphoon.rtc.datacenter.datacore.api.EventContext;
import com.juphoon.rtc.datacenter.servicecore.utils.TheaUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Map;

import static com.juphoon.rtc.datacenter.servicecore.api.TheaConstant.*;


/**
 * <p>天赛音视频加房成功率检测</p>
 *
 * @author Jiahui.Huang
 * @date 2022/7/18 13:42
 * @description
 */
@Getter
@Setter
@ToString
@Document
@CompoundIndexes({
        @CompoundIndex(name = "time_domain_app_idx",
                def = "{'timestamp':-1,'domainId':1,'appId':1}")
})
public class TheaJoinMonitorPO extends TheaCommonPO {
    /**
     * 更新时间戳
     */
    private Integer date;

    /**
     * 会场id
     */
    private String callId;

    /**
     * 加入房间成功次数
     */
    private Integer joinSuccessCount = 0;

    /**
     * 加入房间总次数
     */
    private Integer joinRoomCount = 0;

    /**
     * 将EventContext转换为TheaJoinMonitorPO
     *  数据源为天赛全量数据（type=900, number=0)
     *  转换结果为：
     *  {
     *       "_id": "62d678d1e23bd80a5e0b7199",
     *       "timestamp": 1658222801572,
     *       "callId": "103451680701174926",
     *       "joinSuccessCount": 1,
     *       "joinRoomCount": 1,
     *       "domainId": 100646,
     *       "appId": 1,
     * }
     * @param context
     * @return TheaJoinMonitorPO
     */
    public static TheaJoinMonitorPO fromEvent(EventContext context) {
        Event event = context.getEvent();
        commonCheckParam(event);

        Map<String, Object> general = TheaUtil.getGeneral(event);

        TheaJoinMonitorPO po = new TheaJoinMonitorPO();
        po.setAppId(event.getAppId());
        po.setDomainId(event.getDomainId());
        po.setDate(Integer.valueOf(DateFormatUtils.format(new Date((Long) event.getTimestamp()),"yyyyMMdd")));
        po.setCallId((String) general.get(CALL_ID));
        int eventNum = (int) general.getOrDefault(EVENT, -1);
        if(eventNum == JOIN_SUCCESS || eventNum == JOIN_FAIL) {
            po.setJoinRoomCount(po.getJoinRoomCount() + 1);
            if((int) general.get(EVENT) == 1) {
                po.setJoinSuccessCount(po.getJoinSuccessCount() + 1);
            }
        }
        return po;

    }

}
