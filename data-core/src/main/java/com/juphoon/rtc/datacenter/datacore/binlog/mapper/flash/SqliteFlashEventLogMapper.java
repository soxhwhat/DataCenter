package com.juphoon.rtc.datacenter.datacore.binlog.mapper.flash;

import com.juphoon.rtc.datacenter.datacore.binlog.entity.EventBinLogPO;
import com.juphoon.rtc.datacenter.datacore.binlog.mapper.EventLogMapper;
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
public class SqliteFlashEventLogMapper extends AbstractSqliteFlashMapper implements EventLogMapper {

    @Override
    public synchronized void createTable() throws SQLException {
        try (PreparedStatement preparedStatement = CONNECTION.prepareStatement(" create table IF NOT EXISTS `event_binlog`\n" +
                "        (\n" +
                "            `id`          integer not null constraint event_log_pk primary key,\n" +
                "            `requestId`   varchar(32) not null,\n" +
                "            `from`        varchar(64) not null,\n" +
                "            `processorId` varchar(32) not null,\n" +
                "            `receivedTimestamp`   integer not null,\n" +
                "            `lastUpdateTimestamp`   integer not null,\n" +
                "            `redoHandler` varchar(32),\n" +
                "            `retryCount`  integer not null,\n" +
                "\n" +
                "            `uuid`      varchar(64) not null,\n" +
                "            `domainId`  integer     not null,\n" +
                "            `appId`     integer     not null,\n" +
                "            `type`      integer     not null,\n" +
                "            `number`    integer     not null,\n" +
                "            `timestamp` integer     not null,\n" +
                "            `params`    text\n" +
                "        );")) {

            preparedStatement.executeUpdate();
        } catch (SQLiteException e) {
            log.warn("创建表失败");
        } catch (SQLException e) {
            if (CONNECTION_CLOSED.equals(e.getMessage())) {
                restart();
                createTable();
            }else {
                throw e;
            }
        }
    }

