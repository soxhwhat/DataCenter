package com.juphoon.rtc.datacenter.sqlite.service;

import com.juphoon.rtc.datacenter.sqlite.mapper.MyBatisAccountMapper;
import com.juphoon.rtc.datacenter.sqlite.entity.Account;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Author: Zhiwei.zhai
 * @Date: 2022/2/21 21:53
 * @Description:
 */
@Service
@Slf4j
public class SqlService {

    @Autowired
    private MyBatisAccountMapper accountMapper;

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void insert(Account account){
        accountMapper.insert(account);
    }

    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void insertBatch(List<Account> accounts){

        accountMapper.insertBatch(accounts);
    }
}
