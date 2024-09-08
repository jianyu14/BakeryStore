package BakeryModule;

import java.util.Scanner;
import java.util.List;
import java.util.Map;



public class DisplayProduct {

    public static void displayMenu(Scanner scanner) {

        int displayChoice;
        do {
            System.out.println();
            System.out.println("Display Menu:");
            System.out.println("1. Display All Product");
            System.out.println("2. Display Low Quantity Products");
            System.out.println("3. Display Expired Products");
            System.out.println("4. Return Main Menu");
            System.out.print("Please enter your choice (1-4): ");
            displayChoice = Integer.parseInt(scanner.nextLine());

            switch (displayChoice) {
                case 1:
                    //Display All Product
                    System.out.println("Display All Products\n");
                    DisplayProduct.displayAllProduct();
                    break;
                case 2:
                    //DisplayLowQuantity
                    System.out.println("Display Low Quantity Products\n");
                    DisplayProduct.displayLowProduct();
                    break;
                case 3:
                    //DisplayExpired
                    System.out.println("Display Expired Products\n");
                    displayExpiredProduct(scanner);
                    break;
                case 4:
                    System.out.println("Returning...");
                    return;
                default:
                    System.out.println("Invalid choice. Please enter your choice (1-4): ");
            }
        } while (true);
    }


        public static void displayAllProduct() {
            BakeryFunction.ProductDAO productDAO = new BakeryFunction.ProductDAO();
            List<BakeryFunction.Product> products = productDAO.getAllProducts();

            System.out.println("Product List");
            System.out.printf("%-15s %-20s %-10s %-15s %-15s %-15s%n",
                    "ID", "Name", "Price", "Left Quantity", "Supplier ID", "Warehouse No");

            for (BakeryFunction.Product product : products) {
                System.out.printf("%-15s %-20s %-10.2f %-15d %-15s %-15s%n",
                        product.getProductId(), product.getProductName(), product.getPrice(),
                        product.getQuantityLeft(), product.getSupplierId(), product.getWarehouseNo());
            }
        }

        public static void displayLowProduct() {
            BakeryFunction.ProductDAO productDAO = new BakeryFunction.ProductDAO();
            List<BakeryFunction.Product> products = productDAO.getLowQuantityProducts(15);

            System.out.println("Products that are Low Quantity (quantity < 15)");
            System.out.printf("%-15s %-20s %-10s %-15s %-15s %-15s%n",
                    "ID", "Name", "Price", "Left Quantity", "Supplier ID", "Warehouse No");

            for (BakeryFunction.Product product : products) {
                System.out.printf("%-15s %-20s %-10.2f %-15d %-15s %-15s%n",
                        product.getProductId(), product.getProductName(), product.getPrice(),
                        product.getQuantityLeft(), product.getSupplierId(), product.getWarehouseNo());
            }
        }

    public static void displayExpiredProduct(Scanner scanner) {
        System.out.print("Please enter today's date (YYYY-MM-DD): ");
        String inputDate = scanner.nextLine();

        BakeryFunction.ProductDAO productDAO = new BakeryFunction.ProductDAO();
        List<Map<String, Object>> expiredProducts = productDAO.getExpiredProducts(inputDate);

        String lastOrderId = ""; // Variable to track the last order ID processed

        for (Map<String, Object> product : expiredProducts) {
            String order_id = (String) product.get("order_id");
            String p_id = (String) product.get("product_id");
            String p_name = (String) product.get("prod_name");
            String w_id = (String) product.get("warehouse_no");
            String expired_date = (String) product.get("expired_date");

            // Check if the current order ID is different from the last one processed
            if (!order_id.equals(lastOrderId)) {
                System.out.printf("\nOrder ID: %s | Expiry Date: %s%n", order_id, expired_date);
                System.out.printf("%-15s %-20s %-15s%n", "Product ID", "Product Name", "Warehouse No");
                lastOrderId = order_id; // Update lastOrderId to the current one
            }

            // Print product details under the current order ID
            System.out.printf("%-15s %-20s %-15s%n", p_id, p_name, w_id);
        }
    }




}

  /*



    public static void displayExpiredProduct(Scanner scanner) {
        System.out.print("Please enter today's date (YYYY-MM-DD): ");
        String inputDate = scanner.nextLine();

        DatabaseUtils dbUtils = new DatabaseUtils();
        String query = "SELECT " +
                "p.product_id, p.prod_name, p.warehouse_no, " +
                "oi.order_id, oi.expired_date " +
                "FROM product p " +
                "JOIN orderitems oi ON p.product_id = oi.product_id " +
                "WHERE oi.expired_date < ? " +
                "ORDER BY oi.order_id";

        try (Connection con = dbUtils.getCon();
             PreparedStatement pstmt = con.prepareStatement(query)) {

            pstmt.setString(1, inputDate);
            ResultSet rs = pstmt.executeQuery();

            String lastOrderId = ""; // To keep track of the last displayed order ID

            while (rs.next()) {
                String order_id = rs.getString("order_id");
                String p_name = rs.getString("prod_name");
                String w_id = rs.getString("warehouse_no");
                String p_id = rs.getString("product_id");
                String order_expiry_date = rs.getString("expired_date");

                if (!order_id.equals(lastOrderId)) {
                    if (!lastOrderId.equals("")) {
                        System.out.println(); // Print a blank line between different orders
                    }
                    // Display the order ID only if it's different from the last one
                    System.out.printf("Order ID: %s | Expiry Date: %s%n", order_id, order_expiry_date);
                    System.out.printf("%-15s %-20s %-15s%n", "Product ID", "Product Name", "Warehouse No");
                    lastOrderId = order_id;
                }

                // Display product details under the current order ID
                System.out.printf("%-15s %-20s %-15s%n", p_id, p_name, w_id);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exceptions appropriately
        }
    }
}

   */