/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.database.query;

import co.id.ez.database.enums.OrderType;
import java.util.Arrays;
import java.util.LinkedList;

/**
 *
 * @author RCS
 */
public class QueryOrder {
    private final LinkedList<String> fieldName;
    private final OrderType order;

    public QueryOrder(OrderType order, String... fieldName) {
        this.fieldName = new LinkedList<>();
        this.fieldName.addAll(Arrays.asList(fieldName));
        this.order = order;
    }

    public String value() {
        if (fieldName.size() > 0) {
            String value = " ORDER BY ";
            int i = fieldName.size();
            for (String name : fieldName) {
                i--;
                value = value.concat(name).concat((i > 0 ? "," : " "));
            }
            
            value = value.concat(order.name());
            return value;
        }
        
        return "";
    }

    @Override
    public String toString() {
        return "{" + fieldName + ", order=" + order + '}';
    }
    
}
