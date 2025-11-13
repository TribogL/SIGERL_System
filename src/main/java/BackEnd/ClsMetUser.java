package BackEnd;

import Connection.ClsConnection;
import Objects.ClsUser;
import java.sql.Connection;
import java.sql.PreparedStatement;

public class ClsMetUser {

    private final ClsConnection conexion;

    public ClsMetUser() {
        this.conexion = new ClsConnection(null);
    }

    public boolean insertUser(ClsUser user) {

        String sql = "INSERT INTO tbluser "
                + "(NameUser, LastNameUser, PhoneUser, EmailUser, PasswordUser, "
                + "ProjectUser, Permissions, CantRequestUser, AreaUser, RolUser) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection cn = conexion.Connect(); PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, user.getName());
            ps.setString(2, user.getLastName());
            ps.setString(3, user.getPhone());
            ps.setString(4, user.getEmail());
            ps.setBoolean(7, user.getPermissions());   // false para user normal
            ps.setInt(8, user.getCantRequest());      // 0 por defecto
            ps.setString(9, user.getArea());          // AreaUser
            ps.setString(10, user.getRol());          // RolUser

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.err.println("Error inserting USER: " + e.getMessage());
            return false;
        }
    }
}
