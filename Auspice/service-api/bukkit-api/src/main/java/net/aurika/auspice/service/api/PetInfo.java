package net.aurika.auspice.service.api;

import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class PetInfo {
    protected final @Nullable UUID owner;
    protected final boolean canBeAttacked;

    public PetInfo(@Nullable UUID owner, boolean canBeAttacked) {
        this.owner = owner;
        this.canBeAttacked = canBeAttacked;
    }

    public final @Nullable UUID getOwner() {
        return this.owner;
    }

    public final boolean canBeAttacked() {
        return this.canBeAttacked;
    }
}
