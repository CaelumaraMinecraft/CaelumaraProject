package net.aurika.config.sections;

import org.checkerframework.common.value.qual.IntRange;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import net.aurika.config.path.ConfigEntry;
import net.aurika.config.path.ConfigEntryMap;
import net.aurika.config.sections.label.Label;
import net.aurika.config.sections.label.StandardLabelResolver;
import net.aurika.config.sections.format.ConfigSectionFormat;
import net.aurika.config.sections.format.StandardConfigSectionFormat;
import net.aurika.config.validation.ConfigValidator;
import net.aurika.config.validation.ConfigValidators;
import net.aurika.config.validation.ValidationContext;
import net.aurika.util.Checker;
import net.aurika.auspice.utils.Validate;
import net.aurika.auspice.utils.unsafe.Fn;
import net.aurika.auspice.utils.number.Numbers;

import java.util.*;

public class MemoryConfigSection extends AbstractConfigSection implements ConfigSection {
    protected @Nullable LinkedHashMap<String, ? extends ConfigSection> subSections;
    protected @Nullable String configuredString;
    protected @Nullable Object parsed;

    /**
     * 构造函数。
     * 创建一个路径已知但父节未知的配置节
     *
     * @param path 这个配置节的路径
     */
    public MemoryConfigSection(@NotNull ConfigEntry path) {
        super(path);
    }

    public MemoryConfigSection(@NotNull ConfigEntry path, @Nullable ConfigSection parent) {
        super(path, parent);
    }

    public MemoryConfigSection(@NotNull ConfigEntry path, @Nullable ConfigSection parent, @Nullable Object parsed) {
        super(path, parent);
        this.parsed = parsed;
    }

    public MemoryConfigSection(@NotNull ConfigEntry path, @Nullable ConfigSection parent, @Nullable Object parsed, @Nullable String configuredString) {
        super(path, parent);
        this.parsed = parsed;
        this.configuredString = configuredString;
    }

    public MemoryConfigSection(@NotNull ConfigEntry path, @Nullable ConfigSection parent, @Nullable LinkedHashMap<String, ConfigSection> subSections) {
        super(path, parent);
        this.subSections = subSections;
    }

    /**
     * 创建一个拥有子配置节的配置节, 它的标签将是 {@linkplain Label#MAP}, 会自动完成 {@linkplain MemoryConfigSection#parsed} 为一个 {@linkplain Map}
     *
     * @param parent      这个配置节的父节
     * @param name        这个配置节的键
     * @param subSections 这个配置节的子配置节
     */
    public MemoryConfigSection(@NotNull ConfigSection parent, @NotNull String name, @Nullable LinkedHashMap<String, ConfigSection> subSections) {
        super(parent, name, Label.MAP);
        this.subSections = subSections;
        Map<String, Object> parsed;

        if (subSections == null) {
            parsed = null;
        } else {
            parsed = new HashMap<>();

            for (Map.Entry<String, ConfigSection> subSection : subSections.entrySet()) {
                parsed.put(subSection.getKey(), subSection.getValue().getParsedValue());
            }
        }

        this.parsed = parsed;
    }

    /**
     * 自动决定 configuredString 所代表的 {@linkplain Label}
     *
     * @param parent           这个配置节的父节
     * @param name             这个配置节的键
     * @param configuredString 这个配置节所配置的字符串
     */
    public MemoryConfigSection(@NotNull ConfigSection parent, @NotNull String name, @Nullable String configuredString) {
        super(parent, name, StandardLabelResolver.resolveLabel(configuredString));
        this.configuredString = configuredString;
        this.parsed = configuredString;
    }

    /**
     * 强制指定 configuredString 所代表的 {@linkplain Label}
     *
     * @param parent           这个配置节的父节
     * @param key              这个配置节的键
     * @param configuredString 这个配置节所配置的字符串
     */
    public MemoryConfigSection(@NotNull ConfigSection parent, @NotNull String key, @Nullable String configuredString, @NotNull Label label) {
        super(parent, key, label);
        this.configuredString = configuredString;

        ConfigValidator validator = ConfigValidators.getValidator(label);

        if (validator != null) {
            validator.validate(new ValidationContext(this, ConfigValidators.getValidators(), new LinkedList<>()));
        }
    }

