package net.aurika.utils.snakeyaml.validation;

import com.cryptomorin.xseries.XEnchantment;
import net.aurika.utils.snakeyaml.nodes.NodesKt;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.enchantments.Enchantment;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.snakeyaml.engine.v2.nodes.ScalarNode;
import org.snakeyaml.engine.v2.nodes.Tag;
import net.aurika.config.validation.CacheType;
import net.aurika.text.compiler.TextCompiler;
import net.aurika.text.compiler.TextCompilerSettings;

import java.util.*;

public final class CustomNodeValidators {
    private static final Map<String, NodeValidator> REGISTERED = new HashMap<>();
    private static final Map<NodeValidator, Set<Class<?>>> COMPATIBILITY_MAP = new HashMap<>();

    public static final Tag MATH = register("Math", MathValidator.INSTANCE);
    public static final Tag CONDITION = register("Condition", ConditionValidator.INSTANCE);
    public static final Tag MESSAGE = register("Message", MessageValidator.INSTANCE);
    public static final Tag ENCHANT = register("Enchant", EnchantValidator.INSTANCE);

    private CustomNodeValidators() {

    }

    @NotNull
    public static Tag register(String tagStr, NodeValidator validator) {
        Tag tag = new Tag(tagStr);
        register(tag, validator);
        return tag;
    }

    public static void register(Tag tag, NodeValidator validator) {
        CacheType ann = validator.getClass().getAnnotation(CacheType.class);
        Set<Class<?>> compatibilitySet;
        if (ann != null && ann.value().length != 0) {
            compatibilitySet = new HashSet<>(Arrays.asList(ann.value()));
            COMPATIBILITY_MAP.put(validator, compatibilitySet);
        }
        REGISTERED.put(tag.getValue(), validator);

    }

    @Nullable
    public static Set<Class<?>> getCompatibility(Tag tag) {
        return getCompatibility(tag.getValue());
    }

    @Nullable
    public static Set<Class<?>> getCompatibility(String tag) {
        return getCompatibility(REGISTERED.get(tag));
    }

    @Nullable
    public static Set<Class<?>> getCompatibility(NodeValidator validator) {
        return COMPATIBILITY_MAP.get(validator);
    }

    public static Map<String, NodeValidator> getValidators() {
        return REGISTERED;
    }

    @CacheType({MathCompiler.Expression.class})
    public static final class MathValidator implements NodeValidator {
        public static final MathValidator INSTANCE = new MathValidator();

        private MathValidator() {
        }

        public ValidationFailure validate(ValidationContext context) {
            Tag tag = context.getNode().getTag();
            if (tag == CustomNodeValidators.MATH) {
                return null;
            } else if (tag != Tag.STR && tag != Tag.INT && tag != Tag.FLOAT) {
                return context.err("Expected math equation, instead got " + tag);
            } else {
                ScalarNode scalarNode = (ScalarNode) context.getNode();

                MathCompiler.Expression expression;
                try {
                    expression = MathCompiler.compile(scalarNode.getValue());
                } catch (Exception var4) {
                    return context.err(var4.getMessage());
                }

                scalarNode.setTag(CustomNodeValidators.MATH);
                NodesKt.cacheConstructed(scalarNode, expression);

                return null;
            }
        }
    }

    @CacheType({ConditionCompiler.LogicalOperand.class})
    public static final class ConditionValidator implements NodeValidator {
        public static final ConditionValidator INSTANCE = new ConditionValidator();

        private ConditionValidator() {
        }

        public final ValidationFailure validate(ValidationContext context) {
            Tag var2;
            if ((var2 = context.getNode().getTag()) == CustomNodeValidators.CONDITION) {
                return null;
            } else if (var2 != Tag.STR && var2 != Tag.BOOL) {
                return context.err("Expected a condition, instead got " + var2);
            } else {
                ScalarNode var5 = (ScalarNode) context.getNode();

                ConditionCompiler.LogicalOperand var3;
                try {
                    var3 = ConditionCompiler.compile(var5.getValue()).evaluate();
                } catch (Exception var4) {
                    return context.err(var4.getMessage());
                }

                var5.setTag(CustomNodeValidators.CONDITION);
                NodesKt.cacheConstructed(var5, var3);
                return null;
            }
        }
    }

    @CacheType({})
    public static final class MessageValidator implements NodeValidator {
        public static final MessageValidator INSTANCE = new MessageValidator();

        private MessageValidator() {

        }

        public ValidationFailure validate(ValidationContext context) {
            Tag tag;
            if ((tag = context.getNode().getTag()) == CustomNodeValidators.MESSAGE) {
                return null;
            } else if (tag == Tag.NULL) {
                return null;
            } else if (tag != Tag.STR) {
                return context.err("Expected a message entry, instead got " + tag);
            } else {
                ScalarNode scalarNode = (ScalarNode) context.getNode();
                TextCompilerSettings settings = TextCompilerSettings.none().validate().allowNewLines().colorize().hovers().translatePlaceholders();
                TextCompiler textCompiler = new TextCompiler(scalarNode.getValue(), settings);
                if (textCompiler.hasErrors()) {
                    return context.err(textCompiler.joinExceptions());
                } else {
                    scalarNode.setTag(CustomNodeValidators.MESSAGE);
                    return null;
                }
            }
        }

    }

    @CacheType({Enchantment.class})
    public static final class EnchantValidator implements NodeValidator {
        public static final EnchantValidator INSTANCE = new EnchantValidator();

        private EnchantValidator() {
        }

        public ValidationFailure validate(ValidationContext context) {
            if (context.getNode().getTag() == CustomNodeValidators.ENCHANT) {
                return null;
            } else if (context.getNode().getTag() != Tag.STR) {
                return context.err("Expected an enchantment");
            } else {
                ScalarNode scalarNode = (ScalarNode) context.getNode();
                String fullName = scalarNode.getValue();
                Optional<XEnchantment> xEnchantment = XEnchantment.matchXEnchantment(fullName);

                if (xEnchantment.isEmpty()) {
                    NamespacedKey encNsKey;
                    int sep = fullName.indexOf(':');
                    if (sep == -1) {
                        encNsKey = NamespacedKey.minecraft(fullName.toLowerCase(Locale.ENGLISH));
                    } else {
                        String namespace = fullName.substring(0, sep);
                        String key = fullName.substring(sep + 1);
                        encNsKey = new NamespacedKey(namespace, key);
                    }
                    Enchantment enchantment = Registry.ENCHANTMENT.get(encNsKey);
                    if (enchantment == null) {
                        return context.err("Unknown enchantment '" + scalarNode.getValue() + '\'');
                    } else {
                        NodesKt.cacheConstructed(scalarNode, enchantment);
                    }
                    return null;   //TODO return value
                } else {
                    scalarNode.setTag(CustomNodeValidators.ENCHANT);
                    NodesKt.cacheConstructed(scalarNode, xEnchantment.get().getEnchant());
                    return null;
                }
            }
        }
    }


}
