package com.juphoon.rtc.datacenter.scheduled;

import com.juphoon.rtc.datacenter.entity.notice.UserKeepAlive;
import com.juphoon.rtc.datacenter.property.NoticeProperties;
import com.juphoon.rtc.datacenter.service.CacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.juphoon.rtc.datacenter.JrtcDataCenterConstant.KEY_AGREE;
import static com.juphoon.rtc.datacenter.JrtcDataCenterConstant.URL_PARAMS;

/**
 * <p>定时清理过期用户缓存</p>
 * TODO TODO TODO 赞同通知，这里的逻辑需要重新考虑，这里只考虑异步通知
 * @author ke.wang@juphoon.com
 * @date 2022/3/24 18:06
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@EnableScheduling
@Component
@Slf4j
public class UserClearTask {
    @Autowired
    CacheService cacheService;

    @Autowired
    NoticeProperties noticeProperties;

    @Autowired
    RestTemplate restTemplate;

    @Scheduled(cron = "0/10 * * * * ? ")
    public void runTask() {
        List<String> removeUri = new ArrayList<>();

        List<Object> list = cacheService.hGetList(KEY_AGREE);
        list.forEach(obj -> {
            UserKeepAlive keepAlive = (UserKeepAlive) obj;
            if (System.currentTimeMillis() - keepAlive.getKeepAliveTime() > noticeProperties.getKeepAliveMillis()) {
                removeUri.add(keepAlive.getUserid());
            }
        });
        removeUri.forEach(uri -> {
            log.info("timeout: {}", uri);
            Object obj = cacheService.hGet(KEY_AGREE, uri);
            UserKeepAlive keepAlive = (UserKeepAlive) obj;
            Map<String, String> map = new HashMap<>(4);
            map.put("appguid", noticeProperties.getAppguid());
            map.put("userid", keepAlive.getUserid());
            map.put("username", keepAlive.getUserid());
            map.put("errorcode", "100");
            HttpEntity<Map> httpEntity = restTemplate.postForEntity("/userlogout_notify" + URL_PARAMS, map, Map.class);
            log.info("keepAlive timeout:{}", httpEntity);
            cacheService.hRemove(KEY_AGREE, uri);
        });


    }

}
