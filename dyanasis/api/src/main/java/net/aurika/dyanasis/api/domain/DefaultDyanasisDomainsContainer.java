package net.aurika.dyanasis.api.domain;

import net.aurika.validate.Validate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class DefaultDyanasisDomainsContainer implements DyanasisDomainObjectsContainer {

  protected @NotNull Map<String, Object> domains;

  public DefaultDyanasisDomainsContainer() {
    this(new HashMap<>());
  }

  public DefaultDyanasisDomainsContainer(@NotNull Map<String, Object> domains) {
    Validate.Arg.notNull(domains, "domains");
    this.domains = domains;
  }

  @Override
  public boolean hasDomain(@NotNull String domainName) {
    return domains.containsKey(domainName);
  }

  @Override
  public @Nullable Object getDomain(@NotNull String domainName) {
    return domains.get(domainName);
  }

  @Override
  public @NotNull Map<String, ?> domains() {
    return new HashMap<>(domains);
  }

}
