/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.util.swings;

import javax.swing.JPanel;

/**
 *
 * @author Lutfi
 */
public class DefaultPanel extends JPanel {

    public DefaultPanel() {
        super();
        init();
    }

    private void init(){
        setOpaque(false);
        setBorder(new javax.swing.border.LineBorder(new java.awt.Color(255, 255, 255), 1, true));
        setForeground(new java.awt.Color(27, 38, 44));
    }

}
