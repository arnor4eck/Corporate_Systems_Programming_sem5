package com.arnor4eck.repository;

import java.nio.charset.StandardCharsets;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBase {
    private final DataSource dataSource;

    public DataBase(String jdbcUrl) throws SQLException {
        try {
            this.dataSource = initDataSource(jdbcUrl);
            initDb();
        } catch (Exception e) {
            throw new SQLException("Init db error", e.getMessage());
        }
    }

    private DataSource initDataSource(String jdbcUrl) {
        PGSimpleDataSource source = new PGSimpleDataSource();
        source.setUrl(jdbcUrl);
        source.setUser("username");
        source.setPassword("password");
        //TODO конфиг бд

        return source;
    }

    private void initDb() throws SQLException, IOException {
        try (InputStream in = DataBase.class.getResourceAsStream("/init.sql")) {
            if (in == null) {
                throw new IOException("Файл ресурса /init.sql не найден");
            }

            String prepareDB = new String(in.readAllBytes(), StandardCharsets.UTF_8);
            String[] scripts = prepareDB.split(";");

            try (Connection connection = this.getConnection();
                 Statement statement = connection.createStatement()) {

                for (String s : scripts) {
                    if (!s.trim().isEmpty()) {
                        statement.execute(s);
                    }
                }
            }
        }
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }
}