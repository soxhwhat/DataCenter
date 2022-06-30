package com.juphoon.rtc.datacenter.servicecore.handle.mongo.entity;

import com.juphoon.rtc.datacenter.datacore.api.Event;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Map;
import java.util.StringJoiner;

/**
 * <p>在开始处详细描述该类的作用</p>
 * <p>描述请遵循 javadoc 规范</p>
 * <p>TODO</p>
 *
 * @author ajian.zheng@juphoon.com
 * @date 6/13/22 8:26 PM
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Document
@CompoundIndexes({
        @CompoundIndex(name = "event_time_type_num_domain_idx",
                def = "{'timestamp':-1,'type':1,'number':1,'domainId':1}")
})
@Getter
@Setter
public class MongoEventPO {
    /**
     * 域ID
     */
    private Integer domainId;

    /**
     * 应用ID
     */
    private Integer appId;

    /**
     * 事件唯一ID
     */
    private String uuid;

    /**
     * 事件类型
     */
    private Integer type;

    /**
     * 事件编号
     */
    private Integer number;

    /**
     * 时间戳
     */
    @Indexed
    private Long timestamp;

    /**
     * 其他参数
     */
    private Map<String, Object> params;

    public void fromEvent(Event event) {
        this.setDomainId(event.getDomainId());
        this.setAppId(event.getAppId());
        this.setUuid(event.getUuid());
        this.setType(event.getType());
        this.setNumber(event.getNumber());
        this.setTimestamp(event.getTimestamp());
        this.setParams(event.getParams());
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", MongoEventPO.class.getSimpleName() + "[", "]")
                .add("domainId=" + domainId)
                .add("appId=" + appId)
                .add("uuid='" + uuid + "'")
                .add("type=" + type)
                .add("number=" + number)
                .add("timestamp=" + timestamp)
                .add("params=" + params)
                .toString();
    }
}
