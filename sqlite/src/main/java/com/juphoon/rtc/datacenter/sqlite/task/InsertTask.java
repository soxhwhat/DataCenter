package com.juphoon.rtc.datacenter.sqlite.task;

import com.juphoon.rtc.datacenter.sqlite.entity.Account;
import com.juphoon.rtc.datacenter.sqlite.service.SqlService;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;

@Slf4j
public class InsertTask implements Callable<Object> {

        int i;
        SqlService sqlService;

        public InsertTask(int i, SqlService sqlService){
            this.i = i;
            this.sqlService = sqlService;
        }

        @Override
        public Object call() {
            try {
                Account account = new Account();
                account.setId(i);
                account.setAccount(i);
                log.info("insert begin--------:{}",i);
                sqlService.insert(account);
                log.info("insert end--------:{}",i);
            } catch (Exception e){
                log.error("insert error-------{}",i);
                e.printStackTrace();
            }

            return null;
        }
    }