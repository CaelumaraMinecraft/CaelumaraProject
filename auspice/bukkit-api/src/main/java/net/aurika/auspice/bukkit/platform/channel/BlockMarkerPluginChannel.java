package net.aurika.auspice.bukkit.platform.channel;

import net.aurika.auspice.platform.location.AbstractBlock3D;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Collections;
import java.util.Map;
import java.util.Objects;

public final class BlockMarkerPluginChannel {

  private final Map<AbstractBlock3D, BlockMarker> markers;

  public BlockMarkerPluginChannel(Map<AbstractBlock3D, BlockMarker> affectedBlocks) {
    this.markers = Objects.requireNonNull(affectedBlocks);
    if (affectedBlocks.isEmpty()) throw new IllegalStateException("Affected blocks is empty");
  }

  @Unmodifiable
  public Map<AbstractBlock3D, BlockMarker> getMarkers() {
    return Collections.unmodifiableMap(markers);
  }

}
