package net.aurika.utils.file.walker;

import net.aurika.checker.Checker;
import org.jetbrains.annotations.NotNull;

import java.nio.file.FileVisitResult;

public interface FileWalkerController {
    /**
     * Pops the directory node that is the current top of the stack so that
     * there are no more events for the directory (including no END_DIRECTORY)
     * event. This method is a no-op if the stack is empty or the walker is
     * closed.
     */
    void skipDirectory();

    /**
     * Skips the remaining entries in the directory at the top of the stack.
     * This method is a no-op if the stack is empty or the walker is closed.
     */
    void skipRemainingSiblings();

    void close();

    default void processResult(@NotNull FileVisitResult result) {
        Checker.Arg.notNull(result, "result");
        switch (result) {
            case FileVisitResult.TERMINATE -> close();
            case FileVisitResult.SKIP_SUBTREE -> skipDirectory();
            case FileVisitResult.SKIP_SIBLINGS -> skipRemainingSiblings();
            case FileVisitResult.CONTINUE -> {
                // Do nothing
            }
        }
    }
}