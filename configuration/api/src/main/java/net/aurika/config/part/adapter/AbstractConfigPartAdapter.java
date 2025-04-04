package net.aurika.config.part.adapter;

import net.aurika.config.part.ConfigPart;
import net.aurika.validate.Validate;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractConfigPartAdapter<P extends ConfigPart> implements ConfigPartAdapter<P> {

  private final @NotNull Class<P> targetType;

  public AbstractConfigPartAdapter(@NotNull Class<P> targetType) {
    Validate.Arg.notNull(targetType, "targetType");
    this.targetType = targetType;
  }

  public final @NotNull Class<? extends P> targetType() {
    return targetType;
  }

}
