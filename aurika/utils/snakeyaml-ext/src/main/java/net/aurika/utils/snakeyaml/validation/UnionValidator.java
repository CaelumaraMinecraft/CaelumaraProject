package net.aurika.utils.snakeyaml.validation;

import net.aurika.utils.snakeyaml.validation.ValidationFailure.Severity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class UnionValidator implements NodeValidator {
    private final NodeValidator[] union;

    public UnionValidator(NodeValidator[] union) {
        this.union = union;
    }

    public ValidationFailure validate(ValidationContext context) {
        List<ValidationFailure> allFails = new ArrayList<>();

        for (NodeValidator validator : this.union) {
            List<ValidationFailure> capturedFails = new ArrayList<>();
            ValidationContext innerContext = new ValidationContext(context.getRelatedKey(), context.getNode(), context.getValidatorMap(), capturedFails);
            ValidationFailure directResult = validator.validate(innerContext);
            if (directResult == null || directResult.getSeverity() == Severity.WARNING) {
                context.getExceptions().addAll(capturedFails);
                return null;
            }

            allFails.addAll(capturedFails);
        }

        return context.fail(new ValidationFailure(Severity.ERROR, context.getNode(), null, "None of the types matched: " + (String)allFails.stream().map(ValidationFailure::getMessage).collect(Collectors.joining(", "))));
    }

    public String getName() {
        return "one of " + Arrays.toString(this.union);
    }

    public String toString() {
        return "UnionValidator{" + Arrays.toString(this.union) + '}';
    }
}

