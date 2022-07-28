package com.juphoon.rtc.datacenter.dist.h20.handler;

import com.juphoon.iron.component.utils.IronJsonUtils;
import com.juphoon.rtc.datacenter.datacore.api.EventContext;
import com.juphoon.rtc.datacenter.datacore.api.EventType;
import com.juphoon.rtc.datacenter.datacore.api.HandlerId;
import com.juphoon.rtc.datacenter.dist.h20.entity.KafkaTicketPo;
import com.juphoon.rtc.datacenter.servicecore.handle.kafka.AbstractKafkaHandler;
import com.juphoon.rtc.datacenter.servicecore.service.impl.RedisServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.KafkaException;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;

import static com.juphoon.rtc.datacenter.datacore.api.EventType.*;

/**
 * <p>将排队机话单数据和sip媒体话单数据拼接后推送到kafka</p>
 * <p>描述请遵循 javadoc 规范</p>
 *
 * @author wenjun.yuan@juphoon.com
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 * @since 2022/7/22 17:27
 */
@Slf4j
@Component
public class H20TicketKafkaHandler extends AbstractKafkaHandler {

    @Autowired
    private RedisServiceImpl cacheService;

    @Override
    public List<EventType> careEvents() {
        return Arrays.asList(INNER_TICKET_ACD, INNER_TICKET_SIP);
    }

    @Override
    public HandlerId handlerId() {
        return HandlerId.H20TicketKafkaHandler;
    }

    @Override
    public String getTopic(EventContext ec) {
        return "VOLTE_CALL_TOPIC";
    }

    @Override
    public boolean handle(EventContext ec) {
        try {
            String topic = getTopic(ec);
            Object data = getData(ec);
            if (data == null) {
                return true;
            }
            String json = IronJsonUtils.objectToJson(data);
            kafkaTemplate.send(topic, json).get();
            log.info("KafkaTicketPo -> json[{}]", json);
        } catch (KafkaException kafkaException) {
            return false;
        } catch (ExecutionException e) {
            log.error("kafkaExecutionException:", e);
            return false;
        } catch (InterruptedException e) {
            return false;
        }
        return true;
    }

    @Override
    public KafkaTicketPo getData(EventContext ec) {
        String callId = (String) ec.getEvent().getParams().get("callId");
        Object acd = cacheService.hGet(INNER_TICKET_ACD.name(), callId);
        Object sip = cacheService.hGet(INNER_TICKET_SIP.name(), callId);
        if (acd == null || sip == null) {
            return null;
        } else {
            KafkaTicketPo kafka = (KafkaTicketPo) acd;
            kafka.setDetail(((KafkaTicketPo) sip).getDetail());
            return kafka;
        }
    }

}
