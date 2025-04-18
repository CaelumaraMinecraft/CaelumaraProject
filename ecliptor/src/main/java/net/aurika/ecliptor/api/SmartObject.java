package net.aurika.ecliptor.api;

import org.jetbrains.annotations.ApiStatus.Internal;
import org.jetbrains.annotations.ApiStatus.NonExtendable;

public interface SmartObject {

  @Internal
  void invalidateObject();

  @Internal
  boolean hasObjectExpired();

  @Internal
  default void ensureObjectExpiration() {
    if (this.hasObjectExpired()) {
      throw new IllegalStateException("This object instance has been unloaded from data but is being used: " + this);
    }
  }

  @Internal
  @NonExtendable
  void saveObjectState(boolean var1);

  @Internal
  @NonExtendable
  boolean isObjectStateSaved();

  @Internal
  boolean shouldSave();

}
