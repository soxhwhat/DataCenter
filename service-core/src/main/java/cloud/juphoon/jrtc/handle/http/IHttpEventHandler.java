package cloud.juphoon.jrtc.handle.http;

import cloud.juphoon.jrtc.api.EventContext;

/**
 * <p>T 封装返回值 R postHandle的返回值 D 执行rest的对象 </p>
 * <p>描述请遵循 javadoc 规范</p>
 * <p>TODO</p>
 *
 * @author ke.wang@juphoon.com
 * @date 2022/3/3 15:02
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public interface IHttpEventHandler<T, R, D> {


    /**
     * <p>收拾好行囊
     * 子类实现  封装请求参数，restTemplate 以及 目标uri等等
     * </p>
     *
     * @param t
     * @return
     * @author ke.wang@juphoon.com
     * @date 2022/3/3
     * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    T preHandle(EventContext t) throws Exception;


    /**
     * <p>奔向远方 </p>
     * 父类实现，执行回调
     *
     * @param t
     * @author ke.wang@juphoon.com
     * @date 2022/3/3
     * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    R postHandle(T t, D restTemplate);


    /**
     * <p>
     * TODO 请求完成后需要做的事
     * </p>
     *
     * @author ke.wang@juphoon.com
     * @date 2022/3/3
     * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
     */
    void afterComplete(R r);


}
