package ce201.src;

import com.csvreader.CsvReader;
import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import java.util.regex.Pattern;

import static java.awt.Color.*;
import static java.time.LocalDate.now;


/**
 * Created by mr16407 on 29/01/2018.
 */
public class SearchForData extends JPanel {

    //final String variables that will use for search
    //id and frequency
    public static String idTyped="";
    public static String demandNumberTyped="";
    public static String frequencyIdTyped="";
    public static String energyTypeSelected="";

    //timestamp
    public static String yearTyped="";
    public static String monthTyped="";
    public static String dayTyped="";
    public static String hourTyped="";
    public static String minuteTyped="";
    public static String secondTyped="";
    static UtilDateModel timeModel;

    //energy type
    public static String coalTyped="";
    public static String nuclearTyped="";
    public static String ccgtTyped="";
    public static String windTyped="";
    public static String french_ictTyped="";
    public static String dutch_ictTyped="";
    public static String irish_ictTyped="";
    public static String ew_ictTyped="";
    public static String pumpedTyped="";
    public static String hydroTyped="";
    public static String oilTyped="";
    public static String ocgtTyped="";
    public static String otherTyped="";
    public static String solarTyped="";
    static boolean sUsed = false;

    static boolean searchsnip;

    //String variables that are used for checking the date
    //boolean variable to check if any date is selected and less than the current date
    //this variables contain the curent timestamp
    public static boolean dateChecker;
    int curentYear= Integer.parseInt(now().format(DateTimeFormatter.ofPattern("yyyy")));
    int curentMonth= Integer.parseInt(now().format(DateTimeFormatter.ofPattern("M")));
    int curentDay= Integer.parseInt(now().format(DateTimeFormatter.ofPattern("d")));
    int curentHour=Integer.parseInt(LocalTime.now().format(DateTimeFormatter.ofPattern("HH")));
    int curentMinute=Integer.parseInt(LocalTime.now().format(DateTimeFormatter.ofPattern("mm")));
    int curentSecond=Integer.parseInt(LocalTime.now().format(DateTimeFormatter.ofPattern("ss")));

    //search results data
    static ArrayList<String> allDataSearch = new ArrayList<>();

    //hystory criteria selected
    public static ArrayList<String[]> hystory=new ArrayList<>();

    public SearchForData() {
        //creating the local Jpanel for Search that will contain the Jpanel for Search Fields and Jpanel for buttons
        JPanel Search=new JPanel(new GridBagLayout());
        //creating Jpanel for search fields  that will contain Jpanels for selecting the ID, energy type and timestamp
        JPanel searchfield=new JPanel(new GridBagLayout());

//creating Jpanel for the entry ID selection and initializing all the components
        JPanel searchId = new JPanel(new BorderLayout());
        JTextField idNumber;
        JLabel idLabel;

        idNumber = new JTextField(6);
        searchId.add(idNumber);

        idLabel =new JLabel("ENTRY ID");
        idLabel.setLabelFor(idNumber);
        searchId.add(idLabel,BorderLayout.NORTH);

        //checking the input for entry ID, and deleting any incorect input
        idNumber.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);

                if(idNumber.getText().length()<=6 &&  Pattern.matches("[0-9]+",idNumber.getText())==true)
                    idTyped=idNumber.getText();
                else
                {
                    if (idNumber.getText().length()==0)
                    {
                        idNumber.setText("");
                        idTyped=idNumber.getText();
                    }
                    else {
                        e.consume();
                        idNumber.setText(idTyped);
                    }
                }
            }
        });


        //ceating Janel for the demand ID selection and initializing all the components
        JPanel demandID = new JPanel(new BorderLayout());
        JTextField demandNumber;
        JLabel demandLabel;

        demandNumber = new JTextField(5);
        demandID.add(demandNumber);

        demandLabel =new JLabel("Demand");
        demandLabel.setLabelFor(demandNumber);
        demandID.add(demandLabel,BorderLayout.NORTH);


//checking the input for demand ID, and deleting any incorect input
        demandNumber.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);

                if(demandNumber.getText().length()<=5 &&  Pattern.matches("[0-9]+",demandNumber.getText())==true)
                    demandNumberTyped=demandNumber.getText();
                else
                {
                    if (demandNumber.getText().length()==0)
                    {
                        demandNumber.setText("");
                        demandNumberTyped=demandNumber.getText();
                    }
                    else {
                        e.consume();
                        demandNumber.setText(demandNumberTyped);
                    }
                }
            }
        });

//ceating Janel for the frequency ID selection and initializing all the components
        JPanel frequencyID = new JPanel(new BorderLayout());
        JTextField  frequencyNumber;
        JLabel frequencyLabel;

        frequencyNumber = new JTextField(6);
        frequencyID.add( frequencyNumber);

        frequencyLabel =new JLabel("FREQUENCY");
        frequencyLabel.setLabelFor( frequencyNumber);
        frequencyID.add(frequencyLabel,BorderLayout.NORTH);


//checking the input for frequency ID, and deleting any incorect input
        frequencyNumber.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);

                if(Pattern.matches("^([+-]?\\d*\\.?\\d*)$", frequencyNumber.getText())==true)
                {
                    if (frequencyNumber.getText().length()==1 && frequencyNumber.getText().charAt(0)=='.')
                    {
                        frequencyNumber.setText("");
                        frequencyIdTyped="";
                    }

                    if (frequencyNumber.getText().length()==2 && frequencyNumber.getText().charAt(0)=='-' && frequencyNumber.getText().charAt(1)=='.')
                    {
                        frequencyNumber.setText("-");
                        frequencyIdTyped="-";
                    }

                    frequencyIdTyped=frequencyNumber.getText();
                }
                else
                {
                    if ( frequencyNumber.getText().length()==0)
                    {
                        frequencyNumber.setText("");
                        frequencyIdTyped="";
                    }
                    else {
                        e.consume();
                        frequencyNumber.setText(frequencyIdTyped);
                    }
                }
            }
        });

        //creating Jpanel for selecting the Energy type and initializing the components
        JPanel searchEnergyType=new JPanel(new GridBagLayout());
        searchEnergyType.setBorder(BorderFactory.createLineBorder(Color.blue));

        //initialize the components and adding all the jlabels to the jtextfields
        GridBagConstraints gbcEnergy=new GridBagConstraints();
        gbcEnergy.gridx=3;
        gbcEnergy.gridy=0;
        gbcEnergy.insets=new Insets(1,2,1,2);

        JLabel energyLabel=new JLabel("Energy types");
        searchEnergyType.add(energyLabel,gbcEnergy);
        gbcEnergy.gridy++;
        gbcEnergy.gridx=0;

        JTextField coal=new JTextField(6);
        JLabel coalLabel=new JLabel("Coal");
        coalLabel.setLabelFor(coal);

        JTextField nuclear=new JTextField(6);
        JLabel nuclearLabel=new JLabel("Nuclear");
        nuclearLabel.setLabelFor(nuclear);

        JTextField CCGT=new JTextField(6);
        JLabel CCGTLabel=new JLabel("CCGT");
        CCGTLabel.setLabelFor(CCGT);

        JTextField wind=new JTextField(6);
        JLabel windLabel=new JLabel("Wind");
        windLabel.setLabelFor(wind);

        JTextField french_ict=new JTextField(6);
        JLabel french_ictLabel=new JLabel("French_ict");
        french_ictLabel.setLabelFor(french_ict);

        JTextField dutch_ict=new JTextField(6);
        JLabel dutch_ictLabel=new JLabel("Dutch_ict");
        dutch_ictLabel.setLabelFor(dutch_ict);

        JTextField irish_ict=new JTextField(6);
        JLabel irish_ictLabel=new JLabel("Irish_ict");
        irish_ictLabel.setLabelFor(irish_ict);

        JTextField ew_ict=new JTextField(6);
        JLabel ew_ictLabel=new JLabel("Ew_ict");
        ew_ictLabel.setLabelFor(ew_ict);

        JTextField pumped=new JTextField(6);
        JLabel pumpedLabel=new JLabel("Pumped");
        pumpedLabel.setLabelFor(pumped);

        JTextField hydro=new JTextField(6);
        JLabel hydroLabel=new JLabel("Hydro");
        hydroLabel.setLabelFor(hydro);

        JTextField oil=new JTextField(6);
        JLabel oilLabel=new JLabel("Oil");
        oilLabel.setLabelFor(oil);

        JTextField ocgt=new JTextField(6);
        JLabel ocgtLabel=new JLabel("OCGT");
        ocgtLabel.setLabelFor(ocgt);

        JTextField other=new JTextField(6);
        JLabel otherLabel=new JLabel("Other");
        otherLabel.setLabelFor(other);

        JTextField solar=new JTextField(6);
        JLabel solarLabel=new JLabel("Solar");
        solarLabel.setLabelFor(solar);

        //arranging all the components in the energy jpanel with an GridBagConstraints
        searchEnergyType.add(coalLabel,gbcEnergy);
        gbcEnergy.gridx++;
        searchEnergyType.add(nuclearLabel,gbcEnergy);
        gbcEnergy.gridx++;
        searchEnergyType.add(CCGTLabel,gbcEnergy);
        gbcEnergy.gridx++;
        searchEnergyType.add(windLabel,gbcEnergy);
        gbcEnergy.gridx++;
        searchEnergyType.add(french_ictLabel,gbcEnergy);
        gbcEnergy.gridx++;
        searchEnergyType.add(dutch_ictLabel,gbcEnergy);
        gbcEnergy.gridx++;
        searchEnergyType.add(irish_ictLabel,gbcEnergy);
        gbcEnergy.gridx++;

        gbcEnergy.gridx=0;
        gbcEnergy.gridy++;

        searchEnergyType.add(coal,gbcEnergy);
        gbcEnergy.gridx++;
        searchEnergyType.add(nuclear,gbcEnergy);
        gbcEnergy.gridx++;
        searchEnergyType.add(CCGT,gbcEnergy);
        gbcEnergy.gridx++;
        searchEnergyType.add(wind,gbcEnergy);
        gbcEnergy.gridx++;
        searchEnergyType.add(french_ict,gbcEnergy);
        gbcEnergy.gridx++;
        searchEnergyType.add(dutch_ict,gbcEnergy);
        gbcEnergy.gridx++;
        searchEnergyType.add(irish_ict,gbcEnergy);
        gbcEnergy.gridx++;

        gbcEnergy.gridx=0;
        gbcEnergy.gridy++;

        searchEnergyType.add(ew_ictLabel,gbcEnergy);
        gbcEnergy.gridx++;
        searchEnergyType.add(pumpedLabel,gbcEnergy);
        gbcEnergy.gridx++;
        searchEnergyType.add(hydroLabel,gbcEnergy);
        gbcEnergy.gridx++;
        searchEnergyType.add(oilLabel,gbcEnergy);
        gbcEnergy.gridx++;
        searchEnergyType.add(ocgtLabel,gbcEnergy);
        gbcEnergy.gridx++;
        searchEnergyType.add(otherLabel,gbcEnergy);
        gbcEnergy.gridx++;
        searchEnergyType.add(solarLabel,gbcEnergy);

        gbcEnergy.gridx=0;
        gbcEnergy.gridy++;

        searchEnergyType.add(ew_ict,gbcEnergy);
        gbcEnergy.gridx++;
        searchEnergyType.add(pumped,gbcEnergy);
        gbcEnergy.gridx++;
        searchEnergyType.add(hydro,gbcEnergy);
        gbcEnergy.gridx++;
        searchEnergyType.add(oil,gbcEnergy);
        gbcEnergy.gridx++;
        searchEnergyType.add(ocgt,gbcEnergy);
        gbcEnergy.gridx++;
        searchEnergyType.add(other,gbcEnergy);
        gbcEnergy.gridx++;
        searchEnergyType.add(solar,gbcEnergy);

        //verify the coal input
        coal.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);

                if(Pattern.matches("^([+-]?\\d*\\.?\\d*)$",coal.getText())==true)
                {
                    if (coal.getText().length()==1 && coal.getText().charAt(0)=='.')
                    {
                        coal.setText("");
                        coalTyped="";
                    }

                    if (coal.getText().length()==2 && coal.getText().charAt(0)=='-' && coal.getText().charAt(1)=='.')
                    {
                        coal.setText("-");
                        coalTyped="-";
                    }

                    coalTyped=coal.getText();
                }
                else
                {
                    if (coal.getText().length()==0)
                    {
                        coal.setText("");
                        coalTyped="";
                    }
                    else {
                        e.consume();
                        coal.setText(coalTyped);
                    }
                }
            }
        });

        //verify the nuclear input
        nuclear.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);

                if(Pattern.matches("^([+-]?\\d*\\.?\\d*)$",nuclear.getText())==true)
                {
                    if (nuclear.getText().length()==1 && coal.getText().charAt(0)=='.')
                    {
                        nuclear.setText("");
                        nuclearTyped="";
                    }

                    if (nuclear.getText().length()==2 && nuclear.getText().charAt(0)=='-' && nuclear.getText().charAt(1)=='.')
                    {
                        nuclear.setText("-");
                        nuclearTyped="-";
                    }

                    nuclearTyped=nuclear.getText();
                }
                else
                {
                    if (nuclear.getText().length()==0)
                    {
                        nuclear.setText("");
                        nuclearTyped="";
                    }
                    else {
                        e.consume();
                        nuclear.setText(nuclearTyped);
                    }
                }
            }
        });

        //verify the CCGT input
        CCGT.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);

                if(Pattern.matches("^([+-]?\\d*\\.?\\d*)$",CCGT.getText())==true)
                {
                    if (CCGT.getText().length()==1 && CCGT.getText().charAt(0)=='.')
                    {
                        CCGT.setText("");
                        ccgtTyped="";
                    }

                    if (CCGT.getText().length()==2 && CCGT.getText().charAt(0)=='-' && CCGT.getText().charAt(1)=='.')
                    {
                        CCGT.setText("-");
                        ccgtTyped="-";
                    }

                    ccgtTyped=CCGT.getText();
                }
                else
                {
                    if (CCGT.getText().length()==0)
                    {
                        CCGT.setText("");
                        ccgtTyped="";
                    }
                    else {
                        e.consume();
                        CCGT.setText(ccgtTyped);
                    }
                }
            }
        });

        //verify the wind input
        wind.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);

                if(Pattern.matches("^([+-]?\\d*\\.?\\d*)$",wind.getText())==true)
                {
                    if (wind.getText().length()==1 && wind.getText().charAt(0)=='.')
                    {
                        wind.setText("");
                        windTyped="";
                    }

                    if (wind.getText().length()==2 && wind.getText().charAt(0)=='-' && wind.getText().charAt(1)=='.')
                    {
                        wind.setText("-");
                        windTyped="-";
                    }

                    windTyped=wind.getText();
                }
                else
                {
                    if (wind.getText().length()==0)
                    {
                        wind.setText("");
                        windTyped="";
                    }
                    else {
                        e.consume();
                        wind.setText(windTyped);
                    }
                }
            }
        });

        //verify the french_ict input
        french_ict.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);

                if(Pattern.matches("^([+-]?\\d*\\.?\\d*)$",french_ict.getText())==true)
                {
                    if (french_ict.getText().length()==1 && french_ict.getText().charAt(0)=='.')
                    {
                        french_ict.setText("");
                        french_ictTyped="";
                    }

                    if (french_ict.getText().length()==2 && french_ict.getText().charAt(0)=='-' && french_ict.getText().charAt(1)=='.')
                    {
                        french_ict.setText("-");
                        french_ictTyped="-";
                    }

                    french_ictTyped=french_ict.getText();
                }
                else
                {
                    if (french_ict.getText().length()==0)
                    {
                        french_ict.setText("");
                        french_ictTyped="";
                    }
                    else {
                        e.consume();
                        french_ict.setText(french_ictTyped);
                    }
                }
            }
        });

