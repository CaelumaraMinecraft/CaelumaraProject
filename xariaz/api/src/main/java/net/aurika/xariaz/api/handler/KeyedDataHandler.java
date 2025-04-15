package net.aurika.xariaz.api.handler;

import net.aurika.common.key.Key;
import net.aurika.common.keyed.Keyed;
import net.aurika.xariaz.api.data.provider.DataGetter;
import net.aurika.xariaz.api.data.provider.DataSetter;
import org.jetbrains.annotations.NotNull;

public interface KeyedDataHandler<K, T extends Keyed<K>> extends DataHandler<T> {

  @Override
  @NotNull Key key();

  T load(@NotNull DataGetter provider, K id);

  @Override
  void save(@NotNull DataSetter provider, T data);

}
