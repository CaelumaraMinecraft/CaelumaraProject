package net.aurika.config.path;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import net.aurika.common.annotations.data.Immutable;
import top.auspice.utils.Pair;
import top.auspice.utils.Validate;
import top.auspice.utils.arrays.ArrayUtils;
import top.auspice.utils.string.Strings;

import java.util.*;

/**
 * 配置路径.
 * 请不要使用带有空节的配置路径, 在某些地方可能会删掉这些空节, 某些地方又不会
 * 以下是不建议的 {@linkplain ConfigPath} 创建方法示范:
 * <blockquote><pre>
 *     new String[]{"", "sub"}
 *     "..a.b.sd"
 * </pre></blockquote>
 */
@Immutable
public class ConfigPath {
    private final String[] path;
    private final boolean containVariables;

    /**
     * 构造函数.
     * <p>
     * 输入空字符串或者 {@code null} 都会产生长度为 0 的 {@linkplain ConfigPath} 实例, 这些空的路径节都没删掉了
     *
     * @param path 路径
     */
    public ConfigPath(@Nullable String path) {
        if (path == null) {
            this.path = new String[0];
            this.containVariables = false;
        } else {
            this.path = Strings.splitArray(path, '.', false);
            this.containVariables = path.contains("{");
        }
    }

    /**
     * 构造函数.
     * <p>
     * 构造完的 {@linkplain ConfigPath} 的长度将会是 {@code path} 的长度.
     *
     * @param path 路径, 里面的任何值都不能为 {@code null}
     */
    public ConfigPath(@NonNull String[] path) {
        Objects.requireNonNull(path);
        Validate.noNullElements(path, "Config path cannot contains any null value.");

        this.path = path;
        boolean var1 = false;
        for (String sec : path) {
            if (sec.startsWith("{")) {
                var1 = true;
                break;
            }
            if (sec.contains(".")) {
                throw new IllegalArgumentException("Any section of a path cannot contains separator '.'");
            }
        }

        this.containVariables = var1;
    }

    public static ConfigPath fromString(String path) {
        return new ConfigPath(path);
    }

    public static ConfigPath of(String... path) {
        return new ConfigPath(path);
    }

    public static ConfigPath of(Collection<String> path) {
        return new ConfigPath(path.toArray(new String[0]));
    }

    public ConfigPath merge(ConfigPath second) {
        String[] newPath = ArrayUtils.merge(this.path, second.path);
        return new ConfigPath(newPath);
    }

    public ConfigPath merge(@NotNull String[] secondPath) {
        String[] newPath = ArrayUtils.merge(this.path, secondPath);
        return new ConfigPath(newPath);
    }

    /**
     * 拼接一节.
     */
    @Contract("null -> this")
    public ConfigPath append(@Nullable String section) {
        if (section == null) {
            return this;
        }
        if (section.contains(".")) {
            throw new IllegalArgumentException("section can not contains separator char '.'");
        }
        int index = this.path.length;
        String[] newPath = new String[index + 1];
        System.arraycopy(this.path, 0, newPath, 0, this.path.length);
        newPath[index] = section;
        return new ConfigPath(newPath);
    }

    public ConfigPath removePrefix(ConfigPath head) {
        return this.removePrefix(head.path);
    }

    /**
     * <p>
     * 去除头部的一段路径
     * </p><p>
     * "this.is.a.path" 在调用此方法 "this.is" 后会得到 "a.path"
     * </p>
     *
     * @throws IllegalArgumentException 当配置路径不以另一字符串数组开头时.
     */
    public ConfigPath removePrefix(String[] other) {
        if (ArrayUtils.startsWith(this.path, other)) {
            return new ConfigPath(ArrayUtils.removePre(this.path, other.length));
        } else {
            throw new IllegalArgumentException("This path is not starts with other String array: " + Arrays.toString(this.path) + ", other: " + Arrays.toString(other));
        }

    }

    /**
     * 返回最后一节
     *
     * @return {@code null} 当长度为 0 时.
     */
    @Nullable
    public String getEnd() {
        if (this.path.length > 0) {
            return this.path[path.length - 1];
        } else {
            return null;
        }
    }

    @Contract("!null ->!null")
    public String getEnd(String whenEmptyPath) {
        if (this.path.length > 0) {
            return this.path[path.length - 1];
        } else {
            return whenEmptyPath;
        }
    }

    @Contract("_ -> new")
    public ConfigPath removeHead(int count) {
        return new ConfigPath(ArrayUtils.removePre(this.path, count));
    }

    @Contract("-> new")
    public ConfigPath reverse() {
        String[] newPath = this.path.clone();
        ArrayUtils.reverse(newPath);
        return new ConfigPath(newPath);
    }

    public static String[] buildRaw(String rawPath) {
        ConfigPath newPath = new ConfigPath(rawPath);
        if (newPath.containVariables) {
            throw new IllegalStateException("Raw config path cannot contain variables");
        } else {
            return newPath.path;
        }
    }

//    public ConfigPath(String[] var1) {
//        this.path = Objects.requireNonNull(var1);
//        this.containVariables = false;
//    }

    public ConfigPath(String path, int... group) {
        this.path = Strings.splitArray(Strings.getGroupedOption(path, group), '.');
        this.containVariables = false;
    }

    @Contract("-> new")
    public String[] getPath() {
        return this.path.clone();
    }

    public boolean containVariables() {
        return this.containVariables;
    }

    public int length() {
        return this.path.length;
    }

