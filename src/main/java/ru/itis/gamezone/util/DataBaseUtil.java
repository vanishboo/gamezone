package ru.itis.gamezone.util;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

public class DataBaseUtil {

    private static  HikariDataSource ds;
    private static  String url;
    private static  String username;
    private static  String password;


    public static void init() {

        try {
            Class.forName("org.postgresql.Driver");

        Properties prop = new Properties();
        InputStream is = DataBaseUtil.class.getClassLoader()
                .getResourceAsStream("db.properties");
        prop.load(is);

        url = prop.getProperty("url");
        username = prop.getProperty("username");
        password = prop.getProperty("password");

        HikariConfig config = new HikariConfig();

        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);
        config.setDriverClassName("org.postgresql.Driver");
        config.setMaximumPoolSize(10);
        config.setMinimumIdle(2);
        config.setConnectionTimeout(30000);
        config.setIdleTimeout(600000);
        config.setMaxLifetime(1800000);
        ds = new HikariDataSource(config);


    } catch(IOException |
    ClassNotFoundException e)
    {
        throw new RuntimeException(e);
    }
    }

    public static Connection getConnection() throws SQLException {
        return ds.getConnection();
    }

    public static void closePool() throws SQLException {

        if (ds != null) {
            ds.close();
        }
    }
}
