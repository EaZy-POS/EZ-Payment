package co.id.ez.ezpay.util.swings.table.ui;

import co.id.ez.ezpay.util.swings.scrollbar.ScrollBar;
import java.awt.Color;
import java.awt.Component;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class Table extends JTable {

    public Table() {
        init();
    }

    private void init() {
        setFont(new java.awt.Font("Arial", 0, 14));
        setShowHorizontalLines(true);
        setShowVerticalLines(false);
        setGridColor(new Color(230, 230, 230));
        setRowHeight(40);
        getTableHeader().setReorderingAllowed(false);
        getTableHeader().setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable jtable,
                    Object o,
                    boolean bln,
                    boolean bln1,
                    int i,
                    int i1) {
                TableHeader header = new TableHeader(o + "");
                return header;
            }
        });
        setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable jtable,
                    Object o,
                    boolean selected,
                    boolean bln1,
                    int i,
                    int i1) {
                Component com = super.getTableCellRendererComponent(jtable, o, selected, bln1, i, i1);
                setBorder(noFocusBorder);
                if (selected) {
                    setBackground(new Color(102, 102, 102));
                    com.setForeground(Color.WHITE);
                } else {
                    com.setBackground(Color.WHITE);
                    setForeground(new Color(102, 102, 102));
                }
                return com;
            }
        });
    }

    public void fixTable(JScrollPane scroll) {
        scroll.setBorder(null);
        scroll.setVerticalScrollBar(new ScrollBar());
        scroll.getVerticalScrollBar().setBackground(Color.WHITE);
        scroll.getViewport().setOpaque(false);
        scroll.setOpaque(false);
        JPanel p = new JPanel();
        p.setBackground(Color.WHITE);
        scroll.setCorner(JScrollPane.UPPER_RIGHT_CORNER, p);
    }
}
