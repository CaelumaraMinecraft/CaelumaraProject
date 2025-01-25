package net.aurika.config.sections;

import com.google.common.base.Enums;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import net.aurika.config.path.ConfigEntry;
import net.aurika.config.path.ConfigEntryMap;
import net.aurika.config.sections.interpreter.SectionInterpreter;
import net.aurika.config.sections.label.Label;
import top.auspice.configs.texts.compiler.TextCompiler;
import top.auspice.configs.texts.compiler.TextCompilerSettings;
import top.auspice.configs.texts.compiler.TextObject;
import top.auspice.configs.texts.placeholders.context.PlaceholderProvider;
import net.aurika.utils.Checker;
import top.auspice.utils.Generics;
import top.auspice.utils.arrays.ArrayUtils;
import top.auspice.utils.compiler.condition.ConditionCompiler;
import top.auspice.utils.compiler.math.MathCompiler;
import top.auspice.utils.map.MapUtils;

import java.time.Duration;
import java.util.*;

/**
 * 实现了 {@link ConfigSection} 的一些基本逻辑.
 */
public abstract class AbstractConfigSection implements ConfigSection {
    protected final @NotNull ConfigEntry path;
    protected final @NotNull String key;
    protected ConfigSection parent;
    protected @NotNull Label label;

    /**
     * 父节未知, 路径已知
     */
    public AbstractConfigSection(ConfigEntry path) {
        this(Checker.Argument.checkNotNull(path, "path", "Path must be provided"), path.getEnd(UNKNOWN_PARENT), null);
    }

    public AbstractConfigSection(@NotNull ConfigEntry path, @NotNull String key, ConfigSection parent) {
        this(path, key, parent, Label.AUTO);
    }

    public AbstractConfigSection(@NotNull ConfigEntry path, ConfigSection parent) {
        this(path, parent, Label.AUTO);
    }

    public AbstractConfigSection(@NotNull ConfigEntry path, ConfigSection parent, @NotNull Label label) {
        this(Checker.Argument.checkNotNull(path, "path"), path.getEnd(UNKNOWN_PARENT), parent, label);
    }

    public AbstractConfigSection(@NotNull ConfigSection parent, @NotNull String key) {
        this(parent, key, Label.AUTO);
    }

    public AbstractConfigSection(@NotNull ConfigSection parent, @NotNull String key, @NotNull Label label) {
        this(Checker.Argument.checkNotNull(parent, "parent", "Parent section must be provided when auto generate path.").getPath().append(Checker.Argument.checkNotNull(key, "key", "Section name must be provided.")), key, parent, label);
    }

    protected AbstractConfigSection(@NotNull ConfigEntry path, @NotNull String key, ConfigSection parent, @NotNull Label label) {
        Checker.Argument.checkNotNull(path, "path", "Path must be provided");
        Checker.Argument.checkNotNull(key, "key", "Key must be provided");
        Checker.Argument.checkNotNull(label, "label", "Label must be provided");
        this.path = path;
        this.key = checkKeyIsPathEnd(path, key);
        this.parent = parent;
        this.label = label;
    }

    /**
     * @return The key
     * @throws IllegalArgumentException When the key isn't the end of path.
     */
    protected static String checkKeyIsPathEnd(ConfigEntry path, String key) {
        Objects.requireNonNull(path, "Config path must be provided.");
        Objects.requireNonNull(key, "Name must be provided.");

        String end = path.getEnd();

        if (end != null) {
            if (!end.equals(key)) {
                throw new IllegalArgumentException("End of path " + path.asString() + " is not equals the key: " + key);
            }
        }

        return key;
    }

    public @NotNull ConfigEntry getPath() {
        return this.path;
    }

    public @NotNull String getKey() {
        return this.key;
    }

    public ConfigSection getParent() {
        return this.parent;
    }

    public @NotNull Collection<String> getKeys(boolean deep) {
        return deep ? this.getKeys(Integer.MAX_VALUE) : this.getKeys();
    }

