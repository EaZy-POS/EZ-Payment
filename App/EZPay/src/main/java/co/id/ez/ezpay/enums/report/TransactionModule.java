/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.enums.report;

/**
 *
 * @author Lutfi
 */
public enum TransactionModule {
    VOUCHER(
            DailyRecapReport.VOUCHER,
            MonthlyRecapReport.GENERAL,
            "report/transaction/vcr_monthly_recap.jrxml"
    ),
    PDAM(
            DailyRecapReport.PDAM,
            MonthlyRecapReport.GENERAL,
            "report/transaction/pam_monthly_recap.jrxml"
    ),
    PREPAID(
            DailyRecapReport.PREPAID,
            MonthlyRecapReport.GENERAL,
            "report/transaction/pre_monthly_recap.jrxml"
    ),
    MULTIPAYMENT(
            DailyRecapReport.MULTIPAYMENT,
            MonthlyRecapReport.MULTIPAYMENT,
            "report/transaction/mp_monthly_recap.jrxml"
    ),
    BPJS(MULTIPAYMENT),
    INTERNET(MULTIPAYMENT),
    POSTPAID(DailyRecapReport.POSTPAID,
            MonthlyRecapReport.GENERAL,
            "report/transaction/pos_monthly_recap.jrxml")
    ;

    private final DailyRecapReport dailyRecapReport;
    private final MonthlyRecapReport monthlyRecapReport;
    private final TransactionModule parent;
    private final String monthlyReportName;

    private TransactionModule(TransactionModule parent) {
        this(parent.dailyRecapReport, parent.monthlyRecapReport, parent, parent.getMonthlyReportName());
    }
    
    private TransactionModule(DailyRecapReport dailyRecapReport, MonthlyRecapReport monthlyRecapReport) {
        this(dailyRecapReport, monthlyRecapReport, null);
    }
    
    private TransactionModule(DailyRecapReport dailyRecapReport, MonthlyRecapReport monthlyRecapReport, String reportName) {
        this(dailyRecapReport, monthlyRecapReport, null, reportName);
    }    
    
    private TransactionModule(DailyRecapReport dailyRecapReport, MonthlyRecapReport monthlyRecapReport, TransactionModule parent, String reportName) {
        this.dailyRecapReport = dailyRecapReport;
        this.monthlyRecapReport = monthlyRecapReport;
        this.parent = parent;
        this.monthlyReportName = reportName;
    }

    public DailyRecapReport getDailyRecapReport() {
        return dailyRecapReport;
    }

    public MonthlyRecapReport getMonthlyRecapReport() {
        return monthlyRecapReport;
    }

    public TransactionModule getParent() {
        return parent;
    }

    public static TransactionModule parse(String name){
        for (TransactionModule value : values()) {
            if(value.name().equals(name)){
                return value;
            }
        }
        
        return null;
    }

    public String getMonthlyReportName() {
        return monthlyReportName;
    }
}
