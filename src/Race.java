import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Race  implements Serializable {

    private Date date;
    private ArrayList<Formula1Driver> racers = new ArrayList<>();

    public Race(Date date, ArrayList<Formula1Driver> drivers){
        this.date = date;
        this.racers = drivers;
    }

    public Date getDate() {
        return date;
    }

    public ArrayList<Formula1Driver> getDrivers(){return racers;}

    public String toString(){
        return "date" + date
                + "racers" + racers;
    }

}
