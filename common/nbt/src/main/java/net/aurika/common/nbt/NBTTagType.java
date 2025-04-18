package net.aurika.common.nbt;

import net.aurika.util.unsafe.fn.Fn;
import net.kyori.adventure.nbt.BinaryTag;
import net.kyori.adventure.nbt.BinaryTagType;
import net.kyori.adventure.nbt.BinaryTagTypes;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.Function;

public class NBTTagType<T extends NBTTag> {

  public static final NBTTagType<NBTTagEnd> END = new NBTTagType<>(
      NBTTagId.END, BinaryTagTypes.END, NBTTagEnd.class, (it) -> NBTTagEnd.nbtTagEnd());
  public static final NBTTagType<NBTTagByte> BYTE = new NBTTagType<>(
      NBTTagId.BYTE, BinaryTagTypes.BYTE, NBTTagByte.class, (it) -> NBTTagByte.nbtTagByte((Byte) it));
  public static final NBTTagType<NBTTagShort> SHORT = new NBTTagType<>(
      NBTTagId.SHORT, BinaryTagTypes.SHORT, NBTTagShort.class, (it) -> NBTTagShort.nbtTagShort((Short) it));
  public static final NBTTagType<NBTTagInt> INT = new NBTTagType<>(
      NBTTagId.INT, BinaryTagTypes.INT, NBTTagInt.class, (it) -> NBTTagInt.nbtTagInt((Integer) it));
  public static final NBTTagType<NBTTagLong> LONG = new NBTTagType<>(
      NBTTagId.LONG, BinaryTagTypes.LONG, NBTTagLong.class, (it) -> NBTTagLong.nbtTagLong((Long) it));
  public static final NBTTagType<NBTTagFloat> FLOAT = new NBTTagType<>(
      NBTTagId.FLOAT, BinaryTagTypes.FLOAT, NBTTagFloat.class, (it) -> NBTTagFloat.nbtTagFloat((Float) it));
  public static final NBTTagType<NBTTagDouble> DOUBLE = new NBTTagType<>(
      NBTTagId.DOUBLE, BinaryTagTypes.DOUBLE, NBTTagDouble.class, (it) -> NBTTagDouble.nbtTagDouble((Double) it));
  public static final NBTTagType<NBTTagByteArray> BYTE_ARRAY = new NBTTagType<>(
      NBTTagId.BYTE_ARRAY, BinaryTagTypes.BYTE_ARRAY, NBTTagByteArray.class,
      (it) -> NBTTagByteArray.nbtTagByteArrayRaw(Arrays.copyOf((byte[]) it, ((byte[]) it).length))
  );
  public static final NBTTagType<NBTTagString> STRING = new NBTTagType<>(
      NBTTagId.STRING, BinaryTagTypes.STRING, NBTTagString.class, (it) -> NBTTagString.nbtTagString((String) it));
  public static final NBTTagType<NBTTagList<?>> LIST = Fn.cast(
      new NBTTagType<>(NBTTagId.LIST, BinaryTagTypes.LIST, NBTTagList.class, (it) -> NBTTagList.ofUnknown((List) it)));
  public static final NBTTagType<NBTTagCompound> COMPOUND = new NBTTagType<>(
      NBTTagId.COMPOUND, BinaryTagTypes.COMPOUND, NBTTagCompound.class,
      (it) -> NBTTagCompound.nbtTagCompound(castAs(it))
  );
  public static final NBTTagType<NBTTagIntArray> INT_ARRAY = new NBTTagType<>(
      NBTTagId.INT_ARRAY, BinaryTagTypes.INT_ARRAY, NBTTagIntArray.class,
      (it) -> NBTTagIntArray.nbtTagIntArrayRaw(Arrays.copyOf((int[]) it, ((int[]) it).length))
  );
  public static final NBTTagType<NBTTagLongArray> LONG_ARRAY = new NBTTagType<>(
      NBTTagId.LONG_ARRAY, BinaryTagTypes.LONG_ARRAY, NBTTagLongArray.class,
      (it) -> NBTTagLongArray.nbtTagLongArray(Arrays.copyOf((long[]) it, ((long[]) it).length))
  );

  private final @NotNull NBTTagId id;
  private final @NotNull BinaryTagType<? extends BinaryTag> adventureType;
  private final @NotNull Class<T> javaType;
  private final @NotNull Function<Object, T> ctor;

  private static final @NotNull Map<Class<?>, NBTTagType<?>> JAVA_TO_NBT;
  private static final @NotNull NBTTagType<?>[] TAG_TYPES;

  private NBTTagType(
      @NotNull NBTTagId id,
      @NotNull BinaryTagType<? extends BinaryTag> adventureType,
      @NotNull Class<T> javaType,
      @NotNull Function<Object, T> ctor
  ) {
    this.id = id;
    this.adventureType = adventureType;
    this.javaType = javaType;
    this.ctor = ctor;
  }

  public final @NotNull String name() {
    return this.id.name();
  }

  public final @NotNull NBTTagId id() {
    return this.id;
  }

