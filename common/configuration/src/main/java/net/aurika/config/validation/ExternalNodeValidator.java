package net.aurika.config.validation;

public class ExternalNodeValidator implements ConfigValidator {
    private final String validatorName;

    public ExternalNodeValidator(String validatorName) {
        this.validatorName = validatorName;
    }

    public ValidationFailure validate(ValidationContext context) {
        ConfigValidator externalValidator = context.getValidator(this.validatorName);
        if (externalValidator == null) {
            throw new IllegalStateException("Unknown external validator: '" + this.validatorName + "' " + context.getSection());  //TODO 正确的表达 section
        } else {
            return externalValidator.validate(context);
        }
    }

    public String getName() {
        return this.validatorName;
    }

    public String toString() {
        return "ExternalNodeValidator{" + this.validatorName + '}';
    }
}
