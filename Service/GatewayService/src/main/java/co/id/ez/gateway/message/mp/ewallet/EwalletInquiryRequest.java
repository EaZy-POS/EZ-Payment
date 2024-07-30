/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.gateway.message.mp.ewallet;

import co.id.ez.gateway.message.mp.MPInquiryRequest;
import co.id.ez.system.core.ex.ServiceException;
import co.id.ez.system.core.log.LogService;
import co.id.ez.system.core.rc.RC;

/**
 *
 * @author RCS
 */
public class EwalletInquiryRequest extends MPInquiryRequest {

    @Override
    public String getMessageStream() {
        
        if(getInput2() == null){
            throw new ServiceException(RC.ERROR_INVALID_MESSAGE, "The value of input2 is null");
        }
        
        try {
            Double.parseDouble(getInput2());
        } catch (NumberFormatException e) {
            throw new ServiceException(RC.ERROR_INVALID_MESSAGE, "The value of input2 must be a number");
        }
        
        LogService.getInstance(this).trace().log("Get Ewallet inquiry stream");
        
        return super.getBasicMessageStream()+ "&nohp=" + getInput1() + "&produk=" + getBiller()
                + "&nominal=" + getInput2() ;
    }

    @Override
    public String getModuleCode() {
        return "EWL";
    }
    
    @Override
    public String getModule() {
        return "ewallet";
    }
    
    @Override
    public String getComand() {
        return "INQ";
    }
}
