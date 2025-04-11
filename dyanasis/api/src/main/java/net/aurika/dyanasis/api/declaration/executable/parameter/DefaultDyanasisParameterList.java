package net.aurika.dyanasis.api.declaration.executable.parameter;

import net.aurika.dyanasis.api.type.DyanasisTypeIdent;
import net.aurika.dyanasis.api.type.DyanasisTypeIdentAware;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class DefaultDyanasisParameterList implements DyanasisParameterList {

  private final @NotNull Parameter @NotNull [] parameters;

  protected DefaultDyanasisParameterList(@NotNull Parameter @NotNull [] parameters) {
    this.parameters = parameters.clone();
  }

  /**
   * Gets t3he arguments count.
   *
   * @return the arguments count
   */
  @Override
  public int arity() { return parameters.length; }

  @Override
  public @NotNull DefaultDyanasisParameterList.Parameter parameterAt(int index) {
    return parameters[index];
  }

  @Override
  public @NotNull Iterator<? extends DefaultDyanasisParameterList.Parameter> parameters() {
    return new DefaultDyanasisParameterList.ParamItr();
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

  /**
   * {@linkplain DyanasisParameter} 的默认实现.
   */
  public class Parameter implements DyanasisParameter, DyanasisTypeIdentAware {

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

    public @NotNull DefaultDyanasisParameterList parameterList() {
      return DefaultDyanasisParameterList.this;
    }

  }

  @Override
  public int hashCode() {
    return Objects.hash(arity());
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof DyanasisParameterList)) return false;
    DyanasisParameterList that = (DyanasisParameterList) obj;
    return arity() == that.arity();
  }

  @Override
  public @NotNull String toString() {
    return "Parameters[arity=" + arity() + "]";
  }

}
