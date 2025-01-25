package net.aurika.data.handlers.abstraction;

import org.jetbrains.annotations.NotNull;
import net.aurika.data.database.dataprovider.SQLDataHandlerProperties;
import net.aurika.data.database.dataprovider.SectionableDataSetter;

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
