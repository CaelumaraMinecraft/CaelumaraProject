package net.aurika.ecliptor.database.mongo;

import net.aurika.common.function.FloatSupplier;
import net.aurika.common.function.TriConsumer;
import net.aurika.ecliptor.api.structured.DataStructSchema;
import net.aurika.ecliptor.api.structured.StructuredDataObject;
import net.aurika.ecliptor.database.dataprovider.DataGetter;
import net.aurika.ecliptor.database.dataprovider.SectionableDataGetter;
import net.aurika.util.uuid.FastUUID;
import net.aurika.common.validate.Validate;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.*;

public class MongoIDGetter implements DataGetter {

  private final @NotNull String value;

  public MongoIDGetter(@NotNull String value) {
    Objects.requireNonNull(value, "value");
    this.value = value;
  }

  @Override
  public int asInt(@NotNull IntSupplier def) {
    return Integer.parseInt(value);
  }

  @Override
  public long asLong(@NotNull LongSupplier def) {
    return Long.parseLong(value);
  }

  @Override
  public float asFloat(@NotNull FloatSupplier def) {
    return Float.parseFloat(value);
  }

  @Override
  public double asDouble(@NotNull DoubleSupplier def) {
    return Double.parseDouble(value);
  }

  @Override
  public boolean asBoolean(@NotNull BooleanSupplier def) {
    Objects.requireNonNull(def, "def");
    throw new UnsupportedOperationException();
  }

  @Override
  public @NotNull String asString(@NotNull Supplier<String> def) {
    Objects.requireNonNull(def, "def");
    return value;
  }

  @Override
  public @NotNull UUID asUUID() {
    return FastUUID.fromString(value);
  }

  @Override
  @ApiStatus.Experimental
  public <T extends StructuredDataObject> T asStruct(@NotNull DataStructSchema<T> template) {
    Validate.Arg.notNull(template, "template");
    return template.plainToObject(value);
  }

  @Override
  public <V, C extends Collection<V>> @NotNull C asCollection(@NotNull C c, @NotNull BiConsumer<C, SectionableDataGetter> handler) {
    Objects.requireNonNull(c, "c");
    Objects.requireNonNull(handler, "handler");
    throw new UnsupportedOperationException();
  }

  @Override
  public <K, V, M extends Map<K, V>> @NotNull M asMap(@NotNull M m, @NotNull TriConsumer<M, DataGetter, SectionableDataGetter> handler) {
    Objects.requireNonNull(m, "");
    Objects.requireNonNull(handler, "");
    throw new UnsupportedOperationException();
  }

}
