package top.auspice.configs.texts.compiler.container;

import top.auspice.config.accessor.ClearlyConfigAccessor;
import top.auspice.config.accessor.UndefinedPathConfigAccessor;
import top.auspice.config.sections.ConfigSection;
import top.auspice.configs.texts.compiler.TextCompiler;
import top.auspice.configs.texts.compiler.TextObject;
import top.auspice.configs.texts.placeholders.context.TextPlaceholderProvider;
import top.auspice.utils.Pair;
import top.auspice.utils.compiler.condition.ConditionCompiler;
import top.auspice.utils.logging.AuspiceLogger;

import java.util.ArrayList;
import java.util.List;

/**
 * 文本容器
 */
public interface TextContainer {
    TextObject get(TextPlaceholderProvider messageContext);

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

