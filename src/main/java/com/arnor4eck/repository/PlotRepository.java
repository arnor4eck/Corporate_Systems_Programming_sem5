package com.arnor4eck.repository;

import com.arnor4eck.model.Plot;
import com.arnor4eck.util.enums.PlotStatus;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class PlotRepository extends AbstractJDBCRepository<Plot> {
    public PlotRepository(DataSource dataSource) {
        super(dataSource);
    }

    @Override
    protected Plot mapRow(ResultSet rs) throws SQLException {
        return new Plot(
                rs.getInt("id"),
                rs.getInt("sector_id"),
                rs.getInt("row_number"),
                rs.getInt("plot_number"),
                PlotStatus.fromString(rs.getString("plot_status")),
                rs.getFloat("length_cm"),
                rs.getFloat("width_сm"),
                rs.getString("coordinates")
        );
    }
    @Override
    public Optional<Plot> get(int id) {
        String sql = "SELECT id, sector_id, row_number," +
                " plot_number, plot_status, length_cm, " +
                "width_sm, coordinates" +
                " FROM plots WHERE id = ?";
        try (Connection conn = this.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()){
                if (!rs.next()) {
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
    public Collection<Plot> getAll() {
        String sql = "SELECT id, sector_id, row_number," +
                " plot_number, plot_status, length_cm, " +
                "width_cm, coordinates " +
                "FROM plots";
        List<Plot> list = new ArrayList<>();
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
    public void save(Plot value) {
        String sql =
                "INSERT INTO plots(sector_id, row_number, " +
                        "plot_number, plot_status, length_cm, " +
                "width_cm, coordinaties) " +
                "VALUES(?, ?, ?, ?, ?, ?, ?);";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setInt(1, value.sectorId());
            stmt.setInt(2, value.rowNumber());
            stmt.setInt(3, value.plotNumber());
            stmt.setString(4, value.status().toString());
            stmt.setFloat(5, value.lengthCm());
            stmt.setFloat(6, value.widthCm());
            stmt.setString(7, value.coordinates());
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
        String sql = "DELETE FROM plots WHERE id = ?";
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
