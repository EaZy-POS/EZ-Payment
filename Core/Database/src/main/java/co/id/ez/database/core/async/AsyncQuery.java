package co.id.ez.database.core.async;

import co.id.ez.database.core.AsyncHandle;
import co.id.ez.database.core.query.Query;
import java.sql.PreparedStatement;

abstract public class AsyncQuery<S extends AsyncQuery<?,?>, T extends PreparedStatement> extends Query<S, T> {
    protected AsyncHandle handle;

    protected AsyncQuery(Class<S> selfClass, AsyncHandle handle, String sql) {
        super(selfClass, handle, sql);
        this.handle = handle;
    }

    abstract void execute();
}
