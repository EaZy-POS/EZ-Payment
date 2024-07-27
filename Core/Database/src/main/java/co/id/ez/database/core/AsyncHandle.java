package co.id.ez.database.core;

import co.id.ez.database.core.async.AsyncQuery;
import co.id.ez.database.core.async.AsyncUpdateQuery;
import java.sql.Connection;

public class AsyncHandle implements ConnectionWrapper {
    private final Connection connection;
    AsyncHandle(Connection connection) {
        this.connection = connection;
    }

    public AsyncUpdateQuery createUpdate(String sql) {
        return new AsyncUpdateQuery(this, sql);
    }

    @Override
    public Connection getConnection() {
        return null;
    }

    public void enqueue(AsyncQuery query) {

    }
}
