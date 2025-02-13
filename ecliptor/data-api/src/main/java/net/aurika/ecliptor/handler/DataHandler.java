package net.aurika.ecliptor.handler;

import net.aurika.validate.Validate;
import net.aurika.ecliptor.database.dataprovider.SQLDataHandlerProperties;
import net.aurika.ecliptor.database.dataprovider.SectionableDataSetter;
import org.jetbrains.annotations.NotNull;

public abstract class DataHandler<T> {
    private final @NotNull SQLDataHandlerProperties sqlProperties;

    public DataHandler(@NotNull SQLDataHandlerProperties sqlProperties) {
        Validate.Arg.notNull(sqlProperties, "sqlProperties");
        this.sqlProperties = sqlProperties;
    }

    public final @NotNull SQLDataHandlerProperties getSqlProperties() {
        return this.sqlProperties;
    }

    public abstract void save(@NotNull SectionableDataSetter dataSetter, T object);
}
