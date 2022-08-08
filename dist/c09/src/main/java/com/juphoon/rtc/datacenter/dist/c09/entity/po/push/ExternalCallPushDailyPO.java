package com.juphoon.rtc.datacenter.dist.c09.entity.po.push;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * <p>外呼push日汇总表</p>
 *
 * @author Jiahui.Huang
 * @date 2022-08-02
 */
@Getter
@Setter
@ToString
public class ExternalCallPushDailyPO extends ExternalCallPushPO {

    @Override
    public String getUniqueKey() {
        if (null == super.getUniqueKey()) {
            String str = getAppId() + "|" + getDomainId() +
                    "|" + getType() + "|" + getNumber();
            super.setUniqueKey(DigestUtils.md5Hex(str));
        }
        return super.getUniqueKey();
    }

}