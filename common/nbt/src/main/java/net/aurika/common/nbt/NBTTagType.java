package net.aurika.common.nbt;

import net.kyori.adventure.nbt.BinaryTag;
import net.kyori.adventure.nbt.BinaryTagType;
import net.kyori.adventure.nbt.BinaryTagTypes;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Function;

public final class NBTTagType<T extends NBTTag> {

  public static final NBTTagType<NBTTagEnd> END = new NBTTagType<>(
      NBTTagId.END, BinaryTagTypes.END, NBTTagEnd.class,
      (it) -> NBTTagEnd.nbtTagEnd()
  );
  public static final NBTTagType<NBTTagByte> BYTE = new NBTTagType<>(
      NBTTagId.BYTE, BinaryTagTypes.BYTE, NBTTagByte.class,
      (it) -> NBTTagByte.nbtTagByte((Byte) it)
  );
  public static final NBTTagType<NBTTagShort> SHORT = new NBTTagType<>(
      NBTTagId.SHORT, BinaryTagTypes.SHORT, NBTTagShort.class,
      (it) -> NBTTagShort.nbtTagShort((Short) it)
  );
  public static final NBTTagType<NBTTagInt> INT = new NBTTagType<>(
      NBTTagId.INT, BinaryTagTypes.INT, NBTTagInt.class,
      (it) -> NBTTagInt.nbtTagInt((Integer) it)
  );
  public static final NBTTagType<NBTTagLong> LONG = new NBTTagType<>(
      NBTTagId.LONG, BinaryTagTypes.LONG, NBTTagLong.class,
      (it) -> NBTTagLong.nbtTagLong((Long) it)
  );
  public static final NBTTagType<NBTTagFloat> FLOAT = new NBTTagType<>(
      NBTTagId.FLOAT, BinaryTagTypes.FLOAT, NBTTagFloat.class,
      (it) -> NBTTagFloat.nbtTagFloat((Float) it)
  );
  public static final NBTTagType<NBTTagDouble> DOUBLE = new NBTTagType<>(
      NBTTagId.DOUBLE, BinaryTagTypes.DOUBLE, NBTTagDouble.class,
      (it) -> NBTTagDouble.nbtTagDouble((Double) it)
  );
  public static final NBTTagType<NBTTagByteArray> BYTE_ARRAY = new NBTTagType<>(
      NBTTagId.BYTE_ARRAY, BinaryTagTypes.BYTE_ARRAY, NBTTagByteArray.class,
      (it) -> NBTTagByteArray.nbtTagByteArrayCloned((byte[]) it)
  );
  public static final NBTTagType<NBTTagString> STRING = new NBTTagType<>(
      NBTTagId.STRING, BinaryTagTypes.STRING, NBTTagString.class,
      (it) -> NBTTagString.nbtTagString((String) it)
  );
  public static final NBTTagType<NBTTagList<?>> LIST = Internal.cast(
      new NBTTagType<>(
          NBTTagId.LIST, BinaryTagTypes.LIST, NBTTagList.class,
          (it) -> NBTTagList.nbtTagListSynced((List) it)
      ));
  @SuppressWarnings("unchecked")
  public static final NBTTagType<NBTTagCompound> COMPOUND = new NBTTagType<>(
      NBTTagId.COMPOUND, BinaryTagTypes.COMPOUND, NBTTagCompound.class,
      (it) -> NBTTagCompound.nbtTagCompoundSynced((Map<String, NBTTag>) it)
  );
  public static final NBTTagType<NBTTagIntArray> INT_ARRAY = new NBTTagType<>(
      NBTTagId.INT_ARRAY, BinaryTagTypes.INT_ARRAY, NBTTagIntArray.class,
      (it) -> NBTTagIntArray.nbtTagIntArraySynced((int[]) it)
  );
  public static final NBTTagType<NBTTagLongArray> LONG_ARRAY = new NBTTagType<>(
      NBTTagId.LONG_ARRAY, BinaryTagTypes.LONG_ARRAY, NBTTagLongArray.class,
      (it) -> NBTTagLongArray.nbtTagLongArraySynced((long[]) it)
  );

  private final @NotNull NBTTagId id;
  private final @NotNull BinaryTagType<? extends BinaryTag> adventureType;
  private final @NotNull Class<T> javaType;
  private final @NotNull Function<Object, T> syncedCtor;

  private static final @NotNull Map<Class<?>, NBTTagType<?>> JAVA_TO_NBT;
  private static final @NotNull NBTTagType<?>[] TAG_TYPES;

