package top.auspice.constants.top;

import com.google.common.base.Strings;
import net.aurika.checker.Checker;
import net.aurika.config.sections.ConfigSection;
import net.aurika.text.compiler.TextCompiler;
import net.aurika.text.placeholders.context.PlaceholderProvider;
import org.jetbrains.annotations.Nullable;
import top.auspice.configs.messages.AuspiceLang;
import top.auspice.configs.messages.messenger.Messenger;
import top.auspice.configs.messages.messenger.StaticMessenger;
import top.auspice.utils.compiler.condition.ConditionCompiler;
import top.auspice.utils.compiler.math.MathCompiler;
import top.auspice.utils.conditions.ConditionProcessor;
import top.auspice.utils.math.MathUtils;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

public abstract class ExpressionBasedTopData<K, V> extends IndexedTopData<K, V> {
    private final MathCompiler.Expression equation;
    private final ConditionCompiler.LogicalOperand filter;
    private final Predicate<V> predicate;
    private final String dataName;
    private final Messenger displayName;
    private final Messenger description;

    public ExpressionBasedTopData(MathCompiler.Expression var1, ConditionCompiler.LogicalOperand var2, String dataName, Messenger var4, Messenger var5) {
        this.equation = Objects.requireNonNull(var1);
        this.filter = var2;
        this.predicate = var2 == null ? null : (var2x) -> ConditionProcessor.process(var2, this.getPlaceholderProvider(var2x));
        this.dataName = Checker.Arg.notEmpty(dataName, "dataName");
        this.displayName = Objects.requireNonNull(var4);
        this.description = Objects.requireNonNull(var5);
    }

    protected abstract PlaceholderProvider getPlaceholderProvider(V var1);

    public static <TOP extends ExpressionBasedTopData<?, ?>> void parse(ConfigSection var0, Creator<TOP> var1, Map<String, TOP> var2) {
        Iterator<String> var3 = var0.getKeys(false).iterator();

        while (true) {
            while (var3.hasNext()) {
                String var4 = var3.next();
                ConfigSection var5 = var0.getSection(new String[]{var4});
                MathCompiler.Expression var6 = var5.getMath(new String[]{"equation"});
                ConditionCompiler.LogicalOperand var7 = var5.getCondition(new String[]{"filter"});
                if (var6 != null && !var6.isDefault()) {
                    String var8;
                    if (Strings.isNullOrEmpty(var8 = var5.getString(new String[]{"name"}))) {
                        var8 = var4;
                    }

                    StaticMessenger var10 = new StaticMessenger(TextCompiler.compile(var8));
                    String var9;
                    Messenger var11;
                    if (Strings.isNullOrEmpty(var9 = var5.getString(new String[]{"description"}))) {
                        var11 = AuspiceLang.NONE;
                    } else {
                        var11 = new StaticMessenger(TextCompiler.compile(var9));
                    }

                    var2.put(var4, var1.create(var5, var4, var10, var11, var6, var7));
                } else {
                    AuspiceLogger.error("No equation defined for top type: " + var4);
                }
            }

            return;
        }
    }

    public ConditionCompiler.LogicalOperand getFilter() {
        return this.filter;
    }

    public @Nullable Predicate<V> getPredicate() {
        return this.predicate;
    }

    public Messenger getDisplayName() {
        return this.displayName;
    }

    public Messenger getDescription() {
        return this.description;
    }

    public String getDataName() {
        return this.dataName;
    }

    public boolean isIncluded(V v) {
        return this.predicate.test(v);
    }

    public double getTopValueOf(V v) {
        return MathUtils.eval(this.equation, this.getPlaceholderProvider(v));
    }

    public int compare(V var1, V var2) {
        double var3 = this.getTopValueOf(var1);
        double var5 = this.getTopValueOf(var2);
        if (var3 < var5) {
            return -1;
        } else {
            return var3 > var5 ? 1 : 0;
        }
    }

    public String toString() {
        return this.getClass().getSimpleName() + '(' + "name=" + this.dataName + ", displayName=" + this.displayName + ", equation=" + this.equation.asString(true) + ", filter=" + (this.filter == null ? "null" : this.filter.asString(true)) + ')';
    }

    public interface Creator<TOP extends ExpressionBasedTopData<?, ?>> {
        TOP create(ConfigSection var1, String var2, Messenger var3, Messenger var4, MathCompiler.Expression var5, ConditionCompiler.LogicalOperand var6);
    }
}

