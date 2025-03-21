package net.aurika.dyanasis.api.invoking.result;

import org.jetbrains.annotations.NotNull;

public interface DyanasisFunctionResult {

    DyanasisFunctionResultVoid VOID = new DyanasisFunctionResultVoid() {
    };

    static boolean isSuccessful(@NotNull DyanasisFunctionResult result) {
        return result instanceof DyanasisFunctionResultSuccess;
    }

    static boolean isFailed(@NotNull DyanasisFunctionResult result) {
        return result instanceof DyanasisFunctionResultFailed;
    }

    static boolean isVoid(@NotNull DyanasisFunctionResult result) {
        return result instanceof DyanasisFunctionResultVoid;
    }
}
