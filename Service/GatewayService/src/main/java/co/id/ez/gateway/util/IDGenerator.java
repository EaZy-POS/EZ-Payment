/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.gateway.util;

import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

/**
 *
 * @author Lutfi
 */
public class IDGenerator {

    private static final HashMap<String, BigInteger> mIDContainer = new HashMap<>();
    private static Date mDate;

    public static String get(String moduleCode) {
        return "TRX"
                + moduleCode.toUpperCase()
                + getDate()
                + getRandomNumber()
                + getSeqNumber(moduleCode);
    }

    private static String getDate() {
        Date tCurrentDate = new Date();

        if (mDate == null) {
            mDate = tCurrentDate;
            mIDContainer.clear();
        }

        SimpleDateFormat tFormater = new SimpleDateFormat("yyyyMMdd");
        
        if (!tFormater.format(mDate).equals(tFormater.format(tCurrentDate))) {
            mDate = tCurrentDate;
            mIDContainer.clear();
        }

        return tFormater.format(mDate);
    }

    private static String getRandomNumber() {
        String tRandom = String.valueOf(Math.abs(new Random().nextInt()));

        if (tRandom.length() > 4) {
            tRandom = tRandom.substring(0, 4);
        }

        while (tRandom.length() < 4) {
            tRandom = tRandom + "0";
        }

        return tRandom;
    }

    private static String getSeqNumber(String moduleCode) {
        BigInteger tNumber;
        
        if (mIDContainer.containsKey(moduleCode)) {
            tNumber = mIDContainer.get(moduleCode).add(BigInteger.ONE);
        } else {
            tNumber = BigInteger.ONE;
        }

        if (tNumber.compareTo(new BigInteger("9999")) > 0) {
            tNumber = BigInteger.ONE;
        }

        mIDContainer.put(moduleCode, tNumber);
        String tVAl = tNumber.toString();

        if (tVAl.length() > 4) {
            tVAl = tVAl.substring(0, 4);
        }

        while (tVAl.length() < 4) {
            tVAl = "0" + tVAl;
        }

        return tVAl;
    }
}
