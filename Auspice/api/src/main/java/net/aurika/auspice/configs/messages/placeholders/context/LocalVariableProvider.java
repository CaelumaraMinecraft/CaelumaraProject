package net.aurika.auspice.configs.messages.placeholders.context;

import net.aurika.auspice.configs.messages.placeholders.PlaceholderParts;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface LocalVariableProvider extends VariableProvider {
    /**
     * Provides local variable by {@linkplain PlaceholderParts}.
     * @param parts the parts
     * @return the local variable
     */
    @Nullable Object provideLocalVariable(@NotNull PlaceholderParts parts);
}
