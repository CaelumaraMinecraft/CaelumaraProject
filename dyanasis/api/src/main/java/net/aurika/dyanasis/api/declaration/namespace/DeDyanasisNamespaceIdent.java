package net.aurika.dyanasis.api.declaration.namespace;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Objects;

@Deprecated(forRemoval = true)
public class DeDyanasisNamespaceIdent {

  private final @NotNull String @NotNull [] path;

  public DeDyanasisNamespaceIdent(@NotNull String @NotNull [] path) {
    this.path = path.clone();
  }

  public @NotNull String @NotNull [] path() {
    return path.clone();
  }

  public @NotNull String asString() {
    return asString(".");
  }

  public @NotNull String asString(@NotNull String delimiter) {
    return String.join(delimiter, path);
  }

  @Override
  public boolean equals(Object o) {
    if (!(o instanceof DeDyanasisNamespaceIdent that)) return false;
    return Objects.deepEquals(path, that.path);
  }

  @Override
  public int hashCode() {
    return Arrays.hashCode(path);
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + "[" + asString() + "]";
  }

}
