package net.aurika.configuration.validation;

import net.aurika.configuration.sections.ConfigSection;

import java.util.Arrays;
import java.util.Locale;
import java.util.Set;

public class FixedValuedValidator implements ConfigValidator {

  private final Set<String> acceptedValues;

  public FixedValuedValidator(Set<String> acceptedValues) {
    this.acceptedValues = acceptedValues;
  }

  public ValidationFailure validate(ValidationContext context) {
    ConfigSection section = context.getSection();
    String scaVal = section.getConfigureString();

    if (scaVal == null) {
      return context.err(
          "Expected a simple scalar value here, but got a " + section.getLabel().getValue() + " instead");
    } else {
      String val = scaVal.toLowerCase(Locale.ENGLISH);
      return this.acceptedValues.contains(val) ? null : context.err(
          "Unexpected value '" + scaVal + "' expected one of " + Arrays.toString(this.acceptedValues.toArray()));
    }
  }

  public String getName() {
    return "one of " + this.acceptedValues;
  }

  public String toString() {
    return "FixedValuedValidator" + this.acceptedValues;
  }

}