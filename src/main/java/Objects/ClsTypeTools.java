
package Objects;

public class ClsTypeTools {
    
    private String IDTypeT;
    private String NameCategory;
    private String DescriptionCategory;
    
    public ClsTypeTools(){
        this.IDTypeT = "";
        this.NameCategory = "";
        this.DescriptionCategory = "";
    }
    
    public ClsTypeTools(String IDTypeT, String NameCategory, String DescriptionCategory) {
        this.IDTypeT = IDTypeT;
        this.NameCategory = NameCategory;
        this.DescriptionCategory = DescriptionCategory;
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
    
}
