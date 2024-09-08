package DAO;

import BakeryFunction.Supplier;
import BakeryDatabase.DatabaseUtils;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SupplierDAO implements Dao<Supplier> {

    @Override
    public List<Supplier> getAll() {
        List<Supplier> suppliers = new ArrayList<>();
        String query = "SELECT * FROM Supplier";

        try (Connection conn = DatabaseUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Supplier supplier = new Supplier();
                supplier.setSupplierId(rs.getString("supplier_id"));
                supplier.setSupplierName(rs.getString("supplier_name"));
                supplier.setSupplierAddress(rs.getString("supplier_address"));
                supplier.setSupplierContact(rs.getString("supplier_contact"));
                suppliers.add(supplier);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return suppliers;
    }

    @Override
    public Supplier getById(String id) {
        String query = "SELECT * FROM Supplier WHERE supplier_id = ?";
        Supplier supplier = null;

        try (Connection conn = DatabaseUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                supplier = new Supplier();
                supplier.setSupplierId(rs.getString("supplier_id"));
                supplier.setSupplierName(rs.getString("supplier_name"));
                supplier.setSupplierAddress(rs.getString("supplier_address"));
                supplier.setSupplierContact(rs.getString("supplier_contact"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return supplier;
    }

    @Override
    public boolean update(Supplier supplier) {
        String query = "UPDATE Supplier SET supplier_name = ?, supplier_address = ?, supplier_contact = ? WHERE supplier_id = ?";

        try (Connection conn = DatabaseUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, supplier.getSupplierName());
            stmt.setString(2, supplier.getSupplierAddress());
            stmt.setString(3, supplier.getSupplierContact());
            stmt.setString(4, supplier.getSupplierId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteById(String id) {
        String query = "DELETE FROM Supplier WHERE supplier_id = ?";

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
    public boolean insert(Supplier supplier) {
        String query = "INSERT INTO Supplier (supplier_id, supplier_name, supplier_address, supplier_contact) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, supplier.getSupplierId());
            stmt.setString(2, supplier.getSupplierName());
            stmt.setString(3, supplier.getSupplierAddress());
            stmt.setString(4, supplier.getSupplierContact());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