    public @NotNull Map<String, Object> getSets(boolean deep) {
        return deep ? this.getSets(Integer.MAX_VALUE) : this.getSets();
    }

    public boolean isSet(@NotNull String @NotNull [] path) {
        return this.getSet(path) != null;
    }

    public ConfigEntryMap<Object> getValues(boolean deep) {
        return deep ? this.getValues(Integer.MAX_VALUE) : ConfigEntryMap.of(this.getSets());
    }

    //=====  实现默认值逻辑
    @Contract("_, !null -> !null")
    public Object getParsed(@NotNull String @NotNull [] path, Object def) {
        Object o = getParsed(path);
        return o == null ? def : o;
    }

    public @NotNull Label getLabel() {
        return this.label;
    }

    public void setLabel(@NotNull Label label) {
        Checker.Argument.checkNotNull(label, "Label", "label of config section must be provided");
        this.label = label;
    }

    @Contract("_, _, !null -> !null")
    public <T> T getObject(@NotNull String @NotNull [] path, @NotNull Class<T> type, T def) {
        Checker.Argument.checkNotNullArray(path, "path");
        T t = this.getObject(path, type);
        return t == null ? def : t;
    }

    @Contract("_, !null -> !null")
    public Boolean getBoolean(@NotNull String @NotNull [] path, Boolean def) {
        Checker.Argument.checkNotNullArray(path, "path");
        Boolean b = getBoolean(path);
        return b == null ? def : b;
    }

    @Contract("_, !null -> !null")
    public Byte getByte(@NotNull String @NotNull [] path, Byte def) {
        Checker.Argument.checkNotNullArray(path, "path");
        Byte b = getByte(path);
        return b == null ? def : b;
    }

    @Contract("_, !null -> !null")
    public Short getShort(@NotNull String @NotNull [] path, Short def) {
        Checker.Argument.checkNotNullArray(path, "path");
        Short s = getShort(path);
        return s == null ? def : s;
    }

    @Contract("_, !null -> !null")
    public Integer getInteger(@NotNull String @NotNull [] path, Integer def) {
        Checker.Argument.checkNotNullArray(path, "path");

        Integer i = this.getInteger(path);
        return i == null ? def : i;
    }

    @Contract("_, !null -> !null")
    public Long getLong(@NotNull String @NotNull [] path, Long def) {
        Checker.Argument.checkNotNullArray(path, "path");

        Long l = this.getLong(path);
        return l == null ? def : l;
    }

    @Contract("_, !null -> !null")
    public Float getFloat(@NotNull String @NotNull [] path, Float def) {
        Checker.Argument.checkNotNullArray(path, "path");

        Float f = this.getFloat(path);
        return f == null ? def : f;
    }

    @Contract("_, !null -> !null")
    public Double getDouble(@NotNull String @NotNull [] path, Double def) {
        Checker.Argument.checkNotNullArray(path, "path");
        Double d = this.getDouble(path);
        return d == null ? def : d;
    }

    @Contract("_, !null -> !null")
    public String getString(@NotNull String @NotNull [] path, String def) {
        Checker.Argument.checkNotNullArray(path, "path");
        String s = getString(path);
        return s == null ? def : s;
    }

    public @Nullable List<Boolean> getBooleanList(@NotNull String @NotNull [] path) {
        Checker.Argument.checkNotNullArray(path, "path");
        return Generics.filterElementType(this.getList(path), Boolean.class);
    }

    public @Nullable List<Character> getCharacterList(@NotNull String @NotNull [] path) {
        Checker.Argument.checkNotNullArray(path, "path");
        return Generics.filterElementType(this.getList(path), Character.class);
    }

    public @Nullable List<Byte> getByteList(@NotNull String @NotNull [] path) {
        Checker.Argument.checkNotNullArray(path, "path");

        List<Number> numberList = Generics.filterElementType(this.getList(path), Number.class);
        if (numberList != null) {
            List<Byte> outList = new ArrayList<>();
            for (Number number : numberList) {
                outList.add(number.byteValue());
            }
            return outList;
        }

        return null;
    }

