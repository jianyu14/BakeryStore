package BakeryFunction;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;
import java.time.LocalDate;
import java.sql.Date;
import BakeryDatabase.DatabaseUtils;
import java.text.SimpleDateFormat;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class SupplierDAO {

    static DatabaseUtils dbUtils = new DatabaseUtils();

    public static void supplierList() {
        String query = "SELECT * FROM Supplier;";
        try (Connection con = DatabaseUtils.getConnection();
             Statement stmt = con.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            System.out.printf("%80s\n", "Supplier List");
            System.out.println("============================================================================================================");
            System.out.printf("|%-12s|%-25s|%-50s|%-15s|\n", "Supplier ID", "Supplier Name", "Supplier Address", "Supplier Contact");
            System.out.println("============================================================================================================");

            while (rs.next()) {
                String supplierID = rs.getString("supplier_id");
                String supplierName = rs.getString("supplier_name");
                String supplierAddress = rs.getString("supplier_address");
                String supplierContact = rs.getString("supplier_contact");

                System.out.printf("|%-12s|%-25s|%-50s|%-15s|\n", supplierID, supplierName, supplierAddress, supplierContact);
                System.out.println("------------------------------------------------------------------------------------------------------------");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Supplier getSupplierDetails(String supplierID) {
        String query = "SELECT * FROM Supplier WHERE supplier_id = ?";  // SQL query with a placeholder
        try (Connection con = DatabaseUtils.getConnection();
             PreparedStatement pstmt = con.prepareStatement(query)) {  // Using PreparedStatement
            pstmt.setString(1, supplierID);  // Setting the placeholder value
            try (ResultSet rs = pstmt.executeQuery()) {  // Executing the query
                if (rs.next()) {
                    return new Supplier(
                            rs.getString("supplier_id"),
                            rs.getString("supplier_name"),
                            rs.getString("supplier_address"),
                            rs.getString("supplier_contact")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;  // Return null if no supplier is found or if an exception occurs
    }

}
