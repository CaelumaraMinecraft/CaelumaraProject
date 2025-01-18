package top.auspice.tasks;

import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.Unmodifiable;
import top.auspice.key.NSedKey;
import top.auspice.key.NSKedRegistry;
import top.auspice.main.Auspice;
import top.auspice.tasks.annotations.TaskAnnotationProcessor;
import top.auspice.tasks.annotations.TaskSessionConstructor;
import top.auspice.tasks.container.AbstractTaskSession;
import top.auspice.tasks.container.LocalTaskSession;
import top.auspice.tasks.container.TaskSession;
import top.auspice.tasks.context.AbstractIOTaskContext;
import top.auspice.tasks.context.IOTaskContext;
import top.auspice.tasks.context.TaskContext;

import java.util.*;
import java.util.function.Consumer;

public final class TaskRegistry<C extends TaskContext, T extends Task<C>> extends NSKedRegistry<T> {
    @Nullable
    private final ParentTask<C> parentTask;
    @NotNull
    private final LinkedList<T> usableList;
    private boolean needsUpdating;

    public TaskRegistry(@Nullable ParentTask<C> parentTask) {
        super(Auspice.get(), "TASK");         //todo
        this.parentTask = parentTask;
        this.usableList = new LinkedList<>();
        this.needsUpdating = true;
    }

    public void register(@NotNull T task) {
        Objects.requireNonNull(task);
        NSedKey NSedKey = task.getNamespacedKey();
        Objects.requireNonNull(NSedKey, "Cannot register task with null namespace");
        if (this.parentTask != null && !Intrinsics.areEqual(task.getParent(), this.parentTask)) {
            throw new IllegalArgumentException("Task parent mismatch: " + task + " not a child of " + this.parentTask);
        } else {
            Task<C> prev = this.registered.putIfAbsent(NSedKey, task);
            if (prev != null) {
                String var5 = task + " was already registered";
                throw new IllegalArgumentException(var5);
            } else {
                this.usableList.add(task);
                this.needsUpdating = true;
            }
        }
    }

    @NotNull
    @Unmodifiable
    public List<T> getUsableList() {
        if (this.needsUpdating) {
            CollectionsKt.sortWith(this.usableList, new TaskComparator());
            this.needsUpdating = false;
        }

        return Collections.unmodifiableList(this.usableList);
    }

    @NotNull
    public <I, O> TaskContext executeTasks(I input, @NotNull Consumer<O> onOutput) {
        Objects.requireNonNull(onOutput);
        TaskSession session = new AbstractTaskSession();
        IOTaskContext<I, O> context = new AbstractIOTaskContext<>(input, session);
        this.executeDefinedTasks(context, onOutput);
        return context;
    }

    public <I, O> void executeDefinedTasks(@NotNull IOTaskContext<I, O> context, @NotNull Consumer<O> onOutput) {
        Objects.requireNonNull(context);
        Objects.requireNonNull(onOutput);

        for (T task : this.getUsableList()) {
            IOTaskContext<I, O> subContext = context.createNew();
            Objects.requireNonNull(subContext, "null cannot be cast to non-null type top.auspice.tasks.context.IOTaskContext<I of top.auspice.tasks.TaskRegistry.executeDefinedTasks, O of top.auspice.tasks.TaskRegistry.executeDefinedTasks>");

            try {
                task.run((C) subContext);
            } catch (Throwable throwable) {
                (new RuntimeException("Error while running task " + task + " with context " + subContext, throwable)).printStackTrace();
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
        Objects.requireNonNull(container);
        this.register(container, new DefaultTaskSessionConstructor<>(container));
    }

    public void register(@NotNull Class<? extends LocalTaskSession> container, @NotNull TaskSessionConstructor<C> constructor) {
        Objects.requireNonNull(container);
        Objects.requireNonNull(constructor);
        DefinedTaskSessionConstructor<C> delegateConstructor = new DefinedTaskSessionConstructor<>(constructor);
        List<Task<C>> $this$forEach$iv = new TaskAnnotationProcessor<>(container, delegateConstructor, null).generate();

        for (Task<C> element$iv : $this$forEach$iv) {
            T it = (T) element$iv;
            Intrinsics.checkNotNull(it, "null cannot be cast to non-null type T of top.auspice.tasks.TaskRegistry.register$lambda$1");
            this.register(it);
        }

    }


    @Override
    public boolean equals(Object obj) {
        return this == obj;
    }

    private final class TaskComparator implements Comparator<Task<C>> {
        public TaskComparator() {
        }

        public int compare(@NotNull Task<C> first, @NotNull Task<C> second) {
            Objects.requireNonNull(first);
            Objects.requireNonNull(second);
            return first.compareTo(second, TaskRegistry.this);
        }
    }
}