package top.auspice.configs.texts.messenger;

import org.jetbrains.annotations.NotNull;
import top.auspice.configs.texts.Locale;
import top.auspice.configs.messages.provider.MessageProvider;
import top.auspice.configs.messages.provider.SingleMessageProvider;

import java.util.Objects;

public final class OptionalMessenger implements Messenger {
    @NotNull
    private final Messenger messenger;

    public OptionalMessenger(@NotNull Messenger messenger) {
        Objects.requireNonNull(messenger);
        this.messenger = messenger;
    }

    @NotNull
    public Messenger getMessenger() {
        return this.messenger;
    }

    @NotNull
    public MessageProvider getProvider(@NotNull Locale locale) {
        Objects.requireNonNull(locale);
        MessageProvider provider = this.messenger.getProvider(locale);
        if (provider == null) {
            provider = new SingleMessageProvider(null);
        }

        return provider;
    }
}
