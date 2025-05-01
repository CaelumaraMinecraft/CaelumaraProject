package net.aurika.common.event;

import net.aurika.common.validate.Validate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class DefaultEventManager<BE extends Event> implements EventManager<BE> {

  private final @NotNull Class<BE> baseEventType;
  private final @NotNull Map<Class<? extends BE>, Conduit<? extends BE>> conduits;

  public DefaultEventManager(@NotNull Class<BE> baseEventType) {
    this(baseEventType, new HashMap<>());
  }

  public DefaultEventManager(@NotNull Class<BE> baseEventType, @NotNull Map<Class<? extends BE>, Conduit<? extends BE>> conduits) {
    Validate.Arg.notNull(baseEventType, "baseEventType");
    Validate.Arg.notNull(conduits, "emitters");
    this.baseEventType = baseEventType;
    this.conduits = conduits;
  }

  @Override
  @SuppressWarnings({"rawtypes", "unchecked"})
  public void callEvent(@NotNull BE event) {
    Validate.Arg.notNull(event, "event");
    if (baseEventType.isInstance(event)) {
      Class<? extends Event> eventClass = event.getClass();
      if (!EventAPI.isListenable(eventClass)) {
        return;  // TODO
      } else {
        for (Class<? extends Event> registeredEventClass : conduits.keySet()) {
          if (registeredEventClass.isAssignableFrom(eventClass) && EventAPI.isListenable(registeredEventClass)) {
            @Nullable Conduit conduit = this.conduits.get(registeredEventClass);
            if (conduit != null) { conduit.transport(event); }
          }
        }
      }
    } else {
      throw new IllegalArgumentException(
          "Event must be assignable from: " + baseEventType.getName() + ", but got" + event.getClass().getName());
    }
  }

  public @NotNull Class<BE> baseEventType() { return baseEventType; }

  @Override
  public @NotNull Collection<? extends Conduit<? extends BE>> conduits() {
    return conduits.values();
  }

  @Override
  public void addConduit(@NotNull Conduit<? extends BE> conduit) {
    Class<? extends BE> eventClass = conduit.eventType();
    if (!EventAPI.isListenable(eventClass)) {
      throw new IllegalArgumentException("Event class for conduit" + conduit + " is not listenable");
    }
    this.conduits.put(eventClass, conduit);
  }

}
