package net.aurika.dyanasis.api.compiler.operator;

import net.aurika.dyanasis.api.compiler.expression.Expression;
import net.aurika.dyanasis.api.compiler.expression.Operation;
import org.jetbrains.annotations.NotNull;

public interface UnaryOperator extends Operator {

  @NotNull Operation accept(@NotNull Expression operand);

}
