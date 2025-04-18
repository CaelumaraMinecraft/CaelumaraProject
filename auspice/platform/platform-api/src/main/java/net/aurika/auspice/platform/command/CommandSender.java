package net.aurika.auspice.platform.command;

import net.aurika.auspice.platform.Platform;
import net.aurika.auspice.platform.permission.Permissible;
import net.aurika.common.annotation.ImplDontThrowUnsupported;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

public interface CommandSender extends Permissible {

  @ImplDontThrowUnsupported
  void sendMessage(@NotNull Component message);

  /**
   * Returns the server instance that this command is running on
   *
   * @return Server instance
   */
  @ImplDontThrowUnsupported
  @NotNull Platform getServer();

  /**
   * Gets the name of this command provider
   *
   * @return Name of the provider
   */
  @ImplDontThrowUnsupported
  @NotNull String getName();

}
