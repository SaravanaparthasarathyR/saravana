import java.sql.*;

public class DBConnection {

    public static Connection getConnection() {

        try {
            Class.forName("net.ucanaccess.jdbc.UcanaccessDriver");

            String url = "jdbc:ucanaccess://E:/tech/studentdb.accdb";

            return DriverManager.getConnection(url);

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}