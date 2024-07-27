package co.id.ez.system.core.log.elm;

public interface Logger {
    static Logger forClass(Class<?> clazz) {
        return new LoggerMaper(clazz);
    }

    loggingType stream();
    loggingType error();
    loggingType warning();
    loggingType trace();
    loggingType db();
    loggingType dbError();
    loggingType config();
    loggingType query();
    loggingType deposit();
    loggingType temp(String name);
}
