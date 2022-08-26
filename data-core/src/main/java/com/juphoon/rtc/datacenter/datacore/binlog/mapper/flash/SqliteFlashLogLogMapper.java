package com.juphoon.rtc.datacenter.datacore.binlog.mapper.flash;

import com.juphoon.rtc.datacenter.datacore.binlog.entity.LogBinLogPO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.sqlite.SQLiteException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.juphoon.rtc.datacenter.datacore.JrtcDataCenterConstant.CONNECTION_CLOSED;

/**
 *
 * @author haijie.liang@juphoon.com
 * @date 2022/8/15 09:53
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Component
@Slf4j
public class SqliteFlashLogLogMapper extends AbstractSqliteFlashMapper {

    public synchronized void createTable() throws SQLException {
        try (PreparedStatement preparedStatement = CONNECTION.prepareStatement(" create table IF NOT EXISTS `log_binlog`\n" +
                "        (\n" +
                "            `id`          integer not null constraint log_log_pk primary key,\n" +
                "            `requestId`   varchar(32) not null,\n" +
                "            `from`        varchar(64) not null,\n" +
                "            `processorId` varchar(32) not null,\n" +
                "            `receivedTimestamp`   integer not null,\n" +
                "            `lastUpdateTimestamp`   integer not null,\n" +
                "            `redoHandler` varchar(32),\n" +
                "            `retryCount`  integer not null,\n" +
                "\n" +
                "            `type`      integer     not null,\n" +
                "            `number`    integer     not null,\n" +
                "            `logs`    text\n" +
                "        );")) {

            preparedStatement.executeUpdate();
        } catch (SQLiteException e) {
            log.warn("创建表失败");
        } catch (SQLException e) {
            if (CONNECTION_CLOSED.equals(e.getMessage())) {
                restart();
                createTable();
            } else {
                throw e;
            }
        }
    }

    public synchronized void dropTable() {
        try (PreparedStatement preparedStatement = CONNECTION.prepareStatement("  DROP TABLE IF EXISTS `log_binlog`;")) {
            preparedStatement.executeUpdate();
        } catch (SQLiteException e) {
            log.warn("删除表失败");
        } catch (SQLException e) {
            if (CONNECTION_CLOSED.equals(e.getMessage())) {
                restart();
                dropTable();
            }
        }

    }

    public synchronized int save(LogBinLogPO event) {
        try (PreparedStatement preparedStatement = CONNECTION.prepareStatement(" INSERT INTO `log_binlog`(`id`, `requestId`, `from`, `processorId`, `receivedTimestamp`, `lastUpdateTimestamp`," +
                "`redoHandler`," +
                "`retryCount`, `type`, `number`,`logs`) VALUES (?,?,?,?,?,?,?,?,?,?,?);")) {
            preparedStatement.setLong(1, event.getId());
            preparedStatement.setString(2, event.getRequestId());
            preparedStatement.setString(3, event.getFrom());
            preparedStatement.setString(4, event.getProcessorId());
            preparedStatement.setLong(5, event.getReceivedTimestamp());
            preparedStatement.setLong(6, event.getLastUpdateTimestamp());
            preparedStatement.setString(7, event.getRedoHandler());
            preparedStatement.setInt(8, event.getRetryCount());
            preparedStatement.setInt(9, event.getType());
            preparedStatement.setInt(10, event.getNumber());
            preparedStatement.setString(11, event.getLogs());
            return preparedStatement.executeUpdate();
        } catch (SQLiteException e) {
            log.warn("保存事件失败,ec:{}", event.getId());
        } catch (SQLException e) {
            if (CONNECTION_CLOSED.equals(e.getMessage())) {
                restart();
                save(event);
            }
        }
        return 0;
    }

    public synchronized int saveList(List<LogBinLogPO> list) {
        try (PreparedStatement preparedStatement = CONNECTION.prepareStatement("INSERT INTO `log_binlog`(`id`, `requestId`, `from`, `processorId`, `receivedTimestamp`, `lastUpdateTimestamp`,\n" +
                "        `redoHandler`,\n" +
                "        `retryCount`, `type`, `number`, `logs`) VALUES (?,?,?,?,?,?,?,?,?,?,?)")) {
            for (int i = 0; i < list.size(); i++) {
                LogBinLogPO logBinLogPO = list.get(i);
                preparedStatement.setLong(1, logBinLogPO.getId());
                preparedStatement.setString(2, logBinLogPO.getRequestId());
                preparedStatement.setString(3, logBinLogPO.getFrom());
                preparedStatement.setString(4, logBinLogPO.getProcessorId());
                preparedStatement.setLong(5, logBinLogPO.getReceivedTimestamp());
                preparedStatement.setLong(6, logBinLogPO.getLastUpdateTimestamp());
                preparedStatement.setString(7, logBinLogPO.getRedoHandler());
                preparedStatement.setInt(8, logBinLogPO.getRetryCount());
                preparedStatement.setInt(9, logBinLogPO.getType());
                preparedStatement.setInt(10, logBinLogPO.getNumber());
                preparedStatement.setString(11, logBinLogPO.getLogs());
                preparedStatement.addBatch();
                if (i % 500 == 0) {
                    preparedStatement.executeBatch();
                    preparedStatement.clearBatch();
                }
            }
            return list.size();
        } catch (SQLiteException e) {
            log.warn("批量保存事件失败");
        } catch (SQLException e) {
            if (CONNECTION_CLOSED.equals(e.getMessage())) {
                restart();
                saveList(list);
            }
        }
        return 0;
    }

    public synchronized void remove(long id) {
        try (PreparedStatement preparedStatement = CONNECTION.prepareStatement(" DELETE FROM `log_binlog` WHERE `id`=?;")) {
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLiteException e) {
            log.warn("删除事件失败,id:{}", id);
        } catch (SQLException e) {
            if (CONNECTION_CLOSED.equals(e.getMessage())) {
                restart();
                remove(id);
            }
        }
    }

    public synchronized void updateRetryCount(LogBinLogPO po) {
        try (PreparedStatement preparedStatement = CONNECTION.prepareStatement("UPDATE `log_binlog` set `retryCount` = `retryCount` + 1,\n" +
                "`lastUpdateTimestamp` = ?  WHERE `id`= ?;")) {
            preparedStatement.setLong(1, po.getLastUpdateTimestamp());
            preparedStatement.setLong(2, po.getId());
            preparedStatement.executeUpdate();
        } catch (SQLiteException e) {
            log.warn("更新重试次数失败,ec:{}", po.getId());
        } catch (SQLException e) {
            if (CONNECTION_CLOSED.equals(e.getMessage())) {
                restart();
                updateRetryCount(po);
            }
        }
    }


    public synchronized LogBinLogPO findById(long contentId) {
        try (PreparedStatement preparedStatement = CONNECTION.prepareStatement(" SELECT `id`, `requestId`, `from`, `processorId`, `receivedTimestamp`, `lastUpdateTimestamp`,\n" +
                "        `redoHandler`, `retryCount`, `type`, `number`, `logs`\n" +
                "        FROM `log_binlog`\n" +
                "        WHERE `id`= ?;")) {
            preparedStatement.setLong(1, contentId);
            ResultSet resultSet = preparedStatement.executeQuery();
            LogBinLogPO logBinLogPO = new LogBinLogPO();
            while (resultSet.next()) {
                logBinLogPO.setId(resultSet.getLong("id"));
                logBinLogPO.setRequestId(resultSet.getString("requestId"));
                logBinLogPO.setFrom(resultSet.getString("from"));
                logBinLogPO.setProcessorId(resultSet.getString("processorId"));
                logBinLogPO.setReceivedTimestamp(resultSet.getLong("receivedTimestamp"));
                logBinLogPO.setLastUpdateTimestamp(resultSet.getLong("lastUpdateTimestamp"));
                logBinLogPO.setRedoHandler(resultSet.getString("redoHandler"));
                logBinLogPO.setRetryCount(resultSet.getInt("retryCount"));
                logBinLogPO.setType(resultSet.getInt("type"));
                logBinLogPO.setNumber(resultSet.getInt("number"));
                logBinLogPO.setLogs(resultSet.getString("logs"));
            }
            return logBinLogPO;
        } catch (SQLiteException e) {
            log.warn("根据id查找失败,id:{}", contentId);
        } catch (SQLException e) {
            if (CONNECTION_CLOSED.equals(e.getMessage())) {
                restart();
                findById(contentId);
            }
        }
        return null;
    }


    public synchronized List<LogBinLogPO> find(int size) {
        try (PreparedStatement preparedStatement = CONNECTION.prepareStatement("SELECT `id`, `requestId`, `from`, `processorId`, `receivedTimestamp`, `lastUpdateTimestamp`,\n" +
                "        `redoHandler`, `retryCount`,\n" +
                "         `type`, `number`, `logs`\n" +
                "        FROM `log_binlog`\n" +
                "        ORDER BY `lastUpdateTimestamp`\n" +
                "        LIMIT ?;")) {
            preparedStatement.setInt(1, size);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<LogBinLogPO> list = new ArrayList<>();
            while (resultSet.next()) {
                LogBinLogPO logBinLogPO = new LogBinLogPO();
                logBinLogPO.setId(resultSet.getLong("id"));
                logBinLogPO.setRequestId(resultSet.getString("requestId"));
                logBinLogPO.setFrom(resultSet.getString("from"));
                logBinLogPO.setProcessorId(resultSet.getString("processorId"));
                logBinLogPO.setReceivedTimestamp(resultSet.getLong("receivedTimestamp"));
                logBinLogPO.setLastUpdateTimestamp(resultSet.getLong("lastUpdateTimestamp"));
                logBinLogPO.setRedoHandler(resultSet.getString("redoHandler"));
                logBinLogPO.setRetryCount(resultSet.getInt("retryCount"));
                logBinLogPO.setType(resultSet.getInt("type"));
                logBinLogPO.setNumber(resultSet.getInt("number"));
                logBinLogPO.setLogs(resultSet.getString("logs"));
                list.add(logBinLogPO);
            }
            return list;
        } catch (SQLiteException e) {
            log.warn("查找失败");
        } catch (SQLException e) {
            if (CONNECTION_CLOSED.equals(e.getMessage())) {
                restart();
                find(size);
            }
        }
        return null;
    }
}
