package cloud.juphoon.jrtc.sqlite.mapper;

import cloud.juphoon.jrtc.sqlite.entity.Account;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface AccountMapper extends BaseMapper<Account> {

    void createTable();

    List<Map> selectInnerJoin(@Param("account")String account);

    List<Map> selectA(@Param("account")String account);

    void insertBatch(@Param("accounts")List<Account> accounts);

}
