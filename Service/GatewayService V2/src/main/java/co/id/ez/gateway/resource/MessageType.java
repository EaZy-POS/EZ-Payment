/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.gateway.resource;

/**
 *
 * @author lutfi
 */
public enum MessageType {
    INQUIRY("INQ"),
    PAYMENT("PAY"),
    ADVICE("ADV");
    
    private final String command;

    private MessageType(String command) {
        this.command = command;
    }

    public String get() {
        return command;
    }
    
}
