package com.arnor4eck.repository;

import com.arnor4eck.model.Request;
import com.arnor4eck.util.enums.RequestStatus;

import javax.sql.DataSource;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class RequestRepository extends AbstractJDBCIRepository<Request> {
    public RequestRepository(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    protected Request mapRow(ResultSet rs) throws SQLException {
        return new Request(
                rs.getInt("id"),
                rs.getInt("customer_id"),
                rs.getInt("employee_id"),
                rs.getInt("plot_id"),
                rs.getString("deceased_full_name"),
                rs.getObject("deceased_birthday", LocalDate.class),
                rs.getObject("deceased_deathday", LocalDate.class),
                rs.getString("deceased_certificate"),
                RequestStatus.fromString(rs.getString("status")),
                rs.getString("totalCost"),
                rs.getString("note"),
                rs.getObject("created_at", LocalDateTime.class)
        );
    }
    @Override
    public Optional<Request> get(int id) {
        String sql = "SELECT id, customer_id, employee_id," +
                " plot_id, deceased_full_name, deceased_birthday" +
                "deceased_birthday, deceased_deathday, deceased_certificate" +
                "status, total_cost, note, created_at" +
                " FROM requests WHERE id = ?";
        try (Connection conn = this.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()){
                if (rs.next()) {
                    return Optional.empty();
                }
                return Optional.of(mapRow(rs));
            }
        }
        catch (SQLException e){
            throw new RuntimeException(String.format(
                    "SQL exception: %s", e.getMessage()
            ));
        }
    }

    @Override
    public Collection<Request> getAll() {
        String sql = "SELECT id, customer_id, employee_id," +
                " plot_id, deceased_full_name, deceased_birthday" +
                "deceased_birthday, deceased_deathday, deceased_certificate" +
                "status, total_cost, note, created_at" +
                "FROM requests";
        List<Request> list = new ArrayList<>();
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
    public void save(Request value) {
        String sql =
                "INSERT INTO requests(id, customer_id, employee_id," +
                " plot_id, deceased_full_name, deceased_birthday" +
                "deceased_birthday, deceased_deathday, deceased_certificate" +
                "status, total_cost, note, created_at" +
                "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, value.costumerId());
            stmt.setInt(2, value.employeeId());
            stmt.setInt(3, value.plotId());

            stmt.setString(4, value.deceasedFullName());
            stmt.setDate(5, Date.valueOf(value.deceasedBirthday()));
            stmt.setDate(6, Date.valueOf(value.deceasedDeathday()));

            stmt.setString(7, value.deceasedCertificate());
            stmt.setString(8, value.status().toString());
            stmt.setString(9, value.totalCost());
            stmt.setString(10, value.note());

            stmt.setTimestamp(11, Timestamp.valueOf(value.createdAt()));
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
        String sql = "DELETE FROM requests WHERE id = ?";
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

    
}
