package co.id.ez.system.core.log.elm;

import co.id.ez.system.core.etc.Utility;
import org.apache.logging.log4j.CloseableThreadContext;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.message.ParameterizedMessage;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

class LoggingTypeWriter implements loggingType {
    private final Class<?> clazz;
    private final Type type;
    private final String module;
    private Throwable cause;
    private Exception excep;
    private final Logger backend;
    private final Map<String, String> context;
    private String stacktraceString;

    LoggingTypeWriter(String module, Class<?> clazz, Type type, Logger backend) {
        this.clazz = clazz;
        this.type = type;
        this.module = module;

        context = new HashMap<>();
        context.put("type", type.getName());
        context.put("module", module);

        this.backend = backend;
    }

    @Override
    public loggingType withCause(Throwable t) {
        LoggingTypeWriter impl = new LoggingTypeWriter(module, clazz, type, backend);
        impl.cause = t;
        impl.stacktraceString = Utility.getStacktraceString(impl.cause);
        return impl;
    }

    @Override
    public void log(String message) {
        try(CloseableThreadContext.Instance ctc = CloseableThreadContext.putAll(context)) {
            if(cause == null) {
                backend.info(message);
            } else {
                backend.info(message, cause);
            }
        }
    }

    @Override
    public void log(String message, Object p1) {
        try(CloseableThreadContext.Instance ctc = CloseableThreadContext.putAll(context)) {
            if (cause == null) {
                backend.info(message, p1);
            } else {
                Message msg = new ParameterizedMessage(message, p1);
                backend.info(msg, cause);
            }
        }
    }

    @Override
    public void log(String message, Object p1, Object p2) {
        try(CloseableThreadContext.Instance ctc = CloseableThreadContext.putAll(context)) {
            if (cause == null) {
                backend.info(message, p1, p2);
            } else {
                Message msg = new ParameterizedMessage(message, p1, p2);
                backend.info(msg, cause);
            }
        }
    }

    @Override
    public void log(String message, Object p1, Object p2, Object p3) {
        try(CloseableThreadContext.Instance ctc = CloseableThreadContext.putAll(context)) {
            if (cause == null) {
                backend.info(message, p1, p2, p3);
            } else {
                Message msg = new ParameterizedMessage(message, p1, p2, p3);
                backend.info(msg, cause);
            }
        }
    }

    @Override
    public void log(String message, Object p1, Object p2, Object p3, Object p4) {
        try(CloseableThreadContext.Instance ctc = CloseableThreadContext.putAll(context)) {
            if (cause == null) {
                backend.info(message, p1, p2, p3, p4);
            } else {
                Message msg = new ParameterizedMessage(message, p1, p2, p3, p4);
                backend.info(msg, cause);
            }
        }
    }

    @Override
    public void log(String message, Object p1, Object p2, Object p3, Object p4, Object p5) {
        try(CloseableThreadContext.Instance ctc = CloseableThreadContext.putAll(context)) {
            if (cause == null) {
                backend.info(message, p1, p2, p3, p4, p5);
            } else {
                Message msg = new ParameterizedMessage(message, p1, p2, p3, p4, p5);
                backend.info(msg, cause);
            }
        }
    }

    @Override
    public void log(String message, Object... params) {
        try(CloseableThreadContext.Instance ctc = CloseableThreadContext.putAll(context)) {
            if (cause == null) {
                backend.info(message, params);
            } else {
                Message msg = new ParameterizedMessage(message, params);
                backend.info(msg, cause);
            }
        }
    }

    @Override
    public void log(String message, Supplier<?> paramSupplier) {
        log(message, paramSupplier.get());
    }

    @Override
    public void log(String message, Supplier<?>... paramSuppliers) {
        Object[] params = new Object[paramSuppliers.length];
        for (int i = 0; i < paramSuppliers.length; i++) {
            params[i] = paramSuppliers[i].get();
        }
        log(message, params);
    }

    @Override
    public void log(Object message) {
        try(CloseableThreadContext.Instance ctc = CloseableThreadContext.putAll(context)) {
            if (cause == null) {
                backend.info(message);
            } else {
                backend.info(message, cause);
            }
        }
    }

    @Override
    public void log(Supplier<?> messageSupplier) {
        log(messageSupplier.get());
    }

    @Override
    public void log(String message, boolean isUseStacktraceString) {
        if (isUseStacktraceString && stacktraceString != null) {
            try (CloseableThreadContext.Instance ctc = CloseableThreadContext.putAll(context)) {
                backend.info(message + stacktraceString);
            }
        } else {
            log(message);
        }
    }

    @Override
    public void log(String message, boolean isUseStacktraceString, Object... params) {
        if (isUseStacktraceString && stacktraceString != null) {
            try (CloseableThreadContext.Instance ctc = CloseableThreadContext.putAll(context)) {
                backend.info(message + stacktraceString, params);
            }
        } else {
            log(message);
        }
    }

    @Override
    public loggingType withCause(Exception t) {
        LoggingTypeWriter impl = new LoggingTypeWriter(module, clazz, type, backend);
        impl.excep = t;
        impl.stacktraceString = Utility.getStacktraceString(impl.excep);
        return impl;
    }
}
