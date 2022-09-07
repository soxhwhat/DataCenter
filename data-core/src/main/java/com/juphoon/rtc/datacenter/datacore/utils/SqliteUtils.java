//package com.juphoon.rtc.datacenter.datacore.utils;
//
//import com.juphoon.rtc.datacenter.datacore.binlog.entity.EventBinLogPO;
//import lombok.extern.slf4j.Slf4j;
//
//import java.sql.Connection;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//
//import static com.juphoon.rtc.datacenter.datacore.JrtcDataCenterConstant.CACHE_NUMBER;
//
///**
// * <p>在开始处详细描述该类的作用</p>
// * <p>描述请遵循 javadoc 规范</p>
// * <p>TODO</p>
// *
// * @author haijie.liang@juphoon.com
// * @date 2022/9/1 17:12
// * @update [序号][日期YYYY-MM-DD] [更改人姓名][变更描述]
// */
//@Slf4j
//public class SqliteUtils {
//
//
//    public static void createTable(Connection connection, String sql) throws SQLException {
//        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
//            preparedStatement.executeUpdate();
//        }
//    }
//
//    public static void dropTable(Connection connection, String sql) throws SQLException {
//        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
//            preparedStatement.executeUpdate();
//        }
//    }
//
//    public static int save(Connection connection, String sql, EventBinLogPO event) throws SQLException {
//        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
//            preparedStatement.setLong(1, event.getId());
//            preparedStatement.setString(2, event.getRequestId());
//            preparedStatement.setString(3, event.getFrom());
//            preparedStatement.setString(4, event.getProcessorId());
//            preparedStatement.setLong(5, event.getReceivedTimestamp());
//            preparedStatement.setLong(6, event.getLastUpdateTimestamp());
//            preparedStatement.setString(7, event.getRedoHandler());
//            preparedStatement.setInt(8, event.getRetryCount());
//            preparedStatement.setInt(9, event.getDomainId());
//            preparedStatement.setInt(10, event.getAppId());
//            preparedStatement.setString(11, event.getUuid());
//            preparedStatement.setInt(12, event.getType());
//            preparedStatement.setInt(13, event.getNumber());
//            preparedStatement.setLong(14, event.getTimestamp());
//            preparedStatement.setString(15, event.getParams());
//            return preparedStatement.executeUpdate();
//        }
//    }
//
//    public static int saveList(Connection connection, String sql, List<EventBinLogPO> list) throws SQLException {
//        int count = 0;
//        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
//            for (int i = 0; i < list.size(); i++) {
//                EventBinLogPO po = list.get(i);
//                preparedStatement.setLong(1, po.getId());
//                preparedStatement.setString(2, po.getRequestId());
//                preparedStatement.setString(3, po.getFrom());
//                preparedStatement.setString(4, po.getProcessorId());
//                preparedStatement.setLong(5, po.getReceivedTimestamp());
//                preparedStatement.setLong(6, po.getLastUpdateTimestamp());
//                preparedStatement.setString(7, po.getRedoHandler());
//                preparedStatement.setInt(8, po.getRetryCount());
//                preparedStatement.setInt(9, po.getDomainId());
//                preparedStatement.setInt(10, po.getAppId());
//                preparedStatement.setString(11, po.getUuid());
//                preparedStatement.setInt(12, po.getType());
//                preparedStatement.setInt(13, po.getNumber());
//                preparedStatement.setLong(14, po.getTimestamp());
//                preparedStatement.setString(15, po.getParams());
//                preparedStatement.addBatch();
//                if (i % CACHE_NUMBER == 0) {
//                    count = Arrays.stream(preparedStatement.executeBatch()).reduce(count, Integer::sum);
//                    preparedStatement.clearBatch();
//                }
//            }
//            count = Arrays.stream(preparedStatement.executeBatch()).reduce(count, Integer::sum);
//            preparedStatement.clearBatch();
//            return count;
//        }
//    }
//
//    public static void remove(Connection connection, String sql, Long id) throws SQLException {
//        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
//            preparedStatement.setLong(1, id);
//            preparedStatement.executeUpdate();
//        }
//    }
//
//    public static void removeList(Connection connection, String sql, List<Long> list) throws SQLException {
//        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
//
//            for (int i = 0; i < list.size(); i++) {
//                stmt.setLong(1, list.get(i));
//                stmt.addBatch();
//                if (i % CACHE_NUMBER == 0) {
//                    stmt.executeBatch();
//                    stmt.clearBatch();
//                }
//            }
//
//            stmt.executeBatch();
//            stmt.clearBatch();
//        }
//    }
//
//    public static void updateRetryCount(Connection connection, String sql, long lastUpdateTimestamp, long id) throws SQLException {
//        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
//            preparedStatement.setLong(1, lastUpdateTimestamp);
//            preparedStatement.setLong(2, id);
//            preparedStatement.executeUpdate();
//        }
//    }
//
//    public static EventBinLogPO findById(Connection connection, String sql, long contentId) throws SQLException {
//        EventBinLogPO po = null;
//        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
//            preparedStatement.setLong(1, contentId);
//            ResultSet resultSet = preparedStatement.executeQuery();
//            if (resultSet.next()) {
//                po = new EventBinLogPO();
//                po.setId(resultSet.getLong("id"));
//                po.setRequestId(resultSet.getString("requestId"));
//                po.setFrom(resultSet.getString("from"));
//                po.setProcessorId(resultSet.getString("processorId"));
//                po.setReceivedTimestamp(resultSet.getLong("receivedTimestamp"));
//                po.setLastUpdateTimestamp(resultSet.getLong("lastUpdateTimestamp"));
//                po.setRedoHandler(resultSet.getString("redoHandler"));
//                po.setRetryCount(resultSet.getInt("retryCount"));
//                po.setDomainId(resultSet.getInt("domainId"));
//                po.setAppId(resultSet.getInt("appId"));
//                po.setUuid(resultSet.getString("uuid"));
//                po.setType(resultSet.getInt("type"));
//                po.setNumber(resultSet.getInt("number"));
//                po.setTimestamp(resultSet.getLong("timestamp"));
//                po.setParams(resultSet.getString("params"));
//            }
//            resultSet.close();
//            return po;
//        }
//    }
//
//    public static List<EventBinLogPO> find(Connection connection, String sql, int size) throws SQLException {
//        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
//            preparedStatement.setInt(1, size);
//            ResultSet resultSet = preparedStatement.executeQuery();
//            List<EventBinLogPO> list = new ArrayList<>();
//            while (resultSet.next()) {
//                EventBinLogPO po = new EventBinLogPO();
//                po.setId(resultSet.getLong("id"));
//                po.setRequestId(resultSet.getString("requestId"));
//                po.setFrom(resultSet.getString("from"));
//                po.setProcessorId(resultSet.getString("processorId"));
//                po.setReceivedTimestamp(resultSet.getLong("receivedTimestamp"));
//                po.setLastUpdateTimestamp(resultSet.getLong("lastUpdateTimestamp"));
//                po.setRedoHandler(resultSet.getString("redoHandler"));
//                po.setRetryCount(resultSet.getInt("retryCount"));
//                po.setDomainId(resultSet.getInt("domainId"));
//                po.setAppId(resultSet.getInt("appId"));
//                po.setUuid(resultSet.getString("uuid"));
//                po.setType(resultSet.getInt("type"));
//                po.setNumber(resultSet.getInt("number"));
//                po.setTimestamp(resultSet.getLong("timestamp"));
//                po.setParams(resultSet.getString("params"));
//                list.add(po);
//            }
//            resultSet.close();
//            return list;
//        }
//    }
//
//}
