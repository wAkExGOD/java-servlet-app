package org.example.demo.service;

import org.example.demo.config.DBConfig;

import java.sql.Connection;
import java.sql.SQLException;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

public class DBConnection {
    private static HikariDataSource dataSource;

    static {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(DBConfig.get("db.url"));
        config.setUsername(DBConfig.get("db.username"));
        config.setPassword(DBConfig.get("db.password"));

        config.setDriverClassName("com.mysql.jdbc.Driver");
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        config.setMaximumPoolSize(10);

        // Для добавления Proxy:
        // config.addDataSourceProperty("proxyHost", "your.proxy.host");
        // config.addDataSourceProperty("proxyPort", "yourProxyPort");

        dataSource = new HikariDataSource(config);
    }

    public static Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}


