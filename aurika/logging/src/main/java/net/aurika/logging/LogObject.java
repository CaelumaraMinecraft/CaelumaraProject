package net.aurika.logging;

import org.jetbrains.annotations.NotNull;

public interface LogObject {
    @NotNull IndentSequence getIndentSequence();

    @NotNull String toLogString();
}
