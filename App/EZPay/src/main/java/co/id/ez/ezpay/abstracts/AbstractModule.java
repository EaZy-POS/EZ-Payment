/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.abstracts;

import co.id.ez.database.query.QueryBuilder;
import co.id.ez.ezpay.app.App;
import co.id.ez.ezpay.app.AppSetting;
import co.id.ez.ezpay.app.Common;
import co.id.ez.ezpay.app.ConnectionManager;
import co.id.ez.ezpay.app.ProfileManager;
import co.id.ez.ezpay.controller.TransactionModuleController;
import co.id.ez.ezpay.enums.util.Aligment;
import co.id.ez.ezpay.enums.Headers;
import co.id.ez.ezpay.enums.MessageType;
import co.id.ez.ezpay.enums.ReceiptFormat;
import co.id.ez.ezpay.model.data.detail.DetailDataFactory;
import co.id.ez.ezpay.model.data.detail.Field;
import co.id.ez.ezpay.util.swings.PopupMenu;
import co.id.ez.ezpay.msg.BillerMessage;
import co.id.ez.ezpay.msg.BillerResponse;
import co.id.ez.ezpay.util.swings.ReportViewer;
import co.id.ez.system.core.config.ConfigService;
import co.id.ez.system.core.etc.EncryptionService;
import co.id.ez.system.core.ex.ServiceException;
import co.id.ez.system.core.log.LogService;
import co.id.ez.system.core.rc.RC;
import com.json.JSONObject;
import com.toedter.calendar.JDateChooser;
import com.toedter.calendar.JMonthChooser;
import com.toedter.calendar.JYearChooser;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.swing.JPanel;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import org.testcontainers.shaded.okhttp3.MediaType;
import org.testcontainers.shaded.okhttp3.OkHttpClient;
import org.testcontainers.shaded.okhttp3.Request;
import org.testcontainers.shaded.okhttp3.RequestBody;
import org.testcontainers.shaded.okhttp3.Response;

/**
 *
 * @author RCS
 */
public abstract class AbstractModule extends JPanel {

    public static final TransactionModuleController transactionController = new TransactionModuleController();
    public static String dbName = "payment";
    public static String cModuleNameSpace = "app-config.";
    public static String cTimeoutKey = "service.timeout";
    protected BillerResponse billerResponse;
    protected PopupMenu popupMenu;

    public abstract void initForm();

    public abstract void resetForm();

    public abstract void loadData();

    public void performInquiry() {
    }

    public void performPayment() {
    }

    public void initListener() {
    }

    public void search() {
    }

    public void updateDataTransaction() {
    }

    public AbstractModule() {
        Common.setOpaqueComponent(false, this);
    }

    public QueryBuilder contructQueryInsertTransaction() {
        return null;
    }

    public boolean validateForm() {
        return true;
    }

    public static boolean dbConnectionCheck() {
        try {
            transactionController.connectionCek();
            System.out.println("Koneksi Database success");
            return true;
        } catch (SQLException e) {
            Common.showErrorMessage("Koneksi Database gagal!", null);
            Common.errorLog("Gagal Koneksi ke Database!", e);
        } catch (Exception e) {
            Common.showErrorMessage("Koneksi Database gagal!", null);
            Common.errorLog("Gagal Koneksi ke Database!", e);
        }

        return false;
    }

    public void setCurentDate(JDateChooser... date_picker) {
        Date curentDate = new Date();
        for (JDateChooser datepicker : date_picker) {
            datepicker.setDate(curentDate);
        }
    }

    public void setCurentMonth(JMonthChooser... date_picker) {
        Date curentDate = new Date();
        for (JMonthChooser datepicker : date_picker) {
            datepicker.setMonth(curentDate.getMonth());
        }
    }

    public void setCurentYear(JYearChooser... date_picker) {
        Date curentDate = new Date();
        for (JYearChooser datepicker : date_picker) {
            datepicker.setYear(curentDate.getMonth());
        }
    }

    public BillerResponse sendRequest(BillerMessage request) {
        return sendRequest(request, "POST");
    }

    public BillerResponse sendRequest(BillerMessage request, String method) {
        return sendRequest(request, method, false);
    }

    public BillerResponse sendRequest(BillerMessage request, String method, boolean isToCentrl) {
        final List<BillerResponse> holder = new ArrayList<>();

        SwingWorker<BillerResponse, Void> sw = new SwingWorker<BillerResponse, Void>() {
            BillerResponse billerResponse;

            @Override
            protected BillerResponse doInBackground() throws Exception {
                billerResponse = workerSendRequest(request, method, isToCentrl);
                holder.add(billerResponse);
                return null;
            }

            @Override
            protected void done() {
                Common.hideLoadingTask();
            }
        };

        Common.showLoadingTask(sw);
        
        int timeout = ConfigService.getInstance().getInt(cTimeoutKey);
        while (timeout > 0) {            
            if(holder.isEmpty()){
                try {
                    timeout = timeout - 500;
                    Thread.sleep(500);
                } catch (Exception e) {
                }
            }else{
                timeout = 0;
            }
        }
        
        return holder.size() > 0 ? holder.get(0) : null;
    }

    public BillerResponse workerSendRequest(BillerMessage request, String method, boolean isToCentrl) {
        try {
            if (isValidConnection()) {
                if (method.equalsIgnoreCase("POST")) {
                    String tResponse = sendPostRequest(request, isToCentrl);
                    if (!tResponse.equals("") && tResponse.startsWith("{")) {
                        JSONObject tBillerResponse = new JSONObject(tResponse);
                        switch (tBillerResponse.get("rc").toString()) {
                            case "2000000":
                                return new BillerResponse(tBillerResponse);
                            case "2000005":
                            case "2000068":
                            case "4020052":
                                if (request.getCommand() != null && request.getCommand().equalsIgnoreCase("PAY")) {
                                    Common.showWarningMessage("Suspect - Transaksi pending"
                                            + "\nSegera lakukan cek tagihan secara berkala!", this);
                                    return new BillerResponse(tBillerResponse);
                                }
                            case "4040063":
                                if (request.getCommand() != null && request.getCommand().equalsIgnoreCase("PAY")) {
                                    Common.showWarningMessage("Suspect - Transaksi pending"
                                            + "\nSegera lakukan cek tagihan secara berkala!", this);
                                    return new BillerResponse(tBillerResponse);
                                }

                                if (request.getCommand() != null && request.getCommand().equalsIgnoreCase("ADV")) {
                                    Common.showWarningMessage("Gagal - Status transaksi pembayaran tidak ditemukan", this);
                                    return new BillerResponse(tBillerResponse);
                                }
                            case "4040096":
                                if (request.getCommand() != null && request.getCommand().equalsIgnoreCase("ADV")) {
                                    Common.showWarningMessage("Gagal - Transaksi pembayaran tidak ditemukan", this);
                                    return new BillerResponse(tBillerResponse);
                                }
                            case "4040094":
                                if (request.getCommand() != null && request.getCommand().equalsIgnoreCase("ADV")) {
                                    Common.showWarningMessage("Gagal - Transaksi pembayaran dibatalkan", this);
                                    return new BillerResponse(tBillerResponse);
                                }
                            default:
                                Common.showWarningMessage("Gagal - " + tBillerResponse.getString("rc")
                                        + " - " + tBillerResponse.getString("rcm"), this);
                                break;
                        }
                    } else {
                        if (request.getCommand() != null && request.getCommand().equalsIgnoreCase("PAY")) {
                            Common.showWarningMessage("Suspect - Waktu koneksi habis"
                                    + "\nSegera lakukan cek tagihan secara berkala!", this);
                            JSONObject tSuspectResponse = request.getBodyRequest();
                            tSuspectResponse.put("rc", "2000068");
                            return new BillerResponse(tSuspectResponse);
                        } else {
                            Common.showWarningMessage("Gagal- Waktu koneksi habis", this);
                        }
                    }
                } else if (method.equalsIgnoreCase("GET")) {
                    String tResponse = sendGetRequest(request, isToCentrl);
                    if (!tResponse.equals("") && tResponse.startsWith("{")) {
                        JSONObject tBillerResponse = new JSONObject(tResponse);
                        switch (tBillerResponse.get("rc").toString()) {
                            case "2000000":
                                return new BillerResponse(tBillerResponse);
                            default:
                                Common.showWarningMessage("Gagal- " + tBillerResponse.getString("rc")
                                        + " - " + tBillerResponse.getString("rcm"), this);
                                break;
                        }
                    } else {
                        Common.showWarningMessage("Gagal- Waktu koneksi habis", this);
                    }
                }
            } else {
                Common.showWarningMessage("Status koneksi offline, silahkan periksa jaringan anda!", this);
            }
        } catch (ServiceException e) {
            Common.errorLog("[ServiceException] Failed to execute sendRequest", e);
            Common.showErrorMessage("Error: " + e.getMessage(), this);
        } catch (Exception e) {
            Common.errorLog("[Exception] Failed to execute sendRequest", e);
            Common.showErrorMessage(MessageType.FATAL_ERROR, this);
        }
        return null;
    }

