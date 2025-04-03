package net.aurika.config.yaml.resolver;

import org.jetbrains.annotations.NotNull;
import org.snakeyaml.engine.v2.nodes.Tag;
import org.snakeyaml.engine.v2.resolver.CoreScalarResolver;

public class AuspiceScalarResolver extends CoreScalarResolver {

  public static final AuspiceScalarResolver INSTANCE = new AuspiceScalarResolver();

  @Override
  public @NotNull Tag resolve(@NotNull String value, @NotNull Boolean implicit) {
    return super.resolve(value, implicit);
  }

}
