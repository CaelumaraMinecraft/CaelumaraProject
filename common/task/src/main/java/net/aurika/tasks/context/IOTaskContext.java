package net.aurika.tasks.context;

import org.jetbrains.annotations.NotNull;

public interface IOTaskContext<I, O> extends TaskContext, InputTaskContext<I>, OutputTaskContext<O> {

  @NotNull IOTaskContext<I, O> createNew();

}
