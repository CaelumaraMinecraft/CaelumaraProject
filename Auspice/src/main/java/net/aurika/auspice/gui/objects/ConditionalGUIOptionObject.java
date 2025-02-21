package net.aurika.auspice.gui.objects;

import net.aurika.text.placeholders.context.PlaceholderProvider;
import top.auspice.utils.AuspiceLogger;
import top.auspice.utils.conditions.ConditionProcessor;

import java.util.Objects;

public class ConditionalGUIOptionObject implements GUIOptionBuilder {
    private final GUIOptionObject a;
    protected final GUIOptionObjectCondition[] options;

    public ConditionalGUIOptionObject(GUIOptionObject var1, GUIOptionObjectCondition[] var2) {
        this.a = Objects.requireNonNull(var1, "Main settings of conditional option cannot be null");
        this.options = Objects.requireNonNull(var2, "Conditional options cannot be null");
    }

    public GUIOptionObjectCondition[] getOptions() {
        return this.options;
    }

    public GUIOptionObject getOption(PlaceholderProvider var1) {
        GUIOptionObjectCondition[] var2;
        int var3 = (var2 = this.options).length;

        for (int var4 = 0; var4 < var3; ++var4) {
            GUIOptionObjectCondition var5;
            if ((var5 = var2[var4]).condition == null) {
                return var5.option.getOption(var1);
            }

            try {
                if (ConditionProcessor.process(var5.condition, var1)) {
                    return var5.option.getOption(var1);
                }
            } catch (Exception var6) {
                AuspiceLogger.error("Error while evaluating condition for option '" + this.a.getName() + "' " + var6.getMessage() + " ");
                var6.printStackTrace();
            }
        }

        return this.a;
    }

    public GUIOptionObject getMainObject() {
        return this.a;
    }
}
