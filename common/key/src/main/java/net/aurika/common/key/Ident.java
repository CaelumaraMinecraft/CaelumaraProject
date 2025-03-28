package net.aurika.common.key;

import net.aurika.common.data.DataStringRepresentation;
import net.aurika.util.string.Strings;
import net.aurika.validate.Validate;
import org.intellij.lang.annotations.Language;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Objects;
import java.util.regex.Pattern;

public interface Ident extends Namespaced, DataStringRepresentation {

  char SEPARATOR = ':';
  char PATH_SEP = '.';

  /**
   * Create a {@linkplain Ident} from an ident string.
   * For example: {@code "Aurika:messages.not_found.user"} {@code "Cityzens:city.create"}
   *
   * @param identString The full ident string
   * @throws IllegalArgumentException When the input string is not valid.
   */
  @SuppressWarnings("PatternValidation")
  static @NotNull Ident ident(@KeyPatterns.Ident final @NotNull String identString) {
    int sepIndex = identString.indexOf(PATH_SEP);
    if (sepIndex == -1) {
      throw new IllegalArgumentException(identString + " is not a valid ident, cannot find separator");
    }
    return ident(identString.substring(0, sepIndex), identString.substring(sepIndex + 1));
  }

  static @NotNull Ident ident(@KeyPatterns.Namespace final @NotNull String namespace, @KeyPatterns.IdentValue final @NotNull String valueString) {
    return ident(namespace, Strings.splitArray(valueString, SEPARATOR, true));
  }

  static @NotNull Ident ident(@KeyPatterns.Namespace final @NotNull String namespace, @NotNull String @NotNull [] value) {
    return new IdentImpl(namespace, value);
  }

  @SuppressWarnings("PatternValidation")
  static @NotNull Ident append(@NotNull Ident ident, @KeyPatterns.IdentSection final @NotNull String section) {
    Validate.Arg.notNull(ident, "ident");
    Validate.Arg.notNull(section, "section");
    Validate.Arg.require(
        IdentImpl.IDENT_SECTION_PATTERN.matcher(section).matches(),
        "Ident section string '" + section + "' doesnt match: " + IdentImpl.ALLOWED_IDENT_SECTION
    );
    String namespace = ident.namespace();
    String[] oldValue = ident.value();
    String[] newValue = new String[oldValue.length + 1];
    System.arraycopy(oldValue, 0, newValue, 0, oldValue.length);
    return ident(namespace, newValue);
  }

  @KeyPatterns.Namespace
  @NotNull String namespace();

  @NotNull String @NotNull [] value();

  @NotNull String asDataString();

  boolean equals(@NotNull Ident other);

  boolean equals(Object obj);

  int hashCode();

}

class IdentImpl extends NamespacedImpl implements Ident {

  @Language("RegExp")
  static final String ALLOWED_IDENT_SECTION = "[a-z0-9_]";   // TODO
  static final Pattern IDENT_SECTION_PATTERN = Pattern.compile(ALLOWED_IDENT_SECTION);
  @Language("RegExp")
  static final String ALLOWED_IDENT_VALUE = "[a-z0-9_\\-./]+";  // TODO
  static final Pattern IDENT_VALUE_PATTERN = Pattern.compile(ALLOWED_NAMESPACE);
  @Language("RegExp")
  static final String ALLOWED_IDENT = "(?:(" + ALLOWED_NAMESPACE + ":)?|:)" + ALLOWED_IDENT_VALUE;  // TODO
  static final Pattern IDENT_PATTERN = Pattern.compile(ALLOWED_IDENT);

  private final @NotNull String @NotNull [] value;

  IdentImpl(
      @KeyPatterns.Namespace final @NotNull String namespace,
      final @NotNull String @NotNull [] value
  ) {
    super(namespace);
    Validate.Arg.notNull(value, "value");
    String[] newValue = value.clone();
    int len = newValue.length;
    for (int i = 0; i < len; i++) {
      String sec = value[i];
      Validate.Arg.notEmpty(sec, "value[" + i + "]");
      Validate.Arg.require(
          IDENT_SECTION_PATTERN.matcher(sec).matches(),
          "value[" + i + "] '" + sec + "' doesnt matches regex: " + ALLOWED_IDENT_SECTION
      );
    }
    this.value = newValue;
  }

  @Override
  public @NotNull String @NotNull [] value() {
    return this.value.clone();
  }

  @Override
  public @NotNull String asDataString() {
    return namespace() + SEPARATOR + String.join(String.valueOf(PATH_SEP), value());
  }

  @Override
  public boolean equals(@NotNull Ident other) {
    Validate.Arg.notNull(other, "other");
    return Objects.equals(this.namespace(), other.namespace()) && Arrays.equals(this.value(), other.value());
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof IdentImpl ident)) return false;
    return equals(ident);
  }

  @Override
  public int hashCode() {
    int result = this.namespace().hashCode();
    result = (31 * result) + Arrays.hashCode(this.value);
    return result;
  }

}
