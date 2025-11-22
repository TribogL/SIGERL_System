
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
    
    public String AddEquipment(ClsEquipment objEquipmentT){
        this.objEquipment = objEquipmentT;
        String ConAddEquipment = "INSERT INTO tblitems(Item ID, Name, Category, Stock, Location, Status, Supplier, Availability) VALUES (?,?,?,?,?,?,?)";
        
        Connection Start = CN.Connect();
        
        try {
            PS = Start.prepareStatement(ConAddEquipment);
            PS.setString(1, objEquipment.getEquipmentName());
            PS.setString(2, objEquipment.getEquipmentCategory());
            PS.setInt(3, objEquipment.getEquipmentStock());
            PS.setString(4, objEquipment.getEquipmentLocation());
            PS.setString(5, objEquipment.getEquipmentStatus());
            PS.setString(6, objEquipment.getEquipmentSupplier());
            PS.setBoolean(7, false);

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
    
    public String SearchEquipment(ClsEquipment objEquipmentT){
        
        
        
    return AdmMssg;
    }
    
    public String DeleteEquipment(ClsEquipment objEquipmentT){
        
        
        
    return AdmMssg;
    }
    
    public String UpdateEquipment(ClsEquipment objEquipmentT){
        
        
        
    return AdmMssg;
    }
    
    public DefaultTableModel ListEquipment(){
        DefaultTableModel Model = new DefaultTableModel();
        
        
        
    return Model;
    }
    
}
