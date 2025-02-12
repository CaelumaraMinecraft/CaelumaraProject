package top.auspice.permission;

import org.jetbrains.annotations.NotNull;
import net.aurika.util.Checker;

public class AbstractPermission implements Permission {
    private final PermissionKey key;
    private @NotNull PermissionDefaultValue defaultValue;

    public AbstractPermission(@NotNull String namespace, @NotNull String[] name, @NotNull PermissionDefaultValue defaultValue) {
        this(PermissionKey.of(namespace, name), defaultValue);
    }

    public AbstractPermission(@NotNull PermissionKey key, @NotNull PermissionDefaultValue defaultValue) {
        Checker.Arg.notNull(key, "key");
        Checker.Arg.notNull(defaultValue, "defaultValue");
        this.key = key;
        this.defaultValue = defaultValue;
    }

    @Override
    public @NotNull PermissionKey getKey() {
        return this.key;
    }

    @Override
    public @NotNull PermissionDefaultValue getDefaultValue() {
        return this.defaultValue;
    }

    @Override
    public void setDefaultValue(@NotNull PermissionDefaultValue defaultValue) {
        Checker.Arg.notNull(defaultValue, "defaultValue");
        this.defaultValue = defaultValue;
    }
}
