/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.auth.resource;

import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.HttpHeaders;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

/**
 *
 * @author Lutfi
 */
@Path("")
public class AuthPlugin extends AuthHandler {
    
    @Produces(MediaType.APPLICATION_JSON)
    @POST
    @Path("/api/auth/validate")
    public Response validate(String pBodyMessage, @Context HttpHeaders pHeaders) {
        return handleRequest(pBodyMessage);
    }
}
