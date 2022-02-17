package cloud.juphoon.jrtc.processor;


import cloud.juphoon.jrtc.accepter.EventContent;

/**
 * @Author: Zhiwei.zhai
 * @Date: 2022/2/9 9:44
 * @Description:
 */
public interface IMessageProcess<T extends EventContent> {

    /**
     * 执行
     * @param t
     */
    void process(T t);
}
