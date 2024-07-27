package co.id.ez.database.core.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface QueryResultMapper<T> {

    Optional<T> one() throws SQLException;

    List<T> list() throws SQLException;


}
