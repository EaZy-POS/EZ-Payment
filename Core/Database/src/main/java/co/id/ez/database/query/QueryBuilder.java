/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.database.query;

import co.id.ez.database.entity.DBField;
import co.id.ez.database.entity.DBFieldEntry;
import co.id.ez.database.entity.WhereField;
import co.id.ez.database.enums.Operator;

/**
 *
 * @author RCS
 */
public class QueryBuilder extends DBFieldEntry{

    public QueryBuilder(String cTableName, QueryType pType) {
        super(pType, cTableName);
    }

    @Override
    public String getQueryValue() {
        String tQuery = "";
        if (getQueryType() == QueryType.INSERT) {
            
            tQuery = getQueryType().getCommand() + getTableName()+" (";
            int i = getListFieldEntry().size();
            String value = "";
            for (DBField dBField : getListFieldEntry()) {
                i--;
                tQuery = tQuery.concat(dBField.getFeldName()).concat((i > 0 ? ", ": ""));
                if (dBField.isIsDefault()) {
                    value = value.concat(dBField.getFieldValue()).concat((i > 0 ? ", ": ""));
                }else{
                    value = value.concat("'"+dBField.getFieldValue()).concat("'" + (i > 0 ? ", ": ""));
                }
            }
            tQuery = tQuery.concat(") VALUES (").concat(value).concat(")");
        }
        
        if (getQueryType() == QueryType.UPDATE) {
            tQuery = getQueryType().getCommand() + getTableName()+" SET ";
            int i = getListFieldEntry().size();
            for (DBField dBField : getListFieldEntry()) {
                i--;
                if (dBField.isIsDefault()) {
                    tQuery = tQuery.concat(dBField.getFeldName()).concat("=").concat(dBField.getFieldValue() + (i > 0 ? ", ": ""));
                }else{
                    tQuery = tQuery.concat(dBField.getFeldName()).concat("=").concat("'"+dBField.getFieldValue()+"'" + (i > 0 ? ", ": ""));
                }
            }
            
            if (getListWhere().size() > 0) {
                tQuery = tQuery.concat(" WHERE ");
            }
            
            i = 0;
            for (WhereField whereField : getListWhere()) {
                if (whereField.isIsDefault()) {
                    tQuery = tQuery.concat((i == 0 ? "" : (whereField.getConditional())))
                            .concat(whereField.getFeldName())
                            .concat(whereField.getOperator())
                            .concat(whereField.getFieldValue());
                } else {
                    tQuery = tQuery.concat((i == 0 ? "" : whereField.getConditional()))
                            .concat(whereField.getFeldName())
                            .concat(whereField.getOperator())
                            .concat("'" + whereField.getFieldValue() + "'");
                }
                i++;
            }
        }
        
        if (getQueryType() == QueryType.DELETE) {
            tQuery = getQueryType().getCommand() + getTableName();
            int i;
            
            if (getListWhere().size() > 0) {
                tQuery = tQuery.concat(" WHERE ");
            }
            
            i = 0;
            for (WhereField whereField : getListWhere()) {
                if (whereField.isIsDefault()) {
                    tQuery = tQuery.concat((i == 0 ? "" : whereField.getConditional())).concat(whereField.getFeldName())
                            .concat(whereField.getOperator())
                            .concat(whereField.getFieldValue());
                } else {
                    tQuery = tQuery.concat((i == 0 ? "" : whereField.getConditional())).concat(whereField.getFeldName())
                            .concat(whereField.getOperator())
                            .concat("'" + whereField.getFieldValue() + "'");
                }
                i++;
            }
        }
        
        if (getQueryType() == QueryType.SELECT) {
            tQuery = getQueryType().getCommand();
            int i = getListFieldEntry().size();
            for (DBField dBField : getListFieldEntry()) {
                i--;
                tQuery = tQuery.concat(dBField.getFeldName()).concat((i > 0 ? ", ": ""));
            }
            
            tQuery = tQuery.concat(" FROM "+ getTableName());
            
            if (getListWhere().size() > 0) {
                tQuery = tQuery.concat(" WHERE ");
            }
            
            i = 0;
            for (WhereField whereField : getListWhere()) {
                if (whereField.isIsDefault()) {
                    tQuery = tQuery.concat((i == 0 ? "" : whereField.getConditional())).concat(whereField.getFeldName())
                            .concat(whereField.getOperator())
                            .concat(whereField.getFieldValue());
                } else {
                    tQuery = tQuery.concat((i == 0 ? "" : whereField.getConditional())).concat(whereField.getFeldName())
                            .concat(whereField.getOperator())
                            .concat("'" + whereField.getFieldValue() + "'");
                }
                i++;
            }
            
            if (getOrder() != null) {
                tQuery = tQuery.concat(getOrder().value());
            }
            
            if (getGroup()!= null) {
                tQuery = tQuery.concat(getGroup().value());
            }
        }
        
        return tQuery;
    }

    @Override
    public String toString() {
        return "QueryBuilder{" + "type = " + getQueryType() + ", "
                + super.toString()
                + '}';
    }
    
    public static void main(String[] args) {
        QueryBuilder builder = new QueryBuilder("PELANGGAN", QueryType.UPDATE);
        builder.addEntryValue("NAMA", "Amanda");
        builder.addEntryValue("ALAMAT", "Bandung");
        builder.addEntryValue("HP", "098909090", true);
        
        builder.addWhereValue(new WhereField("NAMA", "Amanda", Operator.EQUALS));
        builder.setOrder("NAMA","ALAMAT");
        builder.setGroup("NAMA");
        
        System.out.println(builder.getQueryValue());
    }
    
}
