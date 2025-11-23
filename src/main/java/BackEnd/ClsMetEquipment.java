package BackEnd;

import Objects.ClsEquipment;

import Connection.ClsConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

public class ClsMetEquipment {
    ClsEquipment objEquipment;
    String AdmMssg;
    
    ClsConnection CN;
    PreparedStatement PS;
    ResultSet RS;
    
    public ClsMetEquipment(){
        objEquipment = new ClsEquipment();
        AdmMssg = "";
        CN = new ClsConnection();
    }
    
    // Create (Crud)
    public String AddEquipment(ClsEquipment objEquipmentT){
        this.objEquipment = objEquipmentT;
        String ConAddEquipment = "INSERT INTO tblitems(EquipmentID, Name, Category, Supplier, Location, Status, Quantity, MinStock, Availability) VALUES (?,?,?,?,?,?,?,?,?)";
        
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
            PS.setBoolean(8, true);

            int res = 0;
            res = PS.executeUpdate();
            if (res >= 1) {
                AdmMssg = "Equipment added succesfully.";
            } else {
                AdmMssg = "Equipment could not be added.";
            }
        } catch (Exception e) {
            AdmMssg = "Database error: " + e.getMessage();
        }
    
        return AdmMssg;
    }
    
    // Read (cRud)
    public String SearchEquipment(ClsEquipment objEquipmentT){
        
        
        
    return AdmMssg;
    }
    
    // Delete (cruD)
    public String DeleteEquipment(ClsEquipment objEquipmentT){
        
        
        
    return AdmMssg;
    }
    
    // Update (crUd)
    public String UpdateEquipment(ClsEquipment objEquipmentT){
        
        
        
    return AdmMssg;
    }
    
    //Listado de datos en la tabla
    public DefaultTableModel ListEquipment(){
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
            while(RS.next()){
                Object[] list ={
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
    
}