    public @Nullable List<Short> getShortList(@NotNull String @NotNull [] path) {
        Checker.Argument.checkNotNullArray(path, "path");

        List<Number> numberList = Generics.filterElementType(this.getList(path), Number.class);
        if (numberList != null) {
            List<Short> outList = new ArrayList<>();
            for (Number number : numberList) {
                outList.add(number.shortValue());
            }
            return outList;
        }

        return null;
    }

    public @Nullable List<Integer> getIntegerList(@NotNull String @NotNull [] path) {
        Checker.Argument.checkNotNullArray(path, "path");

        List<Number> numberList = Generics.filterElementType(this.getList(path), Number.class);
        if (numberList != null) {
            List<Integer> outList = new ArrayList<>();
            for (Number number : numberList) {
                outList.add(number.intValue());
            }
            return outList;
        }

        return null;
    }

    public @Nullable List<Long> getLongList(@NotNull String @NotNull [] path) {
        Checker.Argument.checkNotNullArray(path, "path");

        List<Number> numberList = Generics.filterElementType(this.getList(path), Number.class);
        if (numberList != null) {
            List<Long> outList = new ArrayList<>();
            for (Number number : numberList) {
                outList.add(number.longValue());
            }
            return outList;
        }

        return null;
    }

    public @Nullable List<Float> getFloatList(@NotNull String @NotNull [] path) {
        Checker.Argument.checkNotNullArray(path, "path");

        List<Number> numberList = Generics.filterElementType(this.getList(path), Number.class);
        if (numberList != null) {
            List<Float> outList = new ArrayList<>();
            for (Number number : numberList) {
                outList.add(number.floatValue());
            }
            return outList;
        }

        return null;
    }

    public @Nullable List<Double> getDoubleList(@NotNull String @NotNull [] path) {
        List<Number> numberList = Generics.filterElementType(this.getList(path), Number.class);
        if (numberList != null) {
            List<Double> outList = new ArrayList<>();
            for (Number number : numberList) {
                outList.add(number.doubleValue());
            }
            return outList;
        }

        return null;
    }

    public @Nullable List<String> getStringList(@NotNull String @NotNull [] path) {
        return Generics.filterElementType(this.getList(path), String.class);
    }

    public @Nullable <E extends Enum<E>> List<E> getEnumList(@NotNull String @NotNull [] path, @NotNull Class<E> type) {
        Objects.requireNonNull(type);
        List<String> strList = this.getStringList(path);

        if (strList != null) {
            List<E> enumList = new LinkedList<>();
            boolean allEnum = true;
            for (String str : strList) {
                com.google.common.base.Optional<E> enumOptional = Enums.getIfPresent(type, str);
                if (enumOptional.isPresent()) {
                    enumList.add(enumOptional.get());
                } else {
                    allEnum = false;
                }
            }

            if (allEnum) {
                return enumList;
            }
        }
        return null;
    }

    public <T> @Nullable List<T> getList(@NotNull String @NotNull [] path, @NotNull Class<T> elementType) {
        Checker.Argument.checkNotNull(elementType, "elementType");
        Checker.Argument.checkNotNullArray(path, "path");
        return Generics.filterElementType(this.getList(path), elementType);
    }

    public <K, V> @Nullable Map<K, V> getMap(@NotNull String @NotNull [] path, @NotNull Class<K> keyType, @NotNull Class<V> valueType) {
        Checker.Argument.checkNotNull(keyType, "keyType");
        Checker.Argument.checkNotNull(valueType, "valueType");
        Checker.Argument.checkNotNullArray(path, "path");
        return Generics.filterKVType(this.getMap(path), keyType, valueType);
    }

    public <T extends Enum<T>> @Nullable T getEnum(@NotNull String @NotNull [] path, Class<T> type) {
        Checker.Argument.checkNotNull(type, "type");
        Checker.Argument.checkNotNullArray(path, "path");
        String s = this.getString(path);
        if (s != null) {
            return Enums.getIfPresent(type, s).orNull();
        }
        return null;
    }

