package net.aurika.util.snakeyaml.validation;

public interface NodeValidator {
    ValidationFailure validate(ValidationContext context);

    default String getName() {
        return this.getClass().getSimpleName();
    }
}
