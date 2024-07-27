package co.id.ez.system.core.log.elm;

import java.security.CodeSource;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

class LoggerMaper implements Logger {
    private static final Map<Class<?>, Map<Type, loggingType>> loggerForTypeMap = new HashMap<>();
    private final Class<?> clazz;
    private final String module;
    private final org.apache.logging.log4j.Logger backend;

    private static loggingType getLoggingType(String module, Class<?> clazz, Type type, org.apache.logging.log4j.Logger backend) {
        if(!loggerForTypeMap.containsKey(clazz)) {
            loggerForTypeMap.put(clazz, new HashMap<>());
        }

        Map<Type, loggingType> typeMap = loggerForTypeMap.get(clazz);
        if(!typeMap.containsKey(type)) {
            typeMap.put(type, new LoggingTypeWriter(module, clazz, type, backend));
        }

        return typeMap.get(type);
    }

    LoggerMaper(Class<?> clazz) {
        this.clazz = clazz;
        module = getModule(clazz);
        backend = org.apache.logging.log4j.LogManager.getLogger(clazz);
    }

    private String getModule(Class<?> clazz) {
        String logFolder;
        CodeSource source = clazz.getProtectionDomain().getCodeSource();
        if(source != null) {
            String[] location = source.getLocation().getFile().split("/|\\\\");
            if(location.length > 0) {
                String jarname = location[location.length - 1];
                if(jarname.contains("classes")) {
                    logFolder = "instance";
                } else {
                    String[] names = jarname.split("-[\\d]");
                    logFolder = names[0];
                }
            } else {
                logFolder = "instance";
            }
        } else {
            logFolder = "jre";
        }

        return logFolder;
    }

    @Override
    public loggingType stream() {
        if(LoggingFactory.isEnabled(module, Type.STREAM)) {
            return getLoggingType(module, clazz, Type.STREAM, backend);
        }
        return NO_OP;
    }

    @Override
    public loggingType error() {
        if(LoggingFactory.isEnabled(module, Type.ERROR)) {
            return getLoggingType(module, clazz, Type.ERROR, backend);
        }
        return NO_OP;
    }

    @Override
    public loggingType warning() {
        if(LoggingFactory.isEnabled(module, Type.WARNING)) {
            return getLoggingType(module, clazz, Type.WARNING, backend);
        }
        return NO_OP;
    }

    @Override
    public loggingType trace() {
        if(LoggingFactory.isEnabled(module, Type.TRACE)) {
            return getLoggingType(module, clazz, Type.TRACE, backend);
        }
        return NO_OP;
    }

    @Override
    public loggingType db() {
        if(LoggingFactory.isEnabled(module, Type.DB)) {
            return getLoggingType(module, clazz, Type.DB, backend);
        }
        return NO_OP;
    }

    @Override
    public loggingType dbError() {
        if(LoggingFactory.isEnabled(module, Type.DB_ERROR)) {
            return getLoggingType(module, clazz, Type.DB_ERROR, backend);
        }
        return NO_OP;
    }

    @Override
    public loggingType config() {
        if(LoggingFactory.isEnabled(module, Type.CONFIG)) {
            return getLoggingType(module, clazz, Type.CONFIG, backend);
        }
        return NO_OP;
    }

    @Override
    public loggingType query() {
        if(LoggingFactory.isEnabled(module, Type.QUERY)) {
            return getLoggingType(module, clazz, Type.QUERY, backend);
        }
        return NO_OP;
    }

    @Override
    public loggingType deposit() {
        if(LoggingFactory.isEnabled(module, Type.DEPOSIT)) {
            return getLoggingType(module, clazz, Type.DEPOSIT, backend);
        }
        return NO_OP;
    }

    @Override
    public loggingType temp(String name) {
        Type temp = Type.forName(name);
        if(LoggingFactory.isEnabled(module, temp, true)) {
            return getLoggingType(module, clazz, temp, backend);
        }
        return NO_OP;
    }



    private static final NoOp NO_OP = new NoOp();

    private static class NoOp implements loggingType {

        @Override
        public loggingType withCause(Throwable t) {
            return this;
        }

        @Override
        public void log(String message) {

        }

        @Override
        public void log(String message, Object p1) {

        }

        @Override
        public void log(String message, Object p1, Object p2) {

        }

        @Override
        public void log(String message, Object p1, Object p2, Object p3) {

        }

        @Override
        public void log(String message, Object p1, Object p2, Object p3, Object p4) {

        }

        @Override
        public void log(String message, Object p1, Object p2, Object p3, Object p4, Object p5) {

        }

        @Override
        public void log(String message, Object... params) {

        }

        @Override
        public void log(String message, Supplier<?> paramSupplier) {

        }

        @Override
        public void log(String message, Supplier<?>... paramSuppliers) {

        }

        @Override
        public void log(Object message) {

        }

        @Override
        public void log(Supplier<?> messageSupplier) {

        }

        @Override
        public void log(String message, boolean isUseStacktraceString) {
            
        }

        @Override
        public void log(String message, boolean isUseStacktraceString, Object... params) {
            
        }

        @Override
        public loggingType withCause(Exception t) {
            return this;
        }
    }
}
