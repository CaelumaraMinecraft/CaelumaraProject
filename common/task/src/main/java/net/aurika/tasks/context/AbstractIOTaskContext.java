package net.aurika.tasks.context;

import net.aurika.tasks.container.TaskSession;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AbstractIOTaskContext<I, O> extends AbstractTaskContext implements IOTaskContext<I, O> {

  private final I input;

  private @Nullable O output;

  public AbstractIOTaskContext(I input, @NotNull TaskSession session) {
    super(session);
    this.input = input;
  }

  public I getInput() {
    return input;
  }

  public @Nullable O getOutput() {
    return output;
  }

  public void setOutput(@Nullable O output) {
    this.output = output;
  }

  public @NotNull IOTaskContext<I, O> createNew() {
    AbstractIOTaskContext<I, O> it = new AbstractIOTaskContext<>(this.getInput(), this.getSession());
    it.setState(this.getState());
    return it;
  }

  public @NotNull String toString() {
    return "IOTaskContext(" + this.getInput() + " => " + this.getOutput() + ')';
  }

}
