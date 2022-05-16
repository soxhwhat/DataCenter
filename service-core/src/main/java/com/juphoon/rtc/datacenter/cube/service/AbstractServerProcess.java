//package com.juphoon.rtc.datacenter.cube.service;
//
//import Common.ServerCall;
//import com.juphoon.iron.cube.starter.CubeUtils;
//import lombok.extern.slf4j.Slf4j;
//
///**
// * @Author: Zhiwei.zhai
// * @Date: 2022/5/9 12:19
// * @Description:
// */
//@Slf4j
//public abstract class AbstractServerProcess<T, R> {
//
//    public R process(ServerCall serverCall, T data) {
//        String magic = CubeUtils.bytesToHex(serverCall.getMagic());
//        log.debug("magic:{}", magic);
//
//        try {
//            String host = serverCall.getParam("host");
//            return trans(data, host, magic);
//        } catch (Exception e) {
//            serverCall.setReason(e.getMessage());
//        }
//    }
//
//    /**
//     * 批量转换为ec，设置magic,host
//     *
//     * @param from
//     * @param host
//     * @param magic
//     * @return
//     * @throws Exception
//     */
//    public abstract R trans(T from, String host, String magic) throws Exception;
//}
