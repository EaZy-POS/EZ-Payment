/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.gateway.util;

import java.security.SecureRandom;
import java.util.Random;

/**
 *
 * @author RCS
 */
public class SNGenerator {
    private final String alphabets = "ABCDEFGHIJKL0123456789MNOPQRSTUVWXYZ";
    private int splitInto = 1;
    private char splitter = '-';
    private boolean isToSplit = false;
    private int LENGTH = 4;
    private final Random random;

    public SNGenerator() {
        this.random = new SecureRandom();
    }
    
    private static class singletonHolder{
        private static final SNGenerator INSTANCE = new SNGenerator();
    }

    public static SNGenerator getInstance(){
        return singletonHolder.INSTANCE;
    }

    public SNGenerator split(int splitInto, char splitter){
        this.splitInto = splitInto;
        this.splitter = splitter;
        isToSplit = true;
        return this;
    }

    private String split() throws InvalidSerialNumberLengthException {
        if(LENGTH % splitInto != 0){
            throw new InvalidSerialNumberLengthException();
        }

        StringBuilder sb = new StringBuilder();
        int spacer = 0, tempLength = LENGTH;

        while(tempLength > 0){
            if(spacer == (LENGTH / splitInto)){
                sb.append(splitter);
                spacer = 0;
            }
            tempLength--;
            spacer++;
            sb.append(pickRandomCharacter());
        }

        return sb.toString();
    }

    private char pickRandomCharacter() {
        return alphabets.charAt(random.nextInt(LENGTH));
    }

    /**
     * Generates random char string
     * @param pLength
     * @return 
     */
    public String generate(int pLength){

        LENGTH = pLength;
        StringBuilder serial = new StringBuilder();

        if(isToSplit){
            try {
                return split();
            } catch (InvalidSerialNumberLengthException e) {
//                Common.errorLog("Error Split SN", e);
            }
        }

        for (int i = 0; i < LENGTH; i++) serial.append(pickRandomCharacter());

        return serial.toString();
    }


    @Override
    public String toString() {
        return "Generates your serial numbers on the fry";
    }

    private static class InvalidSerialNumberLengthException extends Exception {
        InvalidSerialNumberLengthException(){
            super("Serial number length must be divisible by the number of parts to perform split action.");
        }
    }
}
