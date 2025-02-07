package net.aurika.namespace.nested.exceptions;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class NestedNamespaceContainsIllegalLevelException extends NestedNamespaceException {

    public NestedNamespaceContainsIllegalLevelException(String @NotNull [] nesting, int level, String reason) {
        super(precessMessage(nesting, level, reason));
    }

    public NestedNamespaceContainsIllegalLevelException(String @NotNull [] nesting, int level, String reason, Throwable cause) {
        super(precessMessage(nesting, level, reason), cause);
    }

    protected static @NotNull String precessMessage(String @NotNull [] nesting, int level, String reason) {
        return "Level " + level + " " + nesting[level] + " at Namespace nesting '" + Arrays.toString(nesting) + "' contains error: " + reason;
    }
}
