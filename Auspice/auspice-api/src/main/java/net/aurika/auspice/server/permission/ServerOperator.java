package net.aurika.auspice.server.permission;

import net.aurika.auspice.server.entity.Player;

/**
 * Represents an object that may become a server operator, such as a {@link Player}.
 */
public interface ServerOperator {

  boolean isOp();

  void setOp(boolean value);

}
