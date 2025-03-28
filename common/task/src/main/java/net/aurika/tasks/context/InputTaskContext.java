package net.aurika.tasks.context;

import org.jetbrains.annotations.NotNull;

public interface InputTaskContext<I> extends TaskContext {

  I getInput();

  @NotNull InputTaskContext<I> createNew();

}