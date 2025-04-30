package net.aurika.common.nbt;

import net.aurika.common.validate.Validate;
import net.kyori.adventure.nbt.BinaryTag;
import net.kyori.adventure.nbt.CompoundBinaryTag;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;

public interface NBTTagCompound extends NBTTagMutableObject<Map<String, NBTTag>> {

  @Contract("_ -> new")
  static @NotNull NBTTagCompound nbtTagCompound(@NotNull CompoundBinaryTag tag) {
    Validate.Arg.notNull(tag, "tag");
    NBTTagCompound created = nbtTagComponentEmpty();
    for (String key : tag.keySet()) {
      created.put(key, NBTTag.nbtTag(tag.get(key)));
    }
    return created;
  }

  @Contract("_ -> new")
  static @NotNull NBTTagCompound nbtTagCompoundSynced(@NotNull Map<String, NBTTag> value) {
    return new NBTTagCompoundImpl(value, true);
  }

  @Contract("_ -> new")
  static @NotNull NBTTagCompound nbtTagComponentCloned(@NotNull Map<String, NBTTag> value) {
    return new NBTTagCompoundImpl(new LinkedHashMap<>(value), true);
  }

  static @NotNull NBTTagCompound nbtTagComponentEmpty() { return new NBTTagCompoundImpl(new LinkedHashMap<>(), false); }

  boolean containsKey(@NotNull String key);

  @Nullable NBTTag get(@NotNull String key);

  /**
   * Puts a sub tag, and returns a tag that keyed by the key.
   *
   * @param key    the key
   * @param subTag the sub tag
   * @return the old tag, maybe null
   */
  @Nullable NBTTag put(@NotNull String key, @NotNull NBTTag subTag);

  void putAll(@NotNull Map<String, ? extends NBTTag> map);

  /**
   * Removes a sub {@link NBTTag} from a key
   *
   * @param key the key
   * @return the sub tag has been removed
   */
  @Nullable NBTTag remove(@NotNull String key);

  @Nullable Object getAsObject(@NotNull String key);

  @Override
  default @NotNull NBTTagType<NBTTagCompound> nbtTagType() { return NBTTagType.COMPOUND; }

  @Override
  @NotNull Map<String, NBTTag> valueCopy();

  @Override
  @NotNull Map<String, NBTTag> valueRaw();

  @Override
  void valueCopy(@NotNull Map<String, NBTTag> value);

  @Override
  void valueRaw(@NotNull Map<String, NBTTag> value);

  @Override
  @NotNull CompoundBinaryTag asBinaryTag();

}

class NBTTagCompoundImpl extends NBTTagImpl implements NBTTagCompound {

  static void check(@NotNull Map<String, ? extends NBTTag> map) {
    Validate.Arg.notNull(map, "value", "value is null");
    for (NBTTag tag : map.values()) {
      if (tag.nbtTagType().id() == NBTTagId.END) {
        throw new IllegalArgumentException("Cannot add END tag to compound tag");
      }
    }
  }

  private @NotNull Map<String, NBTTag> value;

  NBTTagCompoundImpl(@NotNull Map<String, NBTTag> value, boolean check) {
    Validate.Arg.notNull(value, "value", "value is null");
    if (check) check(value);
    this.value = value;
  }

  @Override
  public @Nullable NBTTag get(@NotNull String key) { return value.get(key); }

  @Override
  public boolean containsKey(@NotNull String key) {
    return this.value.containsKey(key);
  }

  @Override
  public NBTTag put(@NotNull String key, @NotNull NBTTag subTag) {
    if (subTag.nbtTagType().id() == NBTTagId.END) {
      throw new IllegalArgumentException("Cannot add END tag to compound tag");
    } else {
      return this.value.put(key, subTag);
    }
  }

  @Override
  public void putAll(@NotNull Map<String, ? extends NBTTag> map) {
    map.forEach(this::put);
  }

  @Override
  public @Nullable NBTTag remove(@NotNull String key) {
    return this.value.remove(key);
  }

  @Override
  public @Nullable Object getAsObject(@NotNull String key) {
    NBTTag sub = this.valueRaw().get(key);
    return sub == null ? null : sub.valueAsObject();
  }

  @Override
  public @NotNull Map<String, NBTTag> valueRaw() {
    return this.value;
  }

  @Override
  public @NotNull Map<String, NBTTag> valueCopy() {
    return new LinkedHashMap<>(this.valueRaw());
  }

  @Override
  public void valueRaw(@NotNull Map<String, NBTTag> value) {
    Validate.Arg.notNull(value, "value");
    this.value = value;
  }

  @Override
  public void valueCopy(@NotNull Map<String, NBTTag> value) {
    Validate.Arg.notNull(value, "value");
    this.value = new LinkedHashMap<>(value);
  }

  @Override
  public @NotNull CompoundBinaryTag asBinaryTag() {
    Map<String, BinaryTag> tags = new LinkedHashMap<>();
    for (Map.Entry<String, ? extends NBTTag> entry : this.valueRaw().entrySet()) {
      tags.put(entry.getKey(), entry.getValue().asBinaryTag());
    }
    return CompoundBinaryTag.from(tags);
  }

  @Override
  public @NotNull String toString() {
    return this.getClass().getSimpleName() + this.value;
  }

}
