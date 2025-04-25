package net.aurika.common.event;

import net.aurika.common.validate.Validate;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class InheritableEventManager<BE extends Event> implements EventManager<BE> {

  private final @NotNull Map<Class<? extends BE>, Emitter<? extends BE>> emitters;

  public InheritableEventManager() {
    this(new HashMap<>());
  }

  public InheritableEventManager(@NotNull Map<Class<? extends BE>, Emitter<? extends BE>> emitters) {
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
          Emitter emitter = emitters.get(registeredEventClass);
          emitter.emit(event);
        }
      }
    }
  }

  @Override
  public @NotNull Collection<? extends Emitter<? extends BE>> emitters() {
    return emitters.values();
  }

  @Override
  public void addEmitter(@NotNull Emitter<? extends BE> emitter) {
    Class<? extends BE> eventClass = emitter.eventType();
    if (!EventAPI.isListenable(eventClass)) {
      throw new IllegalArgumentException("Event class on " + emitter + " is not listenable");
    }
    emitters.put(eventClass, emitter);
  }

}
