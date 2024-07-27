/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.database.entity;

import co.id.ez.database.enums.Operator;
import co.id.ez.database.enums.OrderType;
import co.id.ez.database.query.QueryConditional;
import co.id.ez.database.query.QueryGroup;
import co.id.ez.database.query.QueryOrder;
import co.id.ez.database.query.QueryType;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 *
 * @author RCS
 */
public abstract class DBFieldEntry {
    
    private final String cTableName;
    private final LinkedList<DBField> cListFieldEntry;
    private final LinkedList<WhereField> cListWhere;
    private final HashMap<QueryConditional, List<WhereField>> cListSubWhere;
    private QueryOrder cOrder;
    private QueryGroup cGroup;
    private final QueryType cType;

    public DBFieldEntry(QueryType pType, String cTableName) {
        this.cTableName = cTableName;
        this.cType = pType;
        cListFieldEntry = new LinkedList<>();
        cListWhere = new LinkedList<>();
        cListSubWhere = new HashMap();
        cGroup = null;
        cOrder = null;
    }
    
    private void validateEntryValue(String pFieldName){
        for (Iterator<DBField> it = cListFieldEntry.iterator(); it.hasNext();) {
            DBField dBField = it.next();
            if (dBField.getFeldName().equals(pFieldName)) {
                it.remove();
            }
        }
    }

    public String getTableName() {
        return cTableName+" ";
    }
    
    public void addEntryValue(String pFieldname, String pFieldValue){
        addEntryValue(pFieldname, pFieldValue, false);
    }
    
    public void addEntryValue(String pFieldname, String pFieldValue, boolean isDef){
        validateEntryValue(pFieldname);
        cListFieldEntry.add(new DBField(pFieldname, pFieldValue, isDef));
    }
    
    public void addEntry(String... pFieldname){
        for (String name : pFieldname) {
            cListFieldEntry.add(new DBField(name, null));
        }
    }
    
    public void addWhereValue(String pFieldname, String pFieldValue, Operator pOperator, QueryConditional pCon){
        cListWhere.add(new WhereField(pFieldname, pFieldValue, pOperator, pCon));
    }
    
    public void addWhereValue(WhereField... pWhereField){
        cListWhere.addAll(Arrays.asList(pWhereField));
    }
    
    public void addSubWhereValue(QueryConditional cond, List<WhereField> whereList){
        cListSubWhere.put(cond, whereList);
    }
    
    public void setOrder(QueryOrder pOrder){
        cOrder = pOrder;
    }
    
    public void setOrder(OrderType pOrdertype, String... pFieldName){
        setOrder(new QueryOrder(pOrdertype, pFieldName));
    }
    
    public void setOrder(String... pFieldName){
        setOrder(OrderType.ASC, pFieldName);
    }
    
    public void setGroup(QueryGroup pGroup){
        cGroup = pGroup;
    }
    
    public void setGroup(String... fieldName){
        setGroup(new QueryGroup(fieldName));
    }

    public LinkedList<DBField> getListFieldEntry() {
        return cListFieldEntry;
    }

    public LinkedList<WhereField> getListWhere() {
        return cListWhere;
    }

    public HashMap<QueryConditional, List<WhereField>> getListSubWhere() {
        return cListSubWhere;
    }

    public QueryOrder getOrder() {
        return cOrder;
    }

    public QueryGroup getGroup() {
        return cGroup;
    }
    
    public QueryType getQueryType() {
        return cType;
    }
    
    public abstract String getQueryValue();

    @Override
    public String toString() {
        String val = "Field Entry: { " + "Table= " + cTableName 
                + ", Field= [" ;
        
        int index = cListFieldEntry.size();
        for (DBField dBField : cListFieldEntry) {
            index--;
            if (cType != QueryType.SELECT) {
                val = val.concat(dBField.getFeldName()).concat("= ").concat(dBField.getFieldValue() == null ? "" : dBField.getFieldValue()).concat((index > 0 ? ", ": ""));
            }else{
                val = val.concat(dBField.getFeldName()).concat((index > 0 ? ", ": ""));
            }
        }
        
        if (cType != QueryType.INSERT) {
            val = val.concat("], Where= [");
            index = cListWhere.size();
            for (WhereField dBField : cListWhere) {
                index--;
                val = val.concat("{")
                        .concat("Field: ").concat(dBField.getFeldName()).concat(", ")
                        .concat("Value: ").concat(dBField.getFieldValue()).concat(", ")
                        .concat("Operator: ").concat(dBField.getOperator()).concat(", ")
                        .concat("Condition: ").concat(dBField.getConditional())
                        .concat((index > 0 ? "}," : "}"));
            }
            
            if (cType == QueryType.SELECT) {
                val = val.concat("]");
                val = val.concat(cOrder != null ? ", Order = " + cOrder.toString() : "");
                val = val.concat(cGroup != null ? ", Group=" + cGroup.toString() : "");
            }
        
        }else{
            val = val.concat("]");
        }
        
        
        val = val.concat("}");
        
        return val;
    }
    
    
}
