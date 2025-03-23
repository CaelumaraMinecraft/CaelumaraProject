package net.aurika.auspice.permission;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public enum PermissionDefaultValue {

    EVERYONE("true", "everyone"),
    NO_ONE("false", "noone"),
    OP("op", "isop", "operator", "isoperator", "admin", "isadmin"),
    NOT_OP("!op", "notop", "!operator", "notoperator", "!admin", "notadmin");

    private final @NotNull String @NotNull [] names;
    private static final Map<String, PermissionDefaultValue> lookup = new HashMap<>();

    PermissionDefaultValue(String @NotNull ... names) {
        this.names = names;
    }

    public boolean getValue(boolean op) {
        return switch (this) {
            case EVERYONE -> true;
            case NO_ONE -> false;
            case OP -> op;
            case NOT_OP -> !op;
        };
    }

    /**
     * Looks up a {@linkplain PermissionDefaultValue} by name
     *
     * @param name Name of the default
     * @return Specified value, or null if not found
     */
    @Nullable
    public static PermissionDefaultValue getByName(@NotNull String name) {
        return lookup.get(name.toLowerCase(Locale.ROOT).replaceAll("[^a-z!]", ""));
    }

    @Override
    public @NotNull String toString() {
        return this.names[0];
    }

    static {
        for (PermissionDefaultValue value : values()) {
            for (String name : value.names) {
                lookup.put(name, value);
            }
        }
    }
}
