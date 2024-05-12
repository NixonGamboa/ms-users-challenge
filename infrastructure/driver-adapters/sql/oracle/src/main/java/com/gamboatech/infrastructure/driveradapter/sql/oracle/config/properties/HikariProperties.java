package com.gamboatech.infrastructure.driveradapter.sql.oracle.config.properties;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class HikariProperties {
    private int minimumIdle;
    private int maximumPoolSize;
    private int idleTimeout;
    private int maxLifetime;
    private int connectionTimeout;
}
