package com.juphoon.rtc.datacenter.entity.po.thea;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.juphoon.rtc.datacenter.api.Event;
import com.juphoon.rtc.datacenter.utils.TheaUtil;
import lombok.Getter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;

import java.io.Serializable;
import java.security.InvalidParameterException;

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
@Slf4j
public class TheaCommonPO implements Serializable {

    /**
     * 域ID
     */
    protected Integer domainId;

    /**
     * 应用ID
     */
    protected Integer appId;

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
            //联调时发现问题
            log.error(new InvalidParameterException("params is empty").getMessage());
        }

        if(MapUtils.isEmpty(TheaUtil.getBody(event))) {
            log.error(new InvalidParameterException("body is empty").getMessage());
        }

        if(MapUtils.isEmpty(TheaUtil.getGeneral(event))) {
            log.error(new InvalidParameterException("general is empty").getMessage());
        }

        if(MapUtils.isEmpty(TheaUtil.getZmf(event))) {
            log.error(new InvalidParameterException("zmf is empty").getMessage());
        }

        if(MapUtils.isEmpty(TheaUtil.getJsm(event))) {
            log.error(new InvalidParameterException("jsm is empty").getMessage());
        }

    }

    protected static void throwException(String message) {
        throw new InvalidParameterException(message);
    }

}
