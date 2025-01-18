package top.auspice.key.nested;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import top.auspice.api.user.AuspiceUser;
import top.auspice.key.NSKey;
import top.auspice.key.nested.exceptions.NestedNamespaceContainsIllegalLevelException;
import top.auspice.utils.Checker;
import top.auspice.utils.string.Strings;

import java.util.Arrays;
import java.util.Objects;

public final class NestedNamespace {
    public static final char STD_SEPARATOR = '_';

    public static final NestedNamespace STD = new NestedNamespace(new String[]{"std"});

    private final @NotNull String @NotNull [] nesting;

    public static NestedNamespace of(@NotNull String @NotNull [] nesting) {
        return new NestedNamespace(nesting);
    }

    public static NestedNamespace of(@NotNull String nesting) {
        return new NestedNamespace(Strings.splitArray(nesting, STD_SEPARATOR, true));
    }

    public NestedNamespace(@NotNull String @NotNull [] nesting) {
        Checker.Argument.checkNotNullArray(nesting, "nesting");

        for (int i = 0; i < nesting.length; i++) {
            String ns = nesting[i];
            if (!NSKey.NAMESPACE_PATTERN.matcher(ns).matches()) {
                throw new NestedNamespaceContainsIllegalLevelException(nesting, i, "Doesnt matches pattern " + NSKey.NAMESPACE_PATTERN);
            }
        }

        this.nesting = nesting;
    }

    @Contract(value = "-> new", pure = true)
    public @NotNull String @NotNull [] getNesting() {
        return this.nesting.clone();
    }

    /**
     * 返回这个嵌套命名空间是否是顶级 (长度为 1)
     */
    public boolean isTop() {
        return this.nesting.length == 1;
    }

    /**
     * 检查这个嵌套命名空间是否在另外一个嵌套命名空间内部.
     * <p>
     * 两个相等的嵌套命名空间都不是对方的内部
     * <p>
     * 比如 {@code "std_testing_color"} 是 {@code "std_testing"} 的内部, 也是 {@code "std"} 的内部, 但不是 {@code "std_testing_color"} 的内部
     *
     * @param other 另外一个嵌套命名空间
     * @return 这个嵌套命名空间是否在另外一个嵌套命名空间内部
     */
    public boolean isInside(@NotNull NestedNamespace other) {
        Checker.Argument.checkNotNull(other, "other");
        String[] other_nesting = other.nesting;
        String[] this_nesting = this.nesting;
        if (this_nesting.length <= other_nesting.length) {
            return false;
        }
        for (int i = 0; i < this_nesting.length; i++) {
            if (!this_nesting[i].equals(other_nesting[i])) {
                return false;
            }
        }
        return true;
    }

    /**
     * 创建一个新的内部嵌套命名空间
     *
     * @return 新的内部嵌套命名空间
     */
    @Contract("_ -> new")
    public @NotNull NestedNamespace newInternal(@NotNull @NSKey.Namespace String ns) {
        Checker.Argument.checkNotNull(ns, "ns");
        if (!NSKey.NAMESPACE_PATTERN.matcher(ns).matches()) {
            throw new IllegalArgumentException("namespace '" + ns + "' doesnt matches: " + NSKey.ALLOWED_NAMESPACE);
        }

        String[] newNesting = new String[this.nesting.length + 1];
        System.arraycopy(this.nesting, 0, newNesting, 0, this.nesting.length);
        newNesting[newNesting.length - 1] = ns;

        return new NestedNamespace(newNesting);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof NestedNamespace that)) return false;
        return Objects.deepEquals(nesting, that.nesting);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(nesting);
    }

    public static @NotNull NestedNamespace topOfAuspiceUser(@NotNull AuspiceUser au) {
        Checker.Argument.checkNotNull(au, "au");
        return new NestedNamespace(new String[]{au.getNamespace()});
    }
}
