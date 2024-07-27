/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.util.swings.table;

import co.id.ez.ezpay.enums.util.Aligment;
import co.id.ez.ezpay.enums.util.CellType;

/**
 *
 * @author RCS
 */
public class TableHeader {
    private final String columnName;
    private final int columnLength;
    private final Aligment aligment;
    private final CellType cellType;

    public TableHeader(String columnName, int columnLength, Aligment aligment) {
        this(columnName, columnLength, aligment, CellType.OBJECT);
    }
    
    public TableHeader(String columnName, int columnLength, Aligment aligment, CellType cellType) {
        this.columnName = columnName;
        this.columnLength = columnLength;
        this.aligment = aligment;
        this.cellType = cellType;
    }

    public String getColumnName() {
        return columnName;
    }

    public int getColumnLength() {
        return columnLength;
    }

    public Aligment getAligment() {
        return aligment;
    }

    public CellType getCellType() {
        return cellType;
    }
    
    public boolean isEditable(){
        return cellType.isIsEditable();
    }

    public static TableHeader create(
            String columnName, CellType cellType){
        return new TableHeader(columnName, 0, Aligment.LEFT, cellType);
    }
    
    public static TableHeader create(
            String columnName, CellType cellType, int columnLength){
        return new TableHeader(columnName, columnLength, Aligment.LEFT, cellType);
    }
    
    public static TableHeader create(
            String columnName, int columnLength){
        return new TableHeader(columnName, columnLength, Aligment.LEFT);
    }
    
    public static TableHeader create(
            String columnName, int columnLength, Aligment aligment){
        return new TableHeader(columnName, columnLength, aligment);
    }
    
    public static TableHeader create(
            String columnName, int columnLength, Aligment aligment, CellType cellType){
        return new TableHeader(columnName, columnLength, aligment, cellType);
    }

    @Override
    public String toString() {
        return "TableHeader{" + "columnName=" + columnName + ", columnLength=" + columnLength + ", aligment=" + aligment + ", cellType=" + cellType + '}';
    }
    
    
}
