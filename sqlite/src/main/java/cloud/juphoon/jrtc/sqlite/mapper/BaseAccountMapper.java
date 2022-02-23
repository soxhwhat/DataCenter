package cloud.juphoon.jrtc.sqlite.mapper;

import cloud.juphoon.jrtc.sqlite.entity.Account;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Zhiwei.zhai
 * @Date: 2022/2/23 14:37
 * @Description:
 */
@Slf4j
@Component
public class BaseAccountMapper {

    public Map<String, PreparedStatement> preparedStatementMap = new HashMap<>();
    Connection connection;

    {
        try {
            connection = DriverManager.getConnection("jdbc:sqlite:a.db?synchronous=full");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @SneakyThrows
    private PreparedStatement getPs(String sql) {
        PreparedStatement preparedStatement = preparedStatementMap.get(sql);
        if (preparedStatement == null) {
            log.info("创建ps:{}",sql);
            PreparedStatement ps = connection.prepareStatement(sql);
            preparedStatementMap.put(sql, ps);
            return ps;
        } else {
            log.info("从缓存中获取ps:{}",sql);
            return preparedStatement;
        }
    }

    @SneakyThrows
    public void createTable() {
        String createTableSql = "create table t_account\n" +
                "(\n" +
                "      id   INTEGER,\n" +
                "      account INTEGER\n" +
                ");";
        getPs(createTableSql).execute();
    }

    public List<Map> selectInnerJoin(String account) {
        return null;
    }

    public List<Map> selectA(String account) {
        return null;
    }

    @SneakyThrows
    public void insertBatch(List<Account> accounts) {
        StringBuilder sb = new StringBuilder();
        if (accounts.size() == 5){
            sb.append("insert into t_account values (?,?),(?,?),(?,?),(?,?),(?,?)");
        }else{
            sb.append("insert into t_account values (");
            for (int i = 0; i < accounts.size(); i++) {
                sb.append("?,?");
                if (i == (accounts.size() - 1)) {
                    sb.append(")");
                }else {
                    sb.append("),(");
                }
            }
        }

        String insertBatchSql = sb.toString();
        PreparedStatement ps = getPs(insertBatchSql);
        ps.clearParameters();
        for (int i = 0; i < accounts.size(); i++) {
            Account account = accounts.get(i);
            ps.setInt(2*i+1,account.getId());
            ps.setInt(2*i+2,account.getId());
        }
        ps.execute();
    }

    @SneakyThrows
    public void insert(Account account) {
        String sql = "insert into t_account values(?,?)";
        PreparedStatement ps = getPs(sql);
        ps.clearParameters();
        ps.setInt(0,account.getId());
        ps.setInt(0,account.getAccount());
        ps.execute();
    }

    @SneakyThrows
    public void deleteByAccount(String account) {
        String sql = "delte from t_account where account = ?";
        PreparedStatement ps = getPs(sql);
        ps.clearParameters();
        ps.setInt(0,Integer.valueOf(account));
        ps.execute();
    }

    @SneakyThrows
    public void deleteById(String id) {
        String sql = "delte from t_account where id = ?";
        PreparedStatement ps = getPs(sql);
        ps.clearParameters();
        ps.setInt(0,Integer.valueOf(id));
        ps.execute();
    }


    public void deleteAll() {
    }

    public Integer selectCount() {
        return null;
    }
}
