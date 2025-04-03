package net.aurika.auspice.utils.debug;

import net.aurika.common.key.Ident;
import net.aurika.common.key.KeyPatterns;
import net.aurika.validate.Validate;
import org.jetbrains.annotations.NotNull;

public final class DebugIdent implements Ident {

  private final Ident delegate;

  public DebugIdent(@NotNull Ident delegate) {
    Validate.Arg.notNull(delegate, "delegate");
    this.delegate = delegate;
  }

  @SuppressWarnings("PatternValidation")
  @Override
  @KeyPatterns.Namespace
  public @NotNull String namespace() {
    return delegate.namespace();
  }

  @Override
  public @NotNull String @NotNull [] value() {
    return delegate.value();
  }

  @Override
  public @NotNull String asDataString() {
    return delegate.asDataString();
  }

  @Override
  public boolean equals(@NotNull Ident other) {
    return delegate.equals(other);
  }

}

