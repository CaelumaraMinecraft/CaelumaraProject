package net.aurika.configuration.sections;

import net.aurika.abstraction.conditional.ConditionChain;
import net.aurika.abstraction.conditional.ConditionalObject;
import net.aurika.auspice.text.compiler.TextCompilerSettings;
import net.aurika.auspice.text.compiler.TextObject;
import net.aurika.auspice.utils.compiler.condition.ConditionCompiler;
import net.aurika.auspice.utils.compiler.condition.ConditionVariableTranslator;
import net.aurika.auspice.utils.compiler.math.MathCompiler;
import net.aurika.configuration.path.ConfigEntry;
import net.aurika.configuration.path.ConfigEntryMap;
import net.aurika.configuration.sections.format.ConfigSectionFormat;
import net.aurika.configuration.sections.interpreter.SectionInterpreter;
import net.aurika.configuration.sections.label.Label;
import net.aurika.text.placeholders.context.PlaceholderProvider;
import net.aurika.util.Checker;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.common.value.qual.IntRange;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.time.Duration;
import java.util.*;

public class ConditionalConfigSection implements ConditionalObject, ConfigSection {

  public static final String FINAL_VALUE_SECTION_NAME = "others";
  //
  @NotNull
  protected final ConfigSection section;
  @NotNull
  protected final LinkedHashMap<ConditionCompiler.LogicalOperand, ConfigSection> branches;
  @Nullable
  protected final ConfigSection defaultValue;

  protected ConditionalConfigSection(@NotNull ConfigSection section) {
    this(section, new LinkedHashMap<>(), null);
  }

  protected ConditionalConfigSection(@NotNull ConfigSection section, @NotNull ConditionChain<ConfigSection> branches, @Nullable ConfigSection defaultValue) {
    Checker.Arg.notNull(section, "section");
    Checker.Arg.notNull(branches, "branches");

    this.section = section;
    this.branches = branches;
    this.defaultValue = defaultValue;
  }

  public static ConditionalConfigSection of(@NotNull ConfigSection section) {
    ConfigSectionFormat format = section.getSectionFormat();
    if (format.isMap()) {
      f
    }

    return null;
  }

  @Override
  public ConfigSection branch(ConditionVariableTranslator cvt) {
    Objects.requireNonNull(cvt);
    LinkedHashMap<ConditionCompiler.LogicalOperand, ConfigSection> branches = this.branches;

    for (Map.Entry<ConditionCompiler.LogicalOperand, ConfigSection> branch : branches.entrySet()) {
      if (Boolean.TRUE.equals(branch.getKey().eval(cvt))) {
        return branch.getValue();
      }
    }

    return null;
  }

  public @NotNull ConfigEntry getPath() {
    return this.section.getPath();
  }

  public @NotNull String getKey() {
    return this.section.getKey();
  }

  public @Nullable ConfigSection getParent() {
    return this.section.getParent();
  }

  public Map<String, ? extends ConfigSection> getSubSections() {
    return this.section.getSubSections();
  }

  public @Nullable ConfigSection getSubSection(@NotNull String key) {
    return this.section.getSubSection(key);
  }

  public @NotNull ConfigSection set(@NotNull String key, @Nullable Object parsed) {
    return this.section.set(key, parsed);
  }

  public @Nullable ConfigSection removeSubSection(@NotNull String key) {
    return this.section.removeSubSection(key);
  }

  public boolean hasSubSections() {
    return this.section.hasSubSections();
  }

  public @Nullable ConfigSection getSection(@NonNull String @NotNull [] path) {
    return this.section.getSection(path);
  }

  public @NotNull ConfigSection createSection(@NonNull String @NotNull [] path) {
    return this.section.createSection(path);
  }

  public @NotNull Set<String> getKeys() {
    return this.section.getKeys();
  }

  public @NotNull Collection<String> getKeys(boolean deep) {
    return this.section.getKeys(deep);
  }

  public @NotNull Collection<String> getKeys(@IntRange(from = 1) int depth) {
    return this.section.getKeys(depth);
  }

  public @NotNull Map<String, Object> getSets() {
    return this.section.getSets();
  }

  public @NotNull Map<String, Object> getSets(boolean deep) {
    return this.section.getSets(deep);
  }

  public @NotNull Map<String, Object> getSets(@IntRange(from = 1) int depth) {
    return this.section.getSets(depth);
  }

  public @Nullable Object getSet(@NotNull String @NotNull [] path) {
    return this.section.getSet(path);
  }

  public boolean isSet(@NotNull String @NotNull [] path) {
    return this.section.isSet(path);
  }

  public @NotNull ConfigSection set(@NonNull String @NotNull [] path, Object value) {
    return this.section.set(path, value);
  }

  public ConfigEntryMap<Object> getValues(boolean deep) {
    return this.section.getValues(deep);
  }

  public ConfigEntryMap<Object> getValues(@IntRange(from = 1) int depth) {
    return this.section.getValues(depth);
  }

