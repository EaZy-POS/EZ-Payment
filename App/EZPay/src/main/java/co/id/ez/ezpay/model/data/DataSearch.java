package co.id.ez.ezpay.model.data;

import co.id.ez.ezpay.interfaces.Model;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

public class DataSearch<T extends Model> {
    
    private final LinkedHashMap<String, T> dataHolder = new LinkedHashMap();

    public LinkedHashMap<String, T> getDataHolder() {
        return dataHolder;
    }
    
    public void putData(T data){
        dataHolder.put(data.getKey(), data);
    }
    
    public T getData(String key){
        return dataHolder.get(key);
    }
    
    public void clear(){
        dataHolder.clear();
    }
    
    protected boolean containsKey(String keySearch, String key) {
        keySearch = keySearch.toLowerCase().replaceAll("[^a-zA-Z0-9#]", "").trim();
        key = key.toLowerCase().replaceAll("[^a-zA-Z0-9#]", "").trim();
        
        String[] arraySearch = keySearch.split("#");
        
        boolean isExsist = key.contains(keySearch);
        int matchingValue = 0;
        
        for (String string : arraySearch) {
            if(key.contains(string)){
                matchingValue++;
            }
        }
        
        return isExsist || matchingValue > 1;
    }
    
    public List<T> getList(String searchKey){
        List<T> list = new ArrayList<>();
        dataHolder.keySet().stream().filter(key -> 
                (containsKey(searchKey, key))).forEachOrdered(key -> {
            list.add(dataHolder.get(key));
        });
        
        return list;
    }
    
    public LinkedList<T> toList(){
        LinkedList<T> list = new LinkedList<>();
        dataHolder.values().forEach(value -> {
            list.add(value);
        });
        
        return list;
    }

    @Override
    public String toString() {
        return "DataSearch{" + "dataHolder=" + dataHolder + '}';
    }

}
