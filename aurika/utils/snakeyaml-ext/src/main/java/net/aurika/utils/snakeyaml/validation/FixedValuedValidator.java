package net.aurika.utils.snakeyaml.validation;


import org.snakeyaml.engine.v2.nodes.ScalarNode;

import java.util.Arrays;
import java.util.Locale;
import java.util.Set;

public class FixedValuedValidator implements NodeValidator {
    private final Set<String> acceptedValues;

    public FixedValuedValidator(Set<String> acceptedValues) {
        this.acceptedValues = acceptedValues;
    }

    public ValidationFailure validate(ValidationContext context) {
        if (!(context.getNode() instanceof ScalarNode scalarNode)) {
            return context.err("Expected a simple scalar value here, but got a " + context.getNode().getTag().getValue() + " instead");
        } else {
            String val = scalarNode.getValue().toLowerCase(Locale.ENGLISH);
            return this.acceptedValues.contains(val) ? null : context.err("Unexpected value '" + scalarNode.getValue() + "' expected one of " + Arrays.toString(this.acceptedValues.toArray()));
        }
    }

    public String getName() {
        return "one of " + this.acceptedValues;
    }

    public String toString() {
        return "FixedValuedValidator" + this.acceptedValues;
    }
}
