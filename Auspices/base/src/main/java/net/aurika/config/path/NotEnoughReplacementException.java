package net.aurika.config.path;

// TODO rename
/**
 * 代表在将一个不确定的配置路径构建为确定的配置路径时, 缺失一个或多个
 */
public class NotEnoughReplacementException extends Exception {
    public NotEnoughReplacementException() {
    }

    public NotEnoughReplacementException(String message) {
        super(message);
    }

    public NotEnoughReplacementException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotEnoughReplacementException(Throwable cause) {
        super(cause);
    }

    protected NotEnoughReplacementException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
