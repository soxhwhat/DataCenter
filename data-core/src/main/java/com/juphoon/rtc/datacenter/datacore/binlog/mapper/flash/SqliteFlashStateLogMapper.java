package com.juphoon.rtc.datacenter.datacore.binlog.mapper.flash;

import com.juphoon.rtc.datacenter.datacore.binlog.entity.StateBinLogPO;
import com.juphoon.rtc.datacenter.datacore.binlog.mapper.AbstractLogMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.sqlite.SQLiteException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static com.juphoon.rtc.datacenter.datacore.JrtcDataCenterConstant.*;

/**
 * @author haijie.liang@juphoon.com
 * @date 2022/8/15 09:53
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Slf4j
@Component
public class SqliteFlashStateLogMapper extends AbstractLogMapper {
    @Override
    public String dbPath() {
        return System.getProperty("user.dir") + LOCAL_DB_FILE_BASE_PATH + LOCAL_DB_FILE_RELIABLE;
    }

    public synchronized void createTable() throws SQLException {
        String sql = " create table IF NOT EXISTS `state_binlog`\n" +
                "        (\n" +
                "            `id`          integer not null constraint state_log_pk primary key,\n" +
                "            `requestId`   varchar(32) not null,\n" +
                "            `from`        varchar(64) not null,\n" +
                "            `processorId` varchar(32) not null,\n" +
                "            `receivedTimestamp`   integer not null,\n" +
                "            `lastUpdateTimestamp`   integer not null,\n" +
                "            `redoHandler` varchar(32),\n" +
                "            `retryCount`  integer not null,\n" +
                "            `uuid`      varchar(64) not null,\n" +
                "            `domainId`  integer     not null,\n" +
                "            `appId`     integer     not null,\n" +
                "            `type`      integer     not null,\n" +
                "            `state`     integer     not null,\n" +
                "            `params`    text\n" +
                "        );";
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(sql)) {
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
        String sql = " DROP TABLE IF EXISTS state_binlog;";

        try (PreparedStatement preparedStatement = getConnection().prepareStatement(sql)) {
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
        String sql = " INSERT INTO `state_binlog`(`id`, `requestId`, `from`, `processorId`, `receivedTimestamp`, `lastUpdateTimestamp`," +
                "`redoHandler`," +
                "`retryCount`, `domainId`, `appId`, `uuid`, `type`, `state`, `params`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        try (PreparedStatement preparedStatement = getConnection().prepareStatement(sql)) {
            savePO(event, preparedStatement);
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
        String sql = "INSERT INTO `state_binlog`(`id`, `requestId`, `from`, `processorId`, `receivedTimestamp`, `lastUpdateTimestamp`,\n" +
                "        `redoHandler`,\n" +
                "        `retryCount`, `domainId`, `appId`, `uuid`, `type`,`state`, `params`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(sql)) {
            for (int i = 0; i < list.size(); i++) {
                StateBinLogPO po = list.get(i);
                savePO(po, preparedStatement);
                preparedStatement.addBatch();
                if (i % CACHE_NUMBER == 0) {
                    preparedStatement.executeBatch();
                    preparedStatement.clearBatch();
                }
            }
            preparedStatement.executeBatch();
            preparedStatement.clearBatch();
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
        String sql = " DELETE FROM `state_binlog` WHERE `id`=?;";

        try (PreparedStatement preparedStatement = getConnection().prepareStatement(sql)) {
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

    /**
     * 批量删除
     *
     * @param list
     */
    public synchronized void remove(List<Long> list) {
        String sql = " DELETE FROM `state_binlog` WHERE `id`=?;";

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
                remove(list);
            }
        }
    }


    public synchronized void updateRetryCount(StateBinLogPO po) {
        String sql = "UPDATE `state_binlog` set `retryCount` = `retryCount` + 1,\n" +
                "`lastUpdateTimestamp` = ?  WHERE `id`= ?;";

        try (PreparedStatement preparedStatement = getConnection().prepareStatement(sql)) {
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

    public synchronized StateBinLogPO findById(long contentId) {
        StateBinLogPO po = null;
        String sql = " SELECT `id`, `requestId`, `from`, `processorId`, `receivedTimestamp`, `lastUpdateTimestamp`,\n" +
                "        `redoHandler`, `retryCount`, `domainId`, `appId`, `uuid`, `type`, `state`, `params`\n" +
                "        FROM `state_binlog`\n" +
                "        WHERE `id`= ?;";
        try (PreparedStatement preparedStatement = getConnection().prepareStatement(sql)) {
            preparedStatement.setLong(1, contentId);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                po = new StateBinLogPO();
                getPo(po, resultSet);
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
        String sql = "SELECT `id`, `requestId`, `from`, `processorId`, `receivedTimestamp`, `lastUpdateTimestamp`,\n" +
                "        `redoHandler`, `retryCount`, `domainId`,\n" +
                "        `appId`, `uuid`, `type`, `state`, `params`\n" +
                "        FROM `state_binlog`\n" +
                "        ORDER BY `lastUpdateTimestamp`\n" +
                "        LIMIT ?;";

        try (PreparedStatement preparedStatement = getConnection().prepareStatement(sql)) {
            preparedStatement.setInt(1, size);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<StateBinLogPO> list = new ArrayList<>();
            while (resultSet.next()) {
                StateBinLogPO stateBinLogPO = new StateBinLogPO();
                getPo(stateBinLogPO, resultSet);
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

    private void getPo(StateBinLogPO po, ResultSet resultSet) throws SQLException {
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

    private void savePO(StateBinLogPO event, PreparedStatement preparedStatement) throws SQLException {
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
    }
}
