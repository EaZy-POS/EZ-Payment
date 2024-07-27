/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.enums.util;

import java.net.URL;
import javax.swing.ImageIcon;

/**
 *
 * @author RCS
 */
public enum Icons {
    
    ASSETS_UNIVERS("/assets/volta.png"),
    ASSETS_UNIVERS_ICO("/assets/universe.ico"),
    ASSETS_ICONS_X("/assets/icons.png"),
    ASSETS_ICONS("/assets/voltapay96.png"),
    
    WIDE_CARD("/ico/wide/card.png"),
    WIDE_ELECTBILL("/ico/wide/electbill.png"),
    WIDE_ELECTS("/ico/wide/elects.png"),
    WIDE_MOBILE("/ico/wide/mobile.png"),
    WIDE_PUMP("/ico/wide/pump.png"),
    WIDE_RECEIPT("/ico/wide/receipt.png"),
    WIDE_SEARCH("/ico/wide/search.png"),
    WIDE_SHAKEPHONE("/ico/wide/shakephone.png"),
    WIDE_VOLT("/ico/wide/volt.png"),
    WIDE_NO_IMAGE("/ico/wide/no_image.png"),
    WIDE_ESTIMATE("/ico/wide/estimate.png"),
    WIDE_PROFITS("/ico/wide/profits.png"),
    WIDE_SALES("/ico/wide/sales.png"),
    WIDE_TOTAL_SALES("/ico/wide/total_sales.png"),
    
    SMALL_ANALYZE("/ico/small/analyze.png"),
    SMALL_BOOKMARK("/ico/small/bookmark.png"),
    SMALL_CONTACS("/ico/small/contacts.png"),
    SMALL_DOCUMENT("/ico/small/document.png"),
    SMALL_CHANGE_PASSWORD("/ico/small/change_password.png"),
    SMALL_DEPOSIT("/ico/small/deposit.png"),
    SMALL_EDIT("/ico/small/edit.png"),
    SMALL_MARK("/ico/small/mark.png"),
    SMALL_REMOVE("/ico/small/remove.png"),
    SMALL_NEWS("/ico/small/news.png"),
    SMALL_PUZZLE("/ico/small/puzzle.png"),
    SMALL_RELOAD("/ico/small/reload.png"),
    SMALL_SETTING("/ico/small/settings.png"),
    SMALL_STATUS("/ico/small/status.png"),
    SMALL_SYNC("/ico/small/sync.png"),
    SMALL_VIEW("/ico/small/view.png"),
    SMALL_VERIVIED("/ico/small/verified.png"),
    SMALL_SENDTOPRINT("/ico/small/sendtoprint.png"),
    SMALL_DETAIL("/ico/small/detail.png"),
    SMALL_EXIT("/ico/small/exit.png"),
    SMALL_USER("/ico/small/user.png"),
    SMALL_CANCEL("/ico/small/cancel.png"),
    SMALL_HIDE("/ico/small/hide.png"),
    SMALL_SAVE("/ico/small/save.png"),
    SMALL_PROFILE("/ico/small/profile.png"),
    SMALL_MITRA("/ico/small/mitra.png"),
    SMALL_MASTER_DATA("/ico/small/master_data.png"),
    SMALL_USER_MANAGEMENT("/ico/small/user_management.png"),
    SMALL_OFFLINE("/ico/small/offline.png"),
    SMALL_ONLINE("/ico/small/online.png"),
    SMALL_CEK_ONLINE("/ico/small/cek_online_1.png"),
    SMALL_DASHBOARD("/ico/small/dashboard.png"),
    SMALL_TRANSACTION("/ico/small/transaction.png"),
    SMALL_MONITORING("/ico/small/monitoring.png"),
    SMALL_DATA_CENTRAL("/ico/small/data_central.png"),
    SMALL_PAGE("/ico/small/page.png"),
    SMALL_PAGE_SHITE("/ico/small/page_white.png"),
    
