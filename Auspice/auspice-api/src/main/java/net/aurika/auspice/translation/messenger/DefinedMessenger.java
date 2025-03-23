package net.aurika.auspice.translation.messenger;

import net.aurika.auspice.translation.message.manager.MessageManager;
import net.aurika.auspice.translation.TranslationEntry;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * 这个 {@link Messenger} 的子类要求提供默认值
 */
public interface DefinedMessenger extends Messenger {
    @NotNull MessageManager messageManager();

    @NotNull TranslationEntry translationEntry();

    @Nullable String defaultValue();
}
