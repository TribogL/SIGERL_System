package BackEnd;

import Objects.ClsEquipment;

import Connection.ClsConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.time.LocalDate; // Importacion de tiempo del sistema operativo del usuario
import java.time.temporal.ChronoUnit; // Importacion de clase ChronoUnit (constantes para calcular con tiempo)

public class ClsMetEquipment {

    ClsEquipment objEquipment;
    String AdmMssg;
    ClsConnection CN;
    PreparedStatement PS;
    ResultSet RS;

    public ClsMetEquipment() {
        objEquipment = new ClsEquipment();
        AdmMssg = "";
        CN = new ClsConnection();
    }

    // Create (Crud)
    public String AddEquipment(ClsEquipment objEquipmentT) {
        this.objEquipment = objEquipmentT;
        String ConAddEquipment = "INSERT INTO tblitems(Name, Category, Supplier, Location, Status, Quantity, MinStock, Description, DateAdded, Availability) VALUES (?,?,?,?,?,?,?,?,CURDATE(),?)";

        Connection Start = CN.Connect();

        try {
            PS = Start.prepareStatement(ConAddEquipment);
            PS.setString(1, objEquipment.getEquipmentName());
            PS.setString(2, objEquipment.getEquipmentCategory());
            PS.setString(3, objEquipment.getEquipmentSupplier());
            PS.setString(4, objEquipment.getEquipmentLocation());
            PS.setString(5, objEquipment.getEquipmentStatus());
            PS.setInt(6, objEquipment.getEquipmentQuantity());
            PS.setInt(7, objEquipment.getEquipmentMinStock());
            PS.setString(8, objEquipment.getDescription());
            PS.setBoolean(9, objEquipment.getEquipmentQuantity() > 0);

            int res = PS.executeUpdate();
            if (res >= 1) {
                AdmMssg = "Equipment added successfully.";
            } else {
                AdmMssg = "Equipment could not be added.";
            }
        } catch (Exception e) {
            AdmMssg = "Database error: " + e.getMessage();
        }

        return AdmMssg;
    }

    // Update (crUd)
    public String UpdateEquipment(ClsEquipment objEquipmentT) {
        this.objEquipment = objEquipmentT;
        String sql = "UPDATE tblitems SET Name=?, Category=?, Supplier=?, Location=?, Status=?, Quantity=?, MinStock=?, Description=?, Availability=? WHERE ItemID=?";

        try (Connection Start = CN.Connect(); PreparedStatement ps = Start.prepareStatement(sql)) {

            ps.setString(1, objEquipment.getEquipmentName());
            ps.setString(2, objEquipment.getEquipmentCategory());
            ps.setString(3, objEquipment.getEquipmentSupplier());
            ps.setString(4, objEquipment.getEquipmentLocation());
            ps.setString(5, objEquipment.getEquipmentStatus());
            ps.setInt(6, objEquipment.getEquipmentQuantity());
            ps.setInt(7, objEquipment.getEquipmentMinStock());
            ps.setString(8, objEquipment.getDescription());
            ps.setBoolean(9, objEquipment.getEquipmentQuantity() > 0);
            ps.setInt(10, objEquipment.getEquipmentID());

            int res = ps.executeUpdate();
            if (res >= 1) {
                AdmMssg = "Equipment updated successfully.";
            } else {
                AdmMssg = "Equipment could not be updated.";
            }
        } catch (Exception e) {
            AdmMssg = "Database error: " + e.getMessage();
        }

        return AdmMssg;
    }

    // Delete (cruD)
    public String DeleteEquipment(int equipmentId) {
        String DEL = "DELETE FROM tblitems WHERE ItemID=?";

        try (Connection Start = CN.Connect(); PreparedStatement ps = Start.prepareStatement(DEL)) {

            ps.setInt(1, equipmentId);

            int res = ps.executeUpdate();
            if (res >= 1) {
                AdmMssg = "Equipment deleted successfully.";
            } else {
                AdmMssg = "Equipment could not be deleted.";
            }
        } catch (Exception e) {
            AdmMssg = "Database error: " + e.getMessage();
        }

        return AdmMssg;
    }

    // Read (cRud)
    public ClsEquipment SearchEquipment(int equipmentId) {
        String SRC = "SELECT * FROM tblitems WHERE ItemID=?";

        try (Connection Start = CN.Connect(); PreparedStatement ps = Start.prepareStatement(SRC)) {

            ps.setInt(1, equipmentId);
            ResultSet rs = ps.executeQuery();

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
                equipment.setEquipmentID(rs.getInt("ItemID"));
                return equipment;
            }

        } catch (Exception e) {
            System.err.println("Error searching equipment: " + e.getMessage());
        }

