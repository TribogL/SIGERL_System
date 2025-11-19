package BackEnd;

import Connection.ClsConnection;
import Objects.ClsUser;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ClsMetUser {

    private final ClsConnection conexion;

    public ClsMetUser() {
        this.conexion = new ClsConnection();

    }

    public boolean emailExistsUser(String email) {
        String sql = "SELECT EmailUser AS email FROM tblUser WHERE EmailUser=? "
                + "UNION "
                + "SELECT EmailAdmin AS email FROM tblAdmin WHERE EmailAdmin=?";

        try (Connection cn = conexion.Connect(); PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, email);

            ResultSet rs = ps.executeQuery();
            return rs.next(); // true si existe

        } catch (Exception e) {
            System.err.println("Error checking email: " + e.getMessage());
            return true; // Por seguridad
        }
    }

    public boolean addUser(ClsUser user) {

        String sql = "INSERT INTO tbluser "
                + "(NameUser, LastNameUser, PhoneUser, EmailUser, PasswordUser, "
                + "ProjectUser, Permissions, CantRequestUser, AreaUser, RolUser) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection cn = conexion.Connect(); PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, user.getName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getPhone());
            ps.setString(4, user.getEmail());
            ps.setString(5, user.getPassword());
            ps.setString(6, user.getProject());
            ps.setBoolean(7, user.getPermissions());
            ps.setInt(8, user.getCantRequest());
            ps.setString(9, user.getArea());
            ps.setString(10, user.getRole());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.err.println("Error inserting USER: " + e.getMessage());
            return false;
        }
    }
}
