package net.aurika.ecliptor.database.nbt;

import kotlin.jvm.internal.Intrinsics;
import net.aurika.common.function.FloatSupplier;
import net.aurika.common.function.TriConsumer;
import net.aurika.common.nbt.*;
import net.aurika.ecliptor.api.structured.DataStructSchema;
import net.aurika.ecliptor.api.structured.StructuredDataObject;
import net.aurika.util.unsafe.fn.Fn;
import net.aurika.util.uuid.FastUUID;
import net.aurika.common.validate.Validate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.*;

public class NBTDataProvider implements DataProvider, SectionCreatableDataSetter {

  private final @NotNull NBTTag element;

  public NBTDataProvider(@NotNull NBTTag var1) {
    Validate.Arg.notNull(var1, "");
    this.element = var1;
  }

  public @NotNull NBTTag getElement$core() {
    return this.element;
  }

  @Override
  public @NotNull DataProvider createSection(@NotNull String var1) {
    Validate.Arg.notNull(var1, "");
    NBTTagCompound var10000 = NBTTagCompound.nbtTagComponentEmpty();
    Intrinsics.checkNotNullExpressionValue(var10000, "");
    if (this.element instanceof NBTTagCompound) {
      ((NBTTagCompound) this.element).put(var1, var10000);
      return new NBTDataProvider(var10000);
    } else {
      throw new UnsupportedOperationException();
    }
  }

  @Override
  public @NotNull SectionableDataSetter createSection() {
    NBTTagCompound var10000 = NBTTagCompound.nbtTagComponentEmpty();
    if (this.element instanceof NBTTagList) {
      ((NBTTagList) this.element).add(var10000);
      return new NamedNBTDataProvider(null, var10000);
    } else {
      throw new UnsupportedOperationException();
    }
  }

  @Override
  public @NotNull DataProvider get(@NotNull String key) {
    Validate.Arg.notNull(key, "key");
    return new NamedNBTDataProvider(key, NBTTagType.COMPOUND.cast(this.element));
  }

  @Override
  public @NotNull DataProvider asSection() {
    return this;
  }

  @Override
  public int asInt(@NotNull IntSupplier def) {
    Validate.Arg.notNull(def, "def");
    return ((NBTTagInt) this.element).value();
  }

  @Override
  public long asLong(@NotNull LongSupplier def) {
    Validate.Arg.notNull(def, "def");
    return ((NBTTagLong) this.element).value();
  }

  @Override
  public float asFloat(@NotNull FloatSupplier def) {
    Validate.Arg.notNull(def, "def");
    return ((NBTTagFloat) this.element).value();
  }

  @Override
  public double asDouble(@NotNull DoubleSupplier def) {
    Validate.Arg.notNull(def, "def");
    return ((NBTTagDouble) this.element).value();
  }

  @Override
  public boolean asBoolean(@NotNull BooleanSupplier def) {
    Validate.Arg.notNull(def, "def");
    return ((NBTTagByte) this.element).booleanValue();
  }

  @Override
  public @Nullable String asString(@NotNull Supplier<String> def) {
    Validate.Arg.notNull(def, "def");
    return this.element instanceof NBTTagString str_ele ? str_ele.value() : def.get();
  }

  @Override
  public @Nullable UUID asUUID() {
    String s = asString();
    return s != null ? FastUUID.fromString(s) : null;
  }

  @Override
  public <T extends StructuredDataObject> @Nullable T asStruct(@NotNull DataStructSchema<T> template) {
    String s = asString();
    return s != null ? template.plainToObject(s) : null;
  }

  @Override
  public <E, C extends Collection<E>> @NotNull C asCollection(@NotNull C c, @NotNull BiConsumer<C, SectionableDataGetter> handler) {
    Validate.Arg.notNull(c, "c");
    Validate.Arg.notNull(handler, "handler");
    List<NBTTag> elements = ((NBTTagList) this.element).valueCopy();

    for (NBTTag e : elements) {
      Objects.requireNonNull(e);
      handler.accept(c, new NBTDataProvider(e));
    }

    return c;
  }

  @Override
  public <K, V, M extends Map<K, V>> @NotNull M asMap(@NotNull M m, @NotNull TriConsumer<M, DataGetter, SectionableDataGetter> handler) {
    Validate.Arg.notNull(m, "m");
    Validate.Arg.notNull(handler, "handler");
    NBTTag _element = this.element;
    Intrinsics.checkNotNull(_element);
    Map var7 = ((NBTTagCompound) _element).valueCopy();
    Intrinsics.checkNotNullExpressionValue(var7, "");

    for (Object o : var7.entrySet()) {
      Map.Entry var4;
      String var5 = (String) (var4 = (Map.Entry) o).getKey();
      NBTTag var6 = (NBTTag) var4.getValue();
      NBTTagString var10004 = NBTTagString.nbtTagString(var5);
      Intrinsics.checkNotNullExpressionValue(var10004, "");
      NBTDataProvider var10002 = new NBTDataProvider(var10004);
      Intrinsics.checkNotNull(var6);
      handler.accept(m, var10002, new NBTDataProvider(var6));
    }

    return m;
  }

  @Override
  public void setInt(int var1) {
    if (this.element instanceof NBTTagList) {
      ((NBTTagList) this.element).add(NBTTagInt.nbtTagInt(var1));
    } else {
      throw new UnsupportedOperationException();
    }
  }

  @Override
  public void setLong(long var1) {
    if (this.element instanceof NBTTagList) {
      ((NBTTagList) this.element).add(NBTTagLong.nbtTagLong(var1));
    } else {
      throw new UnsupportedOperationException();
    }
  }

  @Override
  public void setFloat(float var1) {
    if (this.element instanceof NBTTagList) {
      ((NBTTagList) this.element).add(NBTTagFloat.nbtTagFloat(var1));
    } else {
      throw new UnsupportedOperationException();
    }
  }

  @Override
  public void setDouble(double var1) {
    if (this.element instanceof NBTTagList) {
      ((NBTTagList) this.element).add(NBTTagDouble.nbtTagDouble(var1));
    } else {
      throw new UnsupportedOperationException();
    }
  }

  @Override
  public void setBoolean(boolean var1) {
    if (this.element instanceof NBTTagList) {
      ((NBTTagList) this.element).add(NBTTagByte.nbtTagBool(var1));
    } else {
      throw new UnsupportedOperationException();
    }
  }

  @Override
  public void setString(@Nullable String var1) {
    if (var1 != null) {
      if (this.element instanceof NBTTagList) {
        ((NBTTagList) this.element).add(NBTTagString.nbtTagString(var1));
      } else {
        throw new UnsupportedOperationException();
      }
    }
  }

  @Override
  public void setUUID(@Nullable UUID var1) {
    this.setString(FastUUID.toString(var1));
  }

  @Override
  public void setStruct(@NotNull StructuredDataObject value) {
    Validate.Arg.notNull(value, "value");
    DataStructSchema<?> schema = value.dataStructSchema();
    setString(schema.objectToPlain(Fn.cast(value)));
  }

  @Override
  public <V> void setCollection(@NotNull Collection<? extends V> value, @NotNull BiConsumer<SectionCreatableDataSetter, V> handler) {
    throw new UnsupportedOperationException();
  }

  @Override
  public <K, V> void setMap(@NotNull Map<K, ? extends V> value, @NotNull MappingSetterHandler<K, V> handler) {
    throw new UnsupportedOperationException();
  }

}
