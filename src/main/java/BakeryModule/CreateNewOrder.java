package BakeryModule;

import BakeryDatabase.DatabaseUtils;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDate;



public class CreateNewOrder {
    String accountID = "";
    String supplierID = "";

    private Scanner scanner;
    private DatabaseUtils dbUtils;

    public CreateNewOrder() {
        this.scanner = new Scanner(System.in);
        this.dbUtils = new DatabaseUtils();
    }

    public void initiateOrderProcess() {
        // Display suppliers
        BakeryFunction.SupplierDAO.supplierList();
        boolean supplierIDValid = false;
        while (!supplierIDValid) {
            System.out.print("Enter supplier ID (SUPXXXX): ");
            supplierID = scanner.next();
            supplierIDValid = BakeryFunction.ProductDAO.displayProductSup(supplierID, scanner);
        }

        // Create Order and set ID
        BakeryFunction.Order order = new BakeryFunction.Order(supplierID);
        String newOrderId = BakeryFunction.OrderDAO.generateNewOrderId();
        order.setOrderID(newOrderId);
        order.setOrderDate(LocalDate.now());

        // Product selection process
        List<Integer> selectedProductIDs = new ArrayList<>();
        List<Integer> quantities = new ArrayList<>();
        List<Double> prices = new ArrayList<>();
        List<Double> totalAmounts = new ArrayList<>();
        boolean addingProducts = true;

        while (addingProducts) {
            System.out.print("\n\nEnter product ID (1-60) or 'X' to stop adding: ");
            String productID = scanner.next();
            scanner.nextLine();

            if (productID.equalsIgnoreCase("X")) {
                addingProducts = false;
            } else {
                int prodID = Integer.parseInt(productID);
                if (prodID > 0 && prodID <= 60) {
                    selectedProductIDs.add(prodID);
                    double price = BakeryFunction.ProductDAO.returnPrice(prodID);
                    System.out.print("Enter quantity for product " + productID + ": ");
                    int quantity = scanner.nextInt();
                    scanner.nextLine();

                    quantities.add(quantity);
                    prices.add(price);
                    double amount = quantity * price;
                    totalAmounts.add(amount);
                    System.out.println("Amount for this product is : " + amount);
                } else {
                    System.out.println("Invalid product ID!!");
                }
            }
        }

        double grandTotalAmount = totalAmounts.stream().mapToDouble(Double::doubleValue).sum();

        // Order confirmation
        System.out.println("Order Confirmation");
        BakeryFunction.ProductDAO.displayProductA(selectedProductIDs, quantities);

        System.out.print("Confirm to place order? (Y/N): ");
        String selection = scanner.nextLine();

        if (selection.equalsIgnoreCase("Y")) {
            boolean transfer = BakeryFunction.AccountDAO.deductBalance(grandTotalAmount);
            if (transfer) {
                System.out.println("\n\n");
                order.setGrandTotalAmount(grandTotalAmount);
                BakeryFunction.OrderDAO.saveOrder(order);
                BakeryFunction.OrderItemDAO.saveOrderItems(selectedProductIDs, quantities, prices, order.getOrderID());
                System.out.println("Order placed successfully.");
            } else {
                System.out.print("\n\nReload account? (Y/N): ");
                if (scanner.nextLine().equalsIgnoreCase("Y")) {
                    System.out.print("\n\nReloaded. Try placing the order again.");
                } else {
                    System.out.println("\n\nFailed to place order");
                }
            }
        } else {
            System.out.println("\n\nOrder canceled");
        }
    }
}

