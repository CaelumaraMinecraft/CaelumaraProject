package net.aurika.caeron.api.tick;

import org.jetbrains.annotations.NotNull;

// TODO better name
public interface CaeronTickTracker {

  long ticks();

  void registerTrigger(@NotNull CaeronTickTrigger trigger);

}
