package net.aurika.util.number;

import org.jetbrains.annotations.NotNull;

public enum Radix {
    BINARY(2, "0b"),//
    OCTAL(8, "0o"),//
    DECIMAL(10, ""),//
    HEXADECIMAL(16, "0x");//
    private final int radix;
    private final @NotNull String prefix;

    Radix(int radix, @NotNull String prefix) {
        this.radix = radix;
        this.prefix = prefix;
    }

    public final int getRadix() {
        return this.radix;
    }

    public final @NotNull String getPrefix() {
        return this.prefix;
    }
}
