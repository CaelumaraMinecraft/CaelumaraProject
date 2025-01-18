package top.auspice.server.command;

import org.jetbrains.annotations.NotNull;
import top.auspice.api.annotations.ImplDontThrowUnsupported;
import top.auspice.configs.messages.MessageObject;
import top.auspice.configs.texts.compiler.TextObject;
import top.auspice.server.core.Server;
import top.auspice.server.permissions.Permissible;

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
