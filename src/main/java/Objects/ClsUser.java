package Objects;

/**
 *
 * @author MSI TIN
 */
public class ClsUser extends ClsPeople {

    private String Rol;
 

    public ClsUser(String DireCli, String DateCli) {
        this.Rol = Rol;
    }

    public ClsUser(String IDDNI, String Name, String LastName, String Email, String Phone, String Password, String Project, boolean Permitions, int CantRequest,String Area, String Rol) {
        super(IDDNI, Name, LastName, Email, Phone, Password, Project, Permitions, CantRequest, Area);
        this.Rol = Rol;

    }

    public String getRol() {
        return Rol;
    }

    public void setRol(String Rol) {
        this.Rol = Rol;
    }
    
}
