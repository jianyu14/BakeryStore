package BakeryModule;

import BakeryDatabase.DatabaseUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.*;
import java.util.Scanner;

public class Login {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BakeryFunction.AccountDAO accountDAO = new BakeryFunction.AccountDAO();  // Create an instance of AccountDAO

        System.out.println("\nWelcome to TARUMT Bakery Inventory System!\n");
        while (true) {
            System.out.println("Enter to Login or type 'exit' to quit the program.");
            String proceed = scanner.nextLine();
            if (proceed.equalsIgnoreCase("exit")) {
                System.out.println("Exiting the system...");
                break;
            }

            System.out.println("Staff Login ");
            System.out.print("Enter Account ID: ");
            String accountId = scanner.nextLine();
            System.out.print("Enter Password: ");
            String password = scanner.nextLine();

            if (accountDAO.login(accountId, password)) {
                System.out.println("Login successful!\n");
                MainMenu.main(new String[]{});  // Assuming MainMenu is properly defined
            } else {
                System.out.println("Login failed. Please retry.\n");
            }
        }
    }
}