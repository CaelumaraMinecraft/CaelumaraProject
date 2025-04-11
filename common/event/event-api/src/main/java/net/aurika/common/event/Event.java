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
 * <li>1. 需要被 {@link Listenable} 注解</li>
 * <li>2. 需要有一个静态的, 名称叫 {@code EMITTER} 的字段</li>
 * <li>3. 实现 {@link Event#emitter()} 方法</li>
 * <pre><code>
 *   &#64;Listenable
 *   interface MyListenableEvent extends Event {
 *     public static final ListenerContainer&lt;MyListenableEvent&gt; EMITTER
 *     = EventAPI.reflectionEmitter(MyListenableEvent.class);
 *
 *     default Emitter&lt;? extends MyListenableEvent&gt; emitter() {
 *       return EMITTER;
 *     }
 *   }
 * </code></pre>
 */
public interface Event {

  /**
   * Gets the {@link Emitter} of the event.
   *
   * @return the listener container
   */
  @NotNull Emitter<? extends Event> emitter();

}
