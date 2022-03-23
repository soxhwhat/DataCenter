package com.juphoon.rtc.datacenter.handle.database;

import com.juphoon.rtc.datacenter.api.EventType;
import com.juphoon.rtc.datacenter.constant.JrtcDataCenterConstant;
import com.juphoon.rtc.datacenter.entity.po.JrtcAcdCallinfoStatPartPo;
import com.juphoon.rtc.datacenter.entity.po.JrtcAcdCommonPo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * <p>jrtc_acd_callinfo_stat_part表的handler类</p>
 * <p>描述请遵循 javadoc 规范</p>
 * <p>TODO</p>
 *
 * @author wenjun.yuan@juphoon.com
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 * @since 2022/3/10 11:49
 */
@Slf4j
@Component(JrtcDataCenterConstant.CALLINFO_STAT_PART)
public class JrtcAcdCallinfoStatPartHandler extends AbstractDatabaseHandler {

    @Autowired
    private com.juphoon.rtc.datacenter.mq.mapper.JrtcAcdCallinfoStatPartMapper JrtcAcdCallinfoStatPartMapper;

    @Override
    public JrtcAcdCallinfoStatPartPo buildJrtcAcdCommonPo(Map<String, Object> params) {
        JrtcAcdCallinfoStatPartPo statPartPo = new JrtcAcdCallinfoStatPartPo();
        statPartPo.setAppId((Integer) params.get("appId"));
        statPartPo.setDomainId((Integer) params.get("domainId"));
        statPartPo.setCnt(1);
        statPartPo.setDuration((Long) params.get("duration"));
        statPartPo.setEndType((Short) params.get("endType"));
        statPartPo.setEventNum((Short) params.get("eventNum"));
        statPartPo.setEventType((Short) params.get("eventType"));
        statPartPo.setSkill((String) params.get("skill"));
        statPartPo.setStatType((Byte) params.get("statType"));
        statPartPo.setStatTime((Long) params.get("statTime"));
        int hashCode = new StringBuilder(statPartPo.getStatTime()+"|").append(statPartPo.getStatType())
                .append("|").append(statPartPo.getSkill()).append("|").append(statPartPo.getEventType())
                .append("|").append(statPartPo.getEventNum()).append("|").append(statPartPo.getEndType())
                .append("|").append(statPartPo.getDomainId()).append("|").append(statPartPo.getAppId())
                .toString().hashCode();
        statPartPo.setUniqueHashCode(hashCode);
        return statPartPo;
    }

    @Override
    public List<EventType> careEvents() {
        return Arrays.asList(EventType.TICKER_STATUS_WAIT, EventType.TICKER_STATUS_RING);
    }

    @Override
    JrtcAcdCommonPo selectByUnique(JrtcAcdCommonPo commonPo) {
        return JrtcAcdCallinfoStatPartMapper.selectByUniqueCondition(JrtcAcdCallinfoStatPartPo::getUniqueHashCode,
                commonPo.getUniqueHashCode());
    }

    @Override
    int insertSelective(JrtcAcdCommonPo commonPo) {
        // TODO 加个缓存，先从缓存中查询
        return JrtcAcdCallinfoStatPartMapper.insertSelective((JrtcAcdCallinfoStatPartPo) commonPo);
    }

    @Override
    int updateByUniqueHashCode(Integer uniqueHashCode, Long duration, Integer cnt) {
        return JrtcAcdCallinfoStatPartMapper.updateAddValueByUniqueHashCode("jrtc_acd_callinfo_stat_part", uniqueHashCode, duration, cnt);
    }
}
