package top.auspice.tasks.annotations;

import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import top.auspice.key.NSedKey;
import top.auspice.tasks.*;
import top.auspice.tasks.container.ConditionalLocalTaskSession;
import top.auspice.tasks.container.LocalTaskSession;
import top.auspice.tasks.context.IOTaskContext;
import top.auspice.tasks.context.InputTaskContext;
import top.auspice.tasks.context.OutputTaskContext;
import top.auspice.tasks.context.TaskContext;
import top.auspice.tasks.priority.PriorityPhase;
import top.auspice.tasks.priority.RelativePriority;
import top.auspice.utils.enumeration.QuickEnumSet;
import top.auspice.utils.reflection.AnnotationContainer;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public final class TaskAnnotationProcessor<C extends TaskContext> {
    @NotNull
    private final Class<? extends LocalTaskSession> container;
    @NotNull
    private final TaskSessionConstructor<C> constructor;
    @Nullable
    private final ParentTask<C> parentTask;

    public TaskAnnotationProcessor(@NotNull Class<? extends LocalTaskSession> adapters, @NotNull TaskSessionConstructor<C> constructor, @Nullable ParentTask<C> parentTask) {
        Objects.requireNonNull(adapters);
        Objects.requireNonNull(constructor);
        this.container = adapters;
        this.constructor = constructor;
        this.parentTask = parentTask;
    }

    public TaskAnnotationProcessor(@NotNull Class<? extends LocalTaskSession> container, @NotNull TaskSessionConstructor<C> constructor) {
        this(container, constructor, null);
    }

    private ProcessedTaskAnnotations processAnnotation(AnnotationContainer container) {
        Task task = container.getAnnotation(Task.class);
        if (task == null) {
            return null;
        } else {
            GroupedTask grouped = AnnotationContainer.of(container.getDeclaringClass()).getAnnotation(GroupedTask.class);
            String groupedNs = grouped == null ? "" : grouped.namespace() + ':';
            NSedKey NSedKey = NSedKey.fromString(groupedNs + task.key());
            if (container instanceof AnnotationContainer.Executable && ((AnnotationContainer.Executable) container).getJavaObject().getParameterCount() > 1) {
                throw new IllegalStateException("Task has more than one parameter: " + ((AnnotationContainer.Executable) container).getJavaObject());
            } else {
                boolean taskStatesInclude = true;
                QuickEnumSet<TaskState> taskStates = null;
                AcceptedTaskStates it = container.getAnnotation(AcceptedTaskStates.class);
                if (it != null) {
                    taskStatesInclude = it.include();
                    taskStates = new QuickEnumSet<>(TaskState.values());
                    CollectionsKt.addAll(taskStates, it.states());
                }

                TaskState var18;
                label46:
                {
                    ReturnTaskState var17 = container.getAnnotation(ReturnTaskState.class);
                    if (var17 != null) {
                        var18 = var17.state();
                        if (var18 != null) {
                            break label46;
                        }
                    }

                    var18 = TaskState.CONTINUE;
                }

                TaskState implicitReturnState = var18;
                Class<? extends Annotation>[] var12 = new Class[]{Priority.class, NumberedPriority.class, Before.class, After.class, Replace.class};
                List<Annotation> priorityAnnotations = container.findAll(var12);
                if (priorityAnnotations.size() > 1) {
                    throw new IllegalStateException("Task cannot have more than one priority annotation. Found " + priorityAnnotations + " for " + container);
                } else {
                    Annotation priorityAnn = CollectionsKt.first(priorityAnnotations);
                    top.auspice.tasks.priority.Priority var19;
                    if (priorityAnn instanceof Priority) {
                        var19 = new top.auspice.tasks.priority.EnumPriority(((Priority) priorityAnn).priority());
                    } else if (priorityAnn instanceof NumberedPriority) {
                        var19 = new top.auspice.tasks.priority.NumberedPriority(((NumberedPriority) priorityAnn).order());
                    } else {
                        RelativePriority.Type relativeType;
                        NSedKey relativeNs;
                        if (priorityAnn instanceof Before) {
                            relativeType = RelativePriority.Type.BEFORE;
                            relativeNs = NSedKey.fromString(((Before) priorityAnn).other());
                            Objects.requireNonNull(relativeNs);
                            var19 = new RelativePriority(relativeType, relativeNs);
                        } else if (priorityAnn instanceof After) {
                            relativeType = RelativePriority.Type.AFTER;
                            relativeNs = NSedKey.fromString(((After) priorityAnn).other());
                            Objects.requireNonNull(relativeNs);
                            var19 = (new RelativePriority(relativeType, relativeNs));
                        } else if (priorityAnn instanceof Replace) {
                            relativeType = RelativePriority.Type.REPLACE;
                            relativeNs = NSedKey.fromString(((Replace) priorityAnn).other());
                            Objects.requireNonNull(relativeNs);
                            var19 = (new RelativePriority(relativeType, relativeNs));
                        } else {
                            var19 = (new top.auspice.tasks.priority.EnumPriority(PriorityPhase.NORMAL));
                        }
                    }

                    top.auspice.tasks.priority.Priority priority = var19;
                    return new ProcessedTaskAnnotations(implicitReturnState, taskStatesInclude, taskStates, priority, NSedKey);
                }
            }
        }
    }

    @NotNull
    public List<top.auspice.tasks.Task<C>> generate() {
        List<top.auspice.tasks.Task<C>> tasks = (new ArrayList<>());
        Method[] methods = this.container.getDeclaredMethods();
        Objects.requireNonNull(methods, "getDeclaredMethods(...)");
        int var3 = 0;

        int var4;
        ProcessedTaskAnnotations settings;
        for (var4 = methods.length; var3 < var4; ++var3) {
            Method method = methods[var3];
            method.setAccessible(true);
            Intrinsics.checkNotNull(method);
            settings = this.processAnnotation(AnnotationContainer.of(method));
            if (settings != null) {
                tasks.add(new ReflectionTask<>(this.constructor, settings, method, this.parentTask));
            }
        }

        Class[] declaredClasses = this.container.getDeclaredClasses();
        var3 = 0;

        for (var4 = declaredClasses.length; var3 < var4; ++var3) {
            Class<? extends LocalTaskSession> innerClass = declaredClasses[var3];
            if (LocalTaskSession.class.isAssignableFrom(innerClass)) {
                if (!ConditionalLocalTaskSession.class.isAssignableFrom(innerClass)) {
                    throw new RuntimeException("Only ConditionalLocalTaskSession are supported for parent tasks: " + innerClass);
                }

                Intrinsics.checkNotNull(innerClass);
                settings = this.processAnnotation(AnnotationContainer.of(innerClass));
                if (settings != null) {
                    DefaultTaskSessionConstructor<C> constructor = new DefaultTaskSessionConstructor<>(innerClass);
                    ReflectionParentTask<C> parentTask = new ReflectionParentTask<>(constructor, settings, this.parentTask);
                    TaskAnnotationProcessor<C> innerProcessor = new TaskAnnotationProcessor<>(innerClass, constructor, parentTask);
                    List<top.auspice.tasks.Task<C>> subTasks = innerProcessor.generate();

                    for (top.auspice.tasks.Task<C> element$iv : subTasks) {
                        parentTask.getSubTasks().register(element$iv);
                    }

                    tasks.add(parentTask);
                }
            }
        }

        return tasks;
    }


    private final class ProcessedTaskAnnotations {
        @NotNull
        private final TaskState implicitReturnState;
        private final boolean taskStatesInclude;
        @Nullable
        private final Set<TaskState> taskStates;
        @NotNull
        private final top.auspice.tasks.priority.Priority priority;
        @NotNull
        private final NSedKey NSedKey;

        public ProcessedTaskAnnotations(@NotNull TaskState implicitReturnState, boolean taskStatesInclude, @Nullable Set<TaskState> taskStates, @NotNull top.auspice.tasks.priority.Priority priority, @NotNull NSedKey NSedKey) {
            Objects.requireNonNull(implicitReturnState, "implicitReturnState");
            Objects.requireNonNull(priority, "priority");
            Objects.requireNonNull(NSedKey, "namespace");
            this.implicitReturnState = implicitReturnState;
            this.taskStatesInclude = taskStatesInclude;
            this.taskStates = taskStates;
            this.priority = priority;
            this.NSedKey = NSedKey;
        }

        @NotNull
        public TaskState getImplicitReturnState() {
            return this.implicitReturnState;
        }

        public boolean getTaskStatesInclude() {
            return this.taskStatesInclude;
        }

        @Nullable
        public Set<TaskState> getTaskStates() {
            return this.taskStates;
        }

        @NotNull
        public top.auspice.tasks.priority.Priority getPriority() {
            return this.priority;
        }

        @NotNull
        public NSedKey getNamespace() {
            return this.NSedKey;
        }
    }


    private static final class ReflectionParentTask<T extends TaskContext> extends AbstractParentTask<T> {
        @NotNull
        private final TaskSessionConstructor<T> constructor;
        @NotNull
        private final TaskAnnotationProcessor<T>.ProcessedTaskAnnotations settings;

        public ReflectionParentTask(@NotNull TaskSessionConstructor<T> constructor, @NotNull TaskAnnotationProcessor<T>.ProcessedTaskAnnotations settings, @Nullable ParentTask<T> parentTask) {
            super(settings.getPriority(), settings.getNamespace(), parentTask);
            this.constructor = constructor;
            this.settings = settings;
        }

        public void run(@NotNull T context) {
            Objects.requireNonNull(context);
            if (this.settings.getTaskStates() == null) {
                if (context.getState() == TaskState.SHOULD_STOP || context.getState() == TaskState.MUST_STOP) {
                    return;
                }
            } else if (this.settings.getTaskStatesInclude() != this.settings.getTaskStates().contains(context.getState())) {
                return;
            }

            LocalTaskSession var10000 = this.constructor.createSession(context);
            Objects.requireNonNull(var10000, "null cannot be cast to non-null type top.auspice.tasks.adapters.ConditionalLocalTaskSession<T of top.auspice.tasks.annotations.TaskAnnotationProcessor.ReflectionParentTask>");
//            Intrinsics.checkNotNull(var10000, "null cannot be cast to non-null type org.kingdoms.tasks.adapters.ConditionalLocalTaskSession<T of org.kingdoms.tasks.annotations.TaskAnnotationProcessor.ReflectionParentTask>");
            ConditionalLocalTaskSession<T> instance = (ConditionalLocalTaskSession) var10000;
            if (instance.shouldExecute(context)) {
                Ref.ObjectRef<T> lastOutput = new Ref.ObjectRef<>();
//                this.getSubTasks().executeDefinedTasks((IOTaskContext)context, ReflectionParentTask::run$lambda$0);
                this.getSubTasks().executeDefinedTasks((IOTaskContext<?, ?>) context, (it) -> lastOutput.element = (T) it);
                if (lastOutput.element != null && context instanceof OutputTaskContext) {
                    ((OutputTaskContext) context).setOutput(lastOutput.element);
                }

            }
        }

//        private static void run$lambda$0(Ref.ObjectRef $lastOutput, Object it) {
//            Objects.requireNonNull($lastOutput, "$lastOutput");
//            Objects.requireNonNull(it, "it");
//            $lastOutput.element = it;
//        }
    }


    private static final class ReflectionTask<T extends TaskContext> extends AbstractTask<T> {
        @NotNull
        private final TaskSessionConstructor<T> constructor;
        @NotNull
        private final TaskAnnotationProcessor<T>.ProcessedTaskAnnotations settings;
        @NotNull
        private final Method method;
        private final boolean needsArg;
        private final boolean directInputArg;
        private final boolean hasReturnValue;

        public ReflectionTask(@NotNull TaskSessionConstructor<T> constructor, @NotNull TaskAnnotationProcessor<T>.ProcessedTaskAnnotations settings, @NotNull Method method, @Nullable ParentTask<T> parentTask) {
            super(settings.getPriority(), settings.getNamespace(), parentTask);
            this.constructor = constructor;
            this.settings = settings;
            this.method = method;
            this.needsArg = this.method.getParameterCount() != 0;
            this.directInputArg = this.needsArg && !TaskContext.class.isAssignableFrom(this.method.getParameterTypes()[0]);
            this.hasReturnValue = !Intrinsics.areEqual(this.method.getReturnType(), Void.class);
        }

        public void run(@NotNull T context) {
            Objects.requireNonNull(context, "context");
            if (this.settings.getTaskStates() == null) {
                if (context.getState().shouldStop()) {
                    return;
                }
            } else if (this.settings.getTaskStatesInclude() != this.settings.getTaskStates().contains(context.getState())) {
                return;
            }

            LocalTaskSession instance = this.constructor.createSession(context);
            Object result;
            try {
                if (this.needsArg) {
                    Method var10000;
                    Object[] var4;
                    if (this.directInputArg) {
                        var10000 = this.method;
                        var4 = new Object[]{((InputTaskContext<?>) context).getInput()};
                        result = var10000.invoke(instance, var4);
                    } else {
                        var10000 = this.method;
                        var4 = new Object[]{context};
                        result = var10000.invoke(instance, var4);
                    }
                } else {
                    result = this.method.invoke(instance);
                }
            } catch (InvocationTargetException | IllegalAccessException e) {
                throw new RuntimeException("Error when invoke Task Method", e);
            }
            if (result != null && context instanceof OutputTaskContext) {
                ((OutputTaskContext) context).setOutput(result);
            }

            if (this.hasReturnValue && result != null) {
                context.setState(this.settings.getImplicitReturnState());
            }

        }
    }
}