package net.aurika.dyanasis.api.domain;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;

/**
 * A container that saved the domains has the same level.
 */
public interface DyanasisDomainObjectsContainer extends DyanasisDomainObjectsAware {

  boolean hasDomain(@NotNull String domainName);

  @Nullable Object getDomain(@NotNull String domainName);

  @NotNull Map<String, ?> domains();

  @Override
  default @NotNull DyanasisDomainObjectsContainer dyanasisDomains() {
    return this;
  }

}
