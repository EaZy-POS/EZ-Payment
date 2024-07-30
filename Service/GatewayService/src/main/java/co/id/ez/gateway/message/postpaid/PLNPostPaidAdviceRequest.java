/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.gateway.message.postpaid;

/**
 *
 * @author RCS
 */
public class PLNPostPaidAdviceRequest extends PLNPostPaidPaymentRequest{

    @Override
    public String getComand() {
        return "ADV";
    }

}
