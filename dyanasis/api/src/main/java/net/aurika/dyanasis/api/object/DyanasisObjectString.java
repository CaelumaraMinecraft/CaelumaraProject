package net.aurika.dyanasis.api.object;

import org.jetbrains.annotations.NotNull;

public interface DyanasisObjectString extends DyanasisObjectSupport {

  @Override
  default @NotNull SupportType supportType() {
    return SupportType.STRING;
  }

  @Override
  @NotNull CharSequence valueAsJava();

}
