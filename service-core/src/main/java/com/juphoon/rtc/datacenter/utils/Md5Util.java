package com.juphoon.rtc.datacenter.utils;

import java.security.MessageDigest;

/**
 * md5加密类
 *
 * @author Yuan
 */
public class Md5Util {

    private static final String SLAT = "&%5123***&&%%$$#@";

    public static String encryptMd5(String dataStr) {
        try {
            dataStr = dataStr + SLAT;
            MessageDigest m = MessageDigest.getInstance("MD5");
            m.update(dataStr.getBytes("UTF8"));
            byte[] s = m.digest();
            StringBuffer buff = new StringBuffer();
            for (int i = 0; i < s.length; i++) {
                buff.append(Integer.toHexString((0x000000FF & s[i]) | 0xFFFFFF00).substring(6));
            }
            return buff.toString();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e.getMessage());
        }
    }

}
