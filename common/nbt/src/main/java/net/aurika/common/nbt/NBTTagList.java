package net.aurika.common.nbt;

import net.aurika.util.unsafe.fn.Fn;
import net.aurika.common.validate.Validate;
import net.kyori.adventure.nbt.BinaryTag;
import net.kyori.adventure.nbt.ListBinaryTag;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public interface NBTTagList<E extends NBTTag> extends NBTTagObject<List<E>> {

  static @NotNull NBTTagList<?> ofUnknown(@NotNull List<? extends NBTTag> value) {
    NBTTagType<?> elementType = null;

    for (NBTTag t : value) {
      if (elementType == null) {
        elementType = t.nbtTagType();
      } else if (t.nbtTagType() != elementType) {
        throw new IllegalArgumentException(
            "Element is not of type " + elementType.name() + " but " + t.nbtTagType().name());
      }
    }

    if (elementType == null) {
      return new NBTTagListImpl<>(new ArrayList<>(value));
    } else {
      return new NBTTagListImpl<>(Fn.cast(new ArrayList<>(value)), elementType);
    }
  }

  static <T extends NBTTag> @NotNull NBTTagList<T> empty(@NotNull NBTTagType<T> elementType) {
    return new NBTTagListImpl<>(new ArrayList<>(), elementType);
  }

  static @NotNull NBTTagList<?> unknownEmpty() {
    return new NBTTagListImpl<>(new ArrayList<>());
  }

  /**
   * Gets the size of this {@link NBTTagList}
   *
   * @return the size
   */
  int size();

  /**
   * Adds a element tag.
   *
   * @param elementTag the element tag
   * @throws IllegalArgumentException when the element tag is not valid
   */
  void add(@NotNull E elementTag) throws IllegalArgumentException;

  void addAll(@NotNull Collection<? extends E> tags) throws IllegalArgumentException;

  /**
   * Gets a element tag from an index.
   *
   * @param index the index
   * @throws IndexOutOfBoundsException if the index is out of range (index < 0 || index >= {@link #size()})
   */
  E get(int index) throws IndexOutOfBoundsException;

  /**
   * Gets weather this list tag has element tag type known.
   *
   * @return weather is known element tag type
   */
  boolean knownElementType();

  /**
   * Gets the element tag type.
   *
   * @return the element tag type
   * @throws IllegalStateException when element tag type is not known
   */
  @NotNull NBTTagType<E> elementType() throws IllegalStateException;

  /**
   * Casts to the new element binary tag type.
   *
   * @param elementType the new element type
   * @throws IllegalStateException when can't cast the element tag type
   */
  <T extends NBTTag> @NotNull NBTTagList<T> castElementType(@NotNull NBTTagType<T> elementType) throws IllegalStateException;

  @Override
  default @NotNull NBTTagType<NBTTagList<E>> nbtTagType() {
    return Fn.cast(NBTTagType.LIST);
  }

  @Override
  default @NotNull List<E> value() {
    return new ArrayList<>(this.rawValue());
  }

  @Override
  @NotNull List<E> rawValue();

  @Override
  default void value(@NotNull List<E> value) {
    this.rawValue(new ArrayList<>(value));
  }

  @Override
  void rawValue(@NotNull List<E> value);

  @Override
  default @NotNull BinaryTag asBinaryTag() {
    List<BinaryTag> binaryTags = new ArrayList<>();
    for (NBTTag sub : this.value()) {
      if (sub != null) {
        binaryTags.add(sub.asBinaryTag());
      }
    }
    return ListBinaryTag.listBinaryTag(elementType().toBinaryTagType(), binaryTags);
  }

}

class NBTTagListImpl<E extends NBTTag> extends NBTTagImpl implements NBTTagList<E> {

  private @NotNull List<E> value;
  private @Nullable NBTTagType<E> elementType;

  NBTTagListImpl(@NotNull List<? extends NBTTag> value) {
    Validate.Arg.notNull(value, "value");
    if (!value.isEmpty()) {
      NBTTagType<?> elementType = null;
      for (NBTTag t : value) {
        if (elementType == null) {
          elementType = t.nbtTagType();
        } else {
          if (elementType != t.nbtTagType()) {
            throw new IllegalArgumentException(
                "Element is not of type " + elementType + " but " + t.nbtTagType().name());
          }
        }
      }
    }
    this.value = (List<E>) value;
  }

  NBTTagListImpl(@NotNull List<E> value, @NotNull NBTTagType<E> elementType) {
    Validate.Arg.notNull(value, "value");
    Validate.Arg.notNull(elementType, "elementType");
    if (!value.isEmpty() && elementType == NBTTagType.END) {  // TODO
      throw new IllegalArgumentException("A non-empty list cannot be of type END");
    } else {
      this.elementType = elementType;
      this.value = value;
    }
  }

  @Override
  public int size() {
    return this.value.size();
  }

  @Override
  public void add(@NotNull E elementTag) throws IllegalArgumentException {
    if (this.elementType != null) {
      if (elementTag.nbtTagType() != this.elementType) {
        throw new IllegalArgumentException(
            "Element is not of type " + this.elementType.name() + " but " + elementTag.nbtTagType().name());
      }
    } else {
      this.elementType = (NBTTagType<E>) elementTag.nbtTagType();
    }

    this.value.add(elementTag);
  }

  @Override
  public void addAll(@NotNull Collection<? extends E> tags) {
    tags.forEach(this::add);
  }

  @Override
  public E get(int index) {
    return this.value.get(index);
  }

  @Override
  public boolean knownElementType() {
    return this.elementType != null;
  }

  @Override
  public @NotNull NBTTagType<E> elementType() throws IllegalStateException {
    if (this.elementType != null) {
      return this.elementType;
    } else {
      throw new IllegalStateException("Cannot get element tag type");
    }
  }

  @Override
  @Contract("_ -> this")
  public <T extends NBTTag> @NotNull NBTTagList<T> castElementType(@NotNull NBTTagType<T> elementType) {
    Validate.Arg.notNull(elementType, "elementType");
    if (!this.value.isEmpty()) {
      for (NBTTag e : this.value) {
        if (elementType != e.nbtTagType()) {
          throw new IllegalStateException("Element is not of type " + elementType + " but " + e.nbtTagType());  // TODO
        }
      }
    }
    this.elementType = (NBTTagType<E>) elementType;
    return (NBTTagList<T>) this;
  }

  @Override
  public @NotNull List<E> rawValue() {
    return this.value;
  }

  @Override
  public void rawValue(@NotNull List<E> value) {
    Validate.Arg.notNull(value, "value");
    for (E sub : value) {
      if (sub == null) {
        continue;
      }
      NBTTagType<?> type = sub.nbtTagType();
      if (type != this.elementType) {
        throw new IllegalArgumentException("Type mismatch, expected " + this.elementType + " but got: " + type);
      }
    }
    this.value = value;
  }

  @NotNull
  public String toString() {
    return this.getClass().getSimpleName() + this.value;
  }

}
