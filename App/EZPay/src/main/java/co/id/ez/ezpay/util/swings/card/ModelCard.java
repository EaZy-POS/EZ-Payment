package co.id.ez.ezpay.util.swings.card;

import javax.swing.ImageIcon;

public class ModelCard {

    public ImageIcon getIcon() {
        return icon;
    }

    public void setIcon(ImageIcon icon) {
        this.icon = icon;
    }

    public String getValues() {
        return values;
    }

    public void setValues(String values) {
        this.values = values;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ModelCard(ImageIcon icon, String values, String description) {
        this.icon = icon;
        this.values = values;
        this.description = description;
    }

    public ModelCard() {
    }

    private ImageIcon icon;
    private String values;
    private String description;
}
