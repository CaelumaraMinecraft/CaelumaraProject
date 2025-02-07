package top.auspice.api.user;

public class AuspiceUserException extends RuntimeException {
    public AuspiceUserException() {
    }

    public AuspiceUserException(String message) {
        super(message);
    }

    public AuspiceUserException(String message, Throwable cause) {
        super(message, cause);
    }

    public AuspiceUserException(Throwable cause) {
        super(cause);
    }

    protected AuspiceUserException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
