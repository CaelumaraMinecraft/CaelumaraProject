package net.aurika.wands.constants.spells.types;

import org.jetbrains.annotations.NotNull;
import net.aurika.auspice.key.NSedKey;

public abstract class AbstractSpellType implements SpellType {

  private final NSedKey NSedKey;

  public AbstractSpellType(NSedKey NSedKey) {
    this.NSedKey = NSedKey;
  }

  public @NotNull NSedKey getNamespacedKey() {
    return this.NSedKey;
  }

}
