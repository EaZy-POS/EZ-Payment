/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.gateway.message.mp;

import co.id.ez.gateway.message.BillerRequest;

/**
 *
 * @author lutfi
 */
public class MPInquiryRequest extends BillerRequest {

    private String input1, input2, input3;
    private String biller;

    public String getInput1() {
        return input1;
    }

    public void setInput1(String input1) {
        this.input1 = input1;
    }

    public String getBiller() {
        return biller;
    }

    public void setBiller(String biller) {
        this.biller = biller;
    }

    public String getInput2() {
        return input2;
    }

    public void setInput2(String input2) {
        this.input2 = input2;
    }

    public String getInput3() {
        return input3;
    }

    public void setInput3(String input3) {
        this.input3 = input3;
    }

    @Override
    public String getMessageStream() {
        return super.getMessageStream() + "&input1=" + input1 + "&biller=" + biller
                + (input2 != null ? input2.equals("") ? "&input2=" + input2 : "" : "")
                + (input3 != null ? input3.equals("") ? "&input3=" + input3 : "" : "");
    }

    @Override
    public String getRemarks() {
        return super.getRemarks() + " idpel " + input1 + "(" + biller + ")";
    }

    @Override
    public String getModuleCode() {
        return "MTP";
    }

    @Override
    public String getModule() {
        return "gp";
    }

    @Override
    public String getComand() {
        return "INQ";
    }

}
