package net.aurika.dyanasis.lexer;

import net.aurika.dyanasis.object.DyanasisObject;
import net.aurika.validate.Validate;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * The standard implementation of {@linkplain ExpressionLexer}.
 */
public class ExpressionLexerImpl extends DyanasisLexerImpl implements ExpressionLexer {

    protected int ap;  // pointer A, move in forward
    protected int bp;  // pointer B, move in reverse

    /**
     * Create.
     *
     * @param parent   the parent lexer
     * @param original the original string
     */
    public ExpressionLexerImpl(@Nullable DyanasisLexer parent, @NotNull DyanasisLexerSettings settings, @NotNull String original) {
        super(parent, settings, original);
        this.ap = 0;
        this.bp = original.length();
    }

    @Override
    public @NotNull AbstractExpr lex() {
        skipWhitespaces();
        @Nullable var anonymousThisInvoke = lexAnonymousSelfInvoke();
    }

    /**
     * Lex self marker that probably be anonymous. It will change the pointer {@link #ap} to the end of self marker.
     * <blockquote><pre>
     * string      |    this.load_00(params)
     * skip ws     |---->
     * self invoke |    ------------>
     *
     * or:
     *
     * string      |    load_00(params)
     * skip ws     |---->
     * self invoke |    ------->
     * </pre></blockquote>
     *
     * @return the lexed invoking, maybe {@code null}
     */
    protected @Nullable AbstractInvokeExpr lexAnonymousSelfInvoke() {
        skipWhitespaces(false);
        if (startWith(settings.idents.self, ap)) {  // after skipping ws, if start with self ident
            ap = ap + settings.idents.self.length();
            skip(settings.idents.invoke);
            return new VariableThisExprImpl();
        } else {
            int invEnd = findInvoke(ap);
            if (invEnd > ap) {  // if found an invoking
                ap = invEnd + 1;
                return new VariableThisExprImpl();
            } else {
                return null;
            }
        }
    }

    protected @Nullable InvokeExpression lexInvoke(@NotNull ExpressionLexer.Expression invoked) {

    }

    /**
     * Skips the present prefix {@code prefix}.
     *
     * @param prefix the prefix to skip
     */
    protected void skip(@NotNull String prefix) {
        if (original.startsWith(prefix, ap)) {
            ap = ap + prefix.length();
        }
    }

    /**
     * Skips whitespaces forward or backward. It will change the pointer {@link #ap} to the nearest char that isn't a white space.
     *
     * @param reverse is reversed skipping
     */
    protected void skipWhitespaces(boolean reverse) {
        if (reverse) {
            bp = findChar((c) -> !Character.isWhitespace(c), true, bp);
        } else {
            ap = findChar((c) -> !Character.isWhitespace(c), false, ap);
        }
    }

    protected static boolean isLogicChar(char c) {
        return switch (c) {
            case '(', ')',  // such as function invoking parameters,
                 '<', '>',
                 '{', '}',
                 '.',  //
                 ',',  //
                 '"',  // string
                 '!',
                 '=',
                 '-',
                 '_',
                 '@',
                 '#',
                 '$',
                 '%',
                 '^',
                 '&',
                 '*',
                 '~',
                 '`' -> true;
            default -> false;
        };
    }

    protected int i_ap() {
        return i_ap(1);
    }

    protected int i_bp() {
        return i_bp(1);
    }

    /**
     * Increases the pointer {@link #ap}.
     *
     * @return the increased pointer
     * @throws IndexOutOfBoundsException when the increased index is out of bound
     */
    protected int i_ap(int step) {
        int added = ap + step;
        if (added > bp) {
            throw new IndexOutOfBoundsException();
        }
        ap = added;
        return ap;
    }

    /**
     * Revers increases the pointer {@link #bp}.
     *
     * @return the increased pointer
     * @throws IndexOutOfBoundsException when the increased index is out of bound
     */
    protected int i_bp(int step) {
        int added = bp - step;
        if (added < ap) {
            throw new IndexOutOfBoundsException();
        }
        bp = added;
        return bp;
    }

