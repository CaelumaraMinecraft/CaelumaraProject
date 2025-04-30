package net.aurika.auspice.platform.entity.type;

import net.aurika.auspice.platform.entity.abstraction.Entity;
import net.kyori.adventure.key.Key;
import net.kyori.adventure.key.Keyed;
import net.kyori.adventure.translation.Translatable;
import org.jetbrains.annotations.NotNull;

public interface EntityType extends Keyed, Translatable, EntityTypeAware {

  @Override
  @NotNull Key key();

  @NotNull Class<? extends Entity> entityClass();

  @Override
  @NotNull String translationKey();

  @Override
  default @NotNull EntityType entityType() { return this; }

}
