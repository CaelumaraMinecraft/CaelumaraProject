package net.aurika.common.event;

import net.aurika.common.ident.Ident;
import net.aurika.common.validate.Validate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public interface EventAPI {

  static boolean isCancellable(@NotNull Class<? extends Event> eventClass) {
    Validate.Arg.notNull(eventClass, "eventClass");
    return Cancelable.class.isAssignableFrom(eventClass);
  }

  static boolean isListenable(@NotNull Class<? extends Event> eventClass) {
    Validate.Arg.notNull(eventClass, "eventClass");
    Listenable listenable = eventClass.getAnnotation(Listenable.class);
    if (listenable == null) {
      return false;
    }
    try {
      Method listenersMethod = eventClass.getMethod("emitter");
      if (Modifier.isAbstract(listenersMethod.getModifiers())) {  // require implement this method
        return false;
      }
    } catch (NoSuchMethodException ignored) {
      return false;
    }
    try {
      @Nullable EmitterMethodName emitterMethodNameAnn = eventClass.getAnnotation(EmitterMethodName.class);
      @NotNull String emitterMethodName = emitterMethodNameAnn != null ? emitterMethodNameAnn.value() : EmitterMethodName.DEFAULT_VALUE;
      Method emitterMethod = eventClass.getMethod(emitterMethodName);
      int modifiers = emitterMethod.getModifiers();
      return Modifier.isStatic(modifiers) && Modifier.isPublic(modifiers);
    } catch (NoSuchMethodException e) {
      return false;
    }
  }

  static <E extends Event> @NotNull Emitter<E> getListenerContainer(@NotNull Class<E> eventClass) throws EventNotListenableException {
    Validate.Arg.notNull(eventClass, "eventClass");
    if (!isListenable(eventClass)) {
      throw new EventNotListenableException("Event class " + eventClass.getName() + " is not listenable");
    }
    Listenable listenable = eventClass.getAnnotation(Listenable.class);
    assert listenable != null;

    @Nullable EmitterMethodName emitterMethodNameAnn = eventClass.getAnnotation(EmitterMethodName.class);
    String emitterMethodName = emitterMethodNameAnn != null ? emitterMethodNameAnn.value() : EmitterMethodName.DEFAULT_VALUE;
    @NotNull Method listenersFiled;
    try {
      listenersFiled = eventClass.getMethod(emitterMethodName, new Class[]{});
    } catch (NoSuchMethodException e) {
      throw new RuntimeException(e);
    }
    try {
      listenersFiled.setAccessible(true);
      // noinspection unchecked
      return (Emitter<E>) listenersFiled.invoke(null);
    } catch (IllegalAccessException | InvocationTargetException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Creates a {@link DefaultEmitter} for the {@code eventClass}.
   *
   * @param eventClass the event class
   * @param <E>        the event type
   * @return the created listener container
   * @throws EventNotListenableException when the {@code eventClass} is not listenable
   */
  static <E extends Event> @NotNull DefaultEmitter<E> defaultEmitter(@NotNull Class<E> eventClass) throws EventNotListenableException {
    Validate.Arg.notNull(eventClass, "eventClass");
    return new DefaultEmitter<>(eventClass);
  }

  static <E extends Event> @NotNull DelegateEmitter<E> delegateEmitter(@NotNull Emitter<E> delegate) throws EventNotListenableException {
    Validate.Arg.notNull(delegate, "delegate");
    return new DelegateEmitter<>(delegate);
  }

  /**
   * Reflects and registers reflection listeners. Note that the methods that listen to event must have public modifier.
   * If {@code inherit} is {@code true}, it will reflect the public methods that inherited from super class,
   * otherwise it will only reflect the public methods that decelerated in the class of {@code instance}.
   *
   * @param instance the instance to reflect reflection listener
   * @param inherit  whether reflect the super methods
   */
  @SuppressWarnings({"unchecked", "rawtypes"})
  static void registerReflectListeners(@NotNull Object instance, boolean inherit) {
    Validate.Arg.notNull(instance, "instance");
    Class<?> clazz = instance.getClass();
    Method[] methods = inherit ? clazz.getMethods() : clazz.getDeclaredMethods();
    for (Method method : methods) {
      if (method == null) continue;
      @Nullable EventListener eventListener = method.getAnnotation(EventListener.class);
      if (eventListener != null) {
        if (Modifier.isPublic(method.getModifiers())) {
          int arity = method.getParameterCount();
          if (arity == 1) {
            Class<?> paramType = method.getParameterTypes()[0];
            if (Event.class.isAssignableFrom(paramType)) {
              Class<? extends Event> paramEventType = (Class<? extends Event>) paramType;
              if (EventAPI.isListenable(paramEventType)) {
                Ident listenerIdent = Ident.ident(eventListener.key());
                boolean ignoreCancelled = eventListener.ignoreCancelled();
                Emitter<? extends Event> emitter = EventAPI.getListenerContainer(paramEventType);
                ReflectionListener reflectionListener = new ReflectionListener<>(
                    listenerIdent,
                    method,
                    instance,
                    emitter,
                    ignoreCancelled,
                    paramEventType
                );
                emitter.register(reflectionListener);
              } else {
                throw new EventNotListenableException("Event class " + paramType.getName() + " is not listenable");
              }
            } else {
              throw new IllegalArgumentException("");
            }
          } else {
            throw new IllegalArgumentException(
                "Reflection event handler method must has only one parameter: " + method.toGenericString());
          }
        } else {
          throw new IllegalArgumentException(
              "Reflection event handler method must has public modifier: " + method.toGenericString());
        }
      }
    }
  }

}
