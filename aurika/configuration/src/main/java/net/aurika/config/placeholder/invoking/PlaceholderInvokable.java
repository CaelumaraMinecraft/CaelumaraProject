package net.aurika.config.placeholder.invoking;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import net.aurika.config.functional.invoking.ConfigFunctionalInvokingData;
import top.auspice.configs.texts.placeholders.context.PlaceholderContextBuilder;

public interface PlaceholderInvokable extends PlaceholderFunctional, PlaceholderAttributeProvider {
    @Nullable Object providePlaceholderAttribute(@NotNull String attributeName);
    @Nullable Object invokePlaceholderFunction(@NotNull String functionName, ConfigFunctionalInvokingData invokeData, PlaceholderContextBuilder context);

}
