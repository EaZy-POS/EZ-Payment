/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.auth.resource;

import co.id.ez.auth.controller.AuthController;
import co.id.ez.auth.enums.Fields;
import co.id.ez.auth.enums.RequiredFields;
import co.id.ez.system.core.etc.EncryptionService;
import co.id.ez.system.core.ex.ServiceException;
import co.id.ez.system.core.log.LogService;
import co.id.ez.system.core.rc.RC;
import com.json.JSONObject;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 *
 * @author Lutfi
 */
public abstract class AuthHandler {

    public static String dbName = "auth-service";
    protected String corelation_id;
    protected final AuthController authController = new AuthController();
    private final String cServiceKey = "0xdef09x70";

    public Response handleRequest(String pBodyMessage) {
        JSONObject tJsonRequest = new JSONObject();
        createCorelationID();
        try {
            String tRequest = "";
            String tUrlInfo = pBodyMessage;
            try {
                tRequest = URLDecoder.decode(tUrlInfo,
                        StandardCharsets.UTF_8.toString()).replace("\n", "").replace("\t", " ").replace("\r", "");
                LogService.getInstance(this).stream()
                        .log("[" + RequiredFields.AUTH.name() + " REQUEST ]:(" + corelation_id + "): " + tRequest);
            } catch (UnsupportedEncodingException ex) {
                throw new Exception("Error Encoding Request");
            }

            tJsonRequest = new JSONObject(tRequest);
            validateMessage(tJsonRequest);

            JSONObject tMitraCred = authController.getCredential(tJsonRequest);
            String key = validateCredential(tJsonRequest, tMitraCred);
            validateSignatureRequest(tJsonRequest, key);

            tJsonRequest.put("rc", "0000");
            tJsonRequest.put("rcm", "SUCCESS");

            LogService.getInstance(this).stream()
                    .log("[" + RequiredFields.AUTH.name() + " RESPONSE ]:(" + corelation_id + "): " + tJsonRequest);
            return constructHttpResponse(RC.SUCCESS, tJsonRequest);

        } catch (ServiceException ex) {
            LogService.getInstance(this).error().withCause(ex)
                    .log("[ServiceException](" + corelation_id + "):  Error on Handle "
                            + RequiredFields.AUTH.name() + " request. ", true);
            RC tRC = ex.getRC();
            String tMsg = ex.getMessage();

            if (tRC.equals(RC.ERROR_DATABASE)) {
                tMsg = "Internal Error";
            }

            if (tRC.equals(RC.ERROR_TIMEOUT) || tRC.equals(RC.ERROR_OTHER)) {
                tMsg = "Internal Server Error";
            }

            if (tRC.equals(RC.ERROR_TRANSACTION_FAILED_FROM_VENDING)) {
                tMsg = "External Server Error";
            }

            tJsonRequest.put("rc", tRC.getResponseCodeString());
            tJsonRequest.put("rcm", tMsg);

            LogService.getInstance(this).stream()
                    .log("[" + RequiredFields.AUTH.name() + " RESPONSE ]:(" + corelation_id + "): " + tJsonRequest.toString());
            return constructHttpResponse(tRC, tJsonRequest);
        } catch (Exception ex) {
            LogService.getInstance(this).error().withCause(ex)
                    .log("[Exception](" + corelation_id + "): Error on Handle " + RequiredFields.AUTH.name() + " request. ", true);
            RC tRC = RC.ERROR_OTHER;
            String tMsg = "Internal Server Error";

            tJsonRequest.put("rc", tRC.getResponseCodeString());
            tJsonRequest.put("rcm", tMsg);

            LogService.getInstance(this).stream()
                    .log("[" + RequiredFields.AUTH.name() + " RESPONSE ]:(" + corelation_id + "): " + tJsonRequest.toString());
            return constructHttpResponse(tRC, tJsonRequest);
        }
    }

    private void validateMessage(JSONObject pRequest) {
        LogService.getInstance(this).trace().log("(" + corelation_id + ") Before validate mandatory field");
        List<String> tList = new ArrayList<>();

        RequiredFields.AUTH.getFields().stream().filter(field -> (!pRequest.has(field))).forEachOrdered(field -> {
            tList.add(field);
        });

        if (tList.size() > 0) {
            throw new ServiceException(RC.ERROR_INVALID_MESSAGE, "Invalid mandatory field. " + tList.toString());
        }

        LogService.getInstance(this).trace().log("(" + corelation_id + ") After validate mandatory field (success)");
    }

