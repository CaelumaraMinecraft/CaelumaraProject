package net.aurika.dyanasis.api.declaration.invokable.function.key;

import net.aurika.dyanasis.api.type.DyanasisTypeIdent;
import net.aurika.dyanasis.api.type.DyanasisTypeIdentAware;
import org.jetbrains.annotations.NotNull;

import java.util.Iterator;

@Deprecated
public interface TypedParameterDyanasisFunctionSignature extends DyanasisFunctionSignature {

  @Override
  @NotNull TypedParameter parameterAt(int index);

  @Override
  @NotNull Iterator<? extends TypedParameter> parameters();

  interface TypedParameter extends Parameter, DyanasisTypeIdentAware {

    /**
     * Gets the type ident of the parameter.
     *
     * @return the type ident
     */
    @NotNull DyanasisTypeIdent dyanasisTypeIdent();

  }

}
