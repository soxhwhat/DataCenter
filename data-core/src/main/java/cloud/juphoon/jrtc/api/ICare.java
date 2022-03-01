package cloud.juphoon.jrtc.api;

import java.util.*;

/**
 * <p>在开始处详细描述该类的作用</p>
 * <p>描述请遵循 javadoc 规范</p>
 * <p>TODO</p>
 *
 * @author  ajian.zheng@juphoon.com
 * @date    2/17/22 5:26 PM
 * @update  [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */ 
public interface ICare {

    Set<EventType> careEvents = new TreeSet<EventType>((o1,o2) ->
         o1.getNumber().intValue() == o2.getNumber().intValue()
                && o1.getType().intValue() == o2.getType().intValue() ? 1 : -1
    );

    /**
     * 是否全部关注
     * @return
     */
    default boolean isAllCare(){
        return false;
    };

    /**
     * 插入单个
     * @param eventType
     */
    default void addCare(EventType eventType){
        careEvents.add(eventType);
    };

    /**
     * 插入多个
     * @param eventTypes
     */
    default void addAllCare(List<EventType> eventTypes){
        careEvents.addAll(eventTypes);
    };
    /**
     * 是否关心
     *
     * @param event
     * @return
     */
    default boolean care(Event event) {
        assert careEvents != null : "关注事件列表必须为非空";
        return careEvents.contains(event.getEventType());
    }

    /**
     * 关注事件列表
     *
     * @return
     */
    default List<EventType> careEvents(){
        return new ArrayList(careEvents);
    };
}
