package top.auspice.config.yaml.snakeyaml.validation;

public interface NodeValidator {
    ValidationFailure validate(ValidationContext context);

    default String getName() {
        return this.getClass().getSimpleName();
    }
}
