package net.aurika.config.part.adapter;

import net.aurika.config.part.ConfigSection;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

public interface ConfigSectionAdapter<S extends ConfigSection> extends ConfigPartAdapter<S> {

  @NotNull Map<? extends CharSequence, ?> asMap(@NotNull S configSection);

}
