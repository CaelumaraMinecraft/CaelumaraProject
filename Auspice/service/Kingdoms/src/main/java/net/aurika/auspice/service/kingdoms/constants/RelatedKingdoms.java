package net.aurika.auspice.service.kingdoms.constants;

import org.kingdoms.constants.group.Kingdom;

import java.util.Objects;

public class RelatedKingdoms {
    private final Kingdom primary;
    private final Kingdom secondary;

    public RelatedKingdoms(Kingdom primary, Kingdom secondary) {
        this.primary = primary;
        this.secondary = secondary;
    }

    public Kingdom getActiveKingdom() {
        return this.primary;
    }

    public Kingdom getPassiveKingdom() {
        return this.secondary;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RelatedKingdoms that = (RelatedKingdoms) o;
        return Objects.equals(primary, that.primary) && Objects.equals(secondary, that.secondary);
    }

    @Override
    public int hashCode() {
        return Objects.hash(primary, secondary);
    }
}

