package BakeryFunction;

public class OrderItem {
    private String orderItemID;
    private String orderID;
    private String productID;
    private int orderQuantity;
    private double totalAmount;
    private String expireDate;

    // Default constructor
    public OrderItem() {
    }

    // Parameterized constructor
    public OrderItem(String orderItemID, String orderID, String productID,
                     int orderQuantity, double totalAmount, String expireDate) {
        this.orderItemID = orderItemID;
        this.orderID = orderID;
        this.productID = productID;
        this.orderQuantity = orderQuantity;
        this.totalAmount = totalAmount;
        this.expireDate = expireDate;
    }

    // Getters
    public String getOrderItemID() {
        return orderItemID;
    }

    public String getOrderID() {
        return orderID;
    }

    public String getProductID() {
        return productID;
    }

    public int getOrderQuantity() {
        return orderQuantity;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public String getExpireDate() {
        return expireDate;
    }

    // Setters
    public void setOrderItemID(String orderItemID) {
        this.orderItemID = orderItemID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public void setOrderQuantity(int orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public void setExpireDate(String expireDate) {
        this.expireDate = expireDate;
    }
}

