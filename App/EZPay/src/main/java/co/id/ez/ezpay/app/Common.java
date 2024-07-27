/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.app;

import co.id.ez.ezpay.MainApps;
import co.id.ez.ezpay.enums.MessageType;
import co.id.ez.ezpay.enums.util.Icons;
import co.id.ez.ezpay.enums.util.InputType;
import co.id.ez.ezpay.model.data.detail.DetailDataFactory;
import co.id.ez.ezpay.util.swings.LoadingViewer;
import co.id.ez.ezpay.view.DetailForm;
import co.id.ez.ezpay.view.Home;
import co.id.ez.system.core.log.LogService;
import com.json.JSONArray;
import java.awt.Component;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingWorker;

/**
 *
 * @author Lutfi
 */
public abstract class Common {
    
    public static final MainApps loogingHook = new MainApps();
    private static Home home;
    private static LoadingViewer loading = new LoadingViewer(null);
    
    public static Home getHome() {
        return home;
    }
    
    public static void setHome(Home main) {
        home = main;
        loading = new LoadingViewer(main);
    }
    
    public static int showMessage(String message, int type) {
        return showMessage(message, type, null);
    }
    
    public static int showMessage(String message, int type, Component parent) {
        MainApps.instance.setLookAndFeel(true);
        switch (type) {
            case JOptionPane.WARNING_MESSAGE:
                JOptionPane.showMessageDialog(parent, message, "Peringatan", type);
                break;
            case JOptionPane.ERROR_MESSAGE:
                JOptionPane.showMessageDialog(parent, message, "Kesalahan", type);
                break;
            case JOptionPane.INFORMATION_MESSAGE:
                JOptionPane.showMessageDialog(parent, message, "Info", type);
                break;
            default:
                break;
        }
        
        return 0;
    }
    
    public static void showWarningMessage(String Message, Component owner) {
        showMessage(Message, JOptionPane.WARNING_MESSAGE, owner);
    }
    
    public static void showErrorMessage(String Message, Component owner) {
        showMessage(Message, JOptionPane.ERROR_MESSAGE, owner);
    }
    
    public static void showErrorMessage(MessageType Message, Component owner) {
        showMessage(Message.getMessage(), JOptionPane.ERROR_MESSAGE, owner);
    }
    
    public static void showInfoMessage(String Message, Component owner) {
        showMessage(Message, JOptionPane.INFORMATION_MESSAGE, owner);
    }
    
    public static int showConfirmMessage(String Message, Component owner) {
        return JOptionPane.showConfirmDialog(owner, Message, "Konfirmasi", JOptionPane.YES_NO_OPTION);
    }
    
    public static void streamLog(Object message) {
        new Thread(() -> {
            LogService.getInstance(loogingHook).stream().log(message);
        }).start();
    }
    
    public static void databaseLog(Object message) {
        new Thread(() -> {
            LogService.getInstance(loogingHook).db().log(message);
        }).start();
    }
    
    public static void errorLog(Object message) {
        new Thread(() -> {
            LogService.getInstance(loogingHook).error().log(message);
        }).start();
    }
    
    public static void errorLog(Object message, Exception ex) {
        new Thread(() -> {
            LogService.getInstance(loogingHook).error().withCause(ex).log(message.toString(), true);
        }).start();
    }
    
    public static void traceLog(Object message) {
        new Thread(() -> {
            LogService.getInstance(loogingHook).trace().log(message.toString());
        }).start();
    }
    
    public static void clearText(JTextField... textField) {
        clearTexts(textField);
    }
    
    public static void clearTexts(Component... textField) {
        for (Component field : textField) {
            if (field instanceof JTextField) {
                JTextField f = (JTextField) field;
                f.setText("");
            }
            
            if (field instanceof JLabel) {
                JLabel f = (JLabel) field;
                f.setText("");
            }
            
            if (field instanceof JButton) {
                JButton f = (JButton) field;
                f.setText("");
            }
        }
    }
    
    public static void activateComponent(boolean isEnable, Component... button) {
        for (Component com : button) {
            if (com instanceof JButton) {
                com.setEnabled(isEnable);
            }
            
            if (com instanceof JTextField) {
                ((JTextField) com).setEditable(isEnable);
            }
            
            if(com instanceof JComboBox){
                ((JComboBox) com).setEnabled(isEnable);
            }
        }
    }
    
    public static void setOpaqueComponent(boolean isEnable, JComponent... com) {
        for (JComponent jPanel : com) {
            jPanel.setOpaque(isEnable);
        }
    }
    
    public static void setVisibility(boolean isEnable, Component... button) {
        for (Component com : button) {
            com.setVisible(isEnable);
        }
    }
    
    public static String formatRupiah(double val) {
        return formatRupiah(val, true);
    }
    
    public static String formatRupiah(double val, boolean isUsCurrency) {
        return (isUsCurrency ? "Rp. " : "") + new DecimalFormat("#,##0").format(val);
    }
    
