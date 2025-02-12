package net.aurika.nbt.stream.exception;

import java.io.Serial;

public class NBTWriteException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public NBTWriteException(String message) {
        super(message);
    }

    public NBTWriteException(String message, Throwable cause) {
        super(message, cause);
    }
}
