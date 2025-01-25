package top.auspice.configs.texts.messenger;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import net.aurika.config.path.ConfigEntry;

/**
 * 这个 {@link Messenger} 的子类要求提供默认值
 */
public interface DefinedMessenger extends Messenger {
    @NotNull ConfigEntry getLanguageEntry();

    @Nullable String getDefaultValue();
}
