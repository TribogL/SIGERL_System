package BackEnd;

import Connection.ClsConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * Authentication service: - Validates login against tblAdmin and tblUser -
 * Returns a LoginResult object with basic user info
 */
public class ClsMetAuth {

    private final ClsConnection conexion;

    public ClsMetAuth() {
        this.conexion = new ClsConnection();
    }

    /**
     * DTO to hold login result information.
     */
    public static class LoginResult {

        public final int id;
        public final String name;
        public final String lastName;
        public final String accountType; // "ADMIN" or "USER"

        public LoginResult(int id, String name, String lastName, String accountType) {
            this.id = id;
            this.name = name;
            this.lastName = lastName;
            this.accountType = accountType;
        }
    }

    /**
     * Login method.
     *
     * @param email Email used for login
     * @param password Password used for login
     * @return LoginResult if credentials are valid, or null if invalid / error.
     */
    public LoginResult login(String email, String password) {
        try (Connection cn = conexion.Connect()) {

            // 1) Try ADMIN login
            // NOTE: Adjust ID column name if different in your DB (IDAdmin)
            String sqlAdmin = "SELECT IDAdmin, NameAdmin, LastNameAdmin "
                    + "FROM tblAdmin "
                    + "WHERE EmailAdmin = ? AND PasswordAdmin = ?";

            try (PreparedStatement psAdmin = cn.prepareStatement(sqlAdmin)) {
                psAdmin.setString(1, email);
                psAdmin.setString(2, password);

                try (ResultSet rsAdmin = psAdmin.executeQuery()) {
                    if (rsAdmin.next()) {
                        int id = rsAdmin.getInt("IDAdmin");
                        String name = rsAdmin.getString("NameAdmin");
                        String lastName = rsAdmin.getString("LastNameAdmin");

                        return new LoginResult(id, name, lastName, "ADMIN");
                    }
                }
            }

            // 2) Try USER login
            // NOTE: Adjust ID column name if different in your DB (IDUser)
            String sqlUser = "SELECT IDUser, NameUser, LastNameUser "
                    + "FROM tblUser "
                    + "WHERE EmailUser = ? AND PasswordUser = ?";

            try (PreparedStatement psUser = cn.prepareStatement(sqlUser)) {
                psUser.setString(1, email);
                psUser.setString(2, password);

                try (ResultSet rsUser = psUser.executeQuery()) {
                    if (rsUser.next()) {
                        int id = rsUser.getInt("IDUser");
                        String name = rsUser.getString("NameUser");
                        String lastName = rsUser.getString("LastNameUser");

                        return new LoginResult(id, name, lastName, "USER");
                    }
                }
            }

            // No match in either table
            return null;

        } catch (Exception e) {
            System.err.println("Auth Error: " + e.getMessage());
            // In case of internal error, return null too (handled in UI)
            return null;
        }
    }
}
