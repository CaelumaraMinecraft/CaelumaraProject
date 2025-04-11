package net.aurika.configuration.part.adapter;

import net.aurika.common.annotation.flow.StaticUse;
import net.aurika.configuration.part.ConfigPart;
import org.jetbrains.annotations.NotNull;

@StaticUse
public interface ConfigPartAdapter<P extends ConfigPart> {

  @NotNull Class<? extends P> targetType();

}