//verify the dutch_ict input
        dutch_ict.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);

                if(Pattern.matches("^([+-]?\\d*\\.?\\d*)$",dutch_ict.getText())==true)
                {
                    if (dutch_ict.getText().length()==1 && dutch_ict.getText().charAt(0)=='.')
                    {
                        dutch_ict.setText("");
                        dutch_ictTyped="";
                    }

                    if (dutch_ict.getText().length()==2 && dutch_ict.getText().charAt(0)=='-' && dutch_ict.getText().charAt(1)=='.')
                    {
                        dutch_ict.setText("-");
                        dutch_ictTyped="-";
                    }

                    dutch_ictTyped=dutch_ict.getText();
                }
                else
                {
                    if (dutch_ict.getText().length()==0)
                    {
                        dutch_ict.setText("");
                        dutch_ictTyped="";
                    }
                    else {
                        e.consume();
                        dutch_ict.setText(dutch_ictTyped);
                    }
                }
            }
        });

        //verify the irish_ict input
        irish_ict.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);

                if(Pattern.matches("^([+-]?\\d*\\.?\\d*)$",irish_ict.getText())==true)
                {
                    if (irish_ict.getText().length()==1 && irish_ict.getText().charAt(0)=='.')
                    {
                        irish_ict.setText("");
                        irish_ictTyped="";
                    }

                    if (irish_ict.getText().length()==2 && irish_ict.getText().charAt(0)=='-' && irish_ict.getText().charAt(1)=='.')
                    {
                        irish_ict.setText("-");
                        irish_ictTyped="-";
                    }

                    irish_ictTyped=irish_ict.getText();
                }
                else
                {
                    if (irish_ict.getText().length()==0)
                    {
                        irish_ict.setText("");
                        irish_ictTyped="";
                    }
                    else {
                        e.consume();
                        irish_ict.setText(irish_ictTyped);
                    }
                }
            }
        });

//verify the ew_ict input
        ew_ict.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);

                if(Pattern.matches("^([+-]?\\d*\\.?\\d*)$",ew_ict.getText())==true)
                {
                    if (ew_ict.getText().length()==1 && ew_ict.getText().charAt(0)=='.')
                    {
                        ew_ict.setText("");
                        ew_ictTyped="";
                    }

                    if (ew_ict.getText().length()==2 && ew_ict.getText().charAt(0)=='-' && ew_ict.getText().charAt(1)=='.')
                    {
                        ew_ict.setText("-");
                        ew_ictTyped="-";
                    }

                    ew_ictTyped=ew_ict.getText();
                }
                else
                {
                    if (ew_ict.getText().length()==0)
                    {
                        ew_ict.setText("");
                        ew_ictTyped="";
                    }
                    else {
                        e.consume();
                        ew_ict.setText(ew_ictTyped);
                    }
                }
            }
        });

        //verify the pumped input
        pumped.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);

                if(Pattern.matches("^([+-]?\\d*\\.?\\d*)$",pumped.getText())==true)
                {
                    if (pumped.getText().length()==1 && pumped.getText().charAt(0)=='.')
                    {
                        pumped.setText("");
                        pumpedTyped="";
                    }

                    if (pumped.getText().length()==2 && pumped.getText().charAt(0)=='-' && pumped.getText().charAt(1)=='.')
                    {
                        pumped.setText("-");
                        pumpedTyped="-";
                    }

                    pumpedTyped=pumped.getText();
                }
                else
                {
                    if (pumped.getText().length()==0)
                    {
                        pumped.setText("");
                        pumpedTyped="";
                    }
                    else {
                        e.consume();
                        pumped.setText(pumpedTyped);
                    }
                }
            }
        });

        //verify the hydro input
        hydro.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);

                if(Pattern.matches("^([+-]?\\d*\\.?\\d*)$",hydro.getText())==true)
                {
                    if (hydro.getText().length()==1 && hydro.getText().charAt(0)=='.')
                    {
                        hydro.setText("");
                        hydroTyped="";
                    }

                    if (hydro.getText().length()==2 && hydro.getText().charAt(0)=='-' && hydro.getText().charAt(1)=='.')
                    {
                        hydro.setText("-");
                        hydroTyped="-";
                    }

                    hydroTyped=hydro.getText();
                }
                else
                {
                    if (hydro.getText().length()==0)
                    {
                        hydro.setText("");
                        hydroTyped="";
                    }
                    else {
                        e.consume();
                        hydro.setText(hydroTyped);
                    }
                }
            }
        });

        //verify the oil input
        oil.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);

                if(Pattern.matches("^([+-]?\\d*\\.?\\d*)$",oil.getText())==true)
                {
                    if (oil.getText().length()==1 && oil.getText().charAt(0)=='.')
                    {
                        oil.setText("");
                        oilTyped="";
                    }

                    if (oil.getText().length()==2 && oil.getText().charAt(0)=='-' && oil.getText().charAt(1)=='.')
                    {
                        oil.setText("-");
                        oilTyped="-";
                    }

                    oilTyped=oil.getText();
                }
                else
                {
                    if (oil.getText().length()==0)
                    {
                        oil.setText("");
                        oilTyped="";
                    }
                    else {
                        e.consume();
                        oil.setText(oilTyped);
                    }
                }
            }
        });

        //verify the OCGT input
        ocgt.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);

                if(Pattern.matches("^([+-]?\\d*\\.?\\d*)$",ocgt.getText())==true)
                {
                    if (ocgt.getText().length()==1 && ocgt.getText().charAt(0)=='.')
                    {
                        ocgt.setText("");
                        ocgtTyped="";
                    }

                    if (ocgt.getText().length()==2 && ocgt.getText().charAt(0)=='-' && ocgt.getText().charAt(1)=='.')
                    {
                        ocgt.setText("-");
                        ocgtTyped="-";
                    }

                    ocgtTyped=ocgt.getText();
                }
                else
                {
                    if (ocgt.getText().length()==0)
                    {
                        ocgt.setText("");
                        ocgtTyped="";
                    }
                    else {
                        e.consume();
                        ocgt.setText(ocgtTyped);
                    }
                }
            }
        });

        //verify the other input
        other.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);

                if(Pattern.matches("^([+-]?\\d*\\.?\\d*)$",other.getText())==true)
                {
                    if (other.getText().length()==1 && other.getText().charAt(0)=='.')
                    {
                        other.setText("");
                        otherTyped="";
                    }

                    if (other.getText().length()==2 && other.getText().charAt(0)=='-' && other.getText().charAt(1)=='.')
                    {
                        other.setText("-");
                        otherTyped="-";
                    }

                    otherTyped=other.getText();
                }
                else
                {
                    if (other.getText().length()==0)
                    {
                        other.setText("");
                        otherTyped="";
                    }
                    else {
                        e.consume();
                        other.setText(otherTyped);
                    }
                }
            }
        });

        //verify the solar input
        solar.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);

                if(Pattern.matches("^([+-]?\\d*\\.?\\d*)$",solar.getText())==true)
                {
                    if (solar.getText().length()==1 && solar.getText().charAt(0)=='.')
                    {
                        solar.setText("");
                        solarTyped="";
                    }

                    if (solar.getText().length()==2 && solar.getText().charAt(0)=='-' && solar.getText().charAt(1)=='.')
                    {
                        solar.setText("-");
                        solarTyped="-";
                    }

                    solarTyped=solar.getText();
                }
                else
                {
                    if (solar.getText().length()==0)
                    {
                        solar.setText("");
                        solarTyped="";
                    }
                    else {
                        e.consume();
                        solar.setText(solarTyped);
                    }
                }
            }
        });


