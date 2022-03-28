import java.util.*;
import java.io.IOException;
import java.io.FileNotFoundException;

public class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Scanner i = new Scanner(System.in);
        String input;
        Formula1ChampionshipManager f1Menu = new Formula1ChampionshipManager();

        try{
            f1Menu.loadFile();
        }catch (FileNotFoundException e){
            System.out.println("File not found!");
        }

        do{
            f1Menu.Menu();
            input = i.nextLine().toLowerCase();
            switch (input){
                case("a"):
                    f1Menu.createDriver();
                    break;
                case("d"):
                    f1Menu.deleteDriver();
                    break;
                case("s"):
                    f1Menu.displayStats();
                    break;
                case("c"):
                    f1Menu.changeDriver();
                    break;
                case("p"):
                    f1Menu.printTable();
                    break;
                case("r"):
                    f1Menu.addRace();
                    break;
                case("i"):
                    f1Menu.saveInfo();
                    break;
                case("g"):
                    new f1GUI();
                break;
            }
        }while(!input.equals("x"));
    }
}