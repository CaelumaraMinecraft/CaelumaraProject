package net.aurika.auspice.configs.messages.context;

import org.jetbrains.annotations.NotNull;

/**
 * Provider of {@linkplain MessageContext}.
 */
public interface MessageContextProvider {
    /**
     * Provides a message context.
     *
     * @return the message context
     */
    @NotNull MessageContext messageContext();
}
