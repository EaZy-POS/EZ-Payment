package co.id.ez.database.core.query;


import co.id.ez.database.core.ConnectionWrapper;
import co.id.ez.database.core.function.JdbcBiFunction;
import co.id.ez.database.core.mapper.CallQueryResultMapper;
import java.sql.CallableStatement;
import java.sql.ResultSet;

public class CallQuery extends Query<CallQuery, CallableStatement> {

    public CallQuery(ConnectionWrapper connectionWrapper, String sql) {
        super(CallQuery.class, connectionWrapper, sql);
    }

    public CallQuery registerOutParameter(int position, int sqlType) {
        paramsFunctions.add(callableStatement -> {
            callableStatement.registerOutParameter(position, sqlType);
            return null;
        });
        return this;
    }

    public CallQuery registerOutParameter(int position, int sqlType, int scale) {
        paramsFunctions.add(callableStatement -> {
            callableStatement.registerOutParameter(position, sqlType, scale);
            return null;
        });
        return this;
    }

    public CallQuery registerOutParameter(String parameterName, int sqlType) {
        paramsFunctions.add(callableStatement -> {
            callableStatement.registerOutParameter(parameterName, sqlType);
            return null;
        });
        return this;
    }

    public CallQuery registerOutParameter(String parameterName, int sqlType, int scale) {
        paramsFunctions.add(callableStatement -> {
            callableStatement.registerOutParameter(parameterName, sqlType, scale);
            return null;
        });
        return this;
    }

    public CallQuery registerOutParameter(String parameterName, int sqlType, String typeName) {
        paramsFunctions.add(callableStatement -> {
            callableStatement.registerOutParameter(parameterName, sqlType, typeName);
            return null;
        });
        return this;
    }

    public <T> CallQueryResultMapper<T> map(JdbcBiFunction<ResultSet, CallQueryResultMapper.Out, T> mapper) {
        return new CallQueryResultMapper<>(connectionWrapper, sql, paramsFunctions, mapper);
    }
}
