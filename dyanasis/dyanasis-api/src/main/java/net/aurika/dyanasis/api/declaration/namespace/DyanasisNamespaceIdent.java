package net.aurika.dyanasis.api.declaration.namespace;

import net.aurika.dyanasis.api.type.DyanasisTypeIdent;
import net.aurika.validate.Validate;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Range;

import java.util.Arrays;
import java.util.Objects;

public final class DyanasisNamespaceIdent implements DyanasisNamespaceIdentAware {

  private final @NotNull String @NotNull [] path;

  public static @NotNull DyanasisNamespaceIdent path(String... path) {
    return new DyanasisNamespaceIdent(path);
  }

  private DyanasisNamespaceIdent(@NotNull String @NotNull [] path) {
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

  public @NotNull String endSection() {
    return path[path.length - 1];
  }

  @Contract("-> new")
  public @NotNull String @NotNull [] path() {
    return path.clone();
  }

  public @NotNull DyanasisTypeIdent mergeToTypeIdent(@NotNull String typeName) {
    Validate.Arg.notNull(typeName, "typeName");
    return new DyanasisTypeIdent(this, typeName);
  }

  public @NotNull String asString() {
    return asString(".");
  }

  public @NotNull String asString(@NotNull String delimiter) {
    return String.join(delimiter, path);
  }

  @Override
  public @NotNull DyanasisNamespaceIdent dyanasisNamespaceIdent() {
    return DyanasisNamespaceIdent.this;
  }

  @Override
  public int hashCode() {
    return Arrays.hashCode(path);
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof DyanasisNamespaceIdent that)) {
      return false;
    }
    return Objects.deepEquals(path, that.path);
  }

  @Override
  protected DyanasisNamespaceIdent clone() throws CloneNotSupportedException {
    throw new CloneNotSupportedException();
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + Arrays.toString(path);
  }

}
