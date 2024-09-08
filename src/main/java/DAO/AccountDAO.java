package DAO;

import BakeryFunction.Account;
import BakeryDatabase.DatabaseUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Account class DAO
public class AccountDAO implements Dao<Account> {

    // Retrieve all accounts
    @Override
    public List<Account> getAll() {
        String query = "SELECT * FROM Account";
        List<Account> accounts = new ArrayList<>();

        try (Connection conn = DatabaseUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Account account = new Account();
                account.setAccId(rs.getString("acc_id"));
                account.setPassword(rs.getString("password"));
                account.setBalance(rs.getDouble("balance"));
                accounts.add(account);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return accounts;
    }

    // Retrieve a specific account by ID
    @Override
    public Account getById(String accId) {
        String query = "SELECT * FROM Account WHERE acc_id = ?";
        Account account = null;

        try (Connection conn = DatabaseUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, accId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                account = new Account();
                account.setAccId(rs.getString("acc_id"));
                account.setPassword(rs.getString("password"));
                account.setBalance(rs.getDouble("balance"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return account;
    }

    // Insert a new account
    @Override
    public boolean insert(Account account) {
        String query = "INSERT INTO Account (acc_id, password, balance) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, account.getAccId());
            stmt.setString(2, account.getPassword());
            stmt.setDouble(3, account.getBalance());

            return stmt.executeUpdate() > 0; // returns true if the insert was successful
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // Update an existing account
    @Override
    public boolean update(Account account) {
        String query = "UPDATE Account SET password = ?, balance = ? WHERE acc_id = ?";

        try (Connection conn = DatabaseUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, account.getPassword());
            stmt.setDouble(2, account.getBalance());
            stmt.setString(3, account.getAccId());

            return stmt.executeUpdate() > 0; // returns true if the update was successful
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    // Delete an account by ID
    @Override
    public boolean deleteById(String accId) {
        String query = "DELETE FROM Account WHERE acc_id = ?";

        try (Connection conn = DatabaseUtils.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, accId);
            return stmt.executeUpdate() > 0; // returns true if the delete was successful
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

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
