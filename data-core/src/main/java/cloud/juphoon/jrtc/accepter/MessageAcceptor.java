package cloud.juphoon.jrtc.accepter;

/**
 * @Author: Zhiwei.zhai
 * @Date: 2022/2/16 15:00
 * @Description:
 */
public interface MessageAcceptor {

    /**
     * 对接业务接口 统一接收消息
     * 1、把数据统一转换成EventContent对象
     * 2、把EventContent对象放入route模块
     * @param msg 消息体 例如话单中的Event实体
     */
    static void recv(Object msg){

    }
}
