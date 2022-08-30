package com.juphoon.rtc.datacenter.servicecore.api;

/**
 * @Author: Jiahui.Huang
 * @Date: 2022/7/13 14:17
 * @Description:
 * 天赛全量数据监控需要使用的一些常量信息
 *
 */
public interface TheaConstant {

    /**
     * 音频MOS
     */
    String CR_A_MOS = "cr_a_mos";

    /**
     * 视频MOS
     */
    String CR_V_TMOS = "cr_v_tmos";

    /**
     * 会场Id
     */
    String CALL_ID = "m_id";

    /**
     * 总rtt往返时延
     */
    String SU_RTT = "su_rtt";


    /**
     * 总上行抖动
     */
    String SU_JITTER = "su_jitter";

    /**
     * 总下行抖动
     */
    String SD_JITTER = "sd_jitter";

    /**
     * 成员ID
     */
    String CB_ACCOUNT_ID = "cb_account_id";

    /**
     * 对端会议成员
     */
    String CR_RECV_ACTOR = "cb_account_id";

    /**
     * 对端会议成员ID
     */
    String CR_RECV_ACTOR_ID = "cr_recv_actor_id";

    /**
     * 视频接收码率
     */
    String CR_V_BR = "cr_v_br";

    /**
     * 终端接收丢包率
     */
    String SD_LOSS = "sd_loss";

    /**
     * 接收帧率
     */
    String CR_V_FPS = "cr_v_fps";

    /**
     * 订阅视频高
     */
    String CR_V_H = "cr_v_h";

    /**
     * 订阅视频宽
     */
    String CR_V_W = "cr_v_w";


    /**
     * 音频接收码率
     */
    String CR_A_BR = "cr_a_br";

    /**
     * 屏幕共享接收码率
     */
    String CR_S_BR = "cr_s_br";


    /**
     * 屏幕共享接收帧率
     */
    String CR_S_FPS = "cr_s_fps";

    /**
     * 屏幕共享渲染帧率
     */
    String CR_S_REND_FPS = "cr_s_rend_fps";

    /**
     * 屏幕共享视频高
     */
    String CR_S_H = "cr_s_h";

    /**
     * 屏幕共享视频宽
     */
    String CR_S_W = "cr_s_w";

    /**
     * 视频发送码率
     */
    String CS_V_BR = "cs_v_br";

    /**
     * 视频发送宽
     */
    String CS_V_W = "cs_v_w";

    /**
     * 视频发送高
     */
    String CS_V_H = "cs_v_h";

    /**
     * 发送带宽
     */
    String SU_BW = "su_bw";

    /**
     * 接收带宽
     */
    String SD_BW = "sd_bw";

    /**
     * 上行丢包率
     */
    String SU_LOSS = "su_loss";

    /**
     * 发送帧率
     */
    String CS_V_FPS = "cs_v_fps";

    /**
     * 采集帧率
     */
    String CS_V_CAP_FPS = "cs_v_cap_fps";

    /**
     * 音频发送码率
     */
    String CS_A_BR = "cs_a_br";

    /**
     * 音频采集音量
     */
    String CS_A_VOL = "cs_a_vol";

    /**
     * 屏幕共享码率
     */
    String CS_S_BR = "cs_s_br";

    /**
     * 发送帧率
     */
    String CS_S_FPS = "cs_s_fps";
    /**
     * 采集帧率
     */
    String CS_S_CAP_FPS = "cs_s_cap_fps";

    /**
     * 优质传输率标准
     */
    Integer SD_LOSS_STANDARD = 5;

    /**
     * 卡顿率计算标准
     */
    Integer MOS_STANDARD = 250;

    /**
     * 成员ID
     */
    String TKO_ACCOUNT_ID = "tko_account_id";


    /**
     * 加入房间事件码
     */
    String EVENT = "event";

    Integer JOIN_SUCCESS = 1;

    Integer JOIN_FAIL = 3;

    String ACCOUNT_ID_PREFIX = "[username:";

    String CD_ACCOUNT_ID_PREFIX = "[username:delivery";

    String DATA_DELIMITER = "_";

}
