package co.id.ez.database.core;

import co.id.ez.database.core.function.ConnectionFactory;
import co.id.ez.database.core.function.JdbcConsumer;
import co.id.ez.database.core.function.JdbcFunction;
import java.sql.Connection;
import javax.sql.DataSource;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Jdbc {
    private final ConnectionFactory factory;

    private Jdbc(ConnectionFactory factory) {
        this.factory = factory;
    }

    public static Jdbc create(DataSource dataSource) {
        return new Jdbc(dataSource::getConnection);
    }
    
    public Connection getConnection() throws SQLException {
        return factory.openConnection();
    }

    public static Jdbc create(String url) {
        return new Jdbc(() -> DriverManager.getConnection(url));
    }

    public static Jdbc create(String url, String username, String password) {
        return new Jdbc(() -> DriverManager.getConnection(url, username, password));
    }

    public Handle open() throws SQLException {
        return new Handle(factory.openConnection());
    }

    public <T> T withHandle(JdbcFunction<Handle, T> f) throws SQLException {
        try(Handle handle = open()) {
            return f.applyWithException(handle);
        }
    }

    public <T> T withTransaction(JdbcFunction<Handle, T> f) throws SQLException {
        return withHandle(handle -> handle.inTransaction(f));
    }

    public <T> void useHandle(JdbcConsumer<Handle> c) throws SQLException {
        try(Handle handle = open()) {
            c.consume(handle);
        }
    }

    public <T> void useTransaction(JdbcConsumer<Handle> c) throws SQLException {
        useHandle(handle -> handle.useTransaction(c));
    }
}