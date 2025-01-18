package top.auspice.data.handlers.abstraction;

import org.jetbrains.annotations.NotNull;
import top.auspice.data.database.dataprovider.SQLDataHandlerProperties;
import top.auspice.data.database.dataprovider.SectionableDataGetter;

public abstract class SingularDataHandler<T> extends DataHandler<T> {
    public SingularDataHandler(@NotNull SQLDataHandlerProperties var1) {
        super(var1);
    }

    public abstract T load(@NotNull SectionableDataGetter var1);
}
