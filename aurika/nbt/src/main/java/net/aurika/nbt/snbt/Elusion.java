package net.aurika.nbt.snbt;

import kotlin.jvm.internal.Intrinsics;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public final class Elusion {

    private Elusion() {
    }

    public static boolean isSafeCharacter(char c) {
        return Intrinsics.compare(65, c) <= 0 && Intrinsics.compare(c, 90) <= 0 || Intrinsics.compare(97, c) <= 0 && Intrinsics.compare(c, 122) <= 0 || Intrinsics.compare(48, c) <= 0 && Intrinsics.compare(c, 57) <= 0 || c == '_' || c == '.' || c == '+' || c == '-';
    }

    @NotNull
    public static CharSequence escapeIfNeeded(@NotNull String s) {
        Objects.requireNonNull(s);
        boolean totallySafe = true;
        int singleCharCount = 0;
        int doubleCharCount = 0;
        int backslashCount = 0;
        int ii = 0;

        for (int builder = s.length(); ii < builder; ++ii) {
            char c = s.charAt(ii);
            if (totallySafe) {
                if (isSafeCharacter(c)) {
                    continue;
                }
            }

            totallySafe = false;
            if (c == '\'') {
                ++singleCharCount;
            } else if (c == '"') {
                ++doubleCharCount;
            } else if (c == '\\') {
                ++backslashCount;
            }
        }

        if (totallySafe) {
            return s;
        } else {
            ii = (int) Math.min(singleCharCount, (double) doubleCharCount);
            StringBuilder builder = new StringBuilder(s.length() + backslashCount + ii + 2);
            char quoteChar = (char) (ii == singleCharCount ? 39 : 34);
            builder.append(quoteChar);
            int i = 0;

            for (int var9 = s.length(); i < var9; ++i) {
                char c = s.charAt(i);
                if (c == '\\' || c == quoteChar) {
                    builder.append('\\');
                }

                builder.append(c);
            }

            builder.append(quoteChar);
            return builder;
        }
    }
}
