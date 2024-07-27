package co.id.ez.database.core.function;

import java.sql.SQLException;
import java.util.function.Function;

@FunctionalInterface
public interface JdbcFunction<T, R> extends Function<T, R> {
    R applyWithException(T input) throws SQLException;
    default R apply(T input) {
        try {
            return applyWithException(input);
        } catch (SQLException e) {
            return null;
        }
    }
}
