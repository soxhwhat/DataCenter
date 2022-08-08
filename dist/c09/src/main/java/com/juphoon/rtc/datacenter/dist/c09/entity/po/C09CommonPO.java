package com.juphoon.rtc.datacenter.dist.c09.entity.po;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.juphoon.rtc.datacenter.datacore.api.Event;
import com.juphoon.rtc.datacenter.servicecore.handle.mongo.entity.MongoEventPO;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.mongodb.core.index.Indexed;

import java.io.Serializable;
import java.security.InvalidParameterException;
import java.util.Map;
import java.util.StringJoiner;

/**
 * <p>宁波银行外呼push需求汇总通用字段</p>
 *
 * @author Jiahui.Huang
 * @date 2022-08-02
 */
@Getter
@Setter
@ToString
@SuppressFBWarnings(value = {"EI_EXPOSE_REP", "EI_EXPOSE_REP2"},
        justification = "I prefer to suppress these FindBugs warnings")
public class C09CommonPO implements Serializable {

    /**
     * 域ID
     */
    private Integer domainId;

    /**
     * 应用ID
     */
    private Integer appId;

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

    /**
     * 事件总次数
     */
    private Integer cnt = 1;

    /**
     * 计算出的值，确定一条数据
     */
    private String uniqueKey;

    public void fromEvent(Event event) throws JsonProcessingException {
        this.setDomainId(event.getDomainId());
        this.setAppId(event.getAppId());
        this.setType(event.getType());
        this.setNumber(event.getNumber());
        this.setTimestamp(event.getTimestamp());
        this.setParams(event.getParams());
        this.setCnt(cnt);
        this.setUniqueKey(uniqueKey);
    }


}