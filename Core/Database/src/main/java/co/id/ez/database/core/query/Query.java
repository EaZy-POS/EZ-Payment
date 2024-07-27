package co.id.ez.database.core.query;

import co.id.ez.database.core.ConnectionWrapper;
import co.id.ez.database.core.function.JdbcFunction;
import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class Query<S extends Query<?,?>, T extends PreparedStatement> {
    protected final S self;
    protected ConnectionWrapper connectionWrapper;
    protected String sql;
    protected List<JdbcFunction<T, Object>> paramsFunctions;

    protected Query(final Class<S> selfClass, ConnectionWrapper connectionWrapper, String sql) {
        self = selfClass.cast(this);
        this.connectionWrapper = connectionWrapper;
        this.sql = sql;
        this.paramsFunctions = new ArrayList<>();
    }

    public S bind(int i, BigDecimal value) {
        paramsFunctions.add(statement -> {
            statement.setBigDecimal(i, value);
            return value;
        });

        return self;
    }

    public S bind(int i, Blob value) {
        paramsFunctions.add(statement -> {
            statement.setBlob(i, value);
            return value;
        });

        return self;
    }

    public S bind(int i, boolean value){
        paramsFunctions.add(statement -> {
            statement.setBoolean(i, value);
            return value;
        });

        return self;
    }

    public S bind(int i, byte value){
        paramsFunctions.add(statement -> {
            statement.setByte(i, value);
            return value;
        });

        return self;
    }

    public S bind(int i, byte[] value){
        paramsFunctions.add(statement -> {
            statement.setBytes(i, value);
            return value;
        });

        return self;
    }

    public S bind(int i, char value){
        paramsFunctions.add(statement -> {
            statement.setString(i, String.valueOf(value));
            return value;
        });

        return self;
    }

    public S bind(int i, Clob value){
        paramsFunctions.add(statement -> {
            statement.setClob(i, value);
            return value;
        });

        return self;
    }

    public S bind(int i, double value){
        paramsFunctions.add(statement -> {
            statement.setDouble(i, value);
            return value;
        });

        return self;
    }

    public S bind(int i, float value){
        paramsFunctions.add(statement -> {
            statement.setFloat(i, value);
            return value;
        });

        return self;
    }

    public S bind(int i, int value){
        paramsFunctions.add(statement -> {
            statement.setInt(i, value);
            return value;
        });

        return self;
    }

    public S bind(int i, long value){
        paramsFunctions.add(statement -> {
            statement.setLong(i, value);
            return value;
        });

        return self;
    }

    public S bind(int i, Object value){
        paramsFunctions.add(statement -> {
            statement.setObject(i, value);
            return value;
        });

        return self;
    }

    public S bind(int i, short value){
        paramsFunctions.add(statement -> {
            statement.setShort(i, value);
            return value;
        });

        return self;
    }

    public S bind(int i, String value){
        paramsFunctions.add(statement -> {
            statement.setString(i, value);
            return value;
        });

        return self;
    }

    public S bind(int i, Date value){
        paramsFunctions.add(statement -> {
            statement.setDate(i, value);
            return value;
        });

        return self;
    }

    public S bind(int i, Time value){
        paramsFunctions.add(statement -> {
            statement.setTime(i, value);
            return value;
        });

        return self;
    }

    public S bind(int i, Timestamp value){
        paramsFunctions.add(statement -> {
            statement.setTimestamp(i, value);
            return value;
        });

        return self;
    }
}
