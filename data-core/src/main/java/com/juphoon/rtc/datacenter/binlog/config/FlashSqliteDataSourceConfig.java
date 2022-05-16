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
@MapperScan(basePackages = {"com.juphoon.rtc.datacenter.binlog.mapper"},
        sqlSessionTemplateRef = "flashSqliteSessionTemplate")
public class FlashSqliteDataSourceConfig {
    @Primary
    @Bean(name = "flashSqliteDatasource")
    public DataSource strategyDataSource() throws SQLException {
        SQLiteDataSource sqLiteDataSource = new SQLiteDataSource();
        sqLiteDataSource.setUrl("jdbc:sqlite:flash.db");

        SQLiteConfig config = new SQLiteConfig();
        // 关日志
        config.setJournalMode(SQLiteConfig.JournalMode.OFF);
        // 关刷新
        config.setSynchronous(SQLiteConfig.SynchronousMode.OFF);

        sqLiteDataSource.setConfig(config);
        return sqLiteDataSource;
    }

    @Bean(name = "flashSqliteSessionFactory")
    public SqlSessionFactory createSqlSessionFactoryBean(@Qualifier("flashSqliteDatasource") DataSource dataSource) throws Exception {
        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();

        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(resolver.getResources("classpath:sqlite/*.xml"));

        assert bean.getObject() != null;

        bean.getObject().getConfiguration().setMapUnderscoreToCamelCase(true);

        return bean.getObject();
    }

    @Bean(name = "flashSqliteSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("flashSqliteSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}