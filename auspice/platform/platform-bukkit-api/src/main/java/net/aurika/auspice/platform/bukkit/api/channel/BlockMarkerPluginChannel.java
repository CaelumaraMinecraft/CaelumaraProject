package net.aurika.auspice.platform.bukkit.api.channel;

import net.aurika.auspice.platform.location.grid.Grid3Pos;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

public final class BlockMarkerPluginChannel {

  private final Map<Grid3Pos, BlockMarker> markers;

  public BlockMarkerPluginChannel(Map<Grid3Pos, BlockMarker> affectedBlocks) {
    this.markers = Objects.requireNonNull(affectedBlocks);
    if (affectedBlocks.isEmpty()) throw new IllegalStateException("Affected blocks is empty");
  }

  @Unmodifiable
  public Map<Grid3Pos, BlockMarker> getMarkers() {
    return Collections.unmodifiableMap(markers);
  }

}
