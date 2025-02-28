package net.aurika.dyanasis.exception;

public class DyanasisException extends RuntimeException {
    public DyanasisException() {
    }

    public DyanasisException(String message) {
        super(message);
    }

    public DyanasisException(String message, Throwable cause) {
        super(message, cause);
    }

    public DyanasisException(Throwable cause) {
        super(cause);
    }

    protected DyanasisException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
