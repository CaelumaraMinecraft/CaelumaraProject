package net.aurika.abstraction.conditional;

import net.aurika.auspice.utils.conditions.ConditionProcessor;
import net.aurika.text.placeholders.context.PlaceholderProvider;
import net.aurika.util.Checker;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public final class ConditionChain<T> {

  private final @NotNull List<ConditionBranch<T>> branches;

  public ConditionChain(@NotNull List<ConditionBranch<T>> branches) {
    Checker.Arg.notNull(branches, "branches");
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
    Checker.Arg.notNull(placeholderProvider, "placeholderProvider");
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
