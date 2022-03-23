package com.juphoon.rtc.datacenter.sqlite;

import com.juphoon.rtc.datacenter.sqlite.entity.Account;
import com.juphoon.rtc.datacenter.sqlite.mapper.BaseAccountMapper;
import com.juphoon.rtc.datacenter.sqlite.task.InsertTask;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: Zhiwei.zhai
 * @Date: 2022/2/18 21:50
 * @Description:
 */
@SpringBootTest(classes = SqliteApplication.class)
@Slf4j
public class SqliteTest {

    @Autowired
    private BaseAccountMapper accountMapper;

    @Test
    public void init(){
        accountMapper.createTable();
    }
    @Test
    public void selectCount(){
        Integer integer = accountMapper.selectCount();
        log.info(integer.toString());
    }
    @Test
    public void cleanUp(){
        accountMapper.deleteAll();
    }

    @Test
    public void insert(){
        Account account = new Account();
        account.setAccount(11);
        accountMapper.insert(account);
        Assert.isTrue(true,"");
    }

    @SneakyThrows
    @Test
    @Transactional
    public void insertBatch(){
        ExecutorService executorService = Executors.newCachedThreadPool();
        List<InsertTask> list = new ArrayList<>();
        for (int i = 0 ; i < 1000 ; i++) {
//            list.add(new InsertTask(i,));
        }
        executorService.invokeAll(list);
    }


}
