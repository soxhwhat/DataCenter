package com.juphoon.rtc.datacenter.cube.notice;

import Common.ObjectServer;
import com.juphoon.iron.cube.starter.AbstractCubeService;
import com.juphoon.iron.cube.starter.annotation.CubeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * <p>原noticeService rpc接口</p>
 *
 * @author  ke.wang@juphoon.com
 * @date   2022/3/25
 * @update  [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Component
@CubeService(serviceName = "#NoticeEvent")
public class NoticeEventServerManager extends AbstractCubeService {

    @Autowired
    private NoticeEventServerImpl noticeEventServer;

    @Override
    public ObjectServer buildServiceServer() {
        return noticeEventServer;
    }
}
