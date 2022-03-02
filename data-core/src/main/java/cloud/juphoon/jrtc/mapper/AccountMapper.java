package cloud.juphoon.jrtc.mapper;

import cloud.juphoon.jrtc.api.Account;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@DS("mysql")
public interface AccountMapper extends BaseMapper<Account> {
}
