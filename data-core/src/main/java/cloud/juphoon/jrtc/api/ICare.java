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

    /**
     * 是否关心
     *
     * @param event
     * @return
     */
    boolean care(Event event) ;

    /**
     * 关注事件列表
     *
     * @return
     */
    List<EventType> careEvents();
}
