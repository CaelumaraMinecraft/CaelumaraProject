package net.aurika.dyanasis.api.compiler.expression;

import net.aurika.dyanasis.api.compiler.operator.Operator;
import org.jetbrains.annotations.NotNull;

/**
 * 数学或逻辑操作.
 * 一个操作语句, 包含一个或多个 {@linkplain Expression operand} 以及一个操作符 {@linkplain Operator operator}.
 * 如 {@code a + b} {@code a > b} {@code "in" == b} {@code a && b} {@code a || b} 等.
 */
public interface Operation extends Statement {

  @NotNull Operator operator();

}
