package com.juphoon.rtc.datacenter.event.storage.sqllite.mapper;

import com.juphoon.rtc.datacenter.api.Account;
import com.baomidou.dynamic.datasource.annotation.DS;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author zhiwei.zhai
 * @data 2022-02-24
 */
@Mapper
@DS("mysql")
public interface AccountMapper extends BaseMapper<Account> {
}
