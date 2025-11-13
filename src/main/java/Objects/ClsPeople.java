package Objects;

public abstract class ClsPeople {

    private int IDP;
    private String Name;
    private String LastName;
    private String Phone;
    private String Email;
    private String Password;
    private String Project;
    private boolean Permissions;
    private int CantRequest;
    private String Area;

    public ClsPeople() {
        this.IDP = 0;
        this.Name = "";
        this.LastName = "";
        this.Email = "";
        this.Phone = "";
        this.Password = "";
        this.Project = "";
        this.Permissions = Permissions;
        this.CantRequest = 0;
        this.Area = "";
    }

    public ClsPeople(int IDP, String Name, String LastName, String Email, String Phone, String Password, String Project, boolean Permissions, int CantRequest, String Area) {
        this.Name = Name;
        this.LastName = LastName;
        this.Email = Email;
        this.Phone = Phone;
        this.Password = Password;
        this.Project = Project;
        this.Permissions = Permissions;
        this.CantRequest = CantRequest;
        this.Area = Area;
    }

    public int getIDP() {
        return IDP;
    }

    public void setIDP(int IDP) {
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

    public boolean getPermissions() {
        return Permissions;
    }

    public void setPermissions(boolean Permissions) {
        this.Permissions = Permissions;
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

    public boolean isPermissions() {
        return Permissions;
    }

}
