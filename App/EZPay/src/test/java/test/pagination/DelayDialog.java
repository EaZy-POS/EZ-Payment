package test.pagination;

import java.awt.event.*;
import javax.swing.*;

public class DelayDialog {

    public static void main(String[] args) {
        final JScrollPane scrollPane = new JScrollPane();
        JButton show = new JButton("show table");
        show.addActionListener((ActionEvent e) -> {
            createTable(scrollPane);
        });
        JPanel north = new JPanel();
        north.add(show);
        JFrame f = new JFrame();
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.getContentPane().add(north, "North");
        f.getContentPane().add(scrollPane);
        f.setSize(400, 400);
        f.setLocation(200, 200);
        f.setVisible(true);
    }

    private static void createTable(JScrollPane scrollPane) {
        String[] headers = {
            "column 1", "column 2", "column 3", "column 4"
        };
        DataGenerator dataGenerator = new DataGenerator();
        String[][] data = dataGenerator.getData();
        JTable table = new JTable(data, headers);
        scrollPane.getViewport().setView(table);
    }
}

class DataGenerator {

    String[][] data;

    public DataGenerator() {
        JLabel label = new JLabel("Loading data...", JLabel.CENTER);
        final JDialog dialog = new JDialog();
        System.out.println("after swing worker");
        dialog.getContentPane().add(label);
        dialog.setModal(true);
        dialog.setSize(300, 160);
        dialog.setLocationRelativeTo(null);
        SwingWorker worker = new SwingWorker() {
            public Object construct() {
                return new ComplexTask();
            }

            public void finished() {
                dialog.dispose();
            }

            @Override
            protected Object doInBackground() throws Exception {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        };
        
        worker.execute();
        dialog.setVisible(true);
    }

    public String[][] getData() {
        return data;
    }

    class ComplexTask {

        public ComplexTask() {
            int rows = 64;
            int cols = 4;
            data = new String[rows][cols];
            try {
                new Thread().sleep(5000);
            } catch (InterruptedException ie) {
                System.out.println("interrupt: " + ie.getMessage());
            }
            for (int j = 0; j < rows; j++) {
                for (int k = 0; k < cols; k++) {
                    data[j][k] = " item " + (j * cols + k + 1);
                }
            }
        }
    }
}
