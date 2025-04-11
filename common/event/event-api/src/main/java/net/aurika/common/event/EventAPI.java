package net.aurika.common.event;

import net.aurika.common.key.Key;
import net.aurika.validate.Validate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
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
      Method listenersMethod = eventClass.getMethod("transformer");
      if (Modifier.isAbstract(listenersMethod.getModifiers())) {  // require implement this method
        return false;
      }
    } catch (NoSuchMethodException ignored) {
      return false;
    }
    try {
      Field listenersFiled = eventClass.getField(listenable.listenerContainerFieldName());
      return Modifier.isStatic(listenersFiled.getModifiers());
    } catch (NoSuchFieldException ignored) {
      return false;
    }
  }

  static <E extends Event> @NotNull Transformer<E> getListenerContainer(@NotNull Class<E> eventClass) throws EventNotListenableException {
    Validate.Arg.notNull(eventClass, "eventClass");
    if (!isListenable(eventClass)) {
      throw new EventNotListenableException("Event class " + eventClass.getName() + " is not listenable");
    }
    Listenable listenable = eventClass.getAnnotation(Listenable.class);
    assert listenable != null;
    String listenersFieldName = listenable.listenerContainerFieldName();
    @NotNull Field listenersFiled;
    try {
      listenersFiled = eventClass.getField(listenersFieldName);
    } catch (NoSuchFieldException e) {
      throw new RuntimeException(e);
    }
    try {
      listenersFiled.setAccessible(true);
      // noinspection unchecked
      return (Transformer<E>) listenersFiled.get(null);
    } catch (IllegalAccessException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Creates a {@link DefaultTransformer} for the {@code eventClass}.
   *
   * @param eventClass the event class
   * @param <E>        the event type
   * @return the created listener container
   * @throws EventNotListenableException when the {@code eventClass} is not listenable
   */
  static <E extends Event> @NotNull DefaultTransformer<E> defaultTransformer(@NotNull Class<E> eventClass) throws EventNotListenableException {
    Validate.Arg.notNull(eventClass, "eventClass");
    return new DefaultTransformer<>(eventClass);
  }

  static <E extends Event> @NotNull DelegateTransformer<E> delegateTransformer(@NotNull Transformer<E> delegate) throws EventNotListenableException {
    Validate.Arg.notNull(delegate, "delegate");
    return new DelegateTransformer<>(delegate);
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
                Key listenerKey = Key.key(eventListener.key());
                boolean ignoreCancelled = eventListener.ignoreCancelled();
                Transformer<? extends Event> transformer = EventAPI.getListenerContainer(paramEventType);
                ReflectionListener reflectionListener = new ReflectionListener<>(
                    listenerKey,
                    method,
                    instance,
                    transformer,
                    ignoreCancelled,
                    paramEventType
                );
                transformer.register(reflectionListener);
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
