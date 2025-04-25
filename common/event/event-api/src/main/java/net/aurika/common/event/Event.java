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
 * <pre><code>
 *   // 需要被 net.aurika.common.event.Listenable 注解
 *   &#64;net.aurika.common.event.Listenable
 *   interface MyListenableEvent extends Event {
 *     public static final ListenerContainer&lt;MyListenableEvent&gt; EMITTER
 *     = EventAPI.reflectionEmitter(MyListenableEvent.class);
 *     // 需要有一个静态的, 名称叫 emitter 的无形参方法
 *     public static Emitter&lt;? extends MyListenableEvent&gt; emitter() {
 *       return EMITTER;
 *     }
 *   }
 * </code></pre>
 *
 * 因为事件支持多继承, 且静态方法没有 "继承" 这一说法, 所以事件类需要实现 {@link Event#eventEmitter()} 方法来确定当前事件对应的 emitter
 */
public interface Event {

  @NotNull Emitter<? extends Event> eventEmitter();

}
