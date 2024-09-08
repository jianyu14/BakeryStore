package BakeryModule;

import java.util.Scanner;

public class MainMenu {

    private Scanner scanner;
    private CreateNewOrder createNewOrder;
    private OrderHistory orderHistory; // Instance of OrderHistory

    public MainMenu() {
        this.scanner = new Scanner(System.in);
        this.createNewOrder = new CreateNewOrder(); // Instantiate CreateNewOrder
        this.orderHistory = new OrderHistory(scanner); // Instantiate OrderHistory
    }

    public void showMenu() {
        int choice;
        do {
            System.out.println();
            System.out.println("Main MenuXXX:");
            System.out.println("1. Display Product Data");
            System.out.println("2. Track Product Warehouse");
            System.out.println("3. Track Order History");
            System.out.println("4. Create New Order");
            System.out.println("5. Enter Daily Sales");
            System.out.println("6. Logout");
            System.out.print("Please enter your choice (1-6): ");
            choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    System.out.println("Display Product Data\n");
                    DisplayProduct.displayMenu(scanner);
                    break;
                case 2:
                    System.out.println("Track Product Warehouse\n");
                    TrackProduct.displayCriteria(scanner);
                    break;
                case 3:
                    System.out.println("Track Order History\n");
                    orderHistory.checkOrderHistory(); // Call method using the instance
                    break;
                case 4:
                    System.out.println("Create New Order\n");
                    createNewOrder.initiateOrderProcess();
                    break;
                case 5:
                    System.out.println("Enter Daily Sales\n");
                    DailySales.sales();
                    break;
                case 6:
                    System.out.println("Logging Out...");
                    return;  // exit the loop and terminate the menu
                default:
                    System.out.println("Invalid choice. Please enter a valid number (1-6).");
                    break;
            }
        } while (true);
    }

    public static void main(String[] args) {
        MainMenu mainMenu = new MainMenu();
        mainMenu.showMenu();
    }
}