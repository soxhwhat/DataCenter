package com.juphoon.rtc.datacenter.dist.c09.entity.po.ticket;

import com.juphoon.rtc.datacenter.dist.c09.entity.po.push.ExternalCallPushPO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * <p>宁波客服话单日汇总表</p>
 *
 * @author Jiahui.Huang
 * @date 2022-08-02
 */
@Getter
@Setter
@ToString
public class ExternalCallTicketDailyPO extends ExternalCallTicketPO {

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