package test.com.juphoon.rtc.datacenter.datacore;

import com.juphoon.rtc.datacenter.datacore.api.Event;
import com.juphoon.rtc.datacenter.datacore.api.EventContext;

import java.util.HashMap;
import java.util.UUID;

/**
 * <p>测试工具类</p>
 *
 * @author  ajian.zheng@juphoon.com
 * @date    7/22/22 10:36 AM
 */
public class TestUtils {
    public static EventContext randomEventContext() {
        EventContext ec = new EventContext(randomEvent());
        String random = UUID.randomUUID().toString();

        ec.setFrom(random);
        ec.setRequestId(random);
        ec.setProcessorId("test");
        ec.setBeginTimestamp(0L);
        ec.setRetryCount(0);

        return ec;
    }

    public static Event randomEvent() {
        return Event.builder().domainId(100645).appId(0).type(1).number(2).timestamp(System.currentTimeMillis())
                .uuid(UUID.randomUUID().toString()).params(new HashMap<String, Object>() {{
                    put("a", "b");
                    put("b", "b");
                }}).build();
    }
}
