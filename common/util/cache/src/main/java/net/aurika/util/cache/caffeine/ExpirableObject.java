package net.aurika.util.cache.caffeine;

import org.jetbrains.annotations.NotNull;

public interface ExpirableObject {

  @NotNull ExpirationStrategy expirationStrategy();

}
