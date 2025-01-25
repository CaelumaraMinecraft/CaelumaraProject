package net.aurika.abstraction.conditional;

import org.jetbrains.annotations.NotNull;
import net.aurika.utils.Checker;
import top.auspice.utils.compiler.condition.ConditionCompiler;

public final class ConditionBranch<T> {
    private final @NotNull ConditionCompiler.LogicalOperand condition;
    private final T casing;

    public ConditionBranch(@NotNull ConditionCompiler.LogicalOperand condition, T casing) {
        Checker.Argument.checkNotNull(condition, "condition");
        this.condition = condition;
        this.casing = casing;
    }

    public @NotNull ConditionCompiler.LogicalOperand getCondition() {
        return this.condition;
    }

    public T getCase() {
        return this.casing;
    }
}

