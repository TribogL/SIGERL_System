package Objects;

public class ClsTools extends ClsTypeTools{
    
    private String IDTool;
    private String NameTool;
    private String Description;
    private int StockTools;
    private boolean IsAviable;

    public ClsTools() {
        this.IDTool = "";
        this.NameTool = "";
        this.Description = "";
        this.StockTools = 0;
        this.IsAviable = IsAviable;
    }

    public ClsTools(String IDTypeT, String NameCategory, String DescriptionCategory, String IDTool, String NameTool, String Description, int StockTools, boolean IsAviable) {
        super(IDTypeT, NameCategory, DescriptionCategory);
        this.IDTool = IDTool;
        this.NameTool = NameTool;
        this.Description = Description;
        this.StockTools = StockTools;
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

    public boolean isIsAviable() {
        return IsAviable;
    }

    public void setIsAviable(boolean IsAviable) {
        this.IsAviable = IsAviable;
    }
    
    

}
