package net.aurika.common.examination;

import net.kyori.examination.Examinable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ExaminableConstructorRegistry {

  <E extends Examinable> @Nullable ExaminableConstructor<E> findConstructor(@NotNull Class<E> examinableType);

  <E extends Examinable> void registerConstructor(@NotNull ExaminableConstructor<E> constructor);

}

