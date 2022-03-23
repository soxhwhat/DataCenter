package com.juphoon.rtc.datacenter.mq.mapper;

import com.juphoon.rtc.datacenter.api.Account;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
@DS("mysql")
public interface AccountMapper extends BaseMapper<Account> {
}
