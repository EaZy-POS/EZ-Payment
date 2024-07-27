package co.id.ez.database.core.function;

import java.sql.SQLException;
import java.util.function.BiFunction;

public interface JdbcBiFunction<T, U, R> extends BiFunction<T, U, R> {
    R applyWithException(T input1, U input2) throws SQLException;

    default R apply(T input1, U input2) {
        try {
            return applyWithException(input1, input2);
        } catch (SQLException e) {
            return null;
        }
    }
}
