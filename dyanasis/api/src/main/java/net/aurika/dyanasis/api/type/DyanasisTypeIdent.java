package net.aurika.dyanasis.api.type;

import net.aurika.dyanasis.api.declaration.namespace.DyanasisNamespaceIdent;
import net.aurika.common.validate.Validate;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class DyanasisTypeIdent implements DyanasisTypeIdentAware {

  private final @NotNull DyanasisNamespaceIdent namespaceIdent;
  private final @NotNull String typeName;

  public DyanasisTypeIdent(@NotNull DyanasisNamespaceIdent namespaceID, @NotNull String typeName) {
    Validate.Arg.notNull(namespaceID, "namespaceID");
    Validate.Arg.notNull(typeName, "typeName");
    this.namespaceIdent = namespaceID;
    this.typeName = typeName;
  }

  public @NotNull DyanasisNamespaceIdent namespaceID() {
    return namespaceIdent;
  }

  public @NotNull String typeName() {
    return typeName;
  }

  public @NotNull String asString() {
    return asString(".");
  }

  /**
   * Returns the dyanasis type ident as a string like {@code net.aurika.dyanasis.std.Map}, the {@code .} is {@code delimiter}.
   *
   * @param delimiter the delimiter
   * @return the string
   */
  public @NotNull String asString(@NotNull String delimiter) {
    return namespaceIdent.asString(delimiter) + delimiter + typeName;
  }

  @Override
  public DyanasisTypeIdent dyanasisTypeIdent() {
    return this;
  }

  @Override
  public int hashCode() {
    return Objects.hash(namespaceIdent, typeName);
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof DyanasisTypeIdent)) return false;
    DyanasisTypeIdent that = (DyanasisTypeIdent) obj;
    return Objects.equals(namespaceIdent, that.namespaceIdent) && Objects.equals(
        typeName, that.typeName);
  }

  @Override
  public String toString() {
    return getClass().getSimpleName() + "[" + asString(".") + "]";
  }

}
