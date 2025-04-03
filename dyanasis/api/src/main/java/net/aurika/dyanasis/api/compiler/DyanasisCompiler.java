package net.aurika.dyanasis.api.compiler;

import net.aurika.dyanasis.api.compiler.context.evaluating.DyanasisCompilerEvaluateContext;
import net.aurika.dyanasis.api.compiler.setting.DyanasisCompilerSettings;
import net.aurika.dyanasis.api.declaration.invokable.function.key.DyanasisFunctionSignature;
import net.aurika.dyanasis.api.declaration.invokable.property.DyanasisProperty;
import net.aurika.dyanasis.api.declaration.namespace.DyanasisNamespace;
import net.aurika.dyanasis.api.invoking.result.DyanasisFunctionResult;
import net.aurika.dyanasis.api.object.DyanasisObject;
import net.aurika.validate.Validate;
import org.jetbrains.annotations.ApiStatus;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

public interface DyanasisCompiler {

  @NotNull Expression compile();

  /**
   * Gets the parent compiler of this compiler instance.
   *
   * @return the parent compiler
   */
  @Nullable DyanasisCompiler parent();

  /**
   * Gets the compile settings.
   *
   * @return the compile settings
   */
  @NotNull DyanasisCompilerSettings settings();

  /**
   * Gets the original string
   *
   * @return the original string
   */
  @NotNull String originalString();

  /**
   * 直接声明一个常量表达式, 如 {@code null} {@code true} {@code ["a": 123, "b": 546]} {@code "abc"} 等.
   */
  interface Constant extends Expression, Declaration {

    @NotNull DyanasisObject constant();

  }

  interface Executing extends Expression { }

  /**
   * An operator who accepts the operand at the right. Like {@code ++i} {@code !b}.
   */
  interface PrefixOperator extends UnaryOperator {

    @Override
    @NotNull Operation accept(@NotNull Expression right);

  }

  /**
   * An operator who accepts the operand at the left. Like {@code i++} {@code i--}.
   */
  interface SuffixOperator extends UnaryOperator {

    /**
     * Accepts the left operand.
     *
     * @param left the left operand
     */
    @Override
    @NotNull Operation accept(@NotNull Expression left);

  }

  interface UnaryOperator extends Operator {

    @NotNull Operation accept(@NotNull Expression operand);

  }

  /**
   * An operator that accepts a left operand and a right operand, like {@code 1 + 4}
   */
  interface BinaryOperator extends Operator {

    @NotNull Operation accept(@NotNull Expression left, @NotNull Expression right);

  }

  /**
   * 三元运算符.
   */
  @ApiStatus.Experimental
  interface TernaryOperator extends Operator { }

  interface LogicalOperator extends Operator { }

  interface MathOperator extends Operator { }

  /**
   * 操作符.
   */
  interface Operator { }

  /**
   * 一个操作语句, 包含一个或多个 {@linkplain Expression operand} 以及一个操作符 {@linkplain Operator operator}.
   * 如 {@code a + b} {@code a > b} {@code "in" == b} {@code a && b} {@code a || b} 等.
   */
  interface Operation extends Statement {

    @NotNull Operator operator();

  }

  interface InvokeProperty extends Invoking {

    @NotNull String propertyName();

    @NotNull DyanasisObject invoke();

    @Override
    @NotNull Expression operand();

  }

  interface InvokeFunction extends Invoking {

    @NotNull DyanasisFunctionResult invoke();

    @NotNull DyanasisFunctionSignature functionSignature();

    @NotNull FnArgs args();

    @Override
    @NotNull Expression operand();

  }

  /**
   * An invoking. It can be a function invoking or a property invoking.
   */
  interface Invoking extends Statement {

    /**
     * Gets the operand that be invoked.
     *
     * @return the operand
     */
    @NotNull Expression operand();

  }

  /**
   * A statement. Such as {@code a = 123} {@code a++} {@code str.substring(4)} {@code arr.length}.
   */
  interface Statement extends Expression { }

  /**
   * The access to a local variable and parameter in a function.
   */
  interface LocalVariableAccess extends Access {

    @NotNull String variableName();

  }

