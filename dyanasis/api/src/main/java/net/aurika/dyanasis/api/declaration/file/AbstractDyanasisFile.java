package net.aurika.dyanasis.api.declaration.file;

import net.aurika.dyanasis.api.NamingContract;
import net.aurika.dyanasis.api.declaration.doc.DyanasisDocEditable;
import net.aurika.dyanasis.api.declaration.invokable.function.AbstractDyanasisFunction;
import net.aurika.dyanasis.api.declaration.invokable.function.key.DyanasisFunctionSignature;
import net.aurika.dyanasis.api.declaration.invokable.property.AbstractDyanasisProperty;
import net.aurika.dyanasis.api.declaration.namespace.DyanasisNamespace;
import net.aurika.dyanasis.api.runtime.DyanasisRuntime;
import net.aurika.dyanasis.api.runtime.DyanasisRuntimeObject;
import net.aurika.validate.Validate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public abstract class AbstractDyanasisFile<Doc extends DyanasisFile.FileDoc> implements DyanasisFile, DyanasisRuntimeObject, DyanasisDocEditable<Doc> {

  private final @NotNull DyanasisRuntime runtime;
  private @Nullable DyanasisNamespace usingNamespace;
  private @Nullable Doc doc;

  public AbstractDyanasisFile(@NotNull DyanasisRuntime runtime) {
    this(runtime, null, null);
  }

  public AbstractDyanasisFile(
      @NotNull DyanasisRuntime runtime,
      @Nullable DyanasisNamespace usingNamespace,
      @Nullable Doc doc
  ) {
    Validate.Arg.notNull(runtime, "runtime");
    this.runtime = runtime;
    this.usingNamespace = usingNamespace;
    this.doc = doc;
  }

  @Override
  public @Nullable DyanasisNamespace usingNamespace() {
    return this.usingNamespace;
  }

  @Override
  public void usingNamespace(@Nullable DyanasisNamespace namespace) {
    usingNamespace = namespace;
  }

  @Override
  public abstract @NotNull FilePropertyContainer<? extends FileProperty> dyanasisProperties();

  @Override
  public abstract @NotNull FileFunctionContainer<? extends FileFunction> dyanasisFunctions();

  @Override
  public @Nullable Doc dyanasisDoc() {
    return doc;
  }

  @Override
  public void dyanasisDoc(@Nullable Doc doc) {
    this.doc = doc;
  }

  @Override
  public @NotNull DyanasisRuntime dyanasisRuntime() {
    return runtime;
  }

  public static abstract class AbstractFileProperty extends AbstractDyanasisProperty implements FileProperty {

    public AbstractFileProperty(@NamingContract.Invokable final @NotNull String name) {
      super(name);
    }

    @Override
    public @NotNull DyanasisRuntime dyanasisRuntime() {
      return owner().dyanasisRuntime();
    }

  }

  public static abstract class AbstractFileFunction extends AbstractDyanasisFunction implements FileFunction {

    public AbstractFileFunction(@NotNull DyanasisFunctionSignature key) {
      super(key);
    }

    @Override
    public @NotNull DyanasisRuntime dyanasisRuntime() {
      return owner().dyanasisRuntime();
    }

  }

  public static abstract class AbstractFileDoc implements FileDoc {

    private final @NotNull String value;

    protected AbstractFileDoc(@NotNull String value) {
      Validate.Arg.notNull(value, "value");
      this.value = value;
    }

    @Override
    public @NotNull String value() {
      return value;
    }

    @Override
    public abstract @NotNull DyanasisFile owner();

    @Override
    public @NotNull DyanasisRuntime dyanasisRuntime() {
      return owner().dyanasisRuntime();
    }

  }

}
