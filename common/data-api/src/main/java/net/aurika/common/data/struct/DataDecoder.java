package net.aurika.common.data.struct;

import org.jetbrains.annotations.NotNull;

public interface DataDecoder<T> {

  @NotNull T decode(@NotNull DataPart data);

}
