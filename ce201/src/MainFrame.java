package ce201.src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static java.awt.Color.*;

// The entire program will be contained within the single JFrame
// , with multiple JPanels added to a CardPanel and navigation through buttons using an ActionListener

public class MainFrame extends JFrame {

    static JPanel cardPanel; // creates the JPanel used for the cardPanel
    static CardLayout cardLayout = new CardLayout(); // sets the standard layout for the cardPanel
    static GridLayout standardLayout = new GridLayout(4, 0, 0, 0); // sets the standard layout used for the panels.
    static final Font titleFont = new Font("Calibri", Font.BOLD, 22); // the font used for titles
    static final Font messageFont = new Font("Calibri", Font.PLAIN, 14); // font used for standard messages

    static boolean nightOn = false;
    static JMenuItem enable;

    public MainFrame() {

        // Sets basic characteristics of the JFrame

        setTitle("CE201 Group 04");
        setSize(600, 400);
        setResizable(false);

        // Creates a skeleton menu bar - can be easily extended using same syntax.

        JMenu about = new JMenu("About");
        JMenuItem credits = new JMenuItem("Credits");
        about.add(credits);

        JMenu prerequisites = new JMenu("Prerequisites");
        JMenuItem instructions = new JMenuItem("Instructions");
        prerequisites.add(instructions);

        JMenu nightmode = new JMenu("Night Mode");
        enable = new JMenuItem("Enable night mode");
        nightmode.add(enable);

        JMenuBar bar = new JMenuBar();
        bar.add(about);
        bar.add(prerequisites);
        bar.add(nightmode);
        setJMenuBar(bar);

        // Creates the card panel and sets the layout for it.

        cardPanel = new JPanel();
        cardPanel.setLayout(cardLayout);

        // Creates each individual panel and adds it to the cardPanel

        WelcomeScreen firstPanel = new WelcomeScreen();
        FileSelection secondPanel = new FileSelection();
        MainMenu thirdPanel = new MainMenu();
        DataSnippet fifthPanel = new DataSnippet();
        PostDataSnippet sixthPanel = new PostDataSnippet();
        Graphs seventhPanel = new Graphs();

        SearchForData forthPanel = new SearchForData();
        RecentSearch eightPanel = new RecentSearch();
        Prediction predictionPanel=new Prediction();

        cardPanel.add(firstPanel, "1");
        cardPanel.add(secondPanel, "2");
        cardPanel.add(thirdPanel, "3");
        cardPanel.add(predictionPanel,"6");
        cardPanel.add(eightPanel,"7");
        cardPanel.add(forthPanel, "8");
        cardPanel.add(fifthPanel, "9");
        cardPanel.add(sixthPanel, "10");
        cardPanel.add(seventhPanel, "12");

        // Adds everything to the JFrame

        add(cardPanel, BorderLayout.NORTH);
        add(cardPanel);

        ButtonHandler pre = new ButtonHandler(21);
        instructions.addActionListener(pre);

        ButtonHandler pre2 = new ButtonHandler(23);
        credits.addActionListener(pre2);

        enable.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                if (!nightOn) {


                    WelcomeScreen.message.setForeground(YELLOW);
                    WelcomeScreen.message.setBackground(BLACK);
                    WelcomeScreen.title.setForeground(YELLOW);
                    WelcomeScreen.title.setBackground(BLACK);
                    WelcomeScreen.contentRestrictor.setForeground(YELLOW);
                    WelcomeScreen.contentRestrictor.setBackground(BLACK);
                    WelcomeScreen.proceed.setForeground(YELLOW);
                    WelcomeScreen.proceed.setBackground(BLACK);


                    FileSelection.readFile.setForeground(YELLOW);
                    FileSelection.readFile.setBackground(BLACK);
                    FileSelection.selectFile.setForeground(YELLOW);
                    FileSelection.selectFile.setBackground(BLACK);
                    if (FileSelection.message.getForeground()!=RED)
                    FileSelection.message.setForeground(YELLOW);
                    FileSelection.message.setBackground(BLACK);
                    FileSelection.messageRestrictor.setForeground(YELLOW);
                    FileSelection.messageRestrictor.setBackground(BLACK);
                    FileSelection.contentRestrictor.setForeground(YELLOW);
                    FileSelection.contentRestrictor.setBackground(BLACK);
                    FileSelection.fileName.setForeground(YELLOW);
                    FileSelection.fileName.setBackground(BLACK);
                    FileSelection.title.setForeground(YELLOW);
                    FileSelection.title.setBackground(BLACK);

                    MainMenu.prediction.setForeground(YELLOW);
                    MainMenu.prediction.setBackground(BLACK);
                    MainMenu.hystorySearch.setForeground(YELLOW);
                    MainMenu.hystorySearch.setBackground(BLACK);
                    MainMenu.readFile.setForeground(YELLOW);
                    MainMenu.readFile.setBackground(BLACK);
                    MainMenu.search.setForeground(YELLOW);
                    MainMenu.search.setBackground(BLACK);
                    MainMenu.snippet.setForeground(YELLOW);
                    MainMenu.snippet.setBackground(BLACK);
                    MainMenu.back.setForeground(YELLOW);
                    MainMenu.back.setBackground(BLACK);
                    MainMenu.message.setForeground(YELLOW);
                    MainMenu.message.setBackground(BLACK);
                    MainMenu.messageRestrictor.setForeground(YELLOW);
                    MainMenu.messageRestrictor.setBackground(BLACK);
                    MainMenu.title.setForeground(YELLOW);
                    MainMenu.title.setBackground(BLACK);

                    DataSnippet.execute.setForeground(YELLOW);
                    DataSnippet.execute.setBackground(BLACK);
                    DataSnippet.back.setForeground(YELLOW);
                    DataSnippet.back.setBackground(BLACK);
                    if (DataSnippet.message.getForeground()!=RED)
                        DataSnippet.message.setForeground(YELLOW);
                    DataSnippet.message.setBackground(BLACK);
                    DataSnippet.messageRestrictor.setForeground(YELLOW);
                    DataSnippet.messageRestrictor.setBackground(BLACK);
                    DataSnippet.title.setForeground(YELLOW);
                    DataSnippet.title.setBackground(BLACK);
                    DataSnippet.contentRestrictor.setForeground(YELLOW);
                    DataSnippet.contentRestrictor.setBackground(BLACK);
                    DataSnippet.startDate.getJFormattedTextField().setBackground(BLACK);
                    DataSnippet.startDate.getJFormattedTextField().setForeground(YELLOW);
                    DataSnippet.endDate.getJFormattedTextField().setBackground(BLACK);
                    DataSnippet.endDate.getJFormattedTextField().setForeground(YELLOW);
                    DataSnippet.startDate.setForeground(YELLOW);
                    DataSnippet.startDate.setBackground(BLACK);
                    DataSnippet.endDate.setForeground(YELLOW);
                    DataSnippet.endDate.setBackground(BLACK);

                    RecentSearch.searchHistory.setForeground(YELLOW);
                    RecentSearch.searchHistory.setBackground(BLACK);
                    RecentSearch.snipHistory.setForeground(YELLOW);
                    RecentSearch.snipHistory.setBackground(BLACK);
                    RecentSearch.clearHistory.setForeground(YELLOW);
                    RecentSearch.clearHistory.setBackground(BLACK);
                    eightPanel.setForeground(YELLOW);
                    eightPanel.setBackground(BLACK);
                    if (RecentSearch.message.getForeground()!=RED)
                        RecentSearch.message.setForeground(YELLOW);
                    RecentSearch.message.setBackground(BLACK);
                    RecentSearch.title.setForeground(YELLOW);
                    RecentSearch.title.setBackground(BLACK);
                    RecentSearch.back.setForeground(YELLOW);
                    RecentSearch.back.setBackground(BLACK);
                    RecentSearch.buttonRestrictor.setForeground(YELLOW);
                    RecentSearch.buttonRestrictor.setBackground(BLACK);

                    Prediction.title.setForeground(YELLOW);
                    Prediction.title.setBackground(BLACK);
                    Prediction.generate.setForeground(YELLOW);
                    Prediction.generate.setBackground(BLACK);
                    Prediction.back.setForeground(YELLOW);
                    Prediction.back.setBackground(BLACK);
                    if (Prediction.message.getForeground()!=RED)
                        Prediction.message.setForeground(YELLOW);
                    Prediction.message.setBackground(BLACK);
                    Prediction.messageRestrictor.setForeground(YELLOW);
                    Prediction.messageRestrictor.setBackground(BLACK);
                    Prediction.predictionNumber.setForeground(YELLOW);
                    Prediction.predictionNumber.setBackground(BLACK);
                    Prediction.contentRestrictor.setForeground(YELLOW);
                    Prediction.contentRestrictor.setBackground(BLACK);
                    Prediction.number.setBackground(BLACK);
                    Prediction.number.setForeground(YELLOW);

                    fifthPanel.setForeground(YELLOW);
                    fifthPanel.setBackground(BLACK);
                    PostDataSnippet.title.setForeground(YELLOW);
                    PostDataSnippet.title.setBackground(BLACK);
                    PostDataSnippet.message.setForeground(YELLOW);
                    PostDataSnippet.message.setBackground(BLACK);
                    PostDataSnippet.messageRestrictor.setForeground(YELLOW);
                    PostDataSnippet.messageRestrictor.setBackground(BLACK);
                    PostDataSnippet.contentRestrictor.setForeground(YELLOW);
                    PostDataSnippet.contentRestrictor.setBackground(BLACK);
                    PostDataSnippet.back.setForeground(YELLOW);
                    PostDataSnippet.back.setBackground(BLACK);
                    PostDataSnippet.graph.setForeground(YELLOW);
                    PostDataSnippet.graph.setBackground(BLACK);

                    Graphs.title.setForeground(YELLOW);
                    Graphs.title.setBackground(BLACK);
                    Graphs.message.setForeground(YELLOW);
                    Graphs.message.setBackground(BLACK);
                    Graphs.buttonRestrictor.setForeground(YELLOW);
                    Graphs.buttonRestrictor.setBackground(BLACK);
                    seventhPanel.setForeground(YELLOW);
                    seventhPanel.setBackground(BLACK);
                    Graphs.bgraph.setForeground(YELLOW);
                    Graphs.bgraph.setBackground(BLACK);
                    Graphs.lgraph.setForeground(YELLOW);
                    Graphs.lgraph.setBackground(BLACK);
                    Graphs.main.setForeground(YELLOW);
                    Graphs.main.setBackground(BLACK);
                    Graphs.pchart.setForeground(YELLOW);
                    Graphs.pchart.setBackground(BLACK);

                    enable.setText("Disable night mode");
                    nightOn = true;

                }
                else{
                    // Welcome screen

                    WelcomeScreen.message.setForeground(null);
                    WelcomeScreen.message.setBackground(null);
                    WelcomeScreen.title.setForeground(null);
                    WelcomeScreen.title.setBackground(null);
                    WelcomeScreen.contentRestrictor.setForeground(null);
                    WelcomeScreen.contentRestrictor.setBackground(null);
                    WelcomeScreen.proceed.setForeground(null);
                    WelcomeScreen.proceed.setBackground(null);


                    FileSelection.readFile.setForeground(null);
                    FileSelection.readFile.setBackground(null);
                    FileSelection.selectFile.setForeground(null);
                    FileSelection.selectFile.setBackground(null);
                    if (FileSelection.message.getForeground()!=RED)
                        FileSelection.message.setForeground(null);
                    FileSelection.message.setBackground(null);
                    FileSelection.messageRestrictor.setForeground(null);
                    FileSelection.messageRestrictor.setBackground(null);
                    FileSelection.contentRestrictor.setForeground(null);
                    FileSelection.contentRestrictor.setBackground(null);
                    FileSelection.fileName.setForeground(null);
                    FileSelection.fileName.setBackground(null);
                    FileSelection.title.setForeground(null);
                    FileSelection.title.setBackground(null);

                    MainMenu.prediction.setForeground(null);
                    MainMenu.prediction.setBackground(null);
                    MainMenu.hystorySearch.setForeground(null);
                    MainMenu.hystorySearch.setBackground(null);
                    MainMenu.readFile.setForeground(null);
                    MainMenu.readFile.setBackground(null);
                    MainMenu.search.setForeground(null);
                    MainMenu.search.setBackground(null);
                    MainMenu.snippet.setForeground(null);
                    MainMenu.snippet.setBackground(null);
                    MainMenu.back.setForeground(null);
                    MainMenu.back.setBackground(null);
                    MainMenu.message.setForeground(null);
                    MainMenu.message.setBackground(null);
                    MainMenu.messageRestrictor.setForeground(null);
                    MainMenu.messageRestrictor.setBackground(null);
                    MainMenu.title.setForeground(null);
                    MainMenu.title.setBackground(null);

                    DataSnippet.execute.setForeground(null);
                    DataSnippet.execute.setBackground(null);
                    DataSnippet.back.setForeground(null);
                    DataSnippet.back.setBackground(null);
                    if (DataSnippet.message.getForeground()!=RED)
                    DataSnippet.message.setForeground(null);
                    DataSnippet.message.setBackground(null);
                    DataSnippet.messageRestrictor.setForeground(null);
                    DataSnippet.messageRestrictor.setBackground(null);
                    DataSnippet.title.setForeground(null);
                    DataSnippet.title.setBackground(null);
                    DataSnippet.contentRestrictor.setForeground(null);
                    DataSnippet.contentRestrictor.setBackground(null);
                    DataSnippet.startDate.getJFormattedTextField().setBackground(null);
                    DataSnippet.startDate.getJFormattedTextField().setForeground(null);
                    DataSnippet.endDate.getJFormattedTextField().setBackground(null);
                    DataSnippet.endDate.getJFormattedTextField().setForeground(null);
                    DataSnippet.startDate.setForeground(null);
                    DataSnippet.startDate.setBackground(null);
                    DataSnippet.endDate.setForeground(null);
                    DataSnippet.endDate.setBackground(null);

                    RecentSearch.searchHistory.setForeground(null);
                    RecentSearch.searchHistory.setBackground(null);
                    RecentSearch.snipHistory.setForeground(null);
                    RecentSearch.snipHistory.setBackground(null);
                    RecentSearch.clearHistory.setForeground(null);
                    RecentSearch.clearHistory.setBackground(null);
                    eightPanel.setForeground(null);
                    eightPanel.setBackground(null);
                    if (RecentSearch.message.getForeground()!=RED)
                    RecentSearch.message.setForeground(null);
                    RecentSearch.message.setBackground(null);
                    RecentSearch.title.setForeground(null);
                    RecentSearch.title.setBackground(null);
                    RecentSearch.back.setForeground(null);
                    RecentSearch.back.setBackground(null);
                    RecentSearch.buttonRestrictor.setForeground(null);
                    RecentSearch.buttonRestrictor.setBackground(null);

                    Prediction.title.setForeground(null);
                    Prediction.title.setBackground(null);
                    Prediction.generate.setForeground(null);
                    Prediction.generate.setBackground(null);
                    Prediction.back.setForeground(null);
                    Prediction.back.setBackground(null);
                    if (Prediction.message.getForeground()!=RED)
                    Prediction.message.setForeground(null);
                    Prediction.message.setBackground(null);
                    Prediction.messageRestrictor.setForeground(null);
                    Prediction.messageRestrictor.setBackground(null);
                    Prediction.predictionNumber.setForeground(null);
                    Prediction.predictionNumber.setBackground(null);
                    Prediction.contentRestrictor.setForeground(null);
                    Prediction.contentRestrictor.setBackground(null);
                    Prediction.number.setBackground(null);
                    Prediction.number.setForeground(null);

                    fifthPanel.setForeground(null);
                    fifthPanel.setBackground(null);
                    PostDataSnippet.title.setForeground(null);
                    PostDataSnippet.title.setBackground(null);
                    PostDataSnippet.message.setForeground(null);
                    PostDataSnippet.message.setBackground(null);
                    PostDataSnippet.messageRestrictor.setForeground(null);
                    PostDataSnippet.messageRestrictor.setBackground(null);
                    PostDataSnippet.contentRestrictor.setForeground(null);
                    PostDataSnippet.contentRestrictor.setBackground(null);
                    PostDataSnippet.back.setForeground(null);
                    PostDataSnippet.back.setBackground(null);
                    PostDataSnippet.graph.setForeground(null);
                    PostDataSnippet.graph.setBackground(null);

                    Graphs.title.setForeground(null);
                    Graphs.title.setBackground(null);
                    Graphs.message.setForeground(null);
                    Graphs.message.setBackground(null);
                    Graphs.buttonRestrictor.setForeground(null);
                    Graphs.buttonRestrictor.setBackground(null);
                    seventhPanel.setForeground(null);
                    seventhPanel.setBackground(null);
                    Graphs.bgraph.setForeground(null);
                    Graphs.bgraph.setBackground(null);
                    Graphs.lgraph.setForeground(null);
                    Graphs.lgraph.setBackground(null);
                    Graphs.main.setForeground(null);
                    Graphs.main.setBackground(null);
                    Graphs.pchart.setForeground(null);
                    Graphs.pchart.setBackground(null);

                    enable.setText("Enable night mode");
                    nightOn = false;
                }
            }
        });
    }
}
