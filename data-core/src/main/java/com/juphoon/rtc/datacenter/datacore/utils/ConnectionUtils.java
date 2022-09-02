package com.juphoon.rtc.datacenter.datacore.utils;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.sqlite.SQLiteConfig;
import org.sqlite.SQLiteDataSource;

import java.sql.Connection;
import java.sql.SQLException;

import static com.juphoon.rtc.datacenter.datacore.JrtcDataCenterConstant.LOCAL_DB_FILE_BASE_PATH;

/**
 * <p>获取连接工具类</p>
 * <p>描述请遵循 javadoc 规范</p>
 * <p>TODO</p>
 *
 * @author haijie.liang@juphoon.com
 * @date 2022/9/1 16:57
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */

@Slf4j
public class ConnectionUtils {

    public static Connection getConnection(Connection connection, String path) {
        try {
            if (connection == null || connection.isClosed()) {
                connection = openConnection(path);
            }
        } catch (SQLException e) {
            log.warn("获取连接失败,{}", e.getMessage());
        }

        return connection;
    }

    private static Connection openConnection(String path) throws SQLException {
        String dbPath = System.getProperty("user.dir") + LOCAL_DB_FILE_BASE_PATH;
        FileUtils.createDir(dbPath);

        SQLiteDataSource sqLiteDataSource = new SQLiteDataSource();
        sqLiteDataSource.setUrl("jdbc:sqlite:" + dbPath + path);

        SQLiteConfig config = new SQLiteConfig();
        // 关日志
        config.setJournalMode(SQLiteConfig.JournalMode.WAL);
        // 关刷新
        config.setSynchronous(SQLiteConfig.SynchronousMode.NORMAL);
        // 文件锁
        config.setLockingMode(SQLiteConfig.LockingMode.EXCLUSIVE);

        config.setBusyTimeout(500);

        sqLiteDataSource.setConfig(config);

        return sqLiteDataSource.getConnection();
    }

    public static void closeConnection(Connection connection) {
        try {
            if (!connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            log.warn("关闭连接失败");
        }
    }

    @SneakyThrows
    public static Connection restart(String path) {
        return ConnectionUtils.openConnection(path);
    }
}
