package org.example.FourthOctober.config;

import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

public final class ManagerConnection {
    private static final String URL_KEY = "db.url";
    private static final String USERNAME_KEY = "db.username";
    private static final String PASSWORD_KEY = "db.password";
    private static  PGSimpleDataSource dataSource = new PGSimpleDataSource();
    public static final JdbcTemplate jdbcTemplate = new JdbcTemplate(get());
    private ManagerConnection() {}
    public static DataSource get() {
        dataSource.setUrl(PropertiesUtil.getKey(URL_KEY));
        dataSource.setUser(PropertiesUtil.getKey(USERNAME_KEY));
        dataSource.setPassword(PropertiesUtil.getKey(PASSWORD_KEY));
        return dataSource;
    }

}
