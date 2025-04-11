package net.aurika.common.data.struct;

import org.jetbrains.annotations.NotNull;

public interface DataSerializer<T> extends DataEncoder<T>, DataDecoder<T> {

  @Override
  @NotNull DataPart encode(@NotNull T value);

  @Override
  @NotNull T decode(@NotNull DataPart data);

}
