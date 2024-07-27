/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.central.handler;

import com.json.JSONObject;

/**
 *
 * @author Lutfi
 */
public class GetProfileHandler extends MessageHandler{

    @Override
    public String performHandler(JSONObject request) {
        return request.toString();
    }
}
