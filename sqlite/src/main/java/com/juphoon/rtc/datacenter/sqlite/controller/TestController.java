package com.juphoon.rtc.datacenter.sqlite.controller;

import com.juphoon.rtc.datacenter.sqlite.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: Zhiwei.zhai
 * @Date: 2022/2/21 17:49
 * @Description:
 */
@RestController
@RequestMapping("/test")
public class TestController {

    @Autowired
    TestService testService;

    /**
     * 手动初始化测试表
     * create table t_account
     *  (
     *      id   INTEGER,
     *      account INTEGER
     *  );
     */
    /**
     * 批量插入单独执行
     */
    @GetMapping("/insertBatch")
    public void insertBatch(){
        testService.insertBatch();
    }

    /**
     * 分组批量插入（暂时5个一组）
     */
    @GetMapping("/insertBatch2")
    public void insertBatch2(){
        testService.insertBatch2();
    }

    /**
     * 不使用线程池 循环插入
     */
    @GetMapping("/insertForEach")
    public void insertForEach(){
        testService.insertForEach();

    }

    /**
     *
     * @param account
     */
    @GetMapping("/deleteByAccount")
    public void deleteByAccount(String account){
        testService.deleteByAccount(account);

    }
    @GetMapping("/deleteById")
    public void deleteById(String id){
        testService.deleteById(id);

    }
    @GetMapping("/selectInnerJoin")
    public void selectInnerJoin(String account){
        testService.selectInnerJoin(account);
    }
    @GetMapping("/selectA")
    public void selectA(String account){
        testService.selectA(account);
    }

}
