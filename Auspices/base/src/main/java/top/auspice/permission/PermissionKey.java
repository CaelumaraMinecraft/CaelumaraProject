package top.auspice.permission;

import org.jetbrains.annotations.NotNull;
import net.aurika.annotations.data.Immutable;
import net.aurika.utils.Checker;
import top.auspice.utils.string.Strings;

import java.util.Arrays;
import java.util.Locale;
import java.util.Objects;

import static top.auspice.permission.Permission.STD_SEPARATOR;

@Immutable
public class PermissionKey {
    private final @NotNull String namespace;
    private final @NotNull String @NotNull [] name;

    public PermissionKey(@NotNull String namespace, @NotNull String @NotNull [] name) {
        Checker.Argument.checkNotNull(namespace, "namespace");
        Checker.Argument.checkNotNullArray(name, "name");
        this.namespace = namespace;
        this.name = name;
    }

    /**
     * 获取权限对应的命名空间 (插件命名空间)
     */
    public @NotNull String getNamespace() {
        return this.namespace;
    }

    /**
     * 获取权限的名称, 不包含命名空间
     */
    public @NotNull String @NotNull [] getName() {
        return this.name;
    }

    public @NotNull String toFullName() {
        return this.namespace + STD_SEPARATOR + String.join(String.valueOf(STD_SEPARATOR), this.name);
    }

    public static @NotNull PermissionKey of(@NotNull String namespace, String @NotNull [] name) {
        return new PermissionKey(namespace, name);
    }

    public static @NotNull PermissionKey fromFullNameArray(@NotNull String @NotNull [] key) {
        Checker.Argument.checkNotNullArray(key, "key");
        String[] nameArr = new String[key.length - 1];
        System.arraycopy(key, 1, nameArr, 0, nameArr.length);
        return PermissionKey.of(key[0], nameArr);
    }

    public static @NotNull PermissionKey fromFullName(@NotNull String fullName) {
        Checker.Argument.checkNotNull(fullName, "fullName");
        String[] key = Strings.splitArray(fullName, STD_SEPARATOR);
        return PermissionKey.fromFullNameArray(key);
    }

    public static @NotNull PermissionKey fromEnum(@NotNull String namespace, @NotNull Enum<?> nameEnum) {
        Checker.Argument.checkNotNull(namespace, "namespace");
        Checker.Argument.checkNotNull(nameEnum, "nameEnum");
        String lowerCaseName = nameEnum.name().toLowerCase(Locale.ENGLISH);
        String replacedName = lowerCaseName.replace('_', STD_SEPARATOR).replace('$', '-');
        String[] nameArr = Strings.splitArray(replacedName, STD_SEPARATOR);
        return PermissionKey.of(namespace, nameArr);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof PermissionKey that)) return false;
        return Objects.equals(namespace, that.namespace) && Objects.deepEquals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(namespace, Arrays.hashCode(name));
    }
}