    public String[] build(@NotNull List<Pair<String, String>> replacementVariables, List<String> appends) {
        if (!this.containVariables && appends == null) {
            return this.path.clone();
        } else {
            String[] path = new String[this.path.length + (appends == null ? 0 : appends.size())];

            for (int i = 0; i < this.path.length; ++i) {
                String var5 = this.path[i];
                if (this.containVariables && var5.charAt(0) == '{') {
                    var5 = var5.substring(1, var5.length() - 1);
                    Iterator<Pair<String, String>> iterator = replacementVariables.iterator();

                    Pair<String, String> pair;
                    do {
                        if (!iterator.hasNext()) {
                            throw new IllegalArgumentException("Cannot find replacement variable for '" + var5 + "' -> " + replacementVariables);
                        }
                    } while (!(pair = iterator.next()).getKey().equals(var5));

                    path[i] = pair.getValue();
                } else {
                    path[i] = var5;
                }
            }

            if (appends != null) {
                int size = appends.size();

                for (int i = 0; i < size; ++i) {
                    path[path.length - size + i] = appends.get(i);
                }
            }

            return path;
        }
    }

    public String asString() {
        StringBuilder builder = new StringBuilder();
        for (String sec : this.path) {
            builder.append(sec);
        }
        return builder.toString();
    }

    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        ConfigPath that = (ConfigPath) object;
        if (this.path.length != that.path.length) {
            return false;
        }
        boolean equals = true;
        for (int i = 0; i < this.path.length; i++) {
            if (!this.path[i].equals(that.path[i])) {
                equals = false;
                break;
            }
        }
        return equals;
    }

    public int hashCode() {
        return Arrays.hashCode(path);
    }

    public String toString() {
        return "ConfigPath{" + String.join(" -> ", this.path) + '}';
    }

    /**
     * 返回一条空的配置路径, 长度为 0
     *
     * @return The empty config path
     */
    public static ConfigPath empty() {
        return new ConfigPath(new String[0]);
    }

    /**
     * 通过枚举常量的名称来翻译一条配置路径.
     * <p>
     * 支持未替换的变量特性, 具体请见 {@link ConfigPath#fromEnum(String)}
     *
     * @param e 枚举常量
     * @see ConfigPath#fromEnum(String)
     */
    public static ConfigPath fromEnum(Enum<?> e) {
        return fromEnum(e.name());
    }

    /**
     * 通过枚举常量的名称来翻译一条配置路径.
     * <p>
     * 支持未替换的变量特性. 被两个美元符号所包围的字符串都会被翻译成一个未替换变量.
     * <p>
     * 比如 "REPLACEMENT_$VARIABLE$" 将会被解释成 "replacement.{variable}"
     * <p>
     *
     * @param name 枚举常量名称
     */
    public static ConfigPath fromEnum(String name) {
        char[] path = name.toLowerCase(Locale.ENGLISH).replace('_', '.').toCharArray();

        int[] indexes = Strings.countSepIndexes(name, '$');
        if (indexes.length % 2 != 0) {
            throw new IllegalArgumentException("Unclosed variable surround, found total" + indexes.length + " '$' of enum name: " + name);
        }

        for (int iToIndexses = 0; iToIndexses < indexes.length; iToIndexses++) {
            int iToStr = indexes[iToIndexses];
            path[iToStr] = (iToIndexses % 2 == 0 ? '{' : '}');
        }

        return new ConfigPath(String.valueOf(path));
    }

    public static SectionValidationResult validateSection(String sec) {
        if (sec == null) {
            return SectionValidationResult.INVALID;
        } else if (sec.isEmpty()) {
            return SectionValidationResult.EMPTY;
        } else {
            char[] chars = sec.toCharArray();
            int length = chars.length;

            boolean validHead = true;
            boolean validBody = true;
            boolean validEnd = true;
            boolean validSurround = (chars[0] == '{') == (chars[length - 1] == '}'); //需要两端均为花括号

            for (int i = 0; i < length; i++) {
                char ch = chars[i];

                if (i == 0) {
                    if (!((ch > 'a' && ch < 'z') || (ch > 'A' && ch < 'Z') || (ch == '{'))) { //若不是 a-z, A-Z, {
                        validHead = false;
                    }
                }
                if (i > 0 && i + 1 < length) {
                    if (!((ch > 'a' && ch < 'z') || (ch > 'A' && ch < 'Z') || (ch == '-') || (ch == '_'))) { //若不是 a-z A-Z - _
                        validBody = false;
                    }
                }
                if (i + 1 == length) {
                    if (!((ch > 'a' && ch < 'z') || (ch > 'A' && ch < 'Z') || (ch == '}'))) { //若不是 a-z, A-Z, }
                        validEnd = false;
                    }
                }
            }

            if (!(validHead && validBody && validEnd && validSurround)) {
                return SectionValidationResult.INVALID;
            } else {
                if (chars[0] == '{') {
                    return SectionValidationResult.VARIABLE;
                } else {
                    return SectionValidationResult.NORMAL;
                }
            }
        }
    }

    public enum SectionValidationResult {
        /**
         * 一般的, 如 player-name
         */
        NORMAL,
        /**
         * 是个变量, 需要被重新放置, 如 {player_name}
         */
        VARIABLE,
        /**
         * 空字符串, 很可能造成一些运行时问题, 应尽力避免
         */
        EMPTY,
        /**
         * 错误的, 如 "{{name}" 多写了个花括号, "}name{" 写反了, "na me" 包含空格
         */
        INVALID
    }

}
