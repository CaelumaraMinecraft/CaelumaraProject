package net.aurika.nbt.stream.exception;

import java.io.Serial;

public class NBTParseException extends RuntimeException {
    @Serial
    private static final long serialVersionUID = 1L;

    public NBTParseException(String message) {
        super(message);
    }

    public NBTParseException(String message, Throwable cause) {
        super(message, cause);
    }
}
