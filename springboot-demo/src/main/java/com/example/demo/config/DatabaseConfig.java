package com.example.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;

//@Configuration
//@PropertySources({
//        @PropertySource( value = "file:c:/dev/config.properties", ignoreResourceNotFound = true ),
//        @PropertySource( value = "file:${user.home}/env/config.properties", ignoreResourceNotFound = true)
//})
public abstract class DatabaseConfig {

    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    protected void configureDataSource(org.apache.tomcat.jdbc.pool.DataSource dataSource) {

        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setTestOnBorrow(false);
        dataSource.setTestOnReturn(false);
    }

}
