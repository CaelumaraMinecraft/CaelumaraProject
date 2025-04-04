package net.aurika.configuration.part.adapter;

import net.aurika.configuration.part.ConfigSequence;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;

public interface ConfigSequenceAdapter<S extends ConfigSequence> extends ConfigPartAdapter<S> {

  @NotNull Collection<?> asCollection(@NotNull S configSequence);

}
