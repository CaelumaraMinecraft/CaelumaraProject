package net.aurika.common.event;

import net.aurika.common.ident.IdentPatterns;
import org.jetbrains.annotations.Range;

public @interface ListenerOrder {

  String NONE = "<none>";
  int VOID_PRIORITY = -1;

  @IdentPatterns.Ident
  String before() default NONE;

  @IdentPatterns.Ident
  String after() default NONE;

  /**
   * The listener priority. The higher priority means the later listener calls. Uses {@link #VOID_PRIORITY} represents don't order by the priority.
   */
  @Range(from = 0, to = 1)
  double priority() default VOID_PRIORITY;

}
