package net.aurika.dyanasis.api.util;

import net.aurika.dyanasis.api.runtime.DyanasisRuntime;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class DyanasisRuntimeLocal<T> {

  protected Map<DyanasisRuntime, T> map;
  protected Function<DyanasisRuntime, T> defaultValue;

  public DyanasisRuntimeLocal() { }

  public DyanasisRuntimeLocal(@NotNull Map<DyanasisRuntime, T> map) {
    this.map = map;
  }

  public @Nullable T find(@NotNull DyanasisRuntime runtime) {
    return map != null ? map.get(runtime) : null;
  }

  public @NotNull T get(@NotNull DyanasisRuntime runtime) throws IllegalStateException {
    if (map == null) {
      if (defaultValue == null) {
        throw new IllegalStateException("DyanasisRuntime has not been initialized");
      } else {
        this.map = createMap();
        T value = defaultValue.apply(runtime);
        this.map.put(runtime, value);
        return value;
      }
    } else {
      T got = map.get(runtime);
      if (got == null) {
        if (defaultValue == null) {
          throw new IllegalStateException("DyanasisRuntime has not been initialized");
        } else {
          T value = defaultValue.apply(runtime);
          this.map.put(runtime, value);
          return value;
        }
      } else {
        return got;
      }
    }
  }

  public T set(@NotNull DyanasisRuntime runtime, @NotNull T value) {
    return map != null ? map.put(runtime, value) : (map = createMap()).put(runtime, value);
  }

  protected @NotNull Map<DyanasisRuntime, T> createMap() {
    return new HashMap<>();
  }

}
