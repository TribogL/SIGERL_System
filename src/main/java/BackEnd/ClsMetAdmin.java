package BackEnd;

import Connection.ClsConnection;
import Objects.ClsAdmin;
import java.sql.Connection;
import java.sql.PreparedStatement;
import javax.swing.JOptionPane;

public class ClsMetAdmin {

    private final ClsConnection conexion;

    public ClsMetAdmin() {
        this.conexion = new ClsConnection();

    }

    public boolean emailExistsAdmin(String email) {
        String sql = "SELECT EmailAdmin FROM tbladmin WHERE EmailAdmin = ?";
        try (Connection cn = conexion.Connect(); PreparedStatement ps = cn.prepareStatement(sql)) {
            ps.setString(1, email);
            return ps.executeQuery().next(); // true si existe
        } catch (Exception e) {
            System.err.println("Error checking ADMIN email: " + e.getMessage());
            return false;
        }
    }

    public boolean addAdmin(ClsAdmin admin) {

        String sql = "INSERT INTO tbladmin "
                + "(NameAdmin, LastNameAdmin, PhoneAdmin, EmailAdmin, PasswordAdmin, "
                + "ProjectAdmin, Permissions, CantRequestAdmin, AreaAdmin, RangeAdmin) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection cn = conexion.Connect(); PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, admin.getName());
            ps.setString(2, admin.getLastName());
            ps.setString(3, admin.getPhone());
            ps.setString(4, admin.getEmail());
            ps.setString(5, admin.getPassword());
            ps.setString(6, admin.getProject());      // ProjectAdmin
            ps.setBoolean(7, admin.getPermissions());  // true para admin
            ps.setInt(8, admin.getCantRequest());     // normalmente 0
            ps.setString(9, admin.getArea());         // AreaAdmin
            ps.setString(10, admin.getRange());       // RangeAdmin

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, e.toString(), "SQL Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
}
