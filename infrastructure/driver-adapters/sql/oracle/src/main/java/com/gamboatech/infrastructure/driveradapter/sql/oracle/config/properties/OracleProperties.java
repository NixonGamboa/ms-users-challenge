package com.gamboatech.infrastructure.driveradapter.sql.oracle.config.properties;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OracleProperties {
    private String url;
    private String username;
    private String password;
    private HikariProperties hikari;
}
