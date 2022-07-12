package com.juphoon.rtc.datacenter.entity.po.thea;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.juphoon.rtc.datacenter.api.Event;
import com.juphoon.rtc.datacenter.api.EventContext;
import com.juphoon.rtc.datacenter.api.StateContext;
import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import lombok.Getter;
import lombok.SneakyThrows;
import lombok.ToString;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.security.InvalidParameterException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>天赛通用字段</p>
 * <p>TODO</p>
 *
 * @author Jiahui.Huang
 * @date 2022/7/7 13:53
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Getter
@ToString
@SuppressFBWarnings(value = {"EI_EXPOSE_REP", "EI_EXPOSE_REP2"},
        justification = "I prefer to suppress these FindBugs warnings")
public class TheaCommonPO implements Serializable {

    @TableId(type = IdType.AUTO)
    private String id;


    /**
     * 域ID
     */
    private Integer domainId;

    /**
     * 应用ID
     */
    private Integer appId;


    public void setId(String id) {
        this.id = id;
    }


    public void setDomainId(int domainId) {
        checkParam(domainId, "domainId");
        this.domainId = domainId;
    }

    public void setAppId(Integer appId) {
        checkParam(appId, "appId");
        this.appId = appId;
    }


    private void checkParam(Integer value, String fieldName) {
        if (value == null || value < 0) {
            throwException(fieldName + "[" + value + "] is null or < 0");
        }

    }

    public static void commonCheckParam(Event event) {

        if(MapUtils.isEmpty(event.getParams())) {
            throwException("params[" + event.getParams() + "] is empty");
        }

        if(MapUtils.isEmpty(event.getBody())) {
            throwException("body[" + event.getBody() + "] is empty");
        }

        if(MapUtils.isEmpty(event.getGeneral())) {
            throwException("general[" + event.getGeneral() + "] is empty");
        }

        if(MapUtils.isEmpty(event.getZmf())) {
            throwException("zmf[" + event.getZmf() + "] is empty");
        }

        if(MapUtils.isEmpty(event.getJsm())) {
            throwException("jsm[" + event.getJsm() + "] is empty");
        }

    }

    protected static void throwException(String message) {
        throw new InvalidParameterException(message);
    }

}
