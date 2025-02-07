package top.auspice.configs.texts.context.provider;

import org.jetbrains.annotations.MustBeInvokedByOverriders;
import org.jetbrains.annotations.NotNull;
import top.auspice.configs.texts.placeholders.context.TextPlaceholderProvider;

public interface CascadingTextContextProvider extends TextContextProvider {
    @MustBeInvokedByOverriders
    void addMessageContextEdits(@NotNull TextPlaceholderProvider textPlaceholderProvider);

    @NotNull
    default TextPlaceholderProvider getTextContext() {
        TextPlaceholderProvider textPlaceholderProvider = new TextPlaceholderProvider();
        this.addMessageContextEdits(textPlaceholderProvider);
        return textPlaceholderProvider;
    }
}
