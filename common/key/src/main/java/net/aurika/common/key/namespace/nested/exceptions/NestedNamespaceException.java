package net.aurika.common.key.namespace.nested.exceptions;

public abstract class NestedNamespaceException extends RuntimeException {
    public NestedNamespaceException() {
    }

    public NestedNamespaceException(String message) {
        super(message);
    }

    public NestedNamespaceException(String message, Throwable cause) {
        super(message, cause);
    }

    public NestedNamespaceException(Throwable cause) {
        super(cause);
    }

    protected NestedNamespaceException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
