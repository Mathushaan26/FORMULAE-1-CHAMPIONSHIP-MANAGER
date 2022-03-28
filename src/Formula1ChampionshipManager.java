import java.util.*;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;


public class Formula1ChampionshipManager implements ChampionshipManager {

    protected static ArrayList<Formula1Driver> formula1_drivers = new ArrayList<>();
    protected static ArrayList<Formula1Driver> sortPoints;
    protected static ArrayList<Race> f1Races = new ArrayList<>();
    private Scanner prompt = new Scanner(System.in);
    private String input;

    @Override
    public void Menu() {
        System.out.println("|-------------------------*Formulae 1 Championship*---------------------|");
        System.out.println();
        System.out.println("|---------------------------------*MENU*--------------------------------|");
        System.out.println("|        A : Creates a new driver                                       |");
        System.out.println("|        D : Delete a driver  in an existing team                       |");
        System.out.println("|        S : Shows statistics for the selected driver                   |");
        System.out.println("|        C : Change the driver to a different team                      |");
        System.out.println("|        P : Prints F1 driver table                                     |");
        System.out.println("|        R : Add the completed race's statistics                        |");
        System.out.println("|        I : Information is saved to file                               |");
        System.out.println("|        G : Graphical User Interface for Formula 1 championship        |");
        System.out.println("|        x : Exit the Program                                           |");
        System.out.println("|-----------------------------------------------------------------------|");
        System.out.println("Enter a letter from the above menu");
    }
    //------------------------------------------CREAT A NEW DRIVER---------------------------------------
    @Override
    public void createDriver() {
        System.out.println("Enter the driver's name");
        String dName = prompt.next();
        System.out.println("Enter the driver's location");
        String dLocation = prompt.next();
        System.out.println("Enter the driver's team");
        String dTeam = prompt.next();

        if (UniqueTeam(dTeam)) {
            int positions[] = new int[10];
            int races = 0;
            while (true) {
                System.out.println("Select a position from 1 - 10");
                int position = prompt.nextInt();
                System.out.println("Enter the number of times that the position was won");
                int won = prompt.nextInt();
                positions[position - 1] = won;
                System.out.println("Do you wish to continue YES/NO");
                input = prompt.next();
                if (input.equalsIgnoreCase("n") || input.equalsIgnoreCase("no")) {
                    break;
                } else {
                    System.out.println();
                }
            }
            for (int i = 0; i < positions.length; i++) {
                races = races + positions[i];
            }

            //---------------DETAILS OF THE ADDED DRIVER------------
            System.out.println("||--------------------DRIVER'S INFO------------------------||");
            System.out.println("    Driver's Name: " + dName);
            System.out.println("    Driver's Location: " + dLocation);
            System.out.println("    Driver's Team: " + dTeam);
            System.out.println("||---------------------------------------------------------||");

            System.out.println("Do you wish to add the driver? YES/NO");
            input = prompt.next();
            if (input.equalsIgnoreCase("yes") || input.equalsIgnoreCase("y")) {
                Formula1Driver added_driver = new Formula1Driver(dName, dLocation, dTeam, races, positions);
                formula1_drivers.add(added_driver);
                System.out.println("Driver added successfully!");
            }
            else {
                System.out.println();
                System.out.println("Driver is not added!");
            }
        }
        else{
            System.out.println("Team already exists!");
        }
    }
    //checks for the unique team
    public static Boolean UniqueTeam (String dTeam){
        for (Formula1Driver f1Driver : formula1_drivers) {
            if (f1Driver.getDTeam().equalsIgnoreCase(dTeam)) {
                return false;
            }
        }
        return true;
    }
    //-------------------------------------------DELETE A DRIVER------------------------------------------
    @Override
    public void deleteDriver() {
        showDriverList();
        System.out.println("Enter the driver name that need to be deleted");
        String delete = prompt.next();
        boolean isThere = true;
        for(Formula1Driver f1Driver : formula1_drivers){
            if (f1Driver.getDName().equalsIgnoreCase(delete)){
                    formula1_drivers.remove(f1Driver);
                    isThere = false;
                    System.out.println();
                    System.out.println(f1Driver.getDName() + " was deleted successfully!");
                    break;
                }
            }
        if (isThere){
            System.out.println();
            System.out.println(delete + " is not available in F1 Championship");
            System.out.println();
            System.out.println("RETRY");
            System.out.println();
            deleteDriver();
        }
    }
    //--------------------------------------------DISPLAY DRIVER STATS----------------------------------------------------
    @Override
    public void displayStats() {
        sortDrivers();
        System.out.println("Select driver to get the statistics of ");
        showDriverList();
        input = prompt.next();

        boolean isThere = false;
        for (Formula1Driver f1Driver : formula1_drivers) {
            if (f1Driver.getDName().equalsIgnoreCase(input)) {
                System.out.println();
                System.out.println("|----------DRIVER STATS-----------|");
                System.out.println("    Driver's Name           : " + f1Driver.getDName());
                System.out.println("    Driver's Location       : " + f1Driver.getDLocation());
                System.out.println("    Driver's Team           : " + f1Driver.getDTeam());
                System.out.println("    Driver's Points         : " + f1Driver.getPoints());
                System.out.println("    No Of Races             : " + f1Driver.getRaces());
                System.out.println("    Number of Firsts Places  : " + f1Driver.getPositions(0));
                System.out.println("    Number of Second Places : " + f1Driver.getPositions(1));
                System.out.println("    Number of Third Places  : " + f1Driver.getPositions(2));
                System.out.println("|-----------------------------------------|");
                isThere = true;
            }
        }
        if (!isThere){
            System.out.println("Invalid Driver Name!");
        }
    }
    //--------------------------------------------CHANGE THE DRIVER----------------------------------------------------
    @Override
    public void changeDriver(){
        System.out.println("Choose a team to change the Driver");
        showDriverList();
        input = prompt.next();
        for(Formula1Driver f1Driver : formula1_drivers) {
            if (f1Driver.getDName().equalsIgnoreCase(input) ||
                    f1Driver.getDTeam().equalsIgnoreCase(input)) {
                System.out.println("Enter a new driver name: ");
                input = prompt.next();
                f1Driver.setDName(input);
                System.out.println("New details of the driver has been updated to TEAM :  " + f1Driver.getDTeam());

            }
        }
    }
    //--------------------------------------------DISPLAY TABLE---------------------------------------------------------
    @Override
    public void printTable () {
        System.out.println("|-----------------------------------------------------------------------------------------F1 CHAMPIONSHIP POINTS TABLE-----------------------------------------------------------------------------|");
        System.out.println();
        System.out.printf("%10s %10s %10s %10s %10s %10s %10s %10s %10s %10s %10s %10s %10s %10s %10s %10s %10s",
                "|","NAME" , "|", "TEAM", "|" , "LOCATION" ,"|" , "RACES", "|" ,"POINTS" , "|" , "1st" , "|" , "2nd" , "|" , "3rd" ,"|");
        System.out.println();
        System.out.println("|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|");
        for(Formula1Driver f1Driver : sortPoints) {
            System.out.format("%10s %10s %10s %10s %10s %10s %10s %10s %10s %10s %10s %10s %10s %10s %10s %10s %10s", "|",
                    f1Driver.getDName(), "|", f1Driver.getDTeam(), "|", f1Driver.getDLocation(), "|", f1Driver.getRaces(), "|", f1Driver.getPoints(), "|",
                    f1Driver.getPositions(0), "|", f1Driver.getPositions(1), "|", f1Driver.getPositions(2), "|" + "\n");
        }
        System.out.println();
        System.out.println("|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|");
    }

