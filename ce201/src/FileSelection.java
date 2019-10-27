package ce201.src;

import javax.swing.*;

import static java.awt.Color.BLACK;
import static java.awt.Color.YELLOW;

public class FileSelection extends JPanel {

    //    static JTextField fileName; // static access allows future usage throughout classes within program
    static JLabel fileName = new JLabel();
    static JButton selectFile = new JButton("Browse");
    static JTextField message;
    static JTextField title;
    static JButton readFile;
    static JPanel messageRestrictor;
    static JPanel contentRestrictor ;

    public FileSelection() {

        fileName.setText("Please select file: ");

        // Basic characteristics and components defined.

        setLayout(MainFrame.standardLayout);

        title = new JTextField("File interface");
        message = new JTextField("Please select the file you wish to read");

        readFile = new JButton("Import CSV File");

        ButtonHandler readFileAction = new ButtonHandler(3); // sends user to the next panel
        readFile.addActionListener(readFileAction);

        // file chooser function
        ButtonHandler selectFileAction = new ButtonHandler(4); // sends user to the next panel
        selectFile.addActionListener(selectFileAction);
        // Uses static fonts created in MainFrame and applied to the title and message.

        title.setFont(MainFrame.titleFont);
        title.setHorizontalAlignment(JTextField.CENTER);
        title.setEditable(false);
        title.setBorder(BorderFactory.createEmptyBorder());

        message.setFont(MainFrame.messageFont);
        title.setHorizontalAlignment(JTextField.CENTER);
        message.setEditable(false);
        message.setBorder(BorderFactory.createEmptyBorder());

        // JPanels are used to enclose components; this allows for an organised layout and format.

        messageRestrictor = new JPanel();
        messageRestrictor.add(message);

        contentRestrictor = new JPanel();
        contentRestrictor.add(fileName);
        contentRestrictor.add(readFile);
        contentRestrictor.add(selectFile);

        // Adds all created components to the main JPanel

        add(title);
        add(messageRestrictor);
        add(contentRestrictor);
        add(readFile);
    }
}