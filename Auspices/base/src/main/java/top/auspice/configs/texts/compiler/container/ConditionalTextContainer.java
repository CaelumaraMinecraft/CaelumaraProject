package top.auspice.configs.texts.compiler.container;

import top.auspice.utils.Pair;
import top.auspice.configs.texts.compiler.TextObject;
import top.auspice.configs.texts.placeholders.context.TextPlaceholderProvider;
import top.auspice.utils.compiler.condition.ConditionCompiler;
import top.auspice.utils.conditions.ConditionProcessor;

import java.util.Iterator;
import java.util.List;

public final class ConditionalTextContainer implements TextContainer {
    private final List<Pair<ConditionCompiler.LogicalOperand, TextObject>> conditionalMessages;

    public ConditionalTextContainer(List<Pair<ConditionCompiler.LogicalOperand, TextObject>> conditionalMessages) {
        this.conditionalMessages = conditionalMessages;
    }

    public TextObject get(TextPlaceholderProvider messageContext) {
        Iterator<Pair<ConditionCompiler.LogicalOperand, TextObject>> it = this.conditionalMessages.iterator();

        Pair<ConditionCompiler.LogicalOperand, TextObject> var3 = it.next();
        do {
            if (!it.hasNext()) {
                return null;
            }
        } while(!ConditionProcessor.process(var3.getKey(), messageContext));

        return var3.getValue();
    }
}

