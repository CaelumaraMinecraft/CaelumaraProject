package net.aurika.caeron.api.tick;

import org.jetbrains.annotations.NotNull;

public interface CaeronTickTrackerAware {

  @NotNull CaeronTickTracker caeronTickTracker();

}
