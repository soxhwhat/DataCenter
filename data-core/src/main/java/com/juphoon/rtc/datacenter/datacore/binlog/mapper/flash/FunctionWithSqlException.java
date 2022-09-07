package com.juphoon.rtc.datacenter.datacore.binlog.mapper.flash;

import java.sql.SQLException;

/**
 * <p>在开始处详细描述该类的作用</p>
 *
 * @author ajian.zheng@juphoon.com
 * @date 9/7/22 7:49 PM
 */
@FunctionalInterface
public interface FunctionWithSqlException<T, R> {
    /**
     * apply
     * @param t
     * @return
     * @throws SQLException
     */
    R apply(T t) throws SQLException;
}