  public final T cast(@NotNull NBTTag tag) {
    Objects.requireNonNull(tag, "tag");
    if (tag.nbtTagType() != this) {
      String var3 = "Tag is a " + tag.nbtTagType().name() + ", not a " + this.name();
      throw new IllegalArgumentException(var3);
    } else {
      return this.javaType.cast(tag);
    }
  }

  public final T construct(@NotNull Object obj) {
    Objects.requireNonNull(obj);
    return this.ctor.apply(obj);
  }

  public boolean equals(@Nullable Object o) {
    if (this == o) {
      return true;
    } else if (o != null && Objects.equals(this.getClass(), o.getClass())) {
      NBTTagType<?> that = (NBTTagType<?>) o;
      return this.id == that.id;
    } else {
      return false;
    }
  }

  public int hashCode() {
    return this.id.hashCode();
  }

  public @NotNull String toString() {
    return this.id.toString();
  }

  public static <T> T castAs(@NotNull Object obj) {
    Objects.requireNonNull(obj);
    return (T) obj;
  }

  public static @NotNull <T extends NBTTag> NBTTagType<NBTTagList<T>> listOf() {
    return (NBTTagType<NBTTagList<T>>) (NBTTagType<?>) NBTTagType.LIST;
  }

  public static @NotNull <T extends NBTTag> NBTTagType<T> fromId(@NotNull NBTTagId id) {
    Objects.requireNonNull(id, "id");
    return (NBTTagType<T>) NBTTagType.TAG_TYPES[id.id()];
  }

  @NotNull
  public static NBTTagType<?> fromJava(@NotNull Object obj) {
    Objects.requireNonNull(obj, "obj");
    final NBTTagType nbtTagType = NBTTagType.JAVA_TO_NBT.get(obj.getClass());
    if (nbtTagType == null) {
      throw new IllegalArgumentException("No NBT type could be detected for object: " + obj + " (" + obj.getClass());
    }
    return (NBTTagType<?>) nbtTagType;
  }

  /**
   * To adventure binary tag type {@link BinaryTagType}.
   *
   * @return the binary tag type
   */
  public @NotNull BinaryTagType<? extends BinaryTag> toBinaryTagType() {
    return adventureType;
  }

  public static final class Companion {

    private Companion() {
    }

    public <T> T castAs(@NotNull final Object obj) {
      Objects.requireNonNull(obj, "obj");
      return (T) obj;
    }

    @NotNull
    public <T extends NBTTag<?>> NBTTagType<NBTTagList<T>> listOf() {
      final NBTTagType list = NBTTagType.LIST;
      Intrinsics.checkNotNull(
          list,
          "null cannot be cast to non-null type org.kingdoms.nbt.tag.NBTTagType<org.kingdoms.nbt.tag.NBTTagList<T of org.kingdoms.nbt.tag.NBTTagType.Companion.listOf>>"
      );
      return (NBTTagType<NBTTagList<T>>) list;
    }

    @NotNull
    public <T extends NBTTag<?>> NBTTagType<T> fromId(@NotNull final NBTTagId id) {
      Objects.requireNonNull(id, "id");
      final NBTTagType nbtTagType = NBTTagType.TAG_TYPES[id.id()];
      Intrinsics.checkNotNull(
          nbtTagType,
          "null cannot be cast to non-null type org.kingdoms.nbt.tag.NBTTagType<T of org.kingdoms.nbt.tag.NBTTagType.Companion.fromId>"
      );
      return (NBTTagType<T>) nbtTagType;
    }

  }

  static {
    Pair[] $this$toTypedArray$iv = new Pair[]{TuplesKt.to(Short.TYPE, SHORT), TuplesKt.to(Byte.TYPE, BYTE), TuplesKt.to(
        Integer.class, INT), TuplesKt.to(Long.TYPE, LONG), TuplesKt.to(Float.TYPE, FLOAT), TuplesKt.to(
        Double.TYPE, DOUBLE), TuplesKt.to(byte[].class, BYTE_ARRAY), TuplesKt.to(int[].class, INT_ARRAY), TuplesKt.to(
        long[].class, LONG_ARRAY), TuplesKt.to(Boolean.TYPE, BOOL), TuplesKt.to(
        NBTTagCompound.class, COMPOUND), TuplesKt.to(
        Collection.class, LIST)};
    JAVA_TO_NBT = MapsKt.hashMapOf($this$toTypedArray$iv);
    NBTTagType<?>[] thisCollection$iv = new NBTTagType[]{END, BYTE, SHORT, INT, LONG, FLOAT, DOUBLE, BYTE_ARRAY, STRING, LIST, COMPOUND, INT_ARRAY, LONG_ARRAY};
    Iterable<NBTTagType<?>> var10000 = CollectionsKt.listOf(thisCollection$iv);
    Comparator<NBTTagType<?>> var10001 = Comparator.comparing((type) -> type.id);
    Intrinsics.checkNotNullExpressionValue(var10001, "comparing(...)");
    Collection<NBTTagType<?>> thisCollection$iv00 = CollectionsKt.sortedWith(var10000, var10001);
    TAG_TYPES = (NBTTagType<?>[]) thisCollection$iv00.toArray(new NBTTagType[0]);
  }
}
