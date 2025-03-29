package net.aurika.auspice.server.command;

import net.aurika.auspice.configs.messages.MessageObject;
import net.aurika.auspice.server.core.Server;
import net.aurika.auspice.server.permission.Permissible;
import net.aurika.common.annotations.ImplDontThrowUnsupported;
import org.jetbrains.annotations.NotNull;

public interface CommandSender extends Permissible {

  @ImplDontThrowUnsupported
  void sendMessage(@NotNull MessageObject message);

  /**
   * Returns the server instance that this command is running on
   *
   * @return Server instance
   */
  @ImplDontThrowUnsupported
  @NotNull Server getServer();

  /**
   * Gets the name of this command provider
   *
   * @return Name of the provider
   */
  @ImplDontThrowUnsupported
  @NotNull String getName();

}
