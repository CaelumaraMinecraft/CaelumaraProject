package net.aurika.auspice.game.bukkit.config.sections;

import net.aurika.config.sections.ConfigSection;
import net.aurika.util.string.Strings;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BukkitConfigurationSection implements ConfigurationSection {

  @NotNull
  private final ConfigSection section;

  public BukkitConfigurationSection(@NotNull ConfigSection section) {
    this.section = section;
  }

  @NotNull
  public Set<String> getKeys(boolean deep) {
    return new HashSet<>(this.section.getKeys(deep));
  }

  @NotNull
  public Map<String, Object> getValues(boolean deep) {
    return this.section.getSets(deep);
  }

  public boolean contains(@NotNull String path) {
    return this.section.getPath().asString().equals(path);
  }

  public boolean contains(@NotNull String path, boolean ignoreDefault) {
    return this.section.getPath().asString().equals(path);
  }

  public boolean isSet(@NotNull String path) {
    return this.section.isSet(Strings.splitArray(path, '.'));
  }

  @Nullable
  public String getCurrentPath() {
    return this.section.getPath().asString();
  }

  @NotNull
  public String getName() {
    return this.section.getKey();
  }

  @Nullable
  public Configuration getRoot() {
    throw new UnsupportedOperationException();
  }

  @Nullable
  public ConfigurationSection getParent() {
    return this.section.getParent() == null ? null : ConfigSectionExtension.toBukkitConfigurationSection(
        this.section.getParent());
  }

  @Nullable
  public Object get(@NotNull String path) {
    return this.section.getParsed(a(path));
  }

  @Nullable
  public Object get(@NotNull String path, @Nullable Object def) {
    return this.section.getParsed(a(path), def);
  }

  public void set(@NotNull String path, @Nullable Object value) {
    this.section.set(a(path), value);
  }

  @NotNull
  public ConfigurationSection createSection(@NotNull String path) {
    return ConfigSectionExtension.toBukkitConfigurationSection(this.section.createSection(a(path)));
  }

  @NotNull
  public ConfigurationSection createSection(@NotNull String path, @NotNull Map<?, ?> map) {
    return ConfigSectionExtension.toBukkitConfigurationSection(this.section.set(a(path), map));
  }

  @Nullable
  public String getString(@NotNull String path) {
    return this.section.getString(a(path));
  }

  @Nullable
  public String getString(@NotNull String path, @Nullable String def) {

    return this.section.getString(a(path), def);
  }

  public boolean isString(@NotNull String path) {
    return this.section.getParsed(a(path)) instanceof String;
  }

  public int getInt(@NotNull String path) {
    return this.section.getInteger(a(path), 0);
  }

  public int getInt(@NotNull String path, int def) {
    return this.section.getInteger(a(path), def);
  }

  public boolean isInt(@NotNull String path) {
    return this.section.getParsed(a(path)) instanceof Number;
  }

  public boolean getBoolean(@NotNull String path) {
    return this.section.getBoolean(a(path), Boolean.FALSE);
  }

  public boolean getBoolean(@NotNull String path, boolean def) {
    return this.section.getBoolean(a(path), def);
  }

  public boolean isBoolean(@NotNull String path) {
    return this.section.getParsed(a(path)) instanceof Boolean;
  }

  public double getDouble(@NotNull String path) {
    return this.section.getDouble(a(path), 0.0);
  }

  public double getDouble(@NotNull String path, double def) {
    return this.section.getDouble(a(path), def);
  }

  public boolean isDouble(@NotNull String path) {
    return this.section.getParsed(a(path)) instanceof Number;
  }

  public long getLong(@NotNull String path) {
    return this.section.getLong(a(path), 0L);
  }

  public long getLong(@NotNull String path, long def) {
    return this.section.getLong(a(path), def);
  }

  public boolean isLong(@NotNull String path) {
    return this.section.getParsed(a(path)) instanceof Number;
  }

  @Nullable
  public List<?> getList(@NotNull String path) {
    return this.section.getList(a(path));
  }

  @Nullable
  public List<?> getList(@NotNull String path, @Nullable List<?> def) {
    throw new UnsupportedOperationException();
  }

  public boolean isList(@NotNull String path) {
    return this.section.getSet(a(path)) instanceof List<?>;
  }

  @NotNull
  public List<String> getStringList(@NotNull String path) {
    List<String> list = this.section.getStringList(a(path));
    return list == null ? List.of() : list;
  }

  @NotNull
  public List<Integer> getIntegerList(@NotNull String path) {
    List<Integer> list = this.section.getIntegerList(a(path));
    return list == null ? List.of() : list;
  }

  @NotNull
  public List<Boolean> getBooleanList(@NotNull String path) {
    List<Boolean> list = this.section.getBooleanList(a(path));
    return list == null ? List.of() : list;
  }

  @NotNull
  public List<Double> getDoubleList(@NotNull String path) {
    List<Double> list = this.section.getDoubleList(a(path));
    return list == null ? List.of() : list;
  }

  @NotNull
  public List<Float> getFloatList(@NotNull String path) {
    List<Float> list = this.section.getFloatList(a(path));
    return list == null ? List.of() : list;
  }

  @NotNull
  public List<Long> getLongList(@NotNull String path) {
    List<Long> list = this.section.getLongList(a(path));
    return list == null ? List.of() : list;
  }

  @NotNull
  public List<Byte> getByteList(@NotNull String path) {
    throw new UnsupportedOperationException("Byte list is not supported.");
  }

  @NotNull
  public List<Character> getCharacterList(@NotNull String path) {
    throw new UnsupportedOperationException("Char list is not supported.");
  }

  @NotNull
  public List<Short> getShortList(@NotNull String path) {
    throw new UnsupportedOperationException("Short list is not supported.");
  }

  @NotNull
  public List<Map<?, ?>> getMapList(@NotNull String path) {
    throw new UnsupportedOperationException();
  }

  @Nullable
  public <T> T getObject(@NotNull String path, @NotNull Class<T> clazz) {
    return this.section.getObject(a(path), clazz);
  }

  @Nullable
  public <T> T getObject(@NotNull String path, @NotNull Class<T> clazz, @Nullable T def) {
    T object = this.section.getObject(a(path), clazz);
    return object == null ? def : object;
  }

  @Nullable
  public <T extends ConfigurationSerializable> T getSerializable(@NotNull String path, @NotNull Class<T> clazz) {
    return this.section.getObject(a(path), clazz);
  }

  @Nullable
  public <T extends ConfigurationSerializable> T getSerializable(@NotNull String path, @NotNull Class<T> clazz, @Nullable T def) {
    return this.section.getObject(a(path), clazz, def);
  }

  @Nullable
  public Vector getVector(@NotNull String path) {
    return this.section.getObject(a(path), Vector.class);
  }

  @Nullable
  public Vector getVector(@NotNull String path, @Nullable Vector def) {
    return this.section.getObject(a(path), Vector.class, def);
  }

  public boolean isVector(@NotNull String path) {
    return this.section.getParsed(a(path)) instanceof Vector;
  }

  @Nullable
  public OfflinePlayer getOfflinePlayer(@NotNull String path) {
    return this.section.getObject(a(path), OfflinePlayer.class);
  }

  @Nullable
  public OfflinePlayer getOfflinePlayer(@NotNull String path, @Nullable OfflinePlayer def) {
    return this.section.getObject(a(path), OfflinePlayer.class, def);
  }

  public boolean isOfflinePlayer(@NotNull String path) {
    return this.section.getParsed(a(path)) instanceof OfflinePlayer;
  }

  @Nullable
  public ItemStack getItemStack(@NotNull String path) {
    return this.section.getObject(a(path), ItemStack.class);
  }

  @Nullable
  public ItemStack getItemStack(@NotNull String path, @Nullable ItemStack def) {
    return this.section.getObject(a(path), ItemStack.class, def);
  }

  public boolean isItemStack(@NotNull String path) {
    return this.section.getParsed(a(path)) instanceof ItemStack;
  }

  @Nullable
  public Color getColor(@NotNull String path) {
    return this.section.getObject(a(path), Color.class);
  }

  @Nullable
  public Color getColor(@NotNull String path, @Nullable Color def) {
    return this.section.getObject(a(path), Color.class, def);
  }

  public boolean isColor(@NotNull String path) {
    return this.section.getParsed(a(path)) instanceof Color;
  }

  @Nullable
  public Location getLocation(@NotNull String path) {
    return this.section.getObject(a(path), Location.class);
  }

  @Nullable
  public Location getLocation(@NotNull String path, @Nullable Location def) {
    return this.section.getObject(a(path), Location.class, def);
  }

  public boolean isLocation(@NotNull String path) {
    return this.section.getParsed(a(path)) instanceof Location;
  }

  @Nullable
  public ConfigurationSection getConfigurationSection(@NotNull String path) {
    ConfigSection section = this.section.getSection(a(path));
    return section == null ? null : ConfigSectionExtension.toBukkitConfigurationSection(section);
  }

  public boolean isConfigurationSection(@NotNull String path) {
    ConfigSection section = this.section.getSection(a(path));
    return section != null;
  }

  @Nullable
  public ConfigurationSection getDefaultSection() {
    throw new UnsupportedOperationException("Default section is not supported.");
  }

  public void addDefault(@NotNull String path, @Nullable Object value) {
    throw new UnsupportedOperationException("Default value is not supported.");
  }

  @NotNull
  public List<String> getComments(@NotNull String path) {
    throw new UnsupportedOperationException();
  }

  @NotNull
  public List<String> getInlineComments(@NotNull String path) {
    throw new UnsupportedOperationException();
  }

  public void setComments(@NotNull String path, @Nullable List<String> comments) {
    throw new UnsupportedOperationException();
  }

  public void setInlineComments(@NotNull String path, @Nullable List<String> comments) {
    throw new UnsupportedOperationException();
  }

  private static String[] a(String path) {
    return Strings.splitArray(path, '.', false);
  }

}