    public static String escape(String s) {
        return s.replace("\\", "\\\\")
                .replace("\t", "\\t")
                .replace("\b", "\\b")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\f", "\\f")
                .replace("\'", "\\'")
                .replace("\"", "\\\"");
    }
    
    public static String leftPadding(String pTarget, int pLength, String pPadding) {
        if (pTarget.length() > pLength) {
            pTarget = pTarget.substring(0, pLength);
        }
        
        while (pTarget.length() < pLength) {            
            pTarget = pPadding + pTarget;
        }
        
        return pTarget;
    }
    
    public static String rigthPadding(String pTarget, int pLength, String pPadding) {
        if (pTarget.length() > pLength) {
            pTarget = pTarget.substring(0, pLength);
        }
        
        while (pTarget.length() < pLength) {            
            pTarget = pTarget + pPadding;
        }
        
        return pTarget;
    }
    
    public static String centerPadding(String pTarget, int pLength, String pPadding) {
        
        if (pTarget.length() > pLength) {
            pTarget = pTarget.substring(0, pLength);
        }
        
        while (pTarget.length() < pLength) {            
            pTarget = pPadding + pTarget + pPadding;
        }
        
        if (pTarget.length() > pLength) {
            pTarget = pTarget.substring(0, pLength);
        }
        
        return pTarget;
    }
    
    public static void showDetailForm(DetailDataFactory data, Component comp, String title) {
        showDetailForm(data, comp, title, false);
    }
    
    public static void showDetailForm(DetailDataFactory data, Component comp, String title, boolean isPrintable) {
        MainApps.instance.setLookAndFeel(true);
        DetailForm dialog = new DetailForm(Common.getHome(), true, data, isPrintable);
        dialog.setTitle(title);
        dialog.setLocationRelativeTo(comp);
        dialog.setVisible(true);
    }
    
    public static String getCurentDate() {
        SimpleDateFormat format = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss");
        Date date = new Date();
        return format.format(date);
    }
    
    public static JSONArray getDataRegions() {
        JSONArray result;
        String fileresult = "";
        
        InputStream is = Icons.class.getResourceAsStream("/regions.json");
        try (Scanner myReader = new Scanner(is)) {
            while (myReader.hasNextLine()) {
                fileresult = fileresult.concat(myReader.nextLine());
            }
        }
        
        result = new JSONArray(fileresult);
        
        return result;
    }
    
    public static boolean validateAlfaNumber(String pInput) {
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9]+$");
        Matcher matcher = pattern.matcher(pInput);
        return matcher.matches();
    }
    
    public static boolean validateNumericOnly(String pInput) {
        Pattern pattern = Pattern.compile("^[0-9]+$");
        Matcher matcher = pattern.matcher(pInput);
        return matcher.matches();
    }
    
    public static boolean validatePasswordFormat(String pInput) {
        Pattern pattern = Pattern.compile("^.*(?=.{8,})(?=.*\\d)(?=.*[a-zA-Z])|(?=.{8,})(?=.*\\d)(?=.*[!@#$%^&])|(?=.{8,})(?=.*[a-zA-Z])(?=.*[!@#$%^&]).*$");
        Matcher matcher = pattern.matcher(pInput);
        return matcher.matches();
    }
    
    public static boolean validateUserNameFormat(String pInput) {
        boolean isRigth = validateAlfaNumber(pInput);
        Pattern pattern = Pattern.compile("^.*(?=.{8,}).*$");
        Matcher matcher = pattern.matcher(pInput);
        return isRigth && matcher.matches();
    }
    
    public static boolean validateNumberFormat(String pInput) {
        Pattern pattern = Pattern.compile("^[0-9]+$");
        Matcher matcher = pattern.matcher(pInput);
        return matcher.matches();
    }
    
    public static boolean validatePhoneNumberFormat(String pInput) {
        Pattern pattern = Pattern.compile("^(?:([0-9]{1})*[- .(]*([0-9]{3})[- .)]*[0-9]{3}[- .]*[0-9]{4})+$");
        Matcher matcher = pattern.matcher(pInput);
        return matcher.matches();
    }
    
    public static boolean validateEmailFormat(String pInput) {
        Pattern pattern = Pattern.compile("^[a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$");
        Matcher matcher = pattern.matcher(pInput);
        return matcher.matches();
    }
    
    public static boolean validateFormatText(JTextField target, InputType type) {
        switch (type) {
            case PHONE_NUMBER:
                return validatePhoneNumberFormat(target.getText());
            case EMAIL:
                return validateEmailFormat(target.getText());
            case NUMERIC:
                return validateNumericOnly(target.getText());
            case ALFANUMBERIC:
                return validateAlfaNumber(target.getText());
            case PASSWORD:
                return validatePasswordFormat(target.getText());
            case USERNAME:
                return validateUserNameFormat(target.getText());
        }
        return true;
    }
    
    public static void showLoadingTask(SwingWorker worker) {
        loading.runningTask(worker);
    }
    
    public static void hideLoadingTask() {
        loading.setVisible(false);
    }
}
