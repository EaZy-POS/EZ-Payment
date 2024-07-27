package co.id.ez.ezpay.util.swings.menu;

import co.id.ez.ezpay.enums.util.Icons;
import javax.swing.ImageIcon;

public class Model_Menu {

    public ImageIcon getIcon() {
        if(icon == null){
            return null;
        }
        return icon.get();
    }

    public String getName() {
        return name;
    }

    public MenuType getType() {
        return type;
    }

    public void setIcon(Icons icon) {
        this.icon = icon;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(MenuType type) {
        this.type = type;
    }

    public boolean isShowNotification() {
        return showNotification;
    }

    public Model_Menu(Icons icon, String name, MenuType type) {
        this(icon, name, type, false);
    }
    
    public Model_Menu(Icons icon, String name, MenuType type, boolean showNotification) {
        this.icon = icon;
        this.name = name;
        this.type = type;
        this.showNotification = showNotification;
    }

    private Icons icon;
    private String name;
    private MenuType type;
    private final boolean showNotification;

    public static enum MenuType {
        TITLE, MENU, EMPTY
    }
}
