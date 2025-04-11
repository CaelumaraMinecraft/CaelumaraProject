package net.aurika.dyanasis.api.executing.input;

import net.aurika.dyanasis.api.compiler.setting.DyanasisCompilerSettings;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class LazyDyanasisExecuteInput extends AbstractDyanasisExecuteInput {

  private @Nullable DyanasisCompilerSettings settings;
  private String[] args;

  public LazyDyanasisExecuteInput(@NotNull String original) {
    this(original, null);
  }

  /**
   * Create a {@linkplain LazyDyanasisExecuteInput} with {@code settings}.
   *
   * @param original the original string
   * @param settings the lexer settings
   */
  public LazyDyanasisExecuteInput(@NotNull String original, @Nullable DyanasisCompilerSettings settings) {
    super(original);
    this.settings = settings;
  }

  @Override
  public @NotNull String @NotNull [] argStrings() {
    if (args == null) {
      if (settings == null) {
        throw new IllegalArgumentException(
            "Get arguments of " + LazyDyanasisExecuteInput.class.getSimpleName() + " requires non-null lexer settings.");
      } else {

      }
    } else {
      return args;
    }
  }

  /**
   * Gets the lexer settings.
   *
   * @return the lexer settings
   */
  public @Nullable DyanasisCompilerSettings settings() {
    return settings;
  }

  /**
   * Sets the lexer settings
   *
   * @param settings the lexer settings
   */
  public void settings(@Nullable DyanasisCompilerSettings settings) {
    this.settings = settings;
  }

}
