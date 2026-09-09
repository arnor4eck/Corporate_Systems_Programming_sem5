package com.arnor4eck.repository;

import com.arnor4eck.model.Employee;
import com.arnor4eck.util.enums.Role;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class EmployeeRepository extends AbstractJDBCRepository<Employee>{

    public EmployeeRepository(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    public Employee get(int id) {
        String sql = "SELECT id, full_name, role," +
                " password_hash, is_active, created_at" +
                " FROM employee WHERE id = ?";
        try (Connection conn = this.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()){
                return mapRow(rs);
            }
        }
        catch (SQLException e){
            throw new RuntimeException(String.format(
                    "SQL exception: %s", e.getMessage()
            ));
        }
    }

    @Override
    public Collection<Employee> getAll() {
        String sql = "SELECT id, full_name, role," +
                "password_hash, is_active, created_at" +
                "FROM employee";
        List<Employee> list = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()){
            while (rs.next()){
                list.add(mapRow(rs));
            }
        }
        catch (SQLException e){
            throw new RuntimeException(String.format(
                    "SQL exception: %s", e.getMessage()
            ));
        }
        return list;
    }

    @Override
    public void save(Employee value) {
        String sql = "INSERT INTO employee(full_name, role, password_hash, is_active, created_at) VALUES(?, ?, ?, ?, ?);";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, value.fullName());
            stmt.setString(2, value.role().toString());
            stmt.setString(3, value.passwordHash());
            stmt.setBoolean(4, value.isActive());
            stmt.setTimestamp(5, Timestamp.valueOf(value.createdAt()));
            stmt.executeUpdate();
        }
        catch (SQLException e){
            throw new RuntimeException(String.format(
                    "SQL exception: %s", e.getMessage()
            ));
        }
    }

    @Override
    public void delete(int id) {
        String sql = "DELETE FROM employees WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
        catch (SQLException e){
            throw new RuntimeException(String.format(
                    "SQL exception: %s", e.getMessage()
            ));
        }

    }

    @Override
    protected Employee mapRow(ResultSet rs) throws SQLException {
        return new Employee(
                rs.getInt("id"),
                rs.getString("full_name"),
                Role.fromString(rs.getString("role")),
                rs.getString("password_hash"),
                rs.getBoolean("is_active"),
                rs.getObject("created_at", LocalDateTime.class)
        );
    }
}
