/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.abstracts;

import co.id.ez.ezpay.controller.AppController;
import co.id.ez.ezpay.controller.MasterDataContoller;

/**
 *
 * @author Lutfi
 */
public abstract class AbstractView extends AbstractModule{
    
    public final MasterDataContoller controller = new MasterDataContoller();
    public final AppController appcontroller = new AppController();
    
    public abstract void initForm();
    public abstract void resetForm();

    public AbstractView() {
        this.setOpaque(false);
    }

    @Override
    public void loadData() {
    }
}
