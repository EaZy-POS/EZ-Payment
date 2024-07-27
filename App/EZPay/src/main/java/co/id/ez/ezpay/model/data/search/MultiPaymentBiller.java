/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.id.ez.ezpay.model.data.search;

import co.id.ez.ezpay.model.data.DataSearch;
import co.id.ez.database.DB;
import co.id.ez.ezpay.abstracts.AbstractModule;
import co.id.ez.ezpay.enums.util.InputType;
import co.id.ez.ezpay.util.swings.PopupMenu;
import co.id.ez.ezpay.interfaces.Model;
import com.json.JSONArray;
import com.json.JSONObject;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedList;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author Lutfi
 */
public class MultiPaymentBiller implements Model{

    private final String biller, billername;
    private final LinkedList<Input> inputList = new LinkedList<>();
    private final LinkedList<String> detailList = new LinkedList<>();

    public MultiPaymentBiller(String biller, String billername, JSONArray input, String[] detail) throws SQLException {
        this.biller = biller;
        this.billername = billername;
        for (int i = 0; i < input.length(); i++) {
            JSONObject tConfig = input.getJSONObject(i);
            inputList.add(new Input(tConfig));
        }
        
        detailList.addAll(Arrays.asList(detail));
    }

    public String getBiller() {
        return biller;
    }

    public String getBillername() {
        return billername;
    }
    
    @Override
    public String getKey() {
        return getBiller().concat(" - ").concat(getBillername());
    }

    @Override
    public String getValue() {
        return getBiller().concat(" - ").concat(getBillername());
    }

    @Override
    public String getInfo() {
        return getBillername();
    }

    public LinkedList<Input> getInputList() {
        return inputList;
    }

    public LinkedList<String> getDetailList() {
        return detailList;
    }

    @Override
    public String toString() {
        String val = "MPModel{" + "biller=" + biller + ", billername=" + billername 
                + ", inputList=["; 
        
        for (Input input : inputList) {
            val = val.concat(input.toString()) + ", ";
        }
        
        val = val + "], detailList=[";
        
        for (String detail : detailList) {
            val = val.concat(detail) + ", ";
        }
        val = val + "]}";
        return val;
    }

    public class Input{
        private final String name;
        private int length;
        private InputType type;
        private DataSearch<InputModel> dataHolderList;
        private PopupMenu popMenu;
        private InputModel model;
        private JTextField inputField;

        public Input(JSONObject input) throws SQLException {
            this.name = input.getString("label");
            contructInput(input.getJSONObject("input"));
        }
        
        private void contructInput(JSONObject inputtype) throws SQLException{
            dataHolderList = new DataSearch<>();
            if(inputtype.has("length")){
                length = Integer.parseInt(inputtype.get("length").toString());
            }else{
                length = 20;
            }
            
            if(inputtype.has("type")){
                switch (inputtype.get("type").toString()) {
                    case "numeric":
                        type = InputType.NUMERIC;
                        break;
                    case "option":
                        type = InputType.OPTION;
                        break;
                    case "queryoption":
                        type = InputType.QUERY_OPTION;
                        break;
                    default:
                        type = InputType.ALFANUMBERIC;
                        break;
                }
            }else{
                type = InputType.ALFANUMBERIC;
            }
            
            if(type == InputType.OPTION){
                Object tOption = inputtype.has("option") ? inputtype.get("option") : null;
                if(tOption != null){
                    if(tOption instanceof JSONArray){
                        readArrayConfig((JSONArray) tOption);
                    }
                }
            }
            
            if(type == InputType.QUERY_OPTION){
                Object tOption = inputtype.has("option") ? inputtype.get("option") : null;
                if(tOption != null){
                    if(tOption instanceof JSONObject){
                        JSONObject tConfigs = (JSONObject) tOption;
                        readQueryConfig(tConfigs);
                    }
                }
            }
        }
        
        private void readArrayConfig(JSONArray config) {
            for (int i = 0; i < config.length(); i++) {
                Object tInputValue = config.get(i);
                if (tInputValue != null) {
                    if (tInputValue instanceof JSONObject) {
                        JSONObject inputs = (JSONObject) tInputValue;
                        if (inputs.has("key") && inputs.has("value")) {
                            String key = inputs.get("key").toString();
                            String value = inputs.get("value").toString();
                            InputModel model = new InputModel(key, value);
                            dataHolderList.putData(model);
                        }
                    } else if (tInputValue instanceof JSONArray) {
                        readArrayConfig((JSONArray) tInputValue);
                    } else {
                        InputModel model = new InputModel(tInputValue.toString(), tInputValue.toString());
                        dataHolderList.putData(model);
                    }
                }
            }
        }
        
