package co.id.ez.system.core.log.elm;

import java.util.function.Supplier;

public interface loggingType {
    loggingType withCause(Throwable t);
    loggingType withCause(Exception t);
    void log(String message);
    void log(String message, Object p1);
    void log(String message, Object p1, Object p2);
    void log(String message, Object p1, Object p2, Object p3);
    void log(String message, Object p1, Object p2, Object p3, Object p4);
    void log(String message, Object p1, Object p2, Object p3, Object p4, Object p5);
    void log(String message, Object... params);
    void log(String message, Supplier<?> paramSupplier);
    void log(String message, Supplier<?>... paramSuppliers);
    void log(Object message);
    void log(Supplier<?> messageSupplier);
    void log(String message, boolean isUseStacktraceString);
    void log(String message, boolean isUseStacktraceString, Object... params);
}
