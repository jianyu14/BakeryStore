package DAO;

import BakeryFunction.Warehouse;
import BakeryDatabase.DatabaseUtils;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WarehouseDAO implements Dao<Warehouse> {

    @Override
    public List<Warehouse> getAll() {
        List<Warehouse> warehouses = new ArrayList<>();
        String query = "SELECT * FROM Warehouse";

        try (Connection conn = DatabaseUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Warehouse warehouse = new Warehouse();
                warehouse.setWarehouseNo(rs.getString("warehouse_no"));
                warehouse.setCapacity(rs.getInt("capacity"));
                warehouses.add(warehouse);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return warehouses;
    }

    @Override
    public Warehouse getById(String id) {
        String query = "SELECT * FROM Warehouse WHERE warehouse_no = ?";
        Warehouse warehouse = null;

        try (Connection conn = DatabaseUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                warehouse = new Warehouse();
                warehouse.setWarehouseNo(rs.getString("warehouse_no"));
                warehouse.setCapacity(rs.getInt("capacity"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return warehouse;
    }

    @Override
    public boolean update(Warehouse warehouse) {
        String query = "UPDATE Warehouse SET capacity = ? WHERE warehouse_no = ?";

        try (Connection conn = DatabaseUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, warehouse.getCapacity());
            stmt.setString(2, warehouse.getWarehouseNo());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteById(String id) {
        String query = "DELETE FROM Warehouse WHERE warehouse_no = ?";

        try (Connection conn = DatabaseUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean insert(Warehouse warehouse) {
        String query = "INSERT INTO Warehouse (warehouse_no, capacity) VALUES (?, ?)";

        try (Connection conn = DatabaseUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, warehouse.getWarehouseNo());
            stmt.setInt(2, warehouse.getCapacity());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
