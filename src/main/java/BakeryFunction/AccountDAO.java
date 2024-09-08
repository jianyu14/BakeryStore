package BakeryFunction;


import BakeryDatabase.DatabaseUtils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.*;


//--------------------------------------------------------//
//--------------------For Login Module-------------------//
public class AccountDAO {
    public boolean login(String accountId, String password) {
        String query = "SELECT * FROM account WHERE acc_id = ? AND password = ?";
        try (Connection con = DatabaseUtils.getConnection(); // Assuming getConnection is a static method
             PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, accountId);
            pstmt.setString(2, password);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();  // Return true if there is a match
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    //--------------------------------------------------------//
    //------------For Create New Order Module-----------------//

    public static double getAccountBalance(String accID) {
        String sql = "SELECT balance FROM account WHERE acc_id = ?";
        try (Connection con = BakeryDatabase.DatabaseUtils.getConnection();
             PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, accID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getDouble("balance");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;  // Return -1 to indicate an error or not found
    }

    public static boolean deductBalance(double amount) { //use for payment

        try(Connection con = BakeryDatabase.DatabaseUtils.getConnection()) {

            String retrieveBalanceSQL = "SELECT balance from account"; //retrieve balance from database
            try (Statement stmt = con.createStatement();
                 ResultSet rs = stmt.executeQuery(retrieveBalanceSQL);) {
                while(rs.next()) {

                    double balance = rs.getDouble("balance");

                    if(balance >= amount) { //if balance is sufficient amount to make payment

                        double newBalance = balance - amount;

                        //then subtract and get newBalance, store back to database

                        String insertOrderSQL = "UPDATE account SET balance = ?";
                        try (PreparedStatement pstmt = con.prepareStatement(insertOrderSQL)) {
                            pstmt.setDouble(1, newBalance);
                            int rowsAffected = pstmt.executeUpdate();
                            if (rowsAffected > 0) {
                                System.out.println("Balance deducted. New balance: $" + newBalance);
                            } else {
                                System.out.println("No rows affected. Payment failed.");
                            }
                        }

                    } else {
                        System.out.println("Insufficient Balance");
                        return false;
                    }
                }
            }

        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return true;
    }

}
