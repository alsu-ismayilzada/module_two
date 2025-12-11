package com.example.module_two.task16.config;

import com.example.module_two.task16.exception.DBConnectionException;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

public class DBConfig {

    private static final DataSource dataSource;

    static {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:postgresql://localhost:5444/car");
        config.setDriverClassName("org.postgresql.Driver");
        config.setUsername("postgres");
        config.setPassword("postgres");
        config.setSchema("module_two");
        config.setAutoCommit(true);
        config.setMinimumIdle(2);
        config.setIdleTimeout(40000);
        config.setMaximumPoolSize(10);
        config.setConnectionTimeout(10000);
        config.setKeepaliveTime(30000);
        dataSource = new HikariDataSource(config);
    }

    public static Connection getConnection() {
        try {
            return dataSource.getConnection();
        } catch (SQLException connectionException) {
            throw new DBConnectionException("Could not connect to DB", connectionException);
        }
    }
}
