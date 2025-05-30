package net.aurika.ecliptor.handler;

import net.aurika.ecliptor.api.Keyed;
import net.aurika.ecliptor.database.dataprovider.IdDataTypeHandler;
import net.aurika.ecliptor.database.dataprovider.SQLDataHandlerProperties;
import net.aurika.ecliptor.database.dataprovider.SectionableDataGetter;
import net.aurika.ecliptor.database.dataprovider.SectionableDataSetter;
import org.jetbrains.annotations.NotNull;

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

  public abstract void save(@NotNull SectionableDataSetter dataSetter, T object);

  public abstract @NotNull T load(@NotNull SectionableDataGetter dataGetter, K key);

}
