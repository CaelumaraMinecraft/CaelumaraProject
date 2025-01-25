package net.aurika.config.validation;

public interface ConfigValidator {
    ValidationFailure validate(ValidationContext context);

    default String getName() {
        return this.getClass().getSimpleName();
    }
}
