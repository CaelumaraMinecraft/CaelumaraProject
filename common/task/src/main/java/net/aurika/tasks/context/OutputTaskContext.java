package net.aurika.tasks.context;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface OutputTaskContext<O> extends TaskContext {
    @Nullable O getOutput();

    void setOutput(@Nullable O output);

    @NotNull OutputTaskContext<O> createNew();
}
