package net.aurika.wands.constants.spells.types;

import net.aurika.common.ident.registry.AbstractKeyedRegistry;

public final class SpellTypeRegistry extends AbstractKeyedRegistry<SpellType> {

  public static final SpellTypeRegistry INSTANCE = new SpellTypeRegistry();

  private SpellTypeRegistry() {
    super();
  }

}
