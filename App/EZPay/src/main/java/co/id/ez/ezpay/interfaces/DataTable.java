/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.interfaces;

/**
 *
 * @author RCS
 */
public interface DataTable {
    public Object[] getArrayData();
    
    default boolean isSuccess(){
        return true;
    }
    
    default void setSelected(Object select, int columnIndex){
    }
}
