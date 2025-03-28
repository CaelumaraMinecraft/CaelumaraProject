package net.aurika.dyanasis.api.object.standard;

import net.aurika.dyanasis.api.NamingContract;
import net.aurika.dyanasis.api.declaration.invokable.function.key.DyanasisFunctionSignature;
import net.aurika.dyanasis.api.declaration.namespace.DyanasisNamespace;
import net.aurika.dyanasis.api.invoking.input.DyanasisFunctionInput;
import net.aurika.dyanasis.api.invoking.result.DyanasisFunctionResult;
import net.aurika.dyanasis.api.lexer.DyanasisCompiler;
import net.aurika.dyanasis.api.object.DefaultDyanasisObject;
import net.aurika.dyanasis.api.object.DyanasisObject;
import net.aurika.dyanasis.api.object.DyanasisObjectBool;
import net.aurika.dyanasis.api.runtime.DyanasisRuntime;
import net.aurika.dyanasis.api.type.DyanasisType;
import net.aurika.dyanasis.api.type.DyanasisTypeIdent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class StandardDyanasisObjectBool<Lexer extends DyanasisCompiler> extends StandardDyanasisObject<Boolean, Lexer> implements DyanasisObjectBool {

  public static final String TYPE_NAME = "Bool";
  public static final DyanasisTypeIdent TYPE_IDENT = standardTypeIdent(TYPE_NAME);

  protected final StandardObjectBoolPropertyContainer properties;
  protected final StandardObjectBoolFunctionContainer functions;
  private final @NotNull DyanasisType<? extends StandardDyanasisObjectBool<Lexer>> type;

  {
    properties = new StandardObjectBoolPropertyContainer();
    functions = new StandardObjectBoolFunctionContainer();
    // noinspection unchecked
    type = standardType(runtime, TYPE_NAME, getClass(), () -> new StandardObjectBoolType(standardNS(runtime)));
  }

  public StandardDyanasisObjectBool(@NotNull DyanasisRuntime runtime, boolean value, Lexer lexer) {
    this(runtime, value, lexer, null);
  }

  protected StandardDyanasisObjectBool(@NotNull DyanasisRuntime runtime, boolean value, Lexer lexer, @Nullable DefaultDyanasisObject.DefaultObjectDoc doc) {
    super(runtime, value, lexer, doc);
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
    return switch (cfgStr) {
      case "true" -> value == Boolean.TRUE;
      case "false" -> value == Boolean.FALSE;
      default -> false;
    };
  }

  @Override
  public @NotNull DyanasisType<? extends StandardDyanasisObjectBool<Lexer>> dyanasisType() {
    return type;
  }

  public class StandardObjectBoolPropertyContainer extends DefaultObjectPropertyContainer<StandardObjectBoolProperty> {

    public StandardObjectBoolPropertyContainer() {
      super();
    }

    @Override
    public @NotNull StandardDyanasisObjectBool<Lexer> owner() {
      return StandardDyanasisObjectBool.this;
    }

  }

  public class StandardObjectBoolFunctionContainer extends DefaultObjectFunctionContainer<StandardObjectBoolFunction> {

    public StandardObjectBoolFunctionContainer() {
      super();
    }

    @Override
    public @NotNull StandardDyanasisObjectBool<Lexer> owner() {
      return StandardDyanasisObjectBool.this;
    }

  }

  public abstract class StandardObjectBoolProperty extends DefaultObjectProperty {

    public StandardObjectBoolProperty(@NamingContract.Invokable final @NotNull String name) {
      super(name);
    }

    @Override
    public abstract @NotNull DyanasisObject value();

    @Override
    public @NotNull StandardDyanasisObjectBool<Lexer> owner() {
      return StandardDyanasisObjectBool.this;
    }

  }

  public abstract class StandardObjectBoolFunction extends DefaultObjectFunction {

    public StandardObjectBoolFunction(@NotNull DyanasisFunctionSignature key) {
      super(key);
    }

    @Override
    public abstract @NotNull DyanasisFunctionResult apply(@NotNull DyanasisFunctionInput input);

    @Override
    public @NotNull StandardDyanasisObjectBool<Lexer> owner() {
      return StandardDyanasisObjectBool.this;
    }

  }

  public class StandardObjectBoolType extends DefaultObjectType<StandardDyanasisObjectBool<Lexer>> {

    public StandardObjectBoolType(@NotNull DyanasisNamespace namespace) {
      // noinspection unchecked
      super(
          StandardDyanasisObjectBool.this.runtime, namespace, StandardDyanasisObjectBool.TYPE_NAME,
          (Class<? extends StandardDyanasisObjectBool<Lexer>>) StandardDyanasisObjectBool.this.getClass()
      );
    }

  }

  public static class StandardDyanasisBoolType extends DefaultObjectType<StandardDyanasisObjectBool<?>> {

    public StandardDyanasisBoolType(@NotNull DyanasisNamespace namespace) {
      // noinspection unchecked
      super(
          StandardDyanasisObjectBool.this.runtime, namespace, StandardDyanasisObjectBool.TYPE_NAME,
          (Class<? extends StandardDyanasisObjectBool<?>>) StandardDyanasisObjectBool.class
      );
    }

  }

}
