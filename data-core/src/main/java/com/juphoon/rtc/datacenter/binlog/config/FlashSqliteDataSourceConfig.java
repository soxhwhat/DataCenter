package com.juphoon.rtc.datacenter.binlog.config;

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

/**
 * 客服统计事件
 *
 * @author ajian.zheng@juphoon.com
 * @date 5/9/22 8:25 PM
 */
@Configuration
@MapperScan(basePackages = {"com.juphoon.rtc.datacenter.binlog.mapper.flash"},
        sqlSessionTemplateRef = "flashSqliteSessionTemplate")
public class FlashSqliteDataSourceConfig {
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
     * locking_mode：文件锁
     * 在缺省NORMAL模式下，一个Connection会在每一次读事务开始时获取共享锁，写事务开始时获取排它锁。每一次读写事务完成时释放文件锁。
     * 在EXCLUSIVE模式下，一个Connection会始终持有文件锁，直到Connection结束。这样会阻止其它进程访问数据库文件。在EXCLUSIVE模式下，读写减少了对文件锁的持有，性能会稍好一些。
     *
     * @return
     * @throws SQLException
     */
    @Bean(name = "flashSqliteDatasource")
    public DataSource strategyDataSource() throws SQLException {
        SQLiteDataSource sqLiteDataSource = new SQLiteDataSource();
        sqLiteDataSource.setUrl("jdbc:sqlite:flash.db");

        SQLiteConfig config = new SQLiteConfig();
        // 关日志
        config.setJournalMode(SQLiteConfig.JournalMode.OFF);
        // 关刷新
        config.setSynchronous(SQLiteConfig.SynchronousMode.OFF);
        // 文件锁
        config.setLockingMode(SQLiteConfig.LockingMode.EXCLUSIVE);

        config.setBusyTimeout(500);

        sqLiteDataSource.setConfig(config);
        return sqLiteDataSource;
    }

    @Bean(name = "flashSqliteSessionFactory")
    public SqlSessionFactory createSqlSessionFactoryBean(@Qualifier("flashSqliteDatasource") DataSource dataSource) throws Exception {
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(resolver.getResources("classpath:sqlite/flash/*.xml"));

        assert bean.getObject() != null;

        bean.getObject().getConfiguration().setMapUnderscoreToCamelCase(true);

        return bean.getObject();
    }

    @Bean(name = "flashSqliteSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("flashSqliteSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}