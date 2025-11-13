package Connection;

import java.sql.*;
import javax.swing.JOptionPane;

public class ClsConnection {

    Connection CN;

    // ✔️ Constructor SIN parámetros
    public ClsConnection() {
    }

    public Connection Connect() {
        try {
            String URL, User, Password;

            URL = "jdbc:mysql://localhost:3306/db_sigerl_system";
            User = "root";
            Password = "";

            Class.forName("com.mysql.cj.jdbc.Driver"); 
            CN = DriverManager.getConnection(URL, User, Password);

            if (CN != null) {
                System.out.println("Connection Successfully!"); 
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "The connection was not established: " + e.getMessage());
        }
        return CN;
    }
}
