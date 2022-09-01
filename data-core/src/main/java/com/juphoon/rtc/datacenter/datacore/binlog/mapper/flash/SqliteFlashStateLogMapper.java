package com.juphoon.rtc.datacenter.datacore.binlog.mapper.flash;

import com.juphoon.rtc.datacenter.datacore.binlog.entity.StateBinLogPO;
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
@Slf4j
@Component
public class SqliteFlashStateLogMapper extends AbstractSqliteFlashMapper {

    public synchronized void createTable() throws SQLException {
        try (PreparedStatement preparedStatement = CONNECTION.prepareStatement(" create table IF NOT EXISTS `state_binlog`\n" +
                "        (\n" +
                "            `id`          integer not null constraint state_log_pk primary key,\n" +
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
                "            `state`     integer     not null,\n" +
                "            `params`    text\n" +
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
        try (PreparedStatement preparedStatement = CONNECTION.prepareStatement(" DROP TABLE IF EXISTS state_binlog;")) {
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

    public synchronized int save(StateBinLogPO event) {
        try (PreparedStatement preparedStatement = CONNECTION.prepareStatement(" INSERT INTO `state_binlog`(`id`, `requestId`, `from`, `processorId`, `receivedTimestamp`, `lastUpdateTimestamp`," +
                "`redoHandler`," +
                "`retryCount`, `domainId`, `appId`, `uuid`, `type`, `state`, `params`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)")) {
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
            preparedStatement.setInt(13, event.getState());
            preparedStatement.setString(14, event.getParams());
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

    public synchronized int saveList(List<StateBinLogPO> list) {
        try (PreparedStatement preparedStatement = CONNECTION.prepareStatement("INSERT INTO `state_binlog`(`id`, `requestId`, `from`, `processorId`, `receivedTimestamp`, `lastUpdateTimestamp`,\n" +
                "        `redoHandler`,\n" +
                "        `retryCount`, `domainId`, `appId`, `uuid`, `type`,`state`, `params`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)")) {
            for (int i = 0; i < list.size(); i++) {
                StateBinLogPO po = list.get(i);
                preparedStatement.setLong(1, po.getId());
                preparedStatement.setString(2, po.getRequestId());
                preparedStatement.setString(3, po.getFrom());
                preparedStatement.setString(4, po.getProcessorId());
                preparedStatement.setLong(5, po.getReceivedTimestamp());
                preparedStatement.setLong(6, po.getLastUpdateTimestamp());
                preparedStatement.setString(7, po.getRedoHandler());
                preparedStatement.setInt(8, po.getRetryCount());
                preparedStatement.setInt(9, po.getDomainId());
                preparedStatement.setInt(10, po.getAppId());
                preparedStatement.setString(11, po.getUuid());
                preparedStatement.setInt(12, po.getType());
                preparedStatement.setInt(13, po.getState());
                preparedStatement.setString(14, po.getParams());
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
        try (PreparedStatement stmt = CONNECTION.prepareStatement(" DELETE FROM `state_binlog` WHERE `id`=?;")) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLiteException e) {
            log.warn("删除事件失败,id:{}", id);
        } catch (SQLException e) {
            if (CONNECTION_CLOSED.equals(e.getMessage())) {
                restart();
                remove(id);
            }
        }
    }

    /**
     * 批量删除
     *
     * @param list
     */
    public synchronized void remove(List<Long> list) {
        try (PreparedStatement stmt = CONNECTION.prepareStatement(" DELETE FROM `state_binlog` WHERE `id`=?;")) {

            for (int i = 0; i < list.size(); i++) {
                stmt.setLong(1, list.get(i));
                stmt.addBatch();
                if (i % 100 == 0) {
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
                remove(list);
            }
        }
    }


    public synchronized void updateRetryCount(StateBinLogPO po) {
        try (PreparedStatement preparedStatement = CONNECTION.prepareStatement("UPDATE `state_binlog` set `retryCount` = `retryCount` + 1,\n" +
                "`lastUpdateTimestamp` = ?  WHERE `id`= ?;")) {
            preparedStatement.setLong(1, po.getLastUpdateTimestamp());
            preparedStatement.setLong(2, po.getId());
            preparedStatement.executeUpdate();
            CONNECTION.commit();
        } catch (SQLiteException e) {
            log.warn("更新重试次数失败,ec:{}", po.getId());
        } catch (SQLException e) {
            if (CONNECTION_CLOSED.equals(e.getMessage())) {
                restart();
                updateRetryCount(po);
            }
        }
    }


    public synchronized StateBinLogPO findById(long contentId) {
        StateBinLogPO po = null;

        try (PreparedStatement preparedStatement = CONNECTION.prepareStatement(" SELECT `id`, `requestId`, `from`, `processorId`, `receivedTimestamp`, `lastUpdateTimestamp`,\n" +
                "        `redoHandler`, `retryCount`, `domainId`, `appId`, `uuid`, `type`, `state`, `params`\n" +
                "        FROM `state_binlog`\n" +
                "        WHERE `id`= ?;")) {
            preparedStatement.setLong(1, contentId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                po = new StateBinLogPO();

                po.setId(resultSet.getLong("id"));
                po.setRequestId(resultSet.getString("requestId"));
                po.setFrom(resultSet.getString("from"));
                po.setProcessorId(resultSet.getString("processorId"));
                po.setReceivedTimestamp(resultSet.getLong("receivedTimestamp"));
                po.setLastUpdateTimestamp(resultSet.getLong("lastUpdateTimestamp"));
                po.setRedoHandler(resultSet.getString("redoHandler"));
                po.setRetryCount(resultSet.getInt("retryCount"));
                po.setDomainId(resultSet.getInt("domainId"));
                po.setAppId(resultSet.getInt("appId"));
                po.setUuid(resultSet.getString("uuid"));
                po.setType(resultSet.getInt("type"));
                po.setState(resultSet.getInt("state"));
                po.setParams(resultSet.getString("params"));
            }
            resultSet.close();

        } catch (SQLiteException e) {
            log.warn("根据id查找失败,id:{}", contentId);
        } catch (SQLException e) {
            if (CONNECTION_CLOSED.equals(e.getMessage())) {
                restart();
                findById(contentId);
            }
        }
        return po;
    }


    public synchronized List<StateBinLogPO> find(int size) {
        try (PreparedStatement preparedStatement = CONNECTION.prepareStatement("SELECT `id`, `requestId`, `from`, `processorId`, `receivedTimestamp`, `lastUpdateTimestamp`,\n" +
                "        `redoHandler`, `retryCount`, `domainId`,\n" +
                "        `appId`, `uuid`, `type`, `state`, `params`\n" +
                "        FROM `state_binlog`\n" +
                "        ORDER BY `lastUpdateTimestamp`\n" +
                "        LIMIT ?;")) {
            preparedStatement.setInt(1, size);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<StateBinLogPO> list = new ArrayList<>();
            while (resultSet.next()) {
                StateBinLogPO stateBinLogPO = new StateBinLogPO();
                stateBinLogPO.setId(resultSet.getLong("id"));
                stateBinLogPO.setRequestId(resultSet.getString("requestId"));
                stateBinLogPO.setFrom(resultSet.getString("from"));
                stateBinLogPO.setProcessorId(resultSet.getString("processorId"));
                stateBinLogPO.setReceivedTimestamp(resultSet.getLong("receivedTimestamp"));
                stateBinLogPO.setLastUpdateTimestamp(resultSet.getLong("lastUpdateTimestamp"));
                stateBinLogPO.setRedoHandler(resultSet.getString("redoHandler"));
                stateBinLogPO.setRetryCount(resultSet.getInt("retryCount"));
                stateBinLogPO.setDomainId(resultSet.getInt("domainId"));
                stateBinLogPO.setAppId(resultSet.getInt("appId"));
                stateBinLogPO.setUuid(resultSet.getString("uuid"));
                stateBinLogPO.setType(resultSet.getInt("type"));
                stateBinLogPO.setState(resultSet.getInt("state"));
                stateBinLogPO.setParams(resultSet.getString("params"));
                list.add(stateBinLogPO);
            }
            resultSet.close();
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
