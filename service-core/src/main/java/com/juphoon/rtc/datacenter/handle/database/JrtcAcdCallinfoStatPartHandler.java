package com.juphoon.rtc.datacenter.handle.database;

import com.juphoon.rtc.datacenter.api.EventContext;
import com.juphoon.rtc.datacenter.api.EventType;
import com.juphoon.rtc.datacenter.api.StatType;
import com.juphoon.rtc.datacenter.constant.JrtcDataCenterConstant;
import com.juphoon.rtc.datacenter.entity.po.JrtcAcdCallinfoStatPartPo;
import com.juphoon.rtc.datacenter.entity.po.JrtcAcdCommonPo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.juphoon.rtc.datacenter.api.EventType.*;

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

    private static List<StatType> statTypes;

    //调整为可配置
    static {
        statTypes = new ArrayList<>(2);
        // 15分钟
        statTypes.add(new StatType(1, 15 * 60 * 1000));
        // 1小时
        statTypes.add(new StatType(3, 60 * 60 * 1000));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean handle(EventContext ec) {
        Map<String, Object> params = ec.getEvent().getParams();
        JrtcAcdCallinfoStatPartPo statPartPo = buildJrtcAcdCommonPo(params);
        Long beginTimestamp = (Long) params.get("beginTimestamp");
        Long endTimestamp = (Long) params.get("endTimestamp");
        statTypes.forEach(one -> {
            List<Map<String, Long>> list = splitStatTime(one.getInterval(), beginTimestamp, endTimestamp);
            list.forEach(map -> {
                statPartPo.setStatType((byte) one.getStatType());
                statPartPo.setStatTime(map.get("statTime"));
                statPartPo.setDuration(map.get("duration"));
                int hashCode = new StringBuilder(statPartPo.getStatTime()+"|").append(statPartPo.getStatType())
                        .append("|").append(statPartPo.getSkill()).append("|").append(statPartPo.getEventType())
                        .append("|").append(statPartPo.getEventNum()).append("|").append(statPartPo.getEndType())
                        .append("|").append(statPartPo.getDomainId()).append("|").append(statPartPo.getAppId())
                        .toString().hashCode();
                statPartPo.setUniqueHashCode(hashCode);
                super.handle(statPartPo);
            });
        });
        return true;
    }

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
        return statPartPo;
    }

    @Override
    public List<EventType> careEvents() {
        // TODO 待定事件：转接，邀请坐席
        return Arrays.asList(TICKER_STATUS_WAIT, TICKER_STATUS_RING, TICKER_STATUS_TALK, TICKER_STATUS_OVERFLOW);
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
