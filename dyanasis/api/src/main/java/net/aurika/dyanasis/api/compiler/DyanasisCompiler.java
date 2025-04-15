package net.aurika.dyanasis.api.compiler;

import net.aurika.dyanasis.api.compiler.expression.Expression;
import net.aurika.dyanasis.api.compiler.setting.DefaultDyanasisCompilerSettings;
import org.jetbrains.annotations.NotNull;

public interface DyanasisCompiler {

  @NotNull Expression compile(@NotNull String string);

  /**
   * Gets the compiler settings.
   *
   * @return the compiler settings
   */
  @NotNull DefaultDyanasisCompilerSettings settings();

}
