package net.aurika.nbt.snbt.reader;

import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class SNBTTokenWithMetadata {
    @NotNull
    private final SNBTToken token;
    private final int charIndex;

    public SNBTTokenWithMetadata(@NotNull SNBTToken token, int charIndex) {
        Intrinsics.checkNotNullParameter(token, "token");
        this.token = token;
        this.charIndex = charIndex;
    }

    @NotNull
    public SNBTToken getToken() {
        return this.token;
    }

    public int getCharIndex() {
        return this.charIndex;
    }

    @NotNull
    public SNBTToken component1() {
        return this.token;
    }

    public int component2() {
        return this.charIndex;
    }

    @NotNull
    public SNBTTokenWithMetadata copy(@NotNull SNBTToken token, int charIndex) {
        Intrinsics.checkNotNullParameter(token, "token");
        return new SNBTTokenWithMetadata(token, charIndex);
    }

    @NotNull
    public String toString() {
        return "SNBTTokenWithMetadata(token=" + this.token + ", charIndex=" + this.charIndex + ')';
    }

    public int hashCode() {
        int result = this.token.hashCode();
        result = result * 31 + Integer.hashCode(this.charIndex);
        return result;
    }

    public boolean equals(@Nullable Object other) {
        if (this == other) {
            return true;
        } else if (!(other instanceof SNBTTokenWithMetadata var2)) {
            return false;
        } else {
            if (!Intrinsics.areEqual(this.token, var2.token)) {
                return false;
            } else {
                return this.charIndex == var2.charIndex;
            }
        }
    }
}
