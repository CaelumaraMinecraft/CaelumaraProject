package net.aurika.dyanasis.api.type;

import net.aurika.dyanasis.api.declaration.invokable.DyanasisInvokable;
import net.aurika.dyanasis.api.object.DyanasisObject;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

@FunctionalInterface
public interface InstanceInvokableHandler<O extends DyanasisObject, K, I extends DyanasisInvokable> {

  @NotNull Map<K, ? extends I> handle(@NotNull O object);

}