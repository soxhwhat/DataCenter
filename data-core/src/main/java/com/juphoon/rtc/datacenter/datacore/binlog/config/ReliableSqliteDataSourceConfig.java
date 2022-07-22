package com.juphoon.rtc.datacenter.datacore.binlog.config;

import com.juphoon.rtc.datacenter.datacore.utils.FileUtils;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.sqlite.SQLiteConfig;
import org.sqlite.SQLiteDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;

import static com.juphoon.rtc.datacenter.datacore.JrtcDataCenterConstant.LOCAL_DB_FILE_BASE_PATH;
import static com.juphoon.rtc.datacenter.datacore.JrtcDataCenterConstant.LOCAL_DB_FILE_RELIABLE;

/**
 * 客服统计事件
 *
 * @author ajian.zheng@juphoon.com
 * @date 5/9/22 8:25 PM
 */
@Configuration
@MapperScan(basePackages = {"com.juphoon.rtc.datacenter.datacore.binlog.mapper.reliable"},
        sqlSessionTemplateRef = "reliableSqliteSessionTemplate")
public class ReliableSqliteDataSourceConfig {
    /**
     * https://easeapi.com/blog/blog/151-sqlite-pragma.html
     *
     * journal_mode有如下选择：
     *
     * OFF：不保留日志；
     * DELETE：事务结束，文件删除（默认采取DELETE模式）；
     * WAL：write ahead log；
     * MEMORY：日志文件存储在内存中；
     * TRUNCATE:
     * PERSIST:
     *
     * synchronous：文件同步方式
     * OFF：交由操作系统处理，性能最高，但在崩溃或断电时数据库很可能会损坏；
     * NORMAL：不像FULL那么频繁操作sync，有小概率会损坏数据库；
     * FULL：在关键磁盘操作后sync，性能差，到可以确保在崩溃或断电时数据库不会被损坏。
     *
     * @return
     * @throws SQLException
     */
    @Primary
    @Bean(name = "reliableSqliteDatasource")
    public DataSource strategyDataSource() throws SQLException {
        String dbPath = System.getProperty("user.dir") + LOCAL_DB_FILE_BASE_PATH;
        FileUtils.createDir(dbPath);

        SQLiteDataSource sqLiteDataSource = new SQLiteDataSource();
        sqLiteDataSource.setUrl("jdbc:sqlite:" + dbPath + LOCAL_DB_FILE_RELIABLE);

        SQLiteConfig config = new SQLiteConfig();
        // 开日志
        config.setJournalMode(SQLiteConfig.JournalMode.WAL);
        // 开刷新
        config.setSynchronous(SQLiteConfig.SynchronousMode.FULL);
        // 文件锁
        config.setLockingMode(SQLiteConfig.LockingMode.NORMAL);

        config.setBusyTimeout(500);

        sqLiteDataSource.setConfig(config);
        return sqLiteDataSource;
    }

    @Primary
    @Bean(name = "reliableSqliteSessionFactory")
    public SqlSessionFactory createSqlSessionFactoryBean(@Qualifier("reliableSqliteDatasource") DataSource dataSource) throws Exception {
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(resolver.getResources("classpath:sqlite/reliable/*.xml"));

        assert bean.getObject() != null;

        bean.getObject().getConfiguration().setMapUnderscoreToCamelCase(true);

        return bean.getObject();
    }

    @Primary
    @Bean(name = "reliableSqliteSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("reliableSqliteSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}