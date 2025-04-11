package net.aurika.common.keyed;

import org.jetbrains.annotations.NotNull;

public interface Keyed<K> {

  @NotNull K key();

}
