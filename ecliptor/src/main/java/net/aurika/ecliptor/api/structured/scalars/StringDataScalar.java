package net.aurika.ecliptor.api.structured.scalars;

import net.aurika.common.validate.Validate;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class StringDataScalar extends DataScalar {

  private final @NotNull String value;

  public StringDataScalar(@NotNull String value) {
    Validate.Arg.notNull(value, "value");
    this.value = value;
  }

  public @NotNull String value() {
    return value;
  }

  @Override
  public @NotNull DataScalarType type() {
    return DataScalarType.STRING;
  }

  @Override
  public @NotNull String valueAsObject() {
    return value;
  }

  @Override
  public @NotNull String valueToString() {
    return value;
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof StringDataScalar that)) return false;
    return Objects.equals(value, that.value);
  }

  @Override
  public int hashCode() {
    return Objects.hashCode(value);
  }

}
