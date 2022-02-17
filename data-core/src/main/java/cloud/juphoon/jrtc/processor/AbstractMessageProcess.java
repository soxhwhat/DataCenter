package cloud.juphoon.jrtc.processor;

import cloud.juphoon.jrtc.accepter.EventContent;
import cloud.juphoon.jrtc.handler.AbstractHandle;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * @Author: Zhiwei.zhai
 * @Date: 2022/2/9 10:39
 * @Description:
 */
@Slf4j
@Setter
@Getter
public abstract class AbstractMessageProcess<T extends EventContent> implements IMessageProcess<T> {

    /**
     * 注册需要关注的类型
     * 只有关注的类型才会被当前process消费
     * 由service模块注入
     */
    private HashSet<String> careClz = new HashSet<>();

    /**
     * 由service模块注入
     */
    private List<AbstractHandle> handles = new ArrayList<>();

    /**
     * 目前设计中 route会直接把消息在所有的process中遍历一遍
     * 所以需要判断泛型对象中的msg对象是否是当前process关注的类型
     * 1、需要判断泛型对象中的msg对象是否是当前process关注的类型
     * 2、如果判断成功需要 推送到消息队列中 遍历执行handle
     * 3、如果判断失败则直接返回
     * @param t
     */
    @Override
    public abstract void process(T t);



}
