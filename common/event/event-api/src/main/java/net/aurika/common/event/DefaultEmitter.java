package net.aurika.common.event;

import net.aurika.validate.Validate;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import static net.aurika.common.util.empty.EmptyArray.emptyArray;

public class DefaultEmitter<E extends Event> implements Emitter<E> {

  private final @NotNull Class<E> eventType;
  protected @NotNull Emitter<? super E> @NotNull [] parents;
  protected @NotNull LinkedList<Listener<E>> listeners;

  protected DefaultEmitter(@NotNull Class<E> eventType) throws EventNotListenableException {
    Validate.Arg.notNull(eventType, "eventType");
    if (!EventAPI.isListenable(eventType)) {
      throw new EventNotListenableException("Event class " + eventType.getName() + " is not listenable");
    }
    this.eventType = eventType;
    refreshParents();
    this.listeners = new LinkedList<>();
  }

  @SuppressWarnings({"unchecked", "rawtypes"})
  @Contract(mutates = "this")
  protected void refreshParents() {
    Class<? super E>[] supers = Util.getSupers(eventType);
    List<Emitter<? super E>> parentContainers = new LinkedList<>();
    for (int i = 0; i < supers.length; i++) {
      Class<? super E> superType = supers[i];
      if (Event.class.isAssignableFrom(superType)) {
        // Class<? extends Event & super E>
        Class<? extends Event> fixedSuperType = (Class<? extends Event>) superType;
        if (EventAPI.isListenable(fixedSuperType)) {
          // ListenerContainer<? extends Event & super E>
          Emitter parentListenerList = EventAPI.getListenerContainer(fixedSuperType);
          parentContainers.add(parentListenerList);
        }
      }
    }
    this.parents = parentContainers.toArray(new Emitter[0]);
  }

  @Override
  public void emit(@NotNull E event) {
    for (Listener<E> listener : listeners()) {
      listener.accept(event);
    }
    for (Emitter<? super E> deepParent : allParentTransformers()) {
      deepParent.emit(event);
    }
  }

  @Override
  public void register(@NotNull Listener<E> listener) {    // TODO 顺序排列
    listeners.add(listener);
  }

  @Override
  public @NotNull Class<E> eventType() {
    return eventType;
  }

  @Override
  public @NotNull Emitter<? super E> @NotNull [] directParentTransformers() {
    return parents.clone();
  }

  @Override
  public @NotNull Set<@NotNull Emitter<? super E>> allParentTransformers() {
    LinkedHashSet<Emitter<? super E>> allParents = new LinkedHashSet<>();
    for (Emitter<? super E> parentEmitter : parents) {
      allParents.addAll(parentEmitter.allParentTransformers());
    }
    return allParents;
  }

  @Override
  public @NotNull Listener<E> @NotNull [] listeners() {
    // noinspection unchecked
    return listeners.toArray(emptyArray(Listener.class));
  }

}
