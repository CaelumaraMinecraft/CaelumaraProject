package net.aurika.common.nbt;

import net.aurika.common.annotation.container.ThrowOnAbsent;
import net.aurika.common.validate.Validate;
import net.kyori.adventure.nbt.BinaryTag;
import net.kyori.adventure.nbt.ListBinaryTag;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import static net.aurika.common.nbt.Internal.cast;

public interface NBTTagList<E extends NBTTag> extends NBTTagMutableObject<List<E>> {

  @Contract("_ -> new")
  static @NotNull NBTTagList<?> nbtTagList(@NotNull ListBinaryTag tag) {
    Validate.Arg.notNull(tag, "tag");
    NBTTagList<?> created = nbtTagListEmpty();
    tag.stream().forEach(e -> created.add(cast(e)));
    return created;
  }

  @Contract("_ -> new")
  static <E extends NBTTag> @NotNull NBTTagList<E> nbtTagList(@NotNull NBTTagType<E> elementType) {
    return new NBTTagListImpl<>(elementType);
  }

  /**
   * Creates a {@link NBTTagList}. It will auto infer the element type.
   *
   * @param value the tag value
   * @return the created {@link NBTTagList}
   */
  @Contract("_ -> new")
  static @NotNull NBTTagList<?> nbtTagListSynced(@NotNull List<? extends NBTTag> value) {
    return new NBTTagListImpl<>(value);
  }

  @Contract(" -> new")
  static @NotNull NBTTagList<?> nbtTagListEmpty() {
    return new NBTTagListImpl<>(new LinkedList<>());
  }

  /**
   * Gets the size of this {@link NBTTagList}
   *
   * @return the size
   */
  int size();

  /**
   * Gets if the element type of the nbt tag list is volatile, it's volatile in cases of:
   * When the {@link #size() size} is {@code 0}.
   *
   * @return is volatile element type
   */
  boolean volatileElementType();

  boolean hasDefiniteElementType();

  /**
   * Specifies the element type and returns this nbt tag.
   *
   * @throws IllegalStateException when the element type is not volatile
   */
  @Contract(value = "_ -> this", mutates = "this")
  <NE extends NBTTag> NBTTagList<NE> specifyElementType(@NotNull NBTTagType<NE> elementType) throws IllegalStateException;

  /**
   * Gets the definite element type of the nbt tag list.
   *
   * @return the definite element type
   * @throws IllegalStateException when this nbt tag list doesn't have a definite element type
   */
  @ThrowOnAbsent
  @NotNull NBTTagType<E> definiteElementType() throws IllegalStateException;

  /**
   * Adds an element tag. Will check the type.
   *
   * @param tag the element tag
   * @throws IllegalArgumentException when the element tag type is not equals the {@link #definiteElementType() definite element type}
   */
  void add(@NotNull E tag) throws IllegalArgumentException;

  void addAll(@NotNull Collection<? extends E> tags) throws IllegalArgumentException;

  /**
   * Gets an element tag from the index.
   *
   * @param index the index
   * @throws IndexOutOfBoundsException if the index is out of range (index < 0 || index >= {@link #size()})
   */
  E get(int index) throws IndexOutOfBoundsException;

  /**
   * Gets the element tag type.
   *
   * @return the element tag type
   * @throws IllegalStateException when the element tag type is not known
   */
  @NotNull NBTTagType<E> elementType() throws IllegalStateException;

  /**
   * Casts to the new element binary tag type.
   *
   * @param elementType the new element type
   * @throws IllegalStateException when can't cast the element tag type
   */
  <T extends NBTTag> @NotNull NBTTagList<T> changeElementType(@NotNull NBTTagType<T> elementType) throws IllegalStateException;

  @Override
  default @NotNull NBTTagType<NBTTagList<E>> nbtTagType() {
    return cast(NBTTagType.LIST);
  }

  @Override
  default @NotNull List<E> valueCopy() {
    return new ArrayList<>(this.valueRaw());
  }

  @Override
  @NotNull List<E> valueRaw();

  @Override
  default void valueCopy(@NotNull List<E> value) {
    this.valueRaw(new ArrayList<>(value));
  }

