package BakeryModule;
import java.util.Scanner;

public class DailySales {

    public static void sales() {
        Scanner sc = new Scanner(System.in);
        BakeryFunction.ProductDAO productDAO = new BakeryFunction.ProductDAO();

        System.out.println("Modify Quantity");
        System.out.println("-------------------");

        System.out.print("Enter the Product ID: ");
        String productID = sc.nextLine();

        System.out.print("Enter the quantity sold today: ");
        int quantitySold = sc.nextInt();

        try {
            int currentQuantity = productDAO.getCurrentQuantity(productID);
            if (currentQuantity == -1) {
                System.out.println("No product found with Product ID: " + productID);
                return;
            }

            int newQuantity = currentQuantity - quantitySold;
            if (newQuantity < 0) {
                System.out.println("Error: Quantity sold exceeds available stock.");
                return;
            }

            if (productDAO.updateProductQuantity(productID, newQuantity)) {
                System.out.println("Quantity updated successfully.");
                System.out.println("New left_quantity: " + newQuantity);
                System.out.println("Today " + productID + " have sold: " + quantitySold);
            } else {
                System.out.println("Failed to update product quantity.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
