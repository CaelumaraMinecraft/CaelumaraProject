package net.aurika.dyanasis.api.invoking.result;

import org.jetbrains.annotations.NotNull;

public interface DyanasisFunctionResult {
    static boolean isSuccessful(@NotNull DyanasisFunctionResult result) {
        return result instanceof DyanasisFunctionResultSuccess;
    }
}
