package co.id.ez.database.core;

import co.id.ez.database.core.function.JdbcConsumer;
import co.id.ez.database.core.function.JdbcFunction;
import co.id.ez.database.core.query.CallQuery;
import co.id.ez.database.core.query.SelectQuery;
import co.id.ez.database.core.query.UpdateQuery;
import java.sql.Connection;
import java.sql.SQLException;

public class Handle implements AutoCloseable, ConnectionWrapper {
    private final Connection connection;

    Handle(Connection connection) {
        this.connection = connection;
    }

    public Connection getConnection() {
        return connection;
    }

    public void beginTransaction() throws SQLException {
        this.connection.setAutoCommit(false);
    }

    public void commitTransaction() throws SQLException {
        this.connection.commit();
        this.connection.setAutoCommit(true);
    }

    public void rollbackTransaction() throws SQLException {
        this.connection.rollback();
        this.connection.setAutoCommit(true);
    }

    @Override
    public void close() throws SQLException {
        this.connection.close();
    }

    public <T> T inTransaction(JdbcFunction<Handle, T> f) throws SQLException {
        try {
            beginTransaction();
            T result = f.applyWithException(this);
            commitTransaction();

            return result;
        } catch (SQLException e) {
            rollbackTransaction();
            throw  e;
        }
    }

    public void useTransaction(JdbcConsumer<Handle> c) throws SQLException {
        try {
            beginTransaction();
            c.consume(this);
            commitTransaction();
        } catch (SQLException e) {
            rollbackTransaction();
            throw  e;
        }
    }

    public UpdateQuery createUpdate(String sql) {
        return new UpdateQuery(this, sql);
    }

    public SelectQuery createSelect(String sql) {
        return new SelectQuery(this, sql);
    }

    public CallQuery createCall(String sql) {
        return new CallQuery(this, sql);
    }
}
