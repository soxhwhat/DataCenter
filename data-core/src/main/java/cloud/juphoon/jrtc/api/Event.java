package cloud.juphoon.jrtc.api;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
import java.util.Map;

/**
 * 
 * @Author: Zhiwei.zhai
 * @Date: 2022/2/10 18:17
 * @Description:
 */
@Getter
@Setter
@ToString
public class Event {


    /**
     * 消息体
     */
    private Integer type;

    private Integer number;

    private Map<String, Object> params;


    public EventType getEventType() {
        return new EventType(type, number);
    }
}
