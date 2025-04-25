package net.aurika.auspice.platform.adapter;

import net.aurika.common.validate.Validate;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractAdapter<AT, PT> implements Adapter<AT, PT> {

  private final Class<AT> auspiceType;
  private final Class<PT> platformType;

  public AbstractAdapter(@NotNull Class<AT> auspiceType, @NotNull Class<PT> platformType) {
    Validate.Arg.notNull(auspiceType, "auspiceType");
    Validate.Arg.notNull(platformType, "platformType");
    this.auspiceType = auspiceType;
    this.platformType = platformType;
  }

  @Override
  public abstract @NotNull AT adaptToAuspice(@NotNull PT platformObj);

  @Override
  public abstract @NotNull PT adaptToActual(@NotNull AT auspiceObj);

  @Override
  public @NotNull Class<? extends AT> auspiceType() { return auspiceType; }

  @Override
  public @NotNull Class<? extends PT> platformType() { return platformType; }

}
