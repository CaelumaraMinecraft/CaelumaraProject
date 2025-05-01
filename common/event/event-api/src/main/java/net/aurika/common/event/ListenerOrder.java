package net.aurika.common.event;

import net.aurika.common.ident.IdentPatterns;
import org.jetbrains.annotations.Range;

public @interface ListenerOrder {

  String NONE_TARGET = "<none>";
  int VOID_PRIORITY = -1;

  @IdentPatterns.Ident
  String before() default NONE_TARGET;

  @IdentPatterns.Ident
  String after() default NONE_TARGET;

  /**
   * The listener priority. The higher priority means the later listener calls. Uses {@link #VOID_PRIORITY} represents don't order by the priority.
   */
  @Range(from = 0, to = 1)
  double priority() default VOID_PRIORITY;

}
