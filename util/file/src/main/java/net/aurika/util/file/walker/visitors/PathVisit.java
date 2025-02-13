package net.aurika.util.file.walker.visitors;

import net.aurika.validate.Validate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

public final class PathVisit {

    private final @NotNull Type visitType;
    private final @NotNull Path path;
    private final @Nullable BasicFileAttributes attributes;
    private final @Nullable IOException exception;

    public PathVisit(@NotNull Type visitType, @NotNull Path path, @Nullable BasicFileAttributes attributes, @Nullable IOException exception) {
        Validate.Arg.notNull(visitType, "visitType");
        Validate.Arg.notNull(path, "path");
        this.visitType = visitType;
        this.path = path;
        this.attributes = attributes;
        this.exception = exception;
    }

    public @NotNull Type visitType() {
        return visitType;
    }

    public @NotNull Path path() {
        return path;
    }

    public @Nullable BasicFileAttributes attributes() {
        return attributes;
    }

    public @Nullable IOException exception() {
        return exception;
    }

    public PathVisit(@NotNull Type type, @NotNull Path file, @Nullable BasicFileAttributes attrs) {
        this(type, file, attrs, null);
    }

    public PathVisit(@NotNull Type type, @NotNull Path file, @Nullable IOException ioe) {
        this(type, file, null, ioe);
    }

    public boolean hasErrors() {
        return exception != null;
    }

    public enum Type {
        START_DIRECTORY,
        END_DIRECTORY,

        /**
         * An entry (file) in a directory
         */
        ENTRY
    }
}
