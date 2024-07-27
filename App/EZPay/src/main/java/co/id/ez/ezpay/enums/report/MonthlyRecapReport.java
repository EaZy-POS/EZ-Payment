/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.enums.report;

import co.id.ez.ezpay.enums.util.Aligment;
import co.id.ez.ezpay.util.swings.table.TableHeader;
import co.id.ez.system.core.ex.ServiceException;
import co.id.ez.system.core.rc.RC;
import java.util.Arrays;
import java.util.LinkedList;

/**
 *
 * @author Lutfi
 */
public enum MonthlyRecapReport {
    GENERAL(
            TableHeader.create("#", 60),
            TableHeader.create("Bulan", 180),
            TableHeader.create("Tanggal", 180),
            TableHeader.create("Jumlah Transaksi", 80),
            TableHeader.create("Total", 140, Aligment.LEFT)
    ),
    MULTIPAYMENT(
            TableHeader.create("#", 60),
            TableHeader.create("Biller", 180),
            TableHeader.create("Bulan", 180),
            TableHeader.create("Tanggal", 180),
            TableHeader.create("Jumlah Transaksi", 80),
            TableHeader.create("Total", 140, Aligment.LEFT)
    ),
    BPJS(MULTIPAYMENT),
    INTERNET(MULTIPAYMENT),
    POSTPAID(),;

    private final LinkedList<TableHeader> tableHeader;
    private final MonthlyRecapReport group;

    private MonthlyRecapReport(MonthlyRecapReport parent) {
        this.tableHeader = parent.getTableHeader();
        group = parent;
    }

    private MonthlyRecapReport(TableHeader... tableHeaderField) {
        this.tableHeader = new LinkedList<>();
        this.tableHeader.addAll(Arrays.asList(tableHeaderField));
        group = null;
    }

    public static MonthlyRecapReport parse(String type) {
        for (MonthlyRecapReport value : values()) {
            if (value.name().equalsIgnoreCase(type)) {
                return value;
            }
        }

        throw new ServiceException(RC.ERROR_UNKNOWN_APPLICATION, "Invalid Request Type with Module [" + type + "]");
    }

    public LinkedList<TableHeader> getTableHeader() {
        return tableHeader;
    }

    public MonthlyRecapReport getGroup() {
        return group;
    }

}
