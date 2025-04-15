package net.aurika.dyanasis.api.declaration.constructor;

import net.aurika.dyanasis.api.declaration.NeedOwner;
import net.aurika.dyanasis.api.declaration.executable.DyanasisExecutable;
import net.aurika.dyanasis.api.executing.input.DyanasisExecuteInput;
import net.aurika.dyanasis.api.executing.result.DyanasisConstructResult;
import org.jetbrains.annotations.NotNull;

public interface DyanasisConstructor extends DyanasisExecutable, NeedOwner {

  @Override
  @NotNull DyanasisConstructResult execute(@NotNull DyanasisExecuteInput input);

  @Override
  @NotNull DyanasisConstructorAnchor owner();

}
