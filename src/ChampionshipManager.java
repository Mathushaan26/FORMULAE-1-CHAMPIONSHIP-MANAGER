import java.io.IOException;

public interface ChampionshipManager {
    void Menu();
    void createDriver();
    void deleteDriver();
    void displayStats();
    void changeDriver();
    void printTable();
    void addRace();
    void saveInfo() throws IOException;
    void loadFile() throws IOException, ClassNotFoundException;
    void sortDrivers();
}
