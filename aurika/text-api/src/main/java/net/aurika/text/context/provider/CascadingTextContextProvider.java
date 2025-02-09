package net.aurika.text.context.provider;

import org.jetbrains.annotations.MustBeInvokedByOverriders;
import org.jetbrains.annotations.NotNull;
import net.aurika.text.placeholders.context.MessagePlaceholderProvider;

public interface CascadingTextContextProvider extends TextContextProvider {
    @MustBeInvokedByOverriders
    void addMessageContextEdits(@NotNull MessagePlaceholderProvider textPlaceholderProvider);

    @NotNull
    default MessagePlaceholderProvider getTextContext() {
        MessagePlaceholderProvider textPlaceholderProvider = new MessagePlaceholderProvider();
        this.addMessageContextEdits(textPlaceholderProvider);
        return textPlaceholderProvider;
    }
}
