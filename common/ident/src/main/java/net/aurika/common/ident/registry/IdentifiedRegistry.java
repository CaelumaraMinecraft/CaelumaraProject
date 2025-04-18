package net.aurika.common.ident.registry;

import net.aurika.common.ident.Ident;
import net.aurika.common.ident.Identified;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

public interface IdentifiedRegistry<T extends Identified> extends net.aurika.common.keyed.KeyedRegistry<Ident, T> {

  @Override
  void register(T obj);

  @Override
  boolean isRegistered(Ident ident);

  @Override
  @Nullable T getRegistered(Ident ident);

  @NotNull Map<Ident, T> registry();

}
