package top.auspice.utils.number;

import org.jetbrains.annotations.NotNull;

public enum Radix {
    BINARY(2, "0b"),//
    OCTAL(8, "0o"),//
    DECIMAL(10, ""),//
    HEXADECIMAL(16, "0x");//
    private final int radix;
    @NotNull
    private final String prefix;
    @NotNull
    public static final Radix[] RADIX = values();

    Radix(int radix, @NotNull String prefix) {
        this.radix = radix;
        this.prefix = prefix;
    }

    public final int getRadix() {
        return this.radix;
    }

    @NotNull
    public final String getPrefix() {
        return this.prefix;
    }

}
