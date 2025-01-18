package top.auspice.config.yaml.snakeyaml.validation;


import org.snakeyaml.engine.v2.nodes.Node;
import org.snakeyaml.engine.v2.nodes.SequenceNode;

public class SequenceValidator implements NodeValidator {
    private final NodeValidator type;
    private final NodeValidator elements;

    public SequenceValidator(NodeValidator type, NodeValidator elements) {
        this.type = type;
        this.elements = elements;
    }

    public ValidationFailure validate(ValidationContext context) {
        if (!(context.getNode() instanceof SequenceNode seqNode)) {
            return context.err("Expected " + this.getName());
        } else {

            for (Node item : seqNode.getValue()) {
                this.elements.validate(context.delegate(context.getRelatedKey(), item));
            }

            this.type.validate(context);
            return null;
        }
    }

    public String getName() {
        return "a " + this.type.getName() + " of " + this.elements.getName();
    }

    public String toString() {
        return "SequenceValidator<" + this.type + ">{" + this.elements + '}';
    }
}
