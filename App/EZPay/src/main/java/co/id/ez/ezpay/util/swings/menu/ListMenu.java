package co.id.ez.ezpay.util.swings.menu;

import co.id.ez.ezpay.interfaces.ListMenuConsumer;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.SwingUtilities;

public class ListMenu<E extends Object> extends JList<E> {

    private final DefaultListModel model;
    private int selectedIndex = -1;
    private ListMenuConsumer consumer;

    public ListMenu() {
        model = new DefaultListModel();
        setModel(model);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent me) {
                if (SwingUtilities.isLeftMouseButton(me)) {
                    int index = locationToIndex(me.getPoint());
                    select(index);
                }
            }
        });
    }
    
    public void select(int index) {
        selectedIndex = index;
        repaint();
        
        if(consumer != null){
            consumer.notify(selectedIndex);
        }
    }
    
    public void setConsumer(ListMenuConsumer consumer){
        this.consumer = consumer;
    }

    @Override
    public ListCellRenderer<? super E> getCellRenderer() {
        return new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> jlist, Object o, int index, boolean selected, boolean focus) {
                MenuItem item = (MenuItem) o;
                item.setSelected(selectedIndex == index);
                return item;
            }

        };
    }

    public void addItem(Model_Menu data) {
        MenuItem item = new MenuItem(data);
        model.addElement(item);
    }
    
    public void changeNotification(int index, String notif){
        if(index < model.size()){
            MenuItem item = (MenuItem) model.getElementAt(index);
            item.changeNotification(notif);
            model.setElementAt(item, index);
            repaint();
        }
    }
}
