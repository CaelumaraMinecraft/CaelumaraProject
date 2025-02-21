package net.aurika.auspice.wandscraftsmanship.constants.spells.types;

import net.aurika.common.key.registry.AbstractKeyedRegistry;

public final class SpellTypeRegistry extends AbstractKeyedRegistry<SpellType> {
    public static final SpellTypeRegistry INSTANCE = new SpellTypeRegistry();

    private SpellTypeRegistry() {
        super();
    }
}