  /**
   * A {@link net.aurika.dyanasis.api.type.DyanasisType type} access.
   */
  interface TypeAccess extends Access { }

  /**
   * A {@link DyanasisNamespace namespace} access.
   */
  interface NamespaceAccess extends Access { }

  /**
   * A domain object access, such as access a {@link DyanasisNamespace namespace},
   * a {@link DyanasisNamespace.NamespaceProperty namespace level property}.
   * Note that execute a function or a constructor is not a {@linkplain DomainAccess}.
   */
  interface DomainAccess extends Access {

    @NotNull DomainAccessPath path();

  }

  /**
   * An expression that is a thing been accessed. Such as access a {@link DyanasisNamespace namespace},
   * a {@code local variable}, a {@link DyanasisProperty peoperty}.
   */
  interface Access extends Expression { }

  class DomainAccessPath {

    protected final @NotNull String @NotNull [] path;

    public DomainAccessPath(@NotNull String @NotNull [] path) {
      Validate.Arg.nonNullArray(path, "chain");
      this.path = path;
    }

    public int length() {
      return path.length;
    }

    /**
     * @throws IndexOutOfBoundsException when the {@code index} of more than the length of the path
     */
    public @NotNull String atIndex(int index) {
      return path[index];
    }

    public String @NotNull [] path() {
      return path.clone();
    }

    @Override
    public int hashCode() {
      return Arrays.hashCode(path);
    }

    @Override
    public boolean equals(Object o) {
      if (!(o instanceof DomainAccessPath that)) return false;
      return Objects.deepEquals(path, that.path);
    }

    @Override
    public String toString() {
      return getClass().getSimpleName() + Arrays.toString(path);
    }

  }

  /**
   * A block. It can be in a function declaration, switch branch, etc...
   */
  interface Block extends Expression, Declaration {

    @NotNull Expression @NotNull [] expressions();

  }

  interface LocalVariableDeclaration extends Declaration {

    @NotNull String variableName();

  }



  interface InstanceFunctionDeclaration extends FunctionDeclaration { }

  interface ExtendFunctionDeclaration extends FunctionDeclaration { }

  interface ProtectedPropertyDeclaration extends PropertyDeclaration { }

  interface ExtendPropertyDeclaration extends PropertyDeclaration { }

  interface PropertyDeclaration extends Declaration { }

  interface FunctionDeclaration extends Declaration {

    @NotNull FunctionDeclarationBody functionBody();

  }

  interface FunctionDeclarationBody { }

  /**
   * 标记一个新的声明
   */
  interface Declaration extends Expression { }

  /**
   * The compiled expression.
   */
  interface Expression {

    /**
     * Evaluates and returns the result.
     *
     * @return the result
     */
    @NotNull Object evaluate(DyanasisCompilerEvaluateContext context);

    /**
     * Gets the corresponding lexer for this expression.
     *
     * @return the lexer
     */
    @NotNull DyanasisCompiler compiler();

  }

  class FnArgs implements Iterable<Expression> {

    protected final @NotNull Expression @NotNull [] args;

    public FnArgs(@NotNull Expression @NotNull [] args) {
      Validate.Arg.nonNullArray(args, "args");
      this.args = args;
    }

    /**
     * Gets the clone of the args array.
     *
     * @return the args copy
     */
    public @NotNull Expression @NotNull [] args() {
      return args.clone();
    }

    @Override
    public @NotNull Iterator<Expression> iterator() {
      return new Itr();
    }

    protected class Itr implements Iterator<Expression> {

      private int index = 0;

      @Override
      public boolean hasNext() {
        return index < args.length;
      }

      @Override
      public Expression next() {
        return args[index++];
      }

    }

  }

  interface MathLexer extends Lexer {

    @Override
    @NotNull Expression lex();

  }

  interface BlockLexer extends Lexer {

    @Override
    @NotNull Expression lex();

  }

  interface StatementLexer extends Lexer {

    @Override
    @NotNull DyanasisCompiler.Operation lex();

  }

  interface ConstantLexer extends Lexer {

    @Override
    @NotNull DyanasisCompiler.Constant lex();

  }

  interface Lexer {

    @NotNull Expression lex();

    @NotNull DyanasisCompiler compiler();

  }

}
