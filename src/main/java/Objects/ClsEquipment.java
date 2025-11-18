package Objects;

public class ClsEquipment {

    private String IDTool;
    private String NameTool;
    private String Description;
    private int StockTools;
    private String IDTypeT;
    private String NameCategory;
    private String DescriptionCategory;
    private boolean IsAviable;

    public ClsEquipment() {
        this.IDTool = "";
        this.NameTool = "";
        this.Description = "";
        this.StockTools = 0;
        this.IDTypeT = "";
        this.NameCategory = "";
        this.DescriptionCategory = "";
        this.IsAviable = IsAviable;
    }

    public ClsEquipment(String IDTool, String NameTool, String Description, int StockTools, String IDTypeT, String NameCategory, String DescriptionCategory, boolean IsAviable) {
        this.IDTool = IDTool;
        this.NameTool = NameTool;
        this.Description = Description;
        this.StockTools = StockTools;
        this.IDTypeT = IDTypeT;
        this.NameCategory = NameCategory;
        this.DescriptionCategory = DescriptionCategory;
        this.IsAviable = IsAviable;
    }

    public String getIDTool() {
        return IDTool;
    }

    public void setIDTool(String IDTool) {
        this.IDTool = IDTool;
    }

    public String getNameTool() {
        return NameTool;
    }

    public void setNameTool(String NameTool) {
        this.NameTool = NameTool;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String Description) {
        this.Description = Description;
    }

    public int getStockTools() {
        return StockTools;
    }

    public void setStockTools(int StockTools) {
        this.StockTools = StockTools;
    }

    public String getIDTypeT() {
        return IDTypeT;
    }

    public void setIDTypeT(String IDTypeT) {
        this.IDTypeT = IDTypeT;
    }

    public String getNameCategory() {
        return NameCategory;
    }

    public void setNameCategory(String NameCategory) {
        this.NameCategory = NameCategory;
    }

    public String getDescriptionCategory() {
        return DescriptionCategory;
    }

    public void setDescriptionCategory(String DescriptionCategory) {
        this.DescriptionCategory = DescriptionCategory;
    }

    public boolean isIsAviable() {
        return IsAviable;
    }

    public void setIsAviable(boolean IsAviable) {
        this.IsAviable = IsAviable;
    }
}
