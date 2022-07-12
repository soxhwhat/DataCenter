package com.juphoon.rtc.datacenter.entity.po.thea;

import com.juphoon.rtc.datacenter.api.Event;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.collections.MapUtils;
import org.springframework.util.StringUtils;

import java.util.Map;

/**
 * <p>天赛音视频通话质量检测</p>
 * <p>TODO</p>
 *
 * @author Jiahui.Huang
 * @date 2022/7/8 13:42
 * @description
 * 类中字段为天赛音视频通话上下行质量检测的公共字段
 */
@Getter
@Setter
@ToString
public class TheaMonitorPO extends TheaCommonPO {
    /**
     * 更新时间戳
     */
    private Long timestamp;

    /**
     * 会场id
     */
    private String callId;


    /**
     * 成员ID
     */
    private String cbAccountid;



}
