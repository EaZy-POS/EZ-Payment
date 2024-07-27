/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.view.deposit;

import co.id.ez.ezpay.util.swings.table.TableHeader;
import java.util.Arrays;
import java.util.LinkedList;

/**
 *
 * @author RCS
 */
public enum Deposit {
    MUTASI(
        TableHeader.create("#", 60),
        TableHeader.create("Tanggal", 80),
        TableHeader.create("Jurnal", 40),
        TableHeader.create("Saldo Awal", 100),
        TableHeader.create("Jumlah", 100),
        TableHeader.create("Saldo Akhir", 100),
        TableHeader.create("ID Transaksi", 100),
        TableHeader.create("Remark", 160)
    );

    private final LinkedList<TableHeader> tableHeader;

    private Deposit(TableHeader... header) {
        tableHeader = new LinkedList<>();
        tableHeader.addAll(Arrays.asList(header));
    }

    public LinkedList<TableHeader> getTableHeader() {
        return tableHeader;
    }
}
