package ce201.src;

import javax.swing.*;
import java.awt.*;

import static java.awt.Color.BLACK;
import static java.awt.Color.YELLOW;

public class MainMenu extends JPanel {

    static JTextField title;
    static JTextArea message;
    static JButton readFile;
    static JButton search;
    static JButton snippet;
    static JButton hystorySearch;
    static JButton prediction;
    static JButton back;
    static JPanel messageRestrictor;

    public MainMenu() {

        // Basic characteristics and components defined.

        setLayout(MainFrame.standardLayout);

        title = new JTextField("Main menu");
        message = new JTextArea("Please select an option listed below:");
        readFile = new JButton("Read file into system");
        search = new JButton("Search for data");
        snippet = new JButton("Obtain a snippet");
        hystorySearch=new JButton("History Search");
        prediction=new JButton("Prediction");

        // Sends the user back to the file selection.

        back = new JButton("Return to file selection");

        ButtonHandler backAction = new ButtonHandler(2); // sends user back a panel
        back.addActionListener(backAction);

        ButtonHandler searchaction = new ButtonHandler(8); // sends user to the search panel
        search.addActionListener(searchaction);

        ButtonHandler snipaction = new ButtonHandler(9); // sends user to the search panel
        snippet.addActionListener(snipaction);

        ButtonHandler searchHystoryAction =new ButtonHandler(7);// sends user to the search hystory
        hystorySearch.addActionListener(searchHystoryAction);

        ButtonHandler predictionAction =new ButtonHandler(6);// sends user to the search hystory
        prediction.addActionListener(predictionAction);

        // Uses static fonts created in MainFrame and applied to the title and message.

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

        JPanel contentRestrictor = new JPanel();
        contentRestrictor.setLayout(new GridLayout(0,4));
        contentRestrictor.add(search);
        contentRestrictor.add(snippet);
        contentRestrictor.add(hystorySearch);
        contentRestrictor.add(prediction);

        // Adds all created components to the main JPanel

        add(title);
        add(messageRestrictor);
        add(contentRestrictor);
        add(back);
    }
}