package co.id.ez.database.core.function;

import java.sql.Connection;
import java.sql.SQLException;

@FunctionalInterface
public interface ConnectionFactory {
    Connection openConnection() throws SQLException;

    default void closeConnection(Connection conn) throws SQLException {
        conn.close();
    }
}
