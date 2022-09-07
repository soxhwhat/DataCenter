package com.juphoon.rtc.datacenter.datacore.binlog.mapper.flash;

import com.juphoon.rtc.datacenter.datacore.binlog.entity.EventBinLogPO;
import com.juphoon.rtc.datacenter.datacore.binlog.mapper.AbstractLogMapper;
import com.juphoon.rtc.datacenter.datacore.binlog.mapper.EventLogMapper;
import lombok.extern.slf4j.Slf4j;
import org.sqlite.SQLiteException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.juphoon.rtc.datacenter.datacore.JrtcDataCenterConstant.CACHE_NUMBER;
import static com.juphoon.rtc.datacenter.datacore.JrtcDataCenterConstant.CONNECTION_CLOSED;

/**
 * <p> 抽象的事件MAPPER </p>
 * <p>TODO 好像没有实现Connection的关闭？</p>
 *
 * @author haijie.liang@juphoon.com
 * @date 2022/8/15 13:48
 * @update [1][2022-09-07] [ajian.zheng][重写]
 */
@Slf4j
public abstract class AbstractEventLogMapper extends AbstractLogMapper<EventBinLogPO> implements EventLogMapper {
    @Override
    public synchronized void createTable() throws SQLException {
        String sql = "create table IF NOT EXISTS `event_binlog`\n" +
                "        (\n" +
                "            `id`          integer not null constraint event_log_pk primary key,\n" +
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
                "            `number`    integer     not null,\n" +
                "            `timestamp` integer     not null,\n" +
                "            `params`    text\n" +
                "        );";

        createTable(sql);
    }

    @Override
    public synchronized void dropTable() {
        String sql = "DROP TABLE IF EXISTS `event_binlog`;";

        dropTable(sql);
    }

    @Override
    public synchronized int save(EventBinLogPO event) {
        String sql = " INSERT INTO `event_binlog`" +
                "(`id`, `requestId`, `from`, `processorId`, `receivedTimestamp`, `lastUpdateTimestamp`," +
                "`redoHandler`,`retryCount`, `domainId`, `appId`, `uuid`, `type`, `number`, `timestamp`, `params`)" +
                "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        return save(sql, event, preparedStatement);
    }

    @Override
    public synchronized int saveList(List<EventBinLogPO> list) {
        String sql = "INSERT INTO `event_binlog`"
                + "(`id`, `requestId`, `from`, `processorId`, `receivedTimestamp`, `lastUpdateTimestamp`," +
                "`redoHandler`, `retryCount`, `domainId`, `appId`, `uuid`, `type`, `number`, `timestamp`, `params`)" +
                " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        return saveList(sql, list, preparedStatement);
    }

    @Override
    public synchronized void remove(long id) {
        String sql = " DELETE FROM `event_binlog` WHERE `id`=?;";

        remove(sql, id);
    }

    /**
     * 批量删除
     *
     * @param list
     */
    @Override
    public synchronized void remove(List<Long> list) {
        String sql = " DELETE FROM `event_binlog` WHERE `id`=?;";

        remove(sql, list);
    }

    @Override
    public synchronized void updateRetryCount(EventBinLogPO po) {
        String sql = "UPDATE `event_binlog` set `retryCount` = `retryCount` + 1,\n" +
                "`lastUpdateTimestamp` = ?  WHERE `id`= ?;";

        updateRetryCount(sql, po.getLastUpdateTimestamp(), po.getId());
    }



    @Override
    public synchronized EventBinLogPO findById(long contentId) {
        EventBinLogPO po = null;
        String sql = " SELECT `id`, `requestId`, `from`, `processorId`, `receivedTimestamp`, `lastUpdateTimestamp`," +
                "`redoHandler`, `retryCount`, `domainId`, `appId`, `uuid`, `type`, `number`, `timestamp`, `params`" +
                "FROM `event_binlog` WHERE `id`= ?;";

        return findById(sql, contentId, fromDatabase);
    }

    @Override
    public synchronized List<EventBinLogPO> find(int size) {
        String sql = "SELECT `id`, `requestId`, `from`, `processorId`, `receivedTimestamp`, `lastUpdateTimestamp`,\n" +
                "        `redoHandler`, `retryCount`, `domainId`,\n" +
                "        `appId`, `uuid`, `type`, `number`, `timestamp`, `params`\n" +
                "        FROM `event_binlog`\n" +
                "        ORDER BY `lastUpdateTimestamp`\n" +
                "        LIMIT ?;";

        return find(sql, size, fromDatabase);
    }

    BiConsumerWithSqlException<PreparedStatement, EventBinLogPO> preparedStatement = (preparedStatement, event) -> {
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
    };

    FunctionWithSqlException<ResultSet, EventBinLogPO> fromDatabase = resultSet -> {
        EventBinLogPO po = new EventBinLogPO();
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
        po.setNumber(resultSet.getInt("number"));
        po.setTimestamp(resultSet.getLong("timestamp"));
        po.setParams(resultSet.getString("params"));

        return po;
    };
}
