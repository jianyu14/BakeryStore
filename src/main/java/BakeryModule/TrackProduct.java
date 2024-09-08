package BakeryModule;

import java.util.Scanner;
import java.util.List;

public class TrackProduct {

    public static void displayCriteria(Scanner scanner) {

        int criteriaChoice;
        do {
            System.out.println();
            System.out.println("Track Product Criteria");
            System.out.println("1. Name");
            System.out.println("2. ID");
            System.out.println("3. Price");
            System.out.println("4. Warehouse");
            System.out.println("5. Supplier");
            System.out.println("6. Return Main Menu");
            System.out.print("Please enter your choice (1-6): ");
            criteriaChoice = Integer.parseInt(scanner.nextLine());

            switch (criteriaChoice) {
                case 1:
                    //Product Name
                    System.out.println("Product Name\n");
                    trackName(scanner);
                    break;
                case 2:
                    //Product Id
                    System.out.println("Product Id\n");
                    trackId(scanner);
                    break;
                case 3:
                    //Product price
                    System.out.println("Product Price\n");
                    trackPrice(scanner);
                    break;
                case 4:
                    //product warehouse
                    System.out.println("Product Warehouse\n");
                    trackWarehouse(scanner);
                    break;
                case 5:
                    //product supplier
                    System.out.println("Product Supplier\n");
                    trackSupplier(scanner);
                    break;
                case 6:
                    System.out.println("Returning...");
                    return;
                default:
                    System.out.println("Invalid choice. Please enter your choice (1-6): ");
            }
        } while (true);
    }

    public static void trackName(Scanner scanner) {
        System.out.print("Enter Product Name: ");
        String inputName = scanner.nextLine();

        BakeryFunction.ProductDAO productDAO = new BakeryFunction.ProductDAO();
        List<BakeryFunction.Product> products = productDAO.findProductsByName(inputName);

        // Check if products list is empty and handle appropriately
        if (products.isEmpty()) {
            System.out.println("No products found for the given name.");
        } else {
            // Print the header
            System.out.printf("%-15s %-20s %-10s %-15s %-15s %-15s%n",
                    "ID", "Name", "Price", "Left Quantity", "Supplier ID", "Warehouse No");

            // Process the list and print each product
            for (BakeryFunction.Product product : products) {
                System.out.printf("%-15s %-20s %-10.2f %-15d %-15s %-15s%n",
                        product.getProductId(), product.getProductName(), product.getPrice(),
                        product.getQuantityLeft(), product.getSupplierId(), product.getWarehouseNo());
            }
        }
    }

    public static void trackId(Scanner scanner) {
        System.out.print("Enter Product ID: ");
        String inputId = scanner.nextLine();

        BakeryFunction.ProductDAO productDAO = new BakeryFunction.ProductDAO();
        List<BakeryFunction.Product> products = productDAO.findProductsById(inputId);  // Corrected method call

        // Check if products list is empty and handle appropriately
        if (products.isEmpty()) {
            System.out.println("No products found with the given ID.");
        } else {
            // Print the header
            System.out.printf("%-15s %-20s %-10s %-15s %-15s %-15s%n",
                    "ID", "Name", "Price", "Left Quantity", "Supplier ID", "Warehouse No");

            // Process the list and print each product
            for (BakeryFunction.Product product : products) {
                System.out.printf("%-15s %-20s %-10.2f %-15d %-15s %-15s%n",
                        product.getProductId(), product.getProductName(), product.getPrice(),
                        product.getQuantityLeft(), product.getSupplierId(), product.getWarehouseNo());
            }
        }
    }

    public static void trackPrice(Scanner scanner) {
        System.out.print("Enter Product Price (0.00): ");
        String inputPrice = scanner.nextLine();

        BakeryFunction.ProductDAO productDAO = new BakeryFunction.ProductDAO();
        List<BakeryFunction.Product> products = productDAO.findProductsByPrice(inputPrice);

        // Check if products list is empty and handle appropriately
        if (products.isEmpty()) {
            System.out.println("No products found with the given price.");
        } else {
            // Print the header
            System.out.printf("%-15s %-20s %-10s %-15s %-15s %-15s%n",
                    "ID", "Name", "Price", "Left Quantity", "Supplier ID", "Warehouse No");

            // Process the list and print each product
            for (BakeryFunction.Product product : products) {
                System.out.printf("%-15s %-20s %-10.2f %-15d %-15s %-15s%n",
                        product.getProductId(), product.getProductName(), product.getPrice(),
                        product.getQuantityLeft(), product.getSupplierId(), product.getWarehouseNo());
            }
        }
    }

    public static void trackWarehouse(Scanner scanner) {
        System.out.print("Enter Warehouse ID(W000X): ");
        String inputWarehouse = scanner.nextLine();

        BakeryFunction.ProductDAO productDAO = new BakeryFunction.ProductDAO();
        List<BakeryFunction.Product> products = productDAO.findProductsByWarehouse(inputWarehouse);

        String lastSupplierId = ""; // To keep track of the last displayed supplier ID

        // Check if products list is empty and handle appropriately
        if (products.isEmpty()) {
            System.out.println("No products found in the given warehouse.");
        } else {
            for (BakeryFunction.Product product : products) {
                String s_id = product.getSupplierId();
                if (!s_id.equals(lastSupplierId)) {
                    if (!lastSupplierId.equals("")) {
                        System.out.println(); // Print a blank line between different suppliers
                    }
                    System.out.printf("Supplier ID: %s%n", s_id);
                    System.out.printf("%-15s %-20s %-10s %-15s%n", "Product ID", "Product Name", "Price", "Left Quantity");
                    lastSupplierId = s_id;
                }
                System.out.printf("%-15s %-20s %-10.2f %-15d%n",
                        product.getProductId(), product.getProductName(), product.getPrice(),
                        product.getQuantityLeft());
            }
        }
    }

    public static void trackSupplier(Scanner scanner) {
        System.out.print("Enter Supplier ID(SUP00X): ");
        String inputSupplierId = scanner.nextLine();

        BakeryFunction.ProductDAO productDAO = new BakeryFunction.ProductDAO();
        List<BakeryFunction.Product> products = productDAO.findProductsBySupplier(inputSupplierId);

        String lastWarehouseNo = ""; // To keep track of the last displayed warehouse number

        // Check if products list is empty and handle appropriately
        if (products.isEmpty()) {
            System.out.println("No products found with the given supplier ID.");
        } else {
            for (BakeryFunction.Product product : products) {
                String w_id = product.getWarehouseNo();
                if (!w_id.equals(lastWarehouseNo)) {
                    if (!lastWarehouseNo.equals("")) {
                        System.out.println(); // Print a blank line between different warehouses
                    }
                    System.out.printf("Warehouse No: %s%n", w_id);
                    System.out.printf("%-15s %-20s %-10s %-15s%n", "Product ID", "Product Name", "Price", "Left Quantity");
                    lastWarehouseNo = w_id;
                }
                System.out.printf("%-15s %-20s %-10.2f %-15d%n",
                        product.getProductId(), product.getProductName(), product.getPrice(),
                        product.getQuantityLeft());
            }
        }
    }
}
