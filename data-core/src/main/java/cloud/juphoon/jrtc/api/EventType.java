package cloud.juphoon.jrtc.api;

import lombok.Data;

import java.util.Objects;

/**
 *
 * @Author: Zhiwei.zhai
 * @Date: 2022/2/10 18:17
 * @Description:
 */
@Data
public class EventType {
    public static final EventType TICKET_EVENT = new EventType(0,0);

    /**
     * 验证码
     */
    public static final EventType VERIFY_CODE = new EventType(1, 1);

    /**
     * 验证加入房间
     */
    public static final EventType VERIFY_JOIN = new EventType(1, 2);

    /**
     * 房间加入/离开事件
     */
    public static final EventType ROOM_NOTICE = new EventType(1, 3);

    /**
     * 拍照事件
     */
    public static final EventType SNAPSHOT_NOTICE = new EventType(1, 4);

    /**
     * 登出事件
     */
    public static final EventType LOGOUT_EVENT = new EventType(98, 98);

    /**
     * 登录事件
     */
    public static final EventType LOGIN_EVENT = new EventType(99, 99);

    //***************************************************************************************************
    //********************************** 客服事件-排队机 ************************************************
    //***************************************************************************************************
    /**
     * 排队
     */
    public static final EventType TICKER_STATUS_WAIT = new EventType(10,0);
    /**
     * 振铃
     */
    public static final EventType TICKER_STATUS_RING = new EventType(10,1);
    /**
     * 通话
     */
    public static final EventType TICKER_STATUS_TALK = new EventType(10,2);
    /**
     * 溢出
     */
    public static final EventType TICKER_STATUS_OVERFLOW = new EventType(10,3);
    /**
     * 转接
     */
    public static final EventType TICKER_STATUS_TRANSFER = new EventType(10,4);
    /**
     * 邀请坐席
     */
    public static final EventType TICKER_STATUS_INVITE_AGENT = new EventType(10,5);

    //***************************************************************************************************
    //********************************** 客服事件-坐席 ************************************************
    //***************************************************************************************************
    /**
     * 坐席示忙
     */
    public static final EventType STAFF_STATUS_BUSY = new EventType(10,10);
    /**
     * 坐席示闲
     */
    public static final EventType STAFF_STATUS_FREE = new EventType(10,11);
    /**
     * 坐席保持
     */
    public static final EventType STAFF_STATUS_KEEP = new EventType(10,12);
    /**
     * 坐席登录
     */
    public static final EventType STAFF_STATUS_LOGIN = new EventType(10,13);

    //***************************************************************************************************
    //********************************** 客服话单事件 ***************************************************
    //***************************************************************************************************
    /**
     * 话单（整个话单真正结束时）
     */
    public static final EventType TICKER_COMPLETE = new EventType(11,1);


    private Integer type;

    private Integer number;

    public EventType(Integer type, Integer number) {
        this.type = type;
        this.number = number;
    }

    // TODO 修正
    @Override
    public boolean equals(Object o) {
        if (this == o) {return true;}
        if (!(o instanceof EventType)){ return false;}
        EventType eventType = (EventType) o;
        return Objects.equals(type, eventType.type) &&
                Objects.equals(number, eventType.number);
    }

    // TODO 修正
    @Override
    public int hashCode() {
        return Objects.hash(type, number);
    }

    public enum TYPE {

    }
}
