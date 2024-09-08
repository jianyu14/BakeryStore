package BakeryFunction;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.Scanner;



public class ProductDAO {


    //--------------------------------------------------------//
    //------------For Display Product Module-----------------//
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM product";
        try (Connection con = BakeryDatabase.DatabaseUtils.getConnection();
             PreparedStatement pstmt = con.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                Product product = new Product();
                product.setProductId(rs.getString("product_id"));
                product.setProductName(rs.getString("prod_name"));
                product.setPrice(rs.getDouble("price"));
                product.setQuantityLeft(rs.getInt("left_quantity"));
                product.setSupplierId(rs.getString("supplier_id"));
                product.setWarehouseNo(rs.getString("warehouse_no"));
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    public List<Product> getLowQuantityProducts(int threshold) {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM product WHERE left_quantity < ?";

        try (Connection con = BakeryDatabase.DatabaseUtils.getConnection();
             PreparedStatement pstmt = con.prepareStatement(query)) {

            pstmt.setInt(1, threshold); // Dynamically set the threshold
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Product product = new Product(
                        rs.getString("product_id"),
                        rs.getString("prod_name"),
                        rs.getDouble("price"),
                        rs.getInt("left_quantity"),
                        rs.getString("supplier_id"),
                        rs.getString("warehouse_no")
                );
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Ideally log this exception
        }
        return products;
    }

    public List<Map<String, Object>> getExpiredProducts(String inputDate) {
        List<Map<String, Object>> expiredProducts = new ArrayList<>();
        String query = "SELECT p.product_id, p.prod_name, p.warehouse_no, oi.order_id, oi.expired_date " +
                "FROM product p JOIN orderitems oi ON p.product_id = oi.product_id " +
                "WHERE oi.expired_date < ? ORDER BY oi.order_id";

        try (Connection con = BakeryDatabase.DatabaseUtils.getConnection();
             PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, inputDate);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Map<String, Object> productDetails = new HashMap<>();
                productDetails.put("order_id", rs.getString("order_id"));
                productDetails.put("product_id", rs.getString("product_id"));
                productDetails.put("prod_name", rs.getString("prod_name"));
                productDetails.put("warehouse_no", rs.getString("warehouse_no"));
                productDetails.put("expired_date", rs.getString("expired_date"));
                expiredProducts.add(productDetails);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return expiredProducts;
    }

    //--------------------------------------------------------//
    //------------For Track Product Module-----------------//
    public List<Product> findProductsByName(String name) {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM product WHERE prod_name LIKE ?";

        try (Connection con = BakeryDatabase.DatabaseUtils.getConnection();
             PreparedStatement pstmt = con.prepareStatement(query)) {

            pstmt.setString(1, name + "%");  // Use wildcard for partial match
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Product product = new Product(
                        rs.getString("product_id"),
                        rs.getString("prod_name"),
                        rs.getDouble("price"),
                        rs.getInt("left_quantity"),
                        rs.getString("supplier_id"),
                        rs.getString("warehouse_no")
                );
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Proper exception handling/log should be here
        }
        return products;
    }

    //for Track Product Function
    public List<Product> findProductsById(String productId) {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM product WHERE product_id LIKE ?";

        try (Connection con = BakeryDatabase.DatabaseUtils.getConnection();
             PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, productId + "%");
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Product product = new Product(
                        rs.getString("product_id"),
                        rs.getString("prod_name"),
                        rs.getDouble("price"),
                        rs.getInt("left_quantity"),
                        rs.getString("supplier_id"),
                        rs.getString("warehouse_no")
                );
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Proper exception handling/log should be here
        }
        return products;
    }

    public List<Product> findProductsByPrice(String price) {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM product WHERE price = ?";

        try (Connection con = BakeryDatabase.DatabaseUtils.getConnection();
             PreparedStatement pstmt = con.prepareStatement(query)) {

            pstmt.setDouble(1, Double.parseDouble(price));  // Convert string to double and set as parameter
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Product product = new Product(
                        rs.getString("product_id"),
                        rs.getString("prod_name"),
                        rs.getDouble("price"),
                        rs.getInt("left_quantity"),
                        rs.getString("supplier_id"),
                        rs.getString("warehouse_no")
                );
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Proper exception handling/log should be here
        } catch (NumberFormatException e) {
            System.out.println("Invalid price format. Please enter a valid number.");
        }
        return products;
    }

