package DAO;

import BakeryFunction.Order;
import BakeryDatabase.DatabaseUtils;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO implements Dao<Order> {

    @Override
    public List<Order> getAll() {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT * FROM `Order`";  // Order is a reserved keyword, backticks are used

        try (Connection conn = DatabaseUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Order order = new Order();
                order.setOrderId(rs.getString("order_id"));
                order.setStatus(rs.getString("status"));
                order.setOrderDate(rs.getDate("order_date"));
                order.setGrandTotalAmount(rs.getDouble("grand_total_amount"));
                order.setSupplierId(rs.getString("supplier_id"));
                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    @Override
    public Order getById(String id) {
        String query = "SELECT * FROM `Order` WHERE order_id = ?";
        Order order = null;

        try (Connection conn = DatabaseUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                order = new Order();
                order.setOrderId(rs.getString("order_id"));
                order.setStatus(rs.getString("status"));
                order.setOrderDate(rs.getDate("order_date"));
                order.setGrandTotalAmount(rs.getDouble("grand_total_amount"));
                order.setSupplierId(rs.getString("supplier_id"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return order;
    }

    @Override
    public boolean update(Order order) {
        String query = "UPDATE `Order` SET status = ?, order_date = ?, grand_total_amount = ?, supplier_id = ? WHERE order_id = ?";

        try (Connection conn = DatabaseUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, order.getStatus());
            stmt.setDate(2, new java.sql.Date(order.getOrderDate().getTime()));
            stmt.setDouble(3, order.getGrandTotalAmount());
            stmt.setString(4, order.getSupplierId());
            stmt.setString(5, order.getOrderId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteById(String id) {
        String query = "DELETE FROM `Order` WHERE order_id = ?";

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
    public boolean insert(Order order) {
        String query = "INSERT INTO `Order` (order_id, status, order_date, grand_total_amount, supplier_id) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, order.getOrderId());
            stmt.setString(2, order.getStatus());
            stmt.setDate(3, new java.sql.Date(order.getOrderDate().getTime()));
            stmt.setDouble(4, order.getGrandTotalAmount());
            stmt.setString(5, order.getSupplierId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Generate a new unique order ID based on the current max order_id
    public static String generateNewOrderId() {
        String prefix = "ORDA";
        int idLength = 5; // Number of digits in the numeric part of the ID
        String query = "SELECT MAX(order_id) AS maxID FROM orders WHERE order_id LIKE '" + prefix + "%'";

        try (Connection conn = DatabaseUtils.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            if (rs.next()) {
                String lastId = rs.getString("maxID");
                if (lastId != null) {
                    // Extract the numeric part and increment
                    int numPart = Integer.parseInt(lastId.substring(prefix.length())) + 1;
                    return prefix + String.format("%0" + idLength + "d", numPart);
                } else {
                    // No IDs are present yet, start with ORDA00001
                    return prefix + String.format("%0" + idLength + "d", 1);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // In case of SQL error or other issues
    }

    // Save a new order to the database
    public static String saveOrder(Order order) {
        String orderID = order.getOrderID();  // Assuming orderID is pre-generated by generateNewOrderId()
        String status = order.getStatus();
        LocalDate orderDate = order.getOrderDate();
        double grandTotalAmount = order.getGrandTotalAmount();
        String supplierID = order.getSupplierID();

        Date sqlDate = Date.valueOf(orderDate);  // Convert LocalDate to java.sql.Date

        String insertOrderSQL = "INSERT INTO Orders (order_id, status, order_date, grand_total_amount, supplier_id) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseUtils.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(insertOrderSQL)) {
            pstmt.setString(1, orderID);
            pstmt.setString(2, status);
            pstmt.setDate(3, sqlDate);
            pstmt.setDouble(4, grandTotalAmount);
            pstmt.setString(5, supplierID);

            if (pstmt.executeUpdate() > 0) {
                System.out.println("Order added successfully.");
                return orderID;
            } else {
                System.out.println("No rows affected. Insert might have failed.");
                return null;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
