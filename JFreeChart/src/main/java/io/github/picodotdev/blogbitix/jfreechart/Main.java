package io.github.picodotdev.blogbitix.jfreechart;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYDataset;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.text.DecimalFormat;
import java.text.NumberFormat;

public class Main {

    public static void main(String[] args) throws Exception {
        DefaultXYDataset dataset = new DefaultXYDataset();
        dataset.addSeries("firefox", new double[][] {{ 2007, 2008, 2009, 2010, 2011, 2012, 2013, 2014, 2015, 2016, 2017 }, { 25, 29.1, 32.1, 32.9, 31.9, 25.5, 20.1, 18.4, 15.3, 11.4, 9.5 }});
        dataset.addSeries("ie", new double[][] {{ 2007, 2008, 2009, 2010, 2011, 2012, 2013, 2014, 2015, 2016, 2017 }, { 67.7, 63.1, 60.2, 50.6, 41.1, 31.8, 27.6, 20.4, 17.3, 12.3, 8.1 }});
        dataset.addSeries("chrome", new double[][] {{ 2009, 2010, 2011, 2012, 2013, 2014, 2015, 2016, 2017 }, { 0.2, 6.4, 14.6, 25.3, 30.1, 34.3, 43.2, 47.3, 58.4 }});

        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
        renderer.setSeriesPaint( 0 , Color.ORANGE );
        renderer.setSeriesPaint( 1 , Color.BLUE );
        renderer.setSeriesPaint( 2 , Color.GREEN );
        renderer.setSeriesStroke( 0 , new BasicStroke( 2 ) );
        renderer.setSeriesStroke( 1 , new BasicStroke( 2 ) );
        renderer.setSeriesStroke( 2 , new BasicStroke( 2 ) );

        JFreeChart chart = ChartFactory.createXYLineChart("Browser Quota", "Year", "Quota", dataset);
        chart.getXYPlot().getRangeAxis().setRange(0, 100);
        ((NumberAxis) chart.getXYPlot().getRangeAxis()).setNumberFormatOverride(new DecimalFormat("#'%'"));
        chart.getXYPlot().setRenderer(renderer);

        BufferedImage image = chart.createBufferedImage(600, 400);
        ImageIO.write(image, "png", new File("xy-chart.png"));
    }
}
