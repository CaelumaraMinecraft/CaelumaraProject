package net.aurika.config.part.accessor;

import org.jetbrains.annotations.NotNull;

/**
 * When can't access the need actual config, access the default.
 */
public interface DefaultableConfigAccessor extends ConfigAccessor {

  boolean isUsingDefault();

  @NotNull DefaultableConfigAccessor noDefault();

  @NotNull DefaultableConfigAccessor useDefault();

}
