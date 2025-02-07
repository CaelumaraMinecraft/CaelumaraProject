package top.auspice.utils.unsafe.string;

import org.jetbrains.annotations.NotNull;

public interface PrettyString<T> {
    void toPrettyString(T obj, @NotNull PrettyStringContext context);
}