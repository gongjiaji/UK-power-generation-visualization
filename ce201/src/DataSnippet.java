package ce201.src;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class DataSnippet extends JPanel {

    static ArrayList<String> allData = new ArrayList<>();
    static UtilDateModel startModel;
    static UtilDateModel endModel;
    static JTextField message;
    static JTextField title;
    static String fileStartRange;
    static String fileEndRange;

    static JButton execute;
    static JButton back;
    static JPanel messageRestrictor;
    static JPanel contentRestrictor;
    static JDatePickerImpl startDate;
    static JDatePickerImpl endDate;
    static Properties startProperties;
    static Properties endProperties;

    //history of the sniped data
    public static ArrayList<String[]> history=new ArrayList<>();

    static JDatePanelImpl endPanel;
    static JDatePanelImpl startPanel;

    public DataSnippet() {

        // Basic characteristics and components defined.
        setLayout(MainFrame.standardLayout);

        fileStartRange = "";
        fileEndRange = "";

        title = new JTextField("Data snippet");
        message = new JTextField("Please select two dates to choose data from; data will then follow: ");

        execute = new JButton("Select data snippet");
        back = new JButton("Back to menu");

        title.setFont(MainFrame.titleFont);
        title.setHorizontalAlignment(JTextField.CENTER);
        title.setEditable(false);
        title.setBorder(BorderFactory.createEmptyBorder());

        message.setFont(MainFrame.messageFont);
        message.setHorizontalAlignment(JTextField.CENTER);
        message.setEditable(false);
        message.setBorder(BorderFactory.createEmptyBorder());

        startProperties = new Properties();
        startProperties.put("text.day", "Day");
        startProperties.put("text.month", "Month");
        startProperties.put("text.year", "Year");
        startModel = new UtilDateModel();
        startPanel = new JDatePanelImpl(startModel,startProperties);
        startPanel.setVisible(false);

        endProperties = new Properties();
        endProperties.put("text.day", "Day");
        endProperties.put("text.month", "Month");
        endProperties.put("text.year", "Year");
        endModel = new UtilDateModel();
        endPanel = new JDatePanelImpl(endModel,endProperties);

        startDate = new JDatePickerImpl(startPanel,new DateFormatter());
        endDate = new JDatePickerImpl(endPanel,new DateFormatter());
        // JPanels are used to enclose components; this allows for an organised layout and format.

        messageRestrictor = new JPanel();
        messageRestrictor.add(message);

        contentRestrictor = new JPanel();
        contentRestrictor.setLayout(new GridLayout(2,4));
        contentRestrictor.add(startDate);
        contentRestrictor.add(endDate);

        JPanel buttonRestrictor = new JPanel();
        buttonRestrictor.setLayout(new GridLayout(2,4));
        buttonRestrictor.add(execute);
        buttonRestrictor.add(back);

        // Adds all created components to the main JPanel

        add(title);
        add(messageRestrictor);
        add(contentRestrictor);
        add(buttonRestrictor);

        ButtonHandler backAction = new ButtonHandler(5); // sends user to the Main Menu panel
        back.addActionListener(backAction);

        ButtonHandler executeAction = new ButtonHandler(10); // sends user to the Main Menu panel
        execute.addActionListener(executeAction);
        execute.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (MainFrame.nightOn)
                    message.setForeground(Color.YELLOW);
                else
                    message.setForeground(null);
                enclosingFunction();
            }
        });

        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (MainFrame.nightOn)
                    message.setForeground(Color.YELLOW);
                else
                    message.setForeground(null);
            }
        });

    }

    public static long getMaxDate() throws ParseException {
        long days = 0;
        String tempDate = "";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        for (String line : allData) {
            String[] splitrow = line.split("\\s");
            Date d1 = dateFormat.parse(splitrow[1]);
            long temp1 = d1.getTime();
            long temp = TimeUnit.DAYS.convert(temp1, TimeUnit.MILLISECONDS);
            if (temp > days) {
                days = temp;
                tempDate = splitrow[1];
            }
        }
        fileEndRange = tempDate;
        return days;
    }

    public static long getMinDate() throws ParseException {
        long days = Integer.MAX_VALUE;
        String tempDate = "";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        for (String line : allData) {
            String[] splitrow = line.split("\\s");
            Date d1 = dateFormat.parse(splitrow[1]);
            long temp1 = d1.getTime();
            long temp = TimeUnit.DAYS.convert(temp1, TimeUnit.MILLISECONDS);
            if (temp < days) {
                days = temp;
                tempDate = splitrow[1];
            }
        }
        fileStartRange = tempDate;
        return days;
    }

    public static void enclosingFunction()  {

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {

            long max = getMaxDate();
            long min = getMinDate();

            String startDate = dateFormat.format(startModel.getValue());
            String endDate = dateFormat.format(endModel.getValue());

            Date d1 = dateFormat.parse(startDate);
            Date d2 = dateFormat.parse(endDate);
            long d11 = d1.getTime();
            long d22 = d2.getTime();
            long diff = d22 - d11;

            long dayDifference = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
            long startDays = TimeUnit.DAYS.convert(d11, TimeUnit.MILLISECONDS);
            long endDays = TimeUnit.DAYS.convert(d22, TimeUnit.MILLISECONDS);

            // Validate the input

            boolean validate = false;
            boolean outOfRange = false;
            if (startDays >= min && endDays <= max) {
                if (dayDifference <= 7 && dayDifference >= 0) {
                    validate = true;
                }
            }
            else {
                outOfRange = true;
            }

            if (validate) {

                message.setText("Please select two dates to choose data from; data will then follow: ");
                message.setHorizontalAlignment(JTextField.CENTER);

                String[] historyDates=new String[2];
                historyDates[0]=startDate;
                historyDates[1]=endDate;
                history.add(historyDates);

                PostDataSnippet.snippedData = new ArrayList<String>();
                PostDataSnippet.snippedData = DataSnippet.organiseItems(startDate, endDate);
                PostDataSnippet.createTable();

                DataSnippet.startDate.getJFormattedTextField().setText("");
                DataSnippet.endDate.getJFormattedTextField().setText("");
                startModel.setValue(null);
                endModel.setValue(null);

//                startModel = new UtilDateModel();
//                endModel = new UtilDateModel();
//                startPanel = new JDatePanelImpl(startModel,startProperties);
//                endPanel = new JDatePanelImpl(endModel,endProperties);
//                DataSnippet.startDate = new JDatePickerImpl(startPanel,new DateFormatter());
//                DataSnippet.endDate = new JDatePickerImpl(endPanel,new DateFormatter());
//
//                System.out.println(startDate);
//                System.out.println(endDate);

            }
            else if (!validate) {
                if (outOfRange) {

                    message.setText("The range must be between " + fileStartRange + " and " + fileEndRange);
                    message.setForeground(Color.RED);
                    message.setHorizontalAlignment(JTextField.CENTER);
                }
                else {

                    message.setText("Range must be less than 7 days & start range <= end range");
                    message.setForeground(Color.RED);
                    message.setHorizontalAlignment(JTextField.CENTER);
                }
            }
        }
        catch (NullPointerException xe) {
            message.setText("Please select dates before attempting a snippet.");
            message.setForeground(Color.RED);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<String> organiseItems(String startDate, String endDate) {

        ArrayList<String> allData = DataSnippet.allData; // List to extract data from

        ArrayList<String> requestedSnippet = new ArrayList<>(); // List to populate
        StringBuilder currentIteration = new StringBuilder(startDate);
        boolean snippetComplete = false;

        while (!snippetComplete) {

            String temp = currentIteration.toString();

            for (String hamza : allData) {
                String[] splitrow = hamza.split("\\s+");

                if (splitrow[1].equals(temp)) {
                    requestedSnippet.add(hamza);
                }
            }

            if (temp.equals(endDate)) {
                snippetComplete = true;
            }

            else {

                String year = currentIteration.substring(0, 4);
                String month = currentIteration.substring(5, 7);
                String day = currentIteration.substring(8, 10);

                if (month.equals("01")) {
                    if (day.equals("31")) {
                        currentIteration.delete(5,7);
                        currentIteration.insert(5,"02");
                        currentIteration.delete(8,10);
                        currentIteration.insert(8,"01");
                    } else {
                        String finalint;
                        int intday = Integer.parseInt(day);
                        intday++;
                        finalint = String.valueOf(intday);
                        if (day.substring(0,1).equals("0") && intday < 10) {
                            finalint = "0" + finalint;
                        }
                        currentIteration.delete(8,10);
                        currentIteration.insert(8,finalint);
                    }
                }

                if (month.equals("02")) {
                    if (day.equals("28")) {
                        currentIteration.delete(5,7);
                        currentIteration.insert(5,"02");
                        currentIteration.delete(8,10);
                        currentIteration.insert(8,"01");
                    } else {
                        String finalint;
                        int intday = Integer.parseInt(day);
                        intday++;
                        finalint = String.valueOf(intday);
                        if (day.substring(0,1).equals("0") && intday < 10) {
                            finalint = "0" + finalint;
                        }
                        currentIteration.delete(8,10);
                        currentIteration.insert(8,finalint);
                    }
                }

                if (month.equals("03")) {
                    if (day.equals("31")) {
                        currentIteration.delete(5,7);
                        currentIteration.insert(5,"04");
                        currentIteration.delete(8,10);
                        currentIteration.insert(8,"01");
                    } else {
                        String finalint;
                        int intday = Integer.parseInt(day);
                        intday++;
                        finalint = String.valueOf(intday);
                        if (day.substring(0,1).equals("0") && intday < 10) {
                            finalint = "0" + finalint;
                        }
                        currentIteration.delete(8,10);
                        currentIteration.insert(8,finalint);
                    }
                }

                if (month.equals("04")) {
                    if (day.equals("30")) {
                        currentIteration.delete(5,7);
                        currentIteration.insert(5,"05");
                        currentIteration.delete(8,10);
                        currentIteration.insert(8,"01");
                    } else {
                        String finalint;
                        int intday = Integer.parseInt(day);
                        intday++;
                        finalint = String.valueOf(intday);
                        if (day.substring(0,1).equals("0") && intday < 10) {
                            finalint = "0" + finalint;
                        }
                        currentIteration.delete(8,10);
                        currentIteration.insert(8,finalint);
                    }
                }

                if (month.equals("05")) {
                    if (day.equals("31")) {
                        currentIteration.delete(5,7);
                        currentIteration.insert(5,"06");
                        currentIteration.delete(8,10);
                        currentIteration.insert(8,"01");
                    } else {
                        String finalint;
                        int intday = Integer.parseInt(day);
                        intday++;
                        finalint = String.valueOf(intday);
                        if (day.substring(0,1).equals("0") && intday < 10) {
                            finalint = "0" + finalint;
                        }
                        currentIteration.delete(8,10);
                        currentIteration.insert(8,finalint);
                    }
                }

                if (month.equals("06")) {
                    if (day.equals("30")) {
                        currentIteration.delete(5,7);
                        currentIteration.insert(5,"07");
                        currentIteration.delete(8,10);
                        currentIteration.insert(8,"01");
                    } else {
                        String finalint;
                        int intday = Integer.parseInt(day);
                        intday++;
                        finalint = String.valueOf(intday);
                        if (day.substring(0,1).equals("0") && intday < 10) {
                            finalint = "0" + finalint;
                        }
                        currentIteration.delete(8,10);
                        currentIteration.insert(8,finalint);
                    }
                }

                if (month.equals("07")) {
                    if (day.equals("31")) {
                        currentIteration.delete(5,7);
                        currentIteration.insert(5,"08");
                        currentIteration.delete(8,10);
                        currentIteration.insert(8,"01");
                    } else {
                        String finalint;
                        int intday = Integer.parseInt(day);
                        intday++;
                        finalint = String.valueOf(intday);
                        if (day.substring(0,1).equals("0") && intday < 10) {
                            finalint = "0" + finalint;
                        }
                        currentIteration.delete(8,10);
                        currentIteration.insert(8,finalint);
                    }
                }

                if (month.equals("08")) {
                    if (day.equals("31")) {
                        currentIteration.delete(5,7);
                        currentIteration.insert(5,"09");
                        currentIteration.delete(8,10);
                        currentIteration.insert(8,"01");
                    } else {
                        String finalint;
                        int intday = Integer.parseInt(day);
                        intday++;
                        finalint = String.valueOf(intday);
                        if (day.substring(0,1).equals("0") && intday < 10) {
                            finalint = "0" + finalint;
                        }
                        currentIteration.delete(8,10);
                        currentIteration.insert(8,finalint);
                    }
                }

                if (month.equals("09")) {
                    if (day.equals("30")) {
                        currentIteration.delete(5,7);
                        currentIteration.insert(5,"10");
                        currentIteration.delete(8,10);
                        currentIteration.insert(8,"01");
                    } else {
                        String finalint;
                        int intday = Integer.parseInt(day);
                        intday++;
                        finalint = String.valueOf(intday);
                        if (day.substring(0,1).equals("0") && intday < 10) {
                            finalint = "0" + finalint;
                        }
                        currentIteration.delete(8,10);
                        currentIteration.insert(8,finalint);
                    }
                }

                if (month.equals("10")) {
                    if (day.equals("31")) {
                        currentIteration.delete(5,7);
                        currentIteration.insert(5,"11");
                        currentIteration.delete(8,10);
                        currentIteration.insert(8,"01");
                    } else {
                        String finalint;
                        int intday = Integer.parseInt(day);
                        intday++;
                        finalint = String.valueOf(intday);
                        if (day.substring(0,1).equals("0") && intday < 10) {
                            finalint = "0" + finalint;
                        }
                        currentIteration.delete(8,10);
                        currentIteration.insert(8,finalint);
                    }
                }

                if (month.equals("11")) {
                    if (day.equals("30")) {
                        currentIteration.delete(5,7);
                        currentIteration.insert(5,"12");
                        currentIteration.delete(8,10);
                        currentIteration.insert(8,"01");
                    } else {
                        String finalint;
                        int intday = Integer.parseInt(day);
                        intday++;
                        finalint = String.valueOf(intday);
                        if (day.substring(0,1).equals("0") && intday < 10) {
                            finalint = "0" + finalint;
                        }
                        currentIteration.delete(8,10);
                        currentIteration.insert(8,finalint);
                    }
                }

                if (month.equals("12")) {
                    if (day.equals("31")) {
                        currentIteration.delete(5,7);
                        currentIteration.insert(5,"01");
                        currentIteration.delete(8,10);
                        currentIteration.insert(8,"01");

                        String finalyear;
                        int intyear = Integer.parseInt(year);
                        intyear++;
                        finalyear = String.valueOf(intyear);
                        currentIteration.delete(0,3);
                        currentIteration.insert(0,finalyear);

                    } else {
                        String finalint;
                        int intday = Integer.parseInt(day);
                        intday++;
                        finalint = String.valueOf(intday);
                        if (day.substring(0,1).equals("0") && intday < 10) {
                            finalint = "0" + finalint;
                        }
                        currentIteration.delete(8,10);
                        currentIteration.insert(8,finalint);
                    }
                }
            }
        }
        return requestedSnippet;
    }
}