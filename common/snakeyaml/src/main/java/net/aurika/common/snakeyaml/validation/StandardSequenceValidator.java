package net.aurika.common.snakeyaml.validation;

import net.aurika.common.snakeyaml.nodes.NodesKt;
import org.snakeyaml.engine.v2.nodes.Node;
import org.snakeyaml.engine.v2.nodes.SequenceNode;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.function.IntFunction;

public class StandardSequenceValidator implements NodeValidator {
    private final Type type;
    private final int minLen;
    private final int maxLen;

    public StandardSequenceValidator(Type type, int minLen, int maxLen) {
        if ((minLen != 0 || maxLen != 0) && minLen >= maxLen) {
            throw new IllegalArgumentException("Validation range cannot be equal or smaller one greater than the bigger one: " + minLen + " - " + maxLen);
        } else {
            this.type = (Type) Objects.requireNonNull(type);
            this.minLen = minLen;
            this.maxLen = maxLen;
        }
    }

    public String toString() {
        return "StandardSequenceValidator<" + this.type + '>' + (this.maxLen == 0 && this.minLen == 0 ? "" : "{" + this.minLen + '-' + this.maxLen + '}');
    }

    public ValidationFailure validate(ValidationContext context) {
        if (!(context.getNode() instanceof SequenceNode seqNode)) {
            return context.err("Standard sequence validation cannot be used on node: " + context.getNode().getClass().getSimpleName());
        } else {
            int length = seqNode.getValue().size();
            if (this.minLen != 0 || this.maxLen != 0) {
                if (length < this.minLen) {
                    return context.err("Value's length must be greater than " + this.minLen);
                }

                if (length > this.maxLen) {
                    return context.err("Value's length must be less than " + this.maxLen);
                }
            }

            Collection<Object> collection = this.type.constructor.apply(length);

            for (Node item : seqNode.getValue()) {
                String parsed = String.valueOf(NodesKt.parsed(item));
                boolean changed = collection.add(parsed);
                if (!changed && this.type == Type.SET) {
                    context.delegate(context.getRelatedKey(), item).warn("Duplicated value '" + parsed + "' in set");
                }
            }

            return null;
        }
    }

    public String getName() {
        return this.type.name;
    }

    public static Type getStandardType(String str) {
        return switch (str) {
            case "list" -> Type.LIST;
            case "set" -> Type.SET;
            default -> null;
        };
    }

    public enum Type {
        LIST("list", ArrayList::new),
        SET("set", HashSet::new);

        private final String name;
        private final IntFunction<Collection<Object>> constructor;

        Type(String name, IntFunction<Collection<Object>> constructor) {
            this.name = name;
            this.constructor = constructor;
        }

        public IntFunction<Collection<Object>> getConstructor() {
            return this.constructor;
        }

        public String getName() {
            return this.name;
        }
    }
}

