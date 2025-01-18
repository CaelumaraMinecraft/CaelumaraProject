package top.auspice.abstraction.conditional;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.auspice.configs.texts.placeholders.context.PlaceholderProvider;
import top.auspice.utils.Checker;
import top.auspice.utils.conditions.ConditionProcessor;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public final class ConditionChain<T> {

    private final @NotNull List<ConditionBranch<T>> branches;

    public ConditionChain(@NotNull List<ConditionBranch<T>> branches) {
        Checker.Argument.checkNotNull(branches, "branches");
        this.branches = branches;
    }

    @NotNull
    public List<ConditionBranch<T>> getCases() {
        return this.branches;
    }

    public ConditionChain(@NotNull ConditionBranch<T> var1) {
        this(Collections.singletonList(Objects.requireNonNull(var1)));
    }

    @Nullable
    public T evaluate(@NotNull PlaceholderProvider placeholderProvider, boolean todo_rename) {
        Checker.Argument.checkNotNull(placeholderProvider, "placeholderProvider");
        Iterator<ConditionBranch<T>> var3 = this.branches.iterator();

        ConditionBranch<T> var4;
        do {
            if (!var3.hasNext()) {
                return null;
            }

            var4 = var3.next();
        } while (todo_rename != ConditionProcessor.process(var4.getCondition(), placeholderProvider));

        T var5 = var4.getCase();
        if (var5 instanceof ConditionalObject conditional) {
            return conditional.getConditionChain().evaluate(placeholderProvider, todo_rename);
        } else {
            return var5;
        }
    }
}