    public @Nullable TextObject getText(@NotNull String @NotNull [] path) {
        Checker.Argument.checkNotNullArray(path, "path");
        return this.getText(path, TextCompiler.defaultSettings());
    }

    public @Nullable TextObject getText(@NotNull String @NotNull [] path, @NotNull TextCompilerSettings settings) {
        Checker.Argument.checkNotNull(settings, "settings", "TextCompiler settings cannot be null");
        Checker.Argument.checkNotNullArray(path, "path");
        String str = this.getString(path);
        return str == null ? null : TextCompiler.compile(str, settings);
    }

    public @Nullable MathCompiler.Expression getMath(@NotNull String @NotNull [] path) {
        Checker.Argument.checkNotNullArray(path, "path");
        String str = this.getString(path);
        return str == null ? null : MathCompiler.compile(str);
    }

    public @Nullable ConditionCompiler.LogicalOperand getCondition(@NotNull String @NotNull [] path) {
        Checker.Argument.checkNotNullArray(path, "path");
        String str = this.getString(path);
        return str == null ? null : ConditionCompiler.compile(str).evaluate();
    }

    public @Nullable List<Boolean> getBooleanList() {
        return Generics.filterElementType(this.getList(), Boolean.class);
    }

    public @Nullable List<Character> getCharacterList() {
        return Generics.filterElementType(this.getList(), Character.class);
    }

    public @Nullable List<Byte> getByteList() {
        List<Number> numberList = Generics.filterElementType(this.getList(), Number.class);
        if (numberList != null) {
            List<Byte> outList = new ArrayList<>();
            for (Number number : numberList) {
                outList.add(number.byteValue());
            }
            return outList;
        }

        return null;
    }

    public @Nullable List<Short> getShortList() {
        List<Number> numberList = Generics.filterElementType(this.getList(), Number.class);
        if (numberList != null) {
            List<Short> outList = new ArrayList<>();
            for (Number number : numberList) {
                outList.add(number.shortValue());
            }
            return outList;
        }

        return null;
    }

    public @Nullable List<Integer> getIntegerList() {
        List<Number> numberList = Generics.filterElementType(this.getList(), Number.class);
        if (numberList != null) {
            List<Integer> outList = new ArrayList<>();
            for (Number number : numberList) {
                outList.add(number.intValue());
            }
            return outList;
        }

        return null;
    }

    public @Nullable List<Long> getLongList() {
        List<Number> numberList = Generics.filterElementType(this.getList(), Number.class);
        if (numberList != null) {
            List<Long> outList = new ArrayList<>();
            for (Number number : numberList) {
                outList.add(number.longValue());
            }
            return outList;
        }

        return null;
    }

    public @Nullable List<Float> getFloatList() {
        List<Number> numberList = Generics.filterElementType(this.getList(), Number.class);
        if (numberList != null) {
            List<Float> outList = new ArrayList<>();
            for (Number number : numberList) {
                outList.add(number.floatValue());
            }
            return outList;
        }

        return null;
    }

    public @Nullable List<Double> getDoubleList() {
        List<Number> numberList = Generics.filterElementType(this.getList(), Number.class);
        if (numberList != null) {
            List<Double> outList = new ArrayList<>();
            for (Number number : numberList) {
                outList.add(number.doubleValue());
            }
            return outList;
        }

        return null;
    }

    public @Nullable List<Number> getNumberList() {
        return Generics.filterElementType(this.getList(), Number.class);
    }

    public @Nullable List<String> getStringList() {
        return Generics.filterElementType(this.getList(), String.class);
    }

    public @Nullable <E extends Enum<E>> List<E> getEnumList(@NotNull Class<E> enumType) {
        Checker.Argument.checkNotNull(enumType, "enumType");

        List<String> strList = this.getStringList();
        if (strList != null) {
            List<E> enums = new LinkedList<>();

            boolean allEnum = true;
            for (String eStr : strList) {
                com.google.common.base.Optional<E> eOptional = Enums.getIfPresent(enumType, eStr);
                if (eOptional.isPresent()) {
                    enums.add(eOptional.get());
                } else {
                    allEnum = false;
                }
            }

            if (allEnum) {
                return enums;
            }
        }
        return null;
    }

