package net.aurika.config.accessor;

public class UndefinedConfigPathAccessException extends RuntimeException {
    public UndefinedConfigPathAccessException() {
    }

    public UndefinedConfigPathAccessException(String message) {
        super(message);
    }

    public UndefinedConfigPathAccessException(String message, Throwable cause) {
        super(message, cause);
    }

    public UndefinedConfigPathAccessException(Throwable cause) {
        super(cause);
    }

    protected UndefinedConfigPathAccessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
