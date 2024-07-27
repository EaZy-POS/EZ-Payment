/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.enums;

import co.id.ez.ezpay.enums.util.Aligment;
import co.id.ez.ezpay.enums.util.CellType;
import co.id.ez.ezpay.util.swings.table.TableHeader;
import java.util.Arrays;
import java.util.LinkedList;

/**
 *
 * @author Lutfi
 */
public enum MasterDataType {
    
    MultiPaymentBiller(
            "Multi Payment Biller",
            TableHeader.create("#", 60),
            TableHeader.create("Biller", 150),
            TableHeader.create("Keterangan", 180),
            TableHeader.create("Status", 120)
    ),
    VoucherProduct(
            "Voucher Product",
            true,
            "report/data/vcr_product.jrxml",
            TableHeader.create("#", 60),
            TableHeader.create("ID Voucher", 150),
            TableHeader.create("Nama Voucher", 180),
            TableHeader.create("Provider", 180),
            TableHeader.create("Tipe", 50),
            TableHeader.create("Harga", 180, Aligment.LEFT),
            TableHeader.create("Harga Jual", 180, Aligment.LEFT),
            TableHeader.create("Status", 120),
            TableHeader.create("Aksi", CellType.ACTIONPANE)
    ),
    PDAMBiller(
            "PDAM Biller",
            TableHeader.create("#", 60),
            TableHeader.create("Kode Biller", 100),
            TableHeader.create("Nama Biller", 130),
            TableHeader.create("Wilayah", 130),
            TableHeader.create("Status", 120)       
    ),
    PrepaidDenom(
            "Prepaid Denom",
            false,
            "report/data/pre_denom.jrxml",
            TableHeader.create("#", 60),
            TableHeader.create("Denom", 120),
            TableHeader.create("Deskripsi", 180),
            TableHeader.create("Harga Jual", 180, Aligment.LEFT),
            TableHeader.create("Status", 120)
    )
    ;
    
    private final LinkedList<TableHeader> tableHeader;
    private final String title, reportName;
    private final boolean isPricable;

    private MasterDataType(String titile, TableHeader... tableHeader) {
        this(titile, false, tableHeader);
    }
    
    private MasterDataType(String titile, boolean isPricable, TableHeader... tableHeader) {
        this(titile, isPricable, null, tableHeader);
    }
    
    private MasterDataType(String titile, boolean isPricable, String reportName, TableHeader... tableHeader) {
        this.tableHeader = new LinkedList<>();
        this.tableHeader.addAll(Arrays.asList(tableHeader));
        this.title = titile;
        this.isPricable = isPricable;
        this.reportName = reportName;
    }

    public LinkedList<TableHeader> getTableHeader() {
        return tableHeader;
    }

    public String getTitle() {
        return title;
    }

    public boolean isIsPricable() {
        return isPricable;
    }

    public String getReportName() {
        return reportName;
    }
    
}
