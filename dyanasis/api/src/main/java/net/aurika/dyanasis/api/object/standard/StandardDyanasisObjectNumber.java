package net.aurika.dyanasis.api.object.standard;

import net.aurika.dyanasis.api.NamingContract;
import net.aurika.dyanasis.api.compiler.DyanasisCompiler;
import net.aurika.dyanasis.api.declaration.member.function.key.DyanasisFunctionSignature;
import net.aurika.dyanasis.api.declaration.namespace.DyanasisNamespace;
import net.aurika.dyanasis.api.object.DyanasisObjectNumber;
import net.aurika.dyanasis.api.runtime.DyanasisRuntime;
import net.aurika.dyanasis.api.type.DyanasisType;
import net.aurika.dyanasis.api.type.DyanasisTypeIdent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class StandardDyanasisObjectNumber<Lexer extends DyanasisCompiler> extends StandardDyanasisObject<Number, Lexer> implements DyanasisObjectNumber {

  public static final String TYPE_NAME = "Number";
  public static final DyanasisTypeIdent TYPE_IDENT = standardTypeIdent(TYPE_NAME);

  protected final @NotNull StandardObjectNumberPropertyContainer properties;
  protected final @NotNull StandardObjectNumberFunctionContainer functions;
  private final @NotNull DyanasisType<? extends StandardDyanasisObjectNumber<Lexer>> type;

  {
    properties = new StandardObjectNumberPropertyContainer();
    functions = new StandardObjectNumberFunctionContainer();
    // noinspection unchecked
    type = standardType(runtime, TYPE_NAME, getClass(), () -> new StandardNumberType(runtime, standardNS(runtime)));
  }

  public StandardDyanasisObjectNumber(@NotNull DyanasisRuntime runtime, Number value, @NotNull Lexer lexer) {
    this(runtime, value, lexer, null);
  }

  protected StandardDyanasisObjectNumber(@NotNull DyanasisRuntime runtime, Number value, @NotNull Lexer lexer, @Nullable DefaultObjectDoc doc) {
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
    return toString().equals(cfgStr);
    //TODO compile and equals
  }

  @Override
  public @NotNull DyanasisType<? extends StandardDyanasisObjectNumber<Lexer>> dyanasisType() {
    return type;
  }

  public class StandardObjectNumberPropertyContainer extends DefaultObjectPropertyContainer<AbstractNumberProperty> {

    @Override
    public @NotNull StandardDyanasisObjectNumber<Lexer> owner() {
      return StandardDyanasisObjectNumber.this;
    }

  }

  public class StandardObjectNumberFunctionContainer extends DefaultObjectFunctionContainer<AbstractNumberFunction> {

    @Override
    public @NotNull StandardDyanasisObjectNumber<Lexer> owner() {
      return StandardDyanasisObjectNumber.this;
    }

  }

  public abstract class AbstractNumberProperty extends DefaultObjectProperty {

    public AbstractNumberProperty(@NamingContract.Invokable final @NotNull String name) {
      super(name);
    }

    @Override
    public @NotNull StandardDyanasisObjectNumber<Lexer> owner() {
      return StandardDyanasisObjectNumber.this;
    }

  }

  public abstract class AbstractNumberFunction extends DefaultObjectFunction {

    public AbstractNumberFunction(@NotNull DyanasisFunctionSignature key) {
      super(key);
    }

    @Override
    public @NotNull StandardDyanasisObjectNumber<Lexer> owner() {
      return StandardDyanasisObjectNumber.this;
    }

  }

  public class StandardNumberType extends DefaultObjectType<StandardDyanasisObjectNumber<Lexer>> {

    public StandardNumberType(@NotNull DyanasisRuntime runtime, @NotNull DyanasisNamespace namespace) {
      // noinspection unchecked
      super(
          runtime, namespace, StandardDyanasisObjectNumber.TYPE_NAME,
          (Class<? extends StandardDyanasisObjectNumber<Lexer>>) StandardDyanasisObjectNumber.this.getClass()
      );
    }

  }

}
