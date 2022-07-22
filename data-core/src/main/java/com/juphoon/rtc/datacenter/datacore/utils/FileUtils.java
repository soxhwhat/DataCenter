package com.juphoon.rtc.datacenter.datacore.utils;

import java.io.File;

/**
 * <p>文件操作相关工具类</p>
 *
 * @author ajian.zheng@juphoon.com
 * @date 7/21/22 4:53 PM
 */
public class FileUtils {


    /**
     * 创建目录方法
     * @param dirName
     * @return
     */
    public static boolean createDir(String dirName) {
        if (!dirName.endsWith(File.separator)) {
            dirName = dirName + File.separator;
        }

        File dir = new File(dirName);
        if (dir.exists()) {
            return true;
        }

        return dir.mkdir();
    }

}
