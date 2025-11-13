package Objects;

public class ClsUser extends ClsPeople {

    private String Rol;

    public ClsUser() {

        this.Rol = "";
    }

    public ClsUser(int IDDNI, String Name, String LastName, String Email, String Phone, String Password, String Project, boolean Permissions, int CantRequest, String Area, String Rol) {
        super(IDDNI, Name, LastName, Email, Phone, Password, Project, Permissions, CantRequest, Area);
        this.Rol = Rol;

    }

    public String getRol() {
        return Rol;
    }

    public void setRol(String Rol) {
        this.Rol = Rol;
    }

}
