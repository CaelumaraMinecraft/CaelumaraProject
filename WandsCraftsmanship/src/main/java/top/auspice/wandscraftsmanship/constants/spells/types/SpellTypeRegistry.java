package top.auspice.wandscraftsmanship.constants.spells.types;

import top.auspice.key.NSKedRegistry;
import top.auspice.wandscraftsmanship.main.WandsCraftsmanship;

public final class SpellTypeRegistry extends NSKedRegistry<SpellType> {
    public static final SpellTypeRegistry INSTANCE = new SpellTypeRegistry();

    private SpellTypeRegistry() {
        super(WandsCraftsmanship.get(), "WAND_TYPE");
    }
}
