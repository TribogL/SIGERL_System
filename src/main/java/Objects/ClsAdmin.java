
package Objects;


public class ClsAdmin extends ClsPeople{
    private String Range;

    public ClsAdmin() {
        this.Range = "";
    }

    public ClsAdmin(int IDDNI, String Name, String LastName, String Email, String Phone, String Password, String Project, boolean Permissions, int CantRequest, String Area, String Range) {
        super(IDDNI, Name, LastName, Email, Phone, Password, Project, Permissions, CantRequest, Area);
        
        this.Range = Range;
    }

    public String getRange() {
        return Range;
    }

    public void setRange(String Range) {
        this.Range = Range;
    }               
}