  public void set(Object value) {
    this.section.set(value);
  }

  public Object getParsedValue() {
    return this.section.getParsedValue();
  }

  public void setParsedValue(Object parsed) {
    this.section.setParsedValue(parsed);
  }

  public @Nullable String getConfigureString() {
    return this.section.getConfigureString();
  }

  public void setConfigureString(String configure) {
    this.section.setConfigureString(configure);
  }

  public Object castToObject() {
    return this; //TODO
  }

  public @Nullable Object getParsed(@NonNull String @NotNull [] path) {
    return this.section.getParsed(path);
  }

  public Object getParsed(@NonNull String @NotNull [] path, Object def) {
    return this.section.getParsed(path, def);
  }

  public @NotNull Label getLabel() {
    return this.section.getLabel();
  }

  public void setLabel(@NotNull Label label) {
    this.section.setLabel(label);
  }

  public <T> T getObject(@NonNull String @NotNull [] path, @NotNull Class<T> type, T def) {
    return this.section.getObject(path, type, def);
  }

  public Boolean getBoolean(@NonNull String @NotNull [] path, Boolean def) {
    return this.section.getBoolean(path, def);
  }

  public Byte getByte(@NonNull String @NotNull [] path, Byte def) {
    return this.section.getByte(path, def);
  }

  public Short getShort(@NonNull String @NotNull [] path, Short def) {
    return this.section.getShort(path, def);
  }

  public Integer getInteger(@NonNull String @NotNull [] path, Integer def) {
    return this.section.getInteger(path, def);
  }

  public Long getLong(@NonNull String @NotNull [] path, Long def) {
    return this.section.getLong(path, def);
  }

  public Float getFloat(@NonNull String @NotNull [] path, Float def) {
    return this.section.getFloat(path, def);
  }

  public Double getDouble(@NonNull String @NotNull [] path, Double def) {
    return this.section.getDouble(path, def);
  }

  public String getString(@NonNull String @NotNull [] path, String def) {
    return this.section.getString(path, def);
  }

  public <T> @Nullable T getObject(@NonNull String @NotNull [] path, @NotNull Class<T> type) {
    return this.section.getObject(path, type);
  }

  public @Nullable Boolean getBoolean(@NonNull String @NotNull [] path) {
    return this.section.getBoolean(path);
  }

  public @Nullable Byte getByte(@NonNull String @NotNull [] path) {
    return this.section.getByte(path);
  }

  public @Nullable Short getShort(@NonNull String @NotNull [] path) {
    return this.section.getShort(path);
  }

  public @Nullable Integer getInteger(@NonNull String @NotNull [] path) {
    return this.section.getInteger(path);
  }

  public @Nullable Long getLong(@NonNull String @NotNull [] path) {
    return this.section.getLong(path);
  }

  public @Nullable Float getFloat(@NonNull String @NotNull [] path) {
    return this.section.getFloat(path);
  }

  public @Nullable Double getDouble(@NonNull String @NotNull [] path) {
    return this.section.getDouble(path);
  }

  public @Nullable String getString(@NonNull String @NotNull [] path) {
    return this.section.getString(path);
  }

  public @Nullable List<Boolean> getBooleanList(@NonNull String @NotNull [] path) {
    return this.section.getBooleanList(path);
  }

  public @Nullable List<Character> getCharacterList(@NotNull String @NotNull [] path) {
    return this.section.getCharacterList(path);
  }

  public @Nullable List<Byte> getByteList(@NonNull String @NotNull [] path) {
    return this.section.getByteList(path);
  }

  public @Nullable List<Short> getShortList(@NonNull String @NotNull [] path) {
    return this.section.getShortList(path);
  }

  public @Nullable List<Integer> getIntegerList(@NonNull String @NotNull [] path) {
    return this.section.getIntegerList(path);
  }

  public @Nullable List<Long> getLongList(@NonNull String @NotNull [] path) {
    return this.section.getLongList(path);
  }

  public @Nullable List<Float> getFloatList(@NonNull String @NotNull [] path) {
    return this.section.getFloatList(path);
  }

  public @Nullable List<Double> getDoubleList(@NonNull String @NotNull [] path) {
    return this.section.getDoubleList(path);
  }

  public @Nullable List<String> getStringList(@NonNull String @NotNull [] path) {
    return this.section.getStringList(path);
  }

  public @Nullable <E extends Enum<E>> List<E> getEnumList(@NotNull String @NotNull [] path, @NotNull Class<E> type) {
    return this.section.getEnumList(path, type);
  }

  public @Nullable List<?> getList(@NonNull String @NotNull [] path) {
    return this.section.getList(path);
  }

  public @Nullable Map<?, ?> getMap(@NonNull String @NotNull [] path) {
    return this.section.getMap(path);
  }

  public @Nullable <T> List<T> getList(@NonNull String @NotNull [] path, @NotNull Class<T> elementType) {
    return this.section.getList(path, elementType);
  }

