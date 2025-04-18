package net.aurika.tasks.annotations;

import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.Ref;
import net.aurika.common.ident.Ident;
import net.aurika.tasks.*;
import net.aurika.tasks.container.ConditionalLocalTaskSession;
import net.aurika.tasks.container.LocalTaskSession;
import net.aurika.tasks.context.IOTaskContext;
import net.aurika.tasks.context.InputTaskContext;
import net.aurika.tasks.context.OutputTaskContext;
import net.aurika.tasks.context.TaskContext;
import net.aurika.tasks.priority.EnumPriority;
import net.aurika.tasks.priority.PriorityPhase;
import net.aurika.tasks.priority.RelativePriority;
import net.aurika.util.enumeration.QuickEnumSet;
import net.aurika.util.reflection.AnnotationContainer;
import net.aurika.common.validate.Validate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

public final class TaskAnnotationProcessor<C extends TaskContext> {

  private final @NotNull Class<? extends LocalTaskSession> container;
  private final @NotNull TaskSessionConstructor<C> constructor;
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

  @SuppressWarnings("PatternValidation")
  private ProcessedTaskAnnotations processAnnotation(AnnotationContainer container) {
    Task task = container.getAnnotation(Task.class);
    if (task == null) {
      return null;
    } else {
      GroupedTask grouped = AnnotationContainer.of(container.getDeclaringClass()).getAnnotation(GroupedTask.class);
      String groupedNs = grouped == null ? "" : grouped.namespace() + ':';
      Ident ident = Ident.ident(groupedNs + task.key());
      if (container instanceof AnnotationContainer.Executable && ((AnnotationContainer.Executable) container).getJavaObject().getParameterCount() > 1) {
        throw new IllegalStateException(
            "Task has more than one parameter: " + ((AnnotationContainer.Executable) container).getJavaObject());
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
          throw new IllegalStateException(
              "Task cannot have more than one priority annotation. Found " + priorityAnnotations + " for " + container);
        } else {
          Annotation priorityAnn = CollectionsKt.first(priorityAnnotations);
          net.aurika.tasks.priority.Priority var19;
          if (priorityAnn instanceof Priority) {
            var19 = new EnumPriority(((Priority) priorityAnn).priority());
          } else if (priorityAnn instanceof NumberedPriority) {
            var19 = new net.aurika.tasks.priority.NumberedPriority(((NumberedPriority) priorityAnn).order());
          } else {
            RelativePriority.Type relativeType;
            Ident relativeID;
            if (priorityAnn instanceof Before) {
              relativeType = RelativePriority.Type.BEFORE;
              relativeID = Ident.ident(((Before) priorityAnn).other());
              Objects.requireNonNull(relativeID);
              var19 = new RelativePriority(relativeType, relativeID);
            } else if (priorityAnn instanceof After) {
              relativeType = RelativePriority.Type.AFTER;
              relativeID = Ident.ident(((After) priorityAnn).other());
              Objects.requireNonNull(relativeID);
              var19 = (new RelativePriority(relativeType, relativeID));
            } else if (priorityAnn instanceof Replace) {
              relativeType = RelativePriority.Type.REPLACE;
              relativeID = Ident.ident(((Replace) priorityAnn).other());
              Objects.requireNonNull(relativeID);
              var19 = (new RelativePriority(relativeType, relativeID));
            } else {
              var19 = (new EnumPriority(PriorityPhase.NORMAL));
            }
          }

          net.aurika.tasks.priority.Priority priority = var19;
          return new ProcessedTaskAnnotations(implicitReturnState, taskStatesInclude, taskStates, priority, ident);
        }
      }
    }
  }

  public @NotNull List<net.aurika.tasks.Task<C>> generate() {
    List<net.aurika.tasks.Task<C>> tasks = (new ArrayList<>());
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
          TaskAnnotationProcessor<C> innerProcessor = new TaskAnnotationProcessor<>(
              innerClass, constructor, parentTask);
          List<net.aurika.tasks.Task<C>> subTasks = innerProcessor.generate();

          for (net.aurika.tasks.Task<C> element$iv : subTasks) {
            parentTask.getSubTasks().register(element$iv);
          }

          tasks.add(parentTask);
        }
      }
    }

    return tasks;
  }

  private static final class ProcessedTaskAnnotations {

    private final @NotNull TaskState implicitReturnState;
    private final boolean taskStatesInclude;
    private final @Nullable Set<TaskState> taskStates;
    private final @NotNull net.aurika.tasks.priority.Priority priority;
    private final @NotNull Ident ident;

    public ProcessedTaskAnnotations(@NotNull TaskState implicitReturnState, boolean taskStatesInclude, @Nullable Set<TaskState> taskStates, @NotNull net.aurika.tasks.priority.Priority priority, @NotNull Ident ident) {
      Validate.Arg.notNull(implicitReturnState, "implicitReturnState");
      Validate.Arg.notNull(priority, "priority");
      Validate.Arg.notNull(ident, "key");
      this.implicitReturnState = implicitReturnState;
      this.taskStatesInclude = taskStatesInclude;
      this.taskStates = taskStates;
      this.priority = priority;
      this.ident = ident;
    }

    public @NotNull TaskState implicitReturnState() {
      return this.implicitReturnState;
    }

    public boolean taskStatesInclude() {
      return this.taskStatesInclude;
    }

    public @Nullable Set<TaskState> getTaskStates() {
      return this.taskStates;
    }

    public @NotNull net.aurika.tasks.priority.Priority getPriority() {
      return this.priority;
    }

    public @NotNull Ident getNamespace() {
      return this.ident;
    }

  }

  private static final class ReflectionParentTask<C extends TaskContext> extends AbstractParentTask<C> {

    private final @NotNull TaskSessionConstructor<C> constructor;
    private final @NotNull TaskAnnotationProcessor.ProcessedTaskAnnotations settings;

    public ReflectionParentTask(@NotNull TaskSessionConstructor<C> constructor, @NotNull TaskAnnotationProcessor.ProcessedTaskAnnotations settings, @Nullable ParentTask<C> parentTask) {
      super(settings.getNamespace(), settings.getPriority(), parentTask);
      this.constructor = constructor;
      this.settings = settings;
    }

    public void run(@NotNull C context) {
      Objects.requireNonNull(context);
      if (this.settings.getTaskStates() == null) {
        if (context.getState() == TaskState.SHOULD_STOP || context.getState() == TaskState.MUST_STOP) {
          return;
        }
      } else if (this.settings.taskStatesInclude() != this.settings.getTaskStates().contains(context.getState())) {
        return;
      }

      LocalTaskSession var10000 = this.constructor.createSession(context);
      Objects.requireNonNull(
          var10000,
          "null cannot be cast to non-null type net.aurika.tasks.adapters.ConditionalLocalTaskSession<T of net.aurika.tasks.annotations.TaskAnnotationProcessor.ReflectionParentTask>"
      );
      ConditionalLocalTaskSession<C> instance = (ConditionalLocalTaskSession<C>) var10000;
      if (instance.shouldExecute(context)) {
        Ref.ObjectRef<C> lastOutput = new Ref.ObjectRef<>();
//                this.getSubTasks().executeDefinedTasks((IOTaskContext)context, ReflectionParentTask::run$lambda$0);
        getSubTasks().executeDefinedTasks((IOTaskContext<?, ?>) context, (it) -> lastOutput.element = (C) it);
        if (lastOutput.element != null && context instanceof OutputTaskContext) {
          ((OutputTaskContext) context).setOutput(lastOutput.element);
        }
      }
    }

  }

  private static final class ReflectionTask<C extends TaskContext> extends AbstractTask<C> {

    private final @NotNull TaskSessionConstructor<C> constructor;
    private final @NotNull TaskAnnotationProcessor.ProcessedTaskAnnotations settings;
    private final @NotNull Method method;
    private final boolean needsArg;
    private final boolean directInputArg;
    private final boolean hasReturnValue;

    public ReflectionTask(
        @NotNull TaskSessionConstructor<C> constructor,
        @NotNull TaskAnnotationProcessor.ProcessedTaskAnnotations settings,
        @NotNull Method method,
        @Nullable ParentTask<C> parentTask
    ) {
      super(settings.getNamespace(), settings.getPriority(), parentTask);
      Validate.Arg.notNull(constructor, "constructor");
      Validate.Arg.notNull(settings, "settings");
      Validate.Arg.notNull(method, "method");
      this.constructor = constructor;
      this.settings = settings;
      this.method = method;
      this.needsArg = this.method.getParameterCount() != 0;
      this.directInputArg = this.needsArg && !TaskContext.class.isAssignableFrom(this.method.getParameterTypes()[0]);
      this.hasReturnValue = !Intrinsics.areEqual(this.method.getReturnType(), Void.class);
    }

    public void run(@NotNull C context) {
      Validate.Arg.notNull(context, "context");
      if (this.settings.getTaskStates() == null) {
        if (context.getState().shouldStop()) {
          return;
        }
      } else if (this.settings.taskStatesInclude() != this.settings.getTaskStates().contains(context.getState())) {
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
        context.setState(this.settings.implicitReturnState());
      }
    }

  }

}