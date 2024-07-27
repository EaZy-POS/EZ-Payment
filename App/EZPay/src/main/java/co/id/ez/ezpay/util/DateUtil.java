/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author RCS
 */
public enum DateUtil {
    
    SUNDAYS(1, "Minggu", Group.DAY),
    MONDAYS(2, "Senin", Group.DAY),
    TUESDAYS(3, "Selasa", Group.DAY),
    WEDNESDAYS(4, "Rabu", Group.DAY),
    THURSDAYS(5, "Kamis", Group.DAY),
    FRIDAYS(6, "Jumat", Group.DAY),
    SATURDAYS(7, "Sabtu", Group.DAY),
    JANUARY(1, "January", "Jan", Group.MOUNTH),
    FEBRUARY(2, "February", "Feb", Group.MOUNTH),
    MARCH(3, "Maret", "Mar", Group.MOUNTH),
    APRIL(4, "April", "Apr", Group.MOUNTH),
    MEI(5, "Mei", Group.MOUNTH),
    JUNE(6, "Juni", "Jun", Group.MOUNTH),
    JULY(7, "Juli", "Jul", Group.MOUNTH),
    AUGUST(8, "Agustus", "Ags", Group.MOUNTH),
    SEPTEMBER(9, "September", "Sep", Group.MOUNTH),
    NOVEMVER(11, "November", "Nov", Group.MOUNTH),
    OCTOBER(10, "Oktober", "Okt", Group.MOUNTH),
    DECEMBER(12, "Desember", "Des", Group.MOUNTH),
    ;
    
    private final int index;
    private final String name, simpeName;
    private final Group group;

    private DateUtil(int index, String name, Group group) {
        this(index, name, name, group);
    }
    
    private DateUtil(int index, String name, String simpleName, Group group) {
        this.index = index;
        this.name = name;
        this.group = group;
        this.simpeName = simpleName;
    }

    public String value() {
        return name;
    }
    
    public int getDay(){
        return index;
    }

    public String simpeValue() {
        return simpeName;
    }

    public static String createDateToday(){
        Date date = new Date();
        String tDayName;
        SimpleDateFormat format = new SimpleDateFormat("E, dd/MM/yyyy");
        String tFormatedDate = format.format(date);
        String tDay = tFormatedDate.substring(0, tFormatedDate.indexOf(","));
        for (DateUtil value : DateUtil.values()) {
            if (value.group == Group.DAY) {
                if(value.name().startsWith(tDay.toUpperCase())){
                    tDayName = value.value();
                    tFormatedDate = tFormatedDate.replace(tDay, tDayName);
                    break;
                }
            }
        }
        
        return tFormatedDate;
    }
    
    public static String getMonth(int month){
        return getMonth(month, false);
    }
    
    public static String getSimpleMonth(int month){
        return getMonth(month, true);
    }
    
    private static String getMonth(int month, boolean isSimple){
        for (DateUtil value : DateUtil.values()) {
            if (value.group == Group.MOUNTH) {
                if(value.index == month){
                    if(isSimple){
                        return value.simpeValue();
                    }else{
                        return value.value();
                    }
                }
            }
        }
        
        return null;
    }
    
    public static String getTime(){
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        return format.format(new Date());
    }
    
    private enum Group{
        MOUNTH,
        DAY
    }
 
    public static String getDateTimeTodayWithFormat(String pFormat){
        SimpleDateFormat format = new SimpleDateFormat(pFormat);
        return format.format(new Date());
    }
}
