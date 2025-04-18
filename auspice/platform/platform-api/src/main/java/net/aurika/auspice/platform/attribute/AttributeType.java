package net.aurika.auspice.platform.attribute;

import net.aurika.auspice.platform.registry.TypedKey;
import net.kyori.adventure.key.Keyed;
import net.kyori.adventure.translation.Translatable;
import org.jetbrains.annotations.NotNull;

public interface AttributeType extends Keyed, Translatable {

  @Override
  @NotNull TypedKey<? extends AttributeType> key();

  @Override
  @NotNull String translationKey();

}
