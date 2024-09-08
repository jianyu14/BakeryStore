package BakeryDatabase;

import java.sql.*;

public class DatabaseUtils {

    private static final String DBURL = "jdbc:mysql://localhost:3306/bakerystoredb";
    private static final String DBUSER = "root";
    private static final String DBPASS = "123456";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DBURL, DBUSER, DBPASS);
    }
}
