package com.juphoon.rtc.datacenter.datacore.binlog.mapper;

import com.juphoon.rtc.datacenter.datacore.binlog.mapper.flash.BiConsumerWithSqlException;
import com.juphoon.rtc.datacenter.datacore.binlog.mapper.flash.FunctionWithSqlException;
import com.juphoon.rtc.datacenter.datacore.utils.FileUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.sqlite.SQLiteConfig;
import org.sqlite.SQLiteDataSource;
import org.sqlite.SQLiteException;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.juphoon.rtc.datacenter.datacore.JrtcDataCenterConstant.CACHE_NUMBER;
import static com.juphoon.rtc.datacenter.datacore.JrtcDataCenterConstant.CONNECTION_CLOSED;

/**
 * <p>TODO 好像没有实现Connection的关闭？</p>
 *
 * @author haijie.liang@juphoon.com
 * @date 2022/8/15 13:48
 * @update [1][2022-09-07] [ajian.zheng][重写]
 */
@Slf4j
public abstract class AbstractLogMapper<T> {
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
    protected void restart() {
        connection = openConnection(dbPath());
    }

    /**
     * 建表
     *
     * @param sql
     * @throws SQLException
     */
    protected void createTable(String sql) throws SQLException {
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(sql)) {
            preparedStatement.executeUpdate();
        } catch (SQLiteException e) {
            log.warn("创建表失败");
        } catch (SQLException e) {
            if (CONNECTION_CLOSED.equals(e.getMessage())) {
                restart();
                createTable(sql);
            } else {
                throw e;
            }
        }
    }

    /**
     * 删表
     *
     * @param sql
     */
    protected void dropTable(String sql) {
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(sql)) {
            preparedStatement.executeUpdate();
        } catch (SQLiteException e) {
            log.warn("删除表失败");
        } catch (SQLException e) {
            if (CONNECTION_CLOSED.equals(e.getMessage())) {
                restart();
                dropTable(sql);
            }
        }
    }

    /**
     * 保存
     *
     * @param sql
     * @param event
     * @param consumer
     * @return
     */
    protected int save(String sql, T event, BiConsumerWithSqlException<PreparedStatement, T> consumer) {
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(sql)) {
            consumer.accept(preparedStatement, event);
            return preparedStatement.executeUpdate();
        } catch (SQLiteException e) {
            log.warn("保存事件失败,ec:{}", event);
        } catch (SQLException e) {
            if (CONNECTION_CLOSED.equals(e.getMessage())) {
                restart();
                save(sql, event, consumer);
            }
        }
        return 0;
    }

    /**
     * 批量保存
     *
     * @param sql
     * @param list
     * @param consumer
     * @return
     */
    protected int saveList(String sql, List<T> list, BiConsumerWithSqlException<PreparedStatement, T> consumer) {
        int count = 0;
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(sql)) {
            for (int i = 0; i < list.size(); i++) {
                consumer.accept(preparedStatement, list.get(i));
                preparedStatement.addBatch();
                if (i % CACHE_NUMBER == 0) {
                    count = Arrays.stream(preparedStatement.executeBatch()).reduce(count, Integer::sum);
                    preparedStatement.clearBatch();
                }
            }
            count = Arrays.stream(preparedStatement.executeBatch()).reduce(count, Integer::sum);
            preparedStatement.clearBatch();
            return count;
        } catch (SQLiteException e) {
            log.warn("批量保存事件失败");
        } catch (SQLException e) {
            if (CONNECTION_CLOSED.equals(e.getMessage())) {
                restart();
                saveList(sql, list, consumer);
            }
        }

        return 0;
    }

    /**
     * 删除
     *
     * @param sql
     * @param id
     */
    protected void remove(String sql, Long id) {
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLiteException e) {
            log.warn("删除事件失败,id:{}", id);
        } catch (SQLException e) {
            if (CONNECTION_CLOSED.equals(e.getMessage())) {
                restart();
                remove(sql, id);
            }
        }
    }

    /**
     * 批量删除
     *
     * @param sql
     * @param list
     */
    protected void remove(String sql, List<Long> list) {
        try (PreparedStatement stmt = getConnection().prepareStatement(sql)) {
            for (int i = 0; i < list.size(); i++) {
                stmt.setLong(1, list.get(i));
                stmt.addBatch();
                if (i % CACHE_NUMBER == 0) {
                    stmt.executeBatch();
                    stmt.clearBatch();
                }
            }

            stmt.executeBatch();
            stmt.clearBatch();
        } catch (SQLiteException e) {
            log.warn("删除事件失败,list:{}", list.size());
        } catch (SQLException e) {
            if (CONNECTION_CLOSED.equals(e.getMessage())) {
                restart();
                remove(sql, list);
            }
        }
    }

    /**
     * 更新重做次数
     *
     * @param sql
     * @param lastUpdateTimestamp
     * @param id
     */
    protected void updateRetryCount(String sql, long lastUpdateTimestamp, long id) {
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(sql)) {
            preparedStatement.setLong(1, lastUpdateTimestamp);
            preparedStatement.setLong(2, id);
            preparedStatement.executeUpdate();
        } catch (
                SQLiteException e) {
            log.warn("更新重试次数失败,ec:{}", id);
        } catch (
                SQLException e) {
            if (CONNECTION_CLOSED.equals(e.getMessage())) {
                restart();
                updateRetryCount(sql, lastUpdateTimestamp, id);
            }
        }
    }

    /**
     * findOne
     *
     * @param sql
     * @param id
     * @return
     */
    protected T findById(String sql, long id, FunctionWithSqlException<ResultSet, T> function) {
        T po = null;

        try (PreparedStatement preparedStatement = getConnection().prepareStatement(sql)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                po = function.apply(resultSet);
            }
            resultSet.close();
            return po;
        } catch (SQLiteException e) {
            log.warn("根据id查找失败,id:{}", id);
        } catch (SQLException e) {
            if (CONNECTION_CLOSED.equals(e.getMessage())) {
                restart();
                findById(sql, id, function);
            }
        }

        return po;
    }

    /**
     * 查找
     *
     * @param sql
     * @param size
     * @param function
     * @return
     */
    protected List<T> find(String sql, int size, FunctionWithSqlException<ResultSet, T> function) {
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(sql)) {
            preparedStatement.setInt(1, size);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<T> list = new ArrayList<>();
            while (resultSet.next()) {
                list.add(function.apply(resultSet));
            }
            resultSet.close();
            return list;
        } catch (SQLiteException e) {
            log.warn("查找失败");
        } catch (SQLException e) {
            if (CONNECTION_CLOSED.equals(e.getMessage())) {
                restart();
                find(sql, size, function);
            }
        }
        return null;
    }

    public void stop() {
        closeConnection();
    }
}
