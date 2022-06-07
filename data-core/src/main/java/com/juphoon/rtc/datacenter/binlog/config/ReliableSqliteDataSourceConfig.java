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
@MapperScan(basePackages = {"com.juphoon.rtc.datacenter.binlog.mapper.reliable"},
        sqlSessionTemplateRef = "reliableSqliteSessionTemplate")
public class ReliableSqliteDataSourceConfig {
    @Primary
    @Bean(name = "reliableSqliteDatasource")
    public DataSource strategyDataSource() throws SQLException {
        SQLiteDataSource sqLiteDataSource = new SQLiteDataSource();
        sqLiteDataSource.setUrl("jdbc:sqlite:reliable.db");

        SQLiteConfig config = new SQLiteConfig();
        // 开日志
//        config.setJournalMode(SQLiteConfig.JournalMode.PERSIST);
        // 开刷新
        config.setSynchronous(SQLiteConfig.SynchronousMode.NORMAL);

        sqLiteDataSource.setConfig(config);
        return sqLiteDataSource;
    }

    @Bean(name = "reliableSqliteSessionFactory")
    public SqlSessionFactory createSqlSessionFactoryBean(@Qualifier("reliableSqliteDatasource") DataSource dataSource) throws Exception {
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(resolver.getResources("classpath:sqlite/*.xml"));

        assert bean.getObject() != null;

        bean.getObject().getConfiguration().setMapUnderscoreToCamelCase(true);

        return bean.getObject();
    }

    @Bean(name = "reliableSqliteSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("reliableSqliteSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}