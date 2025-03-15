package net.aurika.dyanasis.api.declaration.namespace;

import net.aurika.validate.Validate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.util.Arrays;
import java.util.Objects;

public final class DyanasisNamespacePath {
    private final @NotNull String @NotNull [] path;

    public static @NotNull DyanasisNamespacePath path(String... path) {
        return new DyanasisNamespacePath(path);
    }

    private DyanasisNamespacePath(@NotNull String @NotNull [] path) {
        Validate.Arg.nonNullArray(path, "path");
        this.path = path.clone();
    }

    /**
     * Gets a section indexed by the {@code index}.
     *
     * @param index the index
     * @return the indexed section
     * @throws IndexOutOfBoundsException when the {@code index} is out of the bound of path
     */
    public @NotNull String sectionAt(int index) {
        return path[index];
    }

    @Range(from = 0, to = Integer.MAX_VALUE)
    public int length() {
        return path.length;
    }

    public @NotNull String @NotNull [] path() {
        return path.clone();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof DyanasisNamespacePath that)) {
            return false;
        }
        return Objects.deepEquals(path, that.path);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(path);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + Arrays.toString(path);
    }
}