    public MemoryConfigSection(@NotNull ConfigSection parent, @NotNull String name, @NotNull Label label, @Nullable LinkedHashMap<String, ConfigSection> subSections, @Nullable Object parsed) {
        super(parent, name, label);
        this.subSections = subSections;
        this.parsed = parsed;
    }

    public MemoryConfigSection(@NotNull ConfigSection parent, @NotNull String key, @NotNull Label label, @Nullable String configuredString, @Nullable Object parsed) {
        super(parent, key, label);
        this.configuredString = configuredString;
        this.parsed = parsed;
    }

    @SuppressWarnings("UnusedReturnValue")
    private static Map<String, Object> $$getSets(Map<String, Object> container, @NotNull Map<String, ? extends ConfigSection> parents, int depthCount) {
        if (depthCount > 0) {
            for (Map.Entry<String, ? extends ConfigSection> parentEntry : parents.entrySet()) {
                ConfigSection parent = parentEntry.getValue();
                if (parent != null) {
                    container.put(parentEntry.getKey(), parent.getParsedValue());
                    if (parent.getSubSections() != null) {
                        $$getSets(container, parent.getSubSections(), depthCount - 1);
                    }
                }
            }
        }
        return container;
    }

    @SuppressWarnings("UnusedReturnValue")
    private static ConfigEntryMap<Object> $$getValues(ConfigEntryMap<Object> container, ConfigEntry rootPath, @NotNull Map<String, ? extends ConfigSection> parents, int depthCount) {
        if (depthCount > 0) {
            for (Map.Entry<String, ? extends ConfigSection> parentEntry : parents.entrySet()) {
                ConfigSection parent = parentEntry.getValue();
                if (parent != null) {
                    container.put(parent.getPath().removePrefix(rootPath), parent.getParsedValue());
                    if (parent.getSubSections() != null) {
                        $$getValues(container, rootPath, parent.getSubSections(), depthCount - 1);
                    }
                }
            }
        }
        return container;
    }

    private static Collection<String> $$getKeys(Collection<String> container, @NotNull Map<String, ? extends ConfigSection> parents, int depthCount) {
        if (depthCount > 0) {
            for (Map.Entry<String, ? extends ConfigSection> parentEntry : parents.entrySet()) {
                ConfigSection parent = parentEntry.getValue();
                if (parent != null) {
                    container.add(parentEntry.getKey());
                    if (parent.getSubSections() != null) {
                        $$getKeys(container, parent.getSubSections(), depthCount - 1);
                    }
                }
            }
        }
        return container;
    }

    @Override
    public @Nullable List<Boolean> getBooleanList(@NotNull String @NotNull [] path) {
        ConfigSection config = this.getSection(path);
        return config == null ? List.of() : config.getBooleanList();
    }

    @Override
    public @Nullable List<Integer> getIntegerList(@NotNull String @NotNull [] path) {
        ConfigSection config = this.getSection(path);
        return config == null ? List.of() : config.getIntegerList();
    }

    @Override
    public @Nullable List<Long> getLongList(@NotNull String @NotNull [] path) {
        ConfigSection config = this.getSection(path);
        return config == null ? List.of() : config.getLongList();
    }

    @Override
    public @Nullable List<Float> getFloatList(String @NotNull [] path) {
        ConfigSection config = this.getSection(path);
        return config == null ? List.of() : config.getFloatList();
    }

    @Override
    public @Nullable List<Double> getDoubleList(String @NotNull [] path) {
        ConfigSection config = this.getSection(path);
        return config == null ? List.of() : config.getDoubleList();
    }

    @Override
    public @Nullable List<String> getStringList(@NotNull String @NotNull [] path) {
        ConfigSection config = this.getSection(path);
        return config == null ? null : config.getStringList();
    }

    @Override
    public @Nullable LinkedHashMap<String, ? extends ConfigSection> getSubSections() {
        return this.subSections;
    }

    @Override
    public @Nullable ConfigSection getSubSection(@NotNull String key) {
        return this.subSections == null ? null : this.subSections.get(key);
    }

    @Override
    public @NotNull ConfigSection set(@NotNull String key, @Nullable Object parsed) {
        Checker.Arg.notNull(key, "key");
        if (this.subSections == null) {
            this.subSections = new LinkedHashMap<>();
        }
        ConfigSection oldSec = this.subSections.get(key);
        if (oldSec == null) {
            MemoryConfigSection newSub = new MemoryConfigSection(this.path.append(key), this, parsed);
            this.subSections.put(key, Fn.cast(newSub));
            return newSub;
        } else {
            return oldSec;
        }
    }

