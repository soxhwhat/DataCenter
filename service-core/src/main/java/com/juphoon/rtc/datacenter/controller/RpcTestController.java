package com.juphoon.rtc.datacenter.controller;

import Common.CallParams;
import Common.ObjectAgent;
import Common.StrStrMap;
import com.juphoon.iron.cube.starter.CubeApplication;
import com.juphoon.iron.cube.starter.CubeUtils;
import com.juphoon.rtc.datacenter.constant.JrtcDataCenterConstant;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

/**
 * <p>在开始处详细描述该类的作用</p>
 * <p>描述请遵循 javadoc 规范</p>
 * <p>TODO</p>
 *
 * @author  ke.wang@juphoon.com
 * @date    2022/3/25 17:17
 * @update  [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@RestController
@Slf4j
@RequestMapping
@ConditionalOnProperty(prefix = "iron.debug", name = "enabled", havingValue = "true")
@SuppressWarnings("PMD")
public class RpcTestController {

    @Autowired
    CubeApplication cubeApplication;

    @GetMapping("testLogin")
    public Object testLogin(int type){
        ObjectAgent agent = cubeApplication.getAgent("#NoticeEvent");
        StrStrMap.Holder outParams = new StrStrMap.Holder();
        CallParams callParams = CubeUtils.newCallParams();
        callParams.setParam("account", "[username:test@100645.cloud.justalk.com]");
        boolean ret = Event.NoticeEventAgent.keepAlive(agent, type, "juphoon", new HashMap<>(0), outParams, callParams);
        if (!ret) {
            return ObjectAgent.getLastReason();
        }
        return outParams.value;
    }
}
