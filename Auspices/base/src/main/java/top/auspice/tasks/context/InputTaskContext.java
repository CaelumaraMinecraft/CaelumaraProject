package top.auspice.tasks.context;

import org.jetbrains.annotations.NotNull;

public interface InputTaskContext<I> extends TaskContext {
    I getInput();

    @NotNull
    InputTaskContext<I> createNew();   //todo 泛型

}