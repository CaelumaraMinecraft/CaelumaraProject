package net.aurika.configuration.placeholder.invoking;

import net.aurika.configuration.functional.invoking.ConfigFunctionalInvokingData;
import net.aurika.text.placeholders.context.PlaceholderContextBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface PlaceholderFunctional {

  @Nullable Object invokePlaceholderFunction(@NotNull String functionName, ConfigFunctionalInvokingData invokeData, PlaceholderContextBuilder context);

}
