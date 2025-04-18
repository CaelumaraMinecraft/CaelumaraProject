package net.aurika.dyanasis.api.compiler;

import net.aurika.dyanasis.api.compiler.context.evaluating.DyanasisCompilerEvaluateContext;
import net.aurika.dyanasis.api.compiler.expression.*;
import net.aurika.dyanasis.api.compiler.setting.DefaultDyanasisCompilerSettings;
import net.aurika.dyanasis.api.declaration.namespace.DyanasisNamespaceIdent;
import net.aurika.dyanasis.api.object.DyanasisObject;
import net.aurika.common.validate.Validate;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class DefaultDyanasisCompilerImpl extends AbstractDyanasisCompiler {

  // Pointers
  protected int ap;  // pointer A, move in forward
  protected int bp;  // pointer B, move in reverse
  // Context
  protected @Nullable DyanasisNamespaceIdent usingNamespace;

  public DefaultDyanasisCompilerImpl(@NotNull DefaultDyanasisCompilerImpl parent, @NotNull String original) {
    this(parent, original, 0, original.length());
  }

  public DefaultDyanasisCompilerImpl(@NotNull DefaultDyanasisCompilerImpl parent, @NotNull String original, int ap, int bp) {
    super(parent, original);
    this.ap = ap;
    this.bp = bp;
  }

  public DefaultDyanasisCompilerImpl(@NotNull DefaultDyanasisCompilerSettings settings, @NotNull String original) {
    this(settings, original, 0, original.length());
  }

  public DefaultDyanasisCompilerImpl(@NotNull DefaultDyanasisCompilerSettings settings, @NotNull String original, int ap, int bp) {
    super(settings, original);
    this.ap = ap;
    this.bp = bp;
  }

  @Override
  public @NotNull Expression compile() {
    TODO
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
    switch (c) {
      case '(':
      case ')':
      case '<':
      case '>':
      case '{':
      case '}':
      case '.':
      case ',':
      case '"':
      case '!':
      case '=':
      case '-':
      case '_':
      case '@':
      case '#':
      case '$':
      case '%':
      case '^':
      case '&':
      case '*':
      case '~':
      case '`':
        return true;
      default:
        return false;
    }
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
   * @return whether the lexer is lex done
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

  public class InvokeFunctionExprImpl extends DefaultAccess implements InvokeFunctionStatement {

    private final @NotNull String functionName;
    private final @NotNull String argumentsRaw;

    public InvokeFunctionExprImpl(@NotNull DefaultDyanasisCompilerImpl.DedfaultExpression invoked, @NotNull String functionName, @NotNull String argumentsRaw) {
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

  public class InvokePropertyExprImpl extends DefaultAccess implements InvokePropertyStatement {

    private final @NotNull String propertyName;

    public InvokePropertyExprImpl(@NotNull DefaultDyanasisCompilerImpl.DedfaultExpression invoked, @NotNull String propertyName) {
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

  public abstract class DefaultAccess extends DedfaultExpression implements Access {

    private final @NotNull DefaultDyanasisCompilerImpl.DedfaultExpression invoked;

    protected DefaultAccess(@NotNull DefaultDyanasisCompilerImpl.DedfaultExpression invoked) {
      Validate.Arg.notNull(invoked, "invoked");
      this.invoked = invoked;
    }

    @Override
    public @NotNull DefaultDyanasisCompilerImpl.DedfaultExpression invoked() {
      return invoked;
    }

  }

  public class DefaultAccessLocalVariable extends DedfaultExpression implements AccessLocalVariable {

    private final @NotNull String variableName;

    public DefaultAccessLocalVariable(@NotNull String variableName) {
      Validate.Arg.notNull(variableName, "variableName");
      this.variableName = variableName;
    }

    @Override
    public @NotNull String variableName() { return variableName; }

    @Override
    public @NotNull Object evaluate(DyanasisCompilerEvaluateContext context) {
      return null;
    }

  }

  public class DefaultLiteralObject extends DedfaultExpression implements DeclareLiteralObject {

    private final @NotNull DyanasisObject value;

    public DefaultLiteralObject(@NotNull DyanasisObject value) {
      Validate.Arg.notNull(value, "value");
      this.value = value;
    }

    @Override
    public @NotNull DyanasisObject literal() { return value; }

    @Override
    public @NotNull Object evaluate(DyanasisCompilerEvaluateContext context) { return value; }

    @Override
    public String toString() {
      return "ConstantExpr(" + value + ")";
    }

  }

  public abstract class DedfaultExpression implements Expression {

    protected DedfaultExpression() {
    }

    @Override
    public @NotNull DefaultDyanasisCompilerImpl compiler() { return DefaultDyanasisCompilerImpl.this; }

  }

  /**
   * The standard implementation of {@linkplain DyanasisStatementCompiler}.
   */
  public class DefaultStatementLexer extends DefaultLexer implements StatementLexer {

    public @NotNull DefaultDyanasisCompilerImpl.DedfaultExpression evaluate() {
      skipWhitespaces();
      @Nullable DefaultDyanasisCompilerImpl.DefaultStatementLexer.StandardInvokeExpr anonymousThisInvoke = lexAnonymousSelfInvoke();
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
    protected @Nullable DefaultDyanasisCompilerImpl.DefaultStatementLexer.StandardInvokeExpr lexAnonymousSelfInvoke() {
      skipWhitespaces(false);
      if (startWith(settings().idents().self(), ap)) {  // after skipping ws, if start with self ident
        ap = ap + settings().idents().self().length();
        skip(settings().idents().invoke());
        return new InvokeVariableThisExprImpl();
      } else {
        int invEnd = findInvoke(ap);
        if (invEnd > ap) {  // if found an invoking
          ap = invEnd + 1;
          return new InvokeVariableThisExprImpl();
        } else {
          return null;
        }
      }
    }

    protected @Nullable Access lexInvoke(@NotNull Statement invoked) {

    }

  }

  public abstract class DefaultLexer implements Lexer {

    @Override
    public abstract @NotNull Expression lex();

    @Override
    public @NotNull DefaultDyanasisCompilerImpl compiler() {
      return DefaultDyanasisCompilerImpl.this;
    }

  }

  public static class Scope {

    private final @NotNull Scope parent;
    private @Nullable Expression selfReference;
    private final Map<String, DyanasisObject> localVariables = new HashMap<>();

    public Scope(@NotNull Scope parent) { this.parent = parent; }

  }

}
