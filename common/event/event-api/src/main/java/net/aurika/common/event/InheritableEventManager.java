package net.aurika.common.event;

import net.aurika.common.validate.Validate;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class InheritableEventManager<BE extends Event> implements EventManager<BE> {

  private final @NotNull Map<Class<? extends BE>, Conduit<? extends BE>> emitters;

  public InheritableEventManager() {
    this(new HashMap<>());
  }

  public InheritableEventManager(@NotNull Map<Class<? extends BE>, Conduit<? extends BE>> emitters) {
    Validate.Arg.notNull(emitters, "emitters");
    this.emitters = emitters;
  }

  @Override
  @SuppressWarnings({"rawtypes", "unchecked"})
  public void callEvent(@NotNull BE event) {
    Class<? extends Event> eventClass = event.getClass();
    if (!EventAPI.isListenable(eventClass)) {
      return;  // TODO
    } else {
      for (Class<? extends Event> registeredEventClass : emitters.keySet()) {
        if (registeredEventClass.isAssignableFrom(eventClass) && EventAPI.isListenable(registeredEventClass)) {
          Conduit conduit = emitters.get(registeredEventClass);
          conduit.transport(event);
        }
      }
    }
  }

  @Override
  public @NotNull Collection<? extends Conduit<? extends BE>> conduits() {
    return emitters.values();
  }

  @Override
  public void addConduit(@NotNull Conduit<? extends BE> conduit) {
    Class<? extends BE> eventClass = conduit.eventType();
    if (!EventAPI.isListenable(eventClass)) {
      throw new IllegalArgumentException("Event class on " + conduit + " is not listenable");
    }
    emitters.put(eventClass, conduit);
  }

}
