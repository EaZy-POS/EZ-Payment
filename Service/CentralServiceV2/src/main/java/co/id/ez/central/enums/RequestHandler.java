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
import co.id.ez.central.handler.module.ModuleAddHandler;
import co.id.ez.central.handler.module.ModuleListHandler;
import co.id.ez.central.handler.module.ModuleUpdateHandler;
import co.id.ez.central.handler.MultiPaymentBillerHandler;
import co.id.ez.central.handler.pdam.PDAMBillerHandler;
import co.id.ez.central.handler.prepaid.PrepaidDenomHandler;
import co.id.ez.central.handler.mitra.RegistrasiHandler;
import co.id.ez.central.handler.TopupHandler;
import co.id.ez.central.handler.mitra.AktifasiHandler;
import co.id.ez.central.handler.mitra.DeleteMitraHandler;
import co.id.ez.central.handler.mitra.MitraListHandler;
import co.id.ez.central.handler.mitra.UpdateMitraHandler;
import co.id.ez.central.handler.voucher.VoucherProductHandler;
import co.id.ez.central.handler.module.ModuleDeleteHandler;
import co.id.ez.central.handler.modulesyslist.ModuleSyslistAddHandler;
import co.id.ez.central.handler.modulesyslist.ModuleSyslistDeleteHandler;
import co.id.ez.central.handler.modulesyslist.ModuleSyslistListHandler;
import co.id.ez.central.handler.modulesyslist.ModuleSyslistUpdateHandler;
import co.id.ez.central.handler.pdam.PDAMBillerAddHandler;
import co.id.ez.central.handler.pdam.PDAMBillerDeleteHandler;
import co.id.ez.central.handler.pdam.PDAMBillerUpdateHandler;
import co.id.ez.central.handler.prepaid.PrepaidDenomAddHandler;
import co.id.ez.central.handler.prepaid.PrepaidDenomDeleteHandler;
import co.id.ez.central.handler.prepaid.PrepaidDenomUpdateHandler;
import co.id.ez.central.handler.voucher.VoucherProductAddHandler;
import co.id.ez.central.handler.voucher.VoucherProductDeleteHandler;
import co.id.ez.central.handler.voucher.VoucherProductMarkupHandler;
import co.id.ez.central.handler.voucher.VoucherProductUpdateHandler;
import co.id.ez.central.handler.voucher.provider.VoucherProviderAddHandler;
import co.id.ez.central.handler.voucher.provider.VoucherProviderDeleteHandler;
import co.id.ez.central.handler.voucher.provider.VoucherProviderHandler;
import co.id.ez.central.handler.voucher.provider.VoucherProviderUpdateHandler;
import co.id.ez.central.handler.voucher.provider.prefix.VoucherProviderPrefixAddHandler;
import co.id.ez.central.handler.voucher.provider.prefix.VoucherProviderPrefixDeleteHandler;
import co.id.ez.central.handler.voucher.provider.prefix.VoucherProviderPrefixHandler;
import co.id.ez.central.handler.voucher.provider.prefix.VoucherProviderPrefixUpdateHandler;

/**
 *
 * @author RCS
 */
public enum RequestHandler {
    
    VOUCHER_PRODUCT(new VoucherProductHandler()),
    ADD_VOUCHER_PRODUCT(new VoucherProductAddHandler()),
    UPDATE_VOUCHER_PRODUCT(new VoucherProductUpdateHandler()),
    DELETE_VOUCHER_PRODUCT(new VoucherProductDeleteHandler()),
    MARKUP_VOUCHER_PRODUCT(new VoucherProductMarkupHandler()),
    PDAM_BILLER(new PDAMBillerHandler()),
    ADD_PDAM_BILLER(new PDAMBillerAddHandler()),
    UPDATE_PDAM_BILLER(new PDAMBillerUpdateHandler()),
    DELETE_PDAM_BILLER(new PDAMBillerDeleteHandler()),
    PREPAID_DENOM(new PrepaidDenomHandler()),
    ADD_PREPAID_DENOM(new PrepaidDenomAddHandler()),
    UPDATE_PREPAID_DENOM(new PrepaidDenomUpdateHandler()),
    DELETE_PREPAID_DENOM(new PrepaidDenomDeleteHandler()),
    MP_BILLER(new MultiPaymentBillerHandler()),
    REGISTRASI(new RegistrasiHandler(), RequiredFields.REGISTRASI),
    AKTIFASI_MITRA(new AktifasiHandler(), RequiredFields.AKTIFASI_MITRA),
    BALANCE(new BalanceHandler()),
    MINISTATEMENT(new MiniStatementHandler(), RequiredFields.MINISTATEMENT),
    TOPUP_SALDO(new TopupHandler(), RequiredFields.TOPUP),
    GET_PROFILE(new GetProfileHandler()),
    MODULE_LIST(new ModuleListHandler()),
    ADD_MODULE_LIST(new ModuleAddHandler()),
    UPDATE_MODULE_LIST(new ModuleUpdateHandler()),
    DELETE_MODULE_LIST(new ModuleDeleteHandler()),
    MODULE_SYSTEM_LIST(new ModuleSyslistListHandler()),
    ADD_MODULE_SYSTEM_LIST(new ModuleSyslistAddHandler()),
    UPDATE_MODULE_SYSTEM_LIST(new ModuleSyslistUpdateHandler()),
    DELETE_MODULE_SYSTEM_LIST(new ModuleSyslistDeleteHandler()),
    VOUCHER_PROVIDER_LIST(new VoucherProviderHandler()),
    ADD_VOUCHER_PROVIDER_LIST(new VoucherProviderAddHandler()),
    UPDATE_VOUCHER_PROVIDER_LIST(new VoucherProviderUpdateHandler()),
    DELETE_VOUCHER_PROVIDER_LIST(new VoucherProviderDeleteHandler()),
    VOUCHER_PROVIDER_PREFIX_LIST(new VoucherProviderPrefixHandler()),
    ADD_VOUCHER_PROVIDER_PREFIX_LIST(new VoucherProviderPrefixAddHandler()),
    UPDATE_VOUCHER_PROVIDER_PREFIX_LIST(new VoucherProviderPrefixUpdateHandler()),
    DELETE_VOUCHER_PROVIDER_PREFIX_LIST(new VoucherProviderPrefixDeleteHandler()),
    MITRA_LIST(new MitraListHandler()),
    UPDATE_MITRA_LIST(new UpdateMitraHandler(), RequiredFields.UPDATE_MITRA),
    DELETE_MITRA_LIST(new DeleteMitraHandler(), RequiredFields.DELETE_MITRA);
    
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
