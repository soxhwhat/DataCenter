package cloud.juphoon.jrtc.api;

import lombok.Getter;
import lombok.Setter;

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
public class Event {


    /**
     * 消息体
     */
    private Integer type;

    private Integer number;

    private Map<String, String> params;


    public EventType getEventType() {
        return new EventType(type, number);
    }
}
