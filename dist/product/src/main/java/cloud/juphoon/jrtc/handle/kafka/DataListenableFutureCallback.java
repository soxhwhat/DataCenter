package cloud.juphoon.jrtc.handle.kafka;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.concurrent.ListenableFutureCallback;

/**
 * kafka回调类
 * @author dp
 * @date: 2021/11/6 6:50 下午
 */
public class DataListenableFutureCallback implements ListenableFutureCallback {

    private Logger log = LoggerFactory.getLogger("kafka");

    @Override
    public void onFailure(Throwable ex) {
        log.info("kafka发送失败  ex:{}" , ex);
    }


    @Override
    public void onSuccess(Object result) {
        log.info("kafka发送成功  result:{}" , result);
    }
}
