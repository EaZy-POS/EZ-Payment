package co.id.ez.database.core.async;

import co.id.ez.database.core.AsyncHandle;
import co.id.ez.database.core.function.JdbcFunction;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AsyncUpdateQuery extends AsyncQuery<AsyncUpdateQuery, PreparedStatement> {
    public AsyncUpdateQuery(AsyncHandle handle, String sql) {
        super(AsyncUpdateQuery.class, handle, sql);
    }

    public void enqueue() {
        handle.enqueue(this);
    }

    @Override
    void execute() {
        Connection conn = handle.getConnection();
        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            for(JdbcFunction<PreparedStatement, Object> f: paramsFunctions) {
                f.applyWithException(stmt);
            }
            stmt.executeUpdate();
        } catch (SQLException e) {
            // Silent error
            // TODO: log error
        }
    }
}
