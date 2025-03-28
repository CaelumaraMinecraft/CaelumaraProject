package net.aurika.dyanasis.api.lexer;

import org.jetbrains.annotations.NotNull;

/**
 * An object that implemented the {@linkplain RawStringTraceable} interface must hold the raw string data from the lexer.
 */
public interface RawStringTraceable {

  /**
   * Gets the raw string.
   *
   * @return the raw string
   */
  @NotNull String rawString();

}