    //------------------------------------------------ADD RACE----------------------------------------------------------
    @Override
    public void addRace() {
        System.out.println("Enter date of race in DD-MM-YYYY format: ");
        String dateInput = prompt.next();
        Date date;
        try{
            date = new SimpleDateFormat("dd-MM-yyyy").parse(dateInput);
        }catch (ParseException ex){
            System.out.println("Invalid Format!");
            System.out.println("RETRY");
            return;
        }
        int x = 0;
        ArrayList<Formula1Driver> f1DriverCopy = new ArrayList<>(formula1_drivers);
        ArrayList<Formula1Driver> racesPos = new ArrayList<>();
        while(x < formula1_drivers.size()){
            for(Formula1Driver f1Driver : f1DriverCopy){
                System.out.println();
                System.out.println("Here are the driver names:");
                System.out.println("  "+f1Driver.getDName());
                System.out.println();
            }
            System.out.println("Enter the driver who won position: " + (x+1));
            input = prompt.next();
            Formula1Driver selectedDriver = null;
            for(Formula1Driver f1Driver : formula1_drivers){
                if(f1Driver.getDName().equalsIgnoreCase(input) && f1DriverCopy.contains(f1Driver)){
                    selectedDriver = formula1_drivers.get(formula1_drivers.indexOf(f1Driver));
                }
            }
            if(selectedDriver == null || !formula1_drivers.contains(selectedDriver)){
                System.out.println("Invalid Input!");
            }else{
                selectedDriver.setRaces(selectedDriver.getRaces()+1);
                selectedDriver.addPositions(x);
                selectedDriver.TotalPoints();
                racesPos.add(selectedDriver);
                f1DriverCopy.removeIf(Integer -> Integer.getDName().equalsIgnoreCase(input));
                x++;

            }
        }
        f1Races.add(new Race(date,racesPos));
    }
    //----------------------------------------------Save Information to the file----------------------------------------
    public void saveInfo() throws IOException{

        FileOutputStream fOut = new FileOutputStream("F1Teams.txt");
        ObjectOutputStream oOut = new ObjectOutputStream(fOut);

        for(Formula1Driver f1Driver : formula1_drivers){
            oOut.writeObject(f1Driver);
        }

        FileOutputStream fOutR = new FileOutputStream("Race.txt");
        ObjectOutputStream oOutR = new ObjectOutputStream(fOutR);

        for(Race race : f1Races){
            oOutR.writeObject(race);
        }
        oOut.flush();
        fOut.close();
        oOut.close();
        oOutR.flush();
        fOutR.close();
        oOutR.close();

        System.out.println("The data has been saved!");
    }

