package net.aurika.configuration.part.adapter;

import net.aurika.configuration.part.ConfigSection;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public interface ConfigSectionAdapter<S extends ConfigSection> extends ConfigPartAdapter<S> {

  @NotNull Map<? extends CharSequence, ?> asMap(@NotNull S configSection);

}
