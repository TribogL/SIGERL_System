
package Objects;


public class ClsRequest{
    
    private String StatusRequest;
    private String Date;

    public ClsRequest() {
        this.StatusRequest = "";
        this.Date = "";
    }

    public ClsRequest(String StatusRequest, String Date) {
        this.StatusRequest = StatusRequest;
        this.Date = Date;
    }

    public String geStatusRequest() {
        return StatusRequest;
    }

    public void setStatusRequest(String StatusRequest) {
        this.StatusRequest = StatusRequest;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String Date) {
        this.Date = Date;
    }
}
