package net.aurika.dyanasis.api.object;

import org.jetbrains.annotations.NotNull;

public interface DyanasisObjectNumber extends DyanasisObjectSupport {

  @Override
  default @NotNull SupportType supportType() {
    return SupportType.NUMBER;
  }

  @Override
  @NotNull Number valueAsJava();

}
