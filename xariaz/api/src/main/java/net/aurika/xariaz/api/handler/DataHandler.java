package net.aurika.xariaz.api.handler;

import net.aurika.common.ident.Ident;
import net.aurika.common.ident.Identified;
import net.aurika.xariaz.api.data.provider.DataSetter;
import org.jetbrains.annotations.NotNull;

public interface DataHandler<T> extends Identified {

  @Override
  @NotNull Ident ident();

  void save(@NotNull DataSetter provider, T data);

}
