package net.aurika.dyanasis.api.declaration.function.signature;

import net.aurika.dyanasis.api.Named;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

@Deprecated
public interface NamedParamDyanasisFunctionSignature extends DyanasisFunctionSignature {

  @Override
  @NotNull NamedParameter parameterAt(int index);

  @Override
  @NotNull Iterator<? extends NamedParameter> parameters();

  interface NamedParameter extends Parameter, Named {

    /**
     * Gets the parameter name.
     *
     * @return the name
     */
    @Override
    @NotNull String name();

  }

}
