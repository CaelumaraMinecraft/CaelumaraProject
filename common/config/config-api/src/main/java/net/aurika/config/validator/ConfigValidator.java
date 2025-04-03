package net.aurika.config.validator;

public interface ConfigValidator {

  ValidationFailure validate(ValidationContext context);

}
