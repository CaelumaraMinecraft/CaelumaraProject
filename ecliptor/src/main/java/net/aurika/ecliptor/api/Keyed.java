package net.aurika.ecliptor.api;

import org.jetbrains.annotations.NotNull;

public interface Keyed<K> {

  @NotNull K dataKey();

}
