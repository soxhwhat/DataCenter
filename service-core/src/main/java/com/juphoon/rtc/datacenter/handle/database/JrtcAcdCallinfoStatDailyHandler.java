package com.juphoon.rtc.datacenter.handle.database;

import com.juphoon.rtc.datacenter.api.EventType;
import com.juphoon.rtc.datacenter.constant.JrtcDataCenterConstant;
import com.juphoon.rtc.datacenter.mq.mapper.JrtcAcdCallinfoStatDailyMapper;
import com.juphoon.rtc.datacenter.entity.po.JrtcAcdCallinfoStatDailyPo;
import com.juphoon.rtc.datacenter.entity.po.JrtcAcdCommonPo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * <p>jrtc_acd_callinfo_stat_daily表的handler类</p>
 * <p>描述请遵循 javadoc 规范</p>
 * <p>TODO</p>
 *
 * @author wenjun.yuan@juphoon.com
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 * @since 2022/3/10 11:49
 */
@Slf4j
@Component(JrtcDataCenterConstant.CALLINFO_STAT_DAILY)
public class JrtcAcdCallinfoStatDailyHandler extends AbstractDatabaseHandler {

    @Autowired
    private JrtcAcdCallinfoStatDailyMapper jrtcAcdCallinfoStatDailyMapper;

    @Override
    public JrtcAcdCallinfoStatDailyPo buildJrtcAcdCommonPo(Map<String, Object> params) {
        JrtcAcdCallinfoStatDailyPo statDailyPo = new JrtcAcdCallinfoStatDailyPo();
        statDailyPo.setAppId((Integer) params.get("appId"));
        statDailyPo.setDomainId((Integer) params.get("domainId"));
        statDailyPo.setCnt(1);
        statDailyPo.setDuration((Long) params.get("duration"));
        statDailyPo.setEndType((Short) params.get("endType"));
        statDailyPo.setEventNum((Short) params.get("eventNum"));
        statDailyPo.setEventType((Short) params.get("eventType"));
        statDailyPo.setSkill((String) params.get("skill"));
        statDailyPo.setStatTime((Long) params.get("statTime"));
        int hashCode = new StringBuilder(statDailyPo.getStatTime()+"|").append(statDailyPo.getSkill()).append("|")
                .append(statDailyPo.getEventType()).append("|").append(statDailyPo.getEventNum()).append("|")
                .append(statDailyPo.getEndType()).append("|").append(statDailyPo.getDomainId()).append("|")
                .append(statDailyPo.getAppId()).toString().hashCode();
        statDailyPo.setUniqueHashCode(hashCode);
        return statDailyPo;
    }

    @Override
    public List<EventType> careEvents() {
        return Arrays.asList(EventType.TICKER_STATUS_WAIT, EventType.TICKER_STATUS_RING);
    }

    @Override
    JrtcAcdCommonPo selectByUnique(JrtcAcdCommonPo commonPo) {
        // TODO 加个缓存，先从缓存中查询
        return jrtcAcdCallinfoStatDailyMapper.selectByCondition(JrtcAcdCallinfoStatDailyPo::getUniqueHashCode,
                commonPo.getUniqueHashCode());
    }

    @Override
    int insertSelective(JrtcAcdCommonPo commonPo) {
        return jrtcAcdCallinfoStatDailyMapper.insertSelective((JrtcAcdCallinfoStatDailyPo) commonPo);
    }

    @Override
    int updateByUniqueHashCode(Integer uniqueHashCode, Long duration, Integer cnt) {
        return jrtcAcdCallinfoStatDailyMapper.updateAddValueByUniqueHashCode("jrtc_acd_callinfo_stat_daily", uniqueHashCode, duration, cnt);
    }
}
