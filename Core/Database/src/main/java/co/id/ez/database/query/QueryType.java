/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.database.query;

/**
 *
 * @author RCS
 */
public enum QueryType {
    SELECT("SELECT "),
    INSERT("INSERT INTO "), 
    UPDATE("UPDATE "), 
    DELETE("DELETE FROM ");
    
    private final String command;
    
    private QueryType(String pComand){
        this.command = pComand;
    }

    public String getCommand() {
        return command;
    }
    
}
