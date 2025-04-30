package net.aurika.common.ident;

import net.aurika.common.keyed.annotation.KeyedBy;
import org.jetbrains.annotations.NotNull;

@KeyedBy(value = "ident")
public interface Identified extends IdentAware {

  /**
   * Gets the ident for this {@link Identified}.
   *
   * @return the ident, it is also the key
   */
  @Override
  @NotNull Ident ident();

}