    //------------------------------------------------LOAD THE SAVED FILE-----------------------------------------------
    @Override
    public void loadFile() throws  IOException,ClassNotFoundException{
        FileInputStream fIn = new FileInputStream("F1Teams.txt");
        ObjectInputStream oIn = new ObjectInputStream(fIn);

        for(;;){
            try{
                Formula1Driver f1Driver = (Formula1Driver) oIn.readObject();
                formula1_drivers.add(f1Driver);
            }catch(EOFException e ){
                break;
            }
        }
        fIn.close();
        oIn.close();
        FileInputStream fInR = new FileInputStream("Race.txt");
        ObjectInputStream oInR = new ObjectInputStream(fInR);

        for(;;){
            try{
                Race race;
                race = (Race) oInR.readObject();
                f1Races.add(race);
            }catch(EOFException e ){
                break;
            }
        }
        fInR.close();
        oInR.close();
        System.out.println();
        System.out.println(" Data Loaded Successfully");
        sortDrivers();
    }


//shows list of all drivers

    public void showDriverList() {
        for (int i = 0; i < formula1_drivers.size(); i++) {
            System.out.println(i + 1 + ". " + formula1_drivers.get(i).getDName() + " "
                    + formula1_drivers.get(i).getDTeam());
        }
    }

//sort drivers
    @Override
    public void sortDrivers(){
        sortPoints = new  ArrayList<>(formula1_drivers);

        Collections.sort(sortPoints, (o1, o2) -> {
                    if (o1.getPoints() > o2.getPoints()) {
                        return -1;
                    } else if (o1.getPoints() < o2.getPoints()) {
                        return 1;
                    } else {
                        if (o1.getPositions(0) > o2.getPositions(0)) {
                            return -1;
                        } else if (o1.getPositions(0) < o2.getPositions(0)) {
                            return 1;
                        } else {
                            return 0;
                        }
                    }
                }
        );
    }
}



