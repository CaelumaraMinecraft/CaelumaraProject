package top.auspice.config.yaml.snakeyaml.validation;

public class ExternalNodeValidator implements NodeValidator {
    private final String validatorName;

    public ExternalNodeValidator(String validatorName) {
        this.validatorName = validatorName;
    }

    public ValidationFailure validate(ValidationContext context) {
        NodeValidator externalValidator = context.getValidator(this.validatorName);
        if (externalValidator == null) {
            throw new IllegalStateException("Unknown external validator: '" + this.validatorName + "' " + context.getNode().getStartMark());
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
