package net.aurika.text.context.provider;

import org.jetbrains.annotations.NotNull;
import net.aurika.text.placeholders.context.MessagePlaceholderProvider;

public interface TextContextProvider {
    @NotNull MessagePlaceholderProvider getTextContext();   //ecliptor
}
