package ce201.src;

import com.csvreader.CsvReader;
import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.regex.Pattern;

import static java.awt.Color.BLACK;
import static java.awt.Color.RED;
import static java.awt.Color.YELLOW;

public class Prediction extends JPanel{

    public static String numberTyped="";

    static JTextField message;
    static JTextField title;
    static JTextField number;
    static JLabel predictionNumber;
    static JPanel messageRestrictor;
    static JPanel contentRestrictor;
    static JPanel buttonRestrictor;
    static JButton back;
    static JButton generate;


    public Prediction() {

        this.setLayout(MainFrame.standardLayout);

        //title and message
        title = new JTextField("Data Prediction");
        message = new JTextField("Select the number of predictions to be displayed:");

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

        back = new JButton("Back to Main Menu");

        ButtonHandler backer = new ButtonHandler(5);
        back.addActionListener(backer);

        generate=new JButton("Generate Prediction");

        number = new JTextField(6);

        predictionNumber=new JLabel("Number of Predictions");
        predictionNumber.setLabelFor(number);

        contentRestrictor = new JPanel();
        contentRestrictor.add(predictionNumber);
        contentRestrictor.add(number);

        buttonRestrictor = new JPanel();
        buttonRestrictor.setLayout(new GridLayout(2,4));
        buttonRestrictor.add(generate);
        buttonRestrictor.add(back);

        add(title);
        add(message);
        add(contentRestrictor);
        add(buttonRestrictor);

        number.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);

