package top.auspice.bukkit.config.accessor;

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
import org.snakeyaml.engine.v2.nodes.Node;
import org.snakeyaml.engine.v2.nodes.Tag;
import net.aurika.config.accessor.YamlClearlyConfigAccessor;
import top.auspice.utils.unsafe.Fn;
import top.auspice.utils.string.Strings;

import java.util.*;

public final class YamlConfigAccessorConfigurationSection implements ConfigurationSection {
    @NotNull
    private final YamlClearlyConfigAccessor accessor;

    public YamlConfigAccessorConfigurationSection(@NotNull YamlClearlyConfigAccessor yamlAccessor) {
        Objects.requireNonNull(yamlAccessor, "Config accessor cannot be null");
        this.accessor = yamlAccessor;
    }

    private static String[] splitArray(String str) {
        return Objects.requireNonNull(Strings.splitArray(str, '.', true));
    }

    private boolean splitArray(String str, Tag tag) {
        Node var10000 = this.accessor.getSection().findNode(splitArray(str));
        return (var10000 != null ? var10000.getTag() : null) == tag;
    }

    @NotNull
    public Set<String> getKeys(boolean deep) {
        return Objects.requireNonNull(this.accessor.getKeys());
    }

    @NotNull
    public Map<String, Object> getValues(boolean var1) {
        return Objects.requireNonNull(this.accessor.getEntries());
    }

    public boolean contains(@NotNull String var1) {
        Objects.requireNonNull(var1, "");
        String[] var2;
        return this.accessor.isSet(Arrays.copyOf(var2 = splitArray(var1), var2.length));
    }

    public boolean contains(@NotNull String var1, boolean var2) {
        Objects.requireNonNull(var1, "");
        String[] var3;
        return this.accessor.isSet(Arrays.copyOf(var3 = splitArray(var1), var3.length));
    }

    public boolean isSet(@NotNull String var1) {
        Objects.requireNonNull(var1);
        String[] var2 = splitArray(var1);
        return this.accessor.isSet(Arrays.copyOf(var2, var2.length));
    }

    @Nullable
    public String getCurrentPath() {
        return this.accessor.getPath().asString();
    }

    @NotNull
    public String getName() {
        return this.accessor.getSection().getKey();
    }

    @Nullable
    public Configuration getRoot() {
        throw new UnsupportedOperationException();
    }

    @Nullable
    public ConfigurationSection getParent() {
        throw new UnsupportedOperationException();
    }

    @Nullable
    public Object get(@NotNull String var1) {
        throw new UnsupportedOperationException();
    }

    @Nullable
    public Object get(@NotNull String var1, @Nullable Object var2) {
        throw new UnsupportedOperationException();
    }

    public void set(@NotNull String var1, @Nullable Object var2) {
        throw new UnsupportedOperationException();
    }

    @NotNull
    public ConfigurationSection createSection(@NotNull String var1) {
        Objects.requireNonNull(var1, "");
        throw new UnsupportedOperationException();
    }

    @NotNull
    public ConfigurationSection createSection(@NotNull String var1, @NotNull Map<?, ?> var2) {
        throw new UnsupportedOperationException();
    }

    @Nullable
    public String getString(@NotNull String name) {
        Objects.requireNonNull(name, "");
        String[] var2;
        (var2 = new String[1])[0] = name;
        return this.accessor.getString(var2);
    }

    @Nullable
    public String getString(@NotNull String var1, @Nullable String var2) {
        Objects.requireNonNull(var1, "");
        String[] var3 = new String[1];
        var3[0] = var1;
        return this.accessor.getString(var3);
    }

    public boolean isString(@NotNull String var1) {
        Objects.requireNonNull(var1, "");
        return this.splitArray(var1, Tag.STR);
    }

    public int getInt(@NotNull String var1) {
        Objects.requireNonNull(var1, "");
        String[] var2 = splitArray(var1);
        return this.accessor.getInteger(Arrays.copyOf(var2, var2.length));
    }

    public int getInt(@NotNull String var1, int var2) {
        Objects.requireNonNull(var1, "");
        String[] var3;
        return this.accessor.getInteger(Arrays.copyOf(var3 = splitArray(var1), var3.length));
    }

    public boolean isInt(@NotNull String var1) {
        Objects.requireNonNull(var1);
        return this.splitArray(var1, Tag.INT);
    }

    public boolean getBoolean(@NotNull String var1) {
        Objects.requireNonNull(var1, "");
        String[] var2;
        return this.accessor.getBoolean(Arrays.copyOf(var2 = splitArray(var1), var2.length));
    }

    public boolean getBoolean(@NotNull String var1, boolean var2) {
        Objects.requireNonNull(var1, "");
        String[] var3;
        return this.accessor.getBoolean(Arrays.copyOf(var3 = splitArray(var1), var3.length));
    }