    public boolean isValidConnection() {
        return ConnectionManager.instance().isAllConnectionSuccess();
    }

    private String sendPostRequest(BillerMessage request, boolean isToCTL) {
        String tResponse = "";
        String tBaseUrl = isToCTL
                ? App.environment().getCentralUrl()
                : App.environment().getGatewayUrl();
        int timeout = ConfigService.getInstance().getInt(cTimeoutKey);

        LogService.getInstance(this).stream().log(request.getReqtype()
                + " REQUEST to HTTP body: [{}]", request.getMessageStream());

        try {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .connectTimeout(20000, TimeUnit.MILLISECONDS)
                    .readTimeout(timeout, TimeUnit.MILLISECONDS)
                    .build();

            MediaType mediaType = MediaType.parse("application/json");
            RequestBody body = RequestBody.create(mediaType, request.getMessageStream());
            Request.Builder builder = new Request.Builder()
                    .url(tBaseUrl.concat(request.getPath()))
                    .method("POST", body)
                    .addHeader("Accept", "application/json")
                    .addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0;Windows98;DigExt)")
                    .addHeader("charset", "utf-8")
                    .addHeader("Content-type", "application/json");

            if (request.isNeedHeader()) {
                if (request.getReqtype() != null) {
                    String tModuleID = request.getReqtype().getModule();
                    String tCurentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                    String tSign = generateSignature(
                            tModuleID,
                            request.getMessageStream().replace("\n", "").replace("\t", " ").replace("\r", ""),
                            tCurentDate
                    );

                    builder.addHeader("module_id", tModuleID);
                    builder.addHeader("date", tCurentDate);
                    builder.addHeader("signature", tSign);
                }

                if (request.getReqtype() != null) {
                    HashMap<Headers, String> appEnv = App.environment().get();
                    appEnv.keySet().forEach(headers -> {
                        if (appEnv.get(headers) != null) {
                            builder.addHeader(headers.getKey(), appEnv.get(headers));
                        }
                    });
                }
            }

            Request httpRequest = builder.build();

            Response response = client.newCall(httpRequest).execute();
            tResponse = response.body().string();
        } catch (IOException e) {
            LogService.getInstance(this)
                    .error()
                    .withCause(e)
                    .log("[IOException] Error No Response from biller. ", true);
        }

