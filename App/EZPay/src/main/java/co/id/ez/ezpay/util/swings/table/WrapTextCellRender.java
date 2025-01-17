/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.util.swings.table;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author RCS
 */
public class WrapTextCellRender extends JTextArea implements TableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(
            JTable table, Object value, 
            boolean isSelected, boolean hasFocus,
            int row, int column) {
        
        this.setText((String) value);
        this.setWrapStyleWord(true);
        this.setLineWrap(true);

        int fontHeight = this.getFontMetrics(this.getFont()).getHeight();
        int textLength = this.getText().length();
        int lines = textLength / this.getColumnWidth();
        if (lines == 0) {
            lines = 1;
        }

        int height = fontHeight * lines;
        table.setRowHeight(row, height);

        if (isSelected == false && row % 2 == 0) {
            this.setBackground(Color.WHITE);
        } else {
            this.setBackground(this.getBackground());
        }
        
        return this;
    }

}
