package BakeryModule;
import java.util.List;
import java.util.Scanner;

public class OrderHistory {

    private Scanner sc;
    private String order_id;


    // Constructor that accepts a Scanner object
    public OrderHistory(Scanner scanner) {
        this.sc = scanner;
    }

    public void checkOrderHistory() {
        System.out.print("Please enter the order ID to check the order (ORDA00000): ");
        order_id = sc.nextLine();

        BakeryFunction.OrderDAO orderDAO = new BakeryFunction.OrderDAO();
        BakeryFunction.Order order = orderDAO.getOrderByID(order_id);

        if (order != null) {
            displayOrderDetails(order);

            if (order.getStatus().equals("pending")){
                confirmOrderReceived(order);
            }
        } else {
            System.out.println("Order not found!");
        }
    }

    private void confirmOrderReceived(BakeryFunction.Order order) {
        System.out.print("Have you received your order? (Y/N): ");
        String input = sc.nextLine().trim().toUpperCase();  // Read the entire line and trim whitespace

        if (!input.isEmpty()) {
            char confirm = input.charAt(0);  // Take only the first character of the trimmed input

            while (true) {
                if (confirm == 'Y') {
                    order.setStatus("received");
                    updateOrderStatus(order);
                    updateProductQuantities(order.getOrderID());
                    System.out.println("Your order status is now changed to 'received'.");
                    break;
                } else if (confirm == 'N') {
                    System.out.println("Order status remained unchanged.");
                    break;
                } else {
                    System.out.print("Invalid input. Please enter 'Y' or 'N': ");
                    input = sc.nextLine().trim().toUpperCase();
                    if (!input.isEmpty()) {
                        confirm = input.charAt(0);  // Reassign confirm with the new input
                    }
                }
            }
        } else {
            System.out.println("No input detected. Please enter 'Y' or 'N'.");
        }
    }

    private void updateProductQuantities(String orderId) {
        BakeryFunction.OrderItemDAO orderItemDAO = new BakeryFunction.OrderItemDAO();
        BakeryFunction.ProductDAO productDAO = new BakeryFunction.ProductDAO();

        List<BakeryFunction.OrderItem> orderItems = orderItemDAO.getOrderItemsByOrderID(orderId);

        for (BakeryFunction.OrderItem item : orderItems) {
            BakeryFunction.Product product = productDAO.GetProductById(item.getProductID());

            if (product != null) {
                int newQuantity = product.getQuantityLeft() + item.getOrderQuantity();
                product.setQuantityLeft(newQuantity);
                productDAO.updateProduct(product);
            }
        }
    }


    private void updateOrderStatus(BakeryFunction.Order order) {
        BakeryFunction.OrderDAO orderDAO = new BakeryFunction.OrderDAO();
        orderDAO.updateOrder(order);
    }

    public void displayAllOrders() {
        BakeryFunction.OrderDAO orderDAO = new BakeryFunction.OrderDAO();
        List<BakeryFunction.Order> orders = orderDAO.getAllOrders();

        if (orders.isEmpty()) {
            System.out.println("No orders found.");
        } else {
            System.out.println("-------------------------------------------------------------------");
            System.out.printf("|%-9s %-9s %-10s %-22s %-11s|\n", "ORDER ID", "STATUS", "ORDER DATE", "GRAND TOTAL AMOUNT(RM)", "SUPPLIER ID");
            System.out.println("-------------------------------------------------------------------");

            for (BakeryFunction.Order order : orders) {
                System.out.printf("|%-9s %-9s %-10s %-22.2f %-11s|\n",
                        order.getOrderID(), order.getStatus(), order.getOrderDate(),
                        order.getGrandTotalAmount(), order.getSupplierID());
            }

            System.out.println("-------------------------------------------------------------------");
        }
    }


    private void displayOrderDetails(BakeryFunction.Order order) {
        BakeryFunction.OrderItemDAO orderItemDAO = new BakeryFunction.OrderItemDAO();
        BakeryFunction.ProductDAO productDAO = new BakeryFunction.ProductDAO();

        List<BakeryFunction.OrderItem> orderItems = orderItemDAO.getOrderItemsByOrderID(order.getOrderID());

        System.out.println("----------------------------------------------------------------------------------------------");
        System.out.printf("|%-18s %2s %-10s                                                            |\n", "Order ID", ":", order.getOrderID());
        System.out.printf("|%-18s %2s %-10s                                                            |\n", "Status", ":", order.getStatus());
        System.out.printf("|%-18s %2s %-10s                                                            |\n", "Order Date", ":", order.getOrderDate());
        System.out.printf("|%-18s %2s RM%-10.2f                                                          |\n", "Grand Total Amount", ":", order.getGrandTotalAmount());
        System.out.printf("|%-18s %2s %-10s                                                            |\n", "Supplier ID", ":", order.getSupplierID());
        System.out.println("|--------------------------------------------------------------------------------------------|");
        System.out.printf("|%-13s %-10s %-20s %-8s %-9s %-15s %-11s|\n", "ORDER ITEM ID", "PRODUCT ID", "PRODUCT NAME",
                "QUANTITY", "PRICE(RM)", "TOTAL PRICE(RM)", "EXPIRE DATE");

        for (BakeryFunction.OrderItem item : orderItems) {
            BakeryFunction.Product product = productDAO.GetProductById(item.getProductID());
            System.out.printf("|%-13s %-10s %-20s %-8d %-9.2f %-15.2f %-11s|\n",
                    item.getOrderItemID(), product.getProductId(), product.getProductName(),
                    item.getOrderQuantity(), product.getPrice(), item.getTotalAmount(), item.getExpireDate());
        }
        System.out.println("----------------------------------------------------------------------------------------------");
    }
}
