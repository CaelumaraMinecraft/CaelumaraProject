package net.aurika.auspice.text.compiler.container;

import net.aurika.auspice.utils.Pair;
import net.aurika.auspice.text.TextObject;
import net.aurika.text.placeholders.context.MessagePlaceholderProvider;
import net.aurika.auspice.utils.compiler.condition.ConditionCompiler;
import net.aurika.auspice.utils.conditions.ConditionProcessor;

import java.util.Iterator;
import java.util.List;

public final class ConditionalTextContainer implements TextContainer {
    private final List<Pair<ConditionCompiler.LogicalOperand, TextObject>> conditionalMessages;

    public ConditionalTextContainer(List<Pair<ConditionCompiler.LogicalOperand, TextObject>> conditionalMessages) {
        this.conditionalMessages = conditionalMessages;
    }

    public TextObject get(MessagePlaceholderProvider messageContext) {
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

