package net.aurika.auspice.platform.event.entity;

import net.aurika.auspice.platform.entity.Entity;
import net.aurika.auspice.platform.event.Event;

public interface EntityEvent extends Event {

  Entity entity();

}
