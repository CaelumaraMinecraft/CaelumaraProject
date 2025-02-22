package net.aurika.config.path;

import net.aurika.abstraction.BuildableObject;
import net.aurika.common.annotations.data.Immutable;
import net.aurika.util.Checker;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import net.aurika.auspice.utils.arrays.ArrayUtils;
import net.aurika.auspice.utils.string.Strings;

import java.util.Arrays;
import java.util.regex.Pattern;

@Immutable
public class ConfigEntry implements BuildableObject {
    public static final String ALLOWED_PATH = "[A-Za-z0-9-]+((\\.[A-Za-z0-9-]+)+)?";
    private static final Pattern PATH_PATTERN = Pattern.compile(ALLOWED_PATH);
    protected static final ConfigEntry EMPTY = new ConfigEntry(new String[0]);

    private final @NotNull String @NotNull [] path;

    public ConfigEntry(@NotNull String @NotNull [] path) {
        Checker.Arg.nonNullArray(path, "path", "Path array cannot contains null values");
        this.path = path;
    }

    public @NotNull String @NotNull [] getPath() {
        return this.path.clone();
    }

    public @NotNull String getSection(int index) throws IndexOutOfBoundsException {
        return this.path[index];
    }

    public boolean isEmpty() {
        return this.path.length == 0;
    }

    public @Nullable String getEnd() {
        if (this.path.length == 0) {
            return null;
        } else {
            return this.path[this.path.length - 1];
        }
    }

    public @NotNull String getEnd(String s) {
        if (this.path.length == 0) {
            return s;
        } else {
            return this.path[this.path.length - 1];
        }
    }

    public ConfigEntry merge(@NotNull ConfigEntry second) {
        String[] newPath = ArrayUtils.merge(this.path, second.path);
        return new ConfigEntry(newPath);
    }

    public ConfigEntry merge(@NotNull String[] secondPath) {
        String[] newPath = ArrayUtils.merge(this.path, secondPath);
        return new ConfigEntry(newPath);
    }

    @Contract(value = "!null, -> new; null -> this", pure = true)
    public ConfigEntry append(String sec) {
        if (sec == null) {
            return this;
        }
        if (sec.contains(".")) {
            throw new IllegalArgumentException("section can not contains separator char '.'");
        }
        int index = this.path.length;
        String[] newPath = new String[index + 1];
        System.arraycopy(this.path, 0, newPath, 0, this.path.length);
        newPath[index] = sec;
        return new ConfigEntry(newPath);
    }

    public ConfigEntry removePrefix(@NotNull ConfigEntry prefix) {
        Checker.Arg.notNull(prefix, "prefix");
        return this.removePrefix(prefix.path);
    }

    /**
     * <p>
     * 去除头部的一段路径
     * <p>
     * "this.is.a.path" 在调用此方法 "this.is" 后会得到 "a.path"
     *
     * @throws IllegalArgumentException 当配置路径不以另一字符串数组开头时.
     */
    public ConfigEntry removePrefix(@NotNull String @NotNull [] other) {
        if (ArrayUtils.startsWith(this.path, other)) {
            return new ConfigEntry(ArrayUtils.removePre(this.path, other.length));
        } else {
            throw new IllegalArgumentException("This path is not starts with other String array: " + Arrays.toString(this.path) + ", other: " + Arrays.toString(other));
        }
    }

    public final int hashCode() {
        int hash = 1;
        for (String sec : this.path) {
            hash = hash * 31 + sec.hashCode();
        }
        return hash;
    }

    public static @NotNull ConfigEntry fromString(@Nullable String str) {
        if (str == null) {
            return EMPTY;
        }
        return new ConfigEntry(Strings.splitArray(str, '.', false));  // TODO
    }

    public static @NotNull ConfigEntry empty() {
        return EMPTY;
    }

    public static boolean isValidConfigEntry(String str) {
        return PATH_PATTERN.matcher(str).matches();
    }

    public final boolean equals(Object obj) {
        if (!(obj instanceof ConfigEntry ConfigEntry)) {
            return false;
        }
        if (ConfigEntry.path.length != this.path.length) {
            return false;
        } else {
            for (int var2 = 0; var2 < this.path.length; ++var2) {
                if (!this.path[var2].equals(ConfigEntry.path[var2])) {
                    return false;
                }
            }

            return true;
        }
    }

    public String asString() {
        return String.join(".", this.path);
    }

    @Override
    public @NotNull ConfigEntryBuilder toBuilder() {
        return new ConfigEntryBuilder(this);
    }

    public String toString() {
        return "ConfigEntry{" + Arrays.toString(this.path) + '}';
    }
}
