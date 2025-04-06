package net.aurika.common.event;

import net.aurika.validate.Validate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ListenerContainer<E extends Event> {

  static <E extends Event> void listenerContainer(@NotNull Class<? super E> eventType) {
    Validate.Arg.notNull(eventType, "eventType");
    @Nullable Listenable listenable = eventType.getAnnotation(Listenable.class);
    if (listenable != null) {
      String listenersFieldName = listenable.listenersFieldName();
      try {
        eventType.getField(listenersFieldName);
      } catch (NoSuchFieldException e) {
        throw new IllegalArgumentException(
            "Listeners filed " + listenersFieldName + " not found in class: " + eventType, e);
      }
    } else {
      throw new IllegalArgumentException("eventType is not listenable");
    }
  }

  void listen(@NotNull E event);

  @NotNull Class<? extends E> eventType();

}
