package co.id.ez.database.core.function;

import java.sql.SQLException;
import java.util.function.Consumer;

@FunctionalInterface
public interface JdbcConsumer<T> extends Consumer<T> {
    default void accept(T t) {
        try {
            consume(t);
        } catch (SQLException ignored) {

        }
    }
    void consume(T t) throws SQLException;
}
