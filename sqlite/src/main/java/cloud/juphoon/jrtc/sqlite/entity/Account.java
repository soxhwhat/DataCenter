package cloud.juphoon.jrtc.sqlite.entity;

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

    private Integer id;
    private Integer account;
}