    public boolean isBoolean(@NotNull String var1) {
        Objects.requireNonNull(var1);
        return this.splitArray(var1, Tag.BOOL);
    }

    public double getDouble(@NotNull String var1) {
        Objects.requireNonNull(var1, "");
        String[] var2;
        (var2 = new String[1])[0] = var1;
        return this.accessor.getDouble(var2);
    }

    public double getDouble(@NotNull String var1, double var2) {
        Objects.requireNonNull(var1, "");
        String[] var4;
        (var4 = new String[1])[0] = var1;
        return this.accessor.getDouble(var4);
    }

    public boolean isDouble(@NotNull String var1) {
        Objects.requireNonNull(var1, "");
        Tag var10002 = Tag.FLOAT;
        Objects.requireNonNull(var10002, "");
        return this.splitArray(var1, var10002);
    }

    public long getLong(@NotNull String var1) {
        Objects.requireNonNull(var1, "");
        String[] var2;
        return this.accessor.getLong(Arrays.copyOf(var2 = splitArray(var1), var2.length));
    }

    public long getLong(@NotNull String var1, long var2) {
        Objects.requireNonNull(var1, "");
        String[] var4;
        return this.accessor.getLong(Arrays.copyOf(var4 = splitArray(var1), var4.length));
    }

    public boolean isLong(@NotNull String var1) {
        Objects.requireNonNull(var1, "");
        throw new UnsupportedOperationException();
    }

    @Nullable
    public List<?> getList(@NotNull String var1) {
        throw new UnsupportedOperationException();
    }

    @Nullable
    public List<?> getList(@NotNull String var1, @Nullable List<?> var2) {
        throw new UnsupportedOperationException();
    }

    public boolean isList(@NotNull String var1) {
        throw new UnsupportedOperationException();
    }

    @NotNull
    public List<String> getStringList(@NotNull String path) {
        Objects.requireNonNull(path, "");
        List<String> var10000 = this.accessor.getStringList(splitArray(path));
        Objects.requireNonNull(var10000, "");
        return var10000;
    }

    @NotNull
    public List<Integer> getIntegerList(@NotNull String path) {
        Objects.requireNonNull(path);
        List<Integer> list = this.accessor.getSection().getIntegerList(splitArray(path));
        return list == null ? new ArrayList<>() : list;
    }

    @NotNull
    public List<Boolean> getBooleanList(@NotNull String path) {
        Objects.requireNonNull(path);
        List<Boolean> list = this.accessor.getSection().getBooleanList(splitArray(path));
        return list == null ? new ArrayList<>() : list;
    }

    @NotNull
    public List<Double> getDoubleList(@NotNull String path) {
        Objects.requireNonNull(path);
        List<Double> list = this.accessor.getSection().getDoubleList(splitArray(path));
        return list == null ? new ArrayList<>() : list;
    }

    @NotNull
    public List<Float> getFloatList(@NotNull String path) {
        Objects.requireNonNull(path);
        List<Float> list = this.accessor.getSection().getFloatList(splitArray(path));
        return list == null ? new ArrayList<>() : list;
    }

    @NotNull
    public List<Long> getLongList(@NotNull String path) {
        Objects.requireNonNull(path);
        List<Long> list = this.accessor.getSection().getLongList(splitArray(path));
        return list == null ? new ArrayList<>() : list;
    }

    @NotNull
    public List<Byte> getByteList(@NotNull String path) {
        Objects.requireNonNull(path);
        List<Byte> list = this.accessor.getSection().getByteList(splitArray(path));
        return list == null ? new ArrayList<>() : list;
    }

    @NotNull
    public List<Character> getCharacterList(@NotNull String path) {
        Objects.requireNonNull(path);
        List<Character> list = this.accessor.getSection().getCharacterList(splitArray(path));
        return list == null ? new ArrayList<>() : list;
    }

    @NotNull
    public List<Short> getShortList(@NotNull String path) {
        Objects.requireNonNull(path);
        splitArray(path);
        List<Short> list = this.accessor.getSection().getShortList(splitArray(path));
        return list == null ? new ArrayList<>() : list;
    }

    @NotNull
    public List<Map<?, ?>> getMapList(@NotNull String path) {
        Objects.requireNonNull(path);
        List<Map> list = this.accessor.getSection().getList(splitArray(path), Map.class);
        return list == null ? new ArrayList<>() : Fn.cast(list);
    }

    @Nullable
    public <T> T getObject(@NotNull String path, @NotNull Class<T> clazz) {
        Objects.requireNonNull(path);
        Objects.requireNonNull(clazz);
        return this.accessor.getSection().getObject(splitArray(path), clazz);
    }

