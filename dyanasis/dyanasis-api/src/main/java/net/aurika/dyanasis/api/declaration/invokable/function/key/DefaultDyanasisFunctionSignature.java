package net.aurika.dyanasis.api.declaration.invokable.function.key;

import net.aurika.dyanasis.api.NamingContract;
import net.aurika.dyanasis.api.type.DyanasisTypeIdent;
import net.aurika.dyanasis.api.type.DyanasisTypeIdentAware;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * {@linkplain DyanasisFunctionSignature} 的默认实现.
 * 这个实现中, {@link DefaultDyanasisFunctionSignature.Parameter 形参} 与它所属的函数签名绑定 {@link Parameter#functionSignature() functionSingature}.
 */
public class DefaultDyanasisFunctionSignature implements DyanasisFunctionSignature {

  @NamingContract.Invokable
  private final @NotNull String name;
  private final @NotNull Parameter @NotNull [] parameters;

  protected DefaultDyanasisFunctionSignature(@NamingContract.Invokable final @NotNull String name, @NotNull Parameter @NotNull [] parameters) {
    this.name = name;
    this.parameters = parameters.clone();
  }

  protected DefaultDyanasisFunctionSignature(@NamingContract.Invokable final @NotNull String name, @NotNull ParameterBuilder @NotNull [] parameterBuilders) {
    this.name = name;
    this.parameters = new Parameter[parameterBuilders.length];
    for (int i = 0; i < parameterBuilders.length; i++) {
      ParameterBuilder parameterBuilder = parameterBuilders[i];
      this.parameters[i] = parameterBuilder.build(this);
    }
  }

  /**
   * Gets the dyanasis function name.
   *
   * @return the dyanasis function name
   */

  @Override
  @NamingContract.Invokable
  public @NotNull String name() {
    return name;
  }

  /**
   * Gets t3he arguments count.
   *
   * @return the arguments count
   */
  @Override
  public int arity() {      // TODO rename
    return parameters.length;
  }

  @Override
  public @NotNull Parameter parameterAt(int index) {
    return parameters[index];
  }

  @Override
  public @NotNull Iterator<? extends Parameter> parameters() {
    return new ParamItr();
  }

  protected class ParamItr implements Iterator<Parameter> {

    private int i = 0;

    @Override
    public boolean hasNext() {
      return i < arity();
    }

    @Override
    public Parameter next() {
      if (i >= arity()) {
        throw new NoSuchElementException();
      }
      return parameters[i++];
    }

  }

  public static class ParameterBuilder {

    private @NotNull String name;
    private @NotNull DyanasisTypeIdent type;

    public ParameterBuilder(@NotNull String name, @NotNull DyanasisTypeIdent type) {
      this.name = name;
      this.type = type;
    }

    public ParameterBuilder name(@NotNull String name) {
      this.name = name;
      return this;
    }

    public ParameterBuilder type(@NotNull DyanasisTypeIdent type) {
      this.type = type;
      return this;
    }

    public @NotNull Parameter build(@NotNull DefaultDyanasisFunctionSignature signature) {
      Objects.requireNonNull(name, "name");
      Objects.requireNonNull(type, "type");
      return signature.new Parameter(name, type);
    }

  }

  /**
   * {@linkplain DyanasisFunctionSignature.Parameter} 的默认实现.
   */
  public class Parameter implements DyanasisFunctionSignature.Parameter, DyanasisTypeIdentAware {

    private final @Nullable String name;
    private final @Nullable DyanasisTypeIdent type;

    public Parameter(@Nullable String name, @Nullable DyanasisTypeIdent type) {
      this.name = name;
      this.type = type;
    }

    /**
     * Returns {@code true} if {@link #dyanasisTypeIdent() typeIdent} is null.
     *
     * @param typeIdent the dyanasis type ident
     * @return whether the input type is allowed type
     */
    @Override
    public boolean isAllowedInputType(@NotNull DyanasisTypeIdent typeIdent) {
      return type == null || type.equals(typeIdent);
    }

    @Override
    public @Nullable String name() {
      return name;
    }

    @Override
    public @Nullable DyanasisTypeIdent dyanasisTypeIdent() {
      return type;
    }

    public @NotNull DefaultDyanasisFunctionSignature functionSignature() {
      return DefaultDyanasisFunctionSignature.this;
    }

  }

  @Override
  public int hashCode() {
    return Objects.hash(name(), arity());
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof DyanasisFunctionSignature that)) return false;
    return arity() == that.arity() && Objects.equals(name(), that.name());
  }

  @Override
  public @NotNull String toString() {
    return "FunctionKey[name='" + name + "', arity=" + arity() + "]";
  }

}
