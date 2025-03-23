package net.aurika.config.sections;

import net.aurika.config.path.ConfigEntry;
import net.aurika.config.path.ConfigEntryMap;
import net.aurika.config.sections.format.ConfigSectionFormat;
import net.aurika.config.sections.interpreter.SectionInterpreter;
import net.aurika.config.sections.label.Label;
import org.checkerframework.common.value.qual.IntRange;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 配置节.
 * <p>用于表达某配置文件中的一部分, 由键(key) 与 根(root) 构成: </p>
 * <p>键为一个字符串, 并且不应为 null. 若这个配置节作为根存在, 则键为 {@link ConfigSection#ROOT}
 * <p>根有两种形式: </p>
 * <p>一种是这个配置节拥有子节, 此时根为字符串键对子配置节值的映射.</p>
 * <p>另一种是这个配置节直接对应一个值, 这个配置节没有子节.</p>
 * <p>此外, 这个配置节应当提供路径(在需要的时候), 路径的最后一节的值( {@linkplain ConfigEntry#getEnd()} )应等于这个配置节的键(这个配置节作为根存在除外).
 * 若这个配置节作为配置文件根存在, 路径应为 {@link ConfigEntry#empty()}/. </p>
 * <p>这是一个示例配置文件：</p>
 * <blockquote><pre>
 * pets:
 *   cat:
 *     name: Pelmen
 *     gender: male
 *     breed: sphinx
 *
 *   dog:
 *     name: Jeason
 *
 * rooms:
 *   washroom:
 *     posX: 30
 *     posY: 25
 *   livingRoom2:
 *     posX: 25
 *     posY: 12
 * </pre></blockquote>
 * 这个配置文件的根配置节的键为 {@link ConfigSection#ROOT}, 路径为 {@link ConfigEntry#empty()}, 子节点为 pets 和 rooms.
 * <p>
 * 从根节点开始读取, 读取路径为 {@code pets.dog.name} 的配置, 将会得到一个 {@linkplain String} 对象, 值为 "Jeason"
 */
public interface ConfigSection {
    /**
     * 不建议使用.
     * <p>
     * 考虑到某些 {@linkplain ConfigSection} 可能只用于向下读取, 不需要父节特性
     */
    String UNKNOWN_PARENT = "<([<unknown parent>])>";
    String ROOT = "[({<ROOT>})]";

    String NULL_CONFIGURED_STRING = "[({No configured String})]";

    String EMPTY_CONFIGURED_STRING = "[({Empty configured String})]";
    String PATH_CONTAINS_NULL = "The provided config path contains null value";

    /**
     * 获取当前配置节的绝对路径.
     * <p>
     * 若是一个配置文件的根, 应返回 {@link ConfigEntry#empty()}
     */
    @NotNull ConfigEntry getPath();

    @NotNull String getKey();

    /**
     * 获取父节.
     *
     * @return {@code null} 当作为一个配置文件的根节点时
     */
    ConfigSection getParent();

    /**
     * 获取这个配置节的子节
     */
    Map<String, ? extends ConfigSection> getSubSections();

    /**
     * 通过子节的键来获取子节
     *
     * @param key 子节的键
     */
    @Nullable ConfigSection getSubSection(@NotNull String key);

    /**
     * <p>
     * 增加一子配置节. 若子配置节存在, 则返回已有的子配置节,
     * </p><p>
     * 应当自动完成子配置节的 {@code name}, {@code path} 属性
     * </p>
     *
     * @param key    子配置节的键
     * @param parsed 子配置节所配置的值
     * @return 已有的或新增的子配置节
     */
    @NotNull ConfigSection set(@NotNull String key, @Nullable Object parsed);

    /**
     * 移除一个子配置节
     * Remove a direct children section
     *
     * @param key 要移除的子配置节的键
     * @return 移除的子配置节, 若移除了一个不存在的子配置节时, 返回 {@code null}
     */
    @Nullable ConfigSection removeSubSection(@NotNull String key);

    boolean hasSubSections();

    /**
     * 通过路径获取配置段, 若此路径上不存在对应配置段则返回 <code>null</code>
     *
     * @param path 配置路径
     * @return 获取的路径
     */
    @Nullable ConfigSection getSection(@NotNull String @NotNull [] path);

    /**
     * 创建一个新的配置节
     *
     * @param path 新节点相对于当前节点的相对路径
     * @return 创造的配置段
     */
    @NotNull ConfigSection createSection(@NotNull String @NotNull [] path);

    Set<String> getKeys();

    Collection<String> getKeys(boolean deep);

    Collection<String> getKeys(@IntRange(from = 1) int depth);

    @NotNull Map<String, Object> getSets();

    @NotNull Map<String, Object> getSets(boolean deep);

    /**
     * 获取当前配置节一定深度下子配置节的配置完成值
     *
     * @param depth 深度
     * @return 一个键为配置节名称, 值为配置节设置值的 {@link Map}
     */
    @NotNull Map<String, Object> getSets(@IntRange(from = 1) int depth);

    @Nullable Object getSet(@NotNull String @NotNull [] path);

    boolean isSet(@NotNull String @NotNull [] path);

    @NotNull ConfigSection set(@NotNull String @NotNull [] path, Object value);

    ConfigEntryMap<Object> getValues(boolean deep);

    ConfigEntryMap<Object> getValues(@IntRange(from = 1) int depth);

    /**
     * 设置这个配置节的值.
     * 应该自动完成 configuredString 和 label 属性
     *
     * @param value 值
     */
    void set(Object value);

    /**
     * 返回这个配置节解析完成的对象
     *
     * @return 解析完的对象
     */
    Object getParsedValue();

    /**
     * 设置该配置节对应的对象
     *
     * @param parsed 解析完的对象
     */
    void setParsedValue(Object parsed);

    /**
     * 获取这个配置段在配置文件中配置的字符串, 如下:
     * <pre>
     *     pre:
     *       type: TEST
     *       description: "Hmm.."
     *       constants:
     *         mapping: "{are: cv}"
     *                   ^^^^^^^^^
     * </pre>
     */
    @Nullable String getConfigureString();

    void setConfigureString(String configure);

    /**
     * 智能转换为一个 java 对象
     */
    Object castToObject();

    /**
     * 通过路径获取配置段, 若此路径上不存在对应配置段则返回 <code>null</code>
     *
     * @param path 配置路径
     * @return 获取的路径
     */
    @Nullable
    default ConfigSection getConfig(String[] path) {
        return this.getSection(path);
    }

    /**
     * 路径不存在, 或在配置文件中对应节点本身就设置为 null 时, 返回 null
     */
    @Nullable Object getParsed(@NotNull String @NotNull [] path);

    @Contract("_, !null -> !null")
    Object getParsed(@NotNull String @NotNull [] path, Object def);

    /**
     * 获取当前配置节值的标签
     *
     * @return 配置节值的标签
     */
    @NotNull Label getLabel();

    void setLabel(@NotNull Label label);

    //======= Path getter Methods with default value
    @Contract("_, _, !null -> !null")
    <T> T getObject(@NotNull String @NotNull [] path, @NotNull Class<T> type, T def);

    @Contract("_, !null -> !null")
    Boolean getBoolean(@NotNull String @NotNull [] path, Boolean def);

    @Contract("_, !null -> !null")
    Byte getByte(@NotNull String @NotNull [] path, Byte def);

    @Contract("_, !null -> !null")
    Short getShort(@NotNull String @NotNull [] path, Short def);

    @Contract("_, !null -> !null")
    Integer getInteger(@NotNull String @NotNull [] path, Integer def);

    @Contract("_, !null -> !null")
    Long getLong(@NotNull String @NotNull [] path, Long def);

    @Contract("_, !null -> !null")
    Float getFloat(@NotNull String @NotNull [] path, Float def);

    @Contract("_, !null -> !null")
    Double getDouble(@NotNull String @NotNull [] path, Double def);

    @Contract("_, !null -> !null")
    String getString(@NotNull String @NotNull [] path, String def);

    //====== Path getter methods

    @Nullable <T> T getObject(@NotNull String @NotNull [] path, @NotNull Class<T> type);

    @Nullable Boolean getBoolean(@NotNull String @NotNull [] path);

    @Nullable Byte getByte(@NotNull String @NotNull [] path);

    @Nullable Short getShort(@NotNull String @NotNull [] path);

    @Nullable Integer getInteger(@NotNull String @NotNull [] path);

    @Nullable Long getLong(@NotNull String @NotNull [] path);

    @Nullable Float getFloat(@NotNull String @NotNull [] path);

    @Nullable Double getDouble(@NotNull String @NotNull [] path);

    @Nullable String getString(@NotNull String @NotNull [] path);

    @Nullable <T extends Enum<T>> T getEnum(@NotNull String @NotNull [] path, Class<T> type);

//    @Nullable TextObject getText(@NotNull String @NotNull [] path);
//
//    @Nullable TextObject getText(@NotNull String @NotNull [] path, @NotNull TextCompilerSettings settings);
//
//    @Nullable MathCompiler.Expression getMath(@NotNull String @NotNull [] path);
//
//    @Nullable ConditionCompiler.LogicalOperand getCondition(@NotNull String @NotNull [] path);

    @Nullable List<?> getList(@NotNull String @NotNull [] path);

    @Nullable <T> List<T> getList(@NotNull String @NotNull [] path, @NotNull Class<T> elementType);

    @Nullable List<Boolean> getBooleanList(@NotNull String @NotNull [] path);

    @Nullable List<Character> getCharacterList(@NotNull String @NotNull [] path);

    @Nullable List<Byte> getByteList(@NotNull String @NotNull [] path);

    @Nullable List<Short> getShortList(@NotNull String @NotNull [] path);

    @Nullable List<Integer> getIntegerList(@NotNull String @NotNull [] path);

    @Nullable List<Long> getLongList(@NotNull String @NotNull [] path);

    @Nullable List<Float> getFloatList(@NotNull String @NotNull [] path);

    @Nullable List<Double> getDoubleList(@NotNull String @NotNull [] path);

    @Nullable List<String> getStringList(@NotNull String @NotNull [] path);

    @Nullable <E extends Enum<E>> List<E> getEnumList(@NotNull String @NotNull [] path, @NotNull Class<E> type);

    @Nullable Map<?, ?> getMap(@NotNull String @NotNull [] path);

    @Nullable <K, V> Map<K, V> getMap(@NotNull String @NotNull [] path, @NotNull Class<K> keyType, @NotNull Class<V> valueType);

//    @Nullable Duration getTime(@NotNull String @NotNull [] path, @Nullable PlaceholderProvider placeholderProvider);


    // ==== direct get methods

    @Nullable Boolean getBoolean();

    @Nullable Character getCharacter();

    @Nullable Number getNumber();

    @Nullable Byte getByte();

    @Nullable Short getShort();

    @Nullable Integer getInteger();

    @Nullable Long getLong();

    @Nullable Float getFloat();

    @Nullable Double getDouble();

    @Nullable String getString();

    @Nullable List<?> getList();

    /**
     * 筛选这个配置节配置的列表.
     * 若这个配置节不是配置为一个列表, 或配置的列表内容不全是需要的类型, 均返回 null.
     *
     * @param elementType 列表项目类型
     * @return 配置的列表 (当配置的列表全部为指定类型时)
     */
    @Nullable <T> List<T> getList(Class<T> elementType);

    @Nullable List<Boolean> getBooleanList();

    @Nullable List<Character> getCharacterList();

    @Nullable List<Number> getNumberList();

    @Nullable List<Byte> getByteList();

    @Nullable List<Short> getShortList();

    @Nullable List<Integer> getIntegerList();

    @Nullable List<Long> getLongList();

    @Nullable List<Float> getFloatList();

    @Nullable List<Double> getDoubleList();

    @Nullable List<String> getStringList();

    @Nullable <E extends Enum<E>> List<E> getEnumList(@NotNull Class<E> enumType);

    @Nullable Map<?, ?> getMap();

    @Nullable <K, V> Map<K, V> getMap(@NotNull Class<K> keyType, @NotNull Class<V> valueType);

//    @Nullable Duration getTime(@Nullable PlaceholderProvider placeholderProvider);

    // =====  sift methods

    @NotNull <T> List<T> siftList(@NotNull Class<T> elementType);

    @NotNull <K, V> Map<K, V> siftMap(@NotNull Class<K> keyType, @NotNull Class<V> valueType);

    /**
     * 假如在此配置节配置了一个列表, 仅筛选其中为布尔值的列表项目
     */
    @NotNull List<Boolean> siftBooleanList();

    @NotNull List<Integer> siftIntegerList();

    @NotNull List<Long> siftLongList();

    @NotNull List<Float> siftFloatList();

    @NotNull List<Double> siftDoubleList();

    @NotNull List<String> siftStringList();

    @NotNull <E extends Enum<E>> List<E> siftEnumList(Class<E> enumType);

    @Nullable <T> T asObject(SectionInterpreter<T> interpreter);

    //======= util methods

    @NotNull
    default String serializeToString_test() {
        StringBuilder str;

        ConfigSection section = this;
        Map<String, ? extends ConfigSection> subSections = this.getSubSections();

        if (subSections != null) {

            str = new StringBuilder(this.getKey() + ':');

            if (subSections.isEmpty()) {
                return str + "{}";
            }

            str = new StringBuilder(this.getKey() + ":{");
            for (ConfigSection subSec : subSections.values()) {
                str.append(subSec.serializeToString_test()).append(';');   //TODO 末尾分号
            }
            str.append('}');
        } else {
            str = new StringBuilder(this.getKey() + ":" + ConfigSection.a(this.getConfigureString()));
        }

        return str.toString();
    }

    private static String a(@Nullable String cfgStr) {
        if (cfgStr == null) {
            return NULL_CONFIGURED_STRING;
        }
        if (cfgStr.isEmpty()) {
            return EMPTY_CONFIGURED_STRING;
        }
        return '"' + cfgStr + '"';
    }

    @NotNull ConfigSection.BranchState getBranchState();

    @NotNull ConfigSectionFormat getSectionFormat();

    enum BranchState {
        /**
         * 作为一个配置文件的根存在, 这个配置节的名称为 {@linkplain ConfigSection#ROOT}, 路径为 {@linkplain ConfigEntry#empty()}.
         * <s>
         * 感觉就是个大奋 (
         */
        ROOT,
        /**
         * 中间段
         */
        MIDDLE,
        /**
         * 配置文件的末段
         */
        END,
    }
}
