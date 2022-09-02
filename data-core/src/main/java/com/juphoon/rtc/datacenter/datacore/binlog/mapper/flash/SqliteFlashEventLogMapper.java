package com.juphoon.rtc.datacenter.datacore.binlog.mapper.flash;

import com.juphoon.rtc.datacenter.datacore.binlog.entity.EventBinLogPO;
import com.juphoon.rtc.datacenter.datacore.binlog.mapper.EventLogMapper;
import com.juphoon.rtc.datacenter.datacore.utils.ConnectionUtils;
import com.juphoon.rtc.datacenter.datacore.utils.SqliteUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.sqlite.SQLiteException;

import java.sql.SQLException;
import java.util.List;

import static com.juphoon.rtc.datacenter.datacore.JrtcDataCenterConstant.CONNECTION_CLOSED;
import static com.juphoon.rtc.datacenter.datacore.JrtcDataCenterConstant.LOCAL_DB_FILE_FLASH;

/**
 * TODO sqliteReliable和sqliteFlash只有连接是不同的 是否能做到不同连接复用同一套代码？
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

        String sql = " create table IF NOT EXISTS `event_binlog`\n" +
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
                "        );";
        try {
            SqliteUtils.createTable(CONNECTION, sql);
        } catch (SQLiteException e) {
            log.warn("创建表失败");
        } catch (SQLException e) {
            if (CONNECTION_CLOSED.equals(e.getMessage())) {
                CONNECTION = ConnectionUtils.restart(LOCAL_DB_FILE_FLASH);
                createTable();
            } else {
                throw e;
            }
        }
    }

    @Override
    public synchronized void dropTable() {
        String sql = " DROP TABLE IF EXISTS event_binlog;";
        try {
            SqliteUtils.dropTable(CONNECTION, sql);
        } catch (SQLiteException e) {
            log.warn("删除表失败");
        } catch (SQLException e) {
            if (CONNECTION_CLOSED.equals(e.getMessage())) {
                CONNECTION = ConnectionUtils.restart(LOCAL_DB_FILE_FLASH);
                dropTable();
            }
        }

    }

    @Override
    public synchronized int save(EventBinLogPO event) {
        String sql = " INSERT INTO `event_binlog`(`id`, `requestId`, `from`, `processorId`, `receivedTimestamp`, `lastUpdateTimestamp`," +
                "`redoHandler`," +
                "`retryCount`, `domainId`, `appId`, `uuid`, `type`, `number`, `timestamp`, `params`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            return SqliteUtils.save(CONNECTION, sql, event);
        } catch (SQLiteException e) {
            log.warn("保存事件失败,ec:{}", event.getId());
        } catch (SQLException e) {
            if (CONNECTION_CLOSED.equals(e.getMessage())) {
                CONNECTION = ConnectionUtils.restart(LOCAL_DB_FILE_FLASH);
                save(event);
            }
        }
        return 0;
    }

    @Override
    public synchronized int saveList(List<EventBinLogPO> list) {
        String sql = "INSERT INTO `event_binlog`(`id`, `requestId`, `from`, `processorId`, `receivedTimestamp`, `lastUpdateTimestamp`,\n" +
                "        `redoHandler`,\n" +
                "        `retryCount`, `domainId`, `appId`, `uuid`, `type`, `number`, `timestamp`, `params`) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        try {
            return SqliteUtils.saveList(CONNECTION, sql, list);
        } catch (SQLiteException e) {
            log.warn("批量保存事件失败");
        } catch (SQLException e) {
            if (CONNECTION_CLOSED.equals(e.getMessage())) {
                CONNECTION = ConnectionUtils.restart(LOCAL_DB_FILE_FLASH);
                saveList(list);
            }
        }
        return 0;
    }

    @Override
    public synchronized void remove(long id) {
        String sql = " DELETE FROM `event_binlog` WHERE `id`=?;";
        try {
            SqliteUtils.remove(CONNECTION, sql, id);
        } catch (SQLiteException e) {
            log.warn("删除事件失败,id:{}", id);
        } catch (SQLException e) {
            if (CONNECTION_CLOSED.equals(e.getMessage())) {
                CONNECTION = ConnectionUtils.restart(LOCAL_DB_FILE_FLASH);
                remove(id);
            }
        }
    }

    /**
     * 批量删除
     *
     * @param list
     */
    @Override
    public synchronized void remove(List<Long> list) {
        String sql = " DELETE FROM `event_binlog` WHERE `id`=?;";
        try {
            SqliteUtils.removeList(CONNECTION, sql, list);
        } catch (SQLiteException e) {
            log.warn("删除事件失败,list:{}", list.size());
        } catch (SQLException e) {
            if (CONNECTION_CLOSED.equals(e.getMessage())) {
                CONNECTION = ConnectionUtils.restart(LOCAL_DB_FILE_FLASH);
                remove(list);
            }
        }
    }

    @Override
    public synchronized void updateRetryCount(EventBinLogPO po) {
        String sql = "UPDATE `event_binlog` set `retryCount` = `retryCount` + 1,\n" +
                "`lastUpdateTimestamp` = ?  WHERE `id`= ?;";
        try {
            SqliteUtils.updateRetryCount(CONNECTION, sql, po.getLastUpdateTimestamp(), po.getId());
        } catch (SQLiteException e) {
            log.warn("更新重试次数失败,ec:{}", po.getId());
        } catch (SQLException e) {
            if (CONNECTION_CLOSED.equals(e.getMessage())) {
                CONNECTION = ConnectionUtils.restart(LOCAL_DB_FILE_FLASH);
                updateRetryCount(po);
            }
        }
    }

    @Override
    public synchronized EventBinLogPO findById(long contentId) {
        EventBinLogPO po = null;
        String sql = " SELECT `id`, `requestId`, `from`, `processorId`, `receivedTimestamp`, `lastUpdateTimestamp`,\n" +
                "        `redoHandler`, `retryCount`, `domainId`, `appId`, `uuid`, `type`, `number`, `timestamp`, `params`\n" +
                "        FROM `event_binlog`\n" +
                "        WHERE `id`= ?;";
        try {
            po = SqliteUtils.findById(CONNECTION, sql, contentId);
        } catch (SQLiteException e) {
            log.warn("根据id查找失败,id:{}", contentId);
        } catch (SQLException e) {
            if (CONNECTION_CLOSED.equals(e.getMessage())) {
                CONNECTION = ConnectionUtils.restart(LOCAL_DB_FILE_FLASH);
                findById(contentId);
            }
        }
        return po;
    }

    @Override
    public synchronized List<EventBinLogPO> find(int size) {
        String sql = "SELECT `id`, `requestId`, `from`, `processorId`, `receivedTimestamp`, `lastUpdateTimestamp`,\n" +
                "        `redoHandler`, `retryCount`, `domainId`,\n" +
                "        `appId`, `uuid`, `type`, `number`, `timestamp`, `params`\n" +
                "        FROM `event_binlog`\n" +
                "        ORDER BY `lastUpdateTimestamp`\n" +
                "        LIMIT ?;";
        try {
            return SqliteUtils.find(CONNECTION, sql, size);
        } catch (SQLiteException e) {
            log.warn("查找失败");
        } catch (SQLException e) {
            if (CONNECTION_CLOSED.equals(e.getMessage())) {
                CONNECTION = ConnectionUtils.restart(LOCAL_DB_FILE_FLASH);
                find(size);
            }
        }
        return null;
    }

    @Override
    public void stop() {
        ConnectionUtils.closeConnection(CONNECTION);
    }
}
