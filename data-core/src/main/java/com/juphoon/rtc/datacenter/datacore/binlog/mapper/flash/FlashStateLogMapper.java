package com.juphoon.rtc.datacenter.datacore.binlog.mapper.flash;

import com.juphoon.rtc.datacenter.datacore.binlog.entity.StateBinLogPO;
import com.juphoon.rtc.datacenter.datacore.binlog.mapper.AbstractLogMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static com.juphoon.rtc.datacenter.datacore.JrtcDataCenterConstant.LOCAL_DB_FILE_BASE_PATH;
import static com.juphoon.rtc.datacenter.datacore.JrtcDataCenterConstant.LOCAL_DB_FILE_RELIABLE;

/**
 * @author haijie.liang@juphoon.com
 * @date 2022/8/15 09:53
 * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
 */
@Slf4j
@Component
public class FlashStateLogMapper extends AbstractLogMapper<StateBinLogPO> {
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

        createTable(sql);
    }

    public synchronized void dropTable() {
        String sql = " DROP TABLE IF EXISTS state_binlog;";

        dropTable(sql);
    }

    public synchronized int save(StateBinLogPO event) {
        String sql = " INSERT INTO `state_binlog`(`id`, `requestId`, `from`, `processorId`, `receivedTimestamp`, `lastUpdateTimestamp`," +
                "`redoHandler`," +
                "`retryCount`, `domainId`, `appId`, `uuid`, `type`, `state`, `params`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        return save(sql, event, savePO);
    }


    public synchronized int saveList(List<StateBinLogPO> list) {
        String sql = "INSERT INTO `state_binlog`(`id`, `requestId`, `from`, `processorId`, `receivedTimestamp`, `lastUpdateTimestamp`,\n" +
                "        `redoHandler`,\n" +
                "        `retryCount`, `domainId`, `appId`, `uuid`, `type`,`state`, `params`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        return saveList(sql, list, savePO);
    }

    public synchronized void remove(long id) {
        String sql = " DELETE FROM `state_binlog` WHERE `id`=?;";

        remove(sql, id);
    }

    /**
     * 批量删除
     *
     * @param list
     */
    public synchronized void remove(List<Long> list) {
        String sql = " DELETE FROM `state_binlog` WHERE `id`=?;";

        remove(sql, list);
    }

    public synchronized void updateRetryCount(StateBinLogPO po) {
        String sql = "UPDATE `state_binlog` set `retryCount` = `retryCount` + 1,\n" +
                "`lastUpdateTimestamp` = ?  WHERE `id`= ?;";

        updateRetryCount(sql, po.getLastUpdateTimestamp(), po.getId());
    }

    public synchronized StateBinLogPO findById(long contentId) {
        StateBinLogPO po = null;
        String sql = " SELECT `id`, `requestId`, `from`, `processorId`, `receivedTimestamp`, `lastUpdateTimestamp`,\n" +
                "        `redoHandler`, `retryCount`, `domainId`, `appId`, `uuid`, `type`, `state`, `params`\n" +
                "        FROM `state_binlog`\n" +
                "        WHERE `id`= ?;";

        return findById(sql, contentId, getPo);
    }

    public synchronized List<StateBinLogPO> find(int size) {
        String sql = "SELECT `id`, `requestId`, `from`, `processorId`, `receivedTimestamp`, `lastUpdateTimestamp`,\n" +
                "        `redoHandler`, `retryCount`, `domainId`,\n" +
                "        `appId`, `uuid`, `type`, `state`, `params`\n" +
                "        FROM `state_binlog`\n" +
                "        ORDER BY `lastUpdateTimestamp`\n" +
                "        LIMIT ?;";

        return find(sql, size, getPo);
    }

    BiConsumerWithSqlException<PreparedStatement, StateBinLogPO> savePO = (preparedStatement, event) -> {
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
    };

    FunctionWithSqlException<ResultSet, StateBinLogPO> getPo = resultSet -> {
        StateBinLogPO po = new StateBinLogPO();

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

        return po;
    };

}
