/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.util;

import javax.swing.JLabel;

/**
 *
 * @author Lutfi
 */
public class DateTimeRunner extends Thread{
    
    private final JLabel txtTarget;

    public DateTimeRunner(JLabel txtTarget) {
        this.txtTarget = txtTarget;
    }

    @Override
    public void run() {
        while (true) {
            try {
                String tDateTime = DateUtil.createDateToday() +" "+ DateUtil.getTime();
                txtTarget.setText(tDateTime +" ");
                Thread.sleep(1000);
            } catch (InterruptedException e) {

            }
        }
    }
    
}