    @Override
    public synchronized void dropTable() {
        try (PreparedStatement preparedStatement = CONNECTION.prepareStatement(" DROP TABLE IF EXISTS event_binlog;")) {
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

    @Override
    public synchronized int save(EventBinLogPO event) {
        try (PreparedStatement preparedStatement = CONNECTION.prepareStatement(" INSERT INTO `event_binlog`(`id`, `requestId`, `from`, `processorId`, `receivedTimestamp`, `lastUpdateTimestamp`," +
                "`redoHandler`," +
                "`retryCount`, `domainId`, `appId`, `uuid`, `type`, `number`, `timestamp`, `params`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)")) {
            preparedStatement.setLong(1, event.getId());
            preparedStatement.setString(2, event.getRequestId());
            preparedStatement.setString(3, event.getFrom());
            preparedStatement.setString(4, event.getProcessorId());
            preparedStatement.setLong(5, event.getReceivedTimestamp());
            preparedStatement.setLong(6, event.getLastUpdateTimestamp());
            preparedStatement.setString(7, event.getRedoHandler());
            preparedStatement.setInt(8, event.getRetryCount());
            preparedStatement.setInt(9, event.getDomainId());
            preparedStatement.setInt(10, event.getAppId());
            preparedStatement.setString(11, event.getUuid());
            preparedStatement.setInt(12, event.getType());
            preparedStatement.setInt(13, event.getNumber());
            preparedStatement.setLong(14, event.getTimestamp());
            preparedStatement.setString(15, event.getParams());
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

    @Override
    public synchronized int saveList(List<EventBinLogPO> list) {
        try (PreparedStatement preparedStatement = CONNECTION.prepareStatement("INSERT INTO `event_binlog`(`id`, `requestId`, `from`, `processorId`, `receivedTimestamp`, `lastUpdateTimestamp`,\n" +
                "        `redoHandler`,\n" +
                "        `retryCount`, `domainId`, `appId`, `uuid`, `type`, `number`, `timestamp`, `params`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)")) {
            for (int i = 0; i < list.size(); i++) {
                EventBinLogPO eventBinLogPO = list.get(i);
                preparedStatement.setLong(1, eventBinLogPO.getId());
                preparedStatement.setString(2, eventBinLogPO.getRequestId());
                preparedStatement.setString(3, eventBinLogPO.getFrom());
                preparedStatement.setString(4, eventBinLogPO.getProcessorId());
                preparedStatement.setLong(5, eventBinLogPO.getReceivedTimestamp());
                preparedStatement.setLong(6, eventBinLogPO.getLastUpdateTimestamp());
                preparedStatement.setString(7, eventBinLogPO.getRedoHandler());
                preparedStatement.setInt(8, eventBinLogPO.getRetryCount());
                preparedStatement.setInt(9, eventBinLogPO.getDomainId());
                preparedStatement.setInt(10, eventBinLogPO.getAppId());
                preparedStatement.setString(11, eventBinLogPO.getUuid());
                preparedStatement.setInt(12, eventBinLogPO.getType());
                preparedStatement.setInt(13, eventBinLogPO.getNumber());
                preparedStatement.setLong(14, eventBinLogPO.getTimestamp());
                preparedStatement.setString(15, eventBinLogPO.getParams());
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

    @Override
    public synchronized void remove(long id) {
        try (PreparedStatement preparedStatement = CONNECTION.prepareStatement(" DELETE FROM `event_binlog` WHERE `id`=?;")) {
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

    @Override
    public synchronized void updateRetryCount(EventBinLogPO po) {
        try (PreparedStatement preparedStatement = CONNECTION.prepareStatement("UPDATE `event_binlog` set `retryCount` = `retryCount` + 1,\n" +
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

    @Override
    public synchronized EventBinLogPO findById(long contentId) {
        try (PreparedStatement preparedStatement = CONNECTION.prepareStatement(" SELECT `id`, `requestId`, `from`, `processorId`, `receivedTimestamp`, `lastUpdateTimestamp`,\n" +
                "        `redoHandler`, `retryCount`, `domainId`, `appId`, `uuid`, `type`, `number`, `timestamp`, `params`\n" +
                "        FROM `event_binlog`\n" +
                "        WHERE `id`= ?;")) {
            preparedStatement.setLong(1, contentId);
            ResultSet resultSet = preparedStatement.executeQuery();
            EventBinLogPO eventBinLogPO = new EventBinLogPO();
            while (resultSet.next()) {
                eventBinLogPO.setId(resultSet.getLong("id"));
                eventBinLogPO.setRequestId(resultSet.getString("requestId"));
                eventBinLogPO.setFrom(resultSet.getString("from"));
                eventBinLogPO.setProcessorId(resultSet.getString("processorId"));
                eventBinLogPO.setReceivedTimestamp(resultSet.getLong("receivedTimestamp"));
                eventBinLogPO.setLastUpdateTimestamp(resultSet.getLong("lastUpdateTimestamp"));
                eventBinLogPO.setRedoHandler(resultSet.getString("redoHandler"));
                eventBinLogPO.setRetryCount(resultSet.getInt("retryCount"));
                eventBinLogPO.setDomainId(resultSet.getInt("domainId"));
                eventBinLogPO.setAppId(resultSet.getInt("appId"));
                eventBinLogPO.setUuid(resultSet.getString("uuid"));
                eventBinLogPO.setType(resultSet.getInt("type"));
                eventBinLogPO.setNumber(resultSet.getInt("number"));
                eventBinLogPO.setTimestamp(resultSet.getLong("timestamp"));
                eventBinLogPO.setParams(resultSet.getString("params"));
            }
            return eventBinLogPO;
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

    @Override
    public synchronized List<EventBinLogPO> find(int size) {
        try (PreparedStatement preparedStatement = CONNECTION.prepareStatement("SELECT `id`, `requestId`, `from`, `processorId`, `receivedTimestamp`, `lastUpdateTimestamp`,\n" +
                "        `redoHandler`, `retryCount`, `domainId`,\n" +
                "        `appId`, `uuid`, `type`, `number`, `timestamp`, `params`\n" +
                "        FROM `event_binlog`\n" +
                "        ORDER BY `lastUpdateTimestamp`\n" +
                "        LIMIT ?;")) {
            preparedStatement.setInt(1, size);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<EventBinLogPO> list = new ArrayList<>();
            while (resultSet.next()) {
                EventBinLogPO eventBinLogPO = new EventBinLogPO();
                eventBinLogPO.setId(resultSet.getLong("id"));
                eventBinLogPO.setRequestId(resultSet.getString("requestId"));
                eventBinLogPO.setFrom(resultSet.getString("from"));
                eventBinLogPO.setProcessorId(resultSet.getString("processorId"));
                eventBinLogPO.setReceivedTimestamp(resultSet.getLong("receivedTimestamp"));
                eventBinLogPO.setLastUpdateTimestamp(resultSet.getLong("lastUpdateTimestamp"));
                eventBinLogPO.setRedoHandler(resultSet.getString("redoHandler"));
                eventBinLogPO.setRetryCount(resultSet.getInt("retryCount"));
                eventBinLogPO.setDomainId(resultSet.getInt("domainId"));
                eventBinLogPO.setAppId(resultSet.getInt("appId"));
                eventBinLogPO.setUuid(resultSet.getString("uuid"));
                eventBinLogPO.setType(resultSet.getInt("type"));
                eventBinLogPO.setNumber(resultSet.getInt("number"));
                eventBinLogPO.setTimestamp(resultSet.getLong("timestamp"));
                eventBinLogPO.setParams(resultSet.getString("params"));
                list.add(eventBinLogPO);
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

    @Override
    public void stop() {
        closeConnection();
    }
}
