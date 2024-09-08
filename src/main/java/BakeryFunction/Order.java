package BakeryFunction;

import java.util.Scanner;
import java.time.LocalDate;


public class Order {
    private String order_id;
    private String status;
    private LocalDate orderDate;  // Use LocalDate
    private double grand_total_amount;
    private String supplier_id;
    private Scanner sc = new Scanner(System.in);

    // Default Constructor with default initialization
    public Order() {
        this.status = "pending"; // Default status
        this.orderDate = LocalDate.now(); // Default to current date
        this.grand_total_amount = 0; // Default amount
        this.order_id = generateNewOrderID();

    }

    // Constructor with supplier_id
    public Order(String supplier_id) {
        this(); // Call the default constructor to initialize other fields
        this.supplier_id = supplier_id; // Set supplier_id specifically
    }

    // Getters and Setters
    public void setOrderID(String order_id) {
        this.order_id = order_id;
    }

    public String getOrderID() {
        return order_id;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setOrderDate(LocalDate orderDate) {
        this.orderDate = orderDate;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public void setGrandTotalAmount(double grand_total_amount) {
        this.grand_total_amount = grand_total_amount;
    }

    public double getGrandTotalAmount() {
        return grand_total_amount;
    }

    public void setSupplierID(String supplier_id) {
        this.supplier_id = supplier_id;
    }

    public String getSupplierID() {
        return supplier_id;
    }

    public static String generateNewOrderID() {
        return ""; // Placeholder for actual implementation
    }
}

