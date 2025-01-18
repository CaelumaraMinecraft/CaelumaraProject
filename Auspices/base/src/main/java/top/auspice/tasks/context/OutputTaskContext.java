package top.auspice.tasks.context;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface OutputTaskContext<O> extends TaskContext {
    @Nullable
    O getOutput();

    void setOutput(@Nullable O var1);

    @NotNull
    OutputTaskContext<O> createNew();   //todo 泛型

}
