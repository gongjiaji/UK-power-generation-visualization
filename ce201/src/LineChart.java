package ce201.src;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class LineChart extends JDialog {

   public ArrayList<String> dataSet;
    //ctor creates jpeg
    public LineChart(String title, String xLable, String yLable, String jpegName){

        dataSet = PostDataSnippet.snippedData;
        JFreeChart lineChartObject = ChartFactory.createLineChart(title,createDateLabel(), yLable, createDataset(),
                PlotOrientation.VERTICAL, true,true,false);



        CategoryPlot plot = lineChartObject.getCategoryPlot();
        CategoryAxis categoryAxis = plot.getDomainAxis();

        categoryAxis.setVisible(true);

        //jpeg creation

        //gets users screen size and sets chart to its size
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        int width = (int) screenSize.getWidth() - 100;
        int height = (int) screenSize.getHeight() - 100;

        //creates new jpeg file
        File lineChart = new File( jpegName );
        try{
            ChartUtilities.saveChartAsJPEG(lineChart ,lineChartObject, width ,height);
        }catch (Exception e){
        }

        //creates frame with chart
        ChartPanel panel = new ChartPanel(lineChartObject);
        //setContentPane(panel);

        this.setTitle(title);
        this.add(panel);
        this.setSize(width,height);
        this.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.setModal(true);
        //creates line chart object

    }

    public String createDateLabel(){
        String label = "";
        try{
            label += (dataSet.get(0).split(","))[1];
            label += "                                                    ";
            label += "                                                    ";
            label += "                                  ";
            label += (dataSet.get(dataSet.size()-1).split(","))[1];
            return label;
        }catch (Exception e){
        }

        /*for (String i: dataSet) {
            String[] dataArray = i.split(",");
            for (int j = 0; j <= dataSet.size(); j += dataSet.size() / 5) {
                label += createDate(dataArray[1]).toString();
            }
        }*/


        return label;
    }
    //creates data set using data from post data snippet
    public DefaultCategoryDataset createDataset(){

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        String[] types = {"id", "timestamp", "demand", "frequency", "coal", "nuclear","cgt","wind","french_ict",
                "dutch_ict","irish_ict","ew_ict","pumped","hydro","oil","ocgt","other","solar"};

        for (String i: dataSet){
            String[] dataArray = i.split(",");

            for (int j = 4; j < dataArray.length;j++){
                //value,energy type, date stamp inserted into dataset
                //try block deals with integers and doubles as not all data is in same format
                try{
                    dataset.addValue(Integer.parseInt(dataArray[j].trim()), types[j], createDate(dataArray[1]));
                }catch (NumberFormatException e){
                    dataset.addValue(Double.parseDouble(dataArray[j].trim()), types[j], createDate(dataArray[1]));
                }
            }
        }
        return dataset;
    }

    //converts timestamp from csv to date object
    public Date createDate(String stringDate){
        Date date = new Date();
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        try{
            date = df.parse(stringDate);
        }catch (ParseException e){

        }
        return date;
    }
}
