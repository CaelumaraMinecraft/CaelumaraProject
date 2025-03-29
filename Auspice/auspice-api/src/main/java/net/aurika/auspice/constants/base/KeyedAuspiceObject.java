package net.aurika.auspice.constants.base;

import net.aurika.ecliptor.api.Keyed;
import net.aurika.ecliptor.api.KeyedDataObject;
import org.jetbrains.annotations.NotNull;

public interface KeyedAuspiceObject<K> extends AuspiceObject, Keyed<K>, KeyedDataObject<K> {

  @Override
  @NotNull K dataKey();

  abstract class Impl<K> extends AuspiceObject.Impl implements KeyedAuspiceObject<K> {

    private final @NotNull K key;

    public Impl(@NotNull K key) {
      this.key = key;
    }

    @Override
    public @NotNull K dataKey() {
      return this.key;
    }

  }

  interface WrapperImpl<K> extends KeyedAuspiceObject<K>, AuspiceObject.WrapperImpl {

    @NotNull KeyedAuspiceObject<K> getWrapped();

    @Override
    default @NotNull K dataKey() {
      return this.getWrapped().dataKey();
    }

  }

}
