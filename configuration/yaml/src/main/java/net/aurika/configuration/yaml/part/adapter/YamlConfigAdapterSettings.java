package net.aurika.configuration.yaml.part.adapter;

import net.aurika.common.validate.Validate;
import org.jetbrains.annotations.NotNull;
import org.snakeyaml.engine.v2.constructor.BaseConstructor;
import org.snakeyaml.engine.v2.representer.BaseRepresenter;

public class YamlConfigAdapterSettings {

  protected final @NotNull BaseConstructor constructor;
  protected final @NotNull BaseRepresenter representer;

  public YamlConfigAdapterSettings(@NotNull BaseConstructor constructor, @NotNull BaseRepresenter representer) {
    Validate.Arg.notNull(constructor, "constructor");
    Validate.Arg.notNull(representer, "representer");
    this.constructor = constructor;
    this.representer = representer;
  }

}
