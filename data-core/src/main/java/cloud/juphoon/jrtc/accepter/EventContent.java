package cloud.juphoon.jrtc.accepter;

import lombok.Data;

import java.util.List;

/**
 * 
 * @Author: Zhiwei.zhai
 * @Date: 2022/2/10 18:17
 * @Description:
 */
@Data
public class EventContent {


    /**
     * 消息体
     */
    private Object msg;

    /**
     * 重做handle的Class列表
     */
    private List<String> redoClzList;

}