        return null;
    }

    //Listado de datos en la tabla
    public DefaultTableModel ListEquipment() {
        // Declaracion de la variable para llenado de datos
        DefaultTableModel Model = new DefaultTableModel();

        Model.addColumn("EquipmentID");
        Model.addColumn("Name");
        Model.addColumn("Category");
        Model.addColumn("Supplier");
        Model.addColumn("Location");
        Model.addColumn("Status");
        Model.addColumn("Quantity");
        Model.addColumn("MinimumStock");
        Model.addColumn("Availability");

        // Carga de datos a la base de datos al modelo
        try {
            String ADmMssg = "";
            int res = 0;

            Connection Start = CN.Connect();
            String ListEquipment = "SELECT * FROM tblitems";

            PS = Start.prepareStatement(ListEquipment);
            RS = PS.executeQuery();

            ClsEquipment objEquipment = new ClsEquipment();
            while (RS.next()) {
                Object[] list = {
                    RS.getInt(1), //ID
                    RS.getString(2), //Name
                    RS.getString(3), //Category
                    RS.getString(4), //Supplier
                    RS.getString(5), //Location
                    RS.getString(6), //Status
                    RS.getInt(7), //Quantity
                    RS.getInt(8), //Minimum Stock
                    RS.getBoolean(9) // Availability
                };
                // Carga de datos
                Model.addRow(list);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Database error: " + e.getMessage(), "DB Error", JOptionPane.ERROR_MESSAGE);
        }

        return Model;
    }

    // Conteo total de equipos
    public int getTotalEquipmentCount() {
        String TOT = "SELECT COUNT(*) as total FROM tblitems";

        try (Connection Start = CN.Connect(); PreparedStatement ps = Start.prepareStatement(TOT); ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("total");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error counting total equipment: " + e.getMessage(), "DB Error", JOptionPane.ERROR_MESSAGE);
        }

        return 0;
    }

    // Logica para contar equipos en low stock
    public int getLowStockCount() {
        String LOW = "SELECT COUNT(*) as total FROM tblitems WHERE Quantity <= MinStock AND Quantity > 0";

        try (Connection Start = CN.Connect(); PreparedStatement ps = Start.prepareStatement(LOW); ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("total");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error counting low stock: " + e.getMessage(), "DB Error", JOptionPane.ERROR_MESSAGE);
        }

        return 0;
    }

    // Logica para contar equipos stock critico
    public int getCriticalStockCount() {
        String CRT = "SELECT COUNT(*) as total FROM tblitems WHERE Quantity = 0";

        try (Connection Start = CN.Connect(); PreparedStatement ps = Start.prepareStatement(CRT); ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("total");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error counting critical stock: " + e.getMessage(), "DB Error", JOptionPane.ERROR_MESSAGE);
        }

        return 0;
    }

    // Conteo de categorias
    public int getCategoriesCount() {
        String CAT = "SELECT COUNT(DISTINCT Category) as total FROM tblitems";

        try (Connection Start = CN.Connect(); PreparedStatement ps = Start.prepareStatement(CAT); ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("total");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error counting categories: " + e.getMessage(), "DB Error", JOptionPane.ERROR_MESSAGE);
        }

        return 0;
    }

    // Conteo de equipos en uso hoy
    public int getActiveEquipmentToday() {
        // DISTINCT es para obtener un valor unico. Los valores se identifican on r.Variable
        String ACT = "SELECT COUNT(DISTINCT r.EquipmentID) as total FROM tblRequests r WHERE r.StatusRequest = 'Approved' AND CURDATE() BETWEEN r.StartDate AND r.EndDate";

        try (Connection Start = CN.Connect(); PreparedStatement ps = Start.prepareStatement(ACT); ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                return rs.getInt("total");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error counting active equipment: " + e.getMessage(), "DB Error", JOptionPane.ERROR_MESSAGE);
        }

        return 0;
    }

    // Conteo de equipos que no estan en uso hoy (disponibles)
    public int getAvailableEquipmentCount() {
        int total = getTotalEquipmentCount();
        int active = getActiveEquipmentToday();
        return total - active;
    }

    // Conteo de equipo nuevo (agregado en los ultimos 7 dias)
    public DefaultTableModel getRecentlyAddedEquipment() {
        DefaultTableModel Model = new DefaultTableModel();
        Model.addColumn("Name");
        Model.addColumn("Category");
        Model.addColumn("Date Added");
        Model.addColumn("Days Ago");
        
        //CURDATE es para saber la fecha actual de la base de datos (current date)
        String NEW = "SELECT Name, Category, DateAdded, DATEDIFF(CURDATE(), DateAdded) as DaysAgo FROM tblitems WHERE DateAdded >= DATE_SUB(CURDATE(), INTERVAL 7 DAY) ORDER BY DateAdded DESC";

        try (Connection Start = CN.Connect(); PreparedStatement ps = Start.prepareStatement(NEW); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Object[] row = {
                    rs.getString("Name"),
                    rs.getString("Category"),
                    rs.getDate("DateAdded"),
                    rs.getInt("DaysAgo") + " days ago"
                };
                Model.addRow(row);
            }

        } catch (Exception e) {
            System.err.println("Error getting recently added equipment: " + e.getMessage());
        }

        return Model;
    }

}
