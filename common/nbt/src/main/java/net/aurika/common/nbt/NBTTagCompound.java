package net.aurika.common.nbt;

import net.aurika.validate.Validate;
import net.kyori.adventure.nbt.BinaryTag;
import net.kyori.adventure.nbt.CompoundBinaryTag;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

public interface NBTTagCompound extends NBTTagObject<Map<String, ? extends NBTTag>> {

  static @NotNull NBTTagCompound nbtTagCompound(@NotNull Map<String, ? extends NBTTag> value) {
    return new NBTTagCompoundImpl(value, true);
  }

  static @NotNull NBTTagCompound empty() {
    return new NBTTagCompoundImpl(new LinkedHashMap<>(), false);
  }

  @Nullable NBTTag get(@NotNull String key);

  boolean hasTag(@NotNull String key);

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
  default @NotNull NBTTagType<NBTTagCompound> nbtTagType() {
    return NBTTagType.COMPOUND;
  }

  @Override
  default @NotNull Map<String, ? extends NBTTag> value() {
    return new LinkedHashMap<>(this.rawValue());
  }

  @Override
  @NotNull Map<String, ? extends NBTTag> rawValue();

  @Override
  default void value(@NotNull Map<String, ? extends NBTTag> value) {
    Validate.Arg.notNull(value, "value");
    this.rawValue(new LinkedHashMap<>(value));
  }

  @Override
  void rawValue(@NotNull Map<String, ? extends NBTTag> value);

  @Override
  default @NotNull CompoundBinaryTag asBinaryTag() {
    Map<String, BinaryTag> tags = new LinkedHashMap<>();
    for (Map.Entry<String, ? extends NBTTag> entry : this.rawValue().entrySet()) {
      tags.put(entry.getKey(), entry.getValue().asBinaryTag());
    }
    return CompoundBinaryTag.from(tags);
  }

}

class NBTTagCompoundImpl extends NBTTagImpl implements NBTTagCompound {

  private Map<String, ? extends NBTTag> value;

  NBTTagCompoundImpl(@NotNull Map<String, ? extends NBTTag> value, boolean check) {
    Objects.requireNonNull(value, "value is null");
    if (check) {
      for (NBTTag tag : value.values()) {
        if (tag.nbtTagType().id() == NBTTagId.END) {
          throw new IllegalArgumentException("Cannot add END tag to compound tag");
        }
      }
    }

    this.value = value;
  }

  @Override
  public @Nullable NBTTag get(@NotNull String key) {
    return value.get(key);
  }

  @Override
  public boolean hasTag(@NotNull String key) {
    return this.value.containsKey(key);
  }

  @Override
  public NBTTag put(@NotNull String key, @NotNull NBTTag subTag) {
    if (subTag.nbtTagType().id() == NBTTagId.END) {
      throw new IllegalArgumentException("Cannot add END tag to compound tag");
    } else {
      return this.value.put(key, NBTTagType.castAs(subTag));
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
    NBTTag sub = this.rawValue().get(key);
    return sub == null ? null : sub.valueAsObject();
  }

  @Override
  public @NotNull Map<String, ? extends NBTTag> rawValue() {
    return this.value;
  }

  @Override
  public void rawValue(@NotNull Map<String, ? extends NBTTag> value) {
    Validate.Arg.notNull(value, "value");
    this.value = value;
  }

  @Override
  public @NotNull String toString() {
    return this.getClass().getSimpleName() + this.value;
  }

}
