package co.id.ez.system.core.ex;

import co.id.ez.system.core.log.elm.Logger;
import co.id.ez.system.core.rc.RC;

public class ServiceException extends RuntimeException {
    private RC code;
    private String message;
    private Throwable cause;

    public ServiceException(RC code, String message) {
        this(code, message, null);
    }

    public ServiceException(RC code, String message, Throwable cause) {
        this.code = code;
        this.message = message;
        this.cause = cause;
    }

    public RC getRC() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Throwable getCause() {
        return cause;
    }

    public void logWith(Logger logger) {
        String logFormat = "Response Code: {} (), Message: {}";
        logger.error().withCause(this).log(logFormat, code.name(), code.getResponseCodeString(), message);
    }
}
