package net.aurika.ecliptor.api.structured.scalars;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

public enum DataScalarType {
    INT("int"),
    LONG("long"),
    FLOAT("float"),
    DOUBLE("double"),
    BOOLEAN("boolean"),
    STRING("string"),
    ;
    private final @NotNull String id;

    DataScalarType(@NotNull String id) {
        this.id = id;
    }

    @ApiStatus.Experimental
    public @NotNull String id() {
        return this.id;
    }
}