// creating the time and date selection panel in and adding all the cpmponents to this panel using GridBagConstraints
        //declaring the dateAndTime Jpanel for the timestamp components
        JPanel dateAndTimee = new JPanel(new GridBagLayout());
        GridBagConstraints gbcTime=new GridBagConstraints();
        gbcTime.gridx=0;
        gbcTime.gridy=0;
        gbcTime.insets=new Insets(1,2,1,2);

//declaring all the Jtextfields and Jlabels for the timestamp
        JTextField year;
        JLabel yearLabel;
        JTextField month;
        JLabel monthLabel;
        JTextField day;
        JLabel dayLabel;
        JTextField hour;
        JLabel hourLabel;
        JTextField minute;
        JLabel minuteLabel;
        JTextField second;
        JLabel secondLabel;

//create the JDatePickerImpl for selecting the date with an interface
        Properties timeProperties = new Properties();
        timeProperties.put("text.day", "Day");
        timeProperties.put("text.month", "Month");
        timeProperties.put("text.year", "Year");
        timeModel=new UtilDateModel();
        JDatePanelImpl selectTime = new JDatePanelImpl(timeModel,timeProperties);
        selectTime.setVisible(false);
//hide the field that displays the date selected
        JDatePickerImpl selectDate=new JDatePickerImpl(selectTime,new DateFormatter());
        selectDate.getComponent(0).setPreferredSize(new Dimension(0,0));

        //initializing and setting all the Jtextfields and Jlabels for the timestamp and adding them to the dateandtime jpanel
        //setting the jlabels for the jtextfields and adding them to the timestamp panel
        year =new JTextField(4);
        yearLabel=new JLabel("Year");
        yearLabel.setLabelFor(year);
        dateAndTimee.add(yearLabel,gbcTime);
        gbcTime.gridx++;

        month =new JTextField(2);
        monthLabel=new JLabel("Month");
        monthLabel.setLabelFor(month);
        dateAndTimee.add(monthLabel,gbcTime);
        gbcTime.gridx++;

        day =new JTextField(2);
        dayLabel=new JLabel("Day");
        dayLabel.setLabelFor(day);
        dateAndTimee.add(dayLabel,gbcTime);
        gbcTime.gridx++;

        hour =new JTextField(2);
        hourLabel=new JLabel("Hour");
        hourLabel.setLabelFor(hour);
        dateAndTimee.add(hourLabel,gbcTime);
        gbcTime.gridx++;

        minute =new JTextField(2);
        minuteLabel=new JLabel("Minute");
        minuteLabel.setLabelFor(minute);
        dateAndTimee.add(minuteLabel,gbcTime);
        gbcTime.gridx++;

        second =new JTextField(2);
        secondLabel=new JLabel("Second");
        secondLabel.setLabelFor(second);
        dateAndTimee.add(secondLabel,gbcTime);
        gbcTime.gridx++;

        // adding the jtextfields to the timestamp panel
        gbcTime.gridx=0;
        gbcTime.gridy++;
        gbcTime.fill= GridBagConstraints.HORIZONTAL;

        dateAndTimee.add(year,gbcTime);
        gbcTime.gridx++;

        dateAndTimee.add(month,gbcTime);
        gbcTime.gridx++;

        dateAndTimee.add(day,gbcTime);
        gbcTime.gridx++;

        dateAndTimee.add(hour,gbcTime);
        gbcTime.gridx++;

        dateAndTimee.add(minute,gbcTime);
        gbcTime.gridx++;

        dateAndTimee.add(second,gbcTime);
        gbcTime.gridx++;

        dateAndTimee.add(selectDate,gbcTime);
        gbcTime.gridx++;

        //check if the input for selecting the date is correct
        selectDate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //get the current date
                String curentDate= LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd");
                String selectedDate1=selectDate.getJFormattedTextField().getText();

                //save current date and selected date
                Date chosenDate=new Date();
                Date actualDate=new Date();
                try {
                    chosenDate=dateFormat.parse(selectedDate1);
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }

                try {
                    actualDate=dateFormat.parse(curentDate);
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }

                //check if selected date is smaller or bigger than the current one
                if (actualDate.after(chosenDate) || actualDate.equals(chosenDate))
                {
                    //update the date fields
                    String[] ds=selectedDate1.split("-");
                    yearTyped=ds[0];
                    year.setText(yearTyped);

                    if (ds[1].charAt(0)=='0')
                        monthTyped=String.valueOf(ds[1].charAt(1));
                    else
                        monthTyped=ds[1];
                    month.setText(monthTyped);

                    if (ds[2].charAt(0)=='0')
                        dayTyped=String.valueOf(ds[2].charAt(1));
                    else
                        dayTyped=ds[2];
                    day.setText(dayTyped);

                    //check if current date is equal with the selected one
                    if (actualDate.equals(chosenDate))
                    {
                        //update the current time
                        //mark that the time is selected
                        curentHour=Integer.parseInt(LocalTime.now().format(DateTimeFormatter.ofPattern("HH")));
                        curentMinute=Integer.parseInt(LocalTime.now().format(DateTimeFormatter.ofPattern("mm")));
                        curentSecond=Integer.parseInt(LocalTime.now().format(DateTimeFormatter.ofPattern("ss")));;
                        dateChecker=true;

                        //check if the selected hour is bigger than the current one and update the time fields
                        if(hour.getText().length()>0 && Integer.parseInt(hour.getText())>curentHour)
                        {
                            hour.setText("");
                            hourTyped="";
                            minute.setText("");
                            minuteTyped="";
                            second.setText("");
                            secondTyped="";
                        }

                        //check if the selected hour is equal with the current one and current minutes are bigger than the current one and update the time fields
                        if(hour.getText().length()>0 && minute.getText().length()>0 && Integer.parseInt(hour.getText())==curentHour  && Integer.parseInt(minute.getText()) > curentMinute)
                        {
                            minute.setText("");
                            minuteTyped="";
                            second.setText("");
                            secondTyped="";
                        }

                        //check if the selected time is bigger wit a few secconds than the current one and update the time fields
                        if(hour.getText().length()>0 && minute.getText().length()>0 && second.getText().length()>0 && Integer.parseInt(hour.getText())==curentHour  && Integer.parseInt(minute.getText()) == curentMinute && Integer.parseInt(second.getText()) > curentSecond)
                        {
                            second.setText("");
                            secondTyped="";
                        }

                    }
                    else
                        dateChecker=false;
                }
            }
        });