  @Override
  void valueRaw(@NotNull List<E> value);

  @Override
  default @NotNull BinaryTag asBinaryTag() {
    List<BinaryTag> binaryTags = new ArrayList<>();
    for (NBTTag sub : this.valueCopy()) {
      if (sub != null) {
        binaryTags.add(sub.asBinaryTag());
      }
    }
    return ListBinaryTag.listBinaryTag(elementType().advtrBinaryTagType(), binaryTags);
  }

}

class NBTTagListImpl<E extends NBTTag> extends NBTTagImpl implements NBTTagList<E> {

  static @Nullable NBTTagType<?> inferElementType(@NotNull List<? extends NBTTag> nbtTagList) {
    Validate.Arg.notNull(nbtTagList, "nbtTagList");
    NBTTagType<?> inferredType = null;
    if (nbtTagList.isEmpty()) {
      return null;
    }
    for (NBTTag t : nbtTagList) {
      if (inferredType == null) {
        inferredType = t.nbtTagType();
      } else {
        if (t.nbtTagType() != inferredType) {
          throw new IllegalArgumentException(
              "Element is not of type " + inferredType + " but " + t.nbtTagType().name());
        }
      }
    }
    return inferredType;
  }

  private @NotNull List<E> value;
  private @Nullable NBTTagType<E> elementType;

  NBTTagListImpl(@NotNull NBTTagType<E> initElementType) {
    this.value = new LinkedList<>();
    this.elementType = initElementType;
  }

  NBTTagListImpl(@NotNull List<? extends NBTTag> value) {
    Validate.Arg.notNull(value, "value");
    this.value = (List<E>) value;
    this.elementType = (NBTTagType<E>) inferElementType(value);
  }

  NBTTagListImpl(@NotNull List<E> value, @NotNull NBTTagType<E> elementType) {
    Validate.Arg.notNull(value, "value");
    Validate.Arg.notNull(elementType, "elementType");
    if (!value.isEmpty() && elementType == NBTTagType.END) {
      throw new IllegalArgumentException("A non-empty list cannot be of type END");
    } else {
      this.elementType = elementType;
      this.value = value;
    }
  }

  private void reinferElementType() {
    this.elementType = (NBTTagType<E>) inferElementType(this.value);
  }

  @Override
  public int size() { return this.value.size(); }

  @Override
  public boolean volatileElementType() {
    return value.isEmpty();
  }

  @Override
  public void add(@NotNull E tag) throws IllegalArgumentException {
    if (hasDefiniteElementType()) {
      NBTTagType<?> definiteElementType = definiteElementType();
      if (definiteElementType != tag.nbtTagType()) {
        throw new IllegalArgumentException(
            "Unexpected element type: " + tag.nbtTagType().name() + ", Excepted " + definiteElementType);
      }
    }
    this.value.add(tag);
    reinferElementType();
  }

  @Override
  public void addAll(@NotNull Collection<? extends E> tags) { tags.forEach(this::add); }

  @Override
  public E get(int index) {
    return this.value.get(index);
  }

  @Override
  public boolean hasDefiniteElementType() {
    return this.elementType != null;
  }

  @Override
  public <NE extends NBTTag> NBTTagList<NE> specifyElementType(@NotNull NBTTagType<NE> elementType) throws IllegalStateException {
    if (this.elementType == elementType) {
      return cast(this);
    }
    if (volatileElementType()) {
      this.elementType = cast(elementType);
      return cast(this);
    } else {
      throw new IllegalStateException("NBT tag element type is not volatile");
    }
  }

  @Override
  public @NotNull NBTTagType<E> definiteElementType() throws IllegalStateException {
    if (elementType == null) {
      throw new IllegalStateException("NBT tag element type is not defined (defined by auto infer or manual specify)");
    } else {
      return elementType;
    }
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
  public <T extends NBTTag> @NotNull NBTTagList<T> changeElementType(@NotNull NBTTagType<T> elementType) {
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
  public @NotNull List<E> valueRaw() {
    return this.value;
  }

  @Override
  public void valueRaw(@NotNull List<E> value) {
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
