package net.aurika.snakeyaml.extension.validation;


import org.snakeyaml.engine.v2.exceptions.Mark;
import org.snakeyaml.engine.v2.nodes.MappingNode;
import org.snakeyaml.engine.v2.nodes.NodeTuple;
import org.snakeyaml.engine.v2.nodes.ScalarNode;
import org.snakeyaml.engine.v2.nodes.Tag;

import java.util.*;

@SuppressWarnings("unused")
public class StandardMappingValidator implements NodeValidator {
    private final NodeValidator keyValidator;
    private final NodeValidator valueValidator;
    private final Map<String, NodeValidator> specificValidators;
    private final HashSet<String> requiredKeys;
    private final HashSet<String> valueValidatorKeys;
    private final NodeValidator[] extendedValidators;
    private final boolean optional;

    public StandardMappingValidator(NodeValidator[] extendedValidators, NodeValidator keyValidator, NodeValidator validators, Collection<String> valueValidatorKeys, Map<String, NodeValidator> specificValidators, Collection<String> requiredKeys, boolean isOptional) {
        this.extendedValidators = extendedValidators;
        this.keyValidator = keyValidator;
        this.valueValidatorKeys = valueValidatorKeys == null ? null : new HashSet<>(valueValidatorKeys);
        this.requiredKeys = requiredKeys == null ? null : new HashSet<>(requiredKeys);
        this.valueValidator = validators;
        this.specificValidators = specificValidators;
        this.optional = isOptional;
    }

    public Set<String> getRequiredKeys() {
        return this.requiredKeys;
    }

    public Set<String> getValueValidatorKeys() {
        return this.valueValidatorKeys;
    }

    public Map<String, NodeValidator> getSpecificValidators() {
        return this.specificValidators;
    }

    public NodeValidator getKeyValidator() {
        return this.keyValidator;
    }

    public boolean isOptional() {
        return this.optional;
    }

    public NodeValidator getValueValidator() {
        return this.valueValidator;
    }

    public NodeValidator[] getExtendedValidators() {
        return this.extendedValidators;
    }

    public NodeValidator getValidatorForEntry(String key) {
        if (this.specificValidators != null) {
            NodeValidator validator = (NodeValidator)this.specificValidators.get(key);
            if (validator != null) {
                return validator;
            }
        }

        return this.valueValidator == null || this.valueValidatorKeys != null && !this.valueValidatorKeys.contains(key) ? null : this.valueValidator;
    }

    public ValidationFailure validate(ValidationContext context) {
        if (this.extendedValidators != null) {
            NodeValidator[] var2 = this.extendedValidators;
            int var3 = var2.length;

            for (NodeValidator extendedValidator : var2) {
                ValidationFailure res = extendedValidator.validate(context);
                if (res != null) {
                    return res;
                }
            }
        }

        if (!(context.getNode() instanceof MappingNode mapping)) {
            return this.optional && context.getNode().getTag() == Tag.NULL ? null : context.err("Expected a mapping section, instead got " + context.getNode().getClass().getSimpleName().toLowerCase(Locale.ENGLISH));
        } else {  //若为MappingNode
            Set<String> requiredKeys = null;
            if (this.requiredKeys != null) {
                requiredKeys = (Set<String>) this.requiredKeys.clone();
            }

            Iterator<NodeTuple> iterator = mapping.getValue().iterator();

            while (true) {
                NodeTuple tuple;
                NodeTuple pair;
                do {
                    do {
                        do {
                            if (!iterator.hasNext()) {
                                if (requiredKeys != null && !requiredKeys.isEmpty()) {
                                    return context.err("Missing required entries " + Arrays.toString(requiredKeys.toArray())).withMarker(context.getRelatedKey() == null ? new Mark("[ROOT]", 0, 0, 0, new char[0], 0) : context.getRelatedKey().getStartMark().get());  //todo inPresent()
                                }

                                return null;
                            }

                            tuple = iterator.next();
                            pair = tuple;
                            if (this.keyValidator != null) {
                                ValidationFailure failure = this.keyValidator.validate(context.delegate(pair.getKeyNode(), pair.getKeyNode()));
                                if (failure != null) {
                                    failure.setMessage("Disallowed key type. " + failure.getMessage());
                                }
                            }

                            if (requiredKeys != null) {
                                requiredKeys.remove(((ScalarNode) tuple.getKeyNode()).getValue());
                            }

                            if (this.specificValidators == null) {
                                break;
                            }

                            NodeValidator validator = this.specificValidators.get(((ScalarNode) tuple.getKeyNode()).getValue());
                            if (validator == null) {
                                break;
                            }

                            validator.validate(context.delegate(pair.getKeyNode(), pair.getValueNode()));
                        } while (!(pair.getValueNode() instanceof MappingNode));
                    } while (this.valueValidator == null);
                } while (this.valueValidatorKeys != null && !this.valueValidatorKeys.contains(((ScalarNode) tuple.getKeyNode()).getValue()));

                this.valueValidator.validate(context.delegate(pair.getKeyNode(), pair.getValueNode()));
            }
        }
    }

    public String getName() {
        return "a section";
    }

    static int findLongestString(Collection<String> strings) {
        int longest = 0;

        String str;
        for(Iterator<String> var2 = strings.iterator(); var2.hasNext(); longest = Math.max(longest, str.length())) {
            str = var2.next();
        }

        return longest;
    }

    static String repeat(int times) {
        StringBuilder builder = new StringBuilder(times);

        for(int i = 0; i < times; ++i) {
            builder.append(' ');
        }

        return builder.toString();
    }

    static String padRight(String str, int num) {
        int diff = num - str.length();
        return str + repeat(diff);
    }

    static String toString(NodeValidator validator, String rootSpaces) {
        StringBuilder builder = new StringBuilder(100);
        String spaces = rootSpaces + "  ";
        if (validator instanceof StandardMappingValidator mappingValidator) {
            builder.append("StandardMappingValidator").append(mappingValidator.optional ? "?" : "").append(" {").append('\n');
            if (mappingValidator.keyValidator != null) {
                builder.append(spaces).append("keyValidator=").append(toString(mappingValidator.keyValidator, rootSpaces + "  ")).append('\n');
            }

            if (mappingValidator.requiredKeys != null && !mappingValidator.requiredKeys.isEmpty()) {
                builder.append(spaces).append("requiredKeys=").append(mappingValidator.requiredKeys).append('\n');
            }

            if (mappingValidator.valueValidator != null) {
                builder.append(spaces).append("valueValidator=").append(toString(mappingValidator.valueValidator, rootSpaces + "  ")).append('\n');
            }

            if (mappingValidator.specificValidators != null && !mappingValidator.specificValidators.isEmpty()) {
                int longest = findLongestString(mappingValidator.specificValidators.keySet());
                String innerSpaces = spaces + repeat(longest) + "    ";
                builder.append(spaces).append("specificValidator={\n");

                for (Map.Entry<String, NodeValidator> entry : mappingValidator.specificValidators.entrySet()) {
                    builder.append(spaces).append("  ").append(padRight(entry.getKey(), longest)).append(" -> ").append(toString(entry.getValue(), innerSpaces + "  ")).append('\n');
                }

                builder.append(spaces).append("}\n");
            }

            builder.append(rootSpaces).append('}');
        } else {
            builder.append(validator.toString());
        }

        return builder.toString();
    }

    public String toString() {
        return toString(this, "");
    }
}
