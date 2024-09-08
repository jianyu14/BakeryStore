package DAO;

import BakeryFunction.OrderItem;
//import BakeryFunction.Order;
//import BakeryFunction.Product;
import BakeryDatabase.DatabaseUtils;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderItemDAO implements Dao<OrderItem> {

    @Override
    public List<OrderItem> getAll() {
        List<OrderItem> orderItems = new ArrayList<>();
        String query = "SELECT * FROM OrderItem";

        try (Connection conn = DatabaseUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                OrderItem orderItem = new OrderItem();
                orderItem.setOrderItemId(rs.getString("order_item_id"));
                orderItem.setOrderId(rs.getString("order_id"));
                orderItem.setProductId(rs.getString("product_id"));
                orderItem.setTotalAmount(rs.getDouble("total_amount"));
                orderItem.setExpireDate(rs.getDate("expire_date"));
                orderItem.setOrderQuantity(rs.getInt("order_quantity"));
                orderItems.add(orderItem);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderItems;
    }

    @Override
    public OrderItem getById(String id) {
        String query = "SELECT * FROM OrderItem WHERE order_item_id = ?";
        OrderItem orderItem = null;

        try (Connection conn = DatabaseUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                orderItem = new OrderItem();
                orderItem.setOrderItemId(rs.getString("order_item_id"));
                orderItem.setOrderId(rs.getString("order_id"));
                orderItem.setProductId(rs.getString("product_id"));
                orderItem.setTotalAmount(rs.getDouble("total_amount"));
                orderItem.setExpireDate(rs.getDate("expire_date"));
                orderItem.setOrderQuantity(rs.getInt("order_quantity"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orderItem;
    }

    @Override
    public boolean update(OrderItem orderItem) {
        String query = "UPDATE OrderItem SET order_id = ?, product_id = ?, total_amount = ?, expire_date = ?, order_quantity = ? WHERE order_item_id = ?";

        try (Connection conn = DatabaseUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, orderItem.getOrderId());
            stmt.setString(2, orderItem.getProductId());
            stmt.setDouble(3, orderItem.getTotalAmount());
            stmt.setDate(4, new java.sql.Date(orderItem.getExpireDate().getTime()));
            stmt.setInt(5, orderItem.getOrderQuantity());
            stmt.setString(6, orderItem.getOrderItemId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean deleteById(String id) {
        String query = "DELETE FROM OrderItem WHERE order_item_id = ?";

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
    public boolean insert(OrderItem orderItem) {
        String query = "INSERT INTO OrderItem (order_item_id, order_id, product_id, total_amount, expire_date, order_quantity) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, orderItem.getOrderItemId());
            stmt.setString(2, orderItem.getOrderId());
            stmt.setString(3, orderItem.getProductId());
            stmt.setDouble(4, orderItem.getTotalAmount());
            stmt.setDate(5, new java.sql.Date(orderItem.getExpireDate().getTime()));
            stmt.setInt(6, orderItem.getOrderQuantity());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

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
