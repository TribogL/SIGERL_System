package Objects;

public class ClsEquipment {

    private int EquipmentID;
    private String EquipmentName;
    private String EquipmentCategory;
    private String EquipmentSupplier;
    private String EquipmentLocation;
    private String EquipmentStatus;
    private int EquipmentQuantity;
    private int EquipmentMinStock;
    private boolean IsAvailable;

    public ClsEquipment() {
        this.EquipmentName = "";
        this.EquipmentCategory = "";
        this.EquipmentSupplier = "";
        this.EquipmentLocation = "";
        this.EquipmentStatus = "";
        this.EquipmentQuantity = 0;
        this.EquipmentMinStock = 0;
        this.IsAvailable = IsAvailable;
    }

    public ClsEquipment(int EquipmentID, String EquipmentName, String EquipmentCategory, int EquipmentStock, String EquipmentLocation, String EquipmentStatus, String EquipmentSupplier, boolean IsAvailable) {
        this.EquipmentID = EquipmentID;
        this.EquipmentName = EquipmentName;
        this.EquipmentCategory = EquipmentCategory;
        this.EquipmentSupplier = EquipmentSupplier;
        this.EquipmentMinStock = EquipmentMinStock;
        this.EquipmentLocation = EquipmentLocation;
        this.EquipmentStatus = EquipmentStatus;
        this.EquipmentQuantity = EquipmentQuantity;
        this.EquipmentMinStock = EquipmentMinStock;
        this.IsAvailable = IsAvailable;
    }

    public int getEquipmentID() {
        return EquipmentID;
    }

    public void setEquipmentID(int EquipmentID) {
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

    public String getEquipmentSupplier() {
        return EquipmentSupplier;
    }

    public void setEquipmentSupplier(String EquipmentSupplier) {
        this.EquipmentSupplier = EquipmentSupplier;
    }

    public String getEquipmentLocation() {
        return EquipmentLocation;
    }

    public void setEquipmentLocation(String EquipmentLocation) {
        this.EquipmentLocation = EquipmentLocation;
    }

    public String getEquipmentStatus() {
        return EquipmentStatus;
    }

    public void setEquipmentStatus() {
        this.EquipmentStatus = EquipmentStatus;
    }

    public int getEquipmentQuantity() {
        return EquipmentQuantity;
    }

    public void setEquipmentQuantity(int EquipmentQuantity) {
        this.EquipmentQuantity = EquipmentQuantity;
    }

    public int getEquipmentMinStock() {
        return EquipmentMinStock;
    }

    public void setEquipmentMinStock(int EquipmentMinStock) {
        this.EquipmentMinStock = EquipmentMinStock;
    }

    public boolean isIsAvailable() { // Para indicar si esta reservado o no
        return IsAvailable;
    }

    public void setIsAvailable(boolean IsAvailable) {
        this.IsAvailable = IsAvailable;
    }
}
