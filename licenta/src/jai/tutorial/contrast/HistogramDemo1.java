package jai.tutorial.contrast;

import java.io.IOException;
import java.util.Random;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.statistics.HistogramDataset;
import org.jfree.data.statistics.HistogramType;
import org.jfree.data.xy.IntervalXYDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

/**
 * A demo of the {@link HistogramDataset} class.
 * 
 * @author Jelai Wang, jelaiw AT mindspring.com
 */
public class HistogramDemo1 extends ApplicationFrame {
    
    /** For generating random numbers. */ 
    static Random random = new Random();

    /**
     * Creates a new demo.
     * 
     * @param title  the frame title.
     */
    public HistogramDemo1(String title) {
        super(title);    
        IntervalXYDataset dataset = createDataset();
        JFreeChart chart = createChart(dataset);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        chartPanel.setMouseZoomable(true, false);
        setContentPane(chartPanel);
    }
    
    /**
     * Creates a sample {@link HistogramDataset}.
     * 
     * @return The dataset.
     */
    private IntervalXYDataset createDataset() {
        HistogramDataset dataset = new HistogramDataset();
        dataset.setType(HistogramType.RELATIVE_FREQUENCY);
        dataset.addSeries("H1", gaussianData(1000, 3.0), 20);
        dataset.addSeries("H0", gaussianData(1000, 0), 20);   
        return dataset;     
    }
    
    // ****************************************************************************
    // * JFREECHART DEVELOPER GUIDE                                               *
    // * The JFreeChart Developer Guide, written by David Gilbert, is available   *
    // * to purchase from Object Refinery Limited:                                *
    // *                                                                          *
    // * http://www.object-refinery.com/jfreechart/guide.html                     *
    // *                                                                          *
    // * Sales are used to provide funding for the JFreeChart project - please    * 
    // * support us so that we can continue developing free software.             *
    // ****************************************************************************
    
    /**
     * Creates a chart.
     * 
     * @param dataset  a dataset.
     * 
     * @return The chart.
     */
    private JFreeChart createChart(IntervalXYDataset dataset) {
        JFreeChart chart = ChartFactory.createHistogram(
            "Histogram Demo", 
            null, 
            null, 
            dataset, 
            PlotOrientation.VERTICAL, 
            true, 
            false, 
            false
        );
        chart.getXYPlot().setForegroundAlpha(0.75f);
        return chart;
    }
    
    /**
     * Generates an array of sample data.
     * 
     * @param size  the array size.
     * @param shift  the shift from zero.
     * 
     * @return The array of sample data.
     */
    private static double[] gaussianData(int size, double shift) {
        double[] d = new double[size];
        for (int i = 0; i < d.length; i++) {
            d[i] = random.nextGaussian() + shift;
        }
        return d;
    }
    
    /**
     * The starting point for the demo.
     * 
     * @param args  ignored.
     * 
     * @throws IOException  if there is a problem saving the file.
     */
    public static void main(String[] args) throws IOException {
        
        HistogramDemo1 demo = new HistogramDemo1("Histogram Demo");
        demo.pack();
        RefineryUtilities.centerFrameOnScreen(demo);
        demo.setVisible(true);
        
    }

}
