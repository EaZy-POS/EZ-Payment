/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.enums.report;

import co.id.ez.ezpay.enums.util.Aligment;
import co.id.ez.ezpay.util.swings.table.TableHeader;
import java.util.Arrays;
import java.util.LinkedList;

/**
 *
 * @author Lutfi
 */
public enum DailyRecapReport {
    VOUCHER(
            "report/transaction/vcr_daily_recap.jrxml",
            TableHeader.create("#", 60, Aligment.LEFT),
            TableHeader.create("Tanggal", 180, Aligment.LEFT),
            TableHeader.create("ID Transaksi", 180, Aligment.LEFT),
            TableHeader.create("No Reff", 180, Aligment.LEFT),
            TableHeader.create("User ID", 80),
            TableHeader.create("Tujuan", 140, Aligment.LEFT),
            TableHeader.create("ID Voucher", 100),
            TableHeader.create("Voucher", 100, Aligment.LEFT),
            TableHeader.create("Provider", 200),
            TableHeader.create("Serial Number", 200, Aligment.LEFT),
            TableHeader.create("Nominal", 100, Aligment.LEFT),
            TableHeader.create("Harga Jual", 80, Aligment.LEFT)
    ),
    PDAM(
            "report/transaction/pam_daily_recap.jrxml",
            TableHeader.create("#", 40, Aligment.LEFT),
            TableHeader.create("Tanggal", 80, Aligment.LEFT),
            TableHeader.create("ID Transaksi", 180, Aligment.LEFT),
            TableHeader.create("No Reff", 180, Aligment.LEFT),
            TableHeader.create("User ID", 80),
            TableHeader.create("ID Pelanggan", 120, Aligment.LEFT),
            TableHeader.create("Nama Pelanggan", 180, Aligment.LEFT),
            TableHeader.create("Tarif", 70),
            TableHeader.create("Bulan", 60, Aligment.LEFT),
            TableHeader.create("Total", 80, Aligment.LEFT)
    ),
    PREPAID(
            "report/transaction/pre_daily_recap.jrxml",
            TableHeader.create("#", 40, Aligment.LEFT),
            TableHeader.create("Tanggal", 80, Aligment.LEFT),
            TableHeader.create("ID Transaksi", 180, Aligment.LEFT),
            TableHeader.create("No Reff", 180, Aligment.LEFT),
            TableHeader.create("User ID", 80),
            TableHeader.create("ID Pelanggan", 120, Aligment.LEFT),
            TableHeader.create("MSN", 120),
            TableHeader.create("Nama Pelanggan", 180, Aligment.LEFT),
            TableHeader.create("Tarif", 70),
            TableHeader.create("Nominal", 80, Aligment.LEFT),
            TableHeader.create("KWH", 70, Aligment.CENTER),
            TableHeader.create("Token", 150, Aligment.CENTER),
            TableHeader.create("Harga Jual", 80, Aligment.LEFT)
    ),
    MULTIPAYMENT(
            "report/transaction/mp_daily_recap.jrxml",
            TableHeader.create("#", 60, Aligment.LEFT),
            TableHeader.create("Biller", 80, Aligment.LEFT),
            TableHeader.create("Tanggal", 180, Aligment.LEFT),
            TableHeader.create("ID Transaksi", 180, Aligment.LEFT),
            TableHeader.create("No Reff", 180, Aligment.LEFT),
            TableHeader.create("User ID", 80),
            TableHeader.create("ID Pelanggan", 140, Aligment.LEFT),
            TableHeader.create("Detail", 100, Aligment.LEFT),
            TableHeader.create("Total", 80, Aligment.LEFT)
    ),
    POSTPAID(
            "report/transaction/pos_daily_recap.jrxml",
            TableHeader.create("#", 40, Aligment.LEFT),
            TableHeader.create("Tanggal", 80, Aligment.LEFT),
            TableHeader.create("ID Transaksi", 180, Aligment.LEFT),
            TableHeader.create("No Reff", 180, Aligment.LEFT),
            TableHeader.create("User ID", 80),
            TableHeader.create("ID Pelanggan", 120, Aligment.LEFT),
            TableHeader.create("Nama Pelanggan", 180, Aligment.LEFT),
            TableHeader.create("Tarif", 70),
            TableHeader.create("Bulan", 60, Aligment.LEFT),
            TableHeader.create("Total", 80, Aligment.LEFT)
    ),;

    private final LinkedList<TableHeader> tableHeader;
    private final String reportName;

    private DailyRecapReport(TableHeader... tableHeaderField) {
        this(null, tableHeaderField);
    }
    
    private DailyRecapReport(String reportName, TableHeader... tableHeaderField) {
        this.tableHeader = new LinkedList<>();
        this.tableHeader.addAll(Arrays.asList(tableHeaderField));
        this.reportName = reportName;
    }

    public LinkedList<TableHeader> getTableHeader() {
        return tableHeader;
    }

    public String getReportName() {
        return reportName;
    }

}
