package com.juphoon.rtc.datacenter.cube.service;

import Common.ServerCall;
import com.juphoon.rtc.datacenter.accepter.IEventRouter;
import com.juphoon.rtc.datacenter.api.Event;
import com.juphoon.rtc.datacenter.api.EventContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @Author: Zhiwei.zhai
 * @Date: 2022/5/9 12:19
 * @Description:
 */
@Slf4j
public abstract class AbstractServerProcess<T> {

    @Autowired
    private IEventRouter eventRouter;

    public boolean process(ServerCall serverCall, List<T> data) {
        String magic = binaryToHexString(serverCall.getMagic());
        log.debug("magic:{}", magic);

        boolean ret = true;
        try {
            if (null == data || CollectionUtils.isEmpty(data)) {
                log.warn("empty");
                throw new IllegalArgumentException("listIsEmpty");
            }
            String host = serverCall.getParam("host");
            List<EventContext> eventContexts = trans(data, host, magic);
            eventRouter.router(eventContexts);
        } catch (Exception e) {
            ret = false;
            serverCall.setReason(e.getMessage());
        }

        return ret;
    }

    /**
     * 批量转换为ec，设置magic,host
     *
     * @param from
     * @param host
     * @param magic
     * @return
     * @throws Exception
     */
    public abstract List<EventContext> trans(List<T> from, String host, String magic) throws Exception;

    /**
     * 原数据转换为Event
     *
     * @param from
     * @return
     * @throws Exception
     */
    public abstract Event trans(T from) throws Exception;

    private static final String HEX_STR = "0123456789ABCDEF";

    static String binaryToHexString(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        String hex;
        for (byte aByte : bytes) {
            //字节高4位
            hex = String.valueOf(HEX_STR.charAt((aByte & 0xF0) >> 4));
            //字节低4位
            hex += String.valueOf(HEX_STR.charAt(aByte & 0x0F));
            result.append(hex);
        }
        return result.toString();
    }
}
