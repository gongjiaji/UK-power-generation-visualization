package ce201.src;

import com.itextpdf.text.Document;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.FileOutputStream;
import java.util.ArrayList;

public class PostDataSnippet extends JPanel {

    public static ArrayList<String> snippedData;
    static JTextArea message;
    static JDialog dialog;

    static JTextField title;
    static JButton graph;
    static JButton back;
    static JPanel messageRestrictor;
    static JPanel contentRestrictor;
    static JPanel buttonRestrictor;
    static boolean pUsed = false;


    public PostDataSnippet() {

        snippedData = new ArrayList<>();

        // Basic characteristics and components defined.
        setLayout(MainFrame.standardLayout);

        title = new JTextField("Results");
        message = new JTextArea("Your request has been generated on dialog. Proceed for further processing below. ");

        graph = new JButton("Plot graph");
        back = new JButton("Back to menu");

        title.setFont(MainFrame.titleFont);
        title.setHorizontalAlignment(JTextField.CENTER);
        title.setEditable(false);
        title.setBorder(BorderFactory.createEmptyBorder());

        message.setFont(MainFrame.messageFont);
        message.setEditable(false);
        message.setBorder(BorderFactory.createEmptyBorder());
        message.setOpaque(false);

        // JPanels are used to enclose components; this allows for an organised layout and format.

        messageRestrictor = new JPanel();
        messageRestrictor.add(message);

        contentRestrictor = new JPanel();

        buttonRestrictor = new JPanel();
        buttonRestrictor.setLayout(new GridLayout(2,4));
        buttonRestrictor.add(graph);
        buttonRestrictor.add(back);

        // Adds all created components to the main JPanel

        add(title);
        add(messageRestrictor);
        add(contentRestrictor);
        add(buttonRestrictor);

        ButtonHandler backAction = new ButtonHandler(5); // sends user to the Main Menu panel
        back.addActionListener(backAction);

        ButtonHandler graphAction = new ButtonHandler(12); // sends user to the next location.
        graph.addActionListener(graphAction);

    }

    public static void createTable() {

        Object headings[] = {"id", "timestamp", "demand", "frequency", "coal", "nuclear","cgt","wind","french_ict","dutch_ict","irish_ict","ew_ict","pumped","hydro","oil","ocgt","other","solar"};

        DefaultTableModel model = new DefaultTableModel(0,18);
        model.setColumnIdentifiers(headings);

        JTable snippets = new JTable();

        snippets.setModel(model);
//        snippets.setBackground(Color.BLACK);
//        snippets.setForeground(Color.WHITE);

        JScrollPane sp = new JScrollPane(snippets);

        JTableHeader h = snippets.getTableHeader();
//        h.setBackground(Color.WHITE);
//        h.setForeground(Color.BLACK);

        for (String snip : snippedData) {
            String[] splitrow = snip.split(",");
            model.addRow(new Object[] {splitrow[0],splitrow[1],splitrow[2],splitrow[3],splitrow[4],splitrow[5],splitrow[6],splitrow[7],splitrow[8],splitrow[9],splitrow[10],splitrow[11],splitrow[12],splitrow[13],splitrow[14],splitrow[15],splitrow[16],splitrow[17]});
        }

        dialog = new JDialog();

        //adding export button to the dialog, on the bottom
        JPanel export = new JPanel(new FlowLayout());

        JButton as = new JButton("Export as PDF");
        export.add(as);
        dialog.add(export, BorderLayout.SOUTH);


        pUsed = true;
        SearchForData.sUsed = false;

        dialog.addWindowListener(new WindowListener() {
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
        as.addActionListener( new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {//creating the PDF in the local folder
                Document doc = new Document(PageSize.A4.rotate(), 10f, 10f, 100f, 0f);
                try {

                    PdfWriter.getInstance(doc, new FileOutputStream("snippet.pdf"));
                    doc.open();
                    PdfPTable pdfTable = new PdfPTable(snippets.getColumnCount());
                    //adding table headers
                    for (int i = 0; i < snippets.getColumnCount(); i++) {
                        pdfTable.addCell(snippets.getColumnName(i));
                    }
                    //extracting data from the JTable and inserting it to PdfPTable
                    for (int rows = 0; rows < snippets.getRowCount() ; rows++) {
                        for (int cols = 0; cols < snippets.getColumnCount(); cols++) {
                            pdfTable.addCell(snippets.getModel().getValueAt(rows, cols).toString());

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

        dialog.setSize(1800,450);
        dialog.add(sp);
        dialog.setModal(true);
        dialog.setVisible(true);
        dialog.setTitle("Data snippet");





    }

}