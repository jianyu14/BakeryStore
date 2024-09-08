package BakeryFunction;

import BakeryDatabase.DatabaseUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import java.time.LocalDate;


public class OrderItemDAO {

    //--------------------------------------------------------//
    //----------For Check Order History Module----------------//

    // Method to get all OrderItems for a given order ID
    public List<OrderItem> getOrderItemsByOrderID(String orderId) {
        String query = "SELECT * FROM `OrderItems` WHERE order_id = ?";
        List<OrderItem> orderItems = new ArrayList<>();

        try (Connection conn = DatabaseUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, orderId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                OrderItem orderItem = new OrderItem();
                orderItem.setOrderItemID(rs.getString("order_item_id"));
                orderItem.setOrderID(rs.getString("order_id"));
                orderItem.setProductID(rs.getString("product_id"));
                orderItem.setOrderQuantity(rs.getInt("order_quantity"));
                orderItem.setTotalAmount(rs.getDouble("total_amount"));
                orderItem.setExpireDate(rs.getString("expired_date"));

                orderItems.add(orderItem);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderItems;
    }

    // Method to add a new OrderItem
    public boolean addOrderItem(OrderItem orderItem) {
        String query = "INSERT INTO `OrderItems` (order_item_id, order_id, product_id, total_amount, expired_date, order_quantity) " +
                "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, orderItem.getOrderItemID());
            stmt.setString(2, orderItem.getOrderID());
            stmt.setString(3, orderItem.getProductID());
            stmt.setDouble(4, orderItem.getTotalAmount());
            stmt.setString(5, orderItem.getExpireDate());
            stmt.setInt(6, orderItem.getOrderQuantity());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Method to update an existing OrderItem
    public boolean updateOrderItem(OrderItem orderItem) {
        String query = "UPDATE `OrderItems` SET product_id = ?, total_amount = ?, expire_date = ?, order_quantity = ? " +
                "WHERE order_item_id = ? AND order_id = ?";

        try (Connection conn = DatabaseUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, orderItem.getProductID());
            stmt.setDouble(2, orderItem.getTotalAmount());
            stmt.setString(3, orderItem.getExpireDate());
            stmt.setInt(4, orderItem.getOrderQuantity());
            stmt.setString(5, orderItem.getOrderItemID());
            stmt.setString(6, orderItem.getOrderID());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Method to delete an OrderItem
    public boolean deleteOrderItem(String orderItemID) {
        String query = "DELETE FROM `OrderItems` WHERE order_item_id = ?";

        try (Connection conn = DatabaseUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, orderItemID);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //--------------------------------------------------------//
    //------------For Create New Order Module-----------------//

    public static void saveOrderItems(List<Integer> selectedProductIDs, List<Integer> quantities, List<Double> prices, String orderID) {
        LocalDate expirationDate = LocalDate.now().plusDays(5); // Calculate expiration date as 5 days from now
        Date sqlExpiredDate = Date.valueOf(expirationDate);

        try (Connection conn = DatabaseUtils.getConnection()) {
            String insertOrderSQL = "INSERT INTO orderitems (order_item_id, order_id, product_id, total_amount, expired_date, order_quantity) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(insertOrderSQL);

            for (int i = 0; i < selectedProductIDs.size(); i++) {
                String orderItemID = generateNewOrderItemId(conn); // Assume a separate method to handle ID generation
                if (orderItemID == null) {
                    continue; // Skip this iteration if ID generation fails
                }

                Integer productID = selectedProductIDs.get(i);
                Integer quantity = quantities.get(i);
                Double price = prices.get(i);
                double totalAmount = price * quantity;

                pstmt.setString(1, orderItemID);
                pstmt.setString(2, orderID);
                pstmt.setString(3, String.format("P%04d", productID));
                pstmt.setDouble(4, totalAmount);
                pstmt.setDate(5, sqlExpiredDate);
                pstmt.setInt(6, quantity);

                pstmt.addBatch(); // Add to batch
            }

            int[] updateCounts = pstmt.executeBatch(); // Execute batch update
            System.out.println("Order Items added successfully: " + updateCounts.length);
            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static String generateNewOrderItemId(Connection con) {
        String query = "SELECT MAX(order_item_id) AS lastID FROM orderitems";
        String prefixRIA = "RIA";
        int lengthRIA = 3; // Length of numeric part

        try (Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            if (rs.next()) {
                String lastID = rs.getString("lastID");
                if (lastID != null && lastID.startsWith(prefixRIA)) {
                    String numericPart = lastID.substring(prefixRIA.length());
                    int newID = Integer.parseInt(numericPart) + 1;
                    return prefixRIA + String.format("%0" + lengthRIA + "d", newID);
                }
            }
            return prefixRIA + String.format("%0" + lengthRIA + "d", 1);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

}
