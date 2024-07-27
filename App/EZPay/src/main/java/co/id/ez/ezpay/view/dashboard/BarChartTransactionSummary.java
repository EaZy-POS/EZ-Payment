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
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedList;
import javax.swing.BorderFactory;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.CategoryItemRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author RCS
 */
public class BarChartTransactionSummary extends javax.swing.JPanel {

    private final LinkedList<JSONObject> data;
    private JFreeChart barChart;
    private ChartPanel chartPanel;

    /**
     * Creates new form BarChartTransactionSummary
     *
     * @param pData
     */
    public BarChartTransactionSummary(LinkedList<JSONObject> pData) {
        this.data = pData;
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
        barChart = ChartFactory.createBarChart(
                "Summary Total Transaksi",
                "Produk",
                "Jumlah Transaksi",
                createDataset(),
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        barChart.setBorderPaint(Color.white);
        barChart.getPlot().setNoDataMessage("Tidak ada data");
        barChart.getPlot().setBackgroundPaint(new Color(143, 148, 251));

        final CategoryPlot plot = barChart.getCategoryPlot();
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);

        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
        rangeAxis.setUpperMargin(0.15);

        final CategoryItemRenderer renderer = plot.getRenderer();
        renderer.setSeriesItemLabelsVisible(0, Boolean.TRUE);

        final CategoryAxis domainAxis = plot.getDomainAxis();
        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.UP_45);

        barChart.getLegend().setItemFont(new Font("Arial", 0, 13));
        chartPanel = new ChartPanel(barChart);
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel.setBackground(Color.white);
        chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));
        
        barChart.setTitle(new TextTitle("Summary Total Transaksi",
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

    private CategoryDataset createDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        HashMap<String, HashMap<String, BigDecimal>> tHolder = new HashMap<>();

        if (data != null) {
            data.forEach(dataTrans -> {
                String tDate = dataTrans.getString("tran_date");
                String tModule = dataTrans.getString("module");
                BigDecimal tValue = new BigDecimal(dataTrans.getDouble("total_transaction"));

                if (tHolder.containsKey(tDate)) {
                    if (tHolder.get(tDate).containsKey(tModule)) {
                        tHolder.get(tDate).get(tModule).add(tValue);
                    } else {
                        tHolder.get(tDate).put(tModule, tValue);
                    }
                } else {
                    HashMap<String, BigDecimal> maps = new HashMap<>();
                    maps.put(tModule, tValue);
                    tHolder.put(tDate, maps);
                }
            });
        }
        
        tHolder.keySet().forEach(date -> {
            HashMap<String, BigDecimal> maps = tHolder.get(date);
            maps.keySet().forEach(module -> {
                dataset.addValue(maps.get(module).doubleValue(), module, date);
            });
        });

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
