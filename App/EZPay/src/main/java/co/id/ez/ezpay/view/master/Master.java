/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.view.master;

import co.id.ez.ezpay.enums.util.Aligment;
import co.id.ez.ezpay.enums.util.CellType;
import co.id.ez.ezpay.util.swings.table.TableHeader;
import java.util.Arrays;
import java.util.LinkedList;

/**
 *
 * @author RCS
 */
public enum Master {

    
    
    DATA_MODULE(
            TableHeader.create("#", 10, Aligment.LEFT, CellType.CHECK),
            TableHeader.create("Modul", 120)
    ),
    DATA_SUB_MODULE(
            TableHeader.create("#", 10, Aligment.LEFT, CellType.CHECK),
            TableHeader.create("Sub Modul", 120),
            TableHeader.create("Tambah", 10, Aligment.LEFT, CellType.CHECK),
            TableHeader.create("Ubah", 10, Aligment.LEFT, CellType.CHECK),
            TableHeader.create("Hapus", 10, Aligment.LEFT, CellType.CHECK)
    ),
    POSITION(
            TableHeader.create("#", 20),
            TableHeader.create("Jabatan", 240),
            TableHeader.create("Aksi", 10, Aligment.LEFT, CellType.ACTIONPANE)
    ),
    KARYAWAN(
            TableHeader.create("#", 20),
            TableHeader.create("NIP", 30),
            TableHeader.create("Nama", 120),
            TableHeader.create("Alamat", 240),
            TableHeader.create("Phone", 30),
            TableHeader.create("Email", 60),
            TableHeader.create("Jabatan", 40),
            TableHeader.create("Status", 30),
            TableHeader.create("Aksi", 10, Aligment.LEFT, CellType.ACTIONPANE)
    ),
    USERS(
            TableHeader.create("#", 20),
            TableHeader.create("NIP", 30),
            TableHeader.create("Nama Karyawan", 120),
            TableHeader.create("Jabatan", 120),
            TableHeader.create("User ID", 120),
            TableHeader.create("Status", 30),
            TableHeader.create("Aksi", 10, Aligment.LEFT, CellType.ACTIONPANE)
    ),
    MODULE(
            TableHeader.create("#", 20),
            TableHeader.create("ID Modul", 30),
            TableHeader.create("Nama Modul", 120),
            TableHeader.create("Session", 120),
            TableHeader.create("Status", 30),
            TableHeader.create("Aksi", 10, Aligment.LEFT, CellType.ACTIONPANE)
    ),
    MODULE_SYSLIST(
            TableHeader.create("#", 20),
            TableHeader.create("ID Modul", 30),
            TableHeader.create("Nama Modul", 120),
            TableHeader.create("Path", 120),
            TableHeader.create("Status", 30),
            TableHeader.create("Aksi", 10, Aligment.LEFT, CellType.ACTIONPANE)
    ),
    PDAM_BILLER(
            TableHeader.create("#", 20),
            TableHeader.create("Biller", 30),
            TableHeader.create("Nama Biller", 120),
            TableHeader.create("Wilayah", 120),
            TableHeader.create("Status", 30),
            TableHeader.create("Aksi", 10, Aligment.LEFT, CellType.ACTIONPANE)
    ),
    PREPAID_DENOM(
            TableHeader.create("#", 20),
            TableHeader.create("Denom", 30),
            TableHeader.create("Deskripsi", 120),
            TableHeader.create("Harga Jual", 120),
            TableHeader.create("Status", 30),
            TableHeader.create("Aksi", 10, Aligment.LEFT, CellType.ACTIONPANE)
    ),
    VOUCHER_PRODUCT(
            TableHeader.create("#", 20),
            TableHeader.create("ID Voucher", 30),
            TableHeader.create("Nama Voucher", 120),
            TableHeader.create("Provider", 80),
            TableHeader.create("Tipe", 30),
            TableHeader.create("Harga Dasar", 80),
            TableHeader.create("Markup", 80),
            TableHeader.create("Harga", 80),
            TableHeader.create("Status", 30),
            TableHeader.create("Aksi", 10, Aligment.LEFT, CellType.ACTIONPANE)
    ),
    PROVIDER_VOUCHER(
            TableHeader.create("#", 20),
            TableHeader.create("Nama Provider", 120),
            TableHeader.create("Status", 30),
            TableHeader.create("Aksi", 10, Aligment.LEFT, CellType.ACTIONPANE)
    ),
    PREFIX_PROVIDER_VOUCHER(
            TableHeader.create("#", 20),
            TableHeader.create("Nama Provider", 120),
            TableHeader.create("Prefix Nomor", 120),
            TableHeader.create("Aksi", 10, Aligment.LEFT, CellType.ACTIONPANE)
    ),
    MITRA(
            TableHeader.create("#", 40),
            TableHeader.create("Mitra ID", 120),
            TableHeader.create("Nama Mitra", 180),
            TableHeader.create("Kota", 300),
            TableHeader.create("Kontak Person", 180),
            TableHeader.create("Telp/HP", 200),
            TableHeader.create("Email", 190),
            TableHeader.create("Status", 100),
            TableHeader.create("Aksi", 160, Aligment.LEFT, CellType.ACTIONPANE)
    ),
    ;

    private final LinkedList<TableHeader> tableHeader;

    private Master(TableHeader... header) {
        tableHeader = new LinkedList<>();
        tableHeader.addAll(Arrays.asList(header));
    }

    public LinkedList<TableHeader> getTableHeader() {
        return tableHeader;
    }

}
