/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.central.enums;

import co.id.ez.central.handler.BalanceHandler;
import co.id.ez.central.handler.GetProfileHandler;
import co.id.ez.central.handler.MessageHandler;
import co.id.ez.central.handler.MiniStatementHandler;
import co.id.ez.central.handler.MultiPaymentBillerHandler;
import co.id.ez.central.handler.PDAMBillerHandler;
import co.id.ez.central.handler.PrepaidDenomHandler;
import co.id.ez.central.handler.RegistrasiHandler;
import co.id.ez.central.handler.TopupHandler;
import co.id.ez.central.handler.VoucherProductHandler;

/**
 *
 * @author RCS
 */
public enum RequestHandler {
    
    VOUCHER_PRODUCT(new VoucherProductHandler()),
    PDAM_BILLER(new PDAMBillerHandler()),
    PREPAID_DENOM(new PrepaidDenomHandler()),
    MP_BILLER(new MultiPaymentBillerHandler()),
    REGISTRASI(new RegistrasiHandler(), RequiredFields.REGISTRASI),
    BALANCE(new BalanceHandler()),
    MINISTATEMENT(new MiniStatementHandler(), RequiredFields.MINISTATEMENT),
    TOPUP_SALDO(new TopupHandler(), RequiredFields.TOPUP),
    GET_PROFILE(new GetProfileHandler()),;
    
    private final MessageHandler handler;
    private final RequiredFields requiredField;

    private RequestHandler(MessageHandler handler) {
        this(handler, null);
    }
    
    private RequestHandler(MessageHandler handler, RequiredFields requiredField) {
        this.handler = handler;
        this.requiredField = requiredField;
    }

    public MessageHandler getHandler() {
        return handler;
    }

    public RequiredFields getRequiredField() {
        return requiredField;
    }
    
}
