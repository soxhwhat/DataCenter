package cloud.juphoon.jrtc.handler;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: Zhiwei.zhai
 * @Date: 2022/2/16 21:54
 * @Description:
 */
public interface AbstractHandle<T> {

    public List<String> careClz = new ArrayList<>();

    /**
     * 实际运行代码
     * @param t
     */
    public void run(T t);
}