        private void readQueryConfig(JSONObject config) throws SQLException{
            if(config.has("query")){
                String key = config.has("key") ? config.get("key").toString() : null;
                String value = config.has("value") ? config.get("value").toString() : null;
                String query = config.getString("query");
                
                LinkedList<JSONObject> data = DB.executeQuery(AbstractModule.dbName, query);
                
                if(data != null && data.size() > 0){
                    
                    for (JSONObject objectData : data) {
                        if(key != null && value != null){
                            if(objectData.has(key) && objectData.has(value)){
                                InputModel model = new InputModel(objectData.get(key).toString(), objectData.get(value).toString());
                                dataHolderList.putData(model);
                            }
                        } else {

                            int index = 0;
                            String paramKey = null;
                            String paramValue = null;
                            for (Object param : objectData.keySet()) {

                                if (index > 1) {
                                    break;
                                }

                                if (index == 0) {
                                    paramKey = param.toString();
                                }

                                if (index == 1) {
                                    paramValue = param.toString();
                                }

                                index++;
                            }
                            
                            if (paramKey != null && paramValue != null) {
                                InputModel model = new InputModel(objectData.get(paramKey).toString(),
                                        objectData.get(paramValue).toString());
                                dataHolderList.putData(model);
                            }
                        }
                    }
                }
            }
        }

        public String getName() {
            return name;
        }

        public int getLength() {
            return length;
        }

        public InputType getType() {
            return type;
        }

        public DataSearch<InputModel> getDataHolderList() {
            return dataHolderList;
        }

        public void setInputHandler(final JLabel label, final JTextField inputHandler) {
            label.setText(getName());
            this.inputField = inputHandler;
            setInputHandlerType(this.inputField);
            label.setVisible(true);
            inputHandler.setVisible(true);
        }
        
        private void setInputHandlerType(JTextField textField){
            if (getType() == InputType.ALFANUMBERIC || getType() == InputType.NUMERIC) {
                textField.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyTyped(KeyEvent e) {
                        if (getType() == InputType.ALFANUMBERIC) {
                            if (textField.getText().trim().length() == getLength()
                                    || e.getKeyChar() == java.awt.event.KeyEvent.VK_BACK_SPACE
                                    || e.getKeyChar() == java.awt.event.KeyEvent.VK_DELETE) {
                                
                                if(textField.getText().trim().length() == getLength()){
                                    textField.getToolkit().beep();
                                }
                                
                                e.consume();
                            }
                        }else if (getType() == InputType.NUMERIC) {
                            char c = e.getKeyChar();
                            if (!((c >= '0') && (c <= '9')
                                    || (c == KeyEvent.VK_BACK_SPACE)
                                    || (c == KeyEvent.VK_DELETE))
                                    || textField.getText().trim().length() == getLength()) {
                                textField.getToolkit().beep();
                                e.consume();
                            }
                        }
                    } 
                });
            } else if (getType() == InputType.OPTION || getType() == InputType.QUERY_OPTION) {
                popMenu = new PopupMenu(textField);
                
                textField.addKeyListener(new KeyAdapter() {

                    @Override
                    public void keyPressed(KeyEvent evt) {
                        switch (evt.getKeyCode()) {
                            case KeyEvent.VK_UP:
                                popMenu.beforeSelection();
                                break;
                            case KeyEvent.VK_DOWN:
                                popMenu.afterSelection();
                                break; 
                            case KeyEvent.VK_ENTER:
                                model = (InputModel) popMenu.setSelectedValue();
                                loadValue(textField);
                                break;
                            default:
                                getData(textField);
                                break;
                        }
                    }
                });
                
                textField.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        getData(textField);
                    }
                });
                
                popMenu.setOnSelected(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        super.mouseClicked(e);
                        if (e.getClickCount() == 2) {
                            model = (InputModel) popMenu.setSelectedValue(popMenu.getSelectedIndex());
                            loadValue(textField);
                        }
                    }

                });
            }
        }
        
        private void getData(JTextField textField) {
            model = null;
            String tSearch = textField.getText().trim();
            if (tSearch.length() > 0) {
                popMenu.show(dataHolderList, tSearch);
            } else {
                popMenu.showAll(dataHolderList);
            }
        }
        
        private void loadValue(JTextField textField){
            if(model != null){
                textField.setText(model.getValue());
            }
        }
        
        public String getValue(){
            if (getType() == InputType.OPTION || getType() == InputType.QUERY_OPTION) {
                if (model != null) {
                    return model.getKey();
                }
            }else{
                return this.inputField.getText();
            }
            
            return null;
        }

        @Override
        public String toString() {
            return "Input{" + "name=" + name + ", length=" + length 
                    + ", type=" + type + ", dataHolderList=" + dataHolderList + '}';
        }
        
    }
}
