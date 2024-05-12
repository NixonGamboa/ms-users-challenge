package com.gamboatech.infrastructure.driveradapter.sql.oracle.config;

import com.gamboatech.infrastructure.driveradapter.sql.oracle.config.properties.OracleProperties;
import oracle.jdbc.pool.OracleDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
@EnableJpaRepositories(basePackages = {"com.gamboatech.infrastructure.driveradapter.sql.oracle"})
@EntityScan(basePackages = {"com.gamboatech.infrastructure.driveradapter.sql.oracle.entities"})
public class OracleConfig {
    @Autowired
    private Environment env;

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public OracleProperties getOracleProperties() {
        return new OracleProperties();
    }

    @Bean
    public DataSource dataSource(OracleProperties oracleProperties) throws SQLException {
        OracleDataSource dataSource = new OracleDataSource();
        dataSource.setUser(oracleProperties.getUsername());
        dataSource.setPassword(oracleProperties.getPassword());
        dataSource.setURL(oracleProperties.getUrl());
        dataSource.setImplicitCachingEnabled(true);

        return dataSource;
    }
}
