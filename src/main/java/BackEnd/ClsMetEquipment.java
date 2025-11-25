package BackEnd;

import Objects.ClsEquipment;
import Connection.ClsConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ClsMetEquipment {

    private final ClsConnection conexion;
    private PreparedStatement ps;
    private ResultSet rs;
    private String admMsg;

    public ClsMetEquipment() {
        conexion = new ClsConnection();
        admMsg = "";
    }

    // ---------------------------------------------------------
    // CREATE
    // ---------------------------------------------------------
    public String AddEquipment(ClsEquipment obj) {
        // OJO: usamos tblitems y EquipmentID, como está en tu tabla
        String sql = "INSERT INTO tblitems "
                + "(Name, Category, Supplier, Location, Status, Quantity, MinStock, "
                + "Description, DateAdded, Availability) "
                + "VALUES (?,?,?,?,?,?,?,?,CURDATE(),?)";

        try (Connection cn = conexion.Connect();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, obj.getEquipmentName());
            ps.setString(2, obj.getEquipmentCategory());
            ps.setString(3, obj.getEquipmentSupplier());
            ps.setString(4, obj.getEquipmentLocation());
            ps.setString(5, obj.getEquipmentStatus());
            ps.setInt(6, obj.getEquipmentQuantity());
            ps.setInt(7, obj.getEquipmentMinStock());
            ps.setString(8, obj.getDescription());
            ps.setBoolean(9, obj.getEquipmentQuantity() > 0); // Availability

            int res = ps.executeUpdate();
            if (res >= 1) {
                admMsg = "Equipment added successfully.";
            } else {
                admMsg = "Equipment could not be added.";
            }
        } catch (Exception e) {
            admMsg = "Database error: " + e.getMessage();
        }

        return admMsg;
    }

    // ---------------------------------------------------------
    // UPDATE
    // ---------------------------------------------------------
    public String UpdateEquipment(ClsEquipment obj) {
        String sql = "UPDATE tblitems SET "
                + "Name=?, Category=?, Supplier=?, Location=?, Status=?, "
                + "Quantity=?, MinStock=?, Description=?, Availability=? "
                + "WHERE EquipmentID=?";

        try (Connection cn = conexion.Connect();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setString(1, obj.getEquipmentName());
            ps.setString(2, obj.getEquipmentCategory());
            ps.setString(3, obj.getEquipmentSupplier());
            ps.setString(4, obj.getEquipmentLocation());
            ps.setString(5, obj.getEquipmentStatus());
            ps.setInt(6, obj.getEquipmentQuantity());
            ps.setInt(7, obj.getEquipmentMinStock());
            ps.setString(8, obj.getDescription());
            ps.setBoolean(9, obj.getEquipmentQuantity() > 0);
            ps.setInt(10, obj.getEquipmentID()); // OJO: EquipmentID

            int res = ps.executeUpdate();
            if (res >= 1) {
                admMsg = "Equipment updated successfully.";
            } else {
                admMsg = "Equipment could not be updated.";
            }
        } catch (Exception e) {
            admMsg = "Database error: " + e.getMessage();
        }

        return admMsg;
    }

    // ---------------------------------------------------------
    // DELETE
    // ---------------------------------------------------------
    public String DeleteEquipment(int equipmentId) {
        String sql = "DELETE FROM tblitems WHERE EquipmentID=?";

        try (Connection cn = conexion.Connect();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, equipmentId);

            int res = ps.executeUpdate();
            if (res >= 1) {
                admMsg = "Equipment deleted successfully.";
            } else {
                admMsg = "Equipment could not be deleted.";
            }
        } catch (Exception e) {
            admMsg = "Database error: " + e.getMessage();
        }

        return admMsg;
    }

    // ---------------------------------------------------------
    // READ (uno)
    // ---------------------------------------------------------
    public ClsEquipment SearchEquipment(int equipmentId) {
        String sql = "SELECT * FROM tblitems WHERE EquipmentID=?";

        try (Connection cn = conexion.Connect();
             PreparedStatement ps = cn.prepareStatement(sql)) {

            ps.setInt(1, equipmentId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ClsEquipment equipment = new ClsEquipment(
                            rs.getString("Name"),
                            rs.getString("Category"),
                            rs.getString("Supplier"),
                            rs.getString("Location"),
                            rs.getString("Status"),
                            rs.getInt("Quantity"),
                            rs.getInt("MinStock"),
                            rs.getString("Description")
                    );
                    equipment.setEquipmentID(rs.getInt("EquipmentID"));
                    return equipment;
                }
            }

        } catch (Exception e) {
            System.err.println("Error searching equipment: " + e.getMessage());
        }

        return null;
    }

    // ---------------------------------------------------------
    // READ (listado para la tabla)
    // ---------------------------------------------------------
    public DefaultTableModel ListEquipment() {
        DefaultTableModel model = new DefaultTableModel();

        model.addColumn("EquipmentID");
        model.addColumn("Name");
        model.addColumn("Category");
        model.addColumn("Supplier");
        model.addColumn("Location");
        model.addColumn("Status");
        model.addColumn("Quantity");
        model.addColumn("MinimumStock");
        model.addColumn("Availability");

        String sql = "SELECT EquipmentID, Name, Category, Supplier, Location, "
                + "Status, Quantity, MinStock, Availability "
                + "FROM tblitems";

        try (Connection cn = conexion.Connect();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Object[] row = {
                        rs.getInt("EquipmentID"),
                        rs.getString("Name"),
                        rs.getString("Category"),
                        rs.getString("Supplier"),
                        rs.getString("Location"),
                        rs.getString("Status"),
                        rs.getInt("Quantity"),
                        rs.getInt("MinStock"),
                        rs.getBoolean("Availability")
                };
                model.addRow(row);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Database error: " + e.getMessage(),
                    "DB Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }

        return model;
    }

    // ---------------------------------------------------------
    // STATS para Inventory / Dashboard
    // ---------------------------------------------------------

    // Total de equipos
    public int getTotalEquipmentCount() {
        String sql = "SELECT COUNT(*) AS total FROM tblitems";

        try (Connection cn = conexion.Connect();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("total");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Error counting total equipment: " + e.getMessage(),
                    "DB Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
        return 0;
    }

    // Equipos en low stock (Quantity <= MinStock y > 0)
    public int getLowStockCount() {
        String sql = "SELECT COUNT(*) AS total "
                + "FROM tblitems "
                + "WHERE Quantity <= MinStock AND Quantity > 0";

        try (Connection cn = conexion.Connect();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("total");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Error counting low stock: " + e.getMessage(),
                    "DB Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
        return 0;
    }

    // Equipos en stock crítico (Quantity = 0)
    public int getCriticalStockCount() {
        String sql = "SELECT COUNT(*) AS total "
                + "FROM tblitems "
                + "WHERE Quantity = 0";

        try (Connection cn = conexion.Connect();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("total");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Error counting critical stock: " + e.getMessage(),
                    "DB Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
        return 0;
    }

    // Nº de categorías distintas
    public int getCategoriesCount() {
        String sql = "SELECT COUNT(DISTINCT Category) AS total FROM tblitems";

        try (Connection cn = conexion.Connect();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("total");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Error counting categories: " + e.getMessage(),
                    "DB Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
        return 0;
    }

    // Equipos en uso HOY (reservas aprobadas cuyo rango incluye hoy)
    public int getActiveEquipmentToday() {
        String sql = "SELECT COUNT(DISTINCT r.EquipmentID) AS total "
                + "FROM tblrequests r "
                + "WHERE r.StatusRequest = 'Approved' "
                + "AND CURRENT_DATE BETWEEN r.StartDate AND r.EndDate";

        try (Connection cn = conexion.Connect();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("total");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(
                    null,
                    "Error counting active equipment: " + e.getMessage(),
                    "DB Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
        return 0;
    }

    // Equipos disponibles = total - en uso hoy
    public int getAvailableEquipmentCount() {
        int total = getTotalEquipmentCount();
        int active = getActiveEquipmentToday();
        return total - active;
    }

    // ---------------------------------------------------------
    // Equipos añadidos recientemente (últimos 7 días)
    // ---------------------------------------------------------
    public DefaultTableModel getRecentlyAddedEquipment() {
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Name");
        model.addColumn("Category");
        model.addColumn("Date Added");
        model.addColumn("Days Ago");

        String sql = "SELECT Name, Category, DateAdded, "
                + "DATEDIFF(CURDATE(), DateAdded) AS DaysAgo "
                + "FROM tblitems "
                + "WHERE DateAdded >= DATE_SUB(CURDATE(), INTERVAL 7 DAY) "
                + "ORDER BY DateAdded DESC";

        try (Connection cn = conexion.Connect();
             PreparedStatement ps = cn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Object[] row = {
                        rs.getString("Name"),
                        rs.getString("Category"),
                        rs.getDate("DateAdded"),
                        rs.getInt("DaysAgo") + " days ago"
                };
                model.addRow(row);
            }

        } catch (Exception e) {
            System.err.println("Error getting recently added equipment: " + e.getMessage());
        }

        return model;
    }
}
