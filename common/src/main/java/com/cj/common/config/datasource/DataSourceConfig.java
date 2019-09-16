package com.cj.common.config.datasource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * 数据源配置
 *
 */
//@Configuration
@EnableScheduling
@Slf4j
public class DataSourceConfig {

    @Autowired(required = false)
    private DBProperties properties;

    @Bean(name = "dataSource")
    public DataSource dataSource() {
        //按照目标数据源名称和目标数据源对象的映射存放在Map中
        Map<Object, Object> targetDataSources = new HashMap<>();
        targetDataSources.put("frame", properties.getFrame());
        targetDataSources.put("shentu", properties.getShentu());
        targetDataSources.put("oilgas", properties.getOilgas());
        //采用是想AbstractRoutingDataSource的对象包装多数据源
        DynamicDataSource dataSource = new DynamicDataSource();
        dataSource.setTargetDataSources(targetDataSources);
        //设置默认的数据源，当拿不到数据源时，使用此配置
        dataSource.setDefaultTargetDataSource(properties.getOilgas());

        return dataSource;
    }

    @Bean
    public PlatformTransactionManager txManager() {
        return new DataSourceTransactionManager(dataSource());
    }

}