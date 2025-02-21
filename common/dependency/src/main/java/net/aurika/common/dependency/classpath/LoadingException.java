package net.aurika.common.dependency.classpath;

public class LoadingException extends Exception {
    public LoadingException(String message) {
        super(message);
    }

    public LoadingException(String message, Throwable cause) {
        super(message, cause);
    }
}
