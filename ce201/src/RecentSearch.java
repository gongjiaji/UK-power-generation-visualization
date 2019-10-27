package ce201.src;

import com.csvreader.CsvReader;
import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.awt.Dimension;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import static java.awt.Color.BLACK;
import static java.awt.Color.RED;
import static java.awt.Color.YELLOW;


public class RecentSearch extends JPanel{

    static JButton back;
    static JButton searchHistory;
    static JButton snipHistory;
    static JButton clearHistory;
    static JTextField title;
    static JTextField message;
    static JPanel messageRestrictor;
    static JPanel buttonRestrictor;

    public static ArrayList<String> historyDataSearched=new ArrayList<>();

    public RecentSearch()
    {
        this.setLayout(MainFrame.standardLayout);

        //creating the search and back jbuttons and adding them to the button

        back = new JButton("Back to Main Menu");
        back.setPreferredSize(new Dimension(180,30));

        searchHistory=new JButton("Search history");
        searchHistory.setPreferredSize(new Dimension(180,30));

        snipHistory=new JButton("Snippet history");
        snipHistory.setPreferredSize(new Dimension(180,30));

        clearHistory=new JButton("Clear history");
        clearHistory.setPreferredSize(new Dimension(180,30));

        //title and message
        title = new JTextField("History");
        message = new JTextField("Please select one of the saved data");

        messageRestrictor = new JPanel();
        messageRestrictor.add(message);

        title.setFont(MainFrame.titleFont);
        title.setHorizontalAlignment(JTextField.CENTER);
        title.setEditable(false);
        title.setBorder(BorderFactory.createEmptyBorder());

        message.setFont(MainFrame.messageFont);
        message.setHorizontalAlignment(JTextField.CENTER);
        message.setEditable(false);
        message.setBorder(BorderFactory.createEmptyBorder());
        message.setOpaque(false);

        buttonRestrictor = new JPanel();
        buttonRestrictor.add(searchHistory);
        buttonRestrictor.add(snipHistory);
        buttonRestrictor.add(clearHistory);

        add(title);
        add(message);
        add(buttonRestrictor);
        add(back);

        ButtonHandler proceedAction = new ButtonHandler(5); // sends user to the Main Menu panel
        back.addActionListener(proceedAction);

        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (MainFrame.nightOn)
                    message.setForeground(YELLOW);
                else
                    message.setForeground(null);
            }
        });

        //clear History
        clearHistory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (SearchForData.hystory.isEmpty() && DataSnippet.history.isEmpty()) {
                    message.setText("History already empty.");
                    message.setForeground(RED);
                }
                else {
                    SearchForData.hystory=new ArrayList<String[]>();
                    DataSnippet.history=new ArrayList<String[]>();
                    message.setText("History cleared.");
                    if (MainFrame.nightOn)
                        message.setForeground(YELLOW);
                    else
                        message.setForeground(null);
                }
            }
        });

        //History of the SearchForData calss
        searchHistory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                if (SearchForData.hystory.isEmpty()) {
                    message.setText("No history available!");
                    message.setForeground(RED);
                }

                else {
                    message.setText("Please select one of the saved data");
                    if (MainFrame.nightOn)
                        message.setForeground(YELLOW);
                    else
                        message.setForeground(null);
                    //create the table with the search history table
                    JDialog resultFrame=new JDialog();

                    DefaultTableModel modelResultTable = new DefaultTableModel();
                    JTable resultTable=new JTable(modelResultTable);

                    modelResultTable.addColumn("id");
                    modelResultTable.addColumn("year");
                    modelResultTable.addColumn("month");
                    modelResultTable.addColumn("day");
                    modelResultTable.addColumn("hour");
                    modelResultTable.addColumn("minute");
                    modelResultTable.addColumn("second");
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


                    for (int i=0;i<SearchForData.hystory.size();i++)
                        modelResultTable.addRow(SearchForData.hystory.get(i));
                    //check what history choice is selected

                    JScrollPane sp = new JScrollPane(resultTable);
                    resultFrame.add(sp);
                    resultFrame.setSize(1300,450);
                    resultFrame.setVisible(true);
                    resultFrame.setTitle("Searched Data Results");

                    resultTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                        @Override
                        public void valueChanged(ListSelectionEvent e) {

                            if (!e.getValueIsAdjusting())
                            {
                                //set values

                                //id and frequency
                                String idTyped=resultTable.getValueAt(resultTable.getSelectedRow(),0).toString();
                                String demandNumberTyped=resultTable.getValueAt(resultTable.getSelectedRow(),7).toString();
                                String frequencyIdTyped=resultTable.getValueAt(resultTable.getSelectedRow(),8).toString();

                                //timestamp
                                String yearTyped=resultTable.getValueAt(resultTable.getSelectedRow(),1).toString();
                                String monthTyped=resultTable.getValueAt(resultTable.getSelectedRow(),2).toString();
                                String dayTyped=resultTable.getValueAt(resultTable.getSelectedRow(),3).toString();
                                String hourTyped=resultTable.getValueAt(resultTable.getSelectedRow(),4).toString();
                                String minuteTyped=resultTable.getValueAt(resultTable.getSelectedRow(),5).toString();
                                String secondTyped=resultTable.getValueAt(resultTable.getSelectedRow(),6).toString();

                                //energy type
                                String coalTyped=resultTable.getValueAt(resultTable.getSelectedRow(),9).toString();
                                String nuclearTyped=resultTable.getValueAt(resultTable.getSelectedRow(),10).toString();
                                String ccgtTyped=resultTable.getValueAt(resultTable.getSelectedRow(),11).toString();
                                String windTyped=resultTable.getValueAt(resultTable.getSelectedRow(),12).toString();
                                String french_ictTyped=resultTable.getValueAt(resultTable.getSelectedRow(),13).toString();
                                String dutch_ictTyped=resultTable.getValueAt(resultTable.getSelectedRow(),14).toString();
                                String irish_ictTyped=resultTable.getValueAt(resultTable.getSelectedRow(),15).toString();
                                String ew_ictTyped=resultTable.getValueAt(resultTable.getSelectedRow(),16).toString();
                                String pumpedTyped=resultTable.getValueAt(resultTable.getSelectedRow(),17).toString();
                                String hydroTyped=resultTable.getValueAt(resultTable.getSelectedRow(),18).toString();
                                String oilTyped=resultTable.getValueAt(resultTable.getSelectedRow(),19).toString();
                                String ocgtTyped=resultTable.getValueAt(resultTable.getSelectedRow(),20).toString();
                                String otherTyped=resultTable.getValueAt(resultTable.getSelectedRow(),21).toString();
                                String solarTyped=resultTable.getValueAt(resultTable.getSelectedRow(),22).toString();

                                //search for the selected history record
                                CsvReader reader = null;
                                ArrayList<String> allDataSearch = new ArrayList<>();

                                try {
                                    reader=new CsvReader(ButtonHandler.filepath);
                                } catch (FileNotFoundException e1) {
                                    e1.printStackTrace();
                                }

                                try {
                                    //initializing an array list to get the search results
                                    reader.readHeaders();
                                    boolean found=false;
                                    ArrayList<String[]> results = new ArrayList<String[]>();
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
                                                    numberOfResults++;;
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
                                                historyDataSearched.add(reader.getRawRecord());
                                                numberOfResults++;
                                            }
                                        }

                                    //verify any records has been found and print the results
                                    if (numberOfResults>0)
                                    {//create display table for the results
                                        JDialog finalResultFrame=new JDialog();

                                        //adding export button to the dialog, on the bottom
                                        JPanel buttonPanel = new JPanel(new FlowLayout());
                                        finalResultFrame.add(buttonPanel, BorderLayout.SOUTH);
                                        JButton close = new JButton("Export as PDF");
                                        buttonPanel.add(close);

                                        DefaultTableModel modelResultTable = new DefaultTableModel();
                                        JTable resultTable=new JTable(modelResultTable);

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

                                        JScrollPane sp = new JScrollPane(resultTable);
                                        finalResultFrame.add(sp);
                                        finalResultFrame.setVisible(true);
                                        finalResultFrame.setSize(1300,450);
                                        finalResultFrame.setTitle("Searched Data Results");


                                        for (int i=0;i<results.size();i++)
                                        {
                                            String [] row=results.get(i);
                                            modelResultTable.addRow(row);
                                        }


                                        //save and sent the records to the graph section
                                        PostDataSnippet.snippedData = new ArrayList<String>();
                                        PostDataSnippet.snippedData = historyDataSearched;
                                        historyDataSearched=new ArrayList<>();

                                        finalResultFrame.addWindowListener(new WindowListener() {
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


                                        //create action listener for the button
                                        close.addActionListener( new ActionListener() {
                                            @Override
                                            public void actionPerformed(ActionEvent e)
                                            { //creating the PDF in the local folder

                                                Document doc = new Document(PageSize.A4.rotate(), 10f, 10f, 100f, 0f);
                                                try {

                                                    PdfWriter.getInstance(doc, new FileOutputStream("SearchHistory.pdf"));
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
                                    }
                                } catch (IOException e1) {
                                    e1.printStackTrace();
                                }
                            }
                        }
                });
            }
        }});
        //get value of the previous snip searches

        snipHistory.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (DataSnippet.history.isEmpty()) {
                    message.setText("No history available!");
                    message.setForeground(RED);
                }
                else {
                    message.setText("Please select one of the saved data");
                    if (MainFrame.nightOn)
                        message.setForeground(YELLOW);
                    else
                        message.setForeground(null);
                    //create the table with the snip History
                    JDialog snipSearchedHistory=new JDialog();
                    DefaultTableModel modelResultTable = new DefaultTableModel();
                    JTable snipResultTable=new JTable(modelResultTable);

                    modelResultTable.addColumn("Start Date");
                    modelResultTable.addColumn("End Date");
                    JScrollPane sp = new JScrollPane(snipResultTable);

                    snipSearchedHistory.add(sp);
                    snipSearchedHistory.setVisible(true);
                    snipSearchedHistory.setSize(350,200);
                    snipSearchedHistory.setTitle("Snip Data Results History");

                    for (int i=0;i<DataSnippet.history.size();i++)
                        modelResultTable.addRow(DataSnippet.history.get(i));

                    //search by the previous criteria

                    snipResultTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
                        @Override
                        public void valueChanged(ListSelectionEvent e) {
                            if (!e.getValueIsAdjusting())
                            {
                                //create the table for displaying results
                                JDialog resultFrame=new JDialog();

                                //adding export button to the dialog, on the bottom
                                JPanel buttonPanel = new JPanel(new FlowLayout());
                                resultFrame.add(buttonPanel, BorderLayout.SOUTH);
                                JButton as = new JButton("Export as PDF");
                                buttonPanel.add(as);

                                DefaultTableModel modelResultTable = new DefaultTableModel();
                                JTable resultTable=new JTable(modelResultTable);

                                as.addActionListener( new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e)
                                    {//creating the PDF in the local folder
                                        Document doc = new Document(PageSize.A4.rotate(), 10f, 10f, 100f, 0f);
                                        try {

                                            PdfWriter.getInstance(doc, new FileOutputStream("SnippetHistory.pdf"));
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

                                        } catch (Exception ex) {
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

                                JScrollPane sp = new JScrollPane(resultTable);
                                resultFrame.add(sp);
                                resultFrame.setVisible(true);
                                resultFrame.setSize(1300,450);
                                resultFrame.setTitle("Searched Data Results");

                                //get the start and end date for search
                                String[] startDate=snipResultTable.getValueAt(snipResultTable.getSelectedRow(),0).toString().split("-");
                                String[] endDate=snipResultTable.getValueAt(snipResultTable.getSelectedRow(),1).toString().split("-");

                                //getting the reader for the search
                                CsvReader reader=null;

                                try {
                                    reader=new CsvReader(ButtonHandler.filepath);
                                } catch (FileNotFoundException e1) {
                                    e1.printStackTrace();
                                }


                                try {
                                    //initializing an array list to get the search results
                                    reader.readHeaders();

                                    boolean fond=false;
                                    ArrayList<String[]> results=new ArrayList<>();

                                    //start search trough the file
                                    while (reader.readRecord() && fond==false)
                                    {
                                        //get the records beteen the start and end date
                                        String[] time=reader.get(1).split(" ");
                                        String[] date=time[0].split("-");
                                        if (Integer.parseInt(startDate[0])<=Integer.parseInt(date[0]) && Integer.parseInt(startDate[1])<=Integer.parseInt(date[1]) && Integer.parseInt(startDate[2])<=Integer.parseInt(date[2]) && Integer.parseInt(endDate[0])>=Integer.parseInt(date[0]) && Integer.parseInt(endDate[1])>=Integer.parseInt(date[1]) && Integer.parseInt(endDate[2])>=Integer.parseInt(date[2]))
                                        {
                                            //add the records on the table
                                            String []row= reader.getValues();
                                            modelResultTable.addRow(row);
                                            historyDataSearched.add(reader.getRawRecord());
                                        }
                                    }

                                    //save and sent the records to the graph section
                                    PostDataSnippet.snippedData = new ArrayList<String>();
                                    PostDataSnippet.snippedData = historyDataSearched;
                                    historyDataSearched=new ArrayList<>();

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

                                } catch (IOException e1) {
                                    e1.printStackTrace();
                                }
                            }
                        }
                    });
                }
            }
        });
    }
}
