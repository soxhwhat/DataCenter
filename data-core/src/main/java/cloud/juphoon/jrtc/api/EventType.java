package cloud.juphoon.jrtc.api;

import java.util.Objects;

/**
 *
 * @Author: Zhiwei.zhai
 * @Date: 2022/2/10 18:17
 * @Description:
 */
public class EventType {
    private Integer type;

    private Integer number;

    public EventType(Integer type, Integer number) {
        this.type = type;
        this.number = number;
    }

    // TODO 修正
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EventType)) return false;
        EventType eventType = (EventType) o;
        return Objects.equals(type, eventType.type) &&
                Objects.equals(number, eventType.number);
    }

    // TODO 修正
    @Override
    public int hashCode() {
        return Objects.hash(type, number);
    }
}
