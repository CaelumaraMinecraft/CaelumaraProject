package top.auspice.utils.conditions;

import net.aurika.abstraction.conditional.ConditionBranch;
import net.aurika.abstraction.conditional.ConditionChain;
import net.aurika.config.sections.ConfigSection;
import net.aurika.text.compiler.TextCompiler;
import net.aurika.text.compiler.PlaceholderTranslationContext;
import net.aurika.text.compiler.builders.MessageObjectBuilder;
import top.auspice.configs.messages.messenger.LanguageEntryMessenger;
import top.auspice.configs.messages.messenger.Messenger;
import top.auspice.configs.messages.messenger.StaticMessenger;
import net.aurika.config.placeholders.context.MessagePlaceholderProvider;
import net.aurika.config.placeholders.context.PlaceholderProvider;
import top.auspice.utils.compiler.condition.ConditionCompiler;
import top.auspice.utils.compiler.condition.ConditionVariableTranslator;
import top.auspice.utils.string.Strings;

import java.util.ArrayList;
import java.util.Iterator;

public final class ConditionProcessor implements ConditionVariableTranslator {
    private final PlaceholderProvider placeholderProvider;
    private final MessagePlaceholderProvider textPlaceholderProvider;

    public ConditionProcessor(PlaceholderProvider placeholderProvider) {
        this.placeholderProvider = placeholderProvider;
        this.textPlaceholderProvider = placeholderProvider instanceof MessagePlaceholderProvider ? (MessagePlaceholderProvider) placeholderProvider : null;
    }

    public static boolean process(ConditionCompiler.LogicalOperand logicOp, PlaceholderProvider var1) {
        try {
            return logicOp.eval(new ConditionProcessor(var1));
        } catch (Throwable var2) {
            throw new RuntimeException("Error while evaluating condition: " + logicOp, var2);
        }
    }

    public static ConditionChain<Messenger> mapConditions(ConfigSection var0) {
        return mapConditions(var0, false);
    }

    public static ConditionChain<Messenger> mapConditions(ConfigSection var0, boolean var1) {
        if (var0 == null) {
            return null;
        } else {
            ArrayList<ConditionBranch<Messenger>> var2 = new ArrayList<>();

            String var4;
            Messenger var6;
            for (Iterator<String> var3 = var0.getKeys(false).iterator(); var3.hasNext(); var2.add(new ConditionBranch<>(ConditionCompiler.compile(var4).evaluate(), var6))) {
                var4 = var3.next();
                String var5 = var0.getString(new String[]{var4});
                if (var1) {
                    var6 = Strings.isNullOrEmpty(var5) ? null : new LanguageEntryMessenger(Strings.splitArray(var5, '.'));
                } else {
                    var6 = Strings.isNullOrEmpty(var5) ? null : new StaticMessenger(TextCompiler.compile(var5));
                }
            }

            return new ConditionChain<>(var2);
        }
    }

    public Object apply(String variableName) {
        Object placeholder = this.placeholderProvider.providePlaceholder(variableName);
        if (placeholder == null) {
            return null;
        } else {
            if (placeholder instanceof PlaceholderTranslationContext) {
                placeholder = ((PlaceholderTranslationContext) placeholder).getValue();
            }

            if (this.textPlaceholderProvider != null) {
                if (placeholder instanceof Messenger) {
                    placeholder = ((Messenger) placeholder).getMessageObject(this.textPlaceholderProvider.getLanguage());
                }

                if (placeholder instanceof MessageObjectBuilder) {
                    placeholder = ((MessageObjectBuilder) placeholder).build(this.textPlaceholderProvider);
                }
            }

            return placeholder;
        }
    }
}

