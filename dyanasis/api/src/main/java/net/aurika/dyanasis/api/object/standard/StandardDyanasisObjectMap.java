package net.aurika.dyanasis.api.object.standard;

import net.aurika.dyanasis.api.NamingContract;
import net.aurika.dyanasis.api.compiler.DyanasisCompiler;
import net.aurika.dyanasis.api.declaration.function.signature.DefaultDyanasisFunctionSignature;
import net.aurika.dyanasis.api.declaration.function.signature.DyanasisFunctionSignature;
import net.aurika.dyanasis.api.declaration.namespace.DyanasisNamespace;
import net.aurika.dyanasis.api.declaration.property.DyanasisGettableProperty;
import net.aurika.dyanasis.api.executing.input.DyanasisExecuteInput;
import net.aurika.dyanasis.api.executing.result.DyanasisExecuteResult;
import net.aurika.dyanasis.api.object.DyanasisObject;
import net.aurika.dyanasis.api.object.DyanasisObjectMap;
import net.aurika.dyanasis.api.runtime.DyanasisRuntime;
import net.aurika.dyanasis.api.type.DefaultDyanasisType;
import net.aurika.dyanasis.api.type.DyanasisType;
import net.aurika.dyanasis.api.type.DyanasisTypeIdent;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public class StandardDyanasisObjectMap<Compiler extends DyanasisCompiler> extends StandardDyanasisObject<Map<?, ?>, Compiler> implements DyanasisObjectMap {

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
  private final @NotNull DyanasisType<? extends StandardDyanasisObjectMap<Compiler>> type;

  {
    properties = new StandardObjectMapPropertyContainer();
    functions = new StandardObjectMapFunctionContainer();
    // noinspection unchecked
    type = standardType(
        dyanasisRuntime(),
        TYPE_NAME,
        getClass(),
        () -> new StandardMapType(dyanasisRuntime(), standardNS(dyanasisRuntime()))
    );
  }

  public StandardDyanasisObjectMap(@NotNull Map<?, ?> value, @NotNull Compiler compiler) {
    super(value, compiler);
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
  public @NotNull DyanasisType<? extends StandardDyanasisObjectMap<Compiler>> dyanasisType() {
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

  public class Size extends AbstractMapProperty implements DyanasisGettableProperty {

    public Size() {
      super(PROPERTY_SIZE);
    }

    @Override
    public @NotNull StandardDyanasisObjectNumber<? extends Compiler> value() {
      return new StandardDyanasisObjectNumber<>(dyanasisRuntime(), value.size(), compiler);
    }

    @Override
    public @NotNull StandardDyanasisObjectNumber<? extends Compiler> getPropertyValue() {
      return value();
    }

  }

  public class Get extends AbstractMapFunction {

    public Get() {
      super(FUNCTION_GET);
    }

    @Override
    public @NotNull DyanasisExecuteResult execute(@NotNull DyanasisExecuteInput input) {
      // TODO
    }

  }

  public abstract class AbstractMapProperty extends DefaultObjectProperty {

    public AbstractMapProperty(@NamingContract.Member final @NotNull String name) {
      super(name);
    }

    @Override
    public @NotNull StandardDyanasisObjectMap<Compiler> owner() {
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

    public abstract @NotNull DyanasisExecuteResult execute(@NotNull DyanasisExecuteInput input);

    @Override
    public @NotNull StandardDyanasisObjectMap<Compiler> owner() {
      return StandardDyanasisObjectMap.this;
    }

    @Override
    public @NotNull DyanasisRuntime dyanasisRuntime() {
      return StandardDyanasisObjectMap.this.dyanasisRuntime();
    }

  }

  public class StandardMapType extends DefaultDyanasisType<StandardDyanasisObjectMap<Compiler>> {

    public StandardMapType(@NotNull DyanasisRuntime runtime, @NotNull DyanasisNamespace namespace) {
      super(
          runtime, namespace, StandardDyanasisObjectMap.TYPE_NAME,
          (Class<? extends StandardDyanasisObjectMap<Compiler>>) StandardDyanasisObjectMap.this.getClass()
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
