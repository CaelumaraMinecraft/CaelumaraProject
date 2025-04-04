package net.aurika.configuration.validation;

import net.aurika.configuration.sections.ConfigSection;

import java.util.Locale;
import java.util.Objects;

@SuppressWarnings("rawtypes")
public class EnumValidator implements ConfigValidator {

  private final Class<? extends Enum> enumerator;

  public EnumValidator(Class<? extends Enum> enumerator) {
    this.enumerator = Objects.requireNonNull(enumerator);
  }

  public ValidationFailure validate(ValidationContext context) {
    ConfigSection section = context.getSection();
    String enumStr = section.getConfigureString();

    if (enumStr == null) {
      return context.err(
          "Expected a " + this.enumerator.getSimpleName() + " type, but got an option of type '" + section.getLabel().getValue());
    } else {
      try {
        Enum<?> enumerate = Enum.valueOf(this.enumerator, enumStr.toUpperCase(Locale.ENGLISH));
        section.setParsedValue(enumerate);
        return null;
      } catch (IllegalArgumentException var4) {
        return context.err("Expected a " + this.enumerator.getSimpleName() + " type, but got '" + enumStr + '\'');
      }
    }
  }

  public String getName() {
    return this.enumerator.getSimpleName();
  }

  public String toString() {
    return "EnumValidator{" + this.enumerator.getName() + '}';
  }

}
