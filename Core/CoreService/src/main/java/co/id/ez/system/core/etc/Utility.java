/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.system.core.etc;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import static org.fusesource.jansi.Ansi.ansi;

/**
 *
 * @author RCS
 */
public class Utility {
    
    public static void drawAppsName(Class pMain) {
        int width = 100;
        int height = 20;
        
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        g.setFont(new Font("SansSerif", Font.BOLD, 16));
        
        Graphics2D graphics = (Graphics2D) g;
        graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        graphics.drawString("EZ SYSTEM", 0, 20);
        
        createSeparator();
        
        for (int y = 0; y < height; y++) {
            StringBuilder sb = new StringBuilder();
            for (int x = 0; x < width; x++) {
                
                sb.append(image.getRGB(x, y) == -16777216 ? " " : "*");
                
            }
            
            if (sb.toString().trim().isEmpty()) {
                continue;
            }
            
            System.out.println(ansi().fgBlue().bold().a(sb).reset());
        }
        
        createSeparator();
        
        String version = pMain.getPackage().getImplementationVersion();
        if (version == null) {
            version = "0.0.0";
        }
        System.out.println(ansi().fgBlue().bold().a(String.format("%81s Version ", version)).reset());
        createSeparator();
    }
    
    public static void createSeparator() {
        StringBuilder sba = new StringBuilder();
        for (int x = 0; x < 90; x++) {
            sba.append("-");
        }
        
        System.out.println(ansi().fgBlue().bold().a(sba).reset());
    }
    
    public static void printError(Exception pException, String pMessage) {
        System.out.println(ansi().fgRed().bold().a(getStacktraceString(pException, pMessage)).reset());
    }
    
    public static String getStacktraceString(Exception pException, String pMessage) {
        String tMessage = pMessage.concat(getStacktraceString(pException));
        return tMessage;
    }
    
    public static String getStacktraceString(Exception pException) {
        if(pException.getCause() == null){
            return getStacktraceString((Throwable) pException);
        }else{
            return getStacktraceString(pException.getCause());
        }
    }
    
    public static String getStacktraceString(Throwable pThrowable) {
        String exceptionName = pThrowable == null ? "Null" : pThrowable.getClass().getSimpleName();

        String tMessage = ("\n [" + exceptionName + "] ")
                .concat(pThrowable == null ? "Null" : pThrowable.getMessage() == null ? "Null" : pThrowable.getMessage())
                .concat("\n");

        int index = 0;
        StackTraceElement[] tElm = pThrowable == null ? new StackTraceElement[0] : pThrowable.getStackTrace();
        for (StackTraceElement elm : tElm) {
            if (index < 30) {
                tMessage = tMessage.concat("  [Stacktrace: ")
                        .concat(elm.getClassName())
                        .concat("(").concat(elm.getMethodName())
                        .concat(":" + elm.getLineNumber()).concat(")]\n");
                index++;
            } else {
                tMessage = tMessage.concat("  and ")
                        .concat((tElm.length - index) + " more ")
                        .concat("................................................... ");
                break;
            }
        }

        return tMessage;
    }
    
    public static void printMessage(String pMessage) {
        System.out.println(ansi().fgBlue().bold().a(pMessage).reset());
    }
    
    public static String leftPadding(String pParam, int pLength, String pPad) {
        String tValue = pParam;
        if (tValue.length() > pLength) {
            tValue = tValue.substring(0, pLength);
        }
        
        while (tValue.length() < pLength) {            
            tValue = pPad + tValue;
        }
        
        return tValue;
    }
    
    public static String rightPadding(String pParam, int pLength, String pPad) {
        String tValue = pParam;
        if (tValue.length() > pLength) {
            tValue = tValue.substring(0, pLength);
        }
        
        while (tValue.length() < pLength) {            
            tValue = tValue + pPad;
        }
        
        return tValue;
    }
    
    public static String formatDateTime(final String pDateTime, final String pPatternInput, final String pPatternExpected) {
        String formattedDate = "";
        
        try {
            SimpleDateFormat tDateYears = new SimpleDateFormat(pPatternInput);
            Date tDate = tDateYears.parse(pDateTime);
            tDateYears = new SimpleDateFormat(pPatternExpected);
            formattedDate = tDateYears.format(tDate);
            
        } catch (ParseException ex) {
        }
        
        return formattedDate;
    }
}
