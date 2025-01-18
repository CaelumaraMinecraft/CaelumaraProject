package top.auspice.data.handlers.abstraction;

import org.jetbrains.annotations.NotNull;
import top.auspice.abstraction.Keyed;
import top.auspice.data.database.dataprovider.IdDataTypeHandler;
import top.auspice.data.database.dataprovider.SQLDataHandlerProperties;
import top.auspice.data.database.dataprovider.SectionableDataGetter;

import java.util.Objects;

public abstract class KeyedDataHandler<K, T extends Keyed<K>> extends DataHandler<T> {
    private final @NotNull IdDataTypeHandler<K> idHandler;

    public KeyedDataHandler(@NotNull IdDataTypeHandler<K> idHandler, @NotNull SQLDataHandlerProperties sqlDataHandlerProperties) {
        super(sqlDataHandlerProperties);
        Objects.requireNonNull(idHandler);
        this.idHandler = idHandler;
    }

    public final @NotNull IdDataTypeHandler<K> getIdHandler() {
        return this.idHandler;
    }

    public abstract @NotNull T load(@NotNull SectionableDataGetter dataGetter, K key);
}
