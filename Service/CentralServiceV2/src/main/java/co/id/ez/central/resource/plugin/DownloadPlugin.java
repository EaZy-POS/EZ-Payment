/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.central.resource.plugin;

import co.id.ez.central.resource.CentralHandler;
import co.id.ez.system.core.config.ConfigService;
import co.id.ez.system.core.log.LogService;
import co.id.ez.system.core.rc.RC;
import com.json.JSONObject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.io.File;
import java.sql.SQLException;

/**
 *
 * @author Lutfi
 */
@Path("")
public class DownloadPlugin extends CentralHandler {
    
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes("application/json")
    @GET
    @Path("/api/cekupdate")
    public Response cekUpdate() {
        
        JSONObject update = new JSONObject();
        
        try {
            update = controller.getAppUpdate();
        } catch (SQLException e) {
            LogService.getInstance(this).dbError().withCause(e)
                    .log("[SQLException] Failed cek update application", true);
            
            update.put("rc", "0091");
            update.put("rcm", "Internal error");
        } catch (Exception e) {
            LogService.getInstance(this).dbError().withCause(e)
                    .log("[Exception] Failed cek update application", true);
            
            update.put("rc", "0005");
            update.put("rcm", "Internal server error");
        }
        
        return constructHttpResponse(RC.parseResponseCodeString(update.getString("rc")), update);
    }
    
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    @Consumes("application/x-www-form-urlencoded")
    @GET
    @Path("/api/download")
    public Response downloadFile(@QueryParam("version") String version, @QueryParam("file") String file) {
        String tUpdateFilePath = ConfigService
                .getInstance().getString("update-path", "/usr/update/")
                .concat(version).concat("/").concat(file);
        
        LogService.getInstance(this).stream().log("Download file: "+ tUpdateFilePath);
        
        File fileDownload = new File(tUpdateFilePath);
        
        LogService.getInstance(this).stream().log("Download file -> "+ fileDownload + ", isExsist ? "+ fileDownload.exists());
        if (fileDownload.exists()) {
            return Response
                    .status(Response.Status.OK)
                    .type(MediaType.APPLICATION_FORM_URLENCODED)
                    .header("Content-Disposition", "attachment;filename="+tUpdateFilePath)
                    .entity(fileDownload)
                    .build();
        }
        
        return Response
                .status(Response.Status.NO_CONTENT)
                .entity("File not found").build();
    }

}
