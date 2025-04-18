package net.aurika.util.cache.caffeine;

import net.aurika.common.validate.Validate;
import org.jetbrains.annotations.NotNull;

public class ReferencedExpirableObject<T> implements ExpirableObject {

  private final T reference;
  private final @NotNull ExpirationStrategy expirationStrategy;

  public ReferencedExpirableObject(T reference, @NotNull ExpirationStrategy expirationStrategy) {
    Validate.Arg.notNull(expirationStrategy, "expirationStrategy");
    this.reference = reference;
    this.expirationStrategy = expirationStrategy;
  }

  public T reference() {
    return this.reference;
  }

  public @NotNull ExpirationStrategy expirationStrategy() {
    return this.expirationStrategy;
  }

}
