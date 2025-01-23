package top.auspice.data.object.structure;

import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.math.BigInteger;

public enum DataQuantumType {
    INT("int", new Class[]{int.class, Integer.class}),
    LONG("long", new Class[]{long.class, Long.class, BigInteger.class}),
    FLOAT("float", new Class[]{float.class, Float.class}),
    DOUBLE("double", new Class[]{double.class, Double.class}),
    BOOLEAN("bool", new Class[]{boolean.class, Boolean.class}),
    STRING("str", new Class[]{String.class, byte[].class, char[].class}),
    ;
    private final @NotNull String id;
    private final @NotNull Class<?> @NotNull [] classes;

    DataQuantumType(@NotNull String id, Class<?> @NotNull [] classes) {
        this.id = id;
        this.classes = classes;
    }

    @ApiStatus.Experimental
    public @NotNull String getId() {
        return this.id;
    }

    @ApiStatus.Experimental
    public @NotNull Class<?> @NotNull [] getClasses() {
        return this.classes.clone();
    }
}
