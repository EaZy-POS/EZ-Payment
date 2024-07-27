package co.id.ez.database.core.mapper;

import co.id.ez.database.core.ConnectionWrapper;
import co.id.ez.database.core.function.JdbcFunction;
import co.id.ez.system.core.log.LogService;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class SelectQueryResultMapper<T> implements QueryResultMapper<T> {
    private static final String separator = ", ";

    private final ConnectionWrapper connectionWrapper;
    private final List<JdbcFunction<PreparedStatement, Object>> paramsFunctions;
    private final JdbcFunction<ResultSet, T> mapper;
    private final String sql;

    public SelectQueryResultMapper(ConnectionWrapper connectionWrapper, String sql, List<JdbcFunction<PreparedStatement, Object>> paramsConsumer, JdbcFunction<ResultSet, T> mapper) {
        this.connectionWrapper = connectionWrapper;
        this.sql = sql;
        this.paramsFunctions = paramsConsumer;
        this.mapper = mapper;
    }

    public Optional<T> one() throws SQLException {
        Optional<T> result = Optional.empty();
        Connection conn = connectionWrapper.getConnection();
        List<String> params = new ArrayList<>();

        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            for(JdbcFunction<PreparedStatement, Object> f: paramsFunctions) {
                Object p = f.applyWithException(stmt);
                params.add(p != null? p.toString(): "null");
            }

            LogService.getInstance(this).query().log("Before executing query: {}, with params: [{}]", 
                    () -> sql, 
                    () -> params.stream().collect(Collectors.joining(separator)));

            try(ResultSet rs = stmt.executeQuery()) {
                if(rs.next()) {
                    result = Optional.of(mapper.applyWithException(rs));
                }
            }

            Optional<T> finalResult = result;
            LogService.getInstance(this).query().log("After executing query: {}, with params: [{}], result: {}", 
                    () -> sql, 
                    () -> params.stream().collect(Collectors.joining(separator)), 
                    () -> finalResult.map(r -> r.toString()));

            return result;
        } catch (SQLException e) {
            throw  e;
        }
    }

    public List<T> list() throws SQLException {
        List<T> result = new ArrayList<>();
        Connection conn = connectionWrapper.getConnection();
        List<String> params = new ArrayList<>();

        try(PreparedStatement stmt = conn.prepareStatement(sql)) {
            for(JdbcFunction<PreparedStatement, Object> f: paramsFunctions) {
                Object p = f.applyWithException(stmt);
                params.add(p != null? p.toString(): "null");
            }

            LogService.getInstance(this).query().log("Before executing query: {}, with params: [{}]", 
                    () -> sql, 
                    () -> params.stream().collect(Collectors.joining(separator)));

            try(ResultSet rs = stmt.executeQuery()) {
                while(rs.next()) {
                    result.add(mapper.apply(rs));
                }
            }

            LogService.getInstance(this).query().log("After executing query: {}, with params: [{}], result: {}", 
                    () -> sql, 
                    () -> params.stream().collect(Collectors.joining("")), 
                    () -> result.stream().map(r -> r.toString()).collect(Collectors.joining(separator)));

            return result;
        } catch (SQLException e) {
            throw  e;
        }
    }
}
