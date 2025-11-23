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
    private boolean Availability;
    private String Description;

    public ClsEquipment() {
        this.EquipmentName = "";
        this.EquipmentCategory = "";
        this.EquipmentSupplier = "";
        this.EquipmentLocation = "";
        this.EquipmentStatus = "";
        this.EquipmentQuantity = 0;
        this.EquipmentMinStock = 0;
        this.Availability = false;
        this.Description = "";
    }

    public ClsEquipment(String EquipmentName, String EquipmentCategory, String EquipmentSupplier,
                    String EquipmentLocation, String EquipmentStatus, int EquipmentQuantity,
                    int EquipmentMinStock, String Description) {
    this.EquipmentName = EquipmentName;
    this.EquipmentCategory = EquipmentCategory;
    this.EquipmentSupplier = EquipmentSupplier;
    this.EquipmentLocation = EquipmentLocation;
    this.EquipmentStatus = EquipmentStatus;
    this.EquipmentQuantity = EquipmentQuantity;
    this.EquipmentMinStock = EquipmentMinStock;
    this.Description = Description;

    // TO DO: Availability a ser calculada
    this.Availability = false;
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

    public boolean Availability() { // Para indicar si esta reservado o no
        return Availability;
    }

    public void setAvailability(boolean Availability) {
        this.Availability = Availability;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

}
