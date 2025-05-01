package net.aurika.common.event;

import net.aurika.common.ident.Ident;
import net.aurika.common.validate.Validate;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import static net.aurika.common.util.empty.EmptyArray.emptyArray;

public class DefaultConduit<E extends Event> implements Conduit<E> {

  private final @NotNull Class<E> eventType;
  protected @NotNull LinkedList<Listener<E>> listenerList;
  protected @NotNull Map<Ident, Listener<E>> listenerMap;

  protected DefaultConduit(@NotNull Class<E> eventType) throws EventNotListenableException {
    Validate.Arg.notNull(eventType, "eventType");
    if (!EventAPI.isListenable(eventType)) {
      throw new EventNotListenableException("Event class " + eventType.getName() + " is not listenable");
    }
    this.eventType = eventType;
    this.listenerList = new LinkedList<>();
    this.listenerMap = new HashMap<>();
  }

  @Override
  public void transport(@NotNull E event) throws IllegalArgumentException {
    if (eventType.isInstance(event)) {
      for (Listener<E> listener : listeners()) {
        listener.accept(event);
      }
    } else {
      throw new IllegalArgumentException(
          "Requires event type " + eventType.getName() + ", the input event type: " + event.getClass().getName() + " is not supported");
    }
  }

  @Override
  public void registerListener(@NotNull Listener<E> listener) {
    Validate.Arg.notNull(listener, "listener");
    if (listener.listenedEventType() != eventType) {
      throw new IllegalArgumentException(
          "The emitter event type " + eventType.getName() + " does not match the listener event type " + listener.listenedEventType());
    }
    Ident id = listener.ident();
    if (listenerMap.containsKey(id)) {
      throw new IllegalArgumentException("Listener of id: " + listener.ident() + " is already registered");
    }
    unsafeAddListener(listener);
  }

  protected void unsafeAddListener(@NotNull Listener<E> listener) {
    listenerList.add(listener);
    listenerMap.put(listener.ident(), listener);
  }

  @Override
  public @NotNull Class<E> eventType() { return eventType; }

  @Override
  public int listenersCount() {
    return listenerList.size();
  }

  @Override
  public @NotNull Listener<E> @NotNull [] listeners() {
    // noinspection unchecked
    return listenerList.toArray(emptyArray(Listener.class));
  }

  @Override
  public @NotNull Listener<E> listenerAtIndex(int index) { return listenerList.get(index); }

  @Override
  public boolean hasListener(@NotNull Ident id) {
    return listenerMap.containsKey(id);
  }

  @Override
  public @NotNull Listener<E> listenerById(@NotNull Ident id) {
    if (!listenerMap.containsKey(id)) {
      throw new IllegalArgumentException("Listener of id: " + id + " is not registered");
    } else {
      return listenerMap.get(id);
    }
  }

  @Override
  public int hashCode() { return super.hashCode(); }

  @Override
  public boolean equals(Object obj) { return super.equals(obj); }

  @Override
  public @NotNull String toString() { return getClass().getSimpleName() + "(" + eventType + ")"; }

}
