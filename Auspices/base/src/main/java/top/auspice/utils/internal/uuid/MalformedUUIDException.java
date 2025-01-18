package top.auspice.utils.internal.uuid;

import org.jetbrains.annotations.NotNull;

public final class MalformedUUIDException extends IllegalArgumentException {
    @NotNull
    private final CharSequence uuid;

    public MalformedUUIDException(@NotNull CharSequence uuid, @NotNull Throwable throwable) {
        super("" + '\'' + uuid + '\'', throwable);
        this.uuid = uuid;
    }

    @NotNull
    public CharSequence getUuid() {
        return this.uuid;
    }
}