    private String validateCredential(final JSONObject pRequest, JSONObject pCredData) {
        String tValidate = pCredData.getString("cred_access");
        String tKey = EncryptionService
                .encryptor()
                .decrypt(
                        EncryptionService
                                .encryptor()
                                .Base64Decrypt(
                                        pCredData.getString("mitra_access")
                                ),
                        cServiceKey
                );

        String tUId
                = EncryptionService
                        .encryptor()
                        .Base64Decrypt(
                                pRequest.getString(Fields.user_id.name())
                        );
        String tPass = EncryptionService
                .encryptor()
                .decrypt(
                        EncryptionService
                                .encryptor()
                                .Base64Decrypt(
                                        pRequest.getString(Fields.password.name())
                                ),
                        cServiceKey
                );

        String tEncPass = EncryptionService.encryptor().Base64Encrypt(
                EncryptionService.encryptor().encrypt(tPass, tKey)
        );

        String tClientID = pRequest.getString(Fields.client_id.name());
        String tModuleID = pRequest.getString(Fields.module_id.name());
        String tPath = pRequest.has(Fields.path.name())
                ? pRequest.getString(Fields.path.name())
                : null;
        String tProduct = pRequest.has(Fields.product.name()) ? pRequest.getString(Fields.product.name()) : null;

        String tStatus = pCredData.getString("status");
        boolean isActive = false;

        if (tStatus.equalsIgnoreCase("active")) {
            isActive = true;
        } else if (tStatus.equalsIgnoreCase("inactive")) {
            isActive = false;
        } else {
            throw new ServiceException(RC.ERROR_UNREGISTERED_MERCHANT_TYPE, "Unregister mitra");
        }

        LogService.getInstance(this).trace().log("(" + corelation_id + ") Before validate module");
        if (!authController.isValidModule(tModuleID)) {
            throw new ServiceException(RC.ERROR_PRODUCT_NOT_AVAILABLE, "Unregister Module [" + tModuleID + "]");
        }
        LogService.getInstance(this).trace().log("(" + corelation_id + ") After validate module (success)");

        LogService.getInstance(this).trace().log("(" + corelation_id + ") Before validate client access");
        if (!authController.isValidClientModule(tClientID, tModuleID, tPath, isActive)) {
            throw new ServiceException(RC.ERROR_BLOCKED_TERMINAL, "Invalid access product client");
        }
        LogService.getInstance(this).trace().log("(" + corelation_id + ") After validate client access (success)");

        if (tProduct != null) {
            validateRightProductAccess(tModuleID, tProduct);
        }
        
        LogService.getInstance(this).trace().log("(" + corelation_id + ") Key: " + tKey);
        LogService.getInstance(this).trace().log("(" + corelation_id + ") Pass: " + tPass);
        LogService.getInstance(this).trace().log("(" + corelation_id + ") CLien ID: " + tClientID);
        LogService.getInstance(this).trace().log("(" + corelation_id + ") User: " + tUId);
        LogService.getInstance(this).trace().log("(" + corelation_id + ") Pass: " + tEncPass);
        LogService.getInstance(this).trace().log("(" + corelation_id + ") Val access: " + tValidate);
        LogService.getInstance(this).trace()
                .log("(" + corelation_id + ") current access: "
                        + EncryptionService
                                .encryptor()
                                .encrypt(
                                        tKey, tClientID, tUId, tEncPass)
                );

        LogService.getInstance(this).trace().log("(" + corelation_id + ") Before validate credential");
        if (!EncryptionService
                .encryptor()
                .validateKey(
                        tValidate,
                        tKey,
                        tClientID,
                        tUId,
                        tEncPass
                )) {
            throw new ServiceException(RC.ERROR_UNREGISTERED_PARTNER_CENTRAL, "Invalid access client [" + tClientID + "]");
        }
        LogService.getInstance(this).trace().log("(" + corelation_id + ") After validate credential (Success)");

        pCredData.remove("cred_access");
        pCredData.remove("mitra_access");
        pCredData.remove("mitra_id");
        pCredData.remove("user_id");

        pRequest.put("detail", pCredData);
        return tKey;
    }

