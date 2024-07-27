/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.view.dashboard;

import co.id.ez.ezpay.app.Common;
import co.id.ez.ezpay.controller.DashboardController;
import co.id.ez.ezpay.util.DateUtil;
import co.id.ez.ezpay.util.swings.RoundPanel;
import com.json.JSONObject;
import java.awt.Color;
import java.awt.Font;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import javax.swing.BorderFactory;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author RCS
 */
public class LineChartSales extends RoundPanel {

    private final DashboardController controller = new DashboardController();
    private LinkedList<JSONObject> data;
    private JFreeChart lineChart;
    private ChartPanel chartPanel;
    private final Calendar cal = Calendar.getInstance();
    private final int max = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
    private final int min = cal.getActualMinimum(Calendar.DAY_OF_MONTH);
    private final String year = new SimpleDateFormat("yyyy").format(new Date());
    private final String month, simpleMonth;

    /**
     * Creates new form TransactionSummary
     */
    public LineChartSales() {
        this.month = DateUtil.getMonth(cal.getTime().getMonth());
        this.simpleMonth = DateUtil.getSimpleMonth(cal.getTime().getMonth());
        initComponents();
        initForm();
    }

    private void initForm() {
        unGradient();
        setRound(15);
        start();
    }

    private void loadData() {
        lineChart = ChartFactory.createLineChart(
                "Transaksi Bulan " + month + " " + year,
                "Tanggal",
                "Total Transaksi",
                createDataset(),
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        chartPanel = new ChartPanel(lineChart);
        chartPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        chartPanel.setBackground(Color.white);
        chartPanel.setPreferredSize(new java.awt.Dimension(560, 367));
        CategoryPlot plot = lineChart.getCategoryPlot();
        LineAndShapeRenderer renderer = new LineAndShapeRenderer(true, true);

        plot.setRenderer(renderer);
        plot.setBackgroundPaint(new Color(143, 148, 251));

        plot.setRangeGridlinesVisible(true);
        plot.setRangeGridlinePaint(Color.BLACK);

        plot.setDomainGridlinesVisible(true);
        plot.setDomainGridlinePaint(Color.BLACK);
        
        plot.setNoDataMessage("Tidak Ada Transaksi");
        plot.setNoDataMessageFont(new Font("Arial", 0, 13));

        lineChart.getLegend().setFrame(BlockBorder.NONE);
        lineChart.setTitle(new TextTitle("Transaksi Bulan " + month + " " + year,
                new Font("Arial", java.awt.Font.BOLD, 14)
        )
        );
        panel_chart.removeAll();
        panel_chart.repaint();
        panel_chart.revalidate();
        panel_chart.add(chartPanel);
        panel_chart.repaint();
        panel_chart.revalidate();
    }

    private void start() {
        new Thread(() -> {
            while (true) {
                try {
                    data = controller.getSummarySalesCurentMonth();
                    loadData();
                    Thread.sleep(30000);
                } catch (Exception e) {
                    Common.errorLog("[Exception] Error load data histori", e);
                }

            }
        }).start();
    }

    private DefaultCategoryDataset createDataset() {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        if (data != null) {
            LinkedHashMap<String, LinkedHashMap<Integer, Double>> maps = new LinkedHashMap<>();
            data.forEach(rows -> {
                String module = rows.getString("module");
                double amount = rows.getDouble("total_amount");
                int date = rows.getInt("transaction_date");

                if (maps.containsKey(module)) {
                    maps.get(module).put(date, amount);
                } else {
                    LinkedHashMap<Integer, Double> tmp = new LinkedHashMap<>();
                    tmp.put(date, amount);
                    maps.put(module, tmp);
                }
            });

            maps.keySet().forEach(module -> {
                LinkedHashMap<Integer, Double> mData = maps.get(module);
                for (int i = min; i <= max; i++) {
                    if (mData.containsKey(i)) {
                        dataset.addValue(mData.get(i), module, String.valueOf(i));
                    } else {
                        dataset.addValue(0, module, String.valueOf(i));
                    }
                }
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

        panel_chart = new javax.swing.JPanel();

        setBackground(new java.awt.Color(255, 255, 255));

        panel_chart.setOpaque(false);
        panel_chart.setLayout(new java.awt.CardLayout());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panel_chart, javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(panel_chart, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel panel_chart;
    // End of variables declaration//GEN-END:variables
}
