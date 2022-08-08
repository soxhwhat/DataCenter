package com.juphoon.rtc.datacenter.dist.c09.handle.push;


import com.juphoon.rtc.datacenter.datacore.api.HandlerId;
import com.juphoon.rtc.datacenter.datacore.api.StatType;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import static com.juphoon.rtc.datacenter.datacore.JrtcDataCenterConstant.MONGO_TEMPLATE_EVENT;

/**
 * <p>宁波外呼push事件处理handler(1hour)</p>
 *
 * @author Jiahui.Huang
 * @date 2022-08-02
 * @description 用于统计宁波外呼push成功率
 */
@Slf4j
@Component
@Setter
public class ExternalCallPushPartHourHandler extends AbstractExternalCallPushPartHandler {

    @Override
    public HandlerId handlerId() {
        return HandlerId.ExternalCallPushPartHourHandler;
    }

    @Override
    public StatType statType() {
        return StatType.STAT_HOUR;
    }

}