    public List<Product> findProductsByWarehouse(String warehouseId) {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM product WHERE warehouse_no LIKE ? ORDER BY supplier_id";

        try (Connection con = BakeryDatabase.DatabaseUtils.getConnection();
             PreparedStatement pstmt = con.prepareStatement(query)) {

            pstmt.setString(1, warehouseId + "%");  // Use wildcard for partial match
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Product product = new Product(
                        rs.getString("product_id"),
                        rs.getString("prod_name"),
                        rs.getDouble("price"),
                        rs.getInt("left_quantity"),
                        rs.getString("supplier_id"),
                        rs.getString("warehouse_no")
                );
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Proper exception handling/log should be here
        }
        return products;
    }

    public List<Product> findProductsBySupplier(String supplierId) {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM product WHERE supplier_id LIKE ? ORDER BY warehouse_no";

        try (Connection con = BakeryDatabase.DatabaseUtils.getConnection();
             PreparedStatement pstmt = con.prepareStatement(query)) {

            pstmt.setString(1, supplierId + "%");  // Use wildcard for partial match
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Product product = new Product(
                        rs.getString("product_id"),
                        rs.getString("prod_name"),
                        rs.getDouble("price"),
                        rs.getInt("left_quantity"),
                        rs.getString("supplier_id"),
                        rs.getString("warehouse_no")
                );
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Proper exception handling/log should be here
        }
        return products;
    }


