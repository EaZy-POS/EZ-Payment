/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.msg.mp;

import co.id.ez.ezpay.enums.RequestType;
import co.id.ez.ezpay.msg.BillerMessage;
import com.json.JSONObject;
import java.math.BigDecimal;

/**
 *
 * @author Lutfi
 */
public class MPAdvice extends BillerMessage{

    private String input1, input2, input3, trxid;
    private final String biller;
    private BigDecimal amount;
    
    public MPAdvice(String biller) {
        super(RequestType.parse(biller), "GP");
        this.biller = biller;
    }

    @Override
    public JSONObject getBodyRequest(){
        JSONObject req = new JSONObject();
        req.put("modul", getModuleid())
                .put("biller", biller)
                .put("command", getCommand())
                .put("trxid", getTrxid())
                .put("amount", getAmount().doubleValue());

        if (input1 != null) {
            req.put("input1", input1);
        }
        
        if (input2 != null) {
            req.put("input2", input2);
        }
        
        if (input3 != null) {
            req.put("input3", input3);
        }

        return req;
    }

    @Override
    public String getPath() {
        return "/api/mp/adv";
    }

    @Override
    public String getCommand() {
        return "ADV";
    }

    public String getInput1() {
        return input1;
    }

    public void setInput1(String input1) {
        this.input1 = input1;
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

    public String getTrxid() {
        return trxid;
    }

    public void setTrxid(String trxid) {
        this.trxid = trxid;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

}
