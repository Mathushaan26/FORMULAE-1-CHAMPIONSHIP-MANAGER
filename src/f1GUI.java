import java.util.*;
import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;


public class f1GUI extends  Formula1ChampionshipManager implements ActionListener {

    private Object[][] data;
    private String[] columnHeader ={"Name","Team","Location","Points","1st","2nd","3rd"} ;
    private String[] driverColumn = {"Date ", "Position"};
    private String[] raceColumn = {"Date","1st","2nd","3rd"};

    private DefaultTableModel tableModel;
    private JTable jtable;
    private DefaultTableModel driverModel;
    private JTable driverTable;


    private JFrame frame = new JFrame();
    private JFrame j;
    private JFrame warning;
    private  JFrame driver;


    private JPanel panel = new JPanel();
    private JPanel panel2 = new JPanel();
    private JPanel panel3 = new JPanel();


    private JButton randomRace = new JButton("Generate Random");
    private JButton calculateRace = new JButton("Calculate Race");
    private JButton printRace = new JButton("Print Race");
    private JButton searchDriver = new JButton("Search");
    private JButton clear = new JButton("Clear");


    private JRadioButton ascending = new JRadioButton("Ascending");
    private JRadioButton descending = new JRadioButton("Descending");
    private JCheckBox checkBox = new JCheckBox("Sort: 1st Position");

    private final JTextField text;
    private JLabel driverLabel = new JLabel();
    private JLabel titleLable;
    private  String searchInput;


    public f1GUI(){
        frame.setLayout(new GridLayout(3,1));
        frame.setTitle("Formula 1 Championship");

        //Table
        tableModel = new DefaultTableModel(convertData(sortPoints), columnHeader);

        jtable = new JTable(tableModel);
        jtable.getTableHeader().setBackground(Color.CYAN);
        jtable.setBounds(0,0,800,400);
        titleLable = new JLabel("|---------------- Formula 1 Statistics----------------|");
        panel.add(titleLable);
        panel.add(new JScrollPane(jtable));

        // Buttons
        ButtonGroup group = new ButtonGroup();
        group.add(ascending);
        group.add(descending);

        // by default points are sorted in descending
        descending.setSelected(true);
        ascending.setBounds(20,700,150,50);
        ascending.addActionListener(this);
        panel2.add(ascending);
        panel2.setBackground( Color.darkGray);
        descending.setBounds(200,700,150,50);
        descending.addActionListener(this);
        panel2.add(descending);

        checkBox.addActionListener(this);
        panel2.add(checkBox);

        randomRace.setBounds(20,800,150,50);
        randomRace.addActionListener(this);
        panel2.add(randomRace);

        calculateRace.setBounds(200,800,150,50);
        calculateRace.addActionListener(this);
        panel2.add(calculateRace);

        printRace.setBounds(20,900,150,50);
        printRace.addActionListener(this);
        panel2.add(printRace);

        clear.addActionListener(this);
        panel2.add(clear);

        text = new JTextField(30);
        searchDriver.addActionListener(this);
        panel3.add(text);
        panel3.add(searchDriver);
        panel3.add(driverLabel);
        panel3.setBackground( Color.darkGray);

        // JFrame setup
        frame.add(panel);
        frame.add(panel2);
        frame.add(panel3);

        frame.setSize(500,600);
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }


    //gui methods
    public  Object[][] convertData(ArrayList<Formula1Driver> drivers){
        Object[][] data = new Object[drivers.size()][7];
        for(int i =0 ;i< drivers.size();i++){
            data[i][0] = drivers.get(i).getDName();
            data[i][1] = drivers.get(i).getDTeam();
            data[i][2] = drivers.get(i).getDLocation();
            data[i][3] = drivers.get(i).getPoints();
            data[i][4] = drivers.get(i).getPositions(0);
            data[i][5] = drivers.get(i).getPositions(1);
            data[i][6] = drivers.get(i).getPositions(2);

        }
        return  data;
    }

    public void sort1stPos(){
        ArrayList<Formula1Driver> posSort = new ArrayList<>(formula1_drivers);
        Collections.sort(posSort, new Comparator<>() {
            @Override
            public int compare(Formula1Driver o1, Formula1Driver o2) {
                if (o1.getPositions(0) > o2.getPositions(0)) {
                    return -1;
                } else if (o1.getPositions(0) < o2.getPositions(0)) {
                    return 1;
                } else {
                    return 0;
                }

            }
        });
        jtable.setModel(new DefaultTableModel(convertData(posSort), columnHeader));
    }


    public void genRandomRace(){
        j = new JFrame("Enter Date");
        String name = JOptionPane.showInputDialog(j,"Enter the date of the race (dd-MM-YYYY): ");
        Date date;
        try{
            date = new SimpleDateFormat("dd-MM-yyyy").parse(name);
        } catch (ParseException ex){
            warning = new JFrame();
            JOptionPane.showMessageDialog(warning,"Invalid Date format","Warning",JOptionPane.WARNING_MESSAGE);
            return;
        }
        ArrayList<Formula1Driver> random = new ArrayList<>(formula1_drivers);
        Collections.shuffle(random);
        int i = 0;
        for(Formula1Driver f1 : random){
            for(Formula1Driver f : formula1_drivers){
                if(f.getDName().equals(f1.getDName())){
                    f.setRaces(f.getRaces() + 1 );
                    f.addPositions(i);
                    f.TotalPoints();
                    sortDrivers();
                    i++;
                }
            }
        }
        f1Races.add(new Race(date,random));
        System.out.println("Race added successfully");
    }

