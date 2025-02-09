package net.aurika.utils.snakeyaml.validation;

public interface NodeValidator {
    ValidationFailure validate(ValidationContext context);

    default String getName() {
        return this.getClass().getSimpleName();
    }
}
