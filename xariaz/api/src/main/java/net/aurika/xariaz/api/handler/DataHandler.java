package net.aurika.xariaz.api.handler;

import net.aurika.common.key.Key;
import net.aurika.common.key.Keyed;
import net.aurika.xariaz.api.data.provider.DataSetter;
import org.jetbrains.annotations.NotNull;

public interface DataHandler<T> extends Keyed {

  @Override
  @NotNull Key key();

  void save(@NotNull DataSetter provider, T data);

}