    @Override
    public @Nullable ConfigSection removeSubSection(@NotNull String key) {
        if (this.subSections != null) {
            return this.subSections.remove(key);
        }
        return null;
    }

    @Override
    public boolean hasSubSections() {
        if (this.subSections != null) {
            if (!this.subSections.isEmpty()) {
                return true;
            } else {  // empty sub sections map
                return this.configuredString == null;
            }
        } else {
            return false;
        }
    }

    @Override
    @Nullable
    public ConfigSection getSection(@NotNull String @NotNull [] path) {
        Validate.noNullElements(path, PATH_CONTAINS_NULL);

        ConfigSection section = this;
        for (String secKey : path) {
            if (section != null) {
                section = section.getSubSection(secKey);
            }
        }

        return section;
    }

    @Override
    public @NotNull ConfigSection createSection(@NotNull String @NotNull [] path) {
        Validate.noNullElements(path, PATH_CONTAINS_NULL);

        ConfigSection section = this;
        for (String subKey : path) {
            section = section.set(subKey, null);
        }

        return section;
    }

    @Override
    public @NotNull Set<String> getKeys() {
        return this.getSubSections() == null ? new HashSet<>() : this.getSubSections().keySet();
    }

    @Override
    public @NotNull Collection<String> getKeys(@IntRange(from = 1) int depth) {
        return this.subSections == null ? List.of() : $$getKeys(new ArrayList<>(), this.subSections, depth);
    }

    @Override
    public @NotNull Map<String, Object> getSets() {
        Map<String, ? extends ConfigSection> subs = this.getSubSections();
        Map<String, Object> sets = new HashMap<>();
        if (subs != null && !subs.isEmpty()) {
            for (Map.Entry<String, ? extends ConfigSection> subEntry : subs.entrySet()) {
                ConfigSection sub = subEntry.getValue();
                if (sub != null) {
                    sets.put(subEntry.getKey(), sub.getParsedValue());
                }
            }
        }
        return sets;
    }

    @Override
    public @NotNull Map<String, Object> getSets(@IntRange(from = 1) int depth) {
        Numbers.requireNonNegative(depth);
        Map<String, Object> container = new HashMap<>();
        container.put(this.key, this.parsed);
        if (this.getSubSections() != null) {
            $$getSets(container, this.getSubSections(), depth);
        }

        return container;
    }

    @Override
    public @Nullable Object getSet(@NotNull String @NotNull [] path) {
        ConfigSection cfg = this.getSection(path);
        return cfg == null ? null : cfg.getParsedValue();
    }

    @Override
    public @NotNull ConfigSection set(String @NotNull [] path, Object value) {
        ConfigSection section = this.createSection(path);
        section.set(value);
        return section;
    }

    @Override
    public ConfigEntryMap<Object> getValues(int depth) {
        ConfigEntryMap<Object> values = new ConfigEntryMap<>();
        if (this.subSections != null) {
            $$getValues(values, this.path, this.subSections, Integer.MAX_VALUE);
        }

        return values;
    }

    @Override
    public void set(Object value) {
        this.parsed = value;
        this.configuredString = Objects.toString(value);
    }

    @Override
    public Object getParsedValue() {
        return this.parsed;
    }

    @Override
    public void setParsedValue(Object parsed) {
        this.parsed = parsed;
    }

    @Override
    public @Nullable String getConfigureString() {
        return this.configuredString;
    }

    @Override
    public void setConfigureString(@Nullable String configuredString) {
        this.configuredString = configuredString;
    }

    @Override
    public Object castToObject() {  // TODO
        if (this.parsed != null) {
            return this.parsed;
        }

        if (this.subSections != null) {
            if (!this.subSections.isEmpty()) {
                Map<String, Object> map = new HashMap<>();
                for (Map.Entry<String, ? extends ConfigSection> entry : this.subSections.entrySet()) {
                    map.put(entry.getKey(), entry.getValue().castToObject());
                }
                return map;
            }
        }
        return null;
    }

    @Override
    public @Nullable Object getParsed(@NotNull String @NotNull [] path) {
        Checker.Arg.nonNullArray(path, "path", PATH_CONTAINS_NULL);
        ConfigSection config = this.getSection(path);
        return config == null ? null : config.getParsedValue();
    }

