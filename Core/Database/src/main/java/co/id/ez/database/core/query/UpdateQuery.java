package co.id.ez.database.core.query;

import co.id.ez.database.core.ConnectionWrapper;
import co.id.ez.database.core.function.JdbcFunction;
import co.id.ez.system.core.log.LogService;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class UpdateQuery extends Query<UpdateQuery, PreparedStatement> {
    private static final String separator = ", ";

    public UpdateQuery(ConnectionWrapper connectionWrapper, String sql) {
        super(UpdateQuery.class, connectionWrapper, sql);
    }

    public int execute() throws SQLException {
        Connection conn = connectionWrapper.getConnection();
        List<String> params = new ArrayList<>();
        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            for(JdbcFunction<PreparedStatement, Object> f: paramsFunctions) {
                Object p = f.applyWithException(stmt);
                params.add(p != null? p.toString(): "null");
            }
            LogService.getInstance(this).query().log(
                    "Before executing query: {}, with params: [{}]", 
                    () -> sql, 
                    () -> params.stream().collect(Collectors.joining(separator)));
            int count = stmt.executeUpdate();
            LogService.getInstance(this).query().log(
                    "After executing query: {}, with params: [{}], affected rows count: {}", 
                    () -> sql, 
                    () -> params.stream().collect(Collectors.joining(separator)), () -> count);

            return count;
        } catch (SQLException e) {
            throw  e;
        }
    }
}
