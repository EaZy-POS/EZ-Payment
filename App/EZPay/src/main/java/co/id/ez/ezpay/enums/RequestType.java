/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.enums;

import co.id.ez.ezpay.enums.util.CellType;
import co.id.ez.ezpay.interfaces.DataTable;
import co.id.ez.ezpay.model.data.history.mp.MPGeneral;
import co.id.ez.ezpay.util.swings.table.TableHeader;
import co.id.ez.system.core.ex.ServiceException;
import co.id.ez.system.core.rc.RC;
import java.util.Arrays;
import java.util.LinkedList;

/**
 *
 * @author Lutfi
 */
public enum RequestType {
    VOUCHER(
            "ISI",
            TableHeader.create("#", 60),
            TableHeader.create("Tanggal", 150),
            TableHeader.create("ID Transaksi", 180),
            TableHeader.create("Tujuan", 140),
            TableHeader.create("Voucher", 100),
            TableHeader.create("Nominal", 100),
            TableHeader.create("SN", 200),
            TableHeader.create("Status", 80),
            TableHeader.create("Aksi", CellType.ACTIONPANE)
    ),
    PDAM("PDAM",
            TableHeader.create("#", 60),
            TableHeader.create("Tanggal", 150),
            TableHeader.create("ID Transaksi", 180),
            TableHeader.create("ID Pelanggan", 140),
            TableHeader.create("Nama", 300),
            TableHeader.create("Tarif", 110),
            TableHeader.create("Tagihan", 120),
            TableHeader.create("Status", 80),
            TableHeader.create("Aksi", CellType.ACTIONPANE)
    ),
    PREPAID("PREPAID",
            TableHeader.create("#", 60),
            TableHeader.create("Tanggal", 150),
            TableHeader.create("ID Transaksi", 180),
            TableHeader.create("ID Pelanggan", 120),
            TableHeader.create("MSN", 120),
            TableHeader.create("Nama", 180),
            TableHeader.create("Nominal", 120),
            TableHeader.create("Token", 200),
            TableHeader.create("Status", 80),
            TableHeader.create("Aksi", CellType.ACTIONPANE)
    ),
    POSTPAID("PLN",
            TableHeader.create("#", 60),
            TableHeader.create("Tanggal", 150),
            TableHeader.create("ID Transaksi", 180),
            TableHeader.create("ID Pelanggan", 140),
            TableHeader.create("Nama", 300),
            TableHeader.create("Tarif", 110),
            TableHeader.create("Tagihan", 120),
            TableHeader.create("Status", 80),
            TableHeader.create("Aksi", CellType.ACTIONPANE)
    ),
    MULTIPAYMENT("Pembayaran Lainnya",
            MPGeneral.class,
            TableHeader.create("#", 60),
            TableHeader.create("Tanggal", 150),
            TableHeader.create("ID Transaksi", 180),
            TableHeader.create("Produk", 100),
            TableHeader.create("ID Pelanggan", 120),
            TableHeader.create("Tagihan", 120),
            TableHeader.create("Detail", CellType.WRAP_TEXT, 400),
            TableHeader.create("Status", 80),
            TableHeader.create("Aksi", CellType.ACTIONPANE)
    ),
    BPJS("BPJS", MULTIPAYMENT),
    INTERNET("INTERNET", MULTIPAYMENT),
    CTLDATA("CTLDATA"),
    CEKKONEKSI("CEKKONEKSI"),
    PROFILE("PROF"),
    REGISTRASI("REGISTRASI")
    ;
    
    private final String module;
    private final LinkedList<TableHeader> tableHeader;
    private Class<? extends DataTable> dataTableClass;
    private final RequestType group;

    private RequestType(String module) {
        this.module = module;
        this.tableHeader = new LinkedList<>();
        group = null;
    }
    
    private RequestType(String module, RequestType parent) {
        this.module = module;
        this.tableHeader = parent.getTableHeader();
        this.dataTableClass = parent.getDataTableClass();
        group = parent;
    }
    
    private RequestType(String module, TableHeader... tableHeaderField) {
        this.module = module;
        this.tableHeader = new LinkedList<>();
        this.tableHeader.addAll(Arrays.asList(tableHeaderField));
        group = null;
    }
    
    private RequestType(String module, Class<? extends DataTable> dataTableClass, TableHeader... tableHeaderField) {
        this.module = module;
        this.tableHeader = new LinkedList<>();        
        this.tableHeader.addAll(Arrays.asList(tableHeaderField));
        this.dataTableClass = dataTableClass;
        group = null;
    }
    
    public String getModule() {
        return module;
    }

    public static RequestType parse(String type){
        for (RequestType value : values()) {
            if(value.getModule().trim().equalsIgnoreCase(type)){
                return value;
            }
        }
        
        for (RequestType value : values()) {
            if(value.name().equalsIgnoreCase(type)){
                return value;
            }
        }
        
        throw new ServiceException(RC.ERROR_UNKNOWN_APPLICATION, "Invalid Request Type with Module ["+type+"]");
    }

    public LinkedList<TableHeader> getTableHeader() {
        return tableHeader;
    }

    public Class getDataTableClass() {
        return dataTableClass;
    }

    public RequestType getGroup() {
        return group;
    }
    
}
