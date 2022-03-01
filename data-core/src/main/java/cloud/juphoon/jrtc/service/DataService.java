package cloud.juphoon.jrtc.service;

import cloud.juphoon.jrtc.api.EventContext;
import cloud.juphoon.jrtc.api.IService;
import cloud.juphoon.jrtc.processor.IEventProcessor;
import lombok.ToString;

import java.util.List;

/**
 * @Author: Zhiwei.zhai
 * @Date: 2022/2/16 17:59
 * @Description:
 */
@ToString
public class DataService implements IService {

    private List<IEventProcessor> processors;

    public DataService(List<IEventProcessor> processors) {
        this.processors = processors;
    }

    @Override
    public void start() {

    }

    /**
     * 提交任务
     *
     * @param ec
     * @throws Exception
     */
    public void commit(EventContext ec) {
        for (IEventProcessor processor : processors) {
            processor.process(ec);
        }
    }

}
