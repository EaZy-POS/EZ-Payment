/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test.pagination;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

/**
 *
 * @author RCS
 */
public class Testssss {

    public static void main(String[] args) {
        LocalDate start = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate end = LocalDate.parse("2024-05-30");

        for (LocalDate date = start; date.isBefore(end); date = date.plusDays(1)) {
            // Do your job here with `date`.
            System.out.println(date);
        }
    }
}
