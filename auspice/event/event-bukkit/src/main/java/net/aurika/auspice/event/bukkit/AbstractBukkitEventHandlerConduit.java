package net.aurika.auspice.event.bukkit;

import net.aurika.common.event.EventNotListenableException;
import net.aurika.common.event.Listener;
import net.aurika.common.validate.Validate;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;

import static net.aurika.common.util.empty.EmptyArray.emptyArray;

public abstract class AbstractBukkitEventHandlerConduit<E extends org.bukkit.event.Event & BukkitEvent> implements BukkitEventHandlerConduit<E> {

  private final @NotNull LinkedList<Listener<E>> listeners;
  private final @NotNull HandlerList handlerList;

  public AbstractBukkitEventHandlerConduit(@NotNull HandlerList handlerList) {
    this(new LinkedList<>(), handlerList);
  }

  public AbstractBukkitEventHandlerConduit(@NotNull LinkedList<Listener<E>> listeners, @NotNull HandlerList handlerList) throws EventNotListenableException {
    Validate.Arg.notNull(listeners, "listeners");
    Validate.Arg.notNull(handlerList, "handlerList");
    this.listeners = listeners;
    this.handlerList = handlerList;
  }

  @Override
  public void transport(@NotNull E event) {
    for (Listener<E> listener : listeners()) {
      listener.accept(event);
    }
  }

  @Override
  public void registerListener(@NotNull Listener<E> listener) {
    Validate.Arg.notNull(listener, "listener");
    if (listener.listenedEventType() != eventType()) {
      throw new IllegalArgumentException(
          "The emitter event type " + eventType().getName() + " does not match the listener event type " + listener.listenedEventType());
    }
    listeners.add(listener);
  }

  @Override
  public abstract @NotNull Class<E> eventType();

  @Override
  public @NotNull HandlerList handlerList() {
    return handlerList;
  }

  @Override
  public @NotNull Listener<E> @NotNull [] listeners() {
    // noinspection unchecked
    return listeners.toArray(emptyArray(Listener.class));
  }

  @Override
  public int hashCode() {
    return super.hashCode();
  }

  @Override
  public boolean equals(Object obj) {
    return super.equals(obj);
  }

  @Override
  public String toString() {
    return super.toString();
  }

}
