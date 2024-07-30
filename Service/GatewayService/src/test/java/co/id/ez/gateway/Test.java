/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.gateway;

import co.id.ez.system.core.ex.ServiceException;
import co.id.ez.system.core.rc.RC;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author RCS
 */
public class Test {
    public static void main(String[] args) {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String reqTime = format.format(new Date());
            String inqTime = "2024-07-30 09:31:17";

            Date curTime = format.parse(reqTime);
            Date tarTime = format.parse(inqTime);

            if ((((curTime.getTime() - tarTime.getTime()) / 1000) / 60) > 5) {
                throw new ServiceException(RC.ERROR_INVALID_ACCESS_TIME, "Request has been Expired, please try again!");
            }
        } catch (ParseException ex) {
            throw new ServiceException(RC.ERROR_OTHER, "Failed parsing date ", ex);
        }
    }
}
