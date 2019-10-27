package ce201.src;



import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Graphs extends JPanel {

    static JTextField title;
    static JTextField message;
    static JButton bgraph;
    static JButton lgraph;
    static JButton hgraph;
    static JButton pchart;
    static JButton main;
    static JPanel buttonRestrictor;

    static BarGraph barGraph;
    static LineChart lineChart;
    static PieChart pieChart;

    public Graphs(){

        this.setLayout(MainFrame.standardLayout);

        title = new JTextField("Plot a graph");
        message = new JTextField("Which graph do you wish to plot:");
        bgraph = new JButton("Bar graph");
        lgraph = new JButton("Line graph");
        pchart = new JButton("Pie Chart");

        main = new JButton("Return to Main menu");

        main.addActionListener(new ActionListener() {
               @Override
               public void actionPerformed(ActionEvent e) {
                   PostDataSnippet.snippedData = new ArrayList<String>();
//                   LineChart.dataSet = new ArrayList<String>();
//                   PieChart.dataSet = new ArrayList<String>()
               }
           }
        );

        ButtonHandler mainMenu = new ButtonHandler(5);
        ButtonHandler lineGraph = new ButtonHandler(20);
        ButtonHandler barChart = new ButtonHandler(15);
        ButtonHandler piechart = new ButtonHandler(22);// sends user to the next panel to plot

        //barGraph = new BarGraph("UK Power Generation Viewer ", "Date", "Power");
        pieChart = new PieChart("UK Power Genereation Viewer", "pieChart.jpg");
        lineChart = new LineChart("UK Power Generation Viewer", "Power (GW)", "Date", "lineChart.jpg");
        barGraph = new BarGraph("UK Power Generation Viewer", "Power (GW)", "Date");

        bgraph.addActionListener(barChart);
        lgraph.addActionListener(lineGraph);
        main.addActionListener(mainMenu);
        pchart.addActionListener(piechart);

        title.setFont(MainFrame.titleFont);
        title.setHorizontalAlignment(JTextField.CENTER);
        title.setEditable(false);
        title.setBorder(BorderFactory.createEmptyBorder());

        message.setFont(MainFrame.messageFont);
        message.setHorizontalAlignment(JTextField.CENTER);
        message.setEditable(false);
        message.setBorder(BorderFactory.createEmptyBorder());

        buttonRestrictor = new JPanel();
        buttonRestrictor.add(bgraph);
        buttonRestrictor.add(lgraph);
        buttonRestrictor.add(pchart);

        add(title);
        add(message);
        add(buttonRestrictor);
        add(main);
    }

}