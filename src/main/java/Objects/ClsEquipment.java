package Objects;

public class ClsEquipment {

    private String EquipmentID;
    private String EquipmentName;
    private String EquipmentCategory;
    private int EquipmentStock;
    private String EquipmentLocation;
    private String EquipmentStatus;
    private String EquipmentSupplier;
    private boolean IsAvailable;
    // private String EquipmentDescription;    
    // private String NameCategory;
    // private String DescriptionCategory;
    
    public ClsEquipment() {
        this.EquipmentID = "";
        this.EquipmentName = "";
        this.EquipmentCategory = "";
        this.EquipmentStock = 0;        
        this.EquipmentLocation = "";
        this.EquipmentStatus = "";
        this.EquipmentSupplier = "";
        this.IsAvailable = IsAvailable;
        // this.Description = "";
        // this.IDTypeT = "";
        // this.NameCategory = "";
        // this.DescriptionCategory = "";
        
    }

    public ClsEquipment(String EquipmentID, String EquipmentName, String EquipmentCategory, int EquipmentStock, String EquipmentLocation, String EquipmentStatus, String EquipmentSupplier, boolean IsAvailable) {
        this.EquipmentID = EquipmentID;
        this.EquipmentName = EquipmentName;
        this.EquipmentCategory = EquipmentCategory;
        this.EquipmentStock = EquipmentStock;
        this.EquipmentLocation = EquipmentLocation;
        this.EquipmentSupplier = EquipmentSupplier;
        this.IsAvailable = IsAvailable;
    }

    public String getEquipmentID() {
        return EquipmentID;
    }

    public void setEquipmentID(String EquipmentID) {
        this.EquipmentID = EquipmentID;
    }

    public String getEquipmentName() {
        return EquipmentName;
    }

    public void setEquipmentName(String EquipmentName) {
        this.EquipmentName = EquipmentName;
    }

    public String getEquipmentCategory() {
        return EquipmentCategory;
    }

    public void setEquipmentCategory(String EquipmentCategory) {
        this.EquipmentCategory = EquipmentCategory;
    }

    public int getEquipmentStock() { 
        return EquipmentStock;
    }

    public void setEquipmentStock(int EquipmentStock) {
        this.EquipmentStock = EquipmentStock;
    }

    public String getEquipmentLocation() {
        return EquipmentLocation;
    }

    public void setEquipmentLocation(String EquipmentLocation) {
        this.EquipmentLocation = EquipmentLocation;
    }

    public String getEquipmentStatus(){
        return EquipmentStatus;
    }
    
    public void setEquipmentStatus(){
        this.EquipmentStatus = EquipmentStatus;
    }
    
    public String getEquipmentSupplier() {
        return EquipmentSupplier;
    }

    public void setEquipmentSupplier(String EquipmentSupplier) {
        this.EquipmentSupplier = EquipmentSupplier;
    }

    public boolean isIsAvailable() { // Para indicar si esta reservado o no
        return IsAvailable;
    }

    public void setIsAvailable(boolean IsAvailable) {
        this.IsAvailable = IsAvailable;
    }
}
