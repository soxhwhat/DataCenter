package com.juphoon.rtc.datacenter.sqlite.service;

import com.juphoon.rtc.datacenter.sqlite.mapper.MyBatisAccountMapper;
import com.juphoon.rtc.datacenter.sqlite.task.InsertBatchTask;
import com.juphoon.rtc.datacenter.sqlite.task.InsertTask;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author: Zhiwei.zhai
 * @Date: 2022/2/21 17:49
 * @Description:
 */
@Service
@Slf4j
public class TestService {

    @Autowired
    SqlService sqlService;

    @Autowired
    MyBatisAccountMapper accountMapper;

    ExecutorService executorService = Executors.newFixedThreadPool(1);

    /**
     * 单个任务单独插入
     */
    @SneakyThrows
    public void insertBatch(){
        List<InsertTask> list = new ArrayList<>();
        for (int i = 0 ; i < 10000 ; i++) {
            list.add(new InsertTask(i,sqlService));
        }
        long startTimeStamp = System.currentTimeMillis();
        executorService.invokeAll(list);
        long endTimeStamp = System.currentTimeMillis();
        log.error("startTime:{},endTime:{},druation:{}",startTimeStamp,endTimeStamp,endTimeStamp-startTimeStamp);
    }

    /**
     * 单个任务批量插入
     */
    @SneakyThrows
    public void insertBatch2(){
        List<InsertBatchTask> list = new ArrayList<>();
        //一次执行5条
        int imax = 2000;
        int jmax = 5;
        for (int i = 0 ; i < imax ; i++) {
            List<Integer> is = new ArrayList<>();
            for(int j = 0 ; j < jmax ; j++) {
                is.add(i*jmax+j);
            }
            list.add(new InsertBatchTask(is, sqlService));
        }
        //一次执行10000条
//        List<Integer> is = new ArrayList<>();
//        for (int i = 0 ; i < 10000 ; i++) {
//            is.add(i);
//        }
//        list.add(new InsertBatchTask(is, sqlService));
        long startTimeStamp = System.currentTimeMillis();
        executorService.invokeAll(list);
        long endTimeStamp = System.currentTimeMillis();
        log.info("startTime:{},endTime:{},druation:{}",startTimeStamp,endTimeStamp,endTimeStamp-startTimeStamp);
    }

    public void insertForEach() {
        for (int i = 0 ; i < 1000 ; i++) {
            InsertTask insertTask = new InsertTask(i, sqlService);
            insertTask.call();
        }
    }

    public void selectInnerJoin(String account) {
        long startTimeStamp = System.currentTimeMillis();
        accountMapper.selectInnerJoin(account);
        long endTimeStamp = System.currentTimeMillis();
        log.error("startTime:{},endTime:{},druation:{}",startTimeStamp,endTimeStamp,endTimeStamp-startTimeStamp);
    }

    public void selectA(String account) {
        long startTimeStamp = System.currentTimeMillis();
        accountMapper.selectA(account);
        long endTimeStamp = System.currentTimeMillis();
        log.error("startTime:{},endTime:{},druation:{}",startTimeStamp,endTimeStamp,endTimeStamp-startTimeStamp);
    }

    @Transactional(readOnly = true)
    public void deleteByAccount(String account) {
        long startTimeStamp = System.currentTimeMillis();
        accountMapper.deleteByAccount(account);
        long endTimeStamp = System.currentTimeMillis();
        log.error("startTime:{},endTime:{},druation:{}",startTimeStamp,endTimeStamp,endTimeStamp-startTimeStamp);
    }

    public void deleteById(String id) {
        long startTimeStamp = System.currentTimeMillis();
        accountMapper.deleteById(id);
        long endTimeStamp = System.currentTimeMillis();
        log.error("startTime:{},endTime:{},druation:{}",startTimeStamp,endTimeStamp,endTimeStamp-startTimeStamp);
    }
}
