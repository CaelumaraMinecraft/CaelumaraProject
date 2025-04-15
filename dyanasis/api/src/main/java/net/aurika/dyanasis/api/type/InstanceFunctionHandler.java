package net.aurika.dyanasis.api.type;

import net.aurika.dyanasis.api.declaration.function.signature.DyanasisFunctionSignature;
import net.aurika.dyanasis.api.object.DyanasisObject;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public interface InstanceFunctionHandler<O extends DyanasisObject> extends InstanceMemberHandler<O, DyanasisFunctionSignature, DyanasisObject.ObjectFunction> {

  @Override
  @NotNull Map<DyanasisFunctionSignature, ? extends DyanasisObject.ObjectFunction> members(@NotNull O object);

}