        LogService.getInstance(this).stream().log(request.getReqtype()
                + " RESPONSE from HTTP body: [{}]", tResponse);
        return tResponse;
    }

    public String sendGetRequest(BillerMessage request, boolean isToCtl) {
        return sendGetRequest(request, isToCtl, null);
    }

    public String sendGetRequest(BillerMessage request, boolean isToCtl, String pLogName) {
        String tResponse = "";

        String tBaseUrl = isToCtl
                ? App.environment().getCentralUrl()
                : App.environment().getGatewayUrl();
        int timeout = ConfigService.getInstance().getInt(cTimeoutKey);

        if (pLogName == null) {
            LogService
                    .getInstance(this)
                    .stream()
                    .log(request.getReqtype()
                            + " REQUEST to HTTP body: [{}]", request.getMessageStream());
        } else {
            LogService
                    .getInstance(this)
                    .temp(pLogName)
                    .log(request.getReqtype()
                            + " REQUEST to HTTP body: [{}]", request.getMessageStream());
        }

        try {
            OkHttpClient client = new OkHttpClient().newBuilder()
                    .connectTimeout(20000, TimeUnit.MILLISECONDS)
                    .readTimeout(timeout, TimeUnit.MILLISECONDS)
                    .build();

            Request.Builder builder = new Request.Builder()
                    .url(tBaseUrl.concat(request.getPath()))
                    .get()
                    .addHeader("Accept", "application/json")
                    .addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0;Windows98;DigExt)")
                    .addHeader("charset", "utf-8")
                    .addHeader("Content-type", "application/json");

            if (request.isNeedHeader()) {
                if (request.getReqtype() != null) {
                    String tModuleID = request.getReqtype().getModule();
                    String tCurentDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
                    String tSign = generateSignature(
                            tModuleID,
                            request.getMessageStream().replace("\n", "").replace("\t", " ").replace("\r", ""),
                            tCurentDate
                    );

                    builder.addHeader("module_id", tModuleID);
                    builder.addHeader("date", tCurentDate);
                    builder.addHeader("signature", tSign);
                }

                if (request.getReqtype() != null) {
                    HashMap<Headers, String> appEnv = App.environment().get();
                    appEnv.keySet().forEach(headers -> {
                        if (appEnv.get(headers) != null) {
                            builder.addHeader(headers.getKey(), appEnv.get(headers));
                        }
                    });
                }
            }

            Request httpRequest = builder.build();

            Response response = client.newCall(httpRequest).execute();
            tResponse = response.body().string();
        } catch (IOException e) {
            throw new ServiceException(RC.ERROR_TRANSACTION_FAILED_FROM_VENDING, "Error Response from biller. ", e);
        }

        if (pLogName == null) {
            LogService
                    .getInstance(this)
                    .stream()
                    .log(request.getReqtype()
                            + " RESPONSE from HTTP body: [{}]", tResponse);
        } else {
            LogService
                    .getInstance(this)
                    .temp(pLogName)
                    .log(request.getReqtype()
                            + " RESPONSE from HTTP body: [{}]", tResponse);
        }
        return tResponse;
    }

    private String generateSignature(String pModuleID, String pBody, String pDate) {
        String tHashTarget = pModuleID.concat(":")
                .concat(pBody)
                .concat(":")
                .concat(pDate)
                .concat(":")
                .concat(App.environment().getDown_mac());

        String tKey = App.environment().getAcc_pocky();

        String tSignature = EncryptionService.encryptor()
                .hashSHA256(
                        EncryptionService
                                .encryptor()
                                .Base64Encrypt(
                                        tHashTarget
                                ).concat("#")
                                .concat(tKey)
                                .concat("#")
                );

        return tSignature;
    }

    public void prePostPayment(String pTableName, BillerResponse pBillerResponse) throws SQLException {
        transactionController.updatePrePostRequest(pTableName, pBillerResponse);
    }

    public void saveTransaction() throws SQLException {
        transactionController.saveTransactionToTranmain(contructQueryInsertTransaction());
    }

    public void previewStruk(String pTrxID, String pTableName) {
        try {
            JSONObject tranmaindata = transactionController.getTransactionData(pTrxID, pTableName);
            Common.showDetailForm(constructStrukData(tranmaindata), this, "Struk Pembayaran", true);
        } catch (SQLException e) {
            LogService
                    .getInstance(this)
                    .dbError()
                    .withCause(e)
                    .log("[SQLException] Failed preview struk", true);
            Common.showErrorMessage(MessageType.SYSTEM_ERROR, this);
        } catch (ServiceException e) {
            LogService
                    .getInstance(this)
                    .error()
                    .withCause(e)
                    .log("[ServiceException] Failed preview struk", true);
            Common.showErrorMessage(e.getMessage(), this);
        } catch (Exception e) {
            LogService
                    .getInstance(this)
                    .error()
                    .withCause(e)
                    .log("[Exception] Failed preview struk", true);
            Common.showErrorMessage(MessageType.SYSTEM_ERROR, this);
        }
    }

    public DetailDataFactory constructStrukData(JSONObject pTranmaindata) {
        ReceiptFormat receipt = AppSetting.instance().getReceiptFormat();

        int labelSize = receipt.getLabelSize();
        int dasSize = receipt.getDashSize();
        int valueSize = receipt.getValueSize();
        int papreSize = labelSize + dasSize + valueSize;
        DetailDataFactory dataList = new DetailDataFactory(papreSize);

        getHeaderStruk(papreSize).forEach(header -> {
            dataList.create(header);
        });

        String tanggal = pTranmaindata.get("paid_date").toString().replace("T", " ");
        String trxid = pTranmaindata.get("transaction_id").toString();
        String kasir = pTranmaindata.get("user_id").toString();

        dataList.create(
                Field.bind("Tanggal", labelSize, Aligment.LEFT, " "),
                Field.bind(": ", dasSize),
                Field.bind(tanggal, valueSize, Aligment.RIGHT, " ")
        );

        dataList.create(
                Field.bind("Trans ID", labelSize, Aligment.LEFT, " "),
                Field.bind(": ", dasSize),
                Field.bind(trxid, valueSize, Aligment.RIGHT, " ")
        );

        dataList.create(
                Field.bind("Kasir", labelSize, Aligment.LEFT, " "),
                Field.bind(": ", dasSize),
                Field.bind(kasir, valueSize, Aligment.RIGHT, " ")
        );

        dataList.create(
                Field.bind("-", papreSize, Aligment.CENTER, "-")
        );

        dataList.create(createBodyStruk(pTranmaindata));

        dataList.create(
                Field.bind("-", papreSize, Aligment.CENTER, "-")
        );

        getFooterStruk(papreSize).forEach(field -> {
            dataList.create(field);
        });

        return dataList;
    }

    private List<Field> getHeaderStruk(int pPapreSize) {
        List<Field> fields = new ArrayList<>();
        fields.add(
                Field.bind(
                        ProfileManager.instance().getMitra_name(),
                        pPapreSize
                )
        );

        fields.add(
                Field.bind(
                        ProfileManager.instance().getAddress().concat(", ")
                                .concat(ProfileManager.instance().getCity()),
                        pPapreSize
                )
        );

        fields.add(
                Field.bind(
                        "No Hp. ".concat(ProfileManager.instance().getWa_number()),
                        pPapreSize
                )
        );

        fields.add(
                Field.bind(
                        ("Email: ").concat(ProfileManager.instance().getEmail()),
                        pPapreSize
                )
        );

        fields.add(
                Field.bind(
                        "-", pPapreSize, "-"
                )
        );

        return fields;
    }

    public List<Field> getFooterStruk(int pPaperSize) {
        List<Field> fields = new ArrayList<>();
        String footer = AppSetting.instance().getString("footer");

        if (footer != null) {
            String[] footers = footer.split(";");
            for (String footer1 : footers) {
                String result = EncryptionService
                        .encryptor()
                        .decrypt(
                                footer1.replace("[", "").replaceAll("]", "").replaceAll("<>", "="),
                                App.environment().getEnv_key()
                        );
                fields.add(Field.bind(result, pPaperSize));
            }
        }

        return fields;
    }

    public Field[][] createBodyStruk(JSONObject pTranmainData) {
        return new Field[0][0];
    }

    public Field[][] createBodyStrukVoucher(JSONObject pTranmainData) {
//        int papreSize = AppSetting.instance().getPrinterStringLength();
        ReceiptFormat receipt = AppSetting.instance().getReceiptFormat();

        int labelSize = receipt.getLabelSize();
        int dasSize = receipt.getDashSize();
        int valueSize = receipt.getValueSize();
        int papreSize = labelSize + dasSize + valueSize;
        String tDestNumber = pTranmainData.get("dest_number").toString();
        String tVoucher = pTranmainData.get("voucher_id").toString();
        String tNominal = pTranmainData.get("nominal").toString();
        String tHargaJual = Common.formatRupiah(pTranmainData.getDouble("harga_jual")).replace("Rp. ", "");
        String tSerialNumber = pTranmainData.get("serial_number").toString();

        Field[][] field = new Field[][]{
            {
                Field.bind("Tujuan ", labelSize, Aligment.LEFT, " "),
                Field.bind(": ", dasSize),
                Field.bind(tDestNumber, valueSize, Aligment.RIGHT, " ")
            },
            {
                Field.bind("Voucher ", labelSize, Aligment.LEFT, " "),
                Field.bind(": ", dasSize),
                Field.bind(tVoucher, valueSize, Aligment.RIGHT, " ")
            },
            {
                Field.bind("Nominal ", labelSize, Aligment.LEFT, " "),
                Field.bind(": ", dasSize),
                Field.bind(tNominal, valueSize, Aligment.RIGHT, " ")
            },
            {
                Field.bind(" SN ", papreSize, Aligment.CENTER, "-")
            },
            {
                Field.bind(tSerialNumber, papreSize, Aligment.CENTER, " ")
            },
            {
                Field.bind("-", papreSize, "-")
            },
            {
                Field.bind("Total ", labelSize, Aligment.LEFT, " "),
                Field.bind(": Rp. ", 6),
                Field.bind(tHargaJual, (papreSize - labelSize - 6), Aligment.RIGHT, " ")
            }
        };

        return field;
    }

    public Field[][] createBodyStrukPrepaid(JSONObject pTranmainData) {
        ReceiptFormat receipt = AppSetting.instance().getReceiptFormat();

        int labelSize = receipt.getLabelSize();
        int dasSize = receipt.getDashSize();
        int valueSize = receipt.getValueSize();
        int papreSize = labelSize + dasSize + valueSize;
        String tSubID = pTranmainData.get("subscriber_id").toString();
        String tMSN = pTranmainData.get("msn").toString();
        String tNama = pTranmainData.get("subscriber_name").toString();
        String tTarif = pTranmainData.get("segmentation").toString();
        String tKwh = pTranmainData.get("kwh").toString();
        String tNominal = Common.formatRupiah(pTranmainData.getDouble("nominal"), false);
        String tHargaJual = Common.formatRupiah(pTranmainData.getDouble("harga_jual"), false);
        String tToken = pTranmainData.get("token").toString();

        Field[][] field = new Field[][]{
            {
                Field.bind("No Pel/MSN ", labelSize, Aligment.LEFT, " "),
                Field.bind(": ", dasSize),
                Field.bind(tSubID + "/" + tMSN, valueSize, Aligment.RIGHT, " ")
            },
            {
                Field.bind("Nama ", labelSize, Aligment.LEFT, " "),
                Field.bind(": ", dasSize),
                Field.bind(tNama, valueSize, Aligment.RIGHT, " ")
            },
            {
                Field.bind("Tarif ", labelSize, Aligment.LEFT, " "),
                Field.bind(": ", dasSize),
                Field.bind(tTarif, valueSize, Aligment.RIGHT, " ")
            },
            {
                Field.bind("Nominal ", labelSize, Aligment.LEFT, " "),
                Field.bind(": Rp. ", 6),
                Field.bind(tNominal, (papreSize - labelSize - 6), Aligment.RIGHT, " ")
            },
            {
                Field.bind("Kwh ", labelSize, Aligment.LEFT, " "),
                Field.bind(": ", dasSize),
                Field.bind(tKwh, valueSize, Aligment.RIGHT, " ")
            },
            {
                Field.bind(" Token ", papreSize, "-")
            },
            {
                Field.bind(tToken, papreSize, Aligment.CENTER, " ")
            },
            {
                Field.bind("-", papreSize, "-")
            },
            {
                Field.bind("Total Bayar", labelSize, Aligment.LEFT, " "),
                Field.bind(": Rp. ", 6),
                Field.bind(tHargaJual, (papreSize - labelSize - 6), Aligment.RIGHT, " ")
            }
        };

        return field;
    }

    public Field[][] createBodyStrukPDAM(JSONObject pTranmainData) {
        ReceiptFormat receipt = AppSetting.instance().getReceiptFormat();

        int labelSize = receipt.getLabelSize();
        int dasSize = receipt.getDashSize();
        int valueSize = receipt.getValueSize();
        int papreSize = labelSize + dasSize + valueSize;
        String tSubID = pTranmainData.get("subscriber_id").toString();
        String tNama = pTranmainData.get("subscriber_name").toString();
        String tTarif = pTranmainData.get("subscriber_segmentation").toString();
        int tBillstatus = pTranmainData.getInt("bill_status");

        String tTagihan = Common.formatRupiah(pTranmainData.getDouble("total_bill"), false);
        String tAdmin = Common.formatRupiah(pTranmainData.getDouble("total_admin_charge"), false);
        String tTotal = Common.formatRupiah(pTranmainData.getDouble("total_bill_amount"), false);

        List<Field[]> list = new ArrayList<>();

        list.add(new Field[]{
            Field.bind("No Pelanggan", labelSize, Aligment.LEFT, " "),
            Field.bind(": ", dasSize),
            Field.bind(tSubID, valueSize, Aligment.RIGHT, " ")
        }
        );

        list.add(new Field[]{
            Field.bind("Nama Pelanggan", labelSize, Aligment.LEFT, " "),
            Field.bind(": ", dasSize),
            Field.bind(tNama, valueSize, Aligment.RIGHT, " ")
        }
        );

        list.add(new Field[]{
            Field.bind("Golongan", labelSize, Aligment.LEFT, " "),
            Field.bind(": ", dasSize),
            Field.bind(tTarif, valueSize, Aligment.RIGHT, " ")
        }
        );

        for (int i = 0; i < tBillstatus; i++) {
            BigDecimal tag = new BigDecimal(pTranmainData.getDouble("bill_amount_" + (i + 1)));
            BigDecimal denda = new BigDecimal(pTranmainData.getDouble("bill_penalty_" + (i + 1)));
            BigDecimal adm = new BigDecimal(pTranmainData.getDouble("bill_adm_" + (i + 1)));
            BigDecimal billadm = new BigDecimal(pTranmainData.getDouble("bill_adm_pdam_" + (i + 1)));
            BigDecimal instalment = new BigDecimal(pTranmainData.getDouble("bill_installment_" + (i + 1)));
            BigDecimal danameter = new BigDecimal(pTranmainData.getDouble("bill_danameter_" + (i + 1)));
            BigDecimal vat = new BigDecimal(pTranmainData.getDouble("bill_vat_" + (i + 1)));
            BigDecimal waste = new BigDecimal(pTranmainData.getDouble("bill_waste_" + (i + 1)));
            BigDecimal materai = new BigDecimal(pTranmainData.getDouble("bill_stamp_" + (i + 1)));

            BigDecimal totalBill = tag.add(denda).add(adm).add(billadm).add(instalment)
                    .add(danameter).add(vat).add(waste).add(materai);

            String tBulan = pTranmainData.getString("periode_" + (i + 1));
            int meterawal = pTranmainData.getInt("meter_start_" + (i + 1));
            int meterakhir = pTranmainData.getInt("meter_end_" + (i + 1));
            int totalMeter = pTranmainData.getInt("meter_usage_" + (i + 1));

            list.add(new Field[]{
                Field.bind(" Tagihan Ke-" + (i + 1) + " ", papreSize, Aligment.CENTER, "-")
            }
            );

            list.add(new Field[]{
                Field.bind("Periode ", labelSize, Aligment.LEFT, " "),
                Field.bind(": ", dasSize),
                Field.bind(tBulan, valueSize, Aligment.RIGHT, " ")
            }
            );

            list.add(new Field[]{
                Field.bind("Meter Awal ", labelSize, Aligment.LEFT, " "),
                Field.bind(": ", dasSize),
                Field.bind(String.valueOf(meterawal), valueSize, Aligment.RIGHT, " ")
            }
            );

            list.add(new Field[]{
                Field.bind("Meter Akhir ", labelSize, Aligment.LEFT, " "),
                Field.bind(": ", dasSize),
                Field.bind(String.valueOf(meterakhir), valueSize, Aligment.RIGHT, " ")
            }
            );

            list.add(new Field[]{
                Field.bind("Total Meter ", labelSize, Aligment.LEFT, " "),
                Field.bind(": ", dasSize),
                Field.bind(String.valueOf(totalMeter), valueSize, Aligment.RIGHT, " ")
            }
            );

            list.add(new Field[]{
                Field.bind("Tagihan ", labelSize, Aligment.LEFT, " "),
                Field.bind(": Rp. ", 6),
                Field.bind(Common.formatRupiah(tag.doubleValue(), false), (papreSize - labelSize - 6), Aligment.RIGHT, " ")
            }
            );

            list.add(new Field[]{
                Field.bind("Denda ", labelSize, Aligment.LEFT, " "),
                Field.bind(": Rp. ", 6),
                Field.bind(Common.formatRupiah(denda.doubleValue(), false), (papreSize - labelSize - 6), Aligment.RIGHT, " ")
            }
            );

            list.add(new Field[]{
                Field.bind("Angsuran ", labelSize, Aligment.LEFT, " "),
                Field.bind(": Rp. ", 6),
                Field.bind(Common.formatRupiah(instalment.doubleValue(), false), (papreSize - labelSize - 6), Aligment.RIGHT, " ")
            }
            );

            list.add(new Field[]{
                Field.bind("Dana Meter ", labelSize, Aligment.LEFT, " "),
                Field.bind(": Rp. ", 6),
                Field.bind(Common.formatRupiah(danameter.doubleValue(), false), (papreSize - labelSize - 6), Aligment.RIGHT, " ")
            }
            );

            list.add(new Field[]{
                Field.bind("Retribusi ", labelSize, Aligment.LEFT, " "),
                Field.bind(": Rp. ", 6),
                Field.bind(Common.formatRupiah(vat.doubleValue(), false), (papreSize - labelSize - 6), Aligment.RIGHT, " ")
            }
            );

            list.add(new Field[]{
                Field.bind("Retribusi ", labelSize, Aligment.LEFT, " "),
                Field.bind(": Rp. ", 6),
                Field.bind(Common.formatRupiah(vat.doubleValue(), false), (papreSize - labelSize - 6), Aligment.RIGHT, " ")
            }
            );

            list.add(new Field[]{
                Field.bind("Materai ", labelSize, Aligment.LEFT, " "),
                Field.bind(": Rp. ", 6),
                Field.bind(Common.formatRupiah(materai.doubleValue(), false), (papreSize - labelSize - 6), Aligment.RIGHT, " ")
            }
            );

            list.add(new Field[]{
                Field.bind("Lain-lain ", labelSize, Aligment.LEFT, " "),
                Field.bind(": Rp. ", 6),
                Field.bind(Common.formatRupiah(waste.doubleValue(), false), (papreSize - labelSize - 6), Aligment.RIGHT, " ")
            }
            );

            list.add(new Field[]{
                Field.bind("Admin PDAM", labelSize, Aligment.LEFT, " "),
                Field.bind(": Rp. ", 6),
                Field.bind(Common.formatRupiah(adm.doubleValue(), false), (papreSize - labelSize - 6), Aligment.RIGHT, " ")
            }
            );

            list.add(new Field[]{
                Field.bind("Admin ", labelSize, Aligment.LEFT, " "),
                Field.bind(": Rp. ", 6),
                Field.bind(Common.formatRupiah(billadm.doubleValue(), false), (papreSize - labelSize - 6), Aligment.RIGHT, " ")
            }
            );

            list.add(new Field[]{
                Field.bind("Total ", labelSize, Aligment.LEFT, " "),
                Field.bind(": Rp. ", 6),
                Field.bind(Common.formatRupiah(totalBill.doubleValue(), false), (papreSize - labelSize - 6), Aligment.RIGHT, " ")
            }
            );
        }

        Field[][] field = new Field[list.size() + 4][];

        int index = 0;
        for (Field[] fields : list) {
            field[index] = fields;
            index++;
        }

        field[index] = new Field[]{
            Field.bind("-", papreSize, "-")
        };
        index++;

        field[index] = new Field[]{
            Field.bind("Total Tagihan", labelSize, Aligment.LEFT, " "),
            Field.bind(": Rp. ", 6),
            Field.bind(tTagihan, (papreSize - labelSize - 6), Aligment.RIGHT, " ")
        };
        index++;

        field[index] = new Field[]{
            Field.bind("Total Admin", labelSize, Aligment.LEFT, " "),
            Field.bind(": Rp. ", 6),
            Field.bind(tAdmin, (papreSize - labelSize - 6), Aligment.RIGHT, " ")
        };
        index++;

        field[index] = new Field[]{
            Field.bind("Total Bayar", labelSize, Aligment.LEFT, " "),
            Field.bind(": Rp. ", 6),
            Field.bind(tTotal, (papreSize - labelSize - 6), Aligment.RIGHT, " ")
        };

        return field;
    }

    public Field[][] createBodyStrukMP(JSONObject pTranmainData) {
        try {
            ReceiptFormat receipt = AppSetting.instance().getReceiptFormat();

            int labelSize = receipt.getLabelSize();
            int dasSize = receipt.getDashSize();
            int valueSize = receipt.getValueSize();
            int papreSize = labelSize + dasSize + valueSize;
            String tTagihan = Common.formatRupiah(pTranmainData.getDouble("bill_amount"), false);
            String tAdmin = Common.formatRupiah(pTranmainData.getDouble("admin_charge"), false);
            String tTotalTagihan = Common.formatRupiah(pTranmainData.getDouble("total_bill_amount"), false);
            String biller = pTranmainData.getString("biller");

            JSONObject tData = new JSONObject(pTranmainData.get("transaction_data").toString());
            JSONObject tMPBiller = transactionController.getMultiPaymentBiller(biller);

            List<Field[]> list = new ArrayList<>();

            for (int i = 0; i < 3; i++) {
                if (pTranmainData.has("input_id_" + (i + 1))) {

                    String label = "Input " + (i + 1);

                    if (tMPBiller != null) {
                        if (tMPBiller.has("input_" + (i + 1) + "_label")) {
                            label = tMPBiller.getString("input_" + (i + 1) + "_label");
                        }
                    }

                    list.add(new Field[]{
                        Field.bind(label, labelSize, Aligment.LEFT, " "),
                        Field.bind(":", 2),
                        Field.bind(pTranmainData.get("input_id_" + (i + 1)).toString().trim(), valueSize, Aligment.RIGHT, " ")
                    });
                }
            }

            if (tMPBiller != null && tMPBiller.has("details")) {
                String tDetails = tMPBiller.get("details").toString();
                String[] detailArray = tDetails.replace("[", "").replace("]", "").split(";");

                for (String key : detailArray) {
                    if (tData.has(key)) {
                        list.add(new Field[]{
                            Field.bind(key.trim(), labelSize, Aligment.LEFT, " "),
                            Field.bind(":", 2),
                            Field.bind(tData.get(key).toString().trim(), valueSize, Aligment.RIGHT, " ")
                        });
                    }
                }
            }

            Field[][] field = new Field[list.size() + 4][];

            int index = 0;
            for (Field[] fields : list) {
                field[index] = fields;
                index++;
            }

            field[index] = new Field[]{
                Field.bind("-", papreSize, "-")
            };
            index++;

            field[index] = new Field[]{
                Field.bind("Tagihan", labelSize, Aligment.LEFT, " "),
                Field.bind(": Rp. ", 6),
                Field.bind(tTagihan, (papreSize - labelSize - 6), Aligment.RIGHT, " ")
            };
            index++;

            field[index] = new Field[]{
                Field.bind("Admin", labelSize, Aligment.LEFT, " "),
                Field.bind(": Rp. ", 6),
                Field.bind(tAdmin, (papreSize - labelSize - 6), Aligment.RIGHT, " ")
            };
            index++;

            field[index] = new Field[]{
                Field.bind("Total Bayar", labelSize, Aligment.LEFT, " "),
                Field.bind(": Rp. ", 6),
                Field.bind(tTotalTagihan, (papreSize - labelSize - 6), Aligment.RIGHT, " ")
            };
            index++;

            return field;
        } catch (SQLException ex) {
            throw new ServiceException(RC.ERROR_DATABASE, "Error get body struk Multi payment", ex);
        }
    }

    public void previewDetail(String pTrxID, String pTableName) {
        try {
            JSONObject tranmaindata = transactionController.getTransactionData(pTrxID, pTableName);
            Common.showDetailForm(constructDetailData(tranmaindata), this, "Detail Transaksi", false);
        } catch (SQLException e) {
            LogService.getInstance(this)
                    .dbError()
                    .withCause(e)
                    .log("[SQLException] Failed previewDetail", true);
            Common.showErrorMessage(MessageType.SYSTEM_ERROR, this);
        } catch (ServiceException e) {
            LogService
                    .getInstance(this)
                    .error()
                    .withCause(e)
                    .log("[ServiceException] Failed previewDetail", true);
            Common.showErrorMessage(e.getMessage(), this);
        } catch (Exception e) {
            LogService
                    .getInstance(this)
                    .error()
                    .withCause(e)
                    .log("[Exception] Failed previewDetail", true);
            Common.showErrorMessage(e.getMessage(), this);
        }
    }

    public Field[][] createBodyStrukPospaid(JSONObject pTranmainData) {
        ReceiptFormat receipt = AppSetting.instance().getReceiptFormat();

        int labelSize = receipt.getLabelSize();
        int dasSize = receipt.getDashSize();
        int valueSize = receipt.getValueSize();
        int papreSize = labelSize + dasSize + valueSize;
        String tSubID = pTranmainData.get("subscriber_id").toString();
        String tNama = pTranmainData.get("subscriber_name").toString();
        String tTarif = pTranmainData.has("segmentation") ? pTranmainData.get("segmentation").toString() : "-";
        int tBillstatus = pTranmainData.getInt("bill_status");

        String tTagihan = Common.formatRupiah(pTranmainData.getDouble("total_bill"), false);
        String tAdmin = Common.formatRupiah(pTranmainData.getDouble("total_admin_charge"), false);
        String tDenda = Common.formatRupiah(pTranmainData.getDouble("total_denda"), false);
        String tTotal = Common.formatRupiah(pTranmainData.getDouble("total_bill_amount"), false);

        List<Field[]> list = new ArrayList<>();

        list.add(new Field[]{
            Field.bind("No Pelanggan", labelSize, Aligment.LEFT, " "),
            Field.bind(": ", dasSize),
            Field.bind(tSubID, valueSize, Aligment.RIGHT, " ")
        }
        );

        list.add(new Field[]{
            Field.bind("Nama Pelanggan", labelSize, Aligment.LEFT, " "),
            Field.bind(": ", dasSize),
            Field.bind(tNama, valueSize, Aligment.RIGHT, " ")
        }
        );

        list.add(new Field[]{
            Field.bind("Golongan", labelSize, Aligment.LEFT, " "),
            Field.bind(": ", dasSize),
            Field.bind(tTarif, valueSize, Aligment.RIGHT, " ")
        }
        );

        for (int i = 0; i < tBillstatus; i++) {
            BigDecimal tag = new BigDecimal(pTranmainData.getDouble("bill_amount_" + (i + 1)));
            BigDecimal denda = new BigDecimal(pTranmainData.getDouble("bill_denda_" + (i + 1)));
            BigDecimal adm = new BigDecimal(pTranmainData.getDouble("bill_adm_" + (i + 1)));

            BigDecimal totalBill = tag.add(denda).add(adm);

            String tBulan = pTranmainData.getString("periode_" + (i + 1));
            String meterawal = pTranmainData.getString("meter_" + (i + 1));

            list.add(new Field[]{
                Field.bind(" Tagihan Ke-" + (i + 1) + " ", papreSize, Aligment.CENTER, "-")
            }
            );

            list.add(new Field[]{
                Field.bind("Periode ", labelSize, Aligment.LEFT, " "),
                Field.bind(": ", dasSize),
                Field.bind(tBulan, valueSize, Aligment.RIGHT, " ")
            }
            );

            list.add(new Field[]{
                Field.bind("Meter ", labelSize, Aligment.LEFT, " "),
                Field.bind(": ", dasSize),
                Field.bind(String.valueOf(meterawal), valueSize, Aligment.RIGHT, " ")
            }
            );

            list.add(new Field[]{
                Field.bind("Tagihan ", labelSize, Aligment.LEFT, " "),
                Field.bind(": Rp. ", 6),
                Field.bind(Common.formatRupiah(tag.doubleValue(), false), (papreSize - labelSize - 6), Aligment.RIGHT, " ")
            }
            );

            list.add(new Field[]{
                Field.bind("Denda ", labelSize, Aligment.LEFT, " "),
                Field.bind(": Rp. ", 6),
                Field.bind(Common.formatRupiah(denda.doubleValue(), false), (papreSize - labelSize - 6), Aligment.RIGHT, " ")
            }
            );

            list.add(new Field[]{
                Field.bind("Admin", labelSize, Aligment.LEFT, " "),
                Field.bind(": Rp. ", 6),
                Field.bind(Common.formatRupiah(adm.doubleValue(), false), (papreSize - labelSize - 6), Aligment.RIGHT, " ")
            }
            );

            list.add(new Field[]{
                Field.bind("Total ", labelSize, Aligment.LEFT, " "),
                Field.bind(": Rp. ", 6),
                Field.bind(Common.formatRupiah(totalBill.doubleValue(), false), (papreSize - labelSize - 6), Aligment.RIGHT, " ")
            }
            );
        }

        Field[][] field = new Field[list.size() + 5][];

        int index = 0;
        for (Field[] fields : list) {
            field[index] = fields;
            index++;
        }

        field[index] = new Field[]{
            Field.bind("-", papreSize, "-")
        };
        index++;

        field[index] = new Field[]{
            Field.bind("Total Tagihan", labelSize, Aligment.LEFT, " "),
            Field.bind(": Rp. ", 6),
            Field.bind(tTagihan, (papreSize - labelSize - 6), Aligment.RIGHT, " ")
        };
        index++;

        field[index] = new Field[]{
            Field.bind("Total Denda", labelSize, Aligment.LEFT, " "),
            Field.bind(": Rp. ", 6),
            Field.bind(tDenda, (papreSize - labelSize - 6), Aligment.RIGHT, " ")
        };
        index++;

        field[index] = new Field[]{
            Field.bind("Total Admin", labelSize, Aligment.LEFT, " "),
            Field.bind(": Rp. ", 6),
            Field.bind(tAdmin, (papreSize - labelSize - 6), Aligment.RIGHT, " ")
        };
        index++;

        field[index] = new Field[]{
            Field.bind("Total Bayar", labelSize, Aligment.LEFT, " "),
            Field.bind(": Rp. ", 6),
            Field.bind(tTotal, (papreSize - labelSize - 6), Aligment.RIGHT, " ")
        };

        return field;
    }

    public DetailDataFactory constructDetailData(JSONObject pTranmaindata) {
        ReceiptFormat receipt = AppSetting.instance().getReceiptFormat();

        int labelSize = receipt.getLabelSize();
        int dasSize = receipt.getDashSize();
        int valueSize = receipt.getValueSize();
        int papreSize = labelSize + dasSize + valueSize;
        DetailDataFactory dataList = new DetailDataFactory(papreSize);

        String tanggal = pTranmaindata.get("transaction_date").toString().replace("T", " ");
        String trxid = pTranmaindata.get("transaction_id").toString();
        String kasir = pTranmaindata.get("user_id").toString();

        dataList.create(
                Field.bind("Tanggal", labelSize, Aligment.LEFT, " "),
                Field.bind(": ", dasSize),
                Field.bind(tanggal, valueSize, Aligment.LEFT, " ")
        );

        dataList.create(
                Field.bind("Trans ID", labelSize, Aligment.LEFT, " "),
                Field.bind(": ", dasSize),
                Field.bind(trxid, valueSize, Aligment.LEFT, " ")
        );

        dataList.create(
                Field.bind("Kasir", labelSize, Aligment.LEFT, " "),
                Field.bind(": ", dasSize),
                Field.bind(kasir, valueSize, Aligment.LEFT, " ")
        );

        dataList.create(
                Field.bind("-", papreSize, Aligment.CENTER, "-")
        );

        dataList.create(createDetailData(pTranmaindata));

        dataList.create(
                Field.bind("-", papreSize, Aligment.CENTER, "-")
        );

        return dataList;
    }

    public Field[][] createDetailData(JSONObject pTranmainData) {
        return new Field[0][0];
    }

    public Field[][] createDetailDataVoucher(JSONObject pTranmainData) {
        ReceiptFormat receipt = AppSetting.instance().getReceiptFormat();

        int labelSize = receipt.getLabelSize();
        int dasSize = receipt.getDashSize();
        int valueSize = receipt.getValueSize();
        int papreSize = labelSize + dasSize + valueSize;
        String tDestNumber = pTranmainData.get("dest_number").toString();
        String tVoucher = pTranmainData.get("voucher_id").toString();
        String tNominal = pTranmainData.get("nominal").toString();
        String tHargaJual = Common.formatRupiah(
                pTranmainData.has("harga_jual")
                ? pTranmainData.getDouble("harga_jual")
                : 0,
                false
        );
        String tSerialNumber = pTranmainData.has("serial_number")
                ? pTranmainData.get("serial_number").toString()
                : "-";

        Field[][] field = new Field[][]{
            {
                Field.bind("Tujuan ", labelSize, Aligment.LEFT, " "),
                Field.bind(": ", dasSize),
                Field.bind(tDestNumber, -1, Aligment.LEFT, " ")
            },
            {
                Field.bind("Voucher ", labelSize, Aligment.LEFT, " "),
                Field.bind(": ", dasSize),
                Field.bind(tVoucher, -1, Aligment.LEFT, " ")
            },
            {
                Field.bind("Nominal ", labelSize, Aligment.LEFT, " "),
                Field.bind(": ", dasSize),
                Field.bind(tNominal, -1, Aligment.LEFT, " ")
            },
            {
                Field.bind("SN ", labelSize, Aligment.LEFT, " "),
                Field.bind(": ", dasSize),
                Field.bind(tSerialNumber, -1, Aligment.LEFT, " ")
            },
            {
                Field.bind("Harga", labelSize, Aligment.LEFT, " "),
                Field.bind(": Rp. ", 6),
                Field.bind(tHargaJual, -1, Aligment.LEFT, " ")
            }
        };

        return field;
    }

    public Field[][] createDetailDataPrepaid(JSONObject pTranmainData) {
        ReceiptFormat receipt = AppSetting.instance().getReceiptFormat();

        int labelSize = receipt.getLabelSize();
        int dasSize = receipt.getDashSize();
        int valueSize = receipt.getValueSize();
        int papreSize = labelSize + dasSize + valueSize;

        String tSubID = pTranmainData.get("subscriber_id").toString();
        String tMSN = pTranmainData.get("msn").toString();
        String tNama = pTranmainData.get("subscriber_name").toString();
        String tTarif = pTranmainData.has("segmentation")
                ? pTranmainData.get("segmentation").toString()
                : "-";
        String tKwh = pTranmainData.has("kwh")
                ? pTranmainData.get("kwh").toString()
                : "-";
        String tNominal = Common.formatRupiah(pTranmainData.getDouble("nominal"), false);
        String tAdmin = Common.formatRupiah(pTranmainData.getDouble("admin"), false);
        String tHargaJual = Common.formatRupiah(
                pTranmainData.has("harga_jual")
                ? pTranmainData.getDouble("harga_jual")
                : 0,
                false
        );
        String tToken = pTranmainData.has("token")
                ? pTranmainData.get("token").toString()
                : "-";

        Field[][] field = new Field[][]{
            {
                Field.bind("No Pel/MSN ", labelSize, Aligment.LEFT, " "),
                Field.bind(": ", dasSize),
                Field.bind(tSubID + "/" + tMSN, -1, Aligment.LEFT, " ")
            },
            {
                Field.bind("Nama ", labelSize, Aligment.LEFT, " "),
                Field.bind(": ", dasSize),
                Field.bind(tNama, -1, Aligment.LEFT, " ")
            },
            {
                Field.bind("Tarif ", labelSize, Aligment.LEFT, " "),
                Field.bind(": ", dasSize),
                Field.bind(tTarif, -1, Aligment.LEFT, " ")
            },
            {
                Field.bind("Nominal ", labelSize, Aligment.LEFT, " "),
                Field.bind(": Rp. ", 6),
                Field.bind(tNominal, -1, Aligment.LEFT, " ")
            },
            {
                Field.bind("Kwh ", labelSize, Aligment.LEFT, " "),
                Field.bind(": ", dasSize),
                Field.bind(tKwh, -1, Aligment.LEFT, " ")
            },
            {
                Field.bind(" Token ", papreSize, "-")
            },
            {
                Field.bind(tToken, papreSize, Aligment.CENTER, " ")
            },
            {
                Field.bind("-", papreSize, "-")
            },
            {
                Field.bind("Admin", labelSize, Aligment.LEFT, " "),
                Field.bind(": Rp. ", 6),
                Field.bind(tAdmin, -1, Aligment.LEFT, " ")
            },
            {
                Field.bind("Total Bayar", labelSize, Aligment.LEFT, " "),
                Field.bind(": Rp. ", 6),
                Field.bind(tHargaJual, -1, Aligment.LEFT, " ")
            }
        };

        return field;
    }

    public Field[][] createDetailDataPDAM(JSONObject pTranmainData) {
        ReceiptFormat receipt = AppSetting.instance().getReceiptFormat();

        int labelSize = receipt.getLabelSize();
        int dasSize = receipt.getDashSize();
        int valueSize = receipt.getValueSize();
        int papreSize = labelSize + dasSize + valueSize;

        String tSubID = pTranmainData.get("subscriber_id").toString();
        String tNama = pTranmainData.get("subscriber_name").toString();
        String tTarif = pTranmainData.get("subscriber_segmentation").toString();
        int tBillstatus = pTranmainData.getInt("bill_status");

        String tTagihan = Common.formatRupiah(pTranmainData.getDouble("total_bill"), false);
        String tAdmin = Common.formatRupiah(pTranmainData.getDouble("total_admin_charge"), false);
        String tTotal = Common.formatRupiah(pTranmainData.getDouble("total_bill_amount"), false);

        List<Field[]> list = new ArrayList<>();

        list.add(new Field[]{
            Field.bind("No Pelanggan", labelSize, Aligment.LEFT, " "),
            Field.bind(": ", dasSize),
            Field.bind(tSubID, -1, Aligment.LEFT, " ")
        }
        );

        list.add(new Field[]{
            Field.bind("Nama Pelanggan", labelSize, Aligment.LEFT, " "),
            Field.bind(": ", dasSize),
            Field.bind(tNama, -1, Aligment.LEFT, " ")
        }
        );

        list.add(new Field[]{
            Field.bind("Golongan", labelSize, Aligment.LEFT, " "),
            Field.bind(": ", dasSize),
            Field.bind(tTarif, -1, Aligment.LEFT, " ")
        }
        );

        for (int i = 0; i < tBillstatus; i++) {
            BigDecimal tag = new BigDecimal(pTranmainData.getDouble("bill_amount_" + (i + 1)));
            BigDecimal denda = new BigDecimal(pTranmainData.getDouble("bill_penalty_" + (i + 1)));
            BigDecimal adm = new BigDecimal(pTranmainData.getDouble("bill_adm_" + (i + 1)));
            BigDecimal billadm = new BigDecimal(pTranmainData.getDouble("bill_adm_pdam_" + (i + 1)));
            BigDecimal instalment = new BigDecimal(pTranmainData.getDouble("bill_installment_" + (i + 1)));
            BigDecimal danameter = new BigDecimal(pTranmainData.getDouble("bill_danameter_" + (i + 1)));
            BigDecimal vat = new BigDecimal(pTranmainData.getDouble("bill_vat_" + (i + 1)));
            BigDecimal waste = new BigDecimal(pTranmainData.getDouble("bill_waste_" + (i + 1)));
            BigDecimal materai = new BigDecimal(pTranmainData.getDouble("bill_stamp_" + (i + 1)));

            BigDecimal totalBill = tag.add(denda).add(adm).add(billadm).add(instalment)
                    .add(danameter).add(vat).add(waste).add(materai);

            String tBulan = pTranmainData.getString("periode_" + (i + 1));
            int meterawal = pTranmainData.getInt("meter_start_" + (i + 1));
            int meterakhir = pTranmainData.getInt("meter_end_" + (i + 1));
            int totalMeter = pTranmainData.getInt("meter_usage_" + (i + 1));

            list.add(new Field[]{
                Field.bind(" Tagihan Ke-" + (i + 1) + " ", papreSize, Aligment.CENTER, "-")
            }
            );

            list.add(new Field[]{
                Field.bind("Periode ", labelSize, Aligment.LEFT, " "),
                Field.bind(": ", dasSize),
                Field.bind(tBulan, -1, Aligment.LEFT, " ")
            }
            );

            list.add(new Field[]{
                Field.bind("Meter Awal ", labelSize, Aligment.LEFT, " "),
                Field.bind(": ", dasSize),
                Field.bind(String.valueOf(meterawal), -1, Aligment.LEFT, " ")
            }
            );

            list.add(new Field[]{
                Field.bind("Meter Akhir ", labelSize, Aligment.LEFT, " "),
                Field.bind(": ", dasSize),
                Field.bind(String.valueOf(meterakhir), -1, Aligment.LEFT, " ")
            }
            );

            list.add(new Field[]{
                Field.bind("Total Meter ", labelSize, Aligment.LEFT, " "),
                Field.bind(": ", dasSize),
                Field.bind(String.valueOf(totalMeter), -1, Aligment.LEFT, " ")
            }
            );

            list.add(new Field[]{
                Field.bind("Tagihan ", labelSize, Aligment.LEFT, " "),
                Field.bind(": Rp. ", 6),
                Field.bind(Common.formatRupiah(tag.doubleValue(), false), -1, Aligment.LEFT, " ")
            }
            );

            list.add(new Field[]{
                Field.bind("Denda ", labelSize, Aligment.LEFT, " "),
                Field.bind(": Rp. ", 6),
                Field.bind(Common.formatRupiah(denda.doubleValue(), false), -1, Aligment.LEFT, " ")
            }
            );

            list.add(new Field[]{
                Field.bind("Angsuran ", labelSize, Aligment.LEFT, " "),
                Field.bind(": Rp. ", 6),
                Field.bind(Common.formatRupiah(instalment.doubleValue(), false), -1, Aligment.LEFT, " ")
            }
            );

            list.add(new Field[]{
                Field.bind("Dana Meter ", labelSize, Aligment.LEFT, " "),
                Field.bind(": Rp. ", 6),
                Field.bind(Common.formatRupiah(danameter.doubleValue(), false), -1, Aligment.LEFT, " ")
            }
            );

            list.add(new Field[]{
                Field.bind("Retribusi ", labelSize, Aligment.LEFT, " "),
                Field.bind(": Rp. ", 6),
                Field.bind(Common.formatRupiah(vat.doubleValue(), false), -1, Aligment.LEFT, " ")
            }
            );

            list.add(new Field[]{
                Field.bind("Retribusi ", labelSize, Aligment.LEFT, " "),
                Field.bind(": Rp. ", 6),
                Field.bind(Common.formatRupiah(vat.doubleValue(), false), -1, Aligment.LEFT, " ")
            }
            );

            list.add(new Field[]{
                Field.bind("Materai ", labelSize, Aligment.LEFT, " "),
                Field.bind(": Rp. ", 6),
                Field.bind(Common.formatRupiah(materai.doubleValue(), false), -1, Aligment.LEFT, " ")
            }
            );

            list.add(new Field[]{
                Field.bind("Lain-lain ", labelSize, Aligment.LEFT, " "),
                Field.bind(": Rp. ", 6),
                Field.bind(Common.formatRupiah(waste.doubleValue(), false), -1, Aligment.LEFT, " ")
            }
            );

            list.add(new Field[]{
                Field.bind("Admin PDAM", labelSize, Aligment.LEFT, " "),
                Field.bind(": Rp. ", 6),
                Field.bind(Common.formatRupiah(adm.doubleValue(), false), -1, Aligment.LEFT, " ")
            }
            );

            list.add(new Field[]{
                Field.bind("Admin ", labelSize, Aligment.LEFT, " "),
                Field.bind(": Rp. ", 6),
                Field.bind(Common.formatRupiah(billadm.doubleValue(), false), -1, Aligment.LEFT, " ")
            }
            );

            list.add(new Field[]{
                Field.bind("Total ", labelSize, Aligment.LEFT, " "),
                Field.bind(": Rp. ", 6),
                Field.bind(Common.formatRupiah(totalBill.doubleValue(), false), -1, Aligment.LEFT, " ")
            }
            );
        }

        Field[][] field = new Field[list.size() + 4][];

        int index = 0;
        for (Field[] fields : list) {
            field[index] = fields;
            index++;
        }

        field[index] = new Field[]{
            Field.bind("-", papreSize, "-")
        };
        index++;

        field[index] = new Field[]{
            Field.bind("Total Tagihan", labelSize, Aligment.LEFT, " "),
            Field.bind(": Rp. ", 6),
            Field.bind(tTagihan, -1, Aligment.LEFT, " ")
        };
        index++;

        field[index] = new Field[]{
            Field.bind("Total Admin", labelSize, Aligment.LEFT, " "),
            Field.bind(": Rp. ", 6),
            Field.bind(tAdmin, -1, Aligment.LEFT, " ")
        };
        index++;

        field[index] = new Field[]{
            Field.bind("Total Bayar", labelSize, Aligment.LEFT, " "),
            Field.bind(": Rp. ", 6),
            Field.bind(tTotal, -1, Aligment.LEFT, " ")
        };

        return field;
    }

    public Field[][] createDetailDataPOSPAID(JSONObject pTranmainData) {
        ReceiptFormat receipt = AppSetting.instance().getReceiptFormat();

        int labelSize = receipt.getLabelSize();
        int dasSize = receipt.getDashSize();
        int valueSize = receipt.getValueSize();
        int papreSize = labelSize + dasSize + valueSize;

        String tSubID = pTranmainData.get("subscriber_id").toString();
        String tNama = pTranmainData.get("subscriber_name").toString();
        String tTarif = pTranmainData.has("segmentation") ? pTranmainData.get("segmentation").toString() : "-";
        int tBillstatus = pTranmainData.getInt("bill_status");

        String tTagihan = Common.formatRupiah(pTranmainData.getDouble("total_bill"), false);
        String tAdmin = Common.formatRupiah(pTranmainData.getDouble("total_admin_charge"), false);
        String tDenda = Common.formatRupiah(pTranmainData.getDouble("total_denda"), false);
        String tTotal = Common.formatRupiah(pTranmainData.getDouble("total_bill_amount"), false);

        List<Field[]> list = new ArrayList<>();

        list.add(new Field[]{
            Field.bind("No Pelanggan", labelSize, Aligment.LEFT, " "),
            Field.bind(": ", dasSize),
            Field.bind(tSubID, -1, Aligment.LEFT, " ")
        }
        );

        list.add(new Field[]{
            Field.bind("Nama Pelanggan", labelSize, Aligment.LEFT, " "),
            Field.bind(": ", dasSize),
            Field.bind(tNama, -1, Aligment.LEFT, " ")
        }
        );

        list.add(new Field[]{
            Field.bind("Tarif", labelSize, Aligment.LEFT, " "),
            Field.bind(": ", dasSize),
            Field.bind(tTarif, -1, Aligment.LEFT, " ")
        }
        );

        for (int i = 0; i < tBillstatus; i++) {
            BigDecimal tag = new BigDecimal(pTranmainData.getDouble("bill_amount_" + (i + 1)));
            BigDecimal denda = new BigDecimal(pTranmainData.getDouble("bill_denda_" + (i + 1)));
            BigDecimal adm = new BigDecimal(pTranmainData.getDouble("bill_adm_" + (i + 1)));

            BigDecimal totalBill = tag.add(denda).add(adm);

            String tBulan = pTranmainData.getString("periode_" + (i + 1));
            String meter = pTranmainData.getString("meter_" + (i + 1));

            list.add(new Field[]{
                Field.bind(" Tagihan Ke-" + (i + 1) + " ", papreSize, Aligment.CENTER, "-")
            }
            );

            list.add(new Field[]{
                Field.bind("Periode ", labelSize, Aligment.LEFT, " "),
                Field.bind(": ", dasSize),
                Field.bind(tBulan, -1, Aligment.LEFT, " ")
            }
            );

            list.add(new Field[]{
                Field.bind("Meter ", labelSize, Aligment.LEFT, " "),
                Field.bind(": ", dasSize),
                Field.bind(String.valueOf(meter), -1, Aligment.LEFT, " ")
            }
            );

            list.add(new Field[]{
                Field.bind("Tagihan ", labelSize, Aligment.LEFT, " "),
                Field.bind(": Rp. ", 6),
                Field.bind(Common.formatRupiah(tag.doubleValue(), false), -1, Aligment.LEFT, " ")
            }
            );

            list.add(new Field[]{
                Field.bind("Denda ", labelSize, Aligment.LEFT, " "),
                Field.bind(": Rp. ", 6),
                Field.bind(Common.formatRupiah(denda.doubleValue(), false), -1, Aligment.LEFT, " ")
            }
            );

            list.add(new Field[]{
                Field.bind("Admin", labelSize, Aligment.LEFT, " "),
                Field.bind(": Rp. ", 6),
                Field.bind(Common.formatRupiah(adm.doubleValue(), false), -1, Aligment.LEFT, " ")
            }
            );

            list.add(new Field[]{
                Field.bind("Total ", labelSize, Aligment.LEFT, " "),
                Field.bind(": Rp. ", 6),
                Field.bind(Common.formatRupiah(totalBill.doubleValue(), false), -1, Aligment.LEFT, " ")
            }
            );
        }

        Field[][] field = new Field[list.size() + 5][];

        int index = 0;
        for (Field[] fields : list) {
            field[index] = fields;
            index++;
        }

        field[index] = new Field[]{
            Field.bind("-", papreSize, "-")
        };
        index++;

        field[index] = new Field[]{
            Field.bind("Total Tagihan", labelSize, Aligment.LEFT, " "),
            Field.bind(": Rp. ", 6),
            Field.bind(tTagihan, -1, Aligment.LEFT, " ")
        };
        index++;

        field[index] = new Field[]{
            Field.bind("Total Denda", labelSize, Aligment.LEFT, " "),
            Field.bind(": Rp. ", 6),
            Field.bind(tDenda, -1, Aligment.LEFT, " ")
        };
        index++;

        field[index] = new Field[]{
            Field.bind("Total Admin", labelSize, Aligment.LEFT, " "),
            Field.bind(": Rp. ", 6),
            Field.bind(tAdmin, -1, Aligment.LEFT, " ")
        };
        index++;

        field[index] = new Field[]{
            Field.bind("Total Bayar", labelSize, Aligment.LEFT, " "),
            Field.bind(": Rp. ", 6),
            Field.bind(tTotal, -1, Aligment.LEFT, " ")
        };

        return field;
    }

    public Field[][] createDetailDataMP(JSONObject pTranmainData) {
        try {
            ReceiptFormat receipt = AppSetting.instance().getReceiptFormat();

            int labelSize = receipt.getLabelSize();
            int dasSize = receipt.getDashSize();
            int valueSize = receipt.getValueSize();
            int papreSize = labelSize + dasSize + valueSize;
            String tTagihan = Common.formatRupiah(pTranmainData.getDouble("bill_amount"), false);
            String tAdmin = Common.formatRupiah(pTranmainData.getDouble("admin_charge"), false);
            String tTotalTagihan = Common.formatRupiah(pTranmainData.getDouble("total_bill_amount"), false);
            String biller = pTranmainData.getString("biller");

            JSONObject tData = new JSONObject(pTranmainData.get("transaction_data").toString());
            JSONObject tMPBiller = transactionController.getMultiPaymentBiller(biller);

            List<Field[]> list = new ArrayList<>();

            for (int i = 0; i < 3; i++) {
                if (pTranmainData.has("input_id_" + (i + 1))) {

                    String label = "Input " + (i + 1);

                    if (tMPBiller != null) {
                        if (tMPBiller.has("input_" + (i + 1) + "_label")) {
                            label = tMPBiller.getString("input_" + (i + 1) + "_label");
                        }
                    }

                    list.add(new Field[]{
                        Field.bind(label, labelSize, Aligment.LEFT, " "),
                        Field.bind(":", 2),
                        Field.bind(pTranmainData.get("input_id_" + (i + 1)).toString().trim(), -1, Aligment.LEFT, " ")
                    });
                }
            }

            if (tMPBiller != null && tMPBiller.has("details")) {
                String tDetails = tMPBiller.get("details").toString();
                String[] detailArray = tDetails.replace("[", "").replace("]", "").split(";");

                for (String key : detailArray) {
                    if (tData.has(key)) {
                        list.add(new Field[]{
                            Field.bind(key.trim(), labelSize, Aligment.LEFT, " "),
                            Field.bind(":", 2),
                            Field.bind(tData.get(key).toString().trim(), -1, Aligment.LEFT, " ")
                        });
                    }
                }
            }

            Field[][] field = new Field[list.size() + 4][];

            int index = 0;
            for (Field[] fields : list) {
                field[index] = fields;
                index++;
            }

            field[index] = new Field[]{
                Field.bind("-", papreSize, "-")
            };
            index++;

            field[index] = new Field[]{
                Field.bind("Tagihan", labelSize, Aligment.LEFT, " "),
                Field.bind(": Rp. ", 6),
                Field.bind(tTagihan, -1, Aligment.LEFT, " ")
            };
            index++;

            field[index] = new Field[]{
                Field.bind("Admin", labelSize, Aligment.LEFT, " "),
                Field.bind(": Rp. ", 6),
                Field.bind(tAdmin, -1, Aligment.LEFT, " ")
            };
            index++;

            field[index] = new Field[]{
                Field.bind("Total Bayar", labelSize, Aligment.LEFT, " "),
                Field.bind(": Rp. ", 6),
                Field.bind(tTotalTagihan, -1, Aligment.LEFT, " ")
            };
            index++;

            return field;
        } catch (SQLException ex) {
            throw new ServiceException(RC.ERROR_DATABASE, "Error get body struk Multi payment", ex);
        }
    }

    public void previewReport(String pFileName, HashMap<String, Object> pParam) {
        previewReport(pFileName, pParam, null);
    }

    public void previewReport(String pFileName, HashMap<String, Object> pParam, String pQuery) {
        try {
            new ReportViewer().viewReport(pFileName, pQuery, pParam);
        } catch (Exception e) {
            Common.showErrorMessage("Cannot preview report, " + e.getMessage(), this);
        }
    }
}
