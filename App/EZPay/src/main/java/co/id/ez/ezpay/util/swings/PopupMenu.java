/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.util.swings;

import co.id.ez.ezpay.interfaces.Model;
import co.id.ez.ezpay.model.data.DataSearch;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.LinkedHashMap;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

/**
 *
 * @author Lutfi
 * @param <T> class model
 */
public class PopupMenu<T extends Model>{

    private final JPopupMenu menu;
    private final JScrollPane scrollPane;
    private final JList<String> listPane;
    private int index = -1;
    private final JTextField targetPopup;
    private final DefaultListModel<String> model = new DefaultListModel<>();
    private final LinkedHashMap<String, T> dataModel = new LinkedHashMap<>();
    private T selectedModel;

    public PopupMenu(JTextField componen) {
        this(componen, true);
    }
    
    public boolean isEmpty(){
        return model.isEmpty();
    }
    
    public PopupMenu(JTextField componen, boolean isDefault) {
        menu = new JPopupMenu();
        listPane = new JList<>();
        scrollPane = new JScrollPane(listPane);
        
        listPane.setFont(new java.awt.Font("Arial", 0, 14));
        listPane.setFixedCellHeight(25);
        listPane.setVisibleRowCount(12);
        listPane.setLayoutOrientation(JList.VERTICAL);

        DefaultListModel model = new DefaultListModel();
        model.addElement("some value");
        listPane.setModel(model);
        
        if (isDefault) {
            listPane.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    if (e.getClickCount() == 2) {
                        index = listPane.getSelectedIndex();
                        setSelectedValue();
                    }
                }

            });
        }
        
        scrollPane.setViewportView(listPane);
        scrollPane.setAutoscrolls(true);
        
        menu.setBorder(BorderFactory.createLineBorder(new Color(164, 164, 164)));
        menu.add(scrollPane);
        menu.setVisible(false);
        this.targetPopup = componen;
        menu.setFocusable(false);
    }
    
    public void setOnSelected(MouseListener lister){
        listPane.addMouseListener(lister);
    }

    public void show() {
        if (model.isEmpty()) {
            hide();
        } else {
            listPane.setModel(model);
            menu.show(targetPopup, 0, targetPopup.getHeight());
            menu.setPopupSize(targetPopup.getWidth() + 100, 300);
            index = -1;
        }
    }

    public void show(DataSearch<T> data, String search) {
        model.clear();
        dataModel.clear();
        selectedModel = null;

        data.getList(search).forEach(models -> {
            dataModel.put(models.getValue(), models);
            model.addElement(models.getValue());
        });

        show();
    }
    
    public void clear(){
        model.clear();
        dataModel.clear();
        selectedModel = null;
    }
    
    public void showAll(DataSearch<T> data) {
        clear();
        data.toList().stream().map(models -> {
            dataModel.put(models.getValue(), models);
            return models;
        }).forEachOrdered(models -> {
            model.addElement(models.getValue());
        });
        
        show();
    }

    private void setSelectedIndex(boolean isNext) {
        if (isNext) {
            index++;
        } else {
            index--;
        }

        if (index < 0) {
            index = model.getSize() - 1;
        }

        if (index > model.getSize() - 1 ) {
            index = 0;
        }

        listPane.setSelectedIndex(index);
        Rectangle rect = listPane.getCellBounds(listPane.getSelectedIndex(), listPane.getSelectedIndex());
        listPane.scrollRectToVisible(rect);
    }

    public void beforeSelection() {
        setSelectedIndex(false);
    }

    public void afterSelection() {
        setSelectedIndex(true);
    }

    public void hide() {
        menu.setVisible(false);
        index = -1;
    }

    public T setSelectedValue() {
        selectedModel = null;
        if (index >= 0) {
            String tValue = listPane.getSelectedValue();
            targetPopup.setText(tValue);
            selectedModel = dataModel.get(tValue);
        }
        hide();
        return selectedModel;
    }
    
    public T setSelectedValue(int index) {
        this.index = index;
        return setSelectedValue();
    }
    
    public int getSelectedIndex(){
        return listPane.getSelectedIndex();
    }
    
    public T getSelectedModel(){
        return selectedModel;
    }
}