    public void genProbabilityRace(){
        j = new JFrame("Enter Date");
        String name = JOptionPane.showInputDialog(j,"Enter the date of the Race");
        Date date;
        try{
            date = new SimpleDateFormat("dd-MM-yyyy").parse(name);
        } catch (ParseException ex){
            warning = new JFrame();
            JOptionPane.showMessageDialog(warning,"Invalid Date format","Warning",JOptionPane.WARNING_MESSAGE);
            return;
        }
        ArrayList<Formula1Driver> randomStartingPoints = new ArrayList<>(formula1_drivers);
        Collections.shuffle(randomStartingPoints);
        Random r = new Random();
        int prob = r.nextInt(100);
        Formula1Driver firstplace = null ;
        if(prob >=0 && prob <=40){
            firstplace = randomStartingPoints.get(0);
        }else if (prob>=41 && prob <=70 ){
            firstplace = randomStartingPoints.get(1);
        }else if (prob>=71 && prob <=90){
            firstplace =randomStartingPoints.get(2);
        }else if(true){

        }
    }

    public void searchDriver(){
        searchInput= text.getText();
        for(Formula1Driver f1 : formula1_drivers){
            if(f1.getDName().equalsIgnoreCase(searchInput)){
                driverLabel.setText(f1.getDName());
                Object[][] data = new Object[f1Races.size()][2];
                int i = 0;
                for(Race r : f1Races){
                    for(Formula1Driver f : r.getDrivers()){
                        if(f.getDName().equals(f1.getDName())){
                            data[i][0] = r.getDate();
                            data[i][1] = (r.getDrivers().indexOf(f) + 1 );
                            i++;
                        }
                    }

                }
                text.setText("");
                driver = new JFrame("Driver Details");
                driverModel = new DefaultTableModel(data,driverColumn);
                driverTable = new JTable(driverModel);
                driverTable.getTableHeader().setBackground(Color.CYAN);
                driver.add(new JScrollPane(driverTable));
                driver.setSize(300,300);
                driver.setVisible(true);
                return;
            }
        }
        driverLabel.setText("Invalid input");
        text.setText("");
    }

    public void showRaces(){
        ArrayList<Race> dateSorted = new ArrayList<>(f1Races);
        Object[][] data = new Object[f1Races.size()][5];
        int i =0;
        for(Race r : f1Races){
            ArrayList<Formula1Driver> racers = r.getDrivers();
            data[i][0] = r.getDate();
            data[i][1] = racers.get(0).getDName();
            data[i][2] = racers.get(1).getDName();
            data[i][3] = racers.get(1).getDName();
            i++;
        }
        jtable.setModel(new DefaultTableModel(data,raceColumn));
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == randomRace){
            genRandomRace();
        }else if(e.getSource() == printRace){
            showRaces();
        }else if(e.getSource() == checkBox){
            if(checkBox.isSelected() == true){
                sort1stPos();
                ascending.setEnabled(false);
                descending.setEnabled(false);
            }else if (checkBox.isSelected() == false){
                ascending.setEnabled(true);
                descending.setEnabled(true);
                jtable.setModel(new DefaultTableModel(convertData(sortPoints), columnHeader));
            }
        }else if(e.getSource() == searchDriver){
            searchDriver();
        }else if(e.getSource() == ascending){
            ArrayList<Formula1Driver> duplicate = new ArrayList<>(sortPoints);
            Collections.reverse(duplicate);
            jtable.setModel(new DefaultTableModel(convertData(duplicate), columnHeader));
        }else if(e.getSource() == descending){
            jtable.setModel(new DefaultTableModel(convertData(sortPoints), columnHeader));
        }else if(e.getSource() == clear){
            jtable.setModel(new DefaultTableModel(convertData(sortPoints), columnHeader));
            checkBox.setSelected(false);
            ascending.setEnabled(true);
            descending.setEnabled(true);
            descending.setSelected(true);
        }
        else if(e.getSource() == calculateRace){
            genProbabilityRace();
        }
    }
}
//References
    //Links
        //YouTube
            //https://www.youtube.com/watch?v=Kmgo00avvEw&ab_channel=BroCode
            //https://www.youtube.com/watch?v=5o3fMLPY7qY&ab_channel=AlexLee
            //https://www.youtube.com/watch?v=b8_7N3P6mic&ab_channel=BTechDays
        //Websites
            //https://www.javatpoint.com/java-swing
            //https://www.javatpoint.com/java-jlabel
            //https://www.javatpoint.com/java-jcheckbox
            //https://www.w3schools.com/java/java_files_create.asp
            //https://www.tutorialspoint.com/swing/swing_gridlayout.htm
    //Books
        //Big Java, Cay Horstmann, fourth edition, Wiley, 2010
