/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.app;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;

/**
 *
 * @author Lutfi
 */
public class Printer {

    private static final HashMap<String, PrintService> printMap = new HashMap<>();

    public Printer() {
        getPrinters();
    }
    
    public final void getPrinters() {
        DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
        PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
        PrintService printServices[] = PrintServiceLookup.lookupPrintServices(flavor, pras);

        for (PrintService printerService : printServices) {
            printMap.put(printerService.getName().trim(), printerService);
        }
    }
    
    public String getDefaultPrinter(){
        PrintService service = PrintServiceLookup.lookupDefaultPrintService();
        return service.getName().trim();
    }
    
    public boolean hasPrinterName(String printerName){
        return printMap.containsKey(printerName);
    }
    
    public List<String> getPrinterNameList(){
        List<String> list = new ArrayList<>();
        printMap.keySet().forEach(string -> {
            list.add(string);
        });
        return list;
    }

    public void print(String printerName, String text) {
        DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
        PrintService service = findPrintService(printerName);
        DocPrintJob job = service.createPrintJob();
        
        try {

            byte[] bytes;

            // important for umlaut chars
            bytes = text.getBytes("CP437");

            Doc doc = new SimpleDoc(bytes, flavor, null);
            
            job.print(doc, null);

        } catch (UnsupportedEncodingException | PrintException e) {
            Common.errorLog("[UnsupportedEncodingException | PrintException] Failed execute printer job", e);
            Common.showErrorMessage("Printer Error", null);
        }
    }
    
    public void print(String text) {
        DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
        PrintService service = PrintServiceLookup.lookupDefaultPrintService();
        DocPrintJob job = service.createPrintJob();
        
        try {

            byte[] bytes;

            // important for umlaut chars
            bytes = text.getBytes("CP437");

            Doc doc = new SimpleDoc(bytes, flavor, null);
            
            job.print(doc, null);

        } catch (UnsupportedEncodingException | PrintException e) {
            Common.errorLog("[UnsupportedEncodingException | PrintException] Failed execute printer job", e);
            Common.showErrorMessage("Printer Error", null);
        }
    }
    
    public void printBytes(String printerName, byte[] bytes) {

        DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
        PrintService service = findPrintService(printerName);
        DocPrintJob job = service.createPrintJob();

        try {

            Doc doc = new SimpleDoc(bytes, flavor, null);

            job.print(doc, null);

        } catch (PrintException e) {
            Common.errorLog("[UnsupportedEncodingException | PrintException] Failed execute printer job", e);
            Common.showErrorMessage("Printer Error", null);
        }
    }

    private PrintService findPrintService(String printerName) {
        
        if (printMap.containsKey(printerName)) {
            return printMap.get(printerName);
        }
        
        return null;
    }

    private static class SingletonHolder{
        public static final Printer holder = new Printer();
    }
    
    public static Printer instance(){
        return SingletonHolder.holder;
    }
}
