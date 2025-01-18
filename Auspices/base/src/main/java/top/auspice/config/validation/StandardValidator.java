package top.auspice.config.validation;

import top.auspice.config.sections.ConfigSection;
import top.auspice.config.sections.label.Label;
import top.auspice.config.validation.ValidationFailure.Severity;

import java.util.Objects;

public class StandardValidator implements ConfigValidator {
    public static final StandardValidator NULL;
    private final Type type;
    private final int minLen;
    private final int maxLen;

    public StandardValidator(Type type, int minLen, int maxLen) {
        if (minLen != 0 || maxLen != 0) {
            if (minLen >= maxLen) {
                throw new IllegalArgumentException("Validation range cannot be equal or smaller one greater than the bigger one: " + minLen + " - " + maxLen);
            }

            if (type != StandardValidator.Type.INT && type != StandardValidator.Type.DECIMAL) {
                throw new IllegalArgumentException("Cannot have range validation for type: " + type);
            }
        }

        this.type = Objects.requireNonNull(type);
        this.minLen = minLen;
        this.maxLen = maxLen;
    }

    public String toString() {
        return "StandardValidator<" + this.type + '>' + (this.maxLen == 0 && this.minLen == 0 ? "" : "{" + this.minLen + '-' + this.maxLen + '}');
    }

    static Type getTypeFromLabel(Label tag) {
        if (tag == Label.STR) {
            return StandardValidator.Type.STR;
        } else if (tag == Label.INT) {
            return StandardValidator.Type.INT;
        } else if (tag == Label.FLOAT) {
            return StandardValidator.Type.DECIMAL;
        } else if (tag == Label.BOOL) {
            return StandardValidator.Type.BOOL;
        } else {
            return tag == Label.NULL ? StandardValidator.Type.NULL : null;
        }
    }

    public ValidationFailure validate(ValidationContext context) {
        ConfigSection section = context.getSection();
        if (!(section.getBranchState() == ConfigSection.BranchState.END)) {
            return context.fail(new ValidationFailure(Severity.ERROR, section, "Wrong type, expected '" + this.type.name + "' but got '" + section.getLabel().getValue() + '\''));
        } else if (this.type == StandardValidator.Type.ANY) {                                                                     //TODO isPresent()
            return null;
        } else {
            String configuredString = section.getConfigureString();
            Objects.requireNonNull(configuredString, "Missing configured string for section " + section.getPath().asString());

            Type type = getTypeFromLabel(section.getLabel());
            if (type == null) {
                return context.err("Expected " + this.type.name + ", but got '" + section.getLabel() + "'");
            } else if (type == Type.NULL) {
                return null;
            } else {
                if (type != this.type) {
                    if (this.type == Type.STR) {
                        section.setParsedValue(configuredString);
                    } else if (this.type != Type.DECIMAL || type != Type.INT) {
                        return context.err("Wrong type, expected '" + this.type.name + "' but got '" + type.name + '\'');
                    }
                }

                section.setLabel(type.getLabel());
                if (this.minLen != 0 || this.maxLen != 0) {
                    int length;
                    if (this.type == StandardValidator.Type.STR) {
                        length = section.getParsedValue().toString().length();

                    } else {
                        length = ((Number) section.getParsedValue()).intValue();
                    }

                    if (length < this.minLen) {
                        return context.err("Value's length must be greater than " + this.minLen);
                    }

                    if (length > this.maxLen) {
                        return context.err("Value's length must be less than " + this.maxLen);
                    }
                }

                return null;
            }
        }
    }

    public String getName() {
        return this.type.name;
    }

    public static Type getStandardType(String str) {
        return switch (str) {
            case "int" -> Type.INT;
            case "decimal" -> Type.DECIMAL;
            case "bool" -> Type.BOOL;
            case "str" -> Type.STR;
            case "null" -> Type.NULL;
            case "any" -> Type.ANY;
            default -> null;
        };
    }

    static {
        NULL = new StandardValidator(StandardValidator.Type.NULL, 0, 0);
    }

    public enum Type {
        INT("integer", Label.INT),
        DECIMAL("decimal", Label.FLOAT),
        BOOL("boolean", Label.BOOL),
        STR("text", Label.STR),
        NULL("null", Label.NULL),
        ANY("any scalar", null);

        private final String name;
        private final Label label;

        Type(String name, Label label) {
            this.name = name;
            this.label = label;
        }

        public Label getLabel() {
            return this.label;
        }

        public String getName() {
            return this.name;
        }
    }
}
