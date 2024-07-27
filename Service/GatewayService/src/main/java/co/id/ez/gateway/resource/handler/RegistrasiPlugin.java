package co.id.ez.gateway.resource.handler;

///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package co.id.ez.gateway.resource;
//
//import co.id.ez.gateway.message.BillerRequest;
//import com.json.JSONObject;
//import jakarta.ws.rs.Path;
//import java.io.UnsupportedEncodingException;
//import java.math.BigDecimal;
//import java.net.URLDecoder;
//import java.nio.charset.StandardCharsets;
//import java.security.InvalidKeyException;
//import java.security.NoSuchAlgorithmException;
//import java.util.LinkedList;
//import java.util.List;
//import javax.crypto.BadPaddingException;
//import javax.crypto.IllegalBlockSizeException;
//import javax.crypto.NoSuchPaddingException;
//import co.id.ez.gateway.server.util.FeeMapping;
//import co.id.ez.gateway.server.util.RequestMapping;
//import co.id.ez.gateway.server.util.SNGenerator;
//import co.id.ez.gateway.server.util.enums.RequiredFields;
//import co.id.ez.gateway.server.util.enums.Tranmain;
//import com.json.JSONObject;
//import jakarta.ws.rs.POST;
//import jakarta.ws.rs.Path;
//import jakarta.ws.rs.Produces;
//import jakarta.ws.rs.core.Context;
//import jakarta.ws.rs.core.HttpHeaders;
//import jakarta.ws.rs.core.MediaType;
//import java.math.BigDecimal;
//import java.util.LinkedList;
//
///**
// *
// * @author RCS
// */
//@Path("")
//public class RegistrasiPlugin extends MessageHandler{
//
//    @Produces(MediaType.APPLICATION_JSON)
//    @POST
//    @Path("/api/registrasi")
//    public String registerDevice(String pBodyMessage, @Context HttpHeaders pHeaders) {
//        return handleInquiryRequest(pBodyMessage, pHeaders, null, Tranmain.PDAM);
//    }
//    
//    @Override
//    public String handleInquiryRequest(String pBodyMessage, @Context HttpHeaders pHeaders, RequiredFields pRequiredField, Tranmain pTranmain) {
//        try {
//            String tRequest = "";
//            try {
//                tRequest = URLDecoder.decode(pBodyMessage, StandardCharsets.UTF_8.toString()).replace(" ", "+");
//                streamLog("[REGISTRATION_DEVICE REQUEST ]: "+ tRequest);
//            } catch (UnsupportedEncodingException ex) {
//                throw new Exception("Error Parsing Request");
//            }
//            
//            RequestMapping map = validateMessage(pHeaders, null, pRequiredField, pTranmain);
//            String tResponseString ;
////            LinkedList<JSONObject> dataMitra = DB.getDataMitra(map.getMitra_id());
////            int licencecy = dataMitra.getFirst().getInt("licency");
////            int registered = dataMitra.getFirst().getInt("registered");
//            
////            if ( registered >= licencecy ) {
////                throw new SystemException(ResponseCode.ERROR_ACCOUNT_ALREADY_REGISTERED, "Mitra ID telah terdaftar!");
////            }
//            
//            JSONObject tJsonRequest = new JSONObject(tRequest);            
////            JSONObject res = registerDeviceForMitra(tJsonRequest, map, licencecy);
////            res.put("rc", "0000");
////            res.put("rcm", "SUKSES");
////            
////            tResponseString = res.toString();
////            
////            streamLog("[REGISTRATION_DEVICE RESPONSE ]: "+ tResponseString);
////            return tResponseString;
//
//        } 
////        catch (AppException ex) {
////            errorLog("Error on Handle request", ex);
////            ResponseCode tRC = ex.getResponseCode();
////            String tMsg = ex.getMessage();
////            
////            if(tRC.equals(ResponseCode.ERROR_DATABASE)){
////                tMsg = "Internal Error";
////            }
////            
////            JSONObject tResponse = constructErrorResponse(tRC.getResponseCodeString(), tMsg);
////            streamLog("[REGISTRATION_DEVICE RESPONSE ]: "+ tResponse.toString());
////            return tResponse.toString();
////        } 
//        catch (Exception ex) {
////            errorLog("[Exception] Error on Handle request", ex);
////            ResponseCode tRC = ResponseCode.ERROR_OTHER;
//            String tMsg = "Error Lainnya";
////            JSONObject tInquiryResponse = constructErrorResponse(tRC.getResponseCodeString(), tMsg);
////            streamLog("[REGISTRATION_DEVICE RESPONSE ]: "+ tInquiryResponse.toString());
////            return tInquiryResponse.toString();
//        }
//        
//        return pBodyMessage;
//    }
//    
//    @Override
//    public RequestMapping validateMessage(@Context HttpHeaders pHeaders, JSONObject request, RequiredFields requiredField, Tranmain pTranmainTable) {
//        RequestMapping reqMaps = validateHeader(pHeaders);
//        return reqMaps;
//    }
//    
//    @Override
//    public RequestMapping validateHeader(@Context HttpHeaders pHeaders) {
//        List<String> tHeaderMitraID = pHeaders.getRequestHeader("mitra-id");
//        List<String> tHeaderMitraKey = pHeaders.getRequestHeader("mitra-key");
//        List<String> tHeaderAuth = pHeaders.getRequestHeader("Authorization");
//
//        RequestMapping reqMaps = new RequestMapping(tHeaderMitraID.get(0), 
//                null, 
//                tHeaderMitraKey.get(0), 
//                tHeaderAuth.get(0));
//        
//        return reqMaps;
//    }
//    
//
//    @Override
//    public BillerRequest constructBillerRequest(JSONObject request) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public BigDecimal getTransactAmountBiller(JSONObject pRequestMessage) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public FeeMapping constructFeeMapping(BigDecimal amount, RequestMapping requestMapping) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public String constructSuccessfullInquiryResponse(JSONObject pBillerResponse, FeeMapping feeMapping) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public void validateTransactAmount(JSONObject request, LinkedList<JSONObject> pTranmainDB) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public void insertSuccessfullResponseToTranmain(JSONObject request, RequestMapping msg, FeeMapping feeMapping) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    @Override
//    public void updateSuccessfullResponseToTranmain(JSONObject request, RequestMapping msg) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }
//
//    private JSONObject registerDeviceForMitra(JSONObject pRequest, RequestMapping reqMap, int licence){
//        JSONObject tObject = new JSONObject();
////        try {
////            String deviceDataEnc = pRequest.getString("device");
////            String device = SystemConfig.getEncryptor().decrypt(deviceDataEnc, reqMap.getMitra_key());
////            JSONObject jsonData = new JSONObject(device);
////            String comp_name = jsonData.getString("COMPUTERNAME");
////            String os = jsonData.getString("OS");
////            String proc_rev = jsonData.getString("PROCESSOR_REVISION");
////            String proc_idnt = jsonData.getString("PROCESSOR_IDENTIFIER");            
////            String deviceID = SystemConfig.getEncryptor().encrypt((getUUIDString()+(comp_name+os+proc_rev+proc_idnt).hashCode()), reqMap.getMitra_key());
////            String snkey = SystemConfig.getEncryptor().encrypt(new SNGenerator(16).split(4, '-').generate(), reqMap.getMitra_key());
////            String tLicency = SystemConfig.getEncryptor().encrypt(String.valueOf(licence), reqMap.getMitra_key());
////            String tRegDate = SystemConfig.getEncryptor().encrypt(EZDate.SQLDATE.today(), reqMap.getMitra_key());
////            DB.insertDeviceMitra(comp_name, os, proc_rev, proc_idnt, deviceID, snkey,reqMap);
////            
////            tObject.put("pvt_0", deviceID);
////            tObject.put("pvt_1", snkey);
////            tObject.put("pvt_2", tLicency);
////            tObject.put("pvt_3", tRegDate);
////            
////        } catch (UnsupportedEncodingException | NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | IllegalBlockSizeException | BadPaddingException ex) {
////            throw new AppException(ResponseCode.ERROR_TIMEOUT, "Internal Error");
////        }
////        
//        return tObject;
//    }
//    
//    private String getUUIDString(){
//        return SNGenerator.getInstance().generate(8);
//    }
//}
