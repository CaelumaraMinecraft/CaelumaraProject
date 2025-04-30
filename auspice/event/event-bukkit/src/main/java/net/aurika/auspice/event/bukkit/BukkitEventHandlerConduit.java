package net.aurika.auspice.event.bukkit;

import net.aurika.common.event.Conduit;
import net.aurika.common.event.Listener;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

/**
 * A bukkit event emitter, it contains a {@link HandlerList}
 *
 * @param <E>
 */
public interface BukkitEventHandlerConduit<E extends org.bukkit.event.Event & BukkitEvent> extends Conduit<E> {

  @Override
  public void transport(@NotNull E event);

  @Override
  public void register(@NotNull Listener<E> listener);

  @Override
  public @NotNull Class<E> eventType();

  /**
   * Gets the bukkit handler list of the emitter.
   *
   * @return the handler list
   */
  public @NotNull HandlerList handlerList();

  @Override
  public @NotNull Listener<E> @NotNull [] listeners();

}