    public void validateSignatureRequest(JSONObject pRequest, String pKey) {
        LogService.getInstance(this).trace().log("(" + corelation_id + ") Before validate signature");
        String tCLientKey = pKey;
        String tClientID = pRequest.getString(Fields.client_id.name());
        String tModuleID = pRequest.getString(Fields.module_id.name());
        String tDate = pRequest.getString(Fields.date.name());
        String tBody = pRequest.getString(Fields.body.name());
        String tSign = EncryptionService
                .encryptor()
                .Base64Decrypt(
                        pRequest.getString(Fields.signature.name()
                        )
                );

        String tHashTarget = tModuleID.concat(":")
                .concat(tBody)
                .concat(":")
                .concat(tDate)
                .concat(":")
                .concat(tClientID);
        LogService.getInstance(this).trace().log("(" + corelation_id + ") Hash target: " + tHashTarget);
        String tValidSignature = EncryptionService.encryptor()
                .hashSHA256(
                        EncryptionService
                                .encryptor()
                                .Base64Encrypt(
                                        tHashTarget
                                ).concat("#")
                                .concat(tCLientKey)
                                .concat("#")
                );
        LogService.getInstance(this).trace().log("(" + corelation_id + ") Sign: " + tSign);
        LogService.getInstance(this).trace().log("(" + corelation_id + ") valid Sign: " + tValidSignature);
        if (!tSign.equals(tValidSignature)) {
            LogService.getInstance(this).trace().log("(" + corelation_id + ") After validate signature (failed)");
            pRequest.remove("detail");
            throw new ServiceException(RC.ERROR_INVALID_HASHCODE, "Invalid signature");
        }
        LogService.getInstance(this).trace().log("After validate signature (success)");
    }
    
    private void validateRightProductAccess(String pModule, String pProduct) {
        LogService.getInstance(this)
                .trace()
                .log("(" + corelation_id + ") Before validate rigth client product access [" + pModule + ", " + pProduct + "]");

        switch (pModule.toUpperCase()) {
            case "MP":
                validateRightGeneralPaymentProductAccess(pProduct);
                break;
            case "EWALLET":
                validateRightEwalletProductAccess(pProduct);
                break;
            case "TELKOM":
                validateRightTelkomProductAccess(pProduct);
                break;
            case "ISI":
                validateRightVoucherProductAccess(pProduct);
                break;
            case "PREPAID":
                validateRightPrepaidDenomAccess(pProduct);
                break;
            case "PDAM":
                validateRightPDAMBillerAccess(pProduct);
                break;

        }
        LogService.getInstance(this)
                .trace()
                .log("(" + corelation_id + ") After validate right client product access [" + pModule + ", " + pProduct + "] (success)");
    }

    private void validateRightGeneralPaymentProductAccess(String pBiller) {
        LogService.getInstance(this)
                .trace()
                .log("(" + corelation_id + ") After validate right Multi Payment product access [" + pBiller + "] (success)");
        if (!authController.isValidGeneralPaymentBillerModule(pBiller)) {
            throw new ServiceException(RC.ERROR_BLOCKED_TERMINAL, "Invalid biller " + pBiller);
        }
        LogService
                .getInstance(this)
                .trace().log("(" + corelation_id + ") After validate right Multi Payment product access [" + pBiller + "] (success)");
    }

    private void validateRightEwalletProductAccess(String pBiller) {
        LogService
                .getInstance(this)
                .trace().log("(" + corelation_id + ") After validate right Ewallet product access [" + pBiller + "] (success)");
        if (!authController.isValidEwalletBillerModule(pBiller)) {
            throw new ServiceException(RC.ERROR_BLOCKED_TERMINAL, "Invalid biller " + pBiller);
        }
        LogService
                .getInstance(this)
                .trace().log("(" + corelation_id + ") After validate right Ewallet product access [" + pBiller + "] (success)");
    }

    private void validateRightTelkomProductAccess(String pBiller) {
        LogService
                .getInstance(this)
                .trace().log("(" + corelation_id + ") After validate right Telkom product access [" + pBiller + "] (success)");
        if (!authController.isValidTelkomBillerModule(pBiller)) {
            throw new ServiceException(RC.ERROR_BLOCKED_TERMINAL, "Invalid biller " + pBiller);
        }
        LogService
                .getInstance(this)
                .trace().log("(" + corelation_id + ") After validate right Telkom product access [" + pBiller + "] (success)");
    }
    