    public @Nullable <T> List<T> getList(Class<T> elementType) {
        Objects.requireNonNull(elementType);
        return Generics.filterElementType(this.getList(), elementType);
    }

    public @Nullable <K, V> Map<K, V> getMap(@NotNull Class<K> keyType, @NotNull Class<V> valueType) {
        Objects.requireNonNull(keyType);
        Objects.requireNonNull(valueType);
        return Generics.filterKVType(this.getMap(), keyType, valueType);
    }

    public @NotNull <T> List<T> siftList(@NotNull Class<T> elementType) {
        List<?> list = this.getList();
        return list == null ? new ArrayList<>() : ArrayUtils.typeFilter(list, new ArrayList<>(), elementType, false);
    }

    public @NotNull <K, V> Map<K, V> siftMap(@NotNull Class<K> keyType, @NotNull Class<V> valueType) {
        Map<?, ?> map = this.getMap();
        return map == null ? new HashMap<>() : MapUtils.filter(map, new HashMap<>(), keyType, false, valueType, false);
    }

    public @NotNull List<Boolean> siftBooleanList() {
        List<?> list = this.getList();
        return list == null ? new ArrayList<>() : ArrayUtils.typeFilter(list, new ArrayList<>(), Boolean.class, true);
    }

    public @NotNull List<Integer> siftIntegerList() {
        List<?> list = this.getList();
        return list == null ? new ArrayList<>() : ArrayUtils.typeFilter(list, new ArrayList<>(), Integer.class, true);
    }

    public @NotNull List<Long> siftLongList() {
        List<?> list = this.getList();
        return list == null ? new ArrayList<>() : ArrayUtils.typeFilter(list, new ArrayList<>(), Long.class, true);
    }

    public @NotNull List<Float> siftFloatList() {
        List<?> list = this.getList();
        return list == null ? new ArrayList<>() : ArrayUtils.typeFilter(list, new ArrayList<>(), Float.class, true);
    }

    public @NotNull List<Double> siftDoubleList() {
        List<?> list = this.getList();
        return list == null ? new ArrayList<>() : ArrayUtils.typeFilter(list, new ArrayList<>(), Double.class, true);
    }

    public @NotNull List<String> siftStringList() {
        List<?> list = this.getList();
        return list == null ? new ArrayList<>() : ArrayUtils.typeFilter(list, new ArrayList<>(), String.class, true);
    }

    public <E extends Enum<E>> @NotNull List<E> siftEnumList(Class<E> enumType) {
        Checker.Argument.checkNotNull(enumType, "enumType");
        List<?> list = this.getList();
        return list == null ? new ArrayList<>() : ArrayUtils.typeFilter(list, new ArrayList<>(), enumType, true);
    }

    public @NotNull ConfigSection.BranchState getBranchState() {
        if (this.getKey().equals(ROOT)) {
            return BranchState.ROOT;
        } else {
            if (this.hasSubSections()) {
                return BranchState.MIDDLE;
            } else {
                return BranchState.END;
            }
        }
    }

    public Duration getTime() {
        return this.getTime(PlaceholderProvider.EMPTY);
    }

    public @Nullable Duration getTime(@Nullable PlaceholderProvider placeholderProvider) {
        try {
            return SectionInterpreter.getTime(this, placeholderProvider);
        } catch (RuntimeException e) {
            return null;
        }
    }

    public Duration getTime(String[] path) {
        return this.getTime(path, PlaceholderProvider.EMPTY);
    }

    public @Nullable Duration getTime(String @NotNull [] path, @Nullable PlaceholderProvider placeholderProvider) {
        try {
            return SectionInterpreter.getTime(this.getSection(path), placeholderProvider);
        } catch (RuntimeException e) {
            return null;
        }
    }

    public @Nullable <T> T asObject(SectionInterpreter<T> interpreter) {
        return interpreter.parse(this);
    }
}
