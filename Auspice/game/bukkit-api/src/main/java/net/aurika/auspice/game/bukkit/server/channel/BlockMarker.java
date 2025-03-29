package net.aurika.auspice.game.bukkit.server.channel;

import java.awt.*;
import java.time.Duration;

public class BlockMarker {

  public final Duration duration;
  public final Color color;
  public final String title;

  public BlockMarker(Duration duration, Color color, String title) {
    this.duration = duration;
    this.color = color;
    this.title = title;
  }

}
