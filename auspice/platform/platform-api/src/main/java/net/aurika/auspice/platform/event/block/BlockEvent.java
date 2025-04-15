package net.aurika.auspice.platform.event.block;

import net.aurika.auspice.platform.block.Block;
import net.aurika.auspice.platform.event.Event;

public interface BlockEvent extends Event {

  Block block();

}
