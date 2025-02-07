package top.auspice.nbt;

import java.io.IOException;

public interface IOFunction<T, R> {
    R apply(T t) throws IOException;
}