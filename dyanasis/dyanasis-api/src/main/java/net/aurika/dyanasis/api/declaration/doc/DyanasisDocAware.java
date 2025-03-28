package net.aurika.dyanasis.api.declaration.doc;

import org.jetbrains.annotations.Nullable;

public interface DyanasisDocAware {

  /**
   * Gets the dyanasis doc of the implementation, can be null.
   *
   * @return the dyanasis doc
   */
  @Nullable DyanasisDoc dyanasisDoc();

}
