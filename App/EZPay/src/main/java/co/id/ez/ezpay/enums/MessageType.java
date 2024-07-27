/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.enums;

/**
 *
 * @author Lutfi
 */
public enum MessageType {

    FATAL_ERROR("Fatal Error!\nSilahkan Hubungi Helpdesk Kami"),
    SYSTEM_ERROR("Terjadi kesalahan pada system!\nSilahkan Hubungi Helpdesk Kami"),
    
    ;
    
    private final String message;

    private MessageType(String module) {
        this.message = module;
    }
    
    public String getMessage() {
        return message;
    }
}
