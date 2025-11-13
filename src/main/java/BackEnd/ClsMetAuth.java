package BackEnd;

import Connection.ClsConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ClsMetAuth {

    private final ClsConnection conexion;

    public ClsMetAuth() {
        this.conexion = new ClsConnection();
    }

    /**
     * Login method: Returns: 1 → Admin 0 → User -1 → Email/password incorrect
     * -2 → Internal DB error
     */
    public int login(String email, String password) {
        try (Connection cn = conexion.Connect()) {

            // 🔍 1. Buscar en tblAdmin
            String sqlAdmin = "SELECT Permissions FROM tblAdmin WHERE EmailAdmin=? AND PasswordAdmin=?";
            PreparedStatement psAdmin = cn.prepareStatement(sqlAdmin);
            psAdmin.setString(1, email);
            psAdmin.setString(2, password);
            ResultSet rsAdmin = psAdmin.executeQuery();

            if (rsAdmin.next()) {
                return 1; // ADMIN
            }

            // 🔍 2. Buscar en tblUser
            String sqlUser = "SELECT Permissions FROM tblUser WHERE EmailUser=? AND PasswordUser=?";
            PreparedStatement psUser = cn.prepareStatement(sqlUser);
            psUser.setString(1, email);
            psUser.setString(2, password);
            ResultSet rsUser = psUser.executeQuery();

            if (rsUser.next()) {
                return 0; // USER
            }

            return -1; // ❌ No coincide nada

        } catch (Exception e) {
            System.err.println("Auth Error: " + e.getMessage());
            return -2; // ❌ error interno
        }
    }
}
