package net.aurika.auspice.platform;

import net.aurika.common.keyed.annotation.KeyedBy;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

@KeyedBy(value = "uniqueId")
public interface UUIDIdentified {

  /**
   * Get the unique identity of the object.
   *
   * @return the unique id
   */
  @NotNull UUID uniqueId();

}