  public @Nullable <K, V> Map<K, V> getMap(@NonNull String @NotNull [] path, @NotNull Class<K> keyType, @NotNull Class<V> valueType) {
    return this.section.getMap(path, keyType, valueType);
  }

  public @Nullable Duration getTime(@NotNull String @NotNull [] path, @Nullable PlaceholderProvider placeholderProvider) {
    return this.section.getTime(path, placeholderProvider);
  }

  public @Nullable <T extends Enum<T>> T getEnum(@NonNull String @NotNull [] path, Class<T> type) {
    return this.section.getEnum(path, type);
  }

  public @Nullable TextObject getText(@NonNull String @NotNull [] path) {
    return this.section.getText(path);
  }

  public @Nullable TextObject getText(@NonNull String @NotNull [] path, @NotNull TextCompilerSettings settings) {
    return this.section.getText(path, settings);
  }

  public @Nullable MathCompiler.Expression getMath(@NonNull String @NotNull [] path) {
    return this.section.getMath(path);
  }

  public @Nullable ConditionCompiler.LogicalOperand getCondition(@NonNull String @NotNull [] path) {
    return this.section.getCondition(path);
  }

  public @Nullable Boolean getBoolean() {
    return this.section.getBoolean();
  }

  public @Nullable Character getCharacter() {
    return this.section.getCharacter();
  }

  public @Nullable Number getNumber() {
    return this.section.getNumber();
  }

  public @Nullable Byte getByte() {
    return this.section.getByte();
  }

  public @Nullable Short getShort() {
    return this.section.getShort();
  }

  public @Nullable Integer getInteger() {
    return this.section.getInteger();
  }

  public @Nullable Long getLong() {
    return this.section.getLong();
  }

  public @Nullable Float getFloat() {
    return this.section.getFloat();
  }

  public @Nullable Double getDouble() {
    return this.section.getDouble();
  }

  public @Nullable String getString() {
    return this.section.getString();
  }

  public @Nullable List<?> getList() {
    return this.section.getList();
  }

  public @Nullable List<Boolean> getBooleanList() {
    return this.section.getBooleanList();
  }

  public @Nullable List<Character> getCharacterList() {
    return this.section.getCharacterList();
  }

  public @Nullable List<Number> getNumberList() {
    return this.section.getNumberList();
  }

  public @Nullable List<Byte> getByteList() {
    return this.section.getByteList();
  }

  public @Nullable List<Short> getShortList() {
    return this.section.getShortList();
  }

  public @Nullable List<Integer> getIntegerList() {
    return this.section.getIntegerList();
  }

  public @Nullable List<Long> getLongList() {
    return this.section.getLongList();
  }

  public @Nullable List<Float> getFloatList() {
    return this.section.getFloatList();
  }

  public @Nullable List<Double> getDoubleList() {
    return this.section.getDoubleList();
  }

  public @Nullable List<String> getStringList() {
    return this.section.getStringList();
  }

  public @Nullable <E extends Enum<E>> List<E> getEnumList(@NotNull Class<E> enumType) {
    return this.section.getEnumList(enumType);
  }

  public @Nullable <T> List<T> getList(Class<T> elementType) {
    return this.section.getList(elementType);
  }

  public @Nullable Map<?, ?> getMap() {
    return this.section.getMap();
  }

  public @Nullable <K, V> Map<K, V> getMap(@NotNull Class<K> keyType, @NotNull Class<V> valueType) {
    return this.section.getMap(keyType, valueType);
  }

  public @Nullable Duration getTime(@Nullable PlaceholderProvider placeholderProvider) {
    return this.section.getTime(placeholderProvider);
  }

  public @NotNull <T> List<T> siftList(@NotNull Class<T> elementType) {
    return this.section.siftList(elementType);
  }

  public @NotNull <K, V> Map<K, V> siftMap(@NotNull Class<K> keyType, @NotNull Class<V> valueType) {
    return this.section.siftMap(keyType, valueType);
  }

  public @NotNull List<Boolean> siftBooleanList() {
    return this.section.siftBooleanList();
  }

  public @NotNull List<Integer> siftIntegerList() {
    return this.section.siftIntegerList();
  }

  public @NotNull List<Long> siftLongList() {
    return this.section.siftLongList();
  }

  public @NotNull List<Float> siftFloatList() {
    return this.section.siftFloatList();
  }

  public @NotNull List<Double> siftDoubleList() {
    return this.section.siftDoubleList();
  }

  public @NotNull List<String> siftStringList() {
    return this.section.siftStringList();
  }

  public @NotNull <E extends Enum<E>> List<E> siftEnumList(Class<E> enumType) {
    return this.section.siftEnumList(enumType);
  }

  public <T> @Nullable T asObject(SectionInterpreter<T> interpreter) {
    return this.section.asObject(interpreter);
  }

  public @NotNull ConfigSection.BranchState getBranchState() {
    return this.section.getBranchState();
  }

  public @NotNull ConfigSectionFormat getSectionFormat() {
    return this.section.getSectionFormat();
  }

}
