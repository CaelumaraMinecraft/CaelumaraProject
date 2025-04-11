package net.aurika.auspice.bukkit.platform.channel;

import net.aurika.auspice.platform.location.Block3;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

public final class BlockMarkerPluginChannel {

  private final Map<Block3, BlockMarker> markers;

  public BlockMarkerPluginChannel(Map<Block3, BlockMarker> affectedBlocks) {
    this.markers = Objects.requireNonNull(affectedBlocks);
    if (affectedBlocks.isEmpty()) throw new IllegalStateException("Affected blocks is empty");
  }

  @Unmodifiable
  public Map<Block3, BlockMarker> getMarkers() {
    return Collections.unmodifiableMap(markers);
  }

}