    LARGE_DEPOSIT("/ico/large/deposit.png"),
    LARGE_LEDGER("/ico/large/ledger.png"),
    LARGE_CANCEL("/ico/large/cancel.png"),
    LARGE_DONE("/ico/large/done.png"),
    LARGE_PAY("/ico/large/pay.png"),
    LARGE_SCOPE("/ico/large/acope.png"),
    LARGE_SEARCH("/ico/large/search.png"),
    LARGE_SEARCH_TEXT("/ico/large/search_text.png"),
    LARGE_PRINT("/ico/large/print.png"),
    LARGE_PRINT_BLUE("/ico/large/print_blue.png"),
    LARGE_FIRST("/ico/large/first.png"),
    LARGE_NEXT("/ico/large/next.png"),
    LARGE_PREV("/ico/large/prev.png"),
    LARGE_LAST("/ico/large/last.png"),
    LARGE_END("/ico/large/end.png"),
    LARGE_PREVIEW("/ico/large/preview.png"),
    LARGE_EXCEL("/ico/large/excel.png"),
    LARGE_EXPORTPDF("/ico/large/exportpdf.png"),
    LARGE_DASHBOARD("/ico/large/dashboard.png"),
    LARGE_SUM_REPORT("/ico/large/sumreport.png"),
    LARGE_TRANS_REPORT("/ico/large/transreport.png"),
    LARGE_MODULE("/ico/large/module.png"),
    LARGE_DATA_MITRA("/ico/large/data_mitra.png"),
    LARGE_TRANSACTION_MONITORING("/ico/large/transaction_monitoring.png"),
    LARGE_SELL("/ico/large/sell.png"),
    LARGE_CLOCK("/ico/large/clock.png"),
    LARGE_TRANSACTION("/ico/large/transaction.png"),
    LARGE_TRANSACTION_CHECK("/ico/large/transaction_check.png"),
    LARGE_TRANSACTION_REPORT("/ico/large/transaction_report.png"),
    LARGE_TRANSACTION_REPRINT("/ico/large/transaction_reprint.png"),
    LARGE_ESTIMATE("/ico/large/estimate.png"),
    LARGE_PRICETAG("/ico/large/price_tag.png"),
    LARGE_SYNC("/ico/large/sync.png"),
    LARGE_DATA_PROVIDER("/ico/large/data_provider.png"),
    LARGE_PROFILE("/ico/large/profile.png"),
    LARGE_USER("/ico/large/user.png"),
    LARGE_USER_MANAGEMENT("/ico/large/user_management.png"),
    LARGE_TOOLS("/ico/large/tools.png"),
    LARGE_SETTINGS("/ico/large/settings.png"),
    LARGE_LOGIN("/ico/large/login.png"),
    LARGE_LOGOUT("/ico/large/logout.png"),
    LARGE_ENTER("/ico/large/enter.png"),
    LARGE_SAVE("/ico/large/save.png"),
    LARGE_REGITRASI("/ico/large/registrasi.png"),
    LARGE_MITRA("/ico/large/mitra.png"),
    LARGE_MASTER_DATA("/ico/large/master_data.png"),
    LARGE_MASTER_POSITION("/ico/large/position.png"),
    LARGE_MASTER_EMPLOYES("/ico/large/employes.png"),
    LARGE_INTEGRATION("/ico/large/integration.png"),
    ;
    
    private final String path;

    private Icons(String path) {
        this.path = path;
    }

    public ImageIcon get() {
        URL ico = Icons.class.getResource(path);
        return new ImageIcon(ico);
    }

    public String getPath() {
        return path;
    }
    
    public String getAbsolutPath() {
        URL ico = Icons.class.getResource(path);
        return ico.getPath();
    }
    
    public URL getURLImage() {
        URL ico = Icons.class.getResource(path);
        return ico;
    }
    
}
