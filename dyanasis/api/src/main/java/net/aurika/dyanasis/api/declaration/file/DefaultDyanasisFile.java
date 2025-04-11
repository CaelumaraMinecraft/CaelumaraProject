package net.aurika.dyanasis.api.declaration.file;

import net.aurika.dyanasis.api.NamingContract;
import net.aurika.dyanasis.api.declaration.member.function.container.SimpleDyanasisFunctionRegistry;
import net.aurika.dyanasis.api.declaration.member.function.key.DyanasisFunctionSignature;
import net.aurika.dyanasis.api.declaration.member.property.container.SimpleDyanasisPropertyRegistry;
import net.aurika.dyanasis.api.declaration.namespace.DyanasisNamespace;
import net.aurika.dyanasis.api.executing.input.DyanasisExecuteInput;
import net.aurika.dyanasis.api.executing.result.DyanasisExecuteResult;
import net.aurika.dyanasis.api.object.DyanasisObject;
import net.aurika.dyanasis.api.runtime.DyanasisRuntime;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DefaultDyanasisFile extends AbstractDyanasisFile<DefaultDyanasisFile.DefaultFileDoc> {

  protected @NotNull DefaultFilePropertyContainer properties = new DefaultFilePropertyContainer();
  protected @NotNull DefaultFileFunctionContainer functions = new DefaultFileFunctionContainer();

  public DefaultDyanasisFile(@NotNull DyanasisRuntime runtime) {
    super(runtime);
  }

  public DefaultDyanasisFile(@NotNull DyanasisRuntime runtime, @Nullable DyanasisNamespace usingNamespace) {
    super(runtime, usingNamespace, null);
  }


//    public DefaultDyanasisFile(@NotNull DyanasisRuntime runtime, @Nullable DyanasisNamespace usingNamespace, @Nullable DefaultFileDoc doc) {
//        super(runtime, usingNamespace, doc);
//    }

  @Override
  public @NotNull DefaultFilePropertyContainer dyanasisProperties() {
    return properties;
  }

  @Override
  public @NotNull DefaultFileFunctionContainer dyanasisFunctions() {
    return functions;
  }

  public class DefaultFilePropertyContainer extends SimpleDyanasisPropertyRegistry<DefaultFileProperty> implements FilePropertyContainer<DefaultFileProperty> {

    @Override
    public @NotNull DyanasisFile owner() {
      return DefaultDyanasisFile.this;
    }

  }

  public class DefaultFileFunctionContainer extends SimpleDyanasisFunctionRegistry<DefaultFileFunction> implements FileFunctionContainer<DefaultFileFunction> {

    @Override
    public @NotNull DyanasisFile owner() {
      return DefaultDyanasisFile.this;
    }

  }

  public abstract class DefaultFileProperty extends AbstractFileProperty {

    public DefaultFileProperty(@NamingContract.Invokable final @NotNull String name) {
      super(name);
    }

    @Override
    public @NotNull DefaultDyanasisFile owner() {
      return DefaultDyanasisFile.this;
    }

    @Override
    public abstract @NotNull DyanasisObject value();

  }

  public abstract class DefaultFileFunction extends AbstractFileFunction {

    public DefaultFileFunction(@NotNull DyanasisFunctionSignature key) {
      super(key);
    }

    public abstract @NotNull DyanasisExecuteResult execute(@NotNull DyanasisExecuteInput input);

    @Override
    public @NotNull DefaultDyanasisFile owner() {
      return DefaultDyanasisFile.this;
    }

  }

  public class DefaultFileDoc extends AbstractFileDoc implements FileDoc {

    public DefaultFileDoc(@NotNull String value) {
      super(value);
    }

    @Override
    public @NotNull DefaultDyanasisFile owner() {
      return DefaultDyanasisFile.this;
    }

  }

}