    @Override
    public <T> @Nullable T getObject(@NotNull String @NotNull [] path, @NotNull Class<T> type) {
        if (this.parsed == null) {
            return null;
        }
        if (type.isInstance(this.parsed)) {
            return Fn.cast(this.parsed);
        }
        return null;
    }

    @Override
    public @Nullable Boolean getBoolean(@NotNull String @NotNull [] path) {
        ConfigSection config = this.getSection(path);
        return config == null ? null : config.getBoolean();
    }

    @Override
    public @Nullable Byte getByte(@NotNull String @NotNull [] path) {
        ConfigSection config = this.getSection(path);
        return config == null ? null : config.getByte();
    }

    @Override
    public @Nullable Short getShort(@NotNull String @NotNull [] path) {
        ConfigSection config = this.getSection(path);
        return config == null ? null : config.getShort();
    }

    @Override
    public @Nullable Integer getInteger(@NotNull String @NotNull [] path) {
        ConfigSection config = this.getSection(path);
        return config == null ? null : config.getInteger();
    }

    @Override
    public @Nullable Long getLong(@NotNull String @NotNull [] path) {
        ConfigSection config = this.getSection(path);
        return config == null ? null : config.getLong();
    }

    @Override
    public @Nullable Float getFloat(@NotNull String @NotNull [] path) {
        ConfigSection config = this.getSection(path);
        return config == null ? null : config.getFloat();
    }

    @Override
    public @Nullable Double getDouble(@NotNull String @NotNull [] path) {
        ConfigSection config = this.getSection(path);
        return config == null ? null : config.getDouble();
    }

    @Override
    public @Nullable String getString(@NotNull String @NotNull [] path) {
        ConfigSection config = this.getSection(path);
        return config == null ? null : config.getString();
    }

    @Override
    public @Nullable List<?> getList(@NotNull String @NotNull [] path) {
        ConfigSection section = this.getSection(path);
        return section == null ? null : section.getList();
    }

    @Override
    public @Nullable Map<?, ?> getMap(String @NotNull [] path) {
        ConfigSection section = this.getSection(path);
        return section == null ? null : section.getMap();
    }

    @Override
    public @Nullable Boolean getBoolean() {
        return this.parsed instanceof Boolean b ? b : null;
    }

    @Override
    public @Nullable Character getCharacter() {
        return this.parsed instanceof Character c ? c : null;
    }

    @Override
    public @Nullable Number getNumber() {
        return this.parsed instanceof Number n ? n : null;
    }

    @Override
    public @Nullable Byte getByte() {
        return this.parsed instanceof Number n ? n.byteValue() : null;
    }

    @Override
    public @Nullable Short getShort() {
        return this.parsed instanceof Number n ? n.shortValue() : null;
    }

    @Override
    public @Nullable Integer getInteger() {
        return this.parsed instanceof Number n ? n.intValue() : null;
    }

    @Override
    public @Nullable Long getLong() {
        return this.parsed instanceof Number n ? n.longValue() : null;
    }

    @Override
    public @Nullable Float getFloat() {
        return this.parsed instanceof Number n ? n.floatValue() : null;
    }

    @Override
    public @Nullable Double getDouble() {
        return this.parsed instanceof Number n ? n.doubleValue() : null;
    }

    @Override
    public @Nullable String getString() {
        return this.parsed instanceof String s ? s : null;
    }

    @Override
    public @Nullable List<?> getList() {
        if (this.parsed instanceof Collection) {
            return new ArrayList<>((Collection<?>) this.parsed);
        }
        return null;
    }

    @Override
    public @Nullable Map<?, ?> getMap() {
        Map<String, Object> map = new HashMap<>();
        if (this.subSections != null) {
            for (Map.Entry<String, ? extends ConfigSection> entry : this.subSections.entrySet()) {
                map.put(entry.getKey(), entry.getValue().castToObject());
            }
        }
        return map;
    }

    @Override
    public @NotNull ConfigSectionFormat getSectionFormat() {
        if (this.hasSubSections()) {
            return StandardConfigSectionFormat.MAPPING;
        }
        if (this.parsed instanceof Collection<?>) {
            return StandardConfigSectionFormat.LIST;
        }
        return StandardConfigSectionFormat.SCALAR;
    }

    public @NotNull String toString() {
        return "MemoryConfigSection{"
                + "path=" + path
                + ", name='" + key + '\''
                + ", configuredString='" + configuredString + '\''
                + ", parsed=" + parsed
                + '}';
    }
}
