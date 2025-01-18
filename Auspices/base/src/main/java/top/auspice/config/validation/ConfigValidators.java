package top.auspice.config.validation;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.auspice.config.sections.ConfigSection;
import top.auspice.config.sections.label.Label;
import top.auspice.configs.texts.compiler.TextCompiler;
import top.auspice.configs.texts.compiler.TextCompilerSettings;
import top.auspice.configs.texts.compiler.TextObject;
import top.auspice.utils.compiler.condition.ConditionCompiler;
import top.auspice.utils.compiler.math.MathCompiler;
import top.auspice.utils.time.TimeUtils;

import java.util.*;

public final class ConfigValidators {
    private static final Map<String, ConfigValidator> REGISTERED = new HashMap<>();
    private static final Map<ConfigValidator, Set<Class<?>>> COMPATIBILITY_MAP = new HashMap<>();

    public static final Label MATH = register("Math", MathValidator.INSTANCE);
    public static final Label CONDITION = register("Condition", ConditionValidator.INSTANCE);
    public static final Label CONDITIONAL = new Label("Conditional");
    public static final Label MESSAGE = register("Message", MessageValidator.INSTANCE);
    public static final Label PERIOD = register("Period", PeriodValidator.INSTANCE);

    private ConfigValidators() {

    }

    @NotNull
    public static Label register(String LabelStr, ConfigValidator validator) {
        Label Label = new Label(LabelStr);
        register(Label, validator);
        return Label;
    }

    public static void register(Label Label, ConfigValidator validator) {
        CacheType ann = validator.getClass().getAnnotation(CacheType.class);
        Set<Class<?>> compatibilitySet;
        if (ann != null && ann.value().length != 0) {
            compatibilitySet = new HashSet<>(Arrays.asList(ann.value()));
            COMPATIBILITY_MAP.put(validator, compatibilitySet);
        }
        REGISTERED.put(Label.getValue(), validator);

    }

    @Nullable
    public static Set<Class<?>> getCompatibility(Label Label) {
        return getCompatibility(Label.getValue());
    }

    @Nullable
    public static Set<Class<?>> getCompatibility(String Label) {
        return getCompatibility(REGISTERED.get(Label));
    }

    @Nullable
    public static Set<Class<?>> getCompatibility(ConfigValidator validator) {
        return COMPATIBILITY_MAP.get(validator);
    }

    public static ConfigValidator getValidator(@NotNull String label) {
        return REGISTERED.get(label);
    }

    public static ConfigValidator getValidator(@NotNull Label label) {
        return REGISTERED.get(label.getValue());
    }

    public static Map<String, ConfigValidator> getValidators() {
        return REGISTERED;
    }

    @CacheType({MathCompiler.Expression.class})
    public static final class MathValidator implements ConfigValidator {
        public static final MathValidator INSTANCE = new MathValidator();

        private MathValidator() {
        }

        public ValidationFailure validate(ValidationContext context) {
            ConfigSection section = context.getSection();
            Label label = section.getLabel();
            if (label == MATH) {
                return null;
            } else if (label != Label.AUTO && label != Label.STR && label != Label.INT && label != Label.FLOAT) {
                return context.err("Expected math equation, instead got " + label);
            } else {
                String str = section.getConfigureString();

                if (str != null) {

                    MathCompiler.Expression expression;
                    try {
                        expression = MathCompiler.compile(str);
                    } catch (Exception var4) {
                        return context.err(var4.getMessage());
                    }

                    section.setLabel(MATH);
                    section.setParsedValue(expression);
                }

                return null;
            }
        }
    }

    @CacheType({ConditionCompiler.LogicalOperand.class})
    public static final class ConditionValidator implements ConfigValidator {
        public static final ConditionValidator INSTANCE = new ConditionValidator();

        private ConditionValidator() {
        }

        public ValidationFailure validate(ValidationContext context) {
            ConfigSection section = context.getSection();
            Label label = section.getLabel();
            if (label == CONDITION) {
                return null;
            } else if (label != Label.AUTO && label != Label.STR && label != Label.BOOL) {
                return context.err("Expected a condition, instead got " + label);
            } else {
                String str = section.getConfigureString();

                ConditionCompiler.LogicalOperand expr;
                try {
                    expr = ConditionCompiler.compile(str).evaluate();
                } catch (Exception var4) {
                    return context.err(var4.getMessage());
                }

                section.setLabel(CONDITION);
                section.setParsedValue(expr);
                return null;
            }
        }
    }

    @CacheType({TextObject.class})
    public static final class MessageValidator implements ConfigValidator {
        public static final MessageValidator INSTANCE = new MessageValidator();

        private MessageValidator() {

        }

        public ValidationFailure validate(ValidationContext context) {
            ConfigSection section = context.getSection();
            Label label = section.getLabel();
            if (label == MESSAGE) {
                return null;
            } else if (label == Label.NULL) {
                return null;
            } else if (label != Label.AUTO && label != Label.STR) {
                return context.err("Expected a message entry, instead got " + label);
            } else {
                String str = section.getConfigureString();

                if (str != null) {
                    TextCompilerSettings settings = TextCompilerSettings.none().validate().allowNewLines().colorize().hovers().translatePlaceholders();
                    TextCompiler textCompiler = new TextCompiler(str, settings);
                    if (textCompiler.hasErrors()) {
                        return context.err(textCompiler.joinExceptions());
                    } else {
                        section.setLabel(MESSAGE);
                        return null;
                    }
                }

                return null;
            }
        }

    }

    public static final class PeriodValidator implements ConfigValidator {
        public static final PeriodValidator INSTANCE = new PeriodValidator();

        private PeriodValidator() {

        }

        public ValidationFailure validate(ValidationContext context) {
            ConfigSection section = context.getSection();

            if (section.getLabel() == PERIOD) {
                return null;
            }
            if (section.getLabel() != Label.STR && section.getLabel() != Label.INT) {
                return context.err("Expected a time period");
            }
            String s = section.getConfigureString();
            if (s == null) {
                return context.err("Expected a time period");
            }

            Object parsed = section.getParsedValue();
            if (parsed instanceof Number numberParsed) {
                int var6 = numberParsed.intValue();
                if (var6 < 0) {
                    return context.err("Cannot parse time period '" + s + '\'');
                }

                if (var6 > 0) {
                    return context.err("Time period without any time suffix.");
                }
            }

            try {
                MathCompiler.Expression var5 = MathCompiler.compile(s);
                if (!var5.contains(MathCompiler.ConstantExpr.class, (var0) -> var0.getType() == MathCompiler.ConstantExprType.TIME)) {
                    context.warn("The provided math equation doesn't seem to contain any time periods");
                }

                section.setLabel(PERIOD);
                section.setParsedValue(var5);
                return null;
            } catch (Exception var4) {
                Long var3 = TimeUtils.parseTime(s);
                if (var3 == null) {
                    return context.err("Cannot parse time period '" + s + '\'');
                }

                section.setParsedValue(var3);
            }

            section.setLabel(PERIOD);
            return null;
        }

    }


}

