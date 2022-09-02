package com.juphoon.rtc.datacenter.datacore.binlog.mapper.flash;

import com.juphoon.rtc.datacenter.datacore.utils.ConnectionUtils;

import java.sql.Connection;

import static com.juphoon.rtc.datacenter.datacore.JrtcDataCenterConstant.LOCAL_DB_FILE_FLASH;

/**
 * <p>
 * </p>
 * <p>描述请遵循 javadoc 规范</p>
 * <p>TODO 好像没有实现Connection的关闭？</p>
 *
 * @author haijie.liang@juphoon.com
 * @date 2022/8/15 13:48
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
public abstract class AbstractSqliteFlashMapper {
    public static Connection CONNECTION;

    static {
        CONNECTION = ConnectionUtils.getConnection(null, LOCAL_DB_FILE_FLASH);
    }


}
