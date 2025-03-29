package net.aurika.auspice.translation;

import net.aurika.validate.Validate;
import org.intellij.lang.annotations.Language;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.regex.Pattern;

public class TranslationEntry {

  private final @NotNull String @NotNull [] value;
  @Language("RegExp")
  public static final String ALLOWED_SECTION = "(AZaz)+(AZaz-)+(AZaz)";              // TODO
  public static final Pattern SECTION_PATTERN = Pattern.compile(ALLOWED_SECTION);

  public TranslationEntry(@NotNull String @NotNull [] value) {
    this(value, true);
  }

  protected TranslationEntry(@NotNull String @NotNull [] value, boolean validate) {
    Validate.Arg.notNull(value, "entry");
    if (validate) {
      for (int i = 0; i < value.length; i++) {
        String sec = value[i];
        if (!SECTION_PATTERN.matcher(sec).matches()) {
          throw new IllegalArgumentException("Invalid entry section at index " + i + " : " + sec);
        }
      }
    }
    this.value = value;
  }

  @Contract(value = "-> new", pure = true)
  public @NotNull String @NotNull [] value() {
    return value.clone();
  }

}
