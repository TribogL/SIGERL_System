    package BackEnd;

import Connection.ClsConnection;
import Objects.ClsRequest;
import java.sql.Connection;
import javax.swing.JOptionPane;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList; // Array que cambia de tamaño automaticamente para mover datos mas facil.
import java.util.List;
import javax.swing.table.DefaultTableModel;

public class ClsMetRequests {

    private final ClsConnection conexion;

    public ClsMetRequests() {
        this.conexion = new ClsConnection();
    }

    // Logicas de reservas, estadisticas y chequeo de disponibilidad
    // Creacion de solicitud de reserva
    public boolean createRequest(int userId, int equipmentId, Date startDate, Date endDate, String purpose, boolean isAdmin) {
        String NEW = "INSERT INTO tblRequests (UserID, EquipmentID, StartDate, EndDate, Purpose, StatusRequest, RequestDate) VALUES (?, ?, ?, ?, ?, ?, CURDATE())";

        try (Connection cn = conexion.Connect(); PreparedStatement ps = cn.prepareStatement(NEW)) {

            ps.setInt(1, userId);
            ps.setInt(2, equipmentId);
            ps.setDate(3, startDate);
            ps.setDate(4, endDate);
            ps.setString(5, purpose);
            // If admin, auto-approve. If user, set as pending
            ps.setString(6, isAdmin ? "Approved" : "Pending");

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error making reservation: " + e.getMessage(), "DB Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    // Aprobacion de solicitudes
    public boolean approveRequest(int requestId) {
        String APR = "UPDATE tblRequests SET StatusRequest = 'Approved' WHERE RequestID = ?";

        try (Connection cn = conexion.Connect(); PreparedStatement ps = cn.prepareStatement(APR)) {

            ps.setInt(1, requestId);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error approving reservation: " + e.getMessage(), "DB Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    // Rechazo de solicitudes
    public boolean rejectRequest(int requestId) {
        String REJ = "UPDATE tblRequests SET StatusRequest = 'Rejected' WHERE RequestID = ?";

        try (Connection cn = conexion.Connect(); PreparedStatement ps = cn.prepareStatement(REJ)) {

            ps.setInt(1, requestId);
            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.err.println("Error rejecting request: " + e.getMessage());
            return false;
        }
    }

    // Conteo de reservaciones activas al dia de hoy
    public int getActiveReservationsToday() {
        String ACT = "SELECT COUNT(*) as total FROM tblRequests WHERE StatusRequest = 'Approved' AND CURDATE() BETWEEN StartDate AND EndDate";

        try (Connection cn = conexion.Connect(); PreparedStatement ps = cn.prepareStatement(ACT); ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("total");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error getting active reservations: " + e.getMessage(), "DB Error", JOptionPane.ERROR_MESSAGE);
        }
        return 0;
    }

    // Conteo de usuarios con reservaciones activas al dia de hoy
    public int getActiveUsersToday() {
        String ACTu = "SELECT COUNT(DISTINCT UserID) as total FROM tblRequests WHERE StatusRequest = 'Approved' AND CURDATE() BETWEEN StartDate AND EndDate";

        try (Connection cn = conexion.Connect(); PreparedStatement ps = cn.prepareStatement(ACTu); ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("total");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error getting active users: " + e.getMessage(), "DB Error", JOptionPane.ERROR_MESSAGE);
        }
        return 0;
    }

    // Conteo de reservaciones pendientes de un usuario
    public int getUserPendingRequests(int userId) {
        String PENu = "SELECT COUNT(*) as total FROM tblRequests WHERE UserID = ? AND StatusRequest = 'Pending'";

        try (Connection cn = conexion.Connect(); PreparedStatement ps = cn.prepareStatement(PENu)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("total");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error getting user's pending requests: " + e.getMessage(), "DB Error", JOptionPane.ERROR_MESSAGE);
        }
        return 0;
    }

    // Conteo de equipos atrasados de un usuario (por devolver)
    public int getUserOverdueEquipment(int userId) {
        String DUE = "SELECT COUNT(*) as total FROM tblRequests WHERE UserID = ? AND StatusRequest = 'Approved' AND CURDATE() > EndDate";

        try (Connection cn = conexion.Connect(); PreparedStatement ps = cn.prepareStatement(DUE)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt("total");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error getting overdue equipment: " + e.getMessage(), "DB Error", JOptionPane.ERROR_MESSAGE);
        }
        return 0;
    }

    // Actividad reciente: Ultimas 10 acciones de admin
    public DefaultTableModel getRecentActivity() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Date");
        model.addColumn("Action");
        model.addColumn("User/Item");
        model.addColumn("Details");

        String sql = "("
                + "SELECT RequestDate as ActivityDate, 'Reservation' as ActionType, "
                + "CONCAT(u.NameUser, ' ', u.LastNameUser) as Actor, "
                + "CONCAT(i.Name, ' - ', StatusRequest) as Details "
                + "FROM tblRequests r "
                + "JOIN tblUser u ON r.UserID = u.IDUser "
                + "JOIN tblItems i ON r.EquipmentID = i.EquipmentID"
                + ") UNION ALL ("
                + "SELECT DateAdded as ActivityDate, 'New Equipment' as ActionType, "
                + "Name as Actor, Category as Details "
                + "FROM tblItems WHERE DateAdded IS NOT NULL"
                + ") ORDER BY ActivityDate DESC LIMIT 10";

        try (Connection cn = conexion.Connect(); PreparedStatement ps = cn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Object[] row = {
                    rs.getDate("ActivityDate"),
                    rs.getString("ActionType"),
                    rs.getString("Actor"),
                    rs.getString("Details")
                };
                model.addRow(row);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error getting recent activity: " + e.getMessage(), "DB Error", JOptionPane.ERROR_MESSAGE);
        }

        return model;
    }
    
    // Conteo de equipos activos de un usuario al dia de hoy
    public DefaultTableModel getUserActiveEquipment(int userId) {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Equipment");
        model.addColumn("Start Date");
        model.addColumn("End Date");
        model.addColumn("Days Left");
        model.addColumn("Location");

        // i. y r. son para identificar valores temporalmente
        String ACTe = "SELECT i.Name, r.StartDate, r.EndDate, DATEDIFF(r.EndDate, CURDATE()) as DaysLeft, i.Location FROM tblRequests r JOIN tblItems i ON r.EquipmentID = i.EquipmentID WHERE r.UserID = ? AND r.StatusRequest = 'Approved' AND CURDATE() BETWEEN r.StartDate AND r.EndDate";

        try (Connection cn = conexion.Connect(); PreparedStatement ps = cn.prepareStatement(ACTe)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Object[] row = {
                    rs.getString("Name"),
                    rs.getDate("StartDate"),
                    rs.getDate("EndDate"),
                    rs.getInt("DaysLeft") + " days",
                    rs.getString("Location")
                };
                model.addRow(row);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error getting user active equipment: " + e.getMessage(), "DB Error", JOptionPane.ERROR_MESSAGE);
        }

        return model;
    }

     // Consulta de ultima reserva de un usuario
    public ClsRequest getUserLastRequest(int userId) {
        String sql = "SELECT * FROM tblRequests WHERE UserID = ? ORDER BY RequestDate DESC LIMIT 1";

        try (Connection cn = conexion.Connect(); PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new ClsRequest(
                        rs.getString("StatusRequest"),
                        rs.getDate("RequestDate").toString()
                );
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error getting user's last request: " + e.getMessage(), "DB Error", JOptionPane.ERROR_MESSAGE);
        }

        return null;
    }

     // Chequeo de si un equipo esta disponible en una fecha
    public boolean isEquipmentAvailable(int equipmentId, Date startDate, Date endDate) {
        //
        String AvailCheck = "SELECT COUNT(*) as conflicts FROM tblRequests WHERE EquipmentID = ? AND StatusRequest = 'Approved' AND NOT (EndDate < ? OR StartDate > ?)";

        try (Connection cn = conexion.Connect(); PreparedStatement ps = cn.prepareStatement(AvailCheck)) {

            ps.setInt(1, equipmentId);
            ps.setDate(2, startDate);
            ps.setDate(3, endDate);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt("conflicts") == 0;
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error checking availability: " + e.getMessage(), "DB Error", JOptionPane.ERROR_MESSAGE);
            }

        return false;
    }

     // Listado de todas las reservaciones para mostrar en un JTable
    public DefaultTableModel listAllReservations() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("ID");
        model.addColumn("User");
        model.addColumn("Equipment");
        model.addColumn("Start Date");
        model.addColumn("End Date");
        model.addColumn("Status");
        model.addColumn("Purpose");

        String sql = "SELECT r.RequestID, CONCAT(u.NameUser, ' ', u.LastNameUser) as UserName, i.Name as EquipmentName, r.StartDate, r.EndDate, r.StatusRequest, r.Purpose FROM tblRequests r JOIN tblUser u ON r.UserID = u.IDUser JOIN tblItems i ON r.EquipmentID = i.EquipmentID ORDER BY r.RequestDate DESC";

        try (Connection cn = conexion.Connect(); PreparedStatement ps = cn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Object[] row = {
                    rs.getInt("RequestID"),
                    rs.getString("UserName"),
                    rs.getString("EquipmentName"),
                    rs.getDate("StartDate"),
                    rs.getDate("EndDate"),
                    rs.getString("StatusRequest"),
                    rs.getString("Purpose")
                };
                model.addRow(row);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error listing reservations: " + e.getMessage(), "DB Error", JOptionPane.ERROR_MESSAGE);
            }

        return model;
    }
    

}