                if(number.getText().length()<=6 &&  Pattern.matches("[0-9]+",number.getText())==true)
                    numberTyped=number.getText();
                else
                {
                    if (number.getText().length()==0)
                    {
                        number.setText("");
                        numberTyped=number.getText();
                    }
                    else {
                        e.consume();
                        number.setText(numberTyped);
                    }
                }
            }
        });

        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (MainFrame.nightOn)
                    message.setForeground(YELLOW);
                else
                    message.setForeground(null);
            }
        });



        generate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                try {

                    if (MainFrame.nightOn)
                        message.setForeground(YELLOW);
                    else
                        message.setForeground(null);

                   ArrayList<String> predictionDataResult=new ArrayList<>();

                    message.setText("Select the number of predictions to be displayed");

                    String last=new String();
                    String beforeLast=new String();
                    ArrayList<String[]>predictions=new ArrayList<>();

                    //create the table for displaying the data
                    JDialog resultFrame=new JDialog();

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


                    //adding export button to the dialog, on the bottom
                    JPanel buttonPanel = new JPanel(new FlowLayout());
                    resultFrame.add(buttonPanel, BorderLayout.SOUTH);
                    JButton close = new JButton("Export as PDF");
                    buttonPanel.add(close);

                    close.addActionListener( new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e)
                        { //creating the PDF in the local folder

                            Document doc = new Document(PageSize.A4.rotate(), 10f, 10f, 100f, 0f);
                            try {

                                PdfWriter.getInstance(doc, new FileOutputStream("Prediction.pdf"));
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


                    //read the file to obtain the formula for the prediction
                    CsvReader reader = null;

                    try {
                        reader=new CsvReader(ButtonHandler.filepath);
                    } catch (FileNotFoundException e1) {
                        e1.printStackTrace();
                    }

                    try {
                        reader.readHeaders();
                        while (reader.readRecord())
                        {
                            beforeLast=last;
                            last=reader.getRawRecord();
                        }
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }


                    String row1[]= beforeLast.split(", ");
                    predictions.add(row1);
                    String[] row2= last.split(", ");
                    predictions.add(row2);

                    float a,b,y3;


                    for (int i=2;i<=Integer.parseInt(numberTyped)+1;i++)
                    {
                        String[] newRow = new String[18];
                        String[] beforeLastRow=predictions.get(i-2);
                        String[] lastrow=predictions.get(i-1);

                        //generate ID
                        newRow[0]= String.valueOf(Integer.parseInt(lastrow[0])+1);


                        //generat Date

                        String[] timestamp1=beforeLastRow[1].split(" ");
                        String[] timestamp2=lastrow[1].split(" ");

                        String[] date1=timestamp1[0].split("-");
                        String[] date2=timestamp2[0].split("-");

                        String[] time1=timestamp1[1].split(":");
                        String[] time2=timestamp2[1].split(":");


                        String[] time3=new String[3];
                        String[] date3=new String[3];


                        int t2,t1,timeDifference;
                        t1=(Integer.parseInt(time1[0])*3600+Integer.parseInt(time1[1])*60+Integer.parseInt(time1[2]));
                        t2=(Integer.parseInt(time2[0])*3600+Integer.parseInt(time2[1])*60+Integer.parseInt(time2[2]));

                        if ((t2-t1)<0)
                            timeDifference=(24*3600-t1)+t2;
                        else
                            timeDifference=t2-t1;


                        date3[1]=date2[1];
                        date3[0]=date2[0];
                        date3[2]=date2[2];

                        int hours,minutes,seconds;
                        hours=0;
                        minutes=0;
                        seconds=0;

                        if (timeDifference>=3600)
                        {
                            hours=timeDifference/3600;
                            timeDifference=timeDifference-hours*3600;
                        }

                        if (timeDifference>=60)
                        {
                            minutes=timeDifference/60;
                            timeDifference=timeDifference-minutes*60;
                        }


                        seconds=timeDifference;

                        time3[0]=String.valueOf(hours);
                        time3[1]=String.valueOf(minutes);
                        time3[2]=String.valueOf(seconds);


                        if (Integer.parseInt(time2[2])+Integer.parseInt(time3[2])>=60)
                        {
                            time3[1]=String.valueOf(Integer.parseInt(time3[1])+1);
                            time3[2]=String.valueOf(Integer.parseInt(time2[2])+Integer.parseInt(time3[2])-60);
                        }
                        else
                            time3[2]=String.valueOf(Integer.parseInt(time2[2])+Integer.parseInt(time3[2]));

                        if (Integer.parseInt(time2[1])+Integer.parseInt(time3[1])>=60)
                        {
                            time3[0]=String.valueOf(Integer.parseInt(time3[0])+1);
                            time3[1]=String.valueOf(Integer.parseInt(time2[1])+Integer.parseInt(time3[1])-60);
                        }
                        else
                            time3[1]=String.valueOf(Integer.parseInt(time2[1])+Integer.parseInt(time3[1]));

                        if (Integer.parseInt(time2[0])+Integer.parseInt(time3[0])>=24)
                        {
                            date3[2]=String.valueOf(Integer.parseInt(date2[2])+1);
                            time3[0]=String.valueOf(Integer.parseInt(time2[0])+Integer.parseInt(time3[0])-24);
                        }
                        else
                            time3[0]=String.valueOf(Integer.parseInt(time2[0])+Integer.parseInt(time3[0]));


                        if (Integer.parseInt(date3[0])%4==0)
                        {
                            if (Integer.parseInt(date3[1])==1||Integer.parseInt(date3[1])==3||Integer.parseInt(date3[1])==5||Integer.parseInt(date3[1])==7||Integer.parseInt(date3[1])==8||Integer.parseInt(date3[1])==10||Integer.parseInt(date3[1])==12)
                            {
                                if (Integer.parseInt(date3[2])>31)
                                {
                                    date3[1]=String.valueOf(Integer.parseInt(date2[1])+1);
                                    date3[2]=String.valueOf(Integer.parseInt(date3[2])-31);
                                }
                            }

                            if (Integer.parseInt(date3[1])==4||Integer.parseInt(date3[1])==6||Integer.parseInt(date3[1])==9||Integer.parseInt(date3[1])==11)
                            {
                                if (Integer.parseInt(date3[2])>30)
                                {
                                    date3[1]=String.valueOf(Integer.parseInt(date3[1])+1);
                                    date3[2]=String.valueOf(Integer.parseInt(date3[2])-30);
                                }

                            }

                            if (Integer.parseInt(date3[1])==2)
                            {
                                if (Integer.parseInt(date3[2])>29)
                                {
                                    date3[1]=String.valueOf(Integer.parseInt(date3[1])+1);
                                    date3[2]=String.valueOf(Integer.parseInt(date3[2])-29);
                                }

                            }

                            if (Integer.parseInt(date3[1])>12)
                            {
                                date3[0]=String.valueOf(Integer.parseInt(date2[0])+1);
                                date3[1]=String.valueOf(Integer.parseInt(date3[1])-12);
                            }
                        }
                        else
                        {
                            if (Integer.parseInt(date3[1])==1||Integer.parseInt(date3[1])==3||Integer.parseInt(date3[1])==5||Integer.parseInt(date3[1])==7||Integer.parseInt(date3[1])==8||Integer.parseInt(date3[1])==10||Integer.parseInt(date3[1])==12)
                            {
                                if (Integer.parseInt(date3[2])>31)
                                {
                                    date3[1]=String.valueOf(Integer.parseInt(date3[1])+1);
                                    date3[2]=String.valueOf(Integer.parseInt(date3[2])-31);
                                }

                            }

                            if (Integer.parseInt(date3[1])==4||Integer.parseInt(date3[1])==6||Integer.parseInt(date3[1])==9||Integer.parseInt(date3[1])==11)
                            {
                                if (Integer.parseInt(date3[2])>30)
                                {
                                    date3[1]=String.valueOf(Integer.parseInt(date3[1])+1);
                                    date3[2]=String.valueOf(Integer.parseInt(date3[2])-30);
                                }
                            }

                            if (Integer.parseInt(date3[1])==2)
                            {
                                if (Integer.parseInt(date3[2])>29)
                                {
                                    date3[1]=String.valueOf(Integer.parseInt(date3[1])+1);
                                    date3[2]=String.valueOf(Integer.parseInt(date3[2])-29);
                                }

                            }

                            if (Integer.parseInt(date3[1])>12)
                            {
                                date3[0]=String.valueOf(Integer.parseInt(date2[0])+1);
                                date3[1]=String.valueOf(Integer.parseInt(date3[1])-12);
                            }
                        }


                        if (time3[0].length()==1)
                            time3[0]="0"+time3[0];

                        if (time3[1].length()==1)
                            time3[1]="0"+time3[1];

                        if (time3[2].length()==1)
                            time3[2]="0"+time3[2];

                        if (date3[1].length()==1)
                            date3[1]="0"+date3[1];

                        if (date3[2].length()==1)
                            date3[2]="0"+date3[2];


                        newRow[1]=date3[0]+"-"+date3[1]+"-"+date3[2]+" "+time3[0]+":"+time3[1]+":"+time3[2];

                        //generate demand id
                        a=Integer.parseInt(lastrow[2])-Integer.parseInt(beforeLastRow[2]);
                        b=2*Integer.parseInt(beforeLastRow[2])-Integer.parseInt(lastrow[2]);
                        y3=3*a+b;
                        if ((int)((float)y3)==y3)
                            newRow[2]=String.valueOf((int)y3);
                        else
                            newRow[2]=String.valueOf(y3);


                        //generate demand frequency
                        a=Float.parseFloat(lastrow[3])-Float.parseFloat(beforeLastRow[3]);
                        b=2*Float.parseFloat(beforeLastRow[3])-Float.parseFloat(lastrow[3]);
                        y3=3*a+b;
                        if ((int)((float)y3)==y3)
                            newRow[3]=String.valueOf((int)y3);
                        else
                            newRow[3]=String.valueOf(y3);


                        //generate coal value
                        a=Float.parseFloat(lastrow[4])-Float.parseFloat(beforeLastRow[4]);
                        b=2*Float.parseFloat(beforeLastRow[4])-Float.parseFloat(lastrow[4]);
                        y3=3*a+b;
                        if ((int)((float)y3)==y3)
                            newRow[4]=String.valueOf((int)y3);
                        else
                            newRow[4]=String.valueOf(y3);

                        //generate nuclear value
                        a=Float.parseFloat(lastrow[5])-Float.parseFloat(beforeLastRow[5]);
                        b=2*Float.parseFloat(beforeLastRow[5])-Float.parseFloat(lastrow[5]);
                        y3=3*a+b;
                        if ((int)((float)y3)==y3)
                            newRow[5]=String.valueOf((int)y3);
                        else
                            newRow[5]=String.valueOf(y3);

                        //generate ccgt value
                        a=Float.parseFloat(lastrow[6])-Float.parseFloat(beforeLastRow[6]);
                        b=2*Float.parseFloat(beforeLastRow[6])-Float.parseFloat(lastrow[6]);
                        y3=3*a+b;
                        if ((int)((float)y3)==y3)
                            newRow[6]=String.valueOf((int)y3);
                        else
                            newRow[6]=String.valueOf(y3);

                        //generate wind value
                        a=Float.parseFloat(lastrow[7])-Float.parseFloat(beforeLastRow[7]);
                        b=2*Float.parseFloat(beforeLastRow[7])-Float.parseFloat(lastrow[7]);
                        y3=3*a+b;
                        if ((int)((float)y3)==y3)
                            newRow[7]=String.valueOf((int)y3);
                        else
                            newRow[7]=String.valueOf(y3);

                        //generate french_ict value
                        a=Float.parseFloat(lastrow[8])-Float.parseFloat(beforeLastRow[8]);
                        b=2*Float.parseFloat(beforeLastRow[8])-Float.parseFloat(lastrow[8]);
                        y3=3*a+b;
                        if ((int)((float)y3)==y3)
                            newRow[8]=String.valueOf((int)y3);
                        else
                            newRow[8]=String.valueOf(y3);

                        //generate dutch_ict value
                        a=Float.parseFloat(lastrow[9])-Float.parseFloat(beforeLastRow[9]);
                        b=2*Float.parseFloat(beforeLastRow[9])-Float.parseFloat(lastrow[9]);
                        y3=3*a+b;
                        if ((int)((float)y3)==y3)
                            newRow[9]=String.valueOf((int)y3);
                        else
                            newRow[9]=String.valueOf(y3);

                        //generate french_ict value
                        a=Float.parseFloat(lastrow[10])-Float.parseFloat(beforeLastRow[10]);
                        b=2*Float.parseFloat(beforeLastRow[10])-Float.parseFloat(lastrow[10]);
                        y3=3*a+b;
                        if ((int)((float)y3)==y3)
                            newRow[10]=String.valueOf((int)y3);
                        else
                            newRow[10]=String.valueOf(y3);

                        //generate ew_ict value
                        a=Float.parseFloat(lastrow[11])-Float.parseFloat(beforeLastRow[11]);
                        b=2*Float.parseFloat(beforeLastRow[11])-Float.parseFloat(lastrow[11]);
                        y3=3*a+b;
                        if ((int)((float)y3)==y3)
                            newRow[11]=String.valueOf((int)y3);
                        else
                            newRow[11]=String.valueOf(y3);

                        //generate pumped value
                        a=Float.parseFloat(lastrow[12])-Float.parseFloat(beforeLastRow[12]);
                        b=2*Float.parseFloat(beforeLastRow[12])-Float.parseFloat(lastrow[12]);
                        y3=3*a+b;
                        if ((int)((float)y3)==y3)
                            newRow[12]=String.valueOf((int)y3);
                        else
                            newRow[12]=String.valueOf(y3);

                        //generate hydro value
                        a=Float.parseFloat(lastrow[13])-Float.parseFloat(beforeLastRow[13]);
                        b=2*Float.parseFloat(beforeLastRow[13])-Float.parseFloat(lastrow[13]);
                        y3=3*a+b;
                        if ((int)((float)y3)==y3)
                            newRow[13]=String.valueOf((int)y3);
                        else
                            newRow[13]=String.valueOf(y3);

                        //generate oil value
                        a=Float.parseFloat(lastrow[14])-Float.parseFloat(beforeLastRow[14]);
                        b=2*Float.parseFloat(beforeLastRow[14])-Float.parseFloat(lastrow[14]);
                        y3=3*a+b;
                        if ((int)((float)y3)==y3)
                            newRow[14]=String.valueOf((int)y3);
                        else
                            newRow[14]=String.valueOf(y3);

                        //generate ocgt value
                        a=Float.parseFloat(lastrow[15])-Float.parseFloat(beforeLastRow[15]);
                        b=2*Float.parseFloat(beforeLastRow[15])-Float.parseFloat(lastrow[15]);
                        y3=3*a+b;
                        if ((int)((float)y3)==y3)
                            newRow[15]=String.valueOf((int)y3);
                        else
                            newRow[15]=String.valueOf(y3);

                        //generate other value
                        a=Float.parseFloat(lastrow[16])-Float.parseFloat(beforeLastRow[16]);
                        b=2*Float.parseFloat(beforeLastRow[16])-Float.parseFloat(lastrow[16]);
                        y3=3*a+b;
                        if ((int)((float)y3)==y3)
                            newRow[16]=String.valueOf((int)y3);
                        else
                            newRow[16]=String.valueOf(y3);

                        //generate solar value
                        a=Float.parseFloat(lastrow[17])-Float.parseFloat(beforeLastRow[17]);
                        b=2*Float.parseFloat(beforeLastRow[17])-Float.parseFloat(lastrow[17]);
                        y3=3*a+b;
                        if ((int)((float)y3)==y3)
                            newRow[17]=String.valueOf((int)y3);
                        else
                            newRow[17]=String.valueOf(y3);

                        predictions.add(newRow);
                        modelResultTable.addRow(newRow);

                        predictionDataResult.add(newRow[0]+","+newRow[1]+","+newRow[2]+","+newRow[3]+","+newRow[4]+","+newRow[5]+","+newRow[6]+","+newRow[7]+","+newRow[8]+","+newRow[9]+","+newRow[10]+","+newRow[11]+","+newRow[12]+","+newRow[13]+","+newRow[14]+","+newRow[15]+","+newRow[16]+","+newRow[17]);
                    }
                    JScrollPane sp = new JScrollPane(resultTable);
                    resultFrame.setSize(1300,450);
                    resultFrame.add(sp);
                    resultFrame.setModal(true);
                    resultFrame.setVisible(true);
                    resultFrame.setTitle("Data Prediction Results");
                    number.setText("");
                    numberTyped="";

                    //save and sent the records to the graph section
                    PostDataSnippet.snippedData = new ArrayList<String>();
                    PostDataSnippet.snippedData = predictionDataResult;
                    predictionDataResult=new ArrayList<>();

                    MainFrame.cardLayout.show(MainFrame.cardPanel, "10"); // displays PostDataSnippet

            }

            catch (NumberFormatException x1) {
                message.setText("Please enter an integer value");
                message.setForeground(RED);
            }
            }



        });
    }
}