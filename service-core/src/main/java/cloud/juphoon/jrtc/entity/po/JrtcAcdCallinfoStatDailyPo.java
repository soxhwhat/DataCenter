package cloud.juphoon.jrtc.entity.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 话务运营日报表
 * @author Yuan
 */
@Getter
@Setter
@ToString
@TableName("jrtc_acd_callinfo_stat_daily")
public class JrtcAcdCallinfoStatDailyPo extends JrtcAcdCommonPo {

    /**
    * 技能组
    */
    private String skill;

    /**
    * 事件类型
    */
    private Short eventType;

    /**
    * 事件编号
    */
    private Short eventNum;

    /**
    * 事件结束类型
    */
    private Short endType;
}