package BakeryFunction;
import BakeryDatabase.DatabaseUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDate;
import java.text.SimpleDateFormat;
import java.sql.Statement;
import java.sql.Date;
import java.sql.*;



public class OrderDAO {

        //--------------------------------------------------------//
        //-----------For Check Order History Module---------------//
        // Method to get an Order by Order ID
        /*
        public Order getOrderByID(String orderId) {
            String query = "SELECT * FROM `Orders` WHERE order_id = ?";
            Order order = null;

            try (Connection conn = DatabaseUtils.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(query)) {

                stmt.setString(1, orderId);
                ResultSet rs = stmt.executeQuery();

                if (rs.next()) {
                    order = new Order();
                    order.setOrderID(rs.getString("order_id"));
                    order.setStatus(rs.getString("status"));

                    String dateString = rs.getString("order_date");
                    // Assuming the date is stored in a format like "yyyy-MM-dd"
                    LocalDate date = LocalDate.parse(dateString);
                    order.setOrderDate(date);

                    order.setGrandTotalAmount(rs.getDouble("grand_total_amount"));
                    order.setSupplierID(rs.getString("supplier_id"));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return order; // Return the order or null if not found
        }

         */

        // Method to update the status of an order
    /*
        public void updateOrder(Order order) {
            String query = "UPDATE `Orders` SET status = ?, order_date = ?, grand_total_amount = ?, supplier_id = ? WHERE order_id = ?";

            try (Connection conn = DatabaseUtils.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(query)) {

                stmt.setString(1, order.getStatus());

                // Convert LocalDate to java.sql.Date
                java.sql.Date sqlDate = java.sql.Date.valueOf(order.getOrderDate());
                stmt.setDate(2, sqlDate);

                stmt.setDouble(3, order.getGrandTotalAmount());
                stmt.setString(4, order.getSupplierID());
                stmt.setString(5, order.getOrderID());

                stmt.executeUpdate(); // Executes the update query
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

     */

        // Method to get all orders from the database
    /*
    public List<Order> getAllOrders() {
            String query = "SELECT * FROM `Orders`";
            List<Order> orders = new ArrayList<>();

            try (Connection conn = DatabaseUtils.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(query);
                 ResultSet rs = stmt.executeQuery()) {

                while (rs.next()) {
                    Order order = new Order();
                    order.setOrderID(rs.getString("order_id"));
                    order.setStatus(rs.getString("status"));

                    String dateString = rs.getString("order_date");
                    // Assuming the date is stored in a format like "yyyy-MM-dd"
                    LocalDate date = LocalDate.parse(dateString);
                    order.setOrderDate(date);

                    order.setGrandTotalAmount(rs.getDouble("grand_total_amount"));
                    order.setSupplierID(rs.getString("supplier_id"));
                    orders.add(order);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            return orders;
        }

     */

        //--------------------------------------------------------//
        //------------For Create New Order Module-----------------//
    /*
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


        public static String saveOrder(BakeryFunction.Order order) {
            String orderID = order.getOrderID();
            //System.out.println(orderID);
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
     */
    }


