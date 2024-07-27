/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.view.dashboard;

import co.id.ez.ezpay.app.Common;
import com.json.JSONObject;
import java.awt.Color;
import java.awt.Font;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.Locale;
import javax.swing.BorderFactory;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;

/**
 *
 * @author RCS
 */
public class LineChartTransactionSummary extends javax.swing.JPanel {

    private final LinkedList<JSONObject> data;
    private final Date startDate, endDate;
    private JFreeChart lineChart;
    private ChartPanel chartPanel;

    /**
     * Creates new form LineChartTransactionSummary
     *
     * @param pData
     * @param pStartDate
     * @param pEndDate
     */
    public LineChartTransactionSummary(LinkedList<JSONObject> pData, Date pStartDate, Date pEndDate) {
        this.data = pData;
        this.startDate = pStartDate;
        this.endDate = pEndDate;
        initComponents();
        initForm();
    }

    private void initForm() {
        start();
    }

    private void start() {
        new Thread(() -> {
            try {
                loadData();
            } catch (Exception e) {
                Common.errorLog("[Exception] Error load data histori", e);
            }

        }).start();
    }

    private void loadData() {
        lineChart = ChartFactory.createTimeSeriesChart(
                "Grafik Transaksi Pembayaran dan Topup", 
                "Bulan Tahun", 
                "Jumlah Transaksi", 
                createDataset(), 
                true, 
                true, 
                false
        );

        chartPanel = new ChartPanel(lineChart);
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel.setBackground(Color.white);
        chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));
        
        XYPlot plot = (XYPlot) lineChart.getPlot();
        XYItemRenderer render = plot.getRenderer();
        if (render instanceof XYLineAndShapeRenderer) {
            XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) render;
            renderer.setDefaultShapesVisible(true);
            renderer.setDefaultShapesFilled(true);
            renderer.setDrawSeriesLineAsPath(true);
        }

        DateAxis axis = (DateAxis) plot.getDomainAxis();
        axis.setDateFormatOverride(new SimpleDateFormat("MMM-yyyy", Locale.getDefault()));
        
        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        rangeAxis.setUpperMargin(0.15);

        plot.setRenderer(render);
        plot.setBackgroundPaint(new Color(143, 148, 251));

        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.BLACK);

        plot.setDomainGridlinesVisible(true);
        plot.setDomainGridlinePaint(Color.BLACK);
        
        plot.setNoDataMessage("Tidak Ada Transaksi");
        plot.setNoDataMessageFont(new Font("Arial", 0, 13));

        lineChart.getLegend().setFrame(BlockBorder.NONE);
        lineChart.setTitle(new TextTitle("Grafik Transaksi Pembayaran dan Topup",
                new Font("Arial", java.awt.Font.BOLD, 14)
        )
        );

        removeAll();
        repaint();
        revalidate();
        add(chartPanel);
        repaint();
        revalidate();
    }

    private XYDataset createDataset() {
        TimeSeriesCollection dataset = new TimeSeriesCollection();

        if (data != null) {
            LinkedHashMap<String, LinkedHashMap<String, Double>> maps = new LinkedHashMap<>();
            data.forEach(rows -> {
                String module = rows.getString("module");
                double amount = rows.getDouble("total_transaction");
                String date = rows.getString("transaction_date");

                if (maps.containsKey(module)) {
                    maps.get(module).put(date, amount);
                } else {
                    LinkedHashMap<String, Double> tmp = new LinkedHashMap<>();
                    tmp.put(date, amount);
                    maps.put(module, tmp);
                }
            });

            LocalDate start = startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            LocalDate end = endDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            maps.keySet().forEach(module -> {
                LinkedHashMap<String, Double> mData = maps.get(module);
                TimeSeries series = new TimeSeries(module);
                for (LocalDate date = start; date.isBefore(end); date = date.plusDays(1)) {
                    String dateTrans = date.toString();
                    if (mData.containsKey(dateTrans)) {
                        series.add(new Day(date.getDayOfMonth(), date.getMonthValue(), date.getYear()), mData.get(dateTrans));
                    } else {
                        series.add(new Day(date.getDayOfMonth(), date.getMonthValue(), date.getYear()), 0);
                    }
                }
                dataset.addSeries(series);
            });

        }
        return dataset;
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setOpaque(false);
        setLayout(new java.awt.CardLayout());
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
