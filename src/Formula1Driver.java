public class Formula1Driver extends Driver {
    private int[] positions;
    private int points;
    private int noOfRaces;
    private final static int[] pointScheme = {25, 18, 15, 12, 10, 8, 6, 4, 2, 1};


    public Formula1Driver(String driverName, String driverLocation, String driverTeam, int noOfRaces, int[] positions) {
        super(driverName, driverLocation, driverTeam);
        this.noOfRaces = noOfRaces;
        this.positions = positions;
        TotalPoints();
    }

    //set methods
    public void addPositions(int pos) {
        this.positions[pos] += 1;
    }

    public void setRaces(int noOfRaces) {
        this.noOfRaces = noOfRaces;
    }

    //get methods
    public int getPositions(int index) {
        return positions[index];
    }

    public int getPoints() {
        return points;
    }

    public int getRaces() {
        return noOfRaces;
    }


    public int TotalPoints() {
        points = 0;
        for (int i = 0; i < positions.length; i++) {
            points = positions[i] * pointScheme[i] + points;
        }
        return points;
    }
}