    /**
     * Gets the chat that has been indexed by the primary pointer.
     *
     * @return the indexed char
     */
    @Contract(pure = true)
    protected char ap_c() {
        return original.charAt(ap);
    }

    /**
     * Gets the chat that has been indexed by the secondary pointer.
     *
     * @return the indexed char
     */
    @Contract(pure = true)
    protected char bp_c() {
        return original.charAt(bp);
    }

    /**
     * Gets this lexer is lex done.
     *
     * @return is lex done
     */
    public boolean isDone() {
        return ap >= bp;
    }

    @Contract(pure = true)
    protected char ap_n_c() {
        return original.charAt(ap + 1);
    }

    @Contract(pure = true)
    protected char bp_n_c() {
        return original.charAt(bp + 1);
    }

    @Contract(pure = false)
    protected char i_ap_c() {
        ap = ap + 1;
        return original.charAt(ap);
    }

    @Contract(pure = false)
    protected char i_bp_c() {
        bp = bp - 1;
        return original.charAt(bp);
    }

    public class InvokeFunctionExprImpl extends AbstractInvokeExpr implements InvokeFunctionExpression {
        private final @NotNull String functionName;
        private final @NotNull String argumentsRaw;

        public InvokeFunctionExprImpl(@NotNull AbstractExpr invoked, @NotNull String functionName, @NotNull String argumentsRaw) {
            super(invoked);
            Validate.Arg.notNull(functionName, "functionName");
            Validate.Arg.notNull(argumentsRaw, "argumentsRaw");
            this.functionName = functionName;
            this.argumentsRaw = argumentsRaw;
        }

        @Override
        public @NotNull DyanasisObject evaluate() {
            TODO
        }

        @Override
        public @NotNull String functionName() {
            return functionName;
        }

        @Override
        public @NotNull String argumentsRaw() {
            return argumentsRaw;
        }
    }

    public class InvokePropertyExprImpl extends AbstractInvokeExpr implements InvokePropertyExpression {
        private final @NotNull String propertyName;

        public InvokePropertyExprImpl(@NotNull ExpressionLexerImpl.AbstractExpr invoked, @NotNull String propertyName) {
            super(invoked);
            Validate.Arg.notNull(propertyName, "propertyName");
            this.propertyName = propertyName;
        }

        @Override
        public @NotNull String propertyName() {
            return propertyName;
        }

        @Override
        public @NotNull DyanasisObject evaluate() {
            TODO
        }
    }

    public abstract class AbstractInvokeExpr extends AbstractExpr implements InvokeExpression {
        private final @NotNull ExpressionLexerImpl.AbstractExpr invoked;

        protected AbstractInvokeExpr(@NotNull ExpressionLexerImpl.AbstractExpr invoked) {
            Validate.Arg.notNull(invoked, "invoked");
            this.invoked = invoked;
        }

        @Override
        public @NotNull ExpressionLexerImpl.AbstractExpr invoked() {
            return invoked;
        }
    }

    public class VariableThisExprImpl extends VariableExprImpl implements VariableExpression {

        public VariableThisExprImpl() {
            super(settings.idents.self);
        }
    }

    public class VariableExprImpl extends AbstractExpr implements VariableExpression {
        private final @NotNull String variableName;

        public VariableExprImpl(@NotNull String variableName) {
            Validate.Arg.notNull(variableName, "variableName");
            this.variableName = variableName;
        }

        @Override
        public @NotNull String variableName() {
            return variableName;
        }

        @Override
        public @NotNull DyanasisObject evaluate() {
            TODO
        }
    }

    public class ConstantExprImpl extends AbstractExpr implements ConstantExpression {

        private final @NotNull DyanasisObject value;

        public ConstantExprImpl(@NotNull DyanasisObject value) {
            Validate.Arg.notNull(value, "value");
            this.value = value;
        }

        @Override
        public @NotNull DyanasisObject evaluate() {
            return value;
        }

        @Override
        public String toString() {
            return "ConstantExpr(" + value + ")";
        }
    }

    public abstract class AbstractExpr implements ExpressionLexer.Expression {

        protected AbstractExpr() {
        }

        @Override
        public @NotNull ExpressionLexer lexer() {
            return ExpressionLexerImpl.this;
        }
    }
}
