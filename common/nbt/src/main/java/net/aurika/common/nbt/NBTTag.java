package net.aurika.common.nbt;

import net.aurika.common.validate.Validate;
import net.kyori.adventure.nbt.*;
import net.kyori.examination.Examinable;
import net.kyori.examination.ExaminableProperty;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.stream.Stream;

/**
 * This class is a mutable implement of {@link BinaryTagLike}.
 */
public interface NBTTag extends NBTTagLike, BinaryTagLike, Examinable {

  static @NotNull NBTTag nbtTag(@NotNull BinaryTagLike tag) {
    Validate.Arg.notNull(tag, "tag");
    BinaryTag binaryTag = tag.asBinaryTag();
    Objects.requireNonNull(binaryTag, "binaryTag");
    if (binaryTag instanceof EndBinaryTag) {
      return NBTTagEnd.nbtTagEnd();
    }
    if (binaryTag instanceof ByteBinaryTag) {
      return NBTTagByte.nbtTagByte((ByteBinaryTag) binaryTag);
    }
    if (binaryTag instanceof ShortBinaryTag) {
      return NBTTagShort.nbtTagShort((ShortBinaryTag) binaryTag);
    }
    if (binaryTag instanceof IntBinaryTag) {
      return NBTTagInt.nbtTagInt((IntBinaryTag) binaryTag);
    }
    if (binaryTag instanceof LongBinaryTag) {
      return NBTTagLong.nbtTagLong((LongBinaryTag) binaryTag);
    }
    if (binaryTag instanceof FloatBinaryTag) {
      return NBTTagFloat.nbtTagFloat((FloatBinaryTag) binaryTag);
    }
    if (binaryTag instanceof DoubleBinaryTag) {
      return NBTTagDouble.nbtTagDouble((DoubleBinaryTag) binaryTag);
    }
    if (binaryTag instanceof ByteArrayBinaryTag) {
      return NBTTagByteArray.nbtTagByteArray((ByteArrayBinaryTag) binaryTag);
    }
    if (binaryTag instanceof StringBinaryTag) {
      return NBTTagString.nbtTagString((StringBinaryTag) binaryTag);
    }
    if (binaryTag instanceof ListBinaryTag) {
      return NBTTagList.nbtTagList((ListBinaryTag) binaryTag);
    }
    if (binaryTag instanceof CompoundBinaryTag) {
      return NBTTagCompound.nbtTagCompound((CompoundBinaryTag) binaryTag);
    }
    if (binaryTag instanceof IntArrayBinaryTag) {
      return NBTTagIntArray.nbtTagIntArray((IntArrayBinaryTag) binaryTag);
    }
    if (binaryTag instanceof LongArrayBinaryTag) {
      return NBTTagLongArray.nbtTagLongArray((LongArrayBinaryTag) binaryTag);
    }
    throw new UnsupportedOperationException("Unsupported binary tag type: " + binaryTag.getClass().getName());
  }

  /**
   * Gets the tag type {@link NBTTagType}.
   */
  @NotNull NBTTagType<? extends NBTTag> nbtTagType();

  /**
   * Gets the tag value as a java object.
   */
  @NotNull Object valueAsObject();

  @Override
  default @NotNull NBTTag asNBTTag() {
    return this;
  }

  @Override
  @NotNull BinaryTag asBinaryTag();

  @Override
  default @NotNull Stream<? extends ExaminableProperty> examinableProperties() {
    return asBinaryTag().examinableProperties();
  }

}

abstract class NBTTagImpl implements NBTTag {

  protected NBTTagImpl() { }

  public int hashCode() { return this.valueAsObject().hashCode(); }

  public boolean equals(@Nullable Object obj) {
    if (this == obj) {
      return true;
    } else if (obj instanceof NBTTag) {
      NBTTag that = (NBTTag) obj;
      return Objects.equals(this.valueAsObject(), that.valueAsObject());
    } else {
      return false;
    }
  }

  public @NotNull String toString() {
    return this.getClass().getSimpleName() + "[" + this.valueAsObject() + ']';
  }

}
