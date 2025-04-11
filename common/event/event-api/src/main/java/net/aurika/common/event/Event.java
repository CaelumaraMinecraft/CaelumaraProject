package net.aurika.common.event;

import org.jetbrains.annotations.NotNull;

/**
 * 事件.
 * <p><b>如何声明一个事件类:</b></p>
 * <blockquote><pre>
 *   interface MyEvent extends Event { }
 * </pre></blockquote>
 *
 * <p><b>如何让声明的事件类能够被监听:</b></p>
 * <blockquote><pre>
 *   // need the {@link Listenable} annotation
 *   {@literal @}Listenable
 *   interface MyListenableEvent extends Event {
 *     // must have the listeners filed and this field has static modifier
 *     public static final ListenerContainer{@literal <}MyListenableEvent{@literal >} LISTENERS = ...;
 *
 *     // must implement the {@link Event#transformer()} method
 *     default ListenerContainer{@literal <}? extends MyListenableEvent{@literal >} listeners() {
 *       return LISTENERS;
 *     }
 *   }
 * </pre></blockquote>
 */
public interface Event {

  /**
   * Gets the {@link Transformer} of the event.
   *
   * @return the listener container
   */
  @NotNull Transformer<? extends Event> transformer();

}
