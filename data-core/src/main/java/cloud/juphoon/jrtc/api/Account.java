package cloud.juphoon.jrtc.api;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Author: Zhiwei.zhai
 * @Date: 2022/2/18 21:44
 * @Description:
 */
@Data
@TableName("t_account")
public class Account {

    @TableId
    private Integer id;
    private Integer account;
}
