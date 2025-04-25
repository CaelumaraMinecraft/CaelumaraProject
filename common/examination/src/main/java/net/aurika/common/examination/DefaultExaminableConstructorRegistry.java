package net.aurika.common.examination;

import net.aurika.common.validate.Validate;
import net.kyori.examination.Examinable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DefaultExaminableConstructorRegistry implements ExaminableConstructorRegistry {

  private final @NotNull Map<Class<?>, ExaminableConstructor<?>> registry;

  public DefaultExaminableConstructorRegistry() {
    this(new HashMap<>());
  }

  public DefaultExaminableConstructorRegistry(@NotNull Map<Class<?>, ExaminableConstructor<?>> registry) {
    Validate.Arg.notNull(registry, "registry");
    this.registry = registry;
  }

  @Override
  public @Nullable <E extends Examinable> ExaminableConstructor<E> findConstructor(@NotNull Class<E> examinableType) {
    // noinspection unchecked
    return (ExaminableConstructor<E>) registry.get(examinableType);
  }

  @Override
  public <E extends Examinable> void registerConstructor(@NotNull ExaminableConstructor<E> constructor) {
    Class<? extends E> examinableType = constructor.examinableType();
    Objects.requireNonNull(examinableType, "examinableType");
    registry.put(examinableType, constructor);
  }

}
