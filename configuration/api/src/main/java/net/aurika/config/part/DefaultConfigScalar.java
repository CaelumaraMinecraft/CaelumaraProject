package net.aurika.config.part;

import net.aurika.validate.Validate;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

@ApiStatus.Obsolete
public class DefaultConfigScalar extends AbstractConfigPart implements ConfigScalar {

  protected String value;

  public DefaultConfigScalar(String value) {
    super();
    this.value = value;
  }

  public DefaultConfigScalar(@NotNull ConfigSequence sequence, String value) {
    super(sequence);
    this.value = value;
  }

  public DefaultConfigScalar(@NotNull ConfigSection section, @NotNull String name, String value) {
    super(section, name);
    this.value = value;
  }

  @Override
  public @NotNull String scalarValue() {
    return value;
  }

  @Override
  public void scalarValue(@NotNull String scalarValue) {
    Validate.Arg.notNull(scalarValue, "scalarValue");
    this.value = scalarValue;
  }

}
