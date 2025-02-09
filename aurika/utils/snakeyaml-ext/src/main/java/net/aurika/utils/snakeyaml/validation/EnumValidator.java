package net.aurika.utils.snakeyaml.validation;

import net.aurika.utils.snakeyaml.nodes.NodesKt;
import org.snakeyaml.engine.v2.nodes.ScalarNode;

import java.util.Locale;
import java.util.Objects;

public class EnumValidator implements NodeValidator {
    private final Class<? extends Enum> enumerator;

    public EnumValidator(Class<? extends Enum> enumerator) {
        this.enumerator = Objects.requireNonNull(enumerator);
    }

    public ValidationFailure validate(ValidationContext context) {
        if (!(context.getNode() instanceof ScalarNode scalarNode)) {
            return context.err("Expected a " + this.enumerator.getSimpleName() + " type, but got an option of type '" + context.getNode().getTag().getValue());
        } else {

            try {
                Enum<?> enumerate = Enum.valueOf(this.enumerator, scalarNode.getValue().toUpperCase(Locale.ENGLISH));
                NodesKt.cacheConstructed(scalarNode, enumerate);
                return null;
            } catch (IllegalArgumentException var4) {
                return context.err("Expected a " + this.enumerator.getSimpleName() + " type, but got '" + scalarNode.getValue() + '\'');
            }
        }
    }

    public String getName() {
        return this.enumerator.getSimpleName();
    }

    public String toString() {
        return "EnumValidator{" + this.enumerator.getName() + '}';
    }
}

