package Connection;

import java.sql.*;
import javax.swing.JOptionPane;

public class ClsConnection {

    //Global Variable
    Connection CN;

    public ClsConnection(Connection CN) {

    }

    public Connection Connect() {

        try {
            String URL, User, Password;

            URL = "jdbc:mysql://localhost:3306/db_sigerl_system";
            User = "root";
            Password = "";

            Class.forName("com.mysql.cj.jdbc.Driver"); // Esto es por algo dentro del JAR
            CN = DriverManager.getConnection(URL, User, Password);

            if (CN != null) {
                JOptionPane.showMessageDialog(null, "Connection Succesfully!");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "The connection was not established." + e.getMessage());
        }
        return CN;
    }

}
