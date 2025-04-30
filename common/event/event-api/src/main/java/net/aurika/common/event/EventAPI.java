package net.aurika.common.event;

import net.aurika.common.ident.Ident;
import net.aurika.common.validate.Validate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public final class EventAPI {

  @Deprecated
  static boolean isCancellable(@NotNull Class<? extends Event> eventClass) {
    Validate.Arg.notNull(eventClass, "eventClass");
    return Cancelable.class.isAssignableFrom(eventClass);
  }

  public static boolean isListenable(@NotNull Class<? extends Event> eventClass) {
    Validate.Arg.notNull(eventClass, "eventClass");
    Listenable listenable = eventClass.getAnnotation(Listenable.class);
    if (listenable == null) {
      return false;
    }
    try {
      Method listenersMethod = eventClass.getMethod("eventConduit");
      if (Modifier.isAbstract(listenersMethod.getModifiers())) {  // require implement this method
        return false;
      }
    } catch (NoSuchMethodException ignored) {
      return false;
    }
    try {
      @Nullable ConduitGetter conduitGetterAnn = eventClass.getAnnotation(ConduitGetter.class);
      @NotNull String emitterMethodName = conduitGetterAnn != null ? conduitGetterAnn.value() : ConduitGetter.DEFAULT_VALUE;
      Method emitterMethod = eventClass.getMethod(emitterMethodName);
      int modifiers = emitterMethod.getModifiers();
      return Modifier.isStatic(modifiers) && Modifier.isPublic(modifiers);
    } catch (NoSuchMethodException e) {
      return false;
    }
  }

  /**
   * Gets the conduit for an event type.
   *
   * @param eventClass the event type
   * @param <E>        the event type
   * @return the conduit
   * @throws EventNotListenableException when the {@code eventType} is not listenable
   */
  public static <E extends Event> @NotNull Conduit<E> getConduit(@NotNull Class<E> eventClass) throws EventNotListenableException {
    Validate.Arg.notNull(eventClass, "eventClass");
    if (!isListenable(eventClass)) {
      throw new EventNotListenableException("Event class " + eventClass.getName() + " is not listenable");
    }
    Listenable listenable = eventClass.getAnnotation(Listenable.class);
    assert listenable != null : "Event class " + eventClass.getName() + " is not listenable";

    @Nullable ConduitGetter conduitGetterAnn = eventClass.getAnnotation(ConduitGetter.class);
    String emitterMethodName = conduitGetterAnn != null ? conduitGetterAnn.value() : ConduitGetter.DEFAULT_VALUE;
    @NotNull Method conduitGetter;
    try {
      conduitGetter = eventClass.getMethod(emitterMethodName);
    } catch (NoSuchMethodException e) {
      throw new RuntimeException(e);
    }
    try {
      conduitGetter.setAccessible(true);
      // noinspection unchecked
      return (Conduit<E>) conduitGetter.invoke(null);
    } catch (IllegalAccessException | InvocationTargetException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Creates a {@link DefaultConduit} for the {@code eventClass}.
   *
   * @param eventClass the event class
   * @param <E>        the event type
   * @return the created listener container
   * @throws EventNotListenableException when the {@code eventClass} is not listenable
   */
  public static <E extends Event> @NotNull DefaultConduit<E> defaultConduit(@NotNull Class<E> eventClass) throws EventNotListenableException {
    Validate.Arg.notNull(eventClass, "eventClass");
    return new DefaultConduit<>(eventClass);
  }

  @Deprecated
  public static <E extends Event> @NotNull DelegateConduit<E> delegateEmitter(@NotNull Conduit<E> delegate) throws EventNotListenableException {
    Validate.Arg.notNull(delegate, "delegate");
    return new DelegateConduit<>(delegate);
  }

  /**
   * Reflects and registers reflection listeners. Note that the methods that listen to event must have public modifier.
   * If {@code inherit} is {@code true}, it will reflect the public methods that inherited from super class,
   * otherwise it will only reflect the public methods that decelerated in the class of {@code instance}.
   *
   * @param instance the instance to reflect reflection listener
   * @param inherit  whether reflect the super methods
   */
  @SuppressWarnings({"unchecked", "rawtypes", "PatternValidation"})
  public static void registerReflectListeners(@NotNull Object instance, boolean inherit) {
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
                Ident listenerIdent = Ident.ident(eventListener.id());
                Conduit<? extends Event> conduit = EventAPI.getConduit(paramEventType);
                ReflectionListener reflectionListener = new ReflectionListener<>(
                    listenerIdent,
                    method,
                    instance,
                    paramEventType
                );
                conduit.registerListener(reflectionListener);
              } else {
                throw new EventNotListenableException("Event class " + paramType.getName() + " is not listenable");
              }
            } else {
              throw new IllegalArgumentException(
                  "The method argument type " + paramType + " is not assignable to " + Event.class.getName());
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
