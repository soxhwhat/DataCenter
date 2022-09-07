package com.juphoon.rtc.datacenter.datacore.binlog.mapper.flash;

import java.sql.SQLException;

/**
 * <p>在开始处详细描述该类的作用</p>
 *
 * @author ajian.zheng@juphoon.com
 * @date 9/7/22 7:49 PM
 */
@FunctionalInterface
public interface BiConsumerWithSqlException<T, U> {
    /**
     * accept
     *
     * @param t
     * @param u
     * @throws SQLException
     */
    void accept(T t, U u) throws SQLException;
}
