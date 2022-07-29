package com.juphoon.rtc.datacenter.servicecore.utils;

import com.juphoon.iron.component.utils.response.IronException;
import com.juphoon.rtc.datacenter.datacore.api.Event;
import lombok.SneakyThrows;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.juphoon.iron.common.IronString.isNotEmpty;

/**
 * <p>天赛全量监控数据工具类</p>
 *
 * @author Jiahui.Huang
 * @date 2022/7/13 15:29
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public class TheaUtil {
    public static Map<String, Object> getBody(Event event) {
        Map<String, Object> body = (Map<String, Object>) event.getParams().get("body");
        return event.getParams().get("body") == null ? Collections.EMPTY_MAP : body;
    }


    public static Map<String, Object> getGeneral(Event event) {
        Map<String, Object> general = (Map<String, Object>) getBody(event).get("general");
        return getBody(event).get("general") == null ? Collections.EMPTY_MAP : general;
    }


    public static Map<String, Object> getZmf(Event event) {
        Map<String, Object> zmf = (Map<String, Object>) getBody(event).get("zmf");
        return getBody(event).get("zmf") == null ? Collections.EMPTY_MAP : zmf;
    }

    public static Map<String, Object> getJsm(Event event) {
        Map<String, Object> jsm = (Map<String, Object>) getBody(event).get("jsm");
        return getBody(event).get("jsm") == null ? Collections.EMPTY_MAP : jsm;
    }


    public static List getRecvActors(Event event) {
        List recvActors = (List) getJsm(event).get("recv_actor");
        return getJsm(event).get("recv_actor") == null ? Collections.EMPTY_LIST : recvActors;
    }

    /**
     * 截取 ACCOUNT
     *
     * @param account
     * @return
     * @throws IronException
     */
    @SuppressWarnings("PMD")
    public static String absAccountId(String account) throws IronException {
        //断言判断account是否为空
//        assert isNotEmpty(account);
        if (!account.startsWith("[username:")) {
            return account;
        }

        int start = account.indexOf(":") + 1;
        int end = account.indexOf("@", start);
        return account.substring(start, end);
    }

    @SneakyThrows
    public static List<Map<String, Object>> getCeEventAuds(Event event) {
        List<Map<String, Object>> ceEventAuds = new ArrayList<>();
        getRecvActors(event).forEach(o -> {
            Map<String, Object> map = (Map<String, Object>) o;
            ceEventAuds.add(map);
        });
        return ceEventAuds;
    }
}
