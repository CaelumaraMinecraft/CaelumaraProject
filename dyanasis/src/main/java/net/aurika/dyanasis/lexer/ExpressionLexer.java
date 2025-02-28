package net.aurika.dyanasis.lexer;

import net.aurika.dyanasis.object.DyanasisObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * The dyanasis expression lexer.
 * <blockquote><pre>
 *     return "string".subString(0, 5)
 *            ^^^^^^^^^^^^^^^^^^^^^^^^
 * </pre></blockquote>
 * <blockquote><pre>
 *     if (str.length > 8) { auspice::plugin::unregister(plugin) } else { doThingOther() }
 *         ^^^^^^^^^^^^^^    ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^          ^^^^^^^^^^^^^^
 *
 *     ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
 * </pre></blockquote>
 */
public interface ExpressionLexer extends DyanasisLexer {

    static @NotNull ExpressionLexerImpl lexerOf(@Nullable DyanasisLexer parent, @NotNull DyanasisLexerSettings settings, @NotNull String expression) {
        return new ExpressionLexerImpl(parent, settings, expression);
    }

    /**
     * Lex and returns the expression.
     *
     * @return the expression
     */
    @Override
    @NotNull Expression lex();

    /**
     * Gets the original string.
     *
     * @return the original string
     */
    @Override
    @NotNull String originalString();

    interface InvokePropertyExpression extends InvokeExpression {
        /**
         * Gets the property name.
         *
         * @return the property name
         */
        @NotNull String propertyName();
    }

    interface TransferToFunctionExpression extends InvokeFunctionExpression {
    }

    /**
     * A function invoking expression.
     */
    interface InvokeFunctionExpression extends InvokeExpression {
        /**
         * Gets the function name.
         *
         * @return the function name
         */
        @NotNull String functionName();

        /**
         * Gets the raw function invoking arguments string.
         *
         * @return the raw arguments string
         */
        @NotNull String argumentsRaw();
    }

    interface InvokeExpression extends Expression {
        /**
         * Gets the expression that be invoked.
         *
         * @return the invoked
         */
        @NotNull Expression invoked();  // TODO rename
    }

    interface VariableThisExpression extends VariableExpression {
        /**
         * Gets this variable is anonymous invoked.
         *
         * @return is anonymous invoked
         */
        boolean isAnonymous();

        @Override
        default @NotNull String variableName() {
            return lexer().settings().idents.self;
        }
    }

    interface VariableExpression extends Expression {
        /**
         * Gets the variable name of the expression.
         *
         * @return the variable name
         */
        @NotNull String variableName();
    }

    interface ConstantExpression extends Expression {
    }

    interface Expression extends DyanasisLexer.Expression {
        @Override
        @NotNull DyanasisObject evaluate();

        @Override
        @NotNull ExpressionLexer lexer();
    }

    interface Operator {

    }

    interface LogicalOperator extends Operator {

    }

    /**
     * An operator who accepts the left operand.
     */
    interface LeftOperator extends Operator {
        /**
         * Accepts the left operand.
         *
         * @param left the left operand
         */
        @NotNull Expression accept(@NotNull Expression left);
    }

    interface BinaryOperator extends Operator {
        @NotNull Expression accept(@NotNull Expression left, @NotNull Expression right);
    }

    enum LogicOperators implements LogicalOperator {

    }
}
