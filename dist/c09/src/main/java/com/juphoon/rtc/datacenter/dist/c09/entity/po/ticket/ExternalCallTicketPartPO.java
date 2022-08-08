package com.juphoon.rtc.datacenter.dist.c09.entity.po.ticket;

import com.juphoon.rtc.datacenter.dist.c09.entity.po.push.ExternalCallPushPO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * <p>宁波客服话单时段报表</p>
 *
 * @author Jiahui.Huang
 * @date 2022-08-02
 */
@Getter
@Setter
@ToString
public class ExternalCallTicketPartPO extends ExternalCallTicketPO {

    /**
     * 汇总类型(15分钟、1小时等)
     */
    private Byte statType;

    @Override
    public String getUniqueKey() {
        if (null == super.getUniqueKey()) {
            String str = getAppId() + "|" + getDomainId() +
                    "|" + getType() + "|" + getNumber() +
                    "|" + getStatType();
            super.setUniqueKey(DigestUtils.md5Hex(str));
        }
        return super.getUniqueKey();
    }
}