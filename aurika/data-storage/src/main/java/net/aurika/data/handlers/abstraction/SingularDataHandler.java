package net.aurika.data.handlers.abstraction;

import org.jetbrains.annotations.NotNull;
import net.aurika.data.database.dataprovider.SQLDataHandlerProperties;
import net.aurika.data.database.dataprovider.SectionableDataGetter;

public abstract class SingularDataHandler<T> extends DataHandler<T> {
    public SingularDataHandler(@NotNull SQLDataHandlerProperties var1) {
        super(var1);
    }

    public abstract T load(@NotNull SectionableDataGetter var1);
}
