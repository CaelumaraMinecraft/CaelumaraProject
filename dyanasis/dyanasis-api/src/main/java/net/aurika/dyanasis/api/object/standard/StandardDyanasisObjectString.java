package net.aurika.dyanasis.api.object.standard;

import net.aurika.dyanasis.api.NamingContract;
import net.aurika.dyanasis.api.declaration.invokable.function.key.DyanasisFunctionSignature;
import net.aurika.dyanasis.api.declaration.invokable.function.key.DefaultDyanasisFunctionSignature;
import net.aurika.dyanasis.api.declaration.invokable.property.DyanasisGetableProperty;
import net.aurika.dyanasis.api.declaration.namespace.DyanasisNamespace;
import net.aurika.dyanasis.api.invoking.input.DyanasisFunctionInput;
import net.aurika.dyanasis.api.invoking.result.DyanasisFunctionResult;
import net.aurika.dyanasis.api.compiler.DyanasisCompiler;
import net.aurika.dyanasis.api.object.DyanasisObjectNumber;
import net.aurika.dyanasis.api.object.DyanasisObjectString;
import net.aurika.dyanasis.api.runtime.DyanasisRuntime;
import net.aurika.dyanasis.api.type.DyanasisType;
import net.aurika.dyanasis.api.type.DyanasisTypeIdent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class StandardDyanasisObjectString<Lexer extends DyanasisCompiler> extends StandardDyanasisObject<String, Lexer> implements DyanasisObjectString {

  public static final String TYPE_NAME = "String";
  public static final DyanasisTypeIdent TYPE_IDENT = standardTypeIdent(TYPE_NAME);

  public static final String PROPERTY_LENGTH = "length";

  public static final DyanasisFunctionSignature FUNCTION_SUBSTRING_1;
  public static final DyanasisFunctionSignature FUNCTION_SUBSTRING_2;

  static {
    FUNCTION_SUBSTRING_1 = DyanasisFunctionSignature.standardFunctionSignature(
        "substring",
        new DefaultDyanasisFunctionSignature.ParameterBuilder("startIndex", StandardDyanasisObjectNumber.TYPE_IDENT)
    );
    FUNCTION_SUBSTRING_2 = DyanasisFunctionSignature.standardFunctionSignature(
        "substring",
        new DefaultDyanasisFunctionSignature.ParameterBuilder("startIndex", StandardDyanasisObjectNumber.TYPE_IDENT),
        new DefaultDyanasisFunctionSignature.ParameterBuilder("endIndex", StandardDyanasisObjectNumber.TYPE_IDENT)
    );
  }

  protected final @NotNull StandardObjectStringPropertyContainer properties;
  protected final @NotNull StandardObjectStringFunctionContainer functions;

  private final @NotNull DyanasisType<? extends StandardDyanasisObjectString<Lexer>> type;

  {
    properties = new StandardObjectStringPropertyContainer();
    functions = new StandardObjectStringFunctionContainer();
    // noinspection unchecked
    type = standardType(runtime, TYPE_NAME, getClass(), () -> new StandardStringType(runtime, standardNS(runtime)));
  }

  public StandardDyanasisObjectString(@NotNull DyanasisRuntime runtime, String value, @NotNull Lexer lexer) {
    this(runtime, value, lexer, null);
  }

  protected StandardDyanasisObjectString(@NotNull DyanasisRuntime runtime, String value, @NotNull Lexer lexer, @Nullable DefaultObjectDoc doc) {
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
    return value.equals(cfgStr);
  }

  @Override
  public @NotNull DyanasisType<? extends StandardDyanasisObjectString<Lexer>> dyanasisType() {
    return type;
  }

  public class StandardObjectStringPropertyContainer extends DefaultObjectPropertyContainer<AbstractStringProperty> {

    @Override
    public @NotNull StandardDyanasisObject<String, Lexer> owner() {
      return StandardDyanasisObjectString.this;
    }

  }

  public class StandardObjectStringFunctionContainer extends DefaultObjectFunctionContainer<AbstractStringFunction> {

    @Override
    public @NotNull StandardDyanasisObject<String, Lexer> owner() {
      return StandardDyanasisObjectString.this;
    }

  }

  public class Length extends AbstractStringProperty implements DyanasisGetableProperty {

    public Length() {
      super("length");
    }

    @Override
    public @NotNull DyanasisObjectNumber getPropertyValue() {
      return value();
    }

    @Override
    @NamingContract.Invokable
    public @NotNull String name() {
      return "length";
    }

    @Override
    public @NotNull DyanasisObjectNumber value() {
      return new StandardDyanasisObjectNumber<>(runtime, value.length(), lexer);
    }

  }

  public class Substring_1 extends AbstractStringFunction {

    public Substring_1() {
      super(FUNCTION_SUBSTRING_1);
    }

    @Override
    public @NotNull DyanasisFunctionResult apply(@NotNull DyanasisFunctionInput input) {
      return value.substring();
    }

  }

  public class Substring_2 extends AbstractStringFunction {

    public Substring_2() {
      super(FUNCTION_SUBSTRING_2);
    }

    @Override
    public @NotNull DyanasisFunctionResult apply(@NotNull DyanasisFunctionInput input) {
      return value.substring();
    }

  }

  public abstract class AbstractStringProperty extends DefaultObjectProperty {

    public AbstractStringProperty(@NamingContract.Invokable final @NotNull String name) {
      super(name);
    }

    @Override
    public @NotNull StandardDyanasisObject<String, Lexer> owner() {
      return StandardDyanasisObjectString.this;
    }

  }

  public abstract class AbstractStringFunction extends DefaultObjectFunction {

    public AbstractStringFunction(@NotNull DyanasisFunctionSignature key) {
      super(key);
    }

    @Override
    public abstract @NotNull DyanasisFunctionResult apply(@NotNull DyanasisFunctionInput input);

    @Override
    public @NotNull StandardDyanasisObject<String, Lexer> owner() {
      return StandardDyanasisObjectString.this;
    }

  }

  public class StandardStringType extends DefaultObjectType<StandardDyanasisObjectString<Lexer>> {

    public StandardStringType(@NotNull DyanasisRuntime runtime, @NotNull DyanasisNamespace namespace) {
      // noinspection unchecked
      super(
          runtime, namespace, StandardDyanasisObjectString.TYPE_NAME,
          (Class<? extends StandardDyanasisObjectString<Lexer>>) StandardDyanasisObjectString.this.getClass()
      );
    }

  }

}
