package net.aurika.config.part;

import net.aurika.config.part.annotation.NotNamed;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface ConfigSequence extends ConfigPart {

  /**
   * Gets the elements of this config sequence. All elements must be not {@link ConfigPart#isNamed() named}.
   *
   * @return the elements
   */
  @NotNull List<@NotNamed ? extends ConfigPart> elements();

}