    private void validateRightVoucherProductAccess(String pVoucherID) {
        LogService
                .getInstance(this)
                .trace().log("(" + corelation_id + ") After validate right voucher product access [" + pVoucherID + "] (success)");
        if (!authController.isValidVoucherProduct(pVoucherID)) {
            throw new ServiceException(RC.ERROR_BLOCKED_TERMINAL, "Invalid voucherid " + pVoucherID);
        }
        LogService
                .getInstance(this)
                .trace().log("(" + corelation_id + ") After validate right Voucher product access [" + pVoucherID + "] (success)");
    }
    
    private void validateRightPrepaidDenomAccess(String pDenom) {
        LogService
                .getInstance(this)
                .trace().log("(" + corelation_id + ") After validate right prepaid denom access [" + pDenom + "] (success)");
        if (!authController.isValidPrepaidDenom(pDenom)) {
            throw new ServiceException(RC.ERROR_BLOCKED_TERMINAL, "Invalid denom " + pDenom);
        }
        LogService
                .getInstance(this)
                .trace().log("(" + corelation_id + ") After validate right prepaid denom access [" + pDenom + "] (success)");
    }
    
    private void validateRightPDAMBillerAccess(String pBiller) {
        LogService
                .getInstance(this)
                .trace().log("(" + corelation_id + ") After validate right pdam biller access [" + pBiller + "] (success)");
        if (!authController.isValidPDAMBiller(pBiller)) {
            throw new ServiceException(RC.ERROR_BLOCKED_TERMINAL, "Invalid biller " + pBiller);
        }
        LogService
                .getInstance(this)
                .trace().log("(" + corelation_id + ") After validate right pdam biller access [" + pBiller + "] (success)");
    }

    private Response constructHttpResponse(RC responseCode, JSONObject pResponse) {
        Response.Status tStatus;
        JSONObject tResponse;

        switch (responseCode) {
            case SUCCESS:
                tStatus = Response.Status.OK;
                tResponse = pResponse;
                break;

            case ERROR_TIMEOUT:
                tStatus = Response.Status.REQUEST_TIMEOUT;
                tResponse = new JSONObject().put("rcm", "Time Out");
                break;

            case ERROR_OTHER:
                tStatus = Response.Status.INTERNAL_SERVER_ERROR;
                tResponse = new JSONObject().put("rcm", "General Error");
                break;

            case ERROR_DATABASE:
                tStatus = Response.Status.INTERNAL_SERVER_ERROR;
                tResponse = new JSONObject().put("rcm", "Internal Server Error");
                break;

            case ERROR_TRANSACTION_FAILED_FROM_VENDING:
            case ERROR_INVALID_PARAMETER:
                tStatus = Response.Status.GATEWAY_TIMEOUT;
                tResponse = new JSONObject().put("rcm", "External Server Error");
                break;

            case ERROR_UNKNOWN_CERTIFICATE:
                tStatus = Response.Status.UNAUTHORIZED;
                tResponse = new JSONObject().put("rcm", pResponse.getString("rcm"));
                break;

            case ERROR_INVALID_MESSAGE:
            case ERROR_INVALID_KEY:
            case ERROR_INVALID_ACCESS_TIME:
            case ERROR_PRODUCT_NOT_AVAILABLE:
            case ERROR_UNREGISTERED_PARTNER_CENTRAL:
            case ERROR_BLOCKED_TERMINAL:
                tStatus = Response.Status.BAD_REQUEST;
                tResponse = new JSONObject().put("rcm", pResponse.getString("rcm"));
                break;

            default:
                tStatus = Response.Status.NOT_FOUND;
                tResponse = pResponse;
                break;
        }

        tResponse.put("rc", tStatus.getStatusCode() + responseCode.getResponseCodeString());
        return Response
                .status(tStatus)
                .entity(tResponse.toString())
                .type(MediaType.APPLICATION_JSON)
                .header("correlation_id", corelation_id)
                .build();

    }

    private void createCorelationID() {
        corelation_id = "id_" + UUID.randomUUID().toString().replace("-", "").substring(0, 15);
    }

}
