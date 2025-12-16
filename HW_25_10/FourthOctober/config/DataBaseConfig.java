package org.example.FourthOctober.config;

import lombok.experimental.UtilityClass;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@UtilityClass
public class DataBaseConfig {

    public static final JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource());

    private static DataSource dataSource() {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl("jdbc:postgresql://localhost:5432/chatting");
        dataSource.setUser("chatting");
        dataSource.setPassword("qwerty007");
        return dataSource;
    }
}