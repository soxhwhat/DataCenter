package cloud.juphoon.jrtc.entity.po;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@TableName("jrtc_acd_callinfo_stat_ext_terminal_daily")
public class JrtcAcdCallinfoStatExtTerminalDailyPo extends JrtcAcdCommonPo {

    /**
    * 技能组
    */
    private String skill;

    /**
    * 终端类型(H5/小程序/安卓)
    */
    private Byte terminal;
}