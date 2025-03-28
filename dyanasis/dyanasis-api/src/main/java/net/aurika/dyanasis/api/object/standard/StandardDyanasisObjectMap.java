package net.aurika.dyanasis.api.object.standard;

import net.aurika.dyanasis.api.NamingContract;
import net.aurika.dyanasis.api.declaration.invokable.function.key.DyanasisFunctionSignature;
import net.aurika.dyanasis.api.declaration.invokable.function.key.DefaultDyanasisFunctionSignature;
import net.aurika.dyanasis.api.declaration.invokable.property.DyanasisGetableProperty;
import net.aurika.dyanasis.api.declaration.namespace.DyanasisNamespace;
import net.aurika.dyanasis.api.invoking.input.DyanasisFunctionInput;
import net.aurika.dyanasis.api.invoking.result.DyanasisFunctionResult;
import net.aurika.dyanasis.api.lexer.DyanasisCompiler;
import net.aurika.dyanasis.api.object.DyanasisObject;
import net.aurika.dyanasis.api.object.DyanasisObjectMap;
import net.aurika.dyanasis.api.runtime.DyanasisRuntime;
import net.aurika.dyanasis.api.type.DyanasisType;
import net.aurika.dyanasis.api.type.DyanasisTypeIdent;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class StandardDyanasisObjectMap<Lexer extends DyanasisCompiler> extends StandardDyanasisObject<Map<?, ?>, Lexer> implements DyanasisObjectMap {

  public static final String TYPE_NAME = "Map";
  public static final DyanasisTypeIdent TYPE_IDENT = standardTypeIdent(TYPE_NAME);

  public static final String PROPERTY_SIZE = "size";

  public static final DyanasisFunctionSignature FUNCTION_GET;
  public static final DyanasisFunctionSignature FUNCTION_SET;

  static {
    FUNCTION_GET = DyanasisFunctionSignature.standardFunctionSignature(
        "get",
        new DefaultDyanasisFunctionSignature.ParameterBuilder("index", StandardDyanasisObjectNumber.TYPE_IDENT)
    );
    FUNCTION_SET = DyanasisFunctionSignature.standardFunctionSignature(
        "set",
        new DefaultDyanasisFunctionSignature.ParameterBuilder("index", StandardDyanasisObjectNumber.TYPE_IDENT),
        new DefaultDyanasisFunctionSignature.ParameterBuilder("value", StandardDyanasisObjectString.TYPE_IDENT)
    );
  }

  protected final @NotNull StandardObjectMapPropertyContainer properties;
  protected final @NotNull StandardObjectMapFunctionContainer functions;
  private final @NotNull DyanasisType<? extends StandardDyanasisObjectMap<Lexer>> type;

  {
    properties = new StandardObjectMapPropertyContainer();
    functions = new StandardObjectMapFunctionContainer();
    // noinspection unchecked
    type = standardType(runtime, TYPE_NAME, getClass(), () -> new StandardMapType(runtime, standardNS(runtime)));
  }

  public StandardDyanasisObjectMap(@NotNull DyanasisRuntime runtime, Map<?, ?> value, @NotNull Lexer lexer) {
    this(runtime, value, lexer, null);
  }

  protected StandardDyanasisObjectMap(@NotNull DyanasisRuntime runtime, Map<?, ?> value, @NotNull Lexer lexer, DefaultObjectDoc doc) {
    super(runtime, value, lexer, doc);
  }

  @Override
  public int size() {
    return value.size();
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
    //TODO lex and equals
    return false;
  }

  @Override
  public @NotNull DyanasisType<? extends StandardDyanasisObjectMap<Lexer>> dyanasisType() {
    return type;
  }

  public class StandardObjectMapPropertyContainer extends DefaultObjectPropertyContainer<AbstractMapProperty> {

    @Override
    public @NotNull DyanasisObject owner() {
      return StandardDyanasisObjectMap.this;
    }

  }

  public class StandardObjectMapFunctionContainer extends DefaultObjectFunctionContainer<AbstractMapFunction> {

    @Override
    public @NotNull DyanasisObject owner() {
      return StandardDyanasisObjectMap.this;
    }

  }

  public class Size extends AbstractMapProperty implements DyanasisGetableProperty {

    public Size() {
      super(PROPERTY_SIZE);
    }

    @Override
    public @NotNull StandardDyanasisObjectNumber<? extends Lexer> value() {
      return new StandardDyanasisObjectNumber<>(runtime, value.size(), lexer);
    }

    @Override
    public @NotNull StandardDyanasisObjectNumber<? extends Lexer> getPropertyValue() {
      return value();
    }

  }

  public class Get extends AbstractMapFunction {

    public Get() {
      super(FUNCTION_GET);
    }

    @Override
    public @NotNull DyanasisFunctionResult apply(@NotNull DyanasisFunctionInput input) {
      // TODO
    }

  }

  public abstract class AbstractMapProperty extends DefaultObjectProperty {

    public AbstractMapProperty(@NamingContract.Invokable final @NotNull String name) {
      super(name);
    }

    @Override
    public @NotNull StandardDyanasisObjectMap<Lexer> owner() {
      return StandardDyanasisObjectMap.this;
    }

    @Override
    public @NotNull DyanasisRuntime dyanasisRuntime() {
      return StandardDyanasisObjectMap.this.dyanasisRuntime();
    }

  }

  public abstract class AbstractMapFunction extends DefaultObjectFunction {

    public AbstractMapFunction(@NotNull DyanasisFunctionSignature key) {
      super(key);
    }

    @Override
    public abstract @NotNull DyanasisFunctionResult apply(@NotNull DyanasisFunctionInput input);

    @Override
    public @NotNull StandardDyanasisObjectMap<Lexer> owner() {
      return StandardDyanasisObjectMap.this;
    }

    @Override
    public @NotNull DyanasisRuntime dyanasisRuntime() {
      return StandardDyanasisObjectMap.this.dyanasisRuntime();
    }

  }

  public class StandardMapType extends DefaultObjectType<StandardDyanasisObjectMap<Lexer>> {

    public StandardMapType(@NotNull DyanasisRuntime runtime, @NotNull DyanasisNamespace namespace) {
      super(
          runtime, namespace, StandardDyanasisObjectMap.TYPE_NAME,
          (Class<? extends StandardDyanasisObjectMap<Lexer>>) StandardDyanasisObjectMap.this.getClass()
      );
    }

    @Override
    protected void addProperties() {
      properties.put(PROPERTY_SIZE, (mapObject) -> mapObject.new Size());
    }

    @Override
    protected void addFunctions() {
      functions.put(FUNCTION_GET, (mapObject) -> mapObject.new Get());
    }

  }

}
