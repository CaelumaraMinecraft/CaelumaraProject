package net.aurika.tasks;

import kotlin.jvm.internal.Intrinsics;
import net.aurika.common.key.Key;
import net.aurika.common.key.registry.AbstractKeyedRegistry;
import net.aurika.tasks.annotations.TaskAnnotationProcessor;
import net.aurika.tasks.annotations.TaskSessionConstructor;
import net.aurika.tasks.container.AbstractTaskSession;
import net.aurika.tasks.container.LocalTaskSession;
import net.aurika.tasks.container.TaskSession;
import net.aurika.tasks.context.AbstractIOTaskContext;
import net.aurika.tasks.context.IOTaskContext;
import net.aurika.tasks.context.TaskContext;
import net.aurika.validate.Validate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;

import java.util.*;
import java.util.function.Consumer;

public final class TaskRegistry<C extends TaskContext, T extends Task<C>> extends AbstractKeyedRegistry<T> {

  private final @Nullable ParentTask<C> parentTask;
  private final @NotNull LinkedList<T> usableList;
  private boolean needsUpdating;

  public TaskRegistry(@Nullable ParentTask<C> parentTask) {
    super(new LinkedHashMap<>());
    this.parentTask = parentTask;
    this.usableList = new LinkedList<>();
    this.needsUpdating = true;
  }

  @Override
  public void register(@NotNull T task) {
    Objects.requireNonNull(task, "task");
    Key key = task.key();
    Objects.requireNonNull(key, "Cannot register task with null key");
    if (this.parentTask != null && !Objects.equals(task.parent(), this.parentTask)) {
      throw new IllegalArgumentException("Task parent mismatch: " + task + " not a child of " + this.parentTask);
    } else {
      Task<C> prev = this.rawRegistry().putIfAbsent(key, task);
      if (prev != null) {
        String var5 = task + " was already registered";
        throw new IllegalArgumentException(var5);
      } else {
        this.usableList.add(task);
        this.needsUpdating = true;
      }
    }
  }

  public @NotNull @Unmodifiable List<T> getUsableList() {
    if (needsUpdating) {
      usableList.sort(new TaskComparator());
      needsUpdating = false;
    }

    return Collections.unmodifiableList(usableList);
  }

  public <I, O> @NotNull TaskContext executeTasks(I input, @NotNull Consumer<O> onOutput) {
    Objects.requireNonNull(onOutput);
    TaskSession session = new AbstractTaskSession();
    IOTaskContext<I, O> context = new AbstractIOTaskContext<>(input, session);
    executeDefinedTasks(context, onOutput);
    return context;
  }

  public <I, O> void executeDefinedTasks(@NotNull IOTaskContext<I, O> context, @NotNull Consumer<O> onOutput) {
    Validate.Arg.notNull(context, "context");
    Validate.Arg.notNull(onOutput, "onOutput");

    for (T task : getUsableList()) {
      IOTaskContext<I, O> subContext = context.createNew();
      Objects.requireNonNull(subContext, "subContext");

      try {
        task.run((C) subContext);
      } catch (Throwable throwable) {
        (new RuntimeException(
            "Error while running task " + task + " with context " + subContext, throwable)).printStackTrace();
        subContext.setState(TaskState.ERROR);
      }

      context.setState(subContext.getState());
      if (subContext.getState() == TaskState.MUST_STOP) {
        return;
      }

      if (subContext.getOutput() != null) {
        context.setOutput(subContext.getOutput());
        onOutput.accept(subContext.getOutput());
      }
    }
  }

  public void register(@NotNull Class<? extends LocalTaskSession> container) {
    Validate.Arg.notNull(container, "container");
    register(container, new DefaultTaskSessionConstructor<>(container));
  }

  public void register(@NotNull Class<? extends LocalTaskSession> container, @NotNull TaskSessionConstructor<C> constructor) {
    Validate.Arg.notNull(container, "container");
    Validate.Arg.notNull(constructor, "constructor");
    DefinedTaskSessionConstructor<C> delegateConstructor = new DefinedTaskSessionConstructor<>(constructor);
    List<Task<C>> $this$forEach$iv = new TaskAnnotationProcessor<>(container, delegateConstructor, null).generate();

    for (Task<C> element$iv : $this$forEach$iv) {
      T it = (T) element$iv;
      Intrinsics.checkNotNull(
          it, "null cannot be cast to non-null type T of net.aurika.auspice.tasks.TaskRegistry.register$lambda$1");
      register(it);
    }
  }

  @Override
  public boolean equals(Object obj) {
    return this == obj;
  }

  private final class TaskComparator implements Comparator<Task<C>> {

    public TaskComparator() {
    }

    @Override
    public int compare(@NotNull Task<C> first, @NotNull Task<C> second) {
      Validate.Arg.notNull(first, "first");
      Validate.Arg.notNull(second, "second");
      return first.compareTo(second, TaskRegistry.this);
    }

  }

}