    @Nullable
    public <T> T getObject(@NotNull String path, @NotNull Class<T> clazz, @Nullable T def) {
        Objects.requireNonNull(path);
        Objects.requireNonNull(clazz);
        T obj = this.accessor.getSection().getObject(splitArray(path), clazz);
        return obj == null ? def : obj;
    }

    @Nullable
    public <T extends ConfigurationSerializable> T getSerializable(@NotNull String path, @NotNull Class<T> clazz) {
        Objects.requireNonNull(path);
        Objects.requireNonNull(clazz);
        return this.accessor.getSection().getObject(splitArray(path), clazz);
    }

    @Nullable
    public <T extends ConfigurationSerializable> T getSerializable(@NotNull String var1, @NotNull Class<T> var2, @Nullable T var3) {
        Objects.requireNonNull(var1);
        Objects.requireNonNull(var2);
        throw new UnsupportedOperationException();
    }

    @Nullable
    public Vector getVector(@NotNull String var1) {
        Objects.requireNonNull(var1, "");
        throw new UnsupportedOperationException();
    }

    @Nullable
    public Vector getVector(@NotNull String var1, @Nullable Vector var2) {
        Objects.requireNonNull(var1, "");
        throw new UnsupportedOperationException();
    }

    public boolean isVector(@NotNull String var1) {
        Objects.requireNonNull(var1, "");
        throw new UnsupportedOperationException();
    }

    @Nullable
    public OfflinePlayer getOfflinePlayer(@NotNull String var1) {
        Objects.requireNonNull(var1, "");
        throw new UnsupportedOperationException();
    }

    @Nullable
    public OfflinePlayer getOfflinePlayer(@NotNull String var1, @Nullable OfflinePlayer var2) {
        Objects.requireNonNull(var1, "");
        throw new UnsupportedOperationException();
    }

    public boolean isOfflinePlayer(@NotNull String var1) {
        Objects.requireNonNull(var1, "");
        throw new UnsupportedOperationException();
    }

    @Nullable
    public ItemStack getItemStack(@NotNull String var1) {
        Objects.requireNonNull(var1, "");
        throw new UnsupportedOperationException();
    }

    @Nullable
    public ItemStack getItemStack(@NotNull String str, @Nullable ItemStack itemStack) {
        Objects.requireNonNull(str, "");
        throw new UnsupportedOperationException();
    }

    public boolean isItemStack(@NotNull String str) {
        Objects.requireNonNull(str, "");
        throw new UnsupportedOperationException();
    }

    @Nullable
    public Color getColor(@NotNull String str) {
        Objects.requireNonNull(str, "");
        throw new UnsupportedOperationException();
    }

    @Nullable
    public Color getColor(@NotNull String str, @Nullable Color color) {
        Objects.requireNonNull(str, "");
        throw new UnsupportedOperationException();
    }

    public boolean isColor(@NotNull String str) {
        Objects.requireNonNull(str, "");
        throw new UnsupportedOperationException();
    }

    @Nullable
    public Location getLocation(@NotNull String str) {
        Objects.requireNonNull(str, "");
        throw new UnsupportedOperationException();
    }

    @Nullable
    public Location getLocation(@NotNull String str, @Nullable Location pos) {
        Objects.requireNonNull(str, "");
        throw new UnsupportedOperationException();
    }

    public boolean isLocation(@NotNull String str) {
        Objects.requireNonNull(str, "");
        throw new UnsupportedOperationException();
    }

    @Nullable
    public ConfigurationSection getConfigurationSection(@NotNull String path) {
        Objects.requireNonNull(path, "");
        YamlClearlyConfigAccessor var3 = this.accessor.gotoSection(Strings.splitArray(path, '.'));
        return var3 == null ? null : new YamlConfigAccessorConfigurationSection(var3);
    }

    public boolean isConfigurationSection(@NotNull String s) {
        Objects.requireNonNull(s, "");
        throw new UnsupportedOperationException();
    }

    @Nullable
    public ConfigurationSection getDefaultSection() {
        throw new UnsupportedOperationException();
    }

    public void addDefault(@NotNull String var1, @Nullable Object obj) {
        Objects.requireNonNull(var1, "");
        throw new UnsupportedOperationException();
    }

    @NotNull
    public List<String> getComments(@NotNull String str) {
        Objects.requireNonNull(str, "");
        throw new UnsupportedOperationException();
    }

    @NotNull
    public List<String> getInlineComments(@NotNull String str) {
        Objects.requireNonNull(str, "");
        throw new UnsupportedOperationException();
    }

    public void setComments(@NotNull String str, @Nullable List<String> strings) {
        Objects.requireNonNull(str, "");
        throw new UnsupportedOperationException();
    }

    public void setInlineComments(@NotNull String str, @Nullable List<String> strings) {
        Objects.requireNonNull(str, "");
        throw new UnsupportedOperationException();
    }
}

