package net.aurika.dyanasis.api.compiler.expression;

import net.aurika.dyanasis.api.object.DyanasisObject;
import org.jetbrains.annotations.NotNull;

/**
 * 一个字面对象声明, 如 {@code null} {@code true} {@code ["a": 123, "b": 546]} {@code "abc"} 等.
 */
public interface DeclareLiteralObject extends Expression, Declare {

  @NotNull DyanasisObject literal();

}
