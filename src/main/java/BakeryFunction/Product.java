package BakeryFunction;

public class Product {
    private String productId;
    private String productName;
    private double price;
    private int quantityLeft;
    private String supplierId;
    private String warehouseNo;


    public Product() {
    }

    public Product(String productId, String productName, double price,
                   int quantityLeft, String supplierId, String warehouseNo) {
        this.productId = productId;
        this.productName = productName;
        this.price = price;
        this.quantityLeft = quantityLeft;
        this.supplierId = supplierId;
        this.warehouseNo = warehouseNo;
    }

    // Getters
    public String getProductId() {
        return productId;
    }


    public String getProductName() {
        return productName;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantityLeft() {
        return quantityLeft;
    }


    public String getSupplierId() {
        return supplierId;
    }

    public String getWarehouseNo() {
        return warehouseNo;
    }

    // Setters
    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setQuantityLeft(int quantityLeft) {
        this.quantityLeft = quantityLeft;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }

    public void setWarehouseNo(String warehouseNo) {
        this.warehouseNo = warehouseNo;
    }



}
