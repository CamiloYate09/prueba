package com.example.prueba.Datasource;

import com.example.prueba.config.ConfiguracionAsincrona;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;


/**
 * https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#howto-two-datasources
 * Informacion de como implementar los Datasource
 */
@Configuration
@PropertySource({ "classpath:application.properties" })
public class DatabaseConfig {
    Logger logger = LoggerFactory.getLogger(DatabaseConfig.class);
    @Bean
    @Primary
    @ConfigurationProperties("app.datasource")
    public DataSourceProperties dataSourceProperties() {
        logger.info("****************************************** DatabaseConfig ");
        return new DataSourceProperties();

    }

    @Bean
    @ConfigurationProperties("app.datasource.configuration")
    public HikariDataSource dataSource(DataSourceProperties properties) {
        logger.info("****************************************** HikariDataSource ");
        return properties.initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }
}