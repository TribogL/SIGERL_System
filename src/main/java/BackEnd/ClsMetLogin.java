package BackEnd;

import Connection.ClsConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ClsMetLogin {

    private final ClsConnection conexion;

    public ClsMetLogin() {
        this.conexion = new ClsConnection();
    }

    public LoginResult validateLogin(String email, String password) {

        String sqlUser = "SELECT IDUser, NameUser, Permissions FROM tbluser WHERE EmailUser = ? AND PasswordUser = ?";
        String sqlAdmin = "SELECT IDAdmin, NameAdmin, Permissions FROM tbladmin WHERE EmailAdmin = ? AND PasswordAdmin = ?";

        try (Connection cn = conexion.Connect()) {

            // ---- USER LOGIN ----
            try (PreparedStatement ps = cn.prepareStatement(sqlUser)) {
                ps.setString(1, email);
                ps.setString(2, password);

                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    return new LoginResult(
                            rs.getInt("IDUser"),
                            rs.getString("NameUser"),
                            rs.getInt("Permissions"),
                            "USER"
                    );
                }
            }

            // ---- ADMIN LOGIN ----
            try (PreparedStatement ps = cn.prepareStatement(sqlAdmin)) {
                ps.setString(1, email);
                ps.setString(2, password);

                ResultSet rs = ps.executeQuery();
                if (rs.next()) {
                    return new LoginResult(
                            rs.getInt("IDAdmin"),
                            rs.getString("NameAdmin"),
                            rs.getInt("Permissions"),
                            "ADMIN"
                    );
                }
            }

        } catch (Exception e) {
            System.err.println("Login error: " + e.getMessage());
        }

        return null; // No se encontró
    }

    // Clase interna para transportar datos del login
    public static class LoginResult {

        public int id;
        public String name;
        public int permissions;
        public String accountType;

        public LoginResult(int id, String name, int permissions, String accountType) {
            this.id = id;
            this.name = name;
            this.permissions = permissions;
            this.accountType = accountType;
        }
    }
}
