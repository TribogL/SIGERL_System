package Objects;

public class ClsUser extends ClsPeople {

    private String Role;

    public ClsUser() {

        this.Role = "";
    }

    public ClsUser(int IDDNI, String Name, String LastName, String Email, String Phone, String Password, String Project, boolean Permissions, int CantRequest, String Area, String Role) {
        super(IDDNI, Name, LastName, Email, Phone, Password, Project, Permissions, CantRequest, Area);
        this.Role = Role;

    }

    public String getRole() {
        return Role;
    }

    public void setRole(String Role) {
        this.Role = Role;
    }

}
