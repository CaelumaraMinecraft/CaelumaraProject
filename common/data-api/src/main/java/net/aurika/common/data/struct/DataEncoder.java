package net.aurika.common.data.struct;

import org.jetbrains.annotations.NotNull;

public interface DataEncoder<T> {

  @NotNull DataPart encode(@NotNull T value);

}
