package net.aurika.dyanasis.api.declaration.file;

import net.aurika.dyanasis.api.declaration.DyanasisDeclaration;
import net.aurika.dyanasis.api.declaration.NeedOwner;
import net.aurika.dyanasis.api.declaration.doc.DyanasisDoc;
import net.aurika.dyanasis.api.declaration.doc.DyanasisDocAnchor;
import net.aurika.dyanasis.api.declaration.doc.DyanasisDocAware;
import net.aurika.dyanasis.api.declaration.function.DyanasisFunction;
import net.aurika.dyanasis.api.declaration.function.DyanasisFunctionAnchor;
import net.aurika.dyanasis.api.declaration.function.DyanasisFunctionsAware;
import net.aurika.dyanasis.api.declaration.function.container.DyanasisFunctionContainer;
import net.aurika.dyanasis.api.declaration.namespace.DyanasisNamespace;
import net.aurika.dyanasis.api.declaration.namespace.UsingNamespace;
import net.aurika.dyanasis.api.declaration.property.DyanasisPropertiesAware;
import net.aurika.dyanasis.api.declaration.property.DyanasisProperty;
import net.aurika.dyanasis.api.declaration.property.DyanasisPropertyAnchor;
import net.aurika.dyanasis.api.declaration.property.container.DyanasisPropertyContainer;
import net.aurika.dyanasis.api.runtime.DyanasisRuntime;
import net.aurika.dyanasis.api.runtime.DyanasisRuntimeObject;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface DyanasisFile
    extends DyanasisDeclaration,
    UsingNamespace, DyanasisRuntimeObject,
    DyanasisPropertiesAware, DyanasisPropertyAnchor,
    DyanasisFunctionsAware, DyanasisFunctionAnchor,
    DyanasisDocAware, DyanasisDocAnchor {

  @Override
  @Nullable DyanasisNamespace usingNamespace();

  @Override
  void usingNamespace(@Nullable DyanasisNamespace namespace);

  @Override
  @NotNull FilePropertyContainer<? extends FileProperty> dyanasisProperties();

  @Override
  @NotNull FileFunctionContainer<? extends FileFunction> dyanasisFunctions();

  @Override
  @Nullable FileDoc dyanasisDoc();

  @Override
  @NotNull DyanasisRuntime dyanasisRuntime();

  interface FilePropertyContainer<P extends FileProperty> extends DyanasisPropertyContainer<P>, NeedOwner {

    @Override
    @NotNull DyanasisFile owner();

  }

  interface FileFunctionContainer<F extends FileFunction> extends DyanasisFunctionContainer<F>, NeedOwner {

    @Override
    @NotNull DyanasisFile owner();

  }

  interface FileProperty extends DyanasisProperty {

    @Override
    @NotNull DyanasisFile owner();

  }

  interface FileFunction extends DyanasisFunction {

    @Override
    @NotNull DyanasisFile owner();

  }

  /**
   * The dyanasis doc to a dyanasis file.
   */
  interface FileDoc extends DyanasisDoc {

    @Override
    @NotNull DyanasisFile owner();

  }

}
