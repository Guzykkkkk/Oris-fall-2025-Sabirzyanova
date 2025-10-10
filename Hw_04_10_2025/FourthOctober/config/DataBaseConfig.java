package org.example.FourthOctober.config;


import lombok.experimental.UtilityClass;
import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;


@UtilityClass
public class DataBaseConfig {

    public final JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource());

    private static DataSource dataSource() {
        PGSimpleDataSource dataSource = new PGSimpleDataSource();
        dataSource.setUrl("jdbc:postgresql://localhost:5432/postgres");
        dataSource.setUser("postgres");
        dataSource.setPassword("Almazcuzel2006");
        return dataSource;
    }
}
