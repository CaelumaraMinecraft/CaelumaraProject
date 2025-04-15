package net.aurika.dyanasis.api.object.standard;

import net.aurika.dyanasis.api.NamingContract;
import net.aurika.dyanasis.api.compiler.DyanasisCompiler;
import net.aurika.dyanasis.api.declaration.function.signature.DyanasisFunctionSignature;
import net.aurika.dyanasis.api.declaration.namespace.DyanasisNamespace;
import net.aurika.dyanasis.api.executing.input.DyanasisExecuteInput;
import net.aurika.dyanasis.api.executing.result.DyanasisExecuteResult;
import net.aurika.dyanasis.api.object.DefaultDyanasisObject;
import net.aurika.dyanasis.api.object.DyanasisObject;
import net.aurika.dyanasis.api.object.DyanasisObjectBool;
import net.aurika.dyanasis.api.runtime.DyanasisRuntime;
import net.aurika.dyanasis.api.type.DefaultDyanasisType;
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
    type = standardType(
        dyanasisRuntime(), TYPE_NAME, getClass(), () -> new StandardObjectBoolType(standardNS(dyanasisRuntime())));
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
    switch (cfgStr) {  // TODO
      case "true":
        return value == Boolean.TRUE;
      case "false":
        return value == Boolean.FALSE;
      default:
        return false;
    }
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

    public StandardObjectBoolProperty(@NamingContract.Member final @NotNull String name) {
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

    public abstract @NotNull DyanasisExecuteResult execute(@NotNull DyanasisExecuteInput input);

    @Override
    public @NotNull StandardDyanasisObjectBool<Lexer> owner() {
      return StandardDyanasisObjectBool.this;
    }

  }

  public class StandardObjectBoolType extends DefaultDyanasisType<StandardDyanasisObjectBool<Lexer>> {

    public StandardObjectBoolType(@NotNull DyanasisNamespace namespace) {
      // noinspection unchecked
      super(
          StandardDyanasisObjectBool.this.dyanasisRuntime(), namespace, StandardDyanasisObjectBool.TYPE_NAME,
          (Class<? extends StandardDyanasisObjectBool<Lexer>>) StandardDyanasisObjectBool.this.getClass()
      );
    }

  }

  public class StandardDyanasisBoolType extends DefaultDyanasisType<StandardDyanasisObjectBool<?>> {

    public StandardDyanasisBoolType(@NotNull DyanasisNamespace namespace) {
      // noinspection unchecked
      super(
          StandardDyanasisObjectBool.this.dyanasisRuntime(), namespace, StandardDyanasisObjectBool.TYPE_NAME,
          (Class<? extends StandardDyanasisObjectBool<?>>) StandardDyanasisObjectBool.class
      );
    }

  }

}
