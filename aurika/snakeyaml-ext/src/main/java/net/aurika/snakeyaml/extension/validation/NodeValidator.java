package net.aurika.snakeyaml.extension.validation;

public interface NodeValidator {
    ValidationFailure validate(ValidationContext context);

    default String getName() {
        return this.getClass().getSimpleName();
    }
}
