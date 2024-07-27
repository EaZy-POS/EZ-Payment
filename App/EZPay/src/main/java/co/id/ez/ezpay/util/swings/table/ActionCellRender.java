package co.id.ez.ezpay.util.swings.table;

import java.awt.Color;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author RAVEN
 */
public class ActionCellRender extends DefaultTableCellRenderer {

    private final TableActionEvent event;

    public ActionCellRender(TableActionEvent event) {
        this.event = event;
    }
    
    @Override
    public Component getTableCellRendererComponent(JTable jtable, Object o, 
            boolean isSeleted, boolean bln1, 
            int row, int column) {
        setHorizontalAlignment(SwingConstants.CENTER);
        Component com = super.getTableCellRendererComponent(jtable, o, isSeleted, bln1, row, column);
        ActionPane action = new ActionPane();
        action.setActiveAction(event);
        if (isSeleted == false && row % 2 == 0) {
            action.setBackground(Color.WHITE);
        } else {
            action.setBackground(com.getBackground());
        }
        return action;
    }
}
