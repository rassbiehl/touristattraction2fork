import java.sql.Connection;
import java.sql.DriverManager;

public class MySQLTest {
    public static void main(String[] args) {
        String url = "jdbc:mysql://touristguidefinal.mysql.database.azure.com:3306/tourist_attractions?useSSL=true&requireSSL=true";
        String user = "rasbiehl";
       
        try {
            // Load the MySQL JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");
            // Attempt to establish a connection
            Connection conn = DriverManager.getConnection(url, user, password);
            System.out.println("Connection successful!");
            conn.close(); // Close the connection
        } catch (Exception e) {
            // Print the stack trace for debugging
            e.printStackTrace();
        }
    }
}