  private NBTTagType(
      @NotNull NBTTagId id,
      @NotNull BinaryTagType<? extends BinaryTag> adventureType,
      @NotNull Class<T> javaType,
      @NotNull Function<Object, T> syncedCtor
  ) {
    this.id = id;
    this.adventureType = adventureType;
    this.javaType = javaType;
    this.syncedCtor = syncedCtor;
  }

  public @NotNull String name() { return this.id.name(); }

  public @NotNull NBTTagId id() { return this.id; }

  public T cast(@NotNull NBTTag tag) {
    Objects.requireNonNull(tag, "tag");
    if (tag.nbtTagType() != this) {
      String var3 = "Tag is a " + tag.nbtTagType().name() + ", not a " + this.name();
      throw new IllegalArgumentException(var3);
    } else {
      return this.javaType.cast(tag);
    }
  }

  public T constructSynced(@NotNull Object obj) {
    return this.syncedCtor.apply(obj);
  }

  public int hashCode() { return this.id.hashCode(); }

  public boolean equals(@Nullable Object obj) {
    return this == obj;
  }

  public @NotNull String toString() { return this.id.toString(); }

  @ApiStatus.Obsolete
  public static @NotNull NBTTagType<?> fromJava(@NotNull Object obj) {
    Objects.requireNonNull(obj, "obj");
    final NBTTagType nbtTagType = NBTTagType.JAVA_TO_NBT.get(obj.getClass());
    if (nbtTagType == null) {
      throw new IllegalArgumentException(
          "No NBT type could be detected for object: " + obj + " (" + obj.getClass() + ")");
    }
    return (NBTTagType<?>) nbtTagType;
  }

  /**
   * To adventure binary tag type {@link BinaryTagType}.
   *
   * @return the binary tag type
   */
  public @NotNull BinaryTagType<? extends BinaryTag> advtrBinaryTagType() { return adventureType; }

  public static <T extends NBTTag> @NotNull NBTTagType<NBTTagList<T>> nbtTagTypeList() {
    return (NBTTagType<NBTTagList<T>>) (NBTTagType) NBTTagType.LIST;
  }

  @NotNull
  public static <T extends NBTTag> NBTTagType<T> fromId(@NotNull final NBTTagId id) {
    Objects.requireNonNull(id, "id");
    return (NBTTagType<T>) NBTTagType.TAG_TYPES[id.id()];
  }

  static {
    Map<Class<?>, NBTTagType<?>> javaToNBT = new HashMap<>();
    javaToNBT.put(byte.class, NBTTagType.BYTE);
    javaToNBT.put(short.class, NBTTagType.SHORT);
    javaToNBT.put(int.class, NBTTagType.INT);
    javaToNBT.put(long.class, NBTTagType.LONG);
    javaToNBT.put(float.class, NBTTagType.FLOAT);
    javaToNBT.put(double.class, NBTTagType.DOUBLE);
    javaToNBT.put(byte[].class, NBTTagType.BYTE_ARRAY);
    javaToNBT.put(String.class, NBTTagType.STRING);
    javaToNBT.put(List.class, NBTTagType.LIST);
    javaToNBT.put(Map.class, NBTTagType.COMPOUND);
    javaToNBT.put(int[].class, NBTTagType.INT_ARRAY);
    javaToNBT.put(long[].class, NBTTagType.LONG_ARRAY);
    JAVA_TO_NBT = Collections.unmodifiableMap(javaToNBT);
    NBTTagType<?>[] tagTypes = new NBTTagType<?>[NBTTagId.values().length];
    tagTypes[NBTTagId.END.id()] = END;
    tagTypes[NBTTagId.BYTE.id()] = BYTE;
    tagTypes[NBTTagId.SHORT.id()] = SHORT;
    tagTypes[NBTTagId.INT.id()] = INT;
    tagTypes[NBTTagId.LONG.id()] = LONG;
    tagTypes[NBTTagId.FLOAT.id()] = FLOAT;
    tagTypes[NBTTagId.DOUBLE.id()] = DOUBLE;
    tagTypes[NBTTagId.BYTE_ARRAY.id()] = BYTE_ARRAY;
    tagTypes[NBTTagId.STRING.id()] = STRING;
    tagTypes[NBTTagId.LIST.id()] = LIST;
    tagTypes[NBTTagId.COMPOUND.id()] = COMPOUND;
    tagTypes[NBTTagId.INT_ARRAY.id()] = INT_ARRAY;
    tagTypes[NBTTagId.LONG_ARRAY.id()] = LONG_ARRAY;
    TAG_TYPES = tagTypes;
  }
}
