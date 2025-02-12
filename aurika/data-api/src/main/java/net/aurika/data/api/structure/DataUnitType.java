package net.aurika.data.api.structure;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

public enum DataUnitType {
    INT("int"),
    LONG("long"),
    FLOAT("float"),
    DOUBLE("double"),
    BOOLEAN("boolean"),
    STRING("string"),
    ;
    private final @NotNull String id;

    DataUnitType(@NotNull String id) {
        this.id = id;
    }

    @ApiStatus.Experimental
    public @NotNull String getId() {
        return this.id;
    }
}
