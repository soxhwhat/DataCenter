package com.juphoon.rtc.datacenter.datacore.binlog.mapper;

import com.juphoon.rtc.datacenter.datacore.utils.FileUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.sqlite.SQLiteConfig;
import org.sqlite.SQLiteDataSource;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;

/**
 * <p>
 * </p>
 * <p>描述请遵循 javadoc 规范</p>
 * <p>TODO 好像没有实现Connection的关闭？</p>
 *
 * @author haijie.liang@juphoon.com
 * @date 2022/8/15 13:48
 * @update [1][2022-09-07] [ajian.zheng][重写]
 */
@Slf4j
public abstract class AbstractLogMapper {
    /**
     * 数据库连接
     */
    private Connection connection;

    /**
     * 设置 db 路径
     *
     * @return 路径
     */
    public abstract String dbPath();

    protected Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                connection = openConnection(dbPath());
            }
        } catch (SQLException e) {
            log.warn("获取连接失败,{}", e.getMessage());
        }

        return connection;
    }

    /**
     * 打开连接
     *
     * @param path
     * @return
     * @throws SQLException
     */
    private Connection openConnection(String path) throws SQLException {
        String dir = path.substring(0, path.lastIndexOf(File.separator));
        FileUtils.createDir(dir);

        SQLiteDataSource sqLiteDataSource = new SQLiteDataSource();
        sqLiteDataSource.setUrl("jdbc:sqlite:" + path);

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

    /**
     * 关闭连接
     */
    public void closeConnection() {
        try {
            if (!connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            log.warn("关闭连接失败");
        }
    }

    /**
     * 重置连接
     *
     * @return
     */
    @SneakyThrows
    public void restart() {
        connection = openConnection(dbPath());
    }
}
