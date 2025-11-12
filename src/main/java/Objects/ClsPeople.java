package Objects;

public abstract class ClsPeople {

    private String IDP;
    private String Name;
    private String LastName;
    private String Phone;
    private String Email;
    private String Password;
    private String Project;
    private boolean Permitions;
    private int CantRequest;
    private String Area;

    public ClsPeople() {
        this.IDP = "";
        this.Name = "";
        this.LastName = "";
        this.Email = "";
        this.Phone = "";
        this.Password = "";
        this.Project = "";
        this.Permitions = Permitions;
        this.CantRequest = 0;
        this.Area = "";
    }

    public ClsPeople(String IDP, String Name, String LastName, String Email, String Phone, String Password, String Project, boolean Permitions, int CantRequest, String Area) {
        this.IDP = IDP;
        this.Name = Name;
        this.LastName = LastName;
        this.Email = Email;
        this.Phone = Phone;
        this.Password = Password;
        this.Project = Project;
        this.Permitions = Permitions;
        this.CantRequest = CantRequest;
        this.Area = Area;
    }

    public String getIDP() {
        return IDP;
    }

    public void setIDP(String IDP) {
        this.IDP = IDP;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String LastName) {
        this.LastName = LastName;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String Phone) {
        this.Phone = Phone;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String Password) {
        this.Password = Password;
    }

    public String getProject() {
        return Project;
    }

    public void setProject(String Project) {
        this.Project = Project;
    }

    public boolean isPermitions() {
        return Permitions;
    }

    public void setPermitions(boolean Permitions) {
        this.Permitions = Permitions;
    }

    public int getCantRequest() {
        return CantRequest;
    }

    public void setCantRequest(int CantRequest) {
        this.CantRequest = CantRequest;
    }

    public String getArea() {
        return Area;
    }

    public void setArea(String Area) {
        this.Area = Area;
    }

    
}
