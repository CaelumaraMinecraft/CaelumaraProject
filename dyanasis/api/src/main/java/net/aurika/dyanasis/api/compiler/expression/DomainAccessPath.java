package net.aurika.dyanasis.api.compiler.expression;

import net.aurika.validate.Validate;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Objects;

public class DomainAccessPath {

  protected final @NotNull String @NotNull [] path;

  public DomainAccessPath(@NotNull String @NotNull [] path) {
    Validate.Arg.nonNullArray(path, "chain");
    this.path = path;
  }

  public int length() {
    return path.length;
  }

  /**
   * @throws IndexOutOfBoundsException when the {@code index} of more than the length of the path
   */
  public @NotNull String atIndex(int index) {
    return path[index];
  }

  public String @NotNull [] path() {
    return path.clone();
  }

  @Override
  public int hashCode() {
    return Arrays.hashCode(path);
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof DomainAccessPath that)) return false;
    return Objects.deepEquals(path, that.path);
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + Arrays.toString(path);
  }

}
