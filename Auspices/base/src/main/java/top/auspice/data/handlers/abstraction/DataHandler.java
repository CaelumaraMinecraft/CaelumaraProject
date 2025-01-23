package top.auspice.data.handlers.abstraction;

import org.jetbrains.annotations.NotNull;
import top.auspice.data.database.dataprovider.SQLDataHandlerProperties;
import top.auspice.data.database.dataprovider.SectionableDataSetter;

import java.util.Objects;

public abstract class DataHandler<T> {
    private final @NotNull SQLDataHandlerProperties sqlProperties;

    public DataHandler(@NotNull SQLDataHandlerProperties sqlProperties) {
        Objects.requireNonNull(sqlProperties, "sqlProperties");
        this.sqlProperties = sqlProperties;
    }

    public final @NotNull SQLDataHandlerProperties getSqlProperties() {
        return this.sqlProperties;
    }

    public abstract void save(@NotNull SectionableDataSetter dataSetter, T object);
}
