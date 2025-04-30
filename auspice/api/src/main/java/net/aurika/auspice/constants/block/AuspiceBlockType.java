package net.aurika.auspice.constants.block;

import net.aurika.common.ident.Ident;
import net.aurika.common.ident.Identified;
import net.aurika.common.validate.Validate;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;

@ApiStatus.NonExtendable
public interface AuspiceBlockType extends Identified {

  @Override
  @NotNull Ident ident();

}

class AuspiceBlockTypeImpl implements AuspiceBlockType {

  private final @NotNull Ident id;

  AuspiceBlockTypeImpl(@NotNull Ident id) {
    Validate.Arg.notNull(id, "id");
    this.id = id;
  }

  @Override
  public @NotNull Ident ident() {
    return id;
  }

}
