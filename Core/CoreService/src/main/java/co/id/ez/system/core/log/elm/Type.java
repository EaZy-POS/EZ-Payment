package co.id.ez.system.core.log.elm;

import java.util.HashMap;
import java.util.Map;

class Type {

    private static final Map<String, Type> types = new HashMap<>();

    public static final Type STREAM = forName("STREAM");
    public static final Type ERROR = forName("ERROR");
    public static final Type WARNING = forName("WARNING");
    public static final Type TRACE = forName("TRACE");
    public static final Type DB = forName("DB");
    public static final Type DB_ERROR = forName("DB_ERROR");
    public static final Type CONFIG = forName("CONFIG");
    public static final Type QUERY = forName("QUERY");
    public static final Type DEPOSIT = forName("DEPOSIT");

    public static Type forName(String name) {
        String key = name.toLowerCase();
        if(!types.containsKey(key)) {
            types.put(key, new Type(key));
        }

        return types.get(key);
    }

    private final String name;

    private Type(String name) {
        this.name = name.toLowerCase();
    }

    String getName() {
        return name;
    }
}
