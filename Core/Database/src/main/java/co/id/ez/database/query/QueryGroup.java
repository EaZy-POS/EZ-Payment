/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.database.query;

import java.util.Arrays;
import java.util.LinkedList;

/**
 *
 * @author RCS
 */
public class QueryGroup {
    private final LinkedList<String> fieldName;

    public QueryGroup(String... fieldName) {
        this.fieldName = new LinkedList<>();
        this.fieldName.addAll(Arrays.asList(fieldName));
    }

    public String value() {
        if (fieldName.size() > 0) {
            String value = " GROUP BY ";
            int i = fieldName.size();
            for (String name : fieldName) {
                i--;
                value = value.concat(name).concat((i > 0 ? "," : " "));
            }
            return value;
        }
        
        return "";
    }

    @Override
    public String toString() {
        return fieldName.toString();
    }
    
}
