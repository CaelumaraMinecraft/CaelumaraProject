package net.aurika.auspice.text.compiler.container;

import net.aurika.auspice.configs.messages.context.MessageContext;
import net.aurika.auspice.text.TextObject;
import net.aurika.auspice.text.compiler.TextCompiler;
import net.aurika.auspice.utils.Pair;
import net.aurika.auspice.utils.compiler.condition.ConditionCompiler;
import net.aurika.auspice.utils.logging.AuspiceLogger;
import net.aurika.config.accessor.ClearlyConfigAccessor;
import net.aurika.config.accessor.UndefinedPathConfigAccessor;
import net.aurika.config.sections.ConfigSection;

import java.util.ArrayList;
import java.util.List;

public interface TextContainer {
    TextObject get(MessageContext messageContext);

    static List<Pair<ConditionCompiler.LogicalOperand, String>> parseRaw(UndefinedPathConfigAccessor undefinedPathConfigAccessor) {
        ArrayList<Pair<ConditionCompiler.LogicalOperand, String>> var1 = new ArrayList<>();
        if (!undefinedPathConfigAccessor.isSet()) {
            return var1;
        } else {
            ClearlyConfigAccessor var2 = undefinedPathConfigAccessor.getSection();
            if (var2 != null) {
                ConfigSection var4 = var2.getSection();

                for (String var3 : var2.getKeys()) {
                    var1.add(Pair.of(ConditionCompiler.compile(var3).evaluate(), var4.getString(new String[]{var3})));
                }
            } else {
                var1.add(Pair.of(null, undefinedPathConfigAccessor.getString()));
            }

            return var1;
        }
    }

    static TextContainer parse(UndefinedPathConfigAccessor undefinedPathConfigAccessor) {
        ClearlyConfigAccessor accessor = undefinedPathConfigAccessor.getSection();
        TextContainer parsed;
        if (accessor != null) {
            ConfigSection section = accessor.getSection();
            ArrayList<Pair<ConditionCompiler.LogicalOperand, TextObject>> conditionalMessages = new ArrayList<>(2);
            parsed = new ConditionalTextContainer(conditionalMessages);

            for (String keyStr : accessor.getKeys()) {
                ConditionCompiler.LogicalOperand var6 = ConditionCompiler.compile(keyStr).evaluate();
                String msgStr = section.getString(new String[]{keyStr});
                if (msgStr == null) {
                    AuspiceLogger.warn("Warn while parsing at path: '" + section.getPath().asString() + "', there is nothing!");  //TODO
                } else {

                    TextObject var7 = TextCompiler.compile(msgStr, TextCompiler.defaultSettingsWithErrorHandler((TextCompiler messageCompiler) -> {
//                        Mark startMark = section.getNode(keyStr).getStartMark();
//                        AuspiceLogger.warn("While parsing '" + keyedConfigAccessor.getOptionPath() + "' macro, line " + startMark.getLine() + ":\n" + messageCompiler.joinExceptions());

                        AuspiceLogger.warn("While parsing '" + undefinedPathConfigAccessor.getOptionPath() + "' macro:\n" + messageCompiler.joinExceptions());
                    }));
                    conditionalMessages.add(Pair.of(var6, var7));
                }
            }
        } else {
            parsed = new SimpleTextContainer(TextCompiler.compile(undefinedPathConfigAccessor.getString(), TextCompiler.defaultSettingsWithErrorHandler((messageCompiler) -> {
//                Mark startMark = keyedConfigAccessor.getNode().getStartMark();
//                AuspiceLogger.warn("While parsing '" + keyedConfigAccessor.getOptionPath() + "' macro, line " + startMark.getLine() + ":\n" + messageCompiler.joinExceptions());

                AuspiceLogger.warn("While parsing '" + undefinedPathConfigAccessor.getOptionPath() + "' macro:\n" + messageCompiler.joinExceptions());
            })));
        }

        return parsed;
    }
}

