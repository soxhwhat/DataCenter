package com.juphoon.rtc.datacenter.servicecore.configuration;

import com.alibaba.druid.pool.DruidDataSource;
import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;
import com.juphoon.rtc.datacenter.servicecore.property.DataCenterProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

import static com.juphoon.rtc.datacenter.datacore.JrtcDataCenterConstant.DATABASE_TYPE_MYSQL;

/**
 * 客服统计事件
 *
 * @author ajian.zheng@juphoon.com
 * @date 5/9/22 8:25 PM
 */
@Slf4j
@Configuration
@MapperScan(basePackages = {"com.juphoon.rtc.datacenter.servicecore.mapper"}, sqlSessionTemplateRef = "sqlSessionTemplate")
public class RdbDataSourceConfig {

    @Autowired
    private DataCenterProperties properties;

    @Bean(name = "dataSource")
    public DataSource dataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(properties.getDataSource().getUrl());
        dataSource.setDriverClassName(properties.getDataSource().getDriverClassName());
        dataSource.setUsername(properties.getDataSource().getUsername());
        dataSource.setPassword(properties.getDataSource().getPassword());
        dataSource.setInitialSize(properties.getDataSource().getInitialSize());
        dataSource.setMinIdle(properties.getDataSource().getMinIdle());
        dataSource.setMaxActive(properties.getDataSource().getMaxActive());
        dataSource.setTestOnBorrow(properties.getDataSource().isTestOnBorrow());
        dataSource.setTestWhileIdle(properties.getDataSource().isTestWhileIdle());
        return dataSource;
    }

    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dataSource") DataSource dataSource) throws Exception {
        MybatisSqlSessionFactoryBean bean = new MybatisSqlSessionFactoryBean();
        bean.setDataSource(dataSource);

        // 默认mysql, 否则oracle，只支持2种
        if (properties.getDataSource().getDriverClassName().toLowerCase().contains(DATABASE_TYPE_MYSQL)) {
            bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:mysql/*Mapper.xml"));
        } else {
            bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath*:oracle/*Mapper.xml"));
        }

        return bean.getObject();
    }

    @Bean(name = "transactionManager")
    public DataSourceTransactionManager transactionManager(@Qualifier("dataSource") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "sqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(@Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
        return new SqlSessionTemplate(sqlSessionFactory);
    }
}