//recording input for the year
        year.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);

                //updating the current timestamp
                curentYear= Integer.parseInt(now().format(DateTimeFormatter.ofPattern("yyyy")));
                curentMonth= Integer.parseInt(now().format(DateTimeFormatter.ofPattern("M")));
                curentDay= Integer.parseInt(now().format(DateTimeFormatter.ofPattern("d")));
                curentHour=Integer.parseInt(LocalTime.now().format(DateTimeFormatter.ofPattern("HH")));
                curentMinute=Integer.parseInt(LocalTime.now().format(DateTimeFormatter.ofPattern("mm")));
                curentSecond=Integer.parseInt(LocalTime.now().format(DateTimeFormatter.ofPattern("ss")));;

                //checking if the input is correct and before the current timestamp
                if(year.getText().length()>0 && year.getText().length()<=4 &&  Pattern.matches("[0-9]+",year.getText())==true && curentYear>=Integer.parseInt(year.getText()))
                {
                    if (month.getText().length()>0)
                    {
                        //checking if the selected timestamp is bigger than the current one
                        if (Integer.parseInt(year.getText()) == curentYear && Integer.parseInt(month.getText()) > curentMonth)
                        {
                            //if so remove all the selected inputs dor month,day,hour and minute
                            //saving the imputed year for search
                            month.setText("");
                            monthTyped="";
                            day.setText("");
                            dayTyped="";
                            hour.setText("");
                            hourTyped="";
                            minute.setText("");
                            minuteTyped="";
                            second.setText("");
                            secondTyped="";
                            yearTyped=year.getText();
                        }
                        else
                        {
                            if (day.getText().length()>0 && Integer.parseInt(year.getText()) == curentYear && Integer.parseInt(month.getText()) == curentMonth && Integer.parseInt(day.getText())>curentDay)
                            {
                                //removing the day input if year and month are equal but selected day is bigger than the curent one
                                day.setText("");
                                dayTyped="";
                                hour.setText("");
                                hourTyped="";
                                minute.setText("");
                                minuteTyped="";
                                second.setText("");
                                secondTyped="";
                                yearTyped=year.getText();
                            }
                            //saving the imputed year for search
                            yearTyped=year.getText();

                            //checking if selected date is bigger than the current one
                            if (day.getText().length()>0 && Integer.parseInt(year.getText()) == curentYear && Integer.parseInt(month.getText()) == curentMonth && Integer.parseInt(day.getText())==curentDay)
                            {
                                //updating the dateChecker=true, date is equal to the current date
                                dateChecker=true;

                                //checking if the inputed hour
                                //if bigger tha the curent one clear the hour,minute and second jtextfield
                                if (curentHour < Integer.parseInt(hour.getText()) && hour.getText().length() > 0)
                                {
                                    hour.setText("");
                                    hourTyped="";
                                    minute.setText("");
                                    minuteTyped="";
                                    second.setText("");
                                    secondTyped="";
                                }

                                //hour is equal with the curent one check if inputed minute is bigger than the curent one
                                //if so clear the minute and second jtextfields
                                if(hour.getText().length()>0 && minute.getText().length()>0 && curentHour==Integer.parseInt(hour.getText()) && curentMinute < Integer.parseInt(minute.getText()))
                                {
                                    minute.setText("");
                                    minuteTyped="";
                                    second.setText("");
                                    secondTyped="";
                                }
                                //checking if selected time is bigger than the current one
                                if(hour.getText().length()>0 && minute.getText().length()>0 && second.getText().length()>0 && curentHour==Integer.parseInt(hour.getText()) && curentMinute == Integer.parseInt(minute.getText())&& curentSecond < Integer.parseInt(second.getText()))
                                {
                                    second.setText("");
                                    secondTyped="";
                                }
                            }
                            else
                                //date is not selected
                                dateChecker=false;
                        }
                    }
                    else
                        //imputed year is correct
                        yearTyped=year.getText();
                }
                else
                {
                    if (year.getText().length()==0)
                    {
                        //reseting the input
                        year.setText("");
                        yearTyped=year.getText();
                    }
                    else {
                        //input is incorect, delete it
                        e.consume();
                        year.setText(yearTyped);
                    }
                }
            }
        });


        month.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);

                //updaiting the current timestamp
                curentYear= Integer.parseInt(now().format(DateTimeFormatter.ofPattern("yyyy")));
                curentMonth= Integer.parseInt(now().format(DateTimeFormatter.ofPattern("M")));
                curentDay= Integer.parseInt(now().format(DateTimeFormatter.ofPattern("d")));
                curentHour=Integer.parseInt(LocalTime.now().format(DateTimeFormatter.ofPattern("HH")));
                curentMinute=Integer.parseInt(LocalTime.now().format(DateTimeFormatter.ofPattern("mm")));
                curentSecond=Integer.parseInt(LocalTime.now().format(DateTimeFormatter.ofPattern("ss")));;

                //checking if the input is correct
                if(month.getText().length()>0 && Pattern.matches("[0-9]+",month.getText())==true && Integer.parseInt(month.getText())<=12)
                {
                    //if first number inserted is 0 delete it
                    if (month.getText().length()==1 && Integer.parseInt(month.getText())==0)
                    {
                        month.setText("");
                        e.consume();
                    }

                    //year is selected
                    if (year.getText().length() > 0)
                    {
                        //verifying if the selected month is bigger than the current one
                        if (Integer.parseInt(year.getText()) == curentYear && Integer.parseInt(month.getText()) > curentMonth)
                        {//resetting the input to the previous one
                            if (month.getText().length()==1)
                            {
                                month.setText("");
                                monthTyped="";
                                e.consume();
                            }
                            else
                            {
                                month.setText(monthTyped);
                                e.consume();
                            }
                        }
                        else
                        {
                            if (day.getText().length()>0 && Integer.parseInt(year.getText()) == curentYear && Integer.parseInt(month.getText()) == curentMonth)
                            {//selected year and month are equal to the current ones and check if selected day is bigger than the current one
                                if (curentDay < Integer.parseInt(day.getText()))
                                {//if so reset the day, hour, minute and second jtextfields
                                    day.setText("");
                                    dayTyped="";
                                    hour.setText("");
                                    hourTyped="";
                                    minute.setText("");
                                    minuteTyped="";
                                    second.setText("");
                                    secondTyped="";
                                    monthTyped=month.getText();
                                }
                                else
                                {
                                    //imputed month is correct
                                    monthTyped=month.getText();
                                    if (curentDay == Integer.parseInt(day.getText()))
                                    {
                                        //date is selected  corectly
                                        dateChecker=true;
                                        if (hour.getText().length()>0 && curentHour<Integer.parseInt(hour.getText()))
                                        {
                                            //selected date is equal with the current one and hour is bigger than the current one
                                            //reset the hour, minute and second
                                            hour.setText("");
                                            hourTyped="";
                                            minute.setText("");
                                            minuteTyped="";
                                            second.setText("");
                                            secondTyped="";
                                        }

                                        if(hour.getText().length()>0 && minute.getText().length()>0 && curentHour==Integer.parseInt(hour.getText()) && curentMinute < Integer.parseInt(minute.getText()))
                                        {
                                            //selected date is equal with the current one and minute is bigger than the current one and hour is equal with the current one
                                            //reset the minute and second jtextfields
                                            minute.setText("");
                                            minuteTyped="";
                                            second.setText("");
                                            secondTyped="";
                                        }


                                        if(hour.getText().length()>0 && minute.getText().length()>0 && second.getText().length()>0 && curentHour==Integer.parseInt(hour.getText()) && curentMinute == Integer.parseInt(minute.getText()) && curentSecond < Integer.parseInt(second.getText()))
                                        {
                                            //selected date is equal with the current one
                                            //selected hour and minute are equal with the current ones
                                            //reset seccond jtextfield
                                            second.setText("");
                                            secondTyped="";
                                        }
                                    }
                                    else
                                        //date is not selected
                                        dateChecker=false;
                                }
                            }
                            else
                                //update moth
                                monthTyped=month.getText();
                        }
                    }
                    else
                        monthTyped=month.getText();
                }
                else
                {
                    //reset month
                    if (month.getText().length()==0)
                    {
                        month.setText("");
                        monthTyped="";
                        e.consume();
                    }

                    if (month.getText().length()==1)
                    {
                        month.setText("");
                        monthTyped="";
                        e.consume();
                    }
                    else
                    {
                        month.setText(monthTyped);
                        e.consume();
                    }
                }
            }
        });


        day.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                //get current timestamp
                curentYear= Integer.parseInt(now().format(DateTimeFormatter.ofPattern("yyyy")));
                curentMonth= Integer.parseInt(now().format(DateTimeFormatter.ofPattern("M")));
                curentDay= Integer.parseInt(now().format(DateTimeFormatter.ofPattern("d")));
                curentHour=Integer.parseInt(LocalTime.now().format(DateTimeFormatter.ofPattern("HH")));
                curentMinute=Integer.parseInt(LocalTime.now().format(DateTimeFormatter.ofPattern("mm")));
                curentSecond=Integer.parseInt(LocalTime.now().format(DateTimeFormatter.ofPattern("ss")));;

                //check if the imputed day is correct
                if(day.getText().length()>0 && day.getText().length()<=2 &&  Pattern.matches("[0-9]+",day.getText())==true && Integer.parseInt(day.getText())<=31)
                {
                    //if user input is 0 reset day
                    if (day.getText().length()==1 && Integer.parseInt(day.getText())==0)
                    {
                        day.setText("");
                        dayTyped="";
                        e.consume();
                    }
                    //month and year are selected
                    if (year.getText().length()>0 && month.getText().length()>0)
                    {
                        //check if current date is smaller than the current one
                        if (Integer.parseInt(year.getText()) == curentYear && Integer.parseInt(month.getText()) == curentMonth && Integer.parseInt(day.getText()) > curentDay)
                        {
                            day.setText(dayTyped);
                            e.consume();
                        }
                        else
                            dayTyped=day.getText();

                        if (Integer.parseInt(year.getText()) == curentYear && Integer.parseInt(month.getText()) == curentMonth && Integer.parseInt(day.getText()) == curentDay)
                        {//current date is equal to the selected one
                            dateChecker=true;
                            if (hour.getText().length()>0 && curentHour<Integer.parseInt(hour.getText()))
                            {//selected hour is bigger than the curren one
                                //reset the hour, minute and second
                                hour.setText("");
                                hourTyped="";
                                minute.setText("");
                                minuteTyped="";
                                second.setText("");
                                secondTyped="";
                            }

                            if(hour.getText().length()>0 && minute.getText().length()>0 && curentHour==Integer.parseInt(hour.getText()) && curentMinute < Integer.parseInt(minute.getText()))
                            {
                                //selected hour is equal with the current one and selected minute is bigger than the current one
                                //reset the minute and second
                                minute.setText("");
                                minuteTyped="";
                                second.setText("");
                                secondTyped="";
                            }

                            if(hour.getText().length()>0 && minute.getText().length()>0 && second.getText().length()>0 && curentHour==Integer.parseInt(hour.getText()) && curentMinute == Integer.parseInt(minute.getText()) && curentSecond < Integer.parseInt(second.getText()))
                            {
                                //selected time is bigger than the curent one
                                //reset second
                                second.setText("");
                                secondTyped="";
                            }
                        }
                        else
                            //date is not equal with the current one
                            dateChecker=false;
                    }
                    else
                        //typed date is corect
                        //update the day for searching
                        dayTyped=day.getText();
                }
                else
                {
                    //reset day to the previous stage if day imputed is incorrect
                    if (day.getText().length()==0)
                    {
                        day.setText("");
                        dayTyped="";
                        e.consume();
                    }
                    else
                    {
                        day.setText(dayTyped);
                        e.consume();
                    }
                }
            }
        });


        hour.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e)
            {
                super.keyReleased(e);
                //update the current timestamp
                curentYear = Integer.parseInt(now().format(DateTimeFormatter.ofPattern("yyyy")));
                curentMonth= Integer.parseInt(now().format(DateTimeFormatter.ofPattern("M")));
                curentDay = Integer.parseInt(now().format(DateTimeFormatter.ofPattern("d")));
                curentHour = Integer.parseInt(LocalTime.now().format(DateTimeFormatter.ofPattern("HH")));
                curentMinute = Integer.parseInt(LocalTime.now().format(DateTimeFormatter.ofPattern("mm")));
                curentSecond = Integer.parseInt(LocalTime.now().format(DateTimeFormatter.ofPattern("ss")));

                //check if the imputed hour is correct
                if (hour.getText().length() <= 2 && Pattern.matches("[0-9]+", hour.getText()) == true && 24 >= Integer.parseInt(hour.getText()) && hour.getText().length() > 0)
                {
                    if (hour.getText().length()==2 && hour.getText().charAt(0)=='0')
                        hour.setText(String.valueOf(hour.getText().charAt(1)));

                    //check if one of the fields for date is empty
                    //if so selected date is not equal with the current one
                    if (year.getText().length() == 0 || month.getText().length() == 0 || day.getText().length() == 0)
                        dateChecker = false;

                    if (dateChecker == true)
                    {//selected date is equal with the current one
                        //check if the imputed hour is bigger than the current one
                        if (curentHour < Integer.parseInt(hour.getText()))
                        {
                            hour.setText(hourTyped);
                            e.consume();
                        }

                        if (hour.getText().length()>0 && minute.getText().length() > 0 && curentHour ==Integer.parseInt(hour.getText()) && curentMinute < Integer.parseInt(minute.getText()))
                        {//selected hour is equal with the current one and selected minute is bigger than the current one
                            //reset minute and second
                            minute.setText("");
                            minuteTyped = "";
                            second.setText("");
                            secondTyped = "";
                            hourTyped = hour.getText();
                        }
                        else
                        {
                            if (hour.getText().length()>0 && minute.getText().length() > 0 && second.getText().length() > 0 && curentHour == Integer.parseInt(hour.getText()) && curentMinute == Integer.parseInt(minute.getText()) && Integer.parseInt(second.getText()) > curentSecond)
                            {
                                //current time is bigger than the current one with a number of seconds
                                //reset second
                                second.setText("");
                                secondTyped = "";
                            }
                            //update hour for search
                            hourTyped = hour.getText();
                        }
                    }
                    else
                        //update hour for search
                        hourTyped = hour.getText();
                }
                else
                {
                    //imputed hour is incorrect, update hour to the previous state
                    if (hour.getText().length()==0)
                    {
                        hour.setText("");
                        hourTyped="";
                    }
                    else
                    {
                        e.consume();
                        hour.setText(hourTyped);
                    }
                }
            }
        });


        minute.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                //update the current timestamp
                curentYear= Integer.parseInt(now().format(DateTimeFormatter.ofPattern("yyyy")));
                curentMonth= Integer.parseInt(now().format(DateTimeFormatter.ofPattern("M")));
                curentDay= Integer.parseInt(now().format(DateTimeFormatter.ofPattern("d")));
                curentHour=Integer.parseInt(LocalTime.now().format(DateTimeFormatter.ofPattern("HH")));
                curentMinute=Integer.parseInt(LocalTime.now().format(DateTimeFormatter.ofPattern("mm")));
                curentSecond=Integer.parseInt(LocalTime.now().format(DateTimeFormatter.ofPattern("ss")));;
                //check if the imputed minute is correct
                if(minute.getText().length()>0 && Pattern.matches("[0-9]+",minute.getText())==true && Integer.parseInt(minute.getText())<=59)
                {
                    //check if one of the fields for date is empty
                    //if so selected date is not equal with the current one
                    if (year.getText().length()==0 || month.getText().length()==0 || day.getText().length()==0)
                        dateChecker=false;

                    if (minute.getText().length()==2 && minute.getText().charAt(0)=='0')
                        minute.setText(String.valueOf(minute.getText().charAt(1)));

                    if(dateChecker==true)
                    {
                        //selected date is equal with the current one
                        //check if the imputed hour is imputed
                        if (hour.getText().length() > 0)
                        {
                            if (minute.getText().length()>0 && Integer.parseInt(hour.getText())==curentHour && Integer.parseInt(minute.getText())>curentMinute)
                            {//selected hour is equal with the current one and selected minute is bigger than the current one
                                //update minute to the previous stage
                                if (minute.getText().length() == 1)
                                {
                                    minute.setText("");
                                    minuteTyped = "";
                                    e.consume();
                                }
                                else
                                {
                                    minute.setText(minuteTyped);
                                    e.consume();
                                }
                            }
                            else
                            {
                                if (second.getText().length() > 0 && minute.getText().length()>0 && Integer.parseInt(hour.getText())==curentHour && Integer.parseInt(minute.getText())==curentMinute)
                                {//selected date, hour and minute are equal with the current ones
                                    //if selected second is smaller or equal with the current one save it for search
                                    if (curentSecond >= Integer.parseInt(second.getText()))
                                        minuteTyped = minute.getText();
                                    else
                                    {
                                        second.setText("");
                                        secondTyped = "";
                                        minuteTyped = minute.getText();
                                    }
                                }
                                else
                                    //save correct imputed minute for search
                                    minuteTyped = minute.getText();
                            }
                        }
                        else
                            //save correct imputed minute for search
                            minuteTyped = minute.getText();
                    }
                    else
                        //save correct imputed minute for search
                        minuteTyped=minute.getText();
                }
                else
                {
                    //incorrect imputed minute is restored to the previous stage
                    if (minute.getText().length()==0)
                    {
                        minute.setText("");
                        minuteTyped="";
                        e.consume();
                    }

                    if (minute.getText().length()==1)
                    {
                        minute.setText("");
                        minuteTyped="";
                        e.consume();
                    }
                    else
                    {
                        minute.setText(minuteTyped);
                        e.consume();
                    }
                }
            }
        });


        second.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                //update the current timestamp
                curentYear= Integer.parseInt(now().format(DateTimeFormatter.ofPattern("yyyy")));
                curentMonth= Integer.parseInt(now().format(DateTimeFormatter.ofPattern("M")));
                curentDay= Integer.parseInt(now().format(DateTimeFormatter.ofPattern("d")));
                curentHour=Integer.parseInt(LocalTime.now().format(DateTimeFormatter.ofPattern("HH")));
                curentMinute=Integer.parseInt(LocalTime.now().format(DateTimeFormatter.ofPattern("mm")));
                curentSecond=Integer.parseInt(LocalTime.now().format(DateTimeFormatter.ofPattern("ss")));
                //check if the imputed second is correct
                if(second.getText().length()>0 && second.getText().length()<=2 &&  Pattern.matches("[0-9]+",second.getText())==true && Integer.parseInt(second.getText())<=59)
                {
                    //check if one of the fields for date is empty
                    //if so selected date is not equal with the current one
                    if (year.getText().length()==0 || month.getText().length()==0 || day.getText().length()==0)
                        dateChecker=false;

                    if (second.getText().length()==2 && second.getText().charAt(0)=='0')
                        second.setText(String.valueOf(second.getText().charAt(1)));

                    if(dateChecker==true)
                    {
                        //selected date is equal with the current one
                        //check if the imputed hour and minute are imputed
                        if (hour.getText().length() > 0 && minute.getText().length() > 0)
                        {//hour and minute are selected
                            if (curentHour==Integer.parseInt(hour.getText()) && curentMinute==Integer.parseInt(minute.getText()) && curentSecond < Integer.parseInt(second.getText()))
                            {
                                //selected date, hour and minute are equal with the current ones
                                //if selected second is smaller or equal with the current one save it for search
                                second.setText(secondTyped);
                                e.consume();
                            }
                            else
                                //save correct imputed second for search
                                secondTyped = second.getText();
                        }
                        else
                            //save correct imputed second for search
                            secondTyped = second.getText();
                    }
                    else
                        //save correct imputed second for search
                        secondTyped=second.getText();
                }
                else
                {
                    //incorrect imputed second is restored to the previous stage
                    if (second.getText().length()==0)
                    {
                        second.setText("");
                        secondTyped="";
                        e.consume();
                    }
                    else
                    {
                        second.setText(secondTyped);
                        e.consume();
                    }
                }
            }
        });


        //creating layout for searchfield panel
        GridBagConstraints gbcSearchField=new GridBagConstraints();
        gbcSearchField.gridx=0;
        gbcSearchField.gridy=0;
        gbcSearchField.insets=new Insets(2,2,2,2);

        //adding all the components to the searchfield jpanels
        searchfield.add(searchId,gbcSearchField);
        gbcSearchField.gridx++;
        searchfield.add(demandID,gbcSearchField);
        gbcSearchField.gridx++;
        searchfield.add(frequencyID,gbcSearchField);
        gbcSearchField.gridx++;
        searchfield.add(dateAndTimee,gbcSearchField);

        //creating the search and back jbuttons and adding them to the button jpanel
        JPanel buttonField=new JPanel();

        JButton back = new JButton("Back to Main Menu");
        back.setPreferredSize(new Dimension(170,30));

        JButton searchButton = new JButton("Search");
        searchButton.setPreferredSize(new Dimension(170,30));

        //adding back and search buttons to the button field
        buttonField.add(back);
        buttonField.add(searchButton);

        //Title
        JTextField title = new JTextField("Search Menu\n\n\n\n");

        title.setFont(MainFrame.titleFont);
        title.setHorizontalAlignment(JTextField.CENTER);
        title.setEditable(false);
        title.setBorder(BorderFactory.createEmptyBorder());

        //message
        JTextArea message = new JTextArea("\n\n\nPlease select the search criteria:");

        message.setFont(MainFrame.messageFont);
        message.setEditable(false);
        message.setBorder(BorderFactory.createEmptyBorder());
        message.setOpaque(false);

        JPanel messageRestrictor = new JPanel();
        messageRestrictor.add(message);

        //creating the search jpanel an adding the search and button fields
        GridBagConstraints gbcSearch=new GridBagConstraints();
        gbcSearch.gridx=1;
        gbcSearch.gridy=0;
        gbcSearch.insets=new Insets(2,2,2,2);

        Search.add(title,gbcSearch);
        gbcSearch.gridy++;

        Search.add(messageRestrictor,gbcSearch);
        gbcSearch.gridy++;

        Search.add(searchfield,gbcSearch);
        gbcSearch.gridy++;

        Search.add(searchEnergyType,gbcSearch);
        gbcSearch.gridy++;

        Search.add(buttonField,gbcSearch);

        add(Search);

        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (MainFrame.nightOn)
                    message.setForeground(YELLOW);
                else
                    message.setForeground(null);
                MainFrame.cardLayout.show(MainFrame.cardPanel, "3"); // displays MainMenu without processing
                message.setText("\n\n\nPlease select the search criteria:");

                coal.setText("");
                nuclear.setText("");
                CCGT.setText("");
                wind.setText("");
                french_ict.setText("");
                dutch_ict.setText("");
                irish_ict.setText("");
                ew_ict.setText("");
                pumped.setText("");
                hydro.setText("");
                oil.setText("");
                ocgt.setText("");
                other.setText("");
                solar.setText("");

                hour.setText("");
                minute.setText("");
                second.setText("");
                year.setText("");
                month.setText("");
                day.setText("");

                idNumber.setText("");
                demandNumber.setText("");
                frequencyNumber.setText("");

                idTyped="";
                demandNumberTyped="";
                frequencyIdTyped="";
                energyTypeSelected="";

                //timestamp
                yearTyped="";
                monthTyped="";
                dayTyped="";
                hourTyped="";
                minuteTyped="";
                secondTyped="";

                //energy type
                coalTyped="";
                nuclearTyped="";
                ccgtTyped="";
                windTyped="";
                french_ictTyped="";
                dutch_ictTyped="";
                irish_ictTyped="";
                ew_ictTyped="";
                pumpedTyped="";
                hydroTyped="";
                oilTyped="";
                ocgtTyped="";
                otherTyped="";
                solarTyped="";

                if (MainFrame.nightOn==true)
                    message.setForeground(YELLOW);
                else
                    message.setForeground(null);

            }
        });


        long totalMilliSeconds = System.currentTimeMillis();
        long totalSeconds = totalMilliSeconds / 1000;
        long currentSecond = totalSeconds % 60;
        long totalMinutes = totalSeconds / 60;
        long currentMinute = totalMinutes % 60;
        long totalHours = totalMinutes / 60;
        long currentHour = totalHours % 24;

        //detect the what mode is selected and change the colors on FearchForData based on it
        MainFrame.enable.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if(MainFrame.nightOn==true){

                    energyLabel.setForeground(YELLOW);
                    energyLabel.setBackground(BLACK);
                    coal.setForeground(YELLOW);
                    coal.setBackground(BLACK);
                    coalLabel.setForeground(YELLOW);
                    coalLabel.setBackground(BLACK);
                    nuclear.setForeground(YELLOW);
                    nuclear.setBackground(BLACK);
                    nuclearLabel.setForeground(YELLOW);
                    nuclearLabel.setBackground(BLACK);
                    CCGT.setForeground(YELLOW);
                    CCGT.setBackground(BLACK);
                    CCGTLabel.setForeground(YELLOW);
                    CCGTLabel.setBackground(BLACK);
                    wind.setForeground(YELLOW);
                    wind.setBackground(BLACK);
                    windLabel.setForeground(YELLOW);
                    windLabel.setBackground(BLACK);
                    french_ict.setForeground(YELLOW);
                    french_ict.setBackground(BLACK);
                    french_ictLabel.setForeground(YELLOW);
                    french_ictLabel.setBackground(BLACK);
                    dutch_ict.setForeground(YELLOW);
                    dutch_ict.setBackground(BLACK);
                    dutch_ictLabel.setForeground(YELLOW);
                    dutch_ictLabel.setBackground(BLACK);
                    irish_ict.setForeground(YELLOW);
                    irish_ict.setBackground(BLACK);
                    irish_ictLabel.setForeground(YELLOW);
                    irish_ictLabel.setBackground(BLACK);
                    ew_ict.setForeground(YELLOW);
                    ew_ict.setBackground(BLACK);
                    ew_ictLabel.setForeground(YELLOW);
                    ew_ictLabel.setBackground(BLACK);
                    pumped.setForeground(YELLOW);
                    pumped.setBackground(BLACK);
                    pumpedLabel.setForeground(YELLOW);
                    pumpedLabel.setBackground(BLACK);
                    hydro.setForeground(YELLOW);
                    hydro.setBackground(BLACK);
                    hydroLabel.setForeground(YELLOW);
                    hydroLabel.setBackground(BLACK);
                    oil.setForeground(YELLOW);
                    oil.setBackground(BLACK);
                    oilLabel.setForeground(YELLOW);
                    oilLabel.setBackground(BLACK);
                    ocgt.setForeground(YELLOW);
                    ocgt.setBackground(BLACK);
                    ocgtLabel.setForeground(YELLOW);
                    ocgtLabel.setBackground(BLACK);
                    other.setForeground(YELLOW);
                    other.setBackground(BLACK);
                    otherLabel.setForeground(YELLOW);
                    otherLabel.setBackground(BLACK);
                    solar.setForeground(YELLOW);
                    solar.setBackground(BLACK);
                    solarLabel.setForeground(YELLOW);
                    solarLabel.setBackground(BLACK);

                    dateAndTimee.setForeground(YELLOW);
                    dateAndTimee.setBackground(BLACK);
                    hour.setForeground(YELLOW);
                    hour.setBackground(BLACK);
                    hourLabel.setForeground(YELLOW);
                    hourLabel.setBackground(BLACK);
                    minute.setForeground(YELLOW);
                    minute.setBackground(BLACK);
                    minuteLabel.setForeground(YELLOW);
                    minuteLabel.setBackground(BLACK);
                    second.setForeground(YELLOW);
                    second.setBackground(BLACK);
                    secondLabel.setForeground(YELLOW);
                    secondLabel.setBackground(BLACK);
                    year.setForeground(YELLOW);
                    year.setBackground(BLACK);
                    yearLabel.setForeground(YELLOW);
                    yearLabel.setBackground(BLACK);
                    month.setForeground(YELLOW);
                    month.setBackground(BLACK);
                    monthLabel.setForeground(YELLOW);
                    monthLabel.setBackground(BLACK);
                    day.setForeground(YELLOW);
                    day.setBackground(BLACK);
                    dayLabel.setForeground(YELLOW);
                    dayLabel.setBackground(BLACK);

                    searchId.setForeground(YELLOW);
                    searchId.setBackground(BLACK);
                    idNumber.setForeground(YELLOW);
                    idNumber.setBackground(BLACK);
                    idLabel.setForeground(YELLOW);
                    idLabel.setBackground(BLACK);
                    demandLabel.setForeground(YELLOW);
                    demandLabel.setBackground(BLACK);
                    demandID.setForeground(YELLOW);
                    demandID.setBackground(BLACK);
                    demandNumber.setForeground(YELLOW);
                    demandNumber.setBackground(BLACK);
                    frequencyID.setForeground(YELLOW);
                    frequencyID.setBackground(BLACK);
                    frequencyLabel.setForeground(YELLOW);
                    frequencyLabel.setBackground(BLACK);
                    frequencyNumber.setForeground(YELLOW);
                    frequencyNumber.setBackground(BLACK);

                    setForeground(YELLOW);
                    setBackground(BLACK);
                    searchEnergyType.setForeground(YELLOW);
                    searchEnergyType.setBackground(BLACK);
                    searchfield.setForeground(YELLOW);
                    searchfield.setBackground(BLACK);
                    Search.setForeground(YELLOW);
                    Search.setBackground(BLACK);
                    back.setForeground(YELLOW);
                    back.setBackground(BLACK);
                    searchButton.setForeground(YELLOW);
                    searchButton.setBackground(BLACK);

                    if (message.getForeground()!=red)
                        message.setForeground(YELLOW);

                    message.setBackground(BLACK);
                    buttonField.setForeground(YELLOW);
                    buttonField.setBackground(BLACK);
                    messageRestrictor.setForeground(YELLOW);
                    messageRestrictor.setBackground(BLACK);
                    title.setForeground(YELLOW);
                    title.setBackground(BLACK);
                }
                else
                {
                    energyLabel.setForeground(null);
                    energyLabel.setBackground(null);
                    coal.setForeground(null);
                    coal.setBackground(null);
                    coalLabel.setForeground(null);
                    coalLabel.setBackground(null);
                    nuclear.setForeground(null);
                    nuclear.setBackground(null);
                    nuclearLabel.setForeground(null);
                    nuclearLabel.setBackground(null);
                    CCGT.setForeground(null);
                    CCGT.setBackground(null);
                    CCGTLabel.setForeground(null);
                    CCGTLabel.setBackground(null);
                    wind.setForeground(null);
                    wind.setBackground(null);
                    windLabel.setForeground(null);
                    windLabel.setBackground(null);
                    french_ict.setForeground(null);
                    french_ict.setBackground(null);
                    french_ictLabel.setForeground(null);
                    french_ictLabel.setBackground(null);
                    dutch_ict.setForeground(null);
                    dutch_ict.setBackground(null);
                    dutch_ictLabel.setForeground(null);
                    dutch_ictLabel.setBackground(null);
                    irish_ict.setForeground(null);
                    irish_ict.setBackground(null);
                    irish_ictLabel.setForeground(null);
                    irish_ictLabel.setBackground(null);
                    ew_ict.setForeground(null);
                    ew_ict.setBackground(null);
                    ew_ictLabel.setForeground(null);
                    ew_ictLabel.setBackground(null);
                    pumped.setForeground(null);
                    pumped.setBackground(null);
                    pumpedLabel.setForeground(null);
                    pumpedLabel.setBackground(null);
                    hydro.setForeground(null);
                    hydro.setBackground(null);
                    hydroLabel.setForeground(null);
                    hydroLabel.setBackground(null);
                    oil.setForeground(null);
                    oil.setBackground(null);
                    oilLabel.setForeground(null);
                    oilLabel.setBackground(null);
                    ocgt.setForeground(null);
                    ocgt.setBackground(null);
                    ocgtLabel.setForeground(null);
                    ocgtLabel.setBackground(null);
                    other.setForeground(null);
                    other.setBackground(null);
                    otherLabel.setForeground(null);
                    otherLabel.setBackground(null);
                    solar.setForeground(null);
                    solar.setBackground(null);
                    solarLabel.setForeground(null);
                    solarLabel.setBackground(null);

                    dateAndTimee.setForeground(null);
                    dateAndTimee.setBackground(null);
                    hour.setForeground(null);
                    hour.setBackground(null);
                    hourLabel.setForeground(null);
                    hourLabel.setBackground(null);
                    minute.setForeground(null);
                    minute.setBackground(null);
                    minuteLabel.setForeground(null);
                    minuteLabel.setBackground(null);
                    second.setForeground(null);
                    second.setBackground(null);
                    secondLabel.setForeground(null);
                    secondLabel.setBackground(null);
                    year.setForeground(null);
                    year.setBackground(null);
                    yearLabel.setForeground(null);
                    yearLabel.setBackground(null);
                    month.setForeground(null);
                    month.setBackground(null);
                    monthLabel.setForeground(null);
                    monthLabel.setBackground(null);
                    day.setForeground(null);
                    day.setBackground(null);
                    dayLabel.setForeground(null);
                    dayLabel.setBackground(null);

                    searchId.setForeground(null);
                    searchId.setBackground(null);
                    idNumber.setForeground(null);
                    idNumber.setBackground(null);
                    idLabel.setForeground(null);
                    idLabel.setBackground(null);
                    demandLabel.setForeground(null);
                    demandLabel.setBackground(null);
                    demandID.setForeground(null);
                    demandID.setBackground(null);
                    demandNumber.setForeground(null);
                    demandNumber.setBackground(null);
                    frequencyID.setForeground(null);
                    frequencyID.setBackground(null);
                    frequencyLabel.setForeground(null);
                    frequencyLabel.setBackground(null);
                    frequencyNumber.setForeground(null);
                    frequencyNumber.setBackground(null);

                    setForeground(null);
                    setBackground(null);
                    searchEnergyType.setForeground(null);
                    searchEnergyType.setBackground(null);
                    searchfield.setForeground(null);
                    searchfield.setBackground(null);
                    Search.setForeground(null);
                    Search.setBackground(null);
                    back.setForeground(null);
                    back.setBackground(null);
                    searchButton.setForeground(null);
                    searchButton.setBackground(null);

                    if (message.getForeground()!=red)
                        message.setForeground(null);

                    message.setBackground(null);
                    buttonField.setForeground(null);
                    buttonField.setBackground(null);
                    messageRestrictor.setForeground(null);
                    messageRestrictor.setBackground(null);
                    title.setForeground(null);
                    title.setBackground(null);

                }
            }
        });



        // searching with the selected inputs

        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //check if t least one search field is selected
                if(idTyped.equals("") && demandNumberTyped.equals("") && frequencyIdTyped.equals("") && energyTypeSelected.equals("") && yearTyped.equals("") && monthTyped.equals("") && dayTyped.equals("") && hourTyped.equals("") && minuteTyped.equals("") && secondTyped.equals("") && coalTyped.equals("") && nuclearTyped.equals("") && ccgtTyped.equals("") && windTyped.equals("") && french_ictTyped.equals("") && dutch_ictTyped.equals("") && irish_ictTyped.equals("") && ew_ictTyped.equals("") && pumpedTyped.equals("") && hydroTyped.equals("") && oilTyped.equals("") && ocgtTyped.equals("") && otherTyped.equals("") && solarTyped.equals(""))
                {
                    //message if no criteria was selected
                    message.setText("\n\nNo Criteria Selected! \nPlease select the search criteria:");
                    message.setForeground(red);

                }
                else
                {
                    if (!frequencyIdTyped.equals("-") && !coalTyped.equals("-") && !nuclearTyped.equals("-") && !ccgtTyped.equals("-") && !wind.equals("-") && !french_ictTyped.equals("-") && !dutch_ictTyped.equals("-") && !irish_ictTyped.equals("-") && !ew_ictTyped.equals("-") && !pumpedTyped.equals("-") && !hydroTyped.equals("-") && !oilTyped.equals("-") && !ocgtTyped.equals("-") && !otherTyped.equals("-") && !solarTyped.equals("-"))
                {

                    CsvReader reader = null;

                    try {
                        reader=new CsvReader(ButtonHandler.filepath);
                    } catch (FileNotFoundException e1) {
                        e1.printStackTrace();
                    }

                    try {
                        //initializing an array list to get the search results
                        reader.readHeaders();
                        boolean found=false;
                        ArrayList<String[]>  results = new ArrayList<String[]>();
                        int numberOfResults = 0;

                        //verify if the id is selected
                        if (!idTyped.equals(""))
                        {
                            //search in the list for the record with that id
                            boolean idFound=false;
                            found=false;
                            while (reader.readRecord() && idFound==false)
                            {
                                if (reader.get(0).equals(idTyped))
                                {//record with the specific id
                                    found=true;
                                    idFound = true;
                                    //verify if any timestamp is selected and check if is the same with the one on the record
                                    if (!yearTyped.equals("") || !monthTyped.equals("") || !dayTyped.equals("") || !hourTyped.equals("") || !minuteTyped.equals("") || !secondTyped.equals(""))
                                    {
                                        //split the date and time in 2 different arrays
                                        String[] checkTime = reader.get(1).split("-");
                                        String[] dayToDate = checkTime[2].split(" ");
                                        checkTime[2] = dayToDate[0];
                                        String[] checkDate = dayToDate[1].split(":");

                                        //verify the year
                                        if (!yearTyped.equals("")) {
                                            if (yearTyped.equals(checkTime[0]))
                                                found = true;
                                            else
                                                found = false;
                                        }

                                        //verify the month
                                        if (!monthTyped.equals("") && found == true) {
                                            if (checkTime[1].charAt(0) == '0') {
                                                if (monthTyped.equals(String.valueOf(checkTime[1].charAt(1))))
                                                    found = true;
                                                else
                                                    found = false;
                                            } else {
                                                if (monthTyped.equals(checkTime[1]))
                                                    found = true;
                                                else
                                                    found = false;
                                            }
                                        }

                                        //verify the day
                                        if (!dayTyped.equals("") && found == true) {
                                            if (checkTime[2].charAt(0) == '0') {
                                                if (dayTyped.equals(String.valueOf(checkTime[2].charAt(1))))
                                                    found = true;
                                                else
                                                    found = false;
                                            } else {
                                                if (dayTyped.equals(checkTime[2]))
                                                    found = true;
                                                else
                                                    found = false;
                                            }
                                        }

                                        //verify the hour
                                        if (!hourTyped.equals("") && found == true) {
                                            if (checkDate[0].charAt(0) == '0') {
                                                if (hourTyped.equals(String.valueOf(checkDate[0].charAt(1))))
                                                    found = true;
                                                else
                                                    found = false;
                                            } else {
                                                if (hourTyped.equals(checkDate[0]))
                                                    found = true;
                                                else
                                                    found = false;
                                            }
                                        }

                                        //verify the minutes
                                        if (!minuteTyped.equals("") && found == true) {
                                            if (checkDate[1].charAt(0) == '0') {
                                                if (minuteTyped.equals(String.valueOf(checkDate[1].charAt(1))))
                                                    found = true;
                                                else
                                                    found = false;
                                            } else {
                                                if (minuteTyped.equals(checkDate[1]))
                                                    found = true;
                                                else
                                                    found = false;
                                            }
                                        }

                                        //verify the seconds
                                        if (!secondTyped.equals("") && found == true) {
                                            if (checkDate[2].charAt(0) == '0') {
                                                if (secondTyped.equals(String.valueOf(checkDate[2].charAt(1))))
                                                    found = true;
                                                else
                                                    found = false;
                                            } else {
                                                if (secondTyped.equals(checkDate[2]))
                                                    found = true;
                                                else
                                                    found = false;
                                            }
                                        }
                                    }

                                    //verify if the demand number mach
                                    if (!demandNumberTyped.equals("") && found == true)
                                        if (demandNumberTyped.equals(reader.get(2)))
                                            found = true;
                                        else
                                            found = false;

                                    //verify if the frequency mach
                                    if (!frequencyIdTyped.equals("") && found == true)
                                        if (frequencyIdTyped.equals(reader.equals(reader.get(3))))
                                            found = true;
                                        else
                                            found = false;

                                    //verify the coal
                                    if (!coalTyped.equals("") && found == true)
                                        if (coalTyped.equals(reader.get(4)))
                                            found = true;
                                        else
                                            found = false;

                                    //verify the nuclear
                                    if (!nuclearTyped.equals("") && found == true)
                                        if (nuclearTyped.equals(reader.get(5)))
                                            found = true;
                                        else
                                            found = false;

                                    //verify the CCGT
                                    if (!ccgtTyped.equals("") && found == true)
                                        if (ccgtTyped.equals(reader.get(6)))
                                            found = true;
                                        else
                                            found = false;

                                    //verify the wind
                                    if (!windTyped.equals("") && found == true)
                                        if (windTyped.equals(reader.get(7)))
                                            found = true;
                                        else
                                            found = false;

                                    //verify the french_ict
                                    if (!french_ictTyped.equals("") && found == true)
                                        if (french_ictTyped.equals(reader.get(8)))
                                            found = true;
                                        else
                                            found = false;

                                    //verify the dutch_ict
                                    if (!dutch_ictTyped.equals("") && found == true)
                                        if (dutch_ictTyped.equals(reader.get(9)))
                                            found = true;
                                        else
                                            found = false;

                                    //verify the irish_ict
                                    if (!irish_ictTyped.equals("") && found == true)
                                        if (irish_ictTyped.equals(reader.get(10)))
                                            found = true;
                                        else
                                            found = false;

                                    //verify the ew_ict
                                    if (!ew_ictTyped.equals("") && found == true)
                                        if (ew_ictTyped.equals(reader.get(11)))
                                            found = true;
                                        else
                                            found = false;

                                    //verify the pumped
                                    if (!pumpedTyped.equals("") && found == true)
                                        if (pumpedTyped.equals(reader.get(12)))
                                            found = true;
                                        else
                                            found = false;

                                    //verify the hydro
                                    if (!hydroTyped.equals("") && found == true)
                                        if (hydroTyped.equals(reader.get(13)))
                                            found = true;
                                        else
                                            found = false;

                                    //verify the oil
                                    if (!oilTyped.equals("") && found == true)
                                        if (oilTyped.equals(reader.get(14)))
                                            found = true;
                                        else
                                            found = false;

                                    //verify the OCGT
                                    if (!ocgtTyped.equals("") && found == true)
                                        if (ocgtTyped.equals(reader.get(15)))
                                            found = true;
                                        else
                                            found = false;

                                    //verify the other
                                    if (!otherTyped.equals("") && found == true)
                                        if (otherTyped.equals(reader.get(16)))
                                            found = true;
                                        else
                                            found = false;

                                    //verify the solar
                                    if (!solarTyped.equals("") && found == true)
                                        if (solarTyped.equals(reader.get(17)))
                                            found = true;
                                        else
                                            found = false;

                                    //verify if all the search criteria mach for the selected record
                                    if (found == true)
                                    {
                                        String []row= reader.getValues();
                                        results.add(row);
                                        numberOfResults++;
                                        allDataSearch.add(reader.getRawRecord());
                                    }
                                }
                            }
                        }
                        else
                            //the id is not selected search by all the other selected criteria
                            while(reader.readRecord())
                            {
                                found=true;

                                //verify if the timestamp is selected
                                if (!yearTyped.equals("") || !monthTyped.equals("") || !dayTyped.equals("") || !hourTyped.equals("") || !minuteTyped.equals("") || !secondTyped.equals(""))
                                {
                                    String[] checkTime = reader.get(1).split("-");
                                    String[] dayToDate = checkTime[2].split(" ");
                                    checkTime[2] = dayToDate[0];
                                    String[] checkDate = dayToDate[1].split(":");

                                    //verify the year
                                    if (!yearTyped.equals("")) {
                                        if (yearTyped.equals(checkTime[0]))
                                            found = true;
                                        else
                                            found = false;
                                    }

                                    //verify the month
                                    if (!monthTyped.equals("") && found == true) {
                                        if (checkTime[1].charAt(0) == '0') {
                                            if (monthTyped.equals(String.valueOf(checkTime[1].charAt(1))))
                                                found = true;
                                            else
                                                found = false;
                                        } else {
                                            if (monthTyped.equals(checkTime[1]))
                                                found = true;
                                            else
                                                found = false;
                                        }
                                    }

                                    //verify the day
                                    if (!dayTyped.equals("") && found == true) {
                                        if (checkTime[2].charAt(0) == '0') {
                                            if (dayTyped.equals(String.valueOf(checkTime[2].charAt(1))))
                                                found = true;
                                            else
                                                found = false;
                                        } else {
                                            if (dayTyped.equals(checkTime[2]))
                                                found = true;
                                            else
                                                found = false;
                                        }
                                    }

                                    //verify the hour
                                    if (!hourTyped.equals("") && found == true) {
                                        if (checkDate[0].charAt(0) == '0') {
                                            if (hourTyped.equals(String.valueOf(checkDate[0].charAt(1))))
                                                found = true;
                                            else
                                                found = false;
                                        } else {
                                            if (hourTyped.equals(checkDate[0]))
                                                found = true;
                                            else
                                                found = false;
                                        }
                                    }

                                    //verify the minute
                                    if (!minuteTyped.equals("") && found == true) {
                                        if (checkDate[1].charAt(0) == '0') {
                                            if (minuteTyped.equals(String.valueOf(checkDate[1].charAt(1))))
                                                found = true;
                                            else
                                                found = false;
                                        } else {
                                            if (minuteTyped.equals(checkDate[1]))
                                                found = true;
                                            else
                                                found = false;
                                        }
                                    }

                                    //verify the second
                                    if (!secondTyped.equals("") && found == true) {
                                        if (checkDate[2].charAt(0) == '0') {
                                            if (secondTyped.equals(String.valueOf(checkDate[2].charAt(1))))
                                                found = true;
                                            else
                                                found = false;
                                        } else {
                                            if (secondTyped.equals(checkDate[2]))
                                                found = true;
                                            else
                                                found = false;
                                        }
                                    }
                                }

                                //verify the demand number
                                if (!demandNumberTyped.equals("") && found == true)
                                    if (demandNumberTyped.equals(reader.get(2)))
                                        found = true;
                                    else
                                        found = false;


                                //verify the frequncy

                                if (!frequencyIdTyped.equals("") && found == true)
                                    if (frequencyIdTyped.equals(reader.get(3)))
                                        found = true;
                                    else
                                        found = false;

                                //verify the coal
                                if (!coalTyped.equals("") && found == true)
                                    if (coalTyped.equals(reader.get(4)))
                                        found = true;
                                    else
                                        found = false;

                                //verify the nuclear
                                if (!nuclearTyped.equals("") && found == true)
                                    if (nuclearTyped.equals(reader.get(5)))
                                        found = true;
                                    else
                                        found = false;

                                //verify the CCGT
                                if (!ccgtTyped.equals("") && found == true)
                                    if (ccgtTyped.equals(reader.get(6)))
                                        found = true;
                                    else
                                        found = false;

                                //verify the wind
                                if (!windTyped.equals("") && found == true)
                                    if (windTyped.equals(reader.get(7)))
                                        found = true;
                                    else
                                        found = false;

                                //verify the french_ict
                                if (!french_ictTyped.equals("") && found == true)
                                    if (french_ictTyped.equals(reader.get(8)))
                                        found = true;
                                    else
                                        found = false;

                                //verify the dutch_ict
                                if (!dutch_ictTyped.equals("") && found == true)
                                    if (dutch_ictTyped.equals(reader.get(9)))
                                        found = true;
                                    else
                                        found = false;

                                //verify the irish_ict
                                if (!irish_ictTyped.equals("") && found == true)
                                    if (irish_ictTyped.equals(reader.get(10)))
                                        found = true;
                                    else
                                        found = false;

                                //verify the ew_ict
                                if (!ew_ictTyped.equals("") && found == true)
                                    if (ew_ictTyped.equals(reader.get(11)))
                                        found = true;
                                    else
                                        found = false;

                                //verify the pumped
                                if (!pumpedTyped.equals("") && found == true)
                                    if (pumpedTyped.equals(reader.get(12)))
                                        found = true;
                                    else
                                        found = false;

                                //verify the hydro
                                if (!hydroTyped.equals("") && found == true)
                                    if (hydroTyped.equals(reader.get(13)))
                                        found = true;
                                    else
                                        found = false;

                                //verify the oil
                                if (!oilTyped.equals("") && found == true)
                                    if (oilTyped.equals(reader.get(14)))
                                        found = true;
                                    else
                                        found = false;

                                //verify the OCGT
                                if (!ocgtTyped.equals("") && found == true)
                                    if (ocgtTyped.equals(reader.get(15)))
                                        found = true;
                                    else
                                        found = false;

                                //verify the other
                                if (!otherTyped.equals("") && found == true)
                                    if (otherTyped.equals(reader.get(16)))
                                        found = true;
                                    else
                                        found = false;

                                //verify the solar
                                if (!solarTyped.equals("") && found == true)
                                    if (solarTyped.equals(reader.get(17)))
                                        found = true;
                                    else
                                        found = false;

                                //verify the any records has been found with the selected criteria
                                //add the found record
                                if (found == true)
                                {
                                    allDataSearch.add(reader.getRawRecord());
                                    String []row= reader.getValues();
                                    results.add(row);
                                    numberOfResults++;
                                }
                            }

                        //verify any records has been found and print the results
                        if (numberOfResults>0)
                        {//create display table for the results
                            if (MainFrame.nightOn)
                                message.setForeground(YELLOW);
                            else
                                message.setForeground(null);
                            JDialog resultFrame=new JDialog();
                            sUsed = true;
                            PostDataSnippet.pUsed = false;

                            resultFrame.addWindowListener(new WindowListener() {
                                @Override
                                public void windowOpened(WindowEvent e) {
                                    MainFrame.cardLayout.show(MainFrame.cardPanel, "10"); // displays PostDataSnippet
                                }

                                @Override
                                public void windowClosing(WindowEvent e) {
                                }

                                @Override
                                public void windowClosed(WindowEvent e) {

                                }

                                @Override
                                public void windowIconified(WindowEvent e) {

                                }

                                @Override
                                public void windowDeiconified(WindowEvent e) {

                                }

                                @Override
                                public void windowActivated(WindowEvent e) {

                                }

                                @Override
                                public void windowDeactivated(WindowEvent e) {

                                }
                            });

                            //adding export button to the dialog, on the bottom
                            JPanel buttonPanel = new JPanel(new FlowLayout());
                            resultFrame.add(buttonPanel, BorderLayout.SOUTH);
                            JButton close = new JButton("Export as PDF");
                            buttonPanel.add(close);

                            DefaultTableModel modelResultTable = new DefaultTableModel();
                            JTable resultTable=new JTable(modelResultTable);

                            //create action listener for the button
                            close.addActionListener( new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e)
                                { //creating the PDF in the local folder

                                    Document doc = new Document(PageSize.A4.rotate(), 10f, 10f, 100f, 0f);
                                    try {

                                        PdfWriter.getInstance(doc, new FileOutputStream("Search.pdf"));
                                        doc.open();
                                        PdfPTable pdfTable = new PdfPTable(resultTable.getColumnCount());
                                        //adding table headers
                                        for (int i = 0; i < resultTable.getColumnCount(); i++) {
                                            pdfTable.addCell(resultTable.getColumnName(i));
                                        }
                                        //extracting data from the JTable and inserting it to PdfPTable
                                        for (int rows = 0; rows < resultTable.getRowCount() ; rows++) {
                                            for (int cols = 0; cols < resultTable.getColumnCount(); cols++) {
                                                pdfTable.addCell(resultTable.getModel().getValueAt(rows, cols).toString());

                                            }
                                        }
                                        doc.add(pdfTable);

                                    }
                                    catch (Exception ex) {
                                        ex.printStackTrace();
                                    }
                                    finally{
                                        if(doc.isOpen()){
                                            doc.close();
                                        }
                                    }
                                }
                            });

                            modelResultTable.addColumn("id");
                            modelResultTable.addColumn("timestamp");
                            modelResultTable.addColumn("demand");
                            modelResultTable.addColumn("frequency");
                            modelResultTable.addColumn("coal");
                            modelResultTable.addColumn("nuclear");
                            modelResultTable.addColumn("ccgt");
                            modelResultTable.addColumn("wind");
                            modelResultTable.addColumn("french_ict");
                            modelResultTable.addColumn("dutch_ict");
                            modelResultTable.addColumn("irish_ict");
                            modelResultTable.addColumn("ew_ict");
                            modelResultTable.addColumn("pumped");
                            modelResultTable.addColumn("hydro");
                            modelResultTable.addColumn("oil");
                            modelResultTable.addColumn("hydro");
                            modelResultTable.addColumn("other");
                            modelResultTable.addColumn("solar");

                            for (int i=0;i<results.size();i++)
                            {
                                String [] row=results.get(i);
                                modelResultTable.addRow(row);

                            }

                            JScrollPane sp = new JScrollPane(resultTable);
                            resultFrame.setModal(true);
                            resultFrame.setSize(1300,450);
                            resultFrame.add(sp);
                            resultFrame.setVisible(true);
                            resultFrame.setTitle("Searched Data Results");


                            message.setText("\n\n\nPlease select the search criteria:");

                            //save oll the search criteria
                            String[] searchHystoryDetails=new String[23];

                            if(idTyped.length()==0)
                                idTyped="";
                            searchHystoryDetails[0]=idTyped;

                            if(yearTyped.length()==0)
                                yearTyped="";
                            searchHystoryDetails[1]=yearTyped;

                            if(monthTyped.length()==0)
                                monthTyped="";
                            searchHystoryDetails[2]=monthTyped;

                            if(dayTyped.length()==0)
                                dayTyped="";
                            searchHystoryDetails[3]=dayTyped;

                            if(hourTyped.length()==0)
                                hourTyped="";
                            searchHystoryDetails[4]=hourTyped;

                            if(minuteTyped.length()==0)
                                minuteTyped="";
                            searchHystoryDetails[5]=minuteTyped;

                            if(secondTyped.length()==0)
                                secondTyped="";
                            searchHystoryDetails[6]=secondTyped;

                            if(demandNumberTyped.length()==0)
                                demandNumberTyped="";
                            searchHystoryDetails[7]=demandNumberTyped;

                            if(frequencyIdTyped.length()==0)
                                frequencyIdTyped="";
                            searchHystoryDetails[8]=frequencyIdTyped;

                            if(coalTyped.length()==0)
                                coalTyped="";
                            searchHystoryDetails[9]=coalTyped;

                            if(nuclearTyped.length()==0)
                                nuclearTyped="";
                            searchHystoryDetails[10]=nuclearTyped;

                            if(ccgtTyped.length()==0)
                                ccgtTyped="";
                            searchHystoryDetails[11]=ccgtTyped;

                            if(windTyped.length()==0)
                                windTyped="";
                            searchHystoryDetails[12]=windTyped;

                            if(french_ictTyped.length()==0)
                                french_ictTyped="";
                            searchHystoryDetails[13]=french_ictTyped;

                            if(dutch_ictTyped.length()==0)
                                dutch_ictTyped="";
                            searchHystoryDetails[14]=dutch_ictTyped;

                            if(irish_ictTyped.length()==0)
                                irish_ictTyped="";
                            searchHystoryDetails[15]=irish_ictTyped;

                            if(ew_ictTyped.length()==0)
                                ew_ictTyped="";
                            searchHystoryDetails[16]=ew_ictTyped;

                            if(pumpedTyped.length()==0)
                                pumpedTyped="";
                            searchHystoryDetails[17]=pumpedTyped;

                            if(hydroTyped.length()==0)
                                hydroTyped="";
                            searchHystoryDetails[18]=hydroTyped;

                            if(oilTyped.length()==0)
                                oilTyped="";
                            searchHystoryDetails[19]=oilTyped;

                            if(ocgtTyped.length()==0)
                                ocgtTyped="";
                            searchHystoryDetails[20]=ocgtTyped;

                            if(otherTyped.length()==0)
                                otherTyped="";
                            searchHystoryDetails[21]=otherTyped;

                            if(solarTyped.length()==0)
                                solarTyped="";
                            searchHystoryDetails[22]=solarTyped;

                            hystory.add(searchHystoryDetails);

                        }
                        else
                        {
                            //message if no records are found
                            message.setText("\n\nNo Records Found!\nPlease select the search criteria:");
                            message.setForeground(red);

                        }

                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                }
                else
                    {
                        //message if no records are found
                        message.setText("\n\nSearch criteria are selected wrong!\nPlease select other search criteria:");
                        message.setForeground(red);
                    }}


                PostDataSnippet.snippedData = new ArrayList<String>();
                PostDataSnippet.snippedData = allDataSearch;
                allDataSearch = new ArrayList<String>();

                coal.setText("");
                nuclear.setText("");
                CCGT.setText("");
                wind.setText("");
                french_ict.setText("");
                dutch_ict.setText("");
                irish_ict.setText("");
                ew_ict.setText("");
                pumped.setText("");
                hydro.setText("");
                oil.setText("");
                ocgt.setText("");
                other.setText("");
                solar.setText("");

                hour.setText("");
                minute.setText("");
                second.setText("");
                year.setText("");
                month.setText("");
                day.setText("");

                idNumber.setText("");
                demandNumber.setText("");
                frequencyNumber.setText("");

                idTyped="";
                demandNumberTyped="";
                frequencyIdTyped="";
                energyTypeSelected="";

                //timestamp
                yearTyped="";
                monthTyped="";
                dayTyped="";
                hourTyped="";
                minuteTyped="";
                secondTyped="";

                //energy type
                coalTyped="";
                nuclearTyped="";
                ccgtTyped="";
                windTyped="";
                french_ictTyped="";
                dutch_ictTyped="";
                irish_ictTyped="";
                ew_ictTyped="";
                pumpedTyped="";
                hydroTyped="";
                oilTyped="";
                ocgtTyped="";
                otherTyped="";
                solarTyped="";


            }
        });
    }}