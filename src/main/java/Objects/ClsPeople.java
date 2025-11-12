
package Objects;

public abstract class ClsPeople {

    private String IDDNI;
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
        this.IDDNI = IDDNI;
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

    public ClsPeople(String IDDNI, String Name, String LastName, String Email, String Phone, String Password, String Project, boolean Permitions, int CantRequest, String Area) {
        this.IDDNI = IDDNI;
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

    public String getIDDNI() {
        return IDDNI;
    }

    public void setIDDNI(String IDDNI) {
        this.IDDNI = IDDNI;
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

    public String getArea() {
        return Project;
    }

    public void setArea(String Area) {
        this.Project = Area;
    }

    public boolean getPermitions() {
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
    
   
}
