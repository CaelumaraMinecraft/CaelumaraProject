package net.aurika.auspice.event.bukkit;

import net.aurika.auspice.event.api.MinecraftEvent;
import net.aurika.auspice.event.api.MinecraftEventManager;
import net.aurika.common.event.Conduit;
import net.aurika.common.event.DefaultEventManager;
import net.aurika.common.validate.Validate;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class BukkitEventManager extends DefaultEventManager<MinecraftEvent> implements MinecraftEventManager {

  private final @NotNull BukkitEventGenerator eventGenerator;

  public BukkitEventManager(@NotNull BukkitEventGenerator eventGenerator) {
    super(MinecraftEvent.class);
    Validate.Arg.notNull(eventGenerator, "eventGenerator");
    this.eventGenerator = eventGenerator;
  }

  public BukkitEventManager(@NotNull BukkitEventGenerator eventGenerator, @NotNull Map<Class<? extends MinecraftEvent>, Conduit<? extends MinecraftEvent>> emitters) {
    super(MinecraftEvent.class, emitters);
    Validate.Arg.notNull(eventGenerator, "eventGenerator");
    this.eventGenerator = eventGenerator;
  }

  /**
   * 生成一个继承自 Bukkit 事件类, 并实现一个抽象事件接口的类. 若缓存中已经有符合要求的此类, 则直接返回缓存的.
   *
   * @param eventInterface  the event interface, not bukkit event type, and it must have the {@link NativeBukkitEvent} annotation.
   * @param otherInterfaces other interfaces the subclass implemented
   */
  public <E extends org.bukkit.event.Event & BukkitEvent> @NotNull Class<? extends E> generateEventBounding(
      @NotNull Class<? extends BukkitEvent> eventInterface,
      @NotNull Class<?> @NotNull [] otherInterfaces
  ) {
    NativeBukkitEvent boundAnn = eventInterface.getAnnotation(NativeBukkitEvent.class);
    if (boundAnn == null) {
      throw new IllegalArgumentException(
          "Event interface " + eventInterface.getName() + " is not annotated with " + NativeBukkitEvent.class.getName());
    }
    Class<? extends org.bukkit.event.Event> bukkitEventClass = boundAnn.value();
    return eventGenerator.generateEventClass(bukkitEventClass, eventInterface, otherInterfaces);
  }

  public @NotNull BukkitEventGenerator eventGenerator() { return this.eventGenerator; }

}
