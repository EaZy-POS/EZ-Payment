/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.view.menu.central;

import co.id.ez.ezpay.enums.util.Icons;
import co.id.ez.ezpay.util.swings.menu.Model_Menu;

/**
 *
 * @author lutfi
 */
public enum Central {
    
    CENTRAL_DATA(
            new Model_Menu(Icons.LARGE_MODULE, "Module", Model_Menu.MenuType.MENU),
            new Model_Menu(Icons.LARGE_SELL, "Produk dan Biller", Model_Menu.MenuType.MENU)
    ),
    MITRA_DATA(
            new Model_Menu(Icons.LARGE_MITRA, "Data Mitra", Model_Menu.MenuType.MENU)
    ),
    TRANSACTION_DATA(
            new Model_Menu(Icons.LARGE_MODULE, "Data Transaksi", Model_Menu.MenuType.MENU)
    ),
    TRANSACTION_MONITORING(
            new Model_Menu(Icons.LARGE_MODULE, "Dashboard", Model_Menu.MenuType.MENU)
    ),;
    
    private final Model_Menu[] listMenu;
    
    private Central(Model_Menu... listMenu){
        this.listMenu = listMenu;
    }

    public Model_Menu[] getListMenu() {
        return listMenu;
    }
    
}
