package com.arnor4eck.repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public abstract class AbstractJDBCRepository<T> implements Repository<T> {
    protected final DataSource dataSource;

    public AbstractJDBCRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    protected Connection getConnection() throws SQLException{
        return dataSource.getConnection();
    }

    protected abstract T mapRow(ResultSet rs) throws SQLException;
}
