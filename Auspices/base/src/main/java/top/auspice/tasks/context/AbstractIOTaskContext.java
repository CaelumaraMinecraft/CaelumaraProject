package top.auspice.tasks.context;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.auspice.tasks.container.TaskSession;

public class AbstractIOTaskContext<I, O> extends AbstractTaskContext implements IOTaskContext<I, O> {
    private final I input;
    @Nullable
    private O output;

    public AbstractIOTaskContext(I input, @NotNull TaskSession session) {
        super(session);
        this.input = input;
    }

    public I getInput() {
        return this.input;
    }

    @Nullable
    public O getOutput() {
        return this.output;
    }

    public void setOutput(@Nullable O var1) {
        this.output = var1;
    }

    @NotNull
    public IOTaskContext<I, O> createNew() {
        AbstractIOTaskContext<I, O> it = new AbstractIOTaskContext<>(this.getInput(), this.getSession());
        it.setState(this.getState());
        return it;
    }

    @NotNull
    public String toString() {
        return "IOTaskContext(" + this.getInput() + " => " + this.getOutput() + ')';
    }
}

