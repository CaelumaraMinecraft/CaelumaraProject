package net.aurika.xariaz.api.handler;

import net.aurika.common.key.Key;
import net.aurika.xariaz.api.data.provider.DataGetter;
import net.aurika.xariaz.api.data.provider.DataSetter;
import org.jetbrains.annotations.NotNull;

public interface SingularDataHandler<T> extends DataHandler<T> {

  @Override
  @NotNull Key key();

  T load(@NotNull DataGetter provider);

  @Override
  void save(@NotNull DataSetter provider, T data);

}
