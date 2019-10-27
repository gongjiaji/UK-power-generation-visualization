package ce201.src;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;


public class PieChart extends JDialog {

    public ArrayList<String> dataSet;

    //ctor creates jpeg
    public PieChart(String title, String jpegName) {

        dataSet = PostDataSnippet.snippedData;


        JFreeChart pieChartObject = ChartFactory.createPieChart(title, createDataset(), true, true, false);

        //jpeg creation

        //gets users screen size and sets chart to its size
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth() - 100;
        int height = (int) screenSize.getHeight() - 100;

        //creates new jpeg file
        File PieChart = new File(jpegName);
        try {
            ChartUtilities.saveChartAsJPEG(PieChart, pieChartObject, width, height);
        } catch (Exception e) {

        }

        //creates frame with chart
        ChartPanel panel = new ChartPanel(pieChartObject);
        //setContentPane(panel);

        this.setTitle(title);
        this.add(panel);
        this.setSize(width, height);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setModal(true);
        //creates line chart object

    }

    //creates data set using data from post data snippet
    public DefaultPieDataset createDataset() {

        DefaultPieDataset dataset = new DefaultPieDataset();
        String[] types = {"id", "timestamp", "demand", "frequency", "coal", "nuclear", "cgt", "wind", "french_ict",
                "dutch_ict", "irish_ict", "ew_ict", "pumped", "hydro", "oil", "ocgt", "other", "solar"};

        for (String i : dataSet) {
            String[] dataArray = i.split(",");
            for (int j = 4; j < dataArray.length; j++) {
                //value,energy type, date stamp inserted into dataset
                //try block deals with integers and doubles as not all data is in same format
                if(Double.parseDouble(dataArray[j]) >= 0){
                    dataset.setValue(types[j], Double.parseDouble(dataArray[j]));
                }

            }
        }
        return dataset;
    }
}

