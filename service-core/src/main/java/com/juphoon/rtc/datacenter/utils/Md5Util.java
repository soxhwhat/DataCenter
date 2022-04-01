package com.juphoon.rtc.datacenter.utils;

import org.apache.commons.codec.digest.DigestUtils;

import java.security.MessageDigest;
import java.util.function.Function;

/**
 * md5加密类
 *
 * @author Yuan
 */
@Deprecated
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

    @SuppressWarnings("PMD")
    static void dump(Function<String, String> function) {
        String str = "123dsfsadfsa12312312dafafasfasd";

        System.out.println(function.apply(str));
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            function.apply(str);
        }
        System.out.println("cost:" + (System.currentTimeMillis() - start));
    }

    public static void main(String[] args) {
        dump(Md5Util::encryptMd5);
        dump(DigestUtils::md5Hex);
        dump(Md5Util::encryptMd5);
        dump(DigestUtils::md5Hex);
        dump(Md5Util::encryptMd5);
        dump(DigestUtils::md5Hex);
    }

}
