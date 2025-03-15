package net.aurika.dyanasis.api.type;

import net.aurika.dyanasis.api.declaration.invokable.function.DyanasisFunctionKey;
import net.aurika.dyanasis.api.object.DyanasisObject;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public interface InstanceFunctionHandler<O extends DyanasisObject> extends InstanceInvokableHandler<O, DyanasisFunctionKey, DyanasisObject.ObjectFunction> {
    @Override
    @NotNull Map<DyanasisFunctionKey, ? extends DyanasisObject.ObjectFunction> handle(@NotNull O object);
}
