package net.aurika.configuration.validator;

public interface ConfigValidator {

  ValidationFailure validate(ValidationContext context);

}
