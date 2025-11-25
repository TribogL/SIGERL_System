package BackEnd;

import Connection.ClsConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.table.DefaultTableModel;

/**
 * Service class for admin dashboard statistics and recent activity.
 */
public class ClsMetDashboard {

    private final ClsConnection conexion;

    public ClsMetDashboard() {
        this.conexion = new ClsConnection();
    }

    /**
     * DTO with all counters shown in the Admin Dashboard.
     */
    public static class AdminDashboardStats {
        public int activeEquipmentToday;
        public int equipmentAvailable;
        public int activeUsersToday;
        public int lowStockEquipment;
        public int criticalStockEquipment;
    }

    /**
     * Returns all statistics needed in the admin dashboard.
     */
    public AdminDashboardStats getAdminDashboardStats() {
        AdminDashboardStats stats = new AdminDashboardStats();

        try (Connection cn = conexion.Connect()) {

            // 1) Active Equipment Today: distinct equipment in approved reservations for today
            String sqlActiveEq = """
                SELECT COUNT(DISTINCT EquipmentID) AS cnt
                FROM tblRequests
                WHERE StatusRequest = 'Approved'
                  AND CURDATE() BETWEEN StartDate AND EndDate
            """;
            try (PreparedStatement ps = cn.prepareStatement(sqlActiveEq);
                 ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    stats.activeEquipmentToday = rs.getInt("cnt");
                }
            }

            // 2) Equipment Available: equipment with positive stock and Availability = 1
            String sqlEqAvail = """
                SELECT COUNT(*) AS cnt
                FROM tblItems
                WHERE Quantity > 0 AND Availability = 1
            """;
            try (PreparedStatement ps = cn.prepareStatement(sqlEqAvail);
                 ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    stats.equipmentAvailable = rs.getInt("cnt");
                }
            }

            // 3) Active Users Today: distinct users with approved reservations today
            String sqlActUsers = """
                SELECT COUNT(DISTINCT UserID) AS cnt
                FROM tblRequests
                WHERE StatusRequest = 'Approved'
                  AND CURDATE() BETWEEN StartDate AND EndDate
            """;
            try (PreparedStatement ps = cn.prepareStatement(sqlActUsers);
                 ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    stats.activeUsersToday = rs.getInt("cnt");
                }
            }

            // 4) Low Stock Equipment: Quantity <= MinStock AND Quantity > 0
            String sqlLowStock = """
                SELECT COUNT(*) AS cnt
                FROM tblItems
                WHERE Quantity > 0 AND Quantity <= MinStock
            """;
            try (PreparedStatement ps = cn.prepareStatement(sqlLowStock);
                 ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    stats.lowStockEquipment = rs.getInt("cnt");
                }
            }

            // 5) Critical Stock Equipment: Quantity <= 0 OR Availability = 0
            String sqlCritStock = """
                SELECT COUNT(*) AS cnt
                FROM tblItems
                WHERE Quantity <= 0 OR Availability = 0
            """;
            try (PreparedStatement ps = cn.prepareStatement(sqlCritStock);
                 ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    stats.criticalStockEquipment = rs.getInt("cnt");
                }
            }

            return stats;

        } catch (Exception e) {
            System.err.println("Dashboard stats error: " + e.getMessage());
            return null;
        }
    }

    /**
     * Returns a table model for the "Recent Activity" section,
     * filtered by the admin that created/approved the requests.
     *
     * @param adminId IDAdmin of the logged admin
     */
    public DefaultTableModel getRecentActivityForAdmin(int adminId) {

        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("RequestID");
        model.addColumn("EquipmentID");
        model.addColumn("UserID");
        model.addColumn("Status");
        model.addColumn("RequestDate");

        String sql = """
            SELECT RequestID, EquipmentID, UserID, StatusRequest, RequestDate
            FROM tblRequests
            WHERE CreatedBy = ? OR ApprovedBy = ?
            ORDER BY RequestDate DESC
            LIMIT 10
        """;

        try (Connection cn = conexion.Connect();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, adminId);
            ps.setInt(2, adminId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Object[] row = new Object[5];
                    row[0] = rs.getInt("RequestID");
                    row[1] = rs.getInt("EquipmentID");
                    row[2] = rs.getInt("UserID");
                    row[3] = rs.getString("StatusRequest");
                    row[4] = rs.getDate("RequestDate");
                    model.addRow(row);
                }
            }

        } catch (Exception e) {
            System.err.println("Recent activity error: " + e.getMessage());
        }

        return model;
    }
}
