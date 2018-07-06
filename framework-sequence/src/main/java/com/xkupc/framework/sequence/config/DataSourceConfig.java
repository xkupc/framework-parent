package com.xkupc.framework.sequence.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * @author xk
 * @create 2018-07-06 14:47
 * @description
 **/
@Configuration
public class DataSourceConfig {
    @Bean(name = "primaryDataSource")
    @ConfigurationProperties(prefix = "spring.datasource.primary")
    public DataSource dataSourceOpenApi() {
        return DruidDataSourceBuilder.create().build();
    }
}
