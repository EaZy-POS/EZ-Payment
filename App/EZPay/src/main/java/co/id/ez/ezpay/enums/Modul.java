/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.enums;

import co.id.ez.ezpay.enums.util.Icons;

/**
 *
 * @author Lutfi
 */
public enum Modul {
    
    DASHBOARD("Dashboard", Icons.LARGE_DASHBOARD),
    VOUCHER("Voucher", Icons.LARGE_MODULE, "modul", "voucher"),
    PDAM("PDAM", Icons.LARGE_MODULE, "modul", "pdam"),
    PREPAID("Prepaid", Icons.LARGE_MODULE, "modul", "prepaid"),
    POSTPAID("Postpaid", Icons.LARGE_MODULE, "modul", "pln"),
    MULTIPAYMENT("Multi Payment", Icons.LARGE_MODULE, "modul", "mp"),
    TRANSACTION_HISTORY("History Transaksi", Icons.LARGE_SUM_REPORT),
    TRANSACTION_REPORT("Laporan Transaksi", Icons.LARGE_TRANS_REPORT),
    PRODUCT_AND_BILLER("Data Produk dan Biller", Icons.LARGE_TRANS_REPORT),
    SETTINGS("Settings", Icons.LARGE_TOOLS),
    DEPOSIT("Deposit", Icons.LARGE_DEPOSIT),
    MITRA("Profil Mitra", Icons.LARGE_PROFILE),
    USERS("Pengguna", Icons.LARGE_USER),
    USER_MANAGEMENT("Manajemen Pengguna", Icons.LARGE_USER_MANAGEMENT),
    MASTER_DATA("Master Data", Icons.LARGE_MASTER_DATA),
    CENTRAL_DATA("Central Data", Icons.LARGE_MODULE),
    MITRA_DATA("Data Mitra", Icons.LARGE_DATA_MITRA),
    TRANSACTION_DATA("Data Transksi", Icons.LARGE_TRANSACTION),
    TRANSACTION_MONITORING("Data Mitra", Icons.LARGE_TRANSACTION_MONITORING),
    ;
    
    private final String title;
    private final String[] access; 
    private final Icons icon;

    private Modul(String title, Icons icon) {
        this(title, icon, "");
    }
    
    private Modul(String title, Icons icon, String... access) {
        this.title = title;
        this.icon = icon;
        this.access = access;
    }

    public String getTitle() {
        return title;
    }

    public Icons getIcons() {
        return icon;
    }

    public String[] getAccess() {
        return access;
    }
    
}
