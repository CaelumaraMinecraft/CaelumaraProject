package net.aurika.configuration.validation;

import net.aurika.configuration.sections.ConfigSection;
import net.aurika.configuration.sections.label.Label;

import java.util.*;

public class StandardMappingValidator implements ConfigValidator {

  private final ConfigValidator keyValidator;
  private final ConfigValidator valueValidator;
  private final Map<String, ConfigValidator> specificValidators;
  private final HashSet<String> requiredKeys;
  private final HashSet<String> valueValidatorKeys;
  private final ConfigValidator[] extendedValidators;
  private final boolean optional;

  public StandardMappingValidator(ConfigValidator[] extendedValidators,
                                  ConfigValidator keyValidator,
                                  ConfigValidator validators,
                                  Collection<String> valueValidatorKeys,
                                  Map<String, ConfigValidator> specificValidators,
                                  Collection<String> requiredKeys,
                                  boolean isOptional) {
    this.extendedValidators = extendedValidators;
    this.keyValidator = keyValidator;
    this.valueValidatorKeys = valueValidatorKeys == null ? null : new HashSet<>(valueValidatorKeys);
    this.requiredKeys = requiredKeys == null ? null : new HashSet<>(requiredKeys);
    this.valueValidator = validators;
    this.specificValidators = specificValidators;
    this.optional = isOptional;
  }

  public Set<String> getRequiredKeys() {
    return this.requiredKeys;
  }

  public Set<String> getValueValidatorKeys() {
    return this.valueValidatorKeys;
  }

  public Map<String, ConfigValidator> getSpecificValidators() {
    return this.specificValidators;
  }

  public ConfigValidator getKeyValidator() {
    return this.keyValidator;
  }

  public boolean isOptional() {
    return this.optional;
  }

  public ConfigValidator getValueValidator() {
    return this.valueValidator;
  }

  public ConfigValidator[] getExtendedValidators() {
    return this.extendedValidators;
  }

  public ConfigValidator getValidatorForEntry(String key) {
    if (this.specificValidators != null) {
      ConfigValidator validator = (ConfigValidator) this.specificValidators.get(key);
      if (validator != null) {
        return validator;
      }
    }

    return this.valueValidator == null || this.valueValidatorKeys != null && !this.valueValidatorKeys.contains(
        key) ? null : this.valueValidator;
  }

  public ValidationFailure validate(ValidationContext context) {
    if (this.extendedValidators != null) {
      ConfigValidator[] var2 = this.extendedValidators;
      int var3 = var2.length;

      for (ConfigValidator extendedValidator : var2) {
        ValidationFailure res = extendedValidator.validate(context);
        if (res != null) {
          return res;
        }
      }
    }

    ConfigSection section = context.getSection();
    Object parsed = section.getParsedValue();

    if (!(parsed instanceof Map<?, ?>)) {
      return this.optional && section.getLabel() == Label.NULL ? null : context.err(
          "Expected a mapping section, instead got " + section.getBranchState().name().toLowerCase(Locale.ENGLISH));
    } else {
      Set<String> requiredKeys = null;
      if (this.requiredKeys != null) {
        requiredKeys = (Set<String>) this.requiredKeys.clone();
      }

      Map<String, ConfigSection> subSections = section.getSubSections();
      Iterator<Map.Entry<String, ConfigSection>> subSecEntryIt = subSections.entrySet().iterator();

      while (true) {
        Map.Entry<String, ConfigSection> tuple;

        Map.Entry<String, ConfigSection> pair;


        do {
          do {
            do {
              if (!subSecEntryIt.hasNext()) {
                if (requiredKeys != null && !requiredKeys.isEmpty()) {
                  return context.err(
                      "Missing required entries " + Arrays.toString(requiredKeys.toArray()) + " ");  //todo inPresent()
                }

                return null;
              }

              tuple = subSecEntryIt.next();

              pair = tuple;

              if (this.keyValidator != null) {
                ValidationFailure failure = this.keyValidator.validate(context.delegate(pair.getKey()));
                if (failure != null) {
                  failure.setMessage("Disallowed key type. " + failure.getMessage());
                }
              }

              if (requiredKeys != null) {
                requiredKeys.remove(tuple.getKey());
              }

              if (this.specificValidators == null) {
                break;
              }

              ConfigValidator validator = this.specificValidators.get(tuple.getKey());
              if (validator == null) {
                break;
              }

              validator.validate(context.delegate(pair.getValue()));
            } while (!(pair.getValue().getBranchState() == ConfigSection.BranchState.MIDDLE));
          } while (this.valueValidator == null);
        } while (this.valueValidatorKeys != null && !this.valueValidatorKeys.contains(tuple.getKey()));

        this.valueValidator.validate(context.delegate(pair.getValue()));
      }
    }
  }

  public String getName() {
    return "a section";
  }

  static int findLongestString(Collection<String> strings) {
    int longest = 0;

    String str;
    for (Iterator<String> var2 = strings.iterator(); var2.hasNext(); longest = Math.max(longest, str.length())) {
      str = var2.next();
    }

    return longest;
  }

  static String repeat(int times) {
    return " ".repeat(Math.max(0, times));
  }

  static String padRight(String str, int num) {
    int diff = num - str.length();
    return str + repeat(diff);
  }

  static String toString(ConfigValidator validator, String rootSpaces) {
    StringBuilder builder = new StringBuilder(100);
    String spaces = rootSpaces + "  ";
    if (validator instanceof StandardMappingValidator mappingValidator) {
      builder.append("StandardMappingValidator").append(mappingValidator.optional ? "?" : "").append(" {").append('\n');
      if (mappingValidator.keyValidator != null) {
        builder.append(spaces).append("keyValidator=").append(
            toString(mappingValidator.keyValidator, rootSpaces + "  ")).append('\n');
      }

      if (mappingValidator.requiredKeys != null && !mappingValidator.requiredKeys.isEmpty()) {
        builder.append(spaces).append("requiredKeys=").append(mappingValidator.requiredKeys).append('\n');
      }

      if (mappingValidator.valueValidator != null) {
        builder.append(spaces).append("valueValidator=").append(
            toString(mappingValidator.valueValidator, rootSpaces + "  ")).append('\n');
      }

      if (mappingValidator.specificValidators != null && !mappingValidator.specificValidators.isEmpty()) {
        int longest = findLongestString(mappingValidator.specificValidators.keySet());
        String innerSpaces = spaces + repeat(longest) + "    ";
        builder.append(spaces).append("specificValidator={\n");

        for (Map.Entry<String, ConfigValidator> entry : mappingValidator.specificValidators.entrySet()) {
          builder.append(spaces).append("  ").append(padRight(entry.getKey(), longest)).append(" -> ").append(
              toString(entry.getValue(), innerSpaces + "  ")).append('\n');
        }

        builder.append(spaces).append("}\n");
      }

      builder.append(rootSpaces).append('}');
    } else {
      builder.append(validator.toString());
    }

    return builder.toString();
  }

  public String toString() {
    return toString(this, "");
  }

}
