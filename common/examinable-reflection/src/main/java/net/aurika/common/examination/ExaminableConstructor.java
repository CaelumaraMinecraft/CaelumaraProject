package net.aurika.common.examination;

import net.kyori.examination.Examinable;
import net.kyori.examination.ExaminableProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

public interface ExaminableConstructor<E extends Examinable> {

  default @NotNull E construct(@NotNull Stream<? extends ExaminableProperty> properties) {
    return construct(properties, null);
  }

  @NotNull E construct(@NotNull Stream<? extends ExaminableProperty> properties, @Nullable ExaminableConstructorRegistry constructorRegistry);

  @NotNull Class<? extends E> examinableType();

}
