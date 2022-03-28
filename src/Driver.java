import java.io.Serializable;

abstract public class Driver  implements Serializable  {
    private String driverName;
    private String driverLocation;
    private String driverTeam;


    //driver constructor
    public Driver(String driverName, String driverLocation, String driverTeam) {
        this.driverName = driverName;
        this.driverLocation = driverLocation;
        this.driverTeam = driverTeam;
    }

    //Setter Methods
    public void setDName(String name){driverName = name;}

    //Getter Methods
    public String getDName() {return driverName;}
    public String getDLocation() {return driverLocation;}
    public String getDTeam() {return driverTeam;}
}