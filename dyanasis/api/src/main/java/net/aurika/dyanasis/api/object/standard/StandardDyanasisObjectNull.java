package net.aurika.dyanasis.api.object.standard;

import net.aurika.dyanasis.api.compiler.DyanasisCompiler;
import net.aurika.dyanasis.api.declaration.namespace.DyanasisNamespace;
import net.aurika.dyanasis.api.object.DyanasisObject;
import net.aurika.dyanasis.api.object.DyanasisObjectNull;
import net.aurika.dyanasis.api.runtime.DyanasisRuntime;
import net.aurika.dyanasis.api.type.DefaultDyanasisType;
import net.aurika.dyanasis.api.type.DyanasisType;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class StandardDyanasisObjectNull<Lexer extends DyanasisCompiler> extends StandardDyanasisObject<Void, Lexer> implements DyanasisObjectNull {

  public static final String TYPE_NAME = "Null";

  protected final StandardObjectNullPropertyContainer properties = new StandardObjectNullPropertyContainer();
  protected final StandardObjectNullFunctionContainer functions = new StandardObjectNullFunctionContainer();
  @SuppressWarnings("unchecked")
  private final @NotNull DyanasisType<? extends StandardDyanasisObjectNull<Lexer>> type = standardType(
      dyanasisRuntime(), TYPE_NAME, getClass(), () -> new StandardNullType(standardNS(dyanasisRuntime())));

  public StandardDyanasisObjectNull(@NotNull DyanasisRuntime runtime, @NotNull Lexer lexer) {
    this(runtime, lexer, null);
  }

  protected StandardDyanasisObjectNull(@NotNull DyanasisRuntime runtime, @NotNull Lexer lexer, @Nullable DefaultObjectDoc doc) {
    super(runtime, null, lexer, doc);
  }

  @Override
  public @NotNull ObjectPropertyContainer<? extends ObjectProperty> dyanasisProperties() {
    return properties;
  }

  @Override
  public @NotNull ObjectFunctionContainer<? extends ObjectFunction> dyanasisFunctions() {
    return functions;
  }

  @Override
  public boolean equals(@NotNull String cfgStr) {
    return Objects.equals(cfgStr, compiler().settings().idents().nil());
  }

  @Override
  public @NotNull DyanasisType<? extends StandardDyanasisObject<Void, Lexer>> dyanasisType() {
    return type;
  }

  public class StandardObjectNullPropertyContainer extends DefaultObjectPropertyContainer {

    @Override
    public @NotNull DyanasisObject owner() {
      return StandardDyanasisObjectNull.this;
    }

  }

  public class StandardObjectNullFunctionContainer extends DefaultObjectFunctionContainer {

    @Override
    public @NotNull DyanasisObject owner() {
      return StandardDyanasisObjectNull.this;
    }

  }

  public class StandardNullType extends DefaultDyanasisType<StandardDyanasisObjectNull<Lexer>> {

    public StandardNullType(@NotNull DyanasisNamespace namespace) {
      // noinspection unchecked
      super(
          StandardDyanasisObjectNull.this.dyanasisRuntime(), namespace, StandardDyanasisObjectNull.TYPE_NAME,
          (Class<? extends StandardDyanasisObjectNull<Lexer>>) StandardDyanasisObjectNull.this.getClass()
      );
    }

  }

  @Override
  public int hashCode() {
    return 0;
  }

  @Override
  public boolean equals(Object obj) {
    return super.equals(obj);
  }

  @Override
  public @NotNull String toString() {
    return "DyanasisObjectNull";
  }

}
