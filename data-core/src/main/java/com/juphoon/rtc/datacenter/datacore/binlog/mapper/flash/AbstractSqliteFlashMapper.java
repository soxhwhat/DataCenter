package com.juphoon.rtc.datacenter.datacore.binlog.mapper.flash;

import com.juphoon.rtc.datacenter.datacore.utils.FileUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.sqlite.SQLiteConfig;
import org.sqlite.SQLiteDataSource;

import java.sql.Connection;
import java.sql.SQLException;

import static com.juphoon.rtc.datacenter.datacore.JrtcDataCenterConstant.LOCAL_DB_FILE_BASE_PATH;
import static com.juphoon.rtc.datacenter.datacore.JrtcDataCenterConstant.LOCAL_DB_FILE_FLASH;

/**
 * <p>
 * FlashMapper基类，主要实现Connection复用
 * </p>
 * <p>描述请遵循 javadoc 规范</p>
 * <p>TODO 好像没有实现Connection的关闭？</p>
 *
 * @author haijie.liang@juphoon.com
 * @date 2022/8/15 13:48
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Slf4j
public abstract class AbstractSqliteFlashMapper {

    static Connection CONNECTION = getConnection();

    public static Connection getConnection() {
        try {
            if (CONNECTION == null || CONNECTION.isClosed()) {
                openConnection();
            }
        } catch (SQLException e) {
            log.warn("获取连接失败,{}",e.getMessage());
        }
        return CONNECTION;
    }

    private static void openConnection() throws SQLException {
        String dbPath = System.getProperty("user.dir") + LOCAL_DB_FILE_BASE_PATH;
        FileUtils.createDir(dbPath);

        SQLiteDataSource sqLiteDataSource = new SQLiteDataSource();
        sqLiteDataSource.setUrl("jdbc:sqlite:" + dbPath + LOCAL_DB_FILE_FLASH);

        SQLiteConfig config = new SQLiteConfig();
        // 关日志
        config.setJournalMode(SQLiteConfig.JournalMode.WAL);
        // 关刷新
        config.setSynchronous(SQLiteConfig.SynchronousMode.NORMAL);
        // 文件锁
        config.setLockingMode(SQLiteConfig.LockingMode.EXCLUSIVE);

        config.setBusyTimeout(500);

        sqLiteDataSource.setConfig(config);
        CONNECTION = sqLiteDataSource.getConnection();

    }

    void closeConnection() {
        try {
            if (!CONNECTION.isClosed()) {
                CONNECTION.close();
            }
        } catch (SQLException e) {
            log.warn("关闭连接失败");
        }
    }


    @SneakyThrows
    void restart() {
        openConnection();
    }
}
