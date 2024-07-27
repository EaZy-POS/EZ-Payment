package co.id.ez.database.core.mapper;

import co.id.ez.database.core.ConnectionWrapper;
import co.id.ez.database.core.function.JdbcBiFunction;
import co.id.ez.database.core.function.JdbcFunction;
import co.id.ez.system.core.log.LogService;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CallQueryResultMapper<T> implements QueryResultMapper<T> {
    private static final String cSeparator = ", ";

    private final ConnectionWrapper connectionWrapper;
    private final List<JdbcFunction<CallableStatement, Object>> paramsFunctions;
    private final JdbcBiFunction<ResultSet, Out, T> mapper;
    private final String sql;

    public CallQueryResultMapper(
            ConnectionWrapper connectionWrapper, 
            String sql, 
            List<JdbcFunction<CallableStatement, Object>> paramsConsumer, 
            JdbcBiFunction<ResultSet, Out, T> mapper) 
    {
        this.connectionWrapper = connectionWrapper;
        this.sql = sql;
        this.paramsFunctions = paramsConsumer;
        this.mapper = mapper;
    }

    public Optional<T> one() throws SQLException {
        Optional<T> result ;
        Connection conn = connectionWrapper.getConnection();
        List<String> params = new ArrayList<>();
        try(CallableStatement stmt = conn.prepareCall(sql)) {
            for(JdbcFunction<CallableStatement, Object> f: paramsFunctions) {
                Object p = f.applyWithException(stmt);
                params.add(p != null? p.toString(): "null");
            }

            LogService.getInstance(this).query().log("Before executing query: {}, with params: [{}]", 
                    () -> sql, 
                    () -> params.stream().collect(Collectors.joining(cSeparator)));
            boolean hasResultSet = stmt.execute();
            Out out = new Out(stmt);
            try(ResultSet rs = stmt.getResultSet()) {
                if(hasResultSet && rs.next()) {
                    result = Optional.of(mapper.applyWithException(rs, out));
                } else {
                    result = Optional.of(mapper.applyWithException(null, out));
                }
            }

            Optional<T> finalResult = result;
            LogService.getInstance(this).query().log("After executing query: {}, with params: [{}], result: {}", 
                    () -> sql, 
                    () -> params.stream().collect(Collectors.joining(cSeparator)), 
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
        try(CallableStatement stmt = conn.prepareCall(sql)) {
            for(JdbcFunction<CallableStatement, Object> f: paramsFunctions) {
                Object p = f.applyWithException(stmt);
                params.add(p != null? p.toString(): "null");
            }
            LogService.getInstance(this).query().log("Before executing query: {}, with params: [{}]", 
                    () -> sql, 
                    () -> params.stream().collect(Collectors.joining(cSeparator)));

            boolean hasResultSet = stmt.execute();
            Out out = new Out(stmt);
            if(hasResultSet) {
                ResultSet rs = stmt.getResultSet();
                while(rs.next()) {
                    result.add(mapper.applyWithException(rs, out));
                }
            } else {
                result.add(mapper.apply(null, out));
            }
            LogService.getInstance(this).query().log("After executing query: {}, with params: [{}], result: {}", 
                    () -> sql, () -> params.stream().collect(Collectors.joining("")), 
                    () -> result.stream().map(r -> r.toString()).collect(Collectors.joining(cSeparator)));

            return result;
        } catch (SQLException e) {
            throw  e;
        }
    }

    public static class Out {
        private CallableStatement statement;

        public Out(CallableStatement statement) {
            this.statement = statement;
        }

        public byte[] getBytes(int position) throws SQLException {
            return statement.getBytes(position);
        }

        public byte[] getBytes(String name) throws SQLException {
            return statement.getBytes(name);
        }

        public Date getDate(int position) throws SQLException {
            return statement.getDate(position);
        }

        public Date getDate(String name) throws SQLException {
            return statement.getDate(name);
        }

        public double getDouble(int position) throws SQLException {
            return statement.getDouble(position);
        }

        public double getDouble(String name) throws SQLException {
            return statement.getDouble(name);
        }

        public float getFloat(int position) throws SQLException {
            return statement.getFloat(position);
        }

        public float getFloat(String name) throws SQLException {
            return statement.getFloat(name);
        }

        public int getInt(int position) throws SQLException {
            return statement.getInt(position);
        }

        public int getInt(String name) throws SQLException {
            return statement.getInt(name);
        }

        public long getLong(int position) throws SQLException {
            return statement.getLong(position);
        }

        public long getLong(String name) throws SQLException {
            return statement.getLong(name);
        }

        public Object getObject(int position) throws SQLException {
            return statement.getObject(position);
        }

        public Object getObject(String name) throws SQLException {
            return statement.getObject(name);
        }

        public short getShort(int position)  throws SQLException {
            return statement.getShort(position);
        }

        public short getShort(String name) throws SQLException {
            return statement.getShort(name);
        }

        public String getString(int position) throws SQLException {
            return statement.getString(position);
        }

        public String getString(String name) throws SQLException {
            return statement.getString(name);
        }

        public Timestamp getTimestamp(int position) throws SQLException {
            return statement.getTimestamp(position);
        }

        public Timestamp getTimestamp(String name) throws SQLException {
            return statement.getTimestamp(name);
        }
    }
}