    //--------------------------------------------------------//
    //------------For Order History Module-------------------//
    public Product GetProductById(String productId) {
        String query = "SELECT * FROM product WHERE product_id = ?";  // Changed to exact match

        try (Connection con = BakeryDatabase.DatabaseUtils.getConnection();
             PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, productId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return new Product(
                        rs.getString("product_id"),
                        rs.getString("prod_name"),
                        rs.getDouble("price"),
                        rs.getInt("left_quantity"),
                        rs.getString("supplier_id"),
                        rs.getString("warehouse_no")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;  // Return null if no product found
    }

    // Method to update an existing Product
    public boolean updateProduct(Product product) {
        String query = "UPDATE `Product` SET prod_name = ?, left_quantity = ?, price = ?, warehouse_no = ? " +
                "WHERE product_id = ?";

        try (Connection con = BakeryDatabase.DatabaseUtils.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, product.getProductName());
            stmt.setInt(2, product.getQuantityLeft());
            stmt.setDouble(3, product.getPrice());
            stmt.setString(4, product.getWarehouseNo());
            stmt.setString(5, product.getProductId());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean addProduct(Product product) {
        String query = "INSERT INTO `Product` (product_id, prod_name, left_quantity, price, warehouse_no) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection con = BakeryDatabase.DatabaseUtils.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, product.getProductId());
            stmt.setString(2, product.getProductName());
            stmt.setInt(3, product.getQuantityLeft());
            stmt.setDouble(4, product.getPrice());
            stmt.setString(5, product.getWarehouseNo());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Method to delete a Product
    public boolean deleteProduct(String productID) {
        String query = "DELETE FROM `Product` WHERE product_id = ?";

        try (Connection con = BakeryDatabase.DatabaseUtils.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {

            stmt.setString(1, productID);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //--------------------------------------------------------//
    //------------For Create New Order Module-----------------//
    public static boolean displayProductSup(String supplierID, Scanner scanner){ //to display product that a certain supplier sells

        String query = "Select * from product where supplier_id=?";

        try(Connection con = BakeryDatabase.DatabaseUtils.getConnection();
            PreparedStatement pstmt = con.prepareStatement(query)){

            pstmt.setString(1, supplierID);
            ResultSet rs = pstmt.executeQuery();

            if (!rs.isBeforeFirst()) {  // No rows returned //if not found, then prompt user to enter supplier ID again
                System.out.println("No products found for the given supplier ID.");
                System.out.print("Enter again? (Y/N) : ");
                String answer = scanner.next();
                if(answer.equalsIgnoreCase("Y")) {
                    return false; //boolean to tells the main function either the supplierID is valid or not, in this case invalid
                }
            }

            System.out.printf("|%-10s|%-30s|%-15s|%-10s|%-12s|\n", "Product ID", "Product Name", "Left Quantity", "Price", "Warehouse No");
            System.out.println("===================================================================================");

            while(rs.next()){
                String productID = rs.getString("product_id");
                String prodName = rs.getString("prod_name");
                int leftQuantity = rs.getInt("left_quantity");
                double price = rs.getDouble("price");
                String warehouseNO = rs.getString("warehouse_no");

                System.out.printf("|%-10s|%-30s|%-15d|%-10.2f|%-12s|\n", productID, prodName, leftQuantity, price, warehouseNO);
                System.out.println("-----------------------------------------------------------------------------------");

            }

        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return true;//boolean to tells the main function either the supplierID is valid or not, in this case valid
    }

    public static void displayProduct(int prodID){ //not used function

        String productID = String.format("P%04d", prodID);

        String query = "Select * from product where product_id=?";

        try(Connection con = BakeryDatabase.DatabaseUtils.getConnection();
            PreparedStatement pstmt = con.prepareStatement(query)){

            pstmt.setString(1, productID);
            ResultSet rs = pstmt.executeQuery();

            System.out.printf("|%s\t|%s\t|%s\t|%s\t|%s\t|%n", "Product ID", "Product Name", "Left Quantity", "Price", "Warehouse No");
            System.out.println("======================================================================");

            while(rs.next()){
                productID = rs.getString("product_id");
                String prodName = rs.getString("prod_name");
                int leftQuantity = rs.getInt("left_quantity");
                double price = rs.getDouble("price");
                String warehouseNO = rs.getString("warehouse_no");

                System.out.printf("|%s\t|%s\t\t|%s\t\t|%-22.2f\t|%-11s\t|\n", productID, prodName, leftQuantity, price, warehouseNO);
                System.out.println("-------------------------------------------------------------------------------------");
            }

        }
        catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void displayProductA(List<Integer> selectedProductIDs, List<Integer> quantities){ //for order confirmation purposes

        System.out.printf("\n\n|%-10s|%-15s|%-15s|%-10s|%-12s|%-10s|%-15s|\n",
                "Product ID", "Product Name", "Left Quantity", "Price", "Warehouse No", "Quantity", "Total Amount");
        System.out.println("===============================================================================================");


        for (int i = 0; i < selectedProductIDs.size(); i++) {
            String productID = String.format("P%04d", selectedProductIDs.get(i));

            String query = "Select * from product where product_id=?";

            try (Connection con = BakeryDatabase.DatabaseUtils.getConnection();
                 PreparedStatement pstmt = con.prepareStatement(query)) {

                pstmt.setString(1, productID);
                ResultSet rs = pstmt.executeQuery();



                while (rs.next()) {
                    productID = rs.getString("product_id");
                    String prodName = rs.getString("prod_name");
                    int leftQuantity = rs.getInt("left_quantity");
                    double price = rs.getDouble("price");
                    String warehouseNO = rs.getString("warehouse_no");

                    double total_amount = price * quantities.get(i);

                    System.out.printf("|%-10s|%-15s|%-15d|%-10.2f|%-12s|%-10d|%-15.6f|\n",
                            productID, prodName, leftQuantity, price, warehouseNO, quantities.get(i), total_amount);
                    System.out.println("-----------------------------------------------------------------------------------------------");
                }

            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public static double returnPrice(int prodID){ //to return price of a certain product

        String productID = String.format("P%04d", prodID);

        String query = "Select * from product where product_id=?";
        try(Connection con = BakeryDatabase.DatabaseUtils.getConnection();
            PreparedStatement pstmt = con.prepareStatement(query)){

            pstmt.setString(1, productID);
            ResultSet rs = pstmt.executeQuery();

            System.out.printf("\n\n|%-10s|%-15s|%-15s|%-10s|%-12s|\n", "Product ID", "Product Name", "Left Quantity", "Price", "Warehouse No");
            System.out.println("====================================================================");


            while(rs.next()){
                String productName = rs.getString("prod_name");
                int leftQuantity = rs.getInt("left_quantity");
                double price = rs.getDouble("price");
                String warehouseNO = rs.getString("warehouse_no");

                System.out.printf("|%-10s|%-15s|%-15d|%-10.2f|%-12s|\n", productID, productName, leftQuantity, price, warehouseNO);
                System.out.println("--------------------------------------------------------------------");

                return price;
            }

        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return 0;
    }

    //--------------------------------------------------------//
    //------------For Daily Sales Module-----------------//
    public int getCurrentQuantity(String productId) throws SQLException {
        String query = "SELECT left_quantity FROM product WHERE product_id = ?";
        try (Connection con = BakeryDatabase.DatabaseUtils.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setString(1, productId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("left_quantity");
            }
            return -1;  // Return -1 if no product is found
        }
    }
    public boolean updateProductQuantity(String productId, int newQuantity) throws SQLException {
        String query = "UPDATE product SET left_quantity = ? WHERE product_id = ?";
        try (Connection con = BakeryDatabase.DatabaseUtils.getConnection();
             PreparedStatement stmt = con.prepareStatement(query)) {
            stmt.setInt(1, newQuantity);
            stmt.setString(2, productId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        }
    }



}

