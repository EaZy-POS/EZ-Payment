package co.id.ez.database.core.query;

import co.id.ez.database.core.ConnectionWrapper;
import co.id.ez.database.core.function.JdbcFunction;
import co.id.ez.database.core.mapper.SelectQueryResultMapper;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class SelectQuery extends Query<SelectQuery, PreparedStatement> {
    public SelectQuery(ConnectionWrapper connectionWrapper, String sql) {
        super(SelectQuery.class, connectionWrapper, sql);
    }

    public <T> SelectQueryResultMapper<T> map(JdbcFunction<ResultSet, T> mapper) {
        return new SelectQueryResultMapper<>(connectionWrapper, sql, paramsFunctions, mapper);
    }
}
