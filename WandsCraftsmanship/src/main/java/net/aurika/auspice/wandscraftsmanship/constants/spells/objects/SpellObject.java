package net.aurika.auspice.wandscraftsmanship.constants.spells.objects;

import net.aurika.auspice.wandscraftsmanship.constants.spells.types.SpellType;

public abstract class SpellObject implements Cloneable {

    protected final SpellType type;

    protected SpellObject(SpellType type) {
        this.type = type;
    }

    public SpellType getType() {
        return this.type;
    }

    public abstract SpellObject clone